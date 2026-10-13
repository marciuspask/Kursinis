package modelis;

import java.util.ArrayList;
import java.util.List;

public class Priziuretojas extends Vartotojas {
    private String aprasymas;
    private double vidutinisIvertinimas;
    private PaslauguImone imone;         // Rysys "1"
    private DarboGrafikas darboGrafikas; // Rysys "1"
    private List<Paslauga> paslaugos;    // Rysys "0..*" (daug su daug)
    private List<Rezervacija> rezervacijos; // Rysys "0..*"
    private List<Komentaras> komentarai;    // Rysys "0..*"

    public Priziuretojas(String vardas, String pavarde, String elPastas, String telefonas,
                         String slaptazodis, String aprasymas) {
        super(vardas, pavarde, elPastas, telefonas, slaptazodis);
        this.aprasymas = aprasymas;
        this.vidutinisIvertinimas = 0.0;
        this.paslaugos = new ArrayList<>();
        this.rezervacijos = new ArrayList<>();
        this.komentarai = new ArrayList<>();
    }

    public void nustatytiDarboGrafika(DarboGrafikas grafikas) {
        this.darboGrafikas = grafikas;
        grafikas.setPriziuretojas(this); // Abipusis rysys
        System.out.println("Prižiūrėtojui " + vardas + " nustatytas darbo grafikas: " + grafikas.gautiInformacija());
    }

    public void matytiPriskirtasRezervacijas() {
        System.out.println("Prižiūrėtojo " + vardas + " rezervacijos (" + rezervacijos.size() + "):");
        for (Rezervacija r : rezervacijos) {
            System.out.println("  " + r.gautiInformacija());
        }
    }

    public PaslaugosVykdymas vykdytiPaslauga(Rezervacija rezervacija) {
        if (rezervacija.getPriziuretojas() != this) {
            System.out.println("KLAIDA: " + vardas + ", ši rezervacija jums nepriskirta!");
            return null;
        }
        if (rezervacija.getPaslaugosVykdymas() != null) {
            System.out.println("KLAIDA: ši paslauga jau vykdoma arba įvykdyta!");
            return null;
        }
        if (!rezervacija.getBusena().equals("Patvirtinta") && !rezervacija.getBusena().equals("Vykdoma")) {
            System.out.println("KLAIDA: rezervacija turi būti patvirtinta (dabar: " + rezervacija.getBusena() + ")!");
            return null;
        }
        PaslaugosVykdymas vykdymas = new PaslaugosVykdymas(rezervacija);
        if (!rezervacija.getBusena().equals("Vykdoma")) {
            rezervacija.keistiBusena("Vykdoma");
        }
        System.out.println(vardas + " pradėjo vykdyti paslaugą: " + rezervacija.getPaslauga().getPavadinimas()
                + " (" + rezervacija.getAugintinis().getVardas() + ")");
        return vykdymas;
    }

    public Atsiliepimas vertintiKlienta(Rezervacija rezervacija, int ivertinimas, int gyvunoElgesys, String komentaras) {
        if (rezervacija.getPriziuretojas() != this) {
            System.out.println("KLAIDA: ši rezervacija jums nepriskirta!");
            return null;
        }
        if (!rezervacija.getBusena().equals("Atlikta")) {
            System.out.println("KLAIDA: vertinti klientą galima tik po atliktos paslaugos!");
            return null;
        }
        if (ivertinimas < 1 || ivertinimas > 5 || gyvunoElgesys < 1 || gyvunoElgesys > 5) {
            System.out.println("KLAIDA: įvertinimai turi būti nuo 1 iki 5!");
            return null;
        }
        for (Atsiliepimas a : rezervacija.getAtsiliepimai()) {
            if (a.getPriziuretojas() == this) {
                System.out.println("KLAIDA: jūs jau įvertinote šį klientą už šią rezervaciją!");
                return null;
            }
        }
        Atsiliepimas atsiliepimas = new Atsiliepimas(ivertinimas, komentaras, rezervacija, "Klientas");
        atsiliepimas.setPriziuretojas(this);
        atsiliepimas.setGyvunoElgesys(gyvunoElgesys);
        rezervacija.getAtsiliepimai().add(atsiliepimas);
        rezervacija.getSavininkas().perskaiciuotiIvertinima();
        System.out.println(vardas + " įvertino klientą " + rezervacija.getSavininkas().getVardas()
                + ": " + atsiliepimas.gautiInformacija());
        return atsiliepimas;
    }

    // Vidurkis skaiciuojamas tik is aktyviu savininku atsiliepimu
    public void perskaiciuotiIvertinima() {
        int suma = 0;
        int kiekis = 0;
        for (Rezervacija r : rezervacijos) {
            for (Atsiliepimas a : r.getAtsiliepimai()) {
                if (a.getTipas().equals("Prižiūrėtojas") && a.isArAktyvus()) {
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

    public Komentaras atsakytiIKomentara(Komentaras tevinis, String tekstas) {
        if (tevinis.getPriziuretojas() != this) {
            System.out.println("KLAIDA: galite atsakyti tik į komentarus savo profilyje!");
            return null;
        }
        Komentaras atsakymas = new Komentaras(tekstas, this, null); // autorius null = rase priziuretojas
        tevinis.pridetiAtsakyma(atsakymas);
        komentarai.add(atsakymas);
        System.out.println(vardas + " atsakė į komentarą: " + tekstas);
        return atsakymas;
    }

    public void rodytiKomentarus() {
        System.out.println("Komentarai " + vardas + " profilyje:");
        for (Komentaras k : komentarai) {
            if (k.getTevinisKomentaras() == null) { // Spausdiname tik pagrindinius, atsakymus parodo rekursija
                k.spausdinti(0);
            }
        }
    }

    // Getteriai ir Setteriai

    public String getAprasymas() {
        return aprasymas;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }

    public double getVidutinisIvertinimas() {
        return vidutinisIvertinimas;
    }

    public void setVidutinisIvertinimas(double vidutinisIvertinimas) {
        this.vidutinisIvertinimas = vidutinisIvertinimas;
    }

    public PaslauguImone getImone() {
        return imone;
    }

    public void setImone(PaslauguImone imone) {
        this.imone = imone;
    }

    public DarboGrafikas getDarboGrafikas() {
        return darboGrafikas;
    }

    public void setDarboGrafikas(DarboGrafikas darboGrafikas) {
        this.darboGrafikas = darboGrafikas;
    }

    public List<Paslauga> getPaslaugos() {
        return paslaugos;
    }

    public void setPaslaugos(List<Paslauga> paslaugos) {
        this.paslaugos = paslaugos;
    }

    public List<Rezervacija> getRezervacijos() {
        return rezervacijos;
    }

    public void setRezervacijos(List<Rezervacija> rezervacijos) {
        this.rezervacijos = rezervacijos;
    }

    public List<Komentaras> getKomentarai() {
        return komentarai;
    }

    public void setKomentarai(List<Komentaras> komentarai) {
        this.komentarai = komentarai;
    }

    @Override
    public String getRole() {
        return "Prižiūrėtojas";
    }
}
