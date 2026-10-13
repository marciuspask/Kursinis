package modelis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaslaugosVykdymas {
    private LocalDateTime pradziosLaikas;
    private LocalDateTime pabaigosLaikas;
    private String aprasymas;
    private double nueitasAtstumas;
    private boolean arPamaitintas;
    private Rezervacija rezervacija;          // Rysys "1"
    private List<Nuotrauka> nuotraukos;       // Rysys "0..*"
    private List<Incidentas> incidentai;      // Rysys "0..*"

    public PaslaugosVykdymas(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
        this.pradziosLaikas = LocalDateTime.of(rezervacija.getData(), rezervacija.getLaikas());
        this.pabaigosLaikas = null; // Dar nebaigta
        this.aprasymas = "";
        this.nueitasAtstumas = 0.0;
        this.arPamaitintas = false;
        this.nuotraukos = new ArrayList<>();
        this.incidentai = new ArrayList<>();

        rezervacija.setPaslaugosVykdymas(this); // Abipusis rysys
    }

    public boolean arUzbaigta() {
        return pabaigosLaikas != null;
    }

    public void pridetiNuotrauka(String url) {
        if (arUzbaigta()) {
            System.out.println("KLAIDA: vykdymas jau užbaigtas, nuotraukos kelti negalima!");
            return;
        }
        Nuotrauka nuotrauka = new Nuotrauka(url, this);
        nuotraukos.add(nuotrauka);
        System.out.println("Įkelta nuotrauka: " + url);
    }

    public void pridetiIncidenta(String aprasymas) {
        if (arUzbaigta()) {
            System.out.println("KLAIDA: vykdymas jau užbaigtas, incidento pridėti negalima!");
            return;
        }
        Incidentas incidentas = new Incidentas(aprasymas, this);
        incidentai.add(incidentas);
        System.out.println("!!! UŽFIKSUOTAS INCIDENTAS: " + aprasymas);
    }

    public void uzbaigti(String aprasymas, double nueitasAtstumas, boolean arPamaitintas) {
        if (arUzbaigta()) {
            System.out.println("KLAIDA: vykdymas jau užbaigtas!");
            return;
        }
        this.aprasymas = aprasymas;
        this.nueitasAtstumas = nueitasAtstumas;
        this.arPamaitintas = arPamaitintas;
        this.pabaigosLaikas = pradziosLaikas.plusMinutes(rezervacija.getPaslauga().getTrukmeMin());
        rezervacija.keistiBusena("Atlikta");
        System.out.println("Paslaugos vykdymas užbaigtas.");
    }

    public String gautiAtaskaita() {
        String ataskaita = "=== VYKDYMO ATASKAITA ===\n"
                + "Paslauga: " + rezervacija.getPaslauga().getPavadinimas() + "\n"
                + "Augintinis: " + rezervacija.getAugintinis().getVardas() + "\n"
                + "Prižiūrėtojas: " + rezervacija.getPriziuretojas().getVardas() + "\n"
                + "Pradžia: " + pradziosLaikas + " | Pabaiga: " + (arUzbaigta() ? pabaigosLaikas : "vyksta") + "\n"
                + "Aprašymas: " + aprasymas + "\n"
                + "Nueitas atstumas: " + nueitasAtstumas + " km | Pamaitintas: " + (arPamaitintas ? "Taip" : "Ne") + "\n"
                + "Nuotraukos (" + nuotraukos.size() + "):\n";
        for (Nuotrauka n : nuotraukos) {
            ataskaita += "  " + n.gautiInformacija() + "\n";
        }
        ataskaita += "Incidentai (" + incidentai.size() + "):\n";
        for (Incidentas i : incidentai) {
            ataskaita += "  " + i.gautiInformacija() + "\n";
        }
        return ataskaita;
    }

    // Getteriai ir Setteriai
    public LocalDateTime getPradziosLaikas() {
        return pradziosLaikas;
    }

    public LocalDateTime getPabaigosLaikas() {
        return pabaigosLaikas;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }

    public double getNueitasAtstumas() {
        return nueitasAtstumas;
    }

    public void setNueitasAtstumas(double nueitasAtstumas) {
        this.nueitasAtstumas = nueitasAtstumas;
    }

    public boolean isArPamaitintas() {
        return arPamaitintas;
    }

    public void setArPamaitintas(boolean arPamaitintas) {
        this.arPamaitintas = arPamaitintas;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public List<Nuotrauka> getNuotraukos() {
        return nuotraukos;
    }

    public List<Incidentas> getIncidentai() {
        return incidentai;
    }
}
