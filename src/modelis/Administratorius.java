package modelis;

import java.util.List;

public class Administratorius extends Vartotojas {

    public Administratorius(String vardas, String pavarde, String elPastas, String telefonas, String slaptazodis) {
        super(vardas, pavarde, elPastas, telefonas, slaptazodis);
    }

    public void perziuretiVisusDuomenis(List<Savininkas> savininkai, List<PaslauguImone> imones) {
        System.out.println("===== VISI SISTEMOS DUOMENYS =====");
        System.out.println("Savininkai (" + savininkai.size() + "):");
        for (Savininkas s : savininkai) {
            System.out.println("  " + s.getVardas() + " " + s.getPavarde() + " | " + s.getElPastas()
                    + " | Augintinių: " + s.getAugintiniai().size()
                    + " | Rezervacijų: " + s.getRezervacijos().size());
        }
        System.out.println("Įmonės (" + imones.size() + "):");
        for (PaslauguImone imone : imones) {
            System.out.println("  " + imone.gautiInformacija());
            System.out.println("    Vadovų: " + imone.getVadovai().size());
            for (Priziuretojas p : imone.getPriziuretojai()) {
                System.out.println("    Prižiūrėtojas: " + p.getVardas() + " " + p.getPavarde()
                        + " | Įvertinimas: " + p.getVidutinisIvertinimas()
                        + " | Rezervacijų: " + p.getRezervacijos().size());
            }
            for (Paslauga paslauga : imone.getPaslaugos()) {
                System.out.println("    " + paslauga.gautiInformacija() + " | Populiarumas: " + paslauga.getPopuliarumas());
            }
        }
        System.out.println("==================================");
    }

    public void valdytiVartotojus(List<Savininkas> savininkai, Savininkas savininkas, String veiksmas, String reiksme) {
        if (!savininkai.contains(savininkas)) {
            System.out.println("KLAIDA: vartotojas " + savininkas.getVardas() + " sistemoje nerastas!");
            return;
        }
        switch (veiksmas) {
            case "keistiElPasta":
                String senas = savininkas.getElPastas();
                savininkas.setElPastas(reiksme);
                System.out.println("Administratorius pakeitė " + savininkas.getVardas() + " el. paštą: " + senas + " -> " + reiksme);
                break;
            case "šalinti":
                for (Rezervacija r : savininkas.getRezervacijos()) {
                    if (arAktyviRezervacija(r)) {
                        System.out.println("KLAIDA: " + savininkas.getVardas() + " turi aktyvių rezervacijų, šalinti negalima!");
                        return;
                    }
                }
                savininkai.remove(savininkas);
                System.out.println("Administratorius pašalino vartotoją " + savininkas.getVardas() + " " + savininkas.getPavarde());
                break;
            default:
                System.out.println("KLAIDA: nežinomas veiksmas '" + veiksmas + "'!");
        }
    }

    public void valdytiPaslaugas(Paslauga paslauga, String veiksmas) {
        switch (veiksmas) {
            case "aktyvuoti":
                paslauga.setArAktyvi(true);
                System.out.println("Administratorius aktyvavo paslaugą " + paslauga.getPavadinimas());
                break;
            case "deaktyvuoti":
                paslauga.setArAktyvi(false);
                System.out.println("Administratorius deaktyvavo paslaugą " + paslauga.getPavadinimas());
                break;
            default:
                System.out.println("KLAIDA: nežinomas veiksmas '" + veiksmas + "'!");
        }
    }

    public void valdytiRezervacijas(Rezervacija rezervacija, String naujaBusena) {
        if (rezervacija.getBusena().equals("Atlikta")) {
            System.out.println("KLAIDA: atliktos rezervacijos būsenos keisti negalima!");
            return;
        }
        System.out.print("Administratorius: ");
        rezervacija.keistiBusena(naujaBusena);
    }

    public void pasleptiAtsiliepima(Atsiliepimas atsiliepimas) {
        atsiliepimas.setArAktyvus(false);
        // Perskaiciuojame visus susijusius vidurkius
        Rezervacija r = atsiliepimas.getRezervacija();
        r.getPriziuretojas().perskaiciuotiIvertinima();
        r.getPaslauga().getImone().perskaiciuotiIvertinima();
        r.getSavininkas().perskaiciuotiIvertinima();
        System.out.println("Administratorius paslėpė atsiliepimą: " + atsiliepimas.gautiInformacija());
    }

    public void salintiKomentara(Komentaras komentaras) {
        // Atkabiname nuo tevinio komentaro
        if (komentaras.getTevinisKomentaras() != null) {
            komentaras.getTevinisKomentaras().getAtsakymai().remove(komentaras);
        }
        int kiekis = salintiSuAtsakymais(komentaras);
        System.out.println("Administratorius pašalino komentarą ir " + (kiekis - 1) + " atsakymą(-us)");
    }

    // Rekursija: pasalina komentara ir visus jo atsakymus is priziuretojo profilio
    private int salintiSuAtsakymais(Komentaras komentaras) {
        int kiekis = 1;
        for (Komentaras atsakymas : komentaras.getAtsakymai()) {
            kiekis += salintiSuAtsakymais(atsakymas);
        }
        komentaras.getPriziuretojas().getKomentarai().remove(komentaras);
        return kiekis;
    }

    private boolean arAktyviRezervacija(Rezervacija r) {
        String busena = r.getBusena();
        return busena.equals("Laukiama") || busena.equals("Patvirtinta") || busena.equals("Vykdoma");
    }

    @Override
    public String getRole() {
        return "Administratorius";
    }
}
