package modelis;

import java.util.ArrayList;
import java.util.List;

public class PaslauguImone {
    private String pavadinimas;
    private String adresas;
    private String miestas;
    private double vidutinisIvertinimas;
    private List<Vadovas> vadovai;             // Rysys "1..*"
    private List<Priziuretojas> priziuretojai; // Rysys "1..*"
    private List<Paslauga> paslaugos;          // Rysys "1..*"

    public PaslauguImone(String pavadinimas, String adresas, String miestas) {
        this.pavadinimas = pavadinimas;
        this.adresas = adresas;
        this.miestas = miestas;
        this.vidutinisIvertinimas = 0.0;
        this.vadovai = new ArrayList<>();
        this.priziuretojai = new ArrayList<>();
        this.paslaugos = new ArrayList<>();
    }

    public void pridetiVadova(Vadovas vadovas) {
        this.vadovai.add(vadovas);
        vadovas.setImone(this); // Abipusis rysys
        System.out.println("Vadovas " + vadovas.getVardas() + " " + vadovas.getPavarde() + " priskirtas įmonei " + pavadinimas);
    }

    public void pridetiPriziuretoja(Priziuretojas priziuretojas) {
        this.priziuretojai.add(priziuretojas);
        priziuretojas.setImone(this); // Abipusis rysys
        System.out.println("Prižiūrėtojas " + priziuretojas.getVardas() + " " + priziuretojas.getPavarde() + " įdarbintas įmonėje " + pavadinimas);
    }

    public void pridetiPaslauga(Paslauga paslauga) {
        this.paslaugos.add(paslauga);
        paslauga.setImone(this); // Abipusis rysys
    }

    // Imones vidurkis is visu aktyviu savininku atsiliepimu
    public void perskaiciuotiIvertinima() {
        int suma = 0;
        int kiekis = 0;
        for (Paslauga p : paslaugos) {
            for (Rezervacija r : p.getRezervacijos()) {
                for (Atsiliepimas a : r.getAtsiliepimai()) {
                    if (a.getTipas().equals("Įmonė") && a.isArAktyvus()) {
                        suma += a.getIvertinimas();
                        kiekis++;
                    }
                }
            }
        }
        if (kiekis > 0) {
            vidutinisIvertinimas = Math.round((double) suma / kiekis * 100) / 100.0;
        } else {
            vidutinisIvertinimas = 0.0;
        }
    }

    public String gautiInformacija() {
        return "Įmonė: " + pavadinimas + " | Adresas: " + adresas + ", " + miestas
                + " | Įvertinimas: " + vidutinisIvertinimas;
    }

    // Getteriai ir Setteriai

    public List<Paslauga> getPaslaugos() {
        return paslaugos;
    }

    public void setPaslaugos(List<Paslauga> paslaugos) {
        this.paslaugos = paslaugos;
    }

    public List<Priziuretojas> getPriziuretojai() {
        return priziuretojai;
    }

    public void setPriziuretojai(List<Priziuretojas> priziuretojai) {
        this.priziuretojai = priziuretojai;
    }

    public List<Vadovas> getVadovai() {
        return vadovai;
    }

    public void setVadovai(List<Vadovas> vadovai) {
        this.vadovai = vadovai;
    }

    public double getVidutinisIvertinimas() {
        return vidutinisIvertinimas;
    }

    public void setVidutinisIvertinimas(double vidutinisIvertinimas) {
        this.vidutinisIvertinimas = vidutinisIvertinimas;
    }

    public String getMiestas() {
        return miestas;
    }

    public void setMiestas(String miestas) {
        this.miestas = miestas;
    }

    public String getAdresas() {
        return adresas;
    }

    public void setAdresas(String adresas) {
        this.adresas = adresas;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    @Override
    public String toString() {
        return pavadinimas + " (" + miestas + ")";
    }
}
