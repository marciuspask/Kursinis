package valdikliai;

import duomenys.DuomenuBaze;
import modelis.Paslauga;
import modelis.PaslauguImone;
import duomenys.Sesija;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

// "Pradzios" skyrius: pasisveikinimas ir suvestines korteles su skaiciais
public class PradziaController {

    @FXML private Label sveikinimasLabel;
    @FXML private FlowPane kortelesPane;

    @FXML
    public void initialize() {
        sveikinimasLabel.setText("Sveiki, " + Sesija.getVartotojas().getVardas() + "!");

        if (Sesija.arAdministratorius()) {
            int vartotoju = DuomenuBaze.getAdministratoriai().size() + DuomenuBaze.getVadovai().size()
                    + DuomenuBaze.getPriziuretojai().size() + DuomenuBaze.getSavininkai().size();
            int paslaugu = 0;
            int rezervaciju = 0;
            for (PaslauguImone imone : DuomenuBaze.getImones()) {
                paslaugu += imone.getPaslaugos().size();
                for (Paslauga p : imone.getPaslaugos()) {
                    rezervaciju += p.getRezervacijos().size();
                }
            }
            pridetiKortele("Įmonės", DuomenuBaze.getImones().size());
            pridetiKortele("Vartotojai", vartotoju);
            pridetiKortele("Paslaugos", paslaugu);
            pridetiKortele("Augintiniai", DuomenuBaze.getAugintiniai().size());
            pridetiKortele("Rezervacijos", rezervaciju);
        } else {
            // Vadovas mato tik savo imones skaicius
            PaslauguImone imone = Sesija.getVadovas().getImone();
            int rezervaciju = 0;
            for (Paslauga p : imone.getPaslaugos()) {
                rezervaciju += p.getRezervacijos().size();
            }
            pridetiKortele("Prižiūrėtojai", imone.getPriziuretojai().size());
            pridetiKortele("Paslaugos", imone.getPaslaugos().size());
            pridetiKortele("Rezervacijos", rezervaciju);
            pridetiKortele("Įvertinimas", imone.getVidutinisIvertinimas());
        }
    }

    // Sukuria viena kortele: didelis skaicius + pavadinimas po juo
    private void pridetiKortele(String pavadinimas, Object reiksme) {
        Label skaicius = new Label(String.valueOf(reiksme));
        skaicius.getStyleClass().add("kortele-skaicius");

        Label tekstas = new Label(pavadinimas);
        tekstas.getStyleClass().add("kortele-pavadinimas");

        VBox kortele = new VBox(4, skaicius, tekstas);
        kortele.getStyleClass().add("kortele");
        kortelesPane.getChildren().add(kortele);
    }
}
