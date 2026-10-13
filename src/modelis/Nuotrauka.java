package modelis;

import java.time.LocalDateTime;

public class Nuotrauka {
    private String url;
    private LocalDateTime ikeltaLaikas;
    private PaslaugosVykdymas vykdymas; // Rysys "1"

    public Nuotrauka(String url, PaslaugosVykdymas vykdymas) {
        this.url = url;
        this.vykdymas = vykdymas;
        this.ikeltaLaikas = LocalDateTime.now().withSecond(0).withNano(0);
    }

    public String gautiInformacija() {
        return url + " (įkelta " + ikeltaLaikas + ")";
    }

    // Getteriai ir Setteriai
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getIkeltaLaikas() {
        return ikeltaLaikas;
    }

    public PaslaugosVykdymas getVykdymas() {
        return vykdymas;
    }
}
