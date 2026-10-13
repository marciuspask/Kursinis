package modelis;

import java.time.LocalDateTime;

public class Incidentas {
    private String aprasymas;
    private LocalDateTime laikas;
    private PaslaugosVykdymas vykdymas; // Rysys "1"

    public Incidentas(String aprasymas, PaslaugosVykdymas vykdymas) {
        this.aprasymas = aprasymas;
        this.vykdymas = vykdymas;
        this.laikas = LocalDateTime.now().withSecond(0).withNano(0);
    }

    public String gautiInformacija() {
        return laikas + " - " + aprasymas;
    }

    // Getteriai ir Setteriai
    public String getAprasymas() {
        return aprasymas;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }

    public LocalDateTime getLaikas() {
        return laikas;
    }

    public PaslaugosVykdymas getVykdymas() {
        return vykdymas;
    }
}