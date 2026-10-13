package modelis;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class Savininkas extends Vartotojas {
    private List<Augintinis> augintiniai; // Rysys "0..*" (savininkas gali tureti daug augintiniu)
    private List<Rezervacija> rezervacijos; // Rysys "0..*"
    private double vidutinisIvertinimas; // Kliento ivertinimas (is priziuretoju atsiliepimu)

    public Savininkas(String vardas, String pavarde, String elPastas, String telefonas, String slaptazodis) {
        super(vardas, pavarde, elPastas, telefonas, slaptazodis);
        this.augintiniai = new ArrayList<>();
        this.rezervacijos = new ArrayList<>();
        this.vidutinisIvertinimas = 0.0;
    }

    public void registruotiAugintini(Augintinis augintinis) {
        this.augintiniai.add(augintinis);
        augintinis.setSavininkas(this); // Abipusis rysys
        System.out.println("Augintinis " + augintinis.getVardas() + " priregistruotas savininkui " + vardas + " " + pavarde);
    }

    // Iesko aktyviu paslaugu, kurios tinka konkreciam augintiniui
    public List<Paslauga> ieskotiPaslaugu(PaslauguImone imone, Augintinis augintinis) {
        List<Paslauga> rezultatai = new ArrayList<>();
        for (Paslauga p : imone.getPaslaugos()) {
            if (p.isArAktyvi() && p.arTinkaAugintiniui(augintinis)) {
                rezultatai.add(p);
            }
        }
        System.out.println("Rasta paslaugų augintiniui " + augintinis.getVardas() + ": " + rezultatai.size());
        return rezultatai;
    }

    public Rezervacija uzsakytiPaslauga(Augintinis augintinis, Paslauga paslauga, LocalDate data, LocalTime laikas) {
        if (!augintiniai.contains(augintinis)) {
            System.out.println("KLAIDA: " + augintinis.getVardas() + " nėra jūsų augintinis!");
            return null;
        }
        if (!paslauga.isArAktyvi()) {
            System.out.println("KLAIDA: paslauga " + paslauga.getPavadinimas() + " neaktyvi!");
            return null;
        }
        if (!paslauga.arTinkaAugintiniui(augintinis)) {
            System.out.println("KLAIDA: paslauga " + paslauga.getPavadinimas() + " netinka augintiniui " + augintinis.getVardas() + "!");
            return null;
        }
        Rezervacija rezervacija = new Rezervacija(data, laikas, this, augintinis, paslauga);
        paslauga.setPopuliarumas(paslauga.getPopuliarumas() + 1);
        System.out.println("Sukurta rezervacija: " + rezervacija.gautiInformacija());
        return rezervacija;
    }

    public void matytiSavoRezervacijas() {
        System.out.println("Savininko " + vardas + " rezervacijos (" + rezervacijos.size() + "):");
        for (Rezervacija r : rezervacijos) {
            System.out.println("  " + r.gautiInformacija());
        }
    }

    // Klientas pasirenka, ka vertina: priziuretoja ARBA imone
    public Atsiliepimas vertinti(Rezervacija rezervacija, String kaVertina, int ivertinimas, String komentaras) {
        if (rezervacija.getSavininkas() != this) {
            System.out.println("KLAIDA: tai ne jūsų rezervacija!");
            return null;
        }
        if (!rezervacija.getBusena().equals("Atlikta")) {
            System.out.println("KLAIDA: vertinti galima tik atlikta paslauga (dabar: " + rezervacija.getBusena() + ")!");
            return null;
        }
        if (!kaVertina.equals("Prižiūrėtojas") && !kaVertina.equals("Įmonė")) {
            System.out.println("KLAIDA: galima vertinti tik 'Prižiūrėtojas' arba 'Įmonė'!");
            return null;
        }
        if (ivertinimas < 1 || ivertinimas > 5) {
            System.out.println("KLAIDA: įvertinimas turi būti nuo 1 iki 5!");
            return null;
        }
        for (Atsiliepimas a : rezervacija.getAtsiliepimai()) {
            if (a.getSavininkas() == this) {
                System.out.println("KLAIDA: jūs jau įvertinote šią rezervaciją!");
                return null;
            }
        }
        Atsiliepimas atsiliepimas = new Atsiliepimas(ivertinimas, komentaras, rezervacija, kaVertina);
        atsiliepimas.setSavininkas(this);
        rezervacija.getAtsiliepimai().add(atsiliepimas);

        if (kaVertina.equals("Prižiūrėtojas")) {
            rezervacija.getPriziuretojas().perskaiciuotiIvertinima();
        } else {
            rezervacija.getPaslauga().getImone().perskaiciuotiIvertinima();
        }
        System.out.println("Paliktas atsiliepimas: " + atsiliepimas.gautiInformacija());
        return atsiliepimas;
    }

    public void perskaiciuotiIvertinima() {
        int suma = 0;
        int kiekis = 0;
        for (Rezervacija r : rezervacijos) {
            for (Atsiliepimas a : r.getAtsiliepimai()) {
                if (a.getTipas().equals("Klientas") && a.isArAktyvus()) {
                    suma += a.getIvertinimas();
                    kiekis++;
                }
            }
        }
        if (kiekis > 0) {
            vidutinisIvertinimas = Math.round((double) suma / kiekis * 100) / 100.0;
        } else {
            vidutinisIvertinimas = 0.0;
        }
    }

    public Komentaras rasytiKomentara(Priziuretojas priziuretojas, String tekstas) {
        Komentaras komentaras = new Komentaras(tekstas, priziuretojas, this);
        priziuretojas.getKomentarai().add(komentaras);
        System.out.println(vardas + " parašė komentarą " + priziuretojas.getVardas() + " profilyje: " + tekstas);
        return komentaras;
    }

    public Komentaras atsakytiIKomentara(Komentaras tevinis, String tekstas) {
        Komentaras atsakymas = new Komentaras(tekstas, tevinis.getPriziuretojas(), this);
        tevinis.pridetiAtsakyma(atsakymas);
        tevinis.getPriziuretojas().getKomentarai().add(atsakymas);
        System.out.println(vardas + " atsakė į komentarą: " + tekstas);
        return atsakymas;
    }

    // Getteriai ir Setteriai

    public double getVidutinisIvertinimas() {
        return vidutinisIvertinimas;
    }

    public void setVidutinisIvertinimas(double vidutinisIvertinimas) {
        this.vidutinisIvertinimas = vidutinisIvertinimas;
    }

    public List<Rezervacija> getRezervacijos() {
        return rezervacijos;
    }

    public void setRezervacijos(List<Rezervacija> rezervacijos) {
        this.rezervacijos = rezervacijos;
    }

    public List<Augintinis> getAugintiniai() {
        return augintiniai;
    }

    public void setAugintiniai(List<Augintinis> augintiniai) {
        this.augintiniai = augintiniai;
    }

    @Override
    public String getRole() {
        return "Savininkas";
    }
}
