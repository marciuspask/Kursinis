package modelis;

import java.time.LocalDateTime;

public class Zinute {
    private String tekstas;
    private LocalDateTime issiuntimoLaikas;
    private LocalDateTime perskaitymoLaikas;
    private boolean arRedaguota;
    private Rezervacija rezervacija;     // Rysys "1"
    private Savininkas savininkas;       // Rysys "0..1" (siuntejas)
    private Priziuretojas priziuretojas; // Rysys "0..1" (siuntejas)
    private Vadovas vadovas;             // Rysys "0..1" (siuntejas)

    public Zinute(String tekstas, Rezervacija rezervacija) {
        this.tekstas = tekstas;
        this.rezervacija = rezervacija;
        this.issiuntimoLaikas = LocalDateTime.now().withSecond(0).withNano(0);
        this.perskaitymoLaikas = null; // Dar neperskaityta
        this.arRedaguota = false;
    }

    // Is triju siuntejo lauku uzpildytas tik vienas
    public String gautiSiuntejoVarda() {
        if (savininkas != null) {
            return savininkas.getVardas() + " (savininkas)";
        }
        if (priziuretojas != null) {
            return priziuretojas.getVardas() + " (prižiūrėtojas)";
        }
        if (vadovas != null) {
            return vadovas.getVardas() + " (vadovas)";
        }
        return "Nežinomas";
    }

    // Perskaityti gali tik rezervacijos dalyvis ir ne pats siuntejas
    public void perskaityti(Savininkas skaitytojas) {
        if (skaitytojas != rezervacija.getSavininkas()) {
            System.out.println("KLAIDA: " + skaitytojas.getVardas() + " nėra šios rezervacijos dalyvis!");
            return;
        }
        if (skaitytojas == savininkas) {
            System.out.println("KLAIDA: savo žinutes pažymėti perskaityta negalima!");
            return;
        }
        pazymetiPerskaityta(skaitytojas.getVardas());
    }

    public void perskaityti(Priziuretojas skaitytojas) {
        if (skaitytojas != rezervacija.getPriziuretojas()) {
            System.out.println("KLAIDA: " + skaitytojas.getVardas() + " nėra šios rezervacijos dalyvis!");
            return;
        }
        if (skaitytojas == priziuretojas) {
            System.out.println("KLAIDA: savo žinutes pažymėti perskaityta negalima!");
            return;
        }
        pazymetiPerskaityta(skaitytojas.getVardas());
    }

    public void perskaityti(Vadovas skaitytojas) {
        if (skaitytojas.getImone() != rezervacija.getPaslauga().getImone()) {
            System.out.println("KLAIDA: " + skaitytojas.getVardas() + " nėra šios rezervacijos dalyvis!");
            return;
        }
        if (skaitytojas == vadovas) {
            System.out.println("KLAIDA: savo žinutes pažymėti perskaityta negalima!");
            return;
        }
        pazymetiPerskaityta(skaitytojas.getVardas());
    }

    private void pazymetiPerskaityta(String skaitytojoVardas) {
        if (perskaitymoLaikas != null) {
            System.out.println("Žinutė jau perskaityta.");
            return;
        }
        perskaitymoLaikas = LocalDateTime.now().withSecond(0).withNano(0);
        System.out.println(skaitytojoVardas + " perskaitė žinutę nuo " + gautiSiuntejoVarda());
    }

    public void redaguoti(String naujasTekstas) {
        if (perskaitymoLaikas != null) {
            System.out.println("KLAIDA: žinutė jau perskaityta, redaguoti negalima!");
            return;
        }
        this.tekstas = naujasTekstas;
        this.arRedaguota = true;
        System.out.println("Žinutė paredaguota: " + naujasTekstas);
    }

    public String gautiInformacija() {
        String zyma = (perskaitymoLaikas == null) ? "*** NAUJA *** " : "";
        return zyma + "[" + issiuntimoLaikas + "] " + gautiSiuntejoVarda() + ": " + tekstas
                + (arRedaguota ? " (redaguota)" : "")
                + (perskaitymoLaikas != null ? " (perskaityta " + perskaitymoLaikas + ")" : "");
    }

    // Getteriai ir Setteriai
    public String getTekstas() {
        return tekstas;
    }

    public LocalDateTime getIssiuntimoLaikas() {
        return issiuntimoLaikas;
    }

    public LocalDateTime getPerskaitymoLaikas() {
        return perskaitymoLaikas;
    }

    public boolean isArRedaguota() {
        return arRedaguota;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public Savininkas getSavininkas() {
        return savininkas;
    }

    public void setSavininkas(Savininkas savininkas) {
        this.savininkas = savininkas;
    }

    public Priziuretojas getPriziuretojas() {
        return priziuretojas;
    }

    public void setPriziuretojas(Priziuretojas priziuretojas) {
        this.priziuretojas = priziuretojas;
    }

    public Vadovas getVadovas() {
        return vadovas;
    }

    public void setVadovas(Vadovas vadovas) {
        this.vadovas = vadovas;
    }
}