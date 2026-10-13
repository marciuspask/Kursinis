package modelis;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class Rezervacija {
    private LocalDate data;
    private LocalTime laikas;
    private String busena;
    private Savininkas savininkas;       // Rysys "1"
    private Augintinis augintinis;       // Rysys "1"
    private Paslauga paslauga;           // Rysys "1"
    private Priziuretojas priziuretojas; // Rysys "0..1" (gali buti dar nepriskirtas)
    private PaslaugosVykdymas paslaugosVykdymas; // Rysys "0..1"
    private List<Zinute> zinutes;             // Rysys "0..*"
    private List<Atsiliepimas> atsiliepimai;  // Rysys "0..*"

    private static final List<String> GALIMOS_BUSENOS =
            List.of("Laukiama", "Patvirtinta", "Vykdoma", "Atlikta", "Atšaukta");

    public Rezervacija(LocalDate data, LocalTime laikas, Savininkas savininkas,
                       Augintinis augintinis, Paslauga paslauga) {
        this.data = data;
        this.laikas = laikas;
        this.savininkas = savininkas;
        this.augintinis = augintinis;
        this.paslauga = paslauga;
        this.busena = "Laukiama"; // Is pradziu laukia patvirtinimo
        this.priziuretojas = null;
        this.zinutes = new ArrayList<>();
        this.atsiliepimai = new ArrayList<>();

        // Pridedame rezervacija i visu susijusiu objektu sarasus
        savininkas.getRezervacijos().add(this);
        augintinis.getRezervacijos().add(this);
        paslauga.getRezervacijos().add(this);
    }

    public void keistiBusena(String naujaBusena) {
        if (!GALIMOS_BUSENOS.contains(naujaBusena)) {
            System.out.println("KLAIDA: būsena '" + naujaBusena + "' neegzistuoja! Galimos: " + GALIMOS_BUSENOS);
            return;
        }
        String senaBusena = this.busena;
        this.busena = naujaBusena;
        System.out.println("Rezervacijos (" + augintinis.getVardas() + ", " + data + " " + laikas
                + ") būsena: " + senaBusena + " -> " + naujaBusena);
    }

    public void priskirtiPriziuretoja(Priziuretojas p) {
        if (!paslauga.getPriziuretojai().contains(p)) {
            System.out.println("KLAIDA: " + p.getVardas() + " neteikia paslaugos " + paslauga.getPavadinimas() + "!");
            return;
        }
        if (p.getDarboGrafikas() == null) {
            System.out.println("KLAIDA: " + p.getVardas() + " neturi darbo grafiko!");
            return;
        }
        if (!p.getDarboGrafikas().rodytiLaisvusLaikus(data).contains(laikas)) {
            System.out.println("KLAIDA: " + p.getVardas() + " užimtas arba nedirba " + data + " " + laikas + "!");
            return;
        }
        this.priziuretojas = p;
        p.getRezervacijos().add(this); // Abipusis rysys
        System.out.println("Rezervacijai priskirtas prižiūrėtojas " + p.getVardas());
        keistiBusena("Patvirtinta");
    }

    // Metodu perkrovimas (overloading): tas pats pavadinimas, skirtingi parametrai
    public Zinute siustiZinute(Savininkas siuntejas, String tekstas) {
        if (siuntejas != this.savininkas) {
            System.out.println("KLAIDA: " + siuntejas.getVardas() + " nėra šios rezervacijos savininkas!");
            return null;
        }
        Zinute zinute = new Zinute(tekstas, this);
        zinute.setSavininkas(siuntejas);
        zinutes.add(zinute);
        System.out.println("Išsiųsta žinutė: " + zinute.gautiInformacija());
        return zinute;
    }

    public Zinute siustiZinute(Priziuretojas siuntejas, String tekstas) {
        if (siuntejas != this.priziuretojas) {
            System.out.println("KLAIDA: " + siuntejas.getVardas() + " nepriskirtas šiai rezervacijai!");
            return null;
        }
        Zinute zinute = new Zinute(tekstas, this);
        zinute.setPriziuretojas(siuntejas);
        zinutes.add(zinute);
        System.out.println("Išsiųsta žinutė: " + zinute.gautiInformacija());
        return zinute;
    }

    public Zinute siustiZinute(Vadovas siuntejas, String tekstas) {
        if (siuntejas.getImone() != paslauga.getImone()) {
            System.out.println("KLAIDA: " + siuntejas.getVardas() + " nėra šios įmonės vadovas!");
            return null;
        }
        Zinute zinute = new Zinute(tekstas, this);
        zinute.setVadovas(siuntejas);
        zinutes.add(zinute);
        System.out.println("Išsiųsta žinutė: " + zinute.gautiInformacija());
        return zinute;
    }

    public void rodytiZinutes() {
        int neperskaitytos = 0;
        for (Zinute z : zinutes) {
            if (z.getPerskaitymoLaikas() == null) {
                neperskaitytos++;
            }
        }
        System.out.println("Rezervacijos (" + augintinis.getVardas() + ", " + data + ") žinutes ("
                + zinutes.size() + ", neperskaitytų: " + neperskaitytos + "):");
        for (Zinute z : zinutes) {
            System.out.println("  " + z.gautiInformacija());
        }
    }

    public String gautiInformacija() {
        String priziuretojoVardas = (priziuretojas != null) ? priziuretojas.getVardas() : "nepriskirtas";
        return data + " " + laikas + " | " + paslauga.getPavadinimas() + " | Augintinis: " + augintinis.getVardas()
                + " | Prižiūrėtojas: " + priziuretojoVardas + " | Būsena: " + busena;
    }

    // Getteriai ir Setteriai
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getLaikas() {
        return laikas;
    }

    public void setLaikas(LocalTime laikas) {
        this.laikas = laikas;
    }

    public String getBusena() {
        return busena;
    }

    public Savininkas getSavininkas() {
        return savininkas;
    }

    public Augintinis getAugintinis() {
        return augintinis;
    }

    public Paslauga getPaslauga() {
        return paslauga;
    }

    public Priziuretojas getPriziuretojas() {
        return priziuretojas;
    }

    public PaslaugosVykdymas getPaslaugosVykdymas() {
        return paslaugosVykdymas;
    }

    public void setPaslaugosVykdymas(PaslaugosVykdymas paslaugosVykdymas) {
        this.paslaugosVykdymas = paslaugosVykdymas;
    }

    public List<Zinute> getZinutes() {
        return zinutes;
    }

    public List<Atsiliepimas> getAtsiliepimai() {
        return atsiliepimai;
    }
}
