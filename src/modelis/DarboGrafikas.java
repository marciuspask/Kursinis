package modelis;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DarboGrafikas {
    private String darboDienos;
    private String darboValandos;
    private String pertraukos;
    private String atostogos;
    private String neteikimoLaikas;
    private int maxGyvunuVienuMetu;
    private Priziuretojas priziuretojas; // Rysys "1" (grafikas priklauso vienam priziuretojui)

    public DarboGrafikas(String darboDienos, String darboValandos, String pertraukos, int maxGyvunuVienuMetu) {
        this.darboDienos = darboDienos;
        this.darboValandos = darboValandos;
        this.pertraukos = pertraukos;
        this.maxGyvunuVienuMetu = maxGyvunuVienuMetu;
        this.atostogos = "Nėra";
        this.neteikimoLaikas = "Nėra";
    }

    public String gautiInformacija() {
        return "Darbo dienos: " + darboDienos + " | Valandos: " + darboValandos
                + " | Pertraukos: " + pertraukos + " | Max gyvūnų: " + maxGyvunuVienuMetu;
    }
    // Grazina laisvus laikus (kas valanda) nurodyta diena
    public List<LocalTime> rodytiLaisvusLaikus(LocalDate data) {
        List<LocalTime> laisviLaikai = new ArrayList<>();
        String[] dalys = darboValandos.split("-"); // "08:00-16:00" -> ["08:00", "16:00"]
        LocalTime pradzia = LocalTime.parse(dalys[0]);
        LocalTime pabaiga = LocalTime.parse(dalys[1]);

        LocalTime laikas = pradzia;
        while (laikas.isBefore(pabaiga)) {
            int uzimta = 0;
            if (priziuretojas != null) {
                for (Rezervacija r : priziuretojas.getRezervacijos()) {
                    if (r.getData().equals(data) && r.getLaikas().equals(laikas)
                            && !r.getBusena().equals("Atšaukta")) {
                        uzimta++;
                    }
                }
            }
            if (uzimta < maxGyvunuVienuMetu) {
                laisviLaikai.add(laikas);
            }
            laikas = laikas.plusHours(1);
        }
        return laisviLaikai;
    }

    // Getteriai ir Setteriai
    public String getDarboDienos() {
        return darboDienos;
    }

    public void setDarboDienos(String darboDienos) {
        this.darboDienos = darboDienos;
    }

    public String getDarboValandos() {
        return darboValandos;
    }

    public void setDarboValandos(String darboValandos) {
        this.darboValandos = darboValandos;
    }

    public String getPertraukos() {
        return pertraukos;
    }

    public void setPertraukos(String pertraukos) {
        this.pertraukos = pertraukos;
    }

    public String getAtostogos() {
        return atostogos;
    }

    public void setAtostogos(String atostogos) {
        this.atostogos = atostogos;
    }

    public String getNeteikimoLaikas() {
        return neteikimoLaikas;
    }

    public void setNeteikimoLaikas(String neteikimoLaikas) {
        this.neteikimoLaikas = neteikimoLaikas;
    }

    public int getMaxGyvunuVienuMetu() {
        return maxGyvunuVienuMetu;
    }

    public void setMaxGyvunuVienuMetu(int maxGyvunuVienuMetu) {
        this.maxGyvunuVienuMetu = maxGyvunuVienuMetu;
    }

    public Priziuretojas getPriziuretojas() {
        return priziuretojas;
    }

    public void setPriziuretojas(Priziuretojas priziuretojas) {
        this.priziuretojas = priziuretojas;
    }
}