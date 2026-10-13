package valdikliai;

import duomenys.Sesija;
import modelis.Vartotojas;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

// Pagrindinio lango valdiklis: soninis meniu, meniu juosta, busenos juosta.
// Pats turinys (skyriai) ikeliamas i centra is atskiru fxml failu.
public class PagrindinisController {

    @FXML private StackPane turinys;
    @FXML private Label busenosLabel;

    @FXML private Button pradziaBtn;
    @FXML private Button vartotojaiBtn;
    @FXML private Button imonesBtn;
    @FXML private Button paslaugosBtn;
    @FXML private Button augintiniaiBtn;
    @FXML private Button rezervacijosBtn;
    @FXML private Button komentaraiBtn;

    @FXML
    public void initialize() {
        Vartotojas v = Sesija.getVartotojas();
        String tekstas = "Prisijungęs: " + v + " (" + v.getRole();
        if (!Sesija.arAdministratorius()) {
            tekstas += ", " + Sesija.getVadovas().getImone().getPavadinimas();
            // Vadovui nereikia visu imoniu ir visu augintiniu
            paslepti(imonesBtn);
            paslepti(augintiniaiBtn);
        }
        busenosLabel.setText(tekstas + ")");

        rodytiPradzia(); // Pradzioje rodome suvestine
    }

    // ===== Skyriu perjungimas =====

    @FXML
    public void rodytiPradzia() {
        ikeltiSkyriu("/pradzia-view.fxml", pradziaBtn);
    }

    @FXML
    public void rodytiVartotojus() {
        ikeltiSkyriu("/vartotojai-view.fxml", vartotojaiBtn);
    }

    @FXML
    public void rodytiImones() {
        rodytiKuriama("Įmonės", imonesBtn);
    }

    @FXML
    public void rodytiPaslaugas() {
        rodytiKuriama("Paslaugos", paslaugosBtn);
    }

    @FXML
    public void rodytiAugintinius() {
        rodytiKuriama("Augintiniai", augintiniaiBtn);
    }

    @FXML
    public void rodytiRezervacijas() {
        rodytiKuriama("Rezervacijos", rezervacijosBtn);
    }

    @FXML
    public void rodytiKomentarus() {
        rodytiKuriama("Komentarai", komentaraiBtn);
    }

    // Ikelia skyriaus fxml ir padeda ji i lango centra
    private void ikeltiSkyriu(String fxmlFailas, Button mygtukas) {
        try {
            Parent skyrius = FXMLLoader.load(getClass().getResource(fxmlFailas));
            turinys.getChildren().setAll(skyrius);
            pazymetiAktyvu(mygtukas);
        } catch (Exception e) {
            e.printStackTrace();
            rodytiKlaida("Nepavyko atidaryti skyriaus: " + e.getMessage());
        }
    }

    // Laikinas vaizdas skyriams, kuriu dar nepadareme
    private void rodytiKuriama(String pavadinimas, Button mygtukas) {
        Label label = new Label(pavadinimas + " – skyrius kuriamas");
        label.getStyleClass().add("antraste");
        turinys.getChildren().setAll(label);
        pazymetiAktyvu(mygtukas);
    }

    // Paryskina paspausta soninio meniu mygtuka
    private void pazymetiAktyvu(Button aktyvus) {
        for (Button b : List.of(pradziaBtn, vartotojaiBtn, imonesBtn, paslaugosBtn,
                augintiniaiBtn, rezervacijosBtn, komentaraiBtn)) {
            b.getStyleClass().remove("aktyvus");
        }
        aktyvus.getStyleClass().add("aktyvus");
    }

    private void paslepti(Button mygtukas) {
        mygtukas.setVisible(false);
        mygtukas.setManaged(false);
    }

    // ===== Meniu juostos veiksmai =====

    @FXML
    public void atsijungti() {
        Sesija.atsijungti();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
            Stage langas = (Stage) turinys.getScene().getWindow();
            langas.setScene(new Scene(loader.load(), 400, 300));
            langas.sizeToScene();      // pritaikome lango dydi naujam turiniui
            langas.centerOnScreen();
        } catch (Exception e) {
            rodytiKlaida("Nepavyko atsijungti: " + e.getMessage());
        }
    }

    @FXML
    public void iseiti() {
        Platform.exit();
    }

    @FXML
    public void apie() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Apie programą");
        alert.setHeaderText("PetCare – augintinių priežiūros sistema");
        alert.setContentText("Kursinis darbas, Programavimo technologijos\nAutorius: Martynas Paškevičius, PRIf-24/1");
        alert.showAndWait();
    }

    private void rodytiKlaida(String tekstas) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Klaida");
        alert.setHeaderText(null);
        alert.setContentText(tekstas);
        alert.showAndWait();
    }
}
