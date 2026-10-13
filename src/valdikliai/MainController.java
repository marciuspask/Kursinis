package valdikliai;

import modelis.Augintinis;
import duomenys.DuomenuBaze;
import modelis.PaslauguImone;
import modelis.Priziuretojas;
import modelis.Savininkas;
import duomenys.Sesija;
import modelis.Vadovas;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Optional;

public class MainController {

    @FXML private Label vartotojoLabel;
    @FXML private Button imonesBtn;
    @FXML private Button vadovoBtn;
    @FXML private Button savininkoBtn;
    @FXML private Button augintinioBtn;
    @FXML private Button priziuretojoBtn;

    // initialize() JavaFX iskviecia AUTOMATISKAI, kai fxml uzkrautas
    // (laukai jau uzpildyti). Cia nustatome, ka mato prisijunges vartotojas.
    @FXML
    public void initialize() {
        if (Sesija.arAdministratorius()) {
            vartotojoLabel.setText("Prisijungęs: " + Sesija.getAdministratorius().getVardas() + " (administratorius)");
        } else {
            Vadovas v = Sesija.getVadovas();
            vartotojoLabel.setText("Prisijungęs: " + v.getVardas() + " (" + v.getImone().getPavadinimas() + " vadovas)");

            // Vadovui slepiame administratoriaus mygtukus
            paslepti(imonesBtn);
            paslepti(vadovoBtn);
            paslepti(savininkoBtn);
            paslepti(augintinioBtn);
        }
    }

    private void paslepti(Button mygtukas) {
        mygtukas.setVisible(false);   // nematomas
        mygtukas.setManaged(false);   // neuzima vietos (kitaip liktu tuscias tarpas)
    }

    // 1. IMONES REGISTRACIJA
    @FXML
    public void atidarytiImonesReg() {
        // 1) Sukuriame iskylanti langa, kuris grazins PaslauguImone objekta
        Dialog<PaslauguImone> dialog = new Dialog<>();
        dialog.setTitle("Imones registracija");

        // 2) Mygtukai apacioje: "Issaugoti" ir "Cancel"
        ButtonType issaugotiMygtukas = new ButtonType("Issaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(issaugotiMygtukas, ButtonType.CANCEL);

        // 3) Laukeliai, sudeti i lentele (stulpelis, eilute)
        TextField pavadinimasField = new TextField();
        pavadinimasField.setPromptText("Pavadinimas");
        TextField adresasField = new TextField();
        adresasField.setPromptText("Adresas");
        TextField miestasField = new TextField();
        miestasField.setPromptText("Miestas");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Pavadinimas:"), 0, 0);
        grid.add(pavadinimasField, 1, 0);
        grid.add(new Label("Adresas:"), 0, 1);
        grid.add(adresasField, 1, 1);
        grid.add(new Label("Miestas:"), 0, 2);
        grid.add(miestasField, 1, 2);
        dialog.getDialogPane().setContent(grid);

        // 4) Kas ivyksta paspaudus mygtuka: tikriname ivesti ir kuriame objekta
        dialog.setResultConverter(paspaustas -> {
            if (paspaustas == issaugotiMygtukas) {
                if (pavadinimasField.getText().isBlank() || miestasField.getText().isBlank()) {
                    rodytiKlaida("Pavadinimas ir miestas privalomi!");
                    return null;
                }
                return new PaslauguImone(pavadinimasField.getText(), adresasField.getText(), miestasField.getText());
            }
            return null; // Paspaustas Cancel
        });

        // 5) Parodome langa, laukiame, ir jei objektas sukurtas, issaugome
        Optional<PaslauguImone> rezultatas = dialog.showAndWait();
        rezultatas.ifPresent(imone -> {
            DuomenuBaze.getImones().add(imone);
            rodytiPranesima("Imone " + imone.getPavadinimas() + " uzregistruota! Is viso imoniu: "
                    + DuomenuBaze.getImones().size());
        });
    }

    // 2. SAVININKO REGISTRACIJA
    @FXML
    public void atidarytiSavininkoReg() {
        // 1) Sukuriame iskylanti langa, kuris grazins Savininko objekta
        Dialog<Savininkas> dialog = new Dialog<>();
        dialog.setTitle("Savininko registracija");

        // 2) Mygtukai apacioje: "Issaugoti" ir "Cancel"
        ButtonType issaugotiMygtukas = new ButtonType("Issaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(issaugotiMygtukas, ButtonType.CANCEL);

        // 3) Laukeliai, sudeti i lentele (stulpelis, eilute)
        TextField vardasField = new TextField();
        vardasField.setPromptText("Vardas");
        TextField pavardeField = new TextField();
        pavardeField.setPromptText("Pavardė");
        TextField elpastasField = new TextField();
        elpastasField.setPromptText("...@...");
        TextField telefonasField = new TextField();
        telefonasField.setPromptText("+370...");
        PasswordField slaptazodisField = new PasswordField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Vardas:"), 0, 0);
        grid.add(vardasField, 1, 0);
        grid.add(new Label("Pavardė:"), 0, 1);
        grid.add(pavardeField, 1, 1);
        grid.add(new Label("Elpaštas:"), 0, 2);
        grid.add(elpastasField, 1, 2);
        grid.add(new Label("Telefonas:"), 0, 3);
        grid.add(telefonasField, 1, 3);
        grid.add(new Label("Slaptažodis:"), 0, 4);
        grid.add(slaptazodisField, 1, 4);
        dialog.getDialogPane().setContent(grid);

        // 4) Kas ivyksta paspaudus mygtuka: tikriname ivesti ir kuriame objekta
        dialog.setResultConverter(paspaustas -> {
            if (paspaustas == issaugotiMygtukas) {
                if (vardasField.getText().isBlank() || pavardeField.getText().isBlank() || elpastasField.getText().isBlank() || telefonasField.getText().isBlank() || slaptazodisField.getText().isBlank()) {
                    rodytiKlaida("Visi laukai yra privalomi!");
                    return null;
                }

                if (telefonasField.getText().length() != 12){
                    rodytiKlaida("Telefono numeris turi būti sudarytas iš 12 simbolių");
                    return null;
                }

                if (telefonasField.getText().charAt(0) != '+'){
                    rodytiKlaida("Telefonas turi prasidėti simboliu +");
                    return null;
                }

                for (int i = 1; i < telefonasField.getText().length(); i++) {
                    if (!Character.isDigit(telefonasField.getText().charAt(i))) {
                        rodytiKlaida("Po + turi būti tik skaitmenys");
                        return null;
                    }
                }

                if (!elpastasField.getText().contains("@")) {
                    rodytiKlaida("Elpastas turi turėti simbolį @");
                    return null;
                }

                return new Savininkas(vardasField.getText(), pavardeField.getText(), elpastasField.getText(), telefonasField.getText(), slaptazodisField.getText());
            }
            return null; // Paspaustas Cancel
        });

        // 5) Parodome langa, laukiame, ir jei objektas sukurtas, issaugome
        Optional<Savininkas> rezultatas = dialog.showAndWait();
        rezultatas.ifPresent(savininkas -> {
            DuomenuBaze.getSavininkai().add(savininkas);
            rodytiPranesima("Savininkas " + savininkas.getVardas() + " uzregistruotas! Is viso savininku: "
                    + DuomenuBaze.getSavininkai().size());
        });
    }

    // 3. VADOVO REGISTRACIJA
    @FXML
    public void atidarytiVadovoReg() {
        // Be imones vadovo uzregistruoti negalima, nes jis turi kazkam priklausyti
        if (DuomenuBaze.getImones().isEmpty()) {
            rodytiKlaida("Pirmiausia uzregistruokite imone!");
            return;
        }

        // 1) Sukuriame iskylanti langa, kuris grazins Savininko objekta
        Dialog<Vadovas> dialog = new Dialog<>();
        dialog.setTitle("Vadovo registracija");

        // 2) Mygtukai apacioje: "Issaugoti" ir "Cancel"
        ButtonType issaugotiMygtukas = new ButtonType("Issaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(issaugotiMygtukas, ButtonType.CANCEL);

        // 3) Laukeliai, sudeti i lentele (stulpelis, eilute)
        TextField vardasField = new TextField();
        vardasField.setPromptText("Vardas");
        TextField pavardeField = new TextField();
        pavardeField.setPromptText("Pavardė");
        TextField elpastasField = new TextField();
        elpastasField.setPromptText("...@...");
        TextField telefonasField = new TextField();
        telefonasField.setPromptText("+370...");
        PasswordField slaptazodisField = new PasswordField();
        ComboBox<PaslauguImone> imonesBox = new ComboBox<>();
        imonesBox.getItems().addAll(DuomenuBaze.getImones());
        imonesBox.setPromptText("Pasirinkite imone...");
        imonesBox.setMaxWidth(Double.MAX_VALUE);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Vardas:"), 0, 0);
        grid.add(vardasField, 1, 0);
        grid.add(new Label("Pavardė:"), 0, 1);
        grid.add(pavardeField, 1, 1);
        grid.add(new Label("Elpaštas:"), 0, 2);
        grid.add(elpastasField, 1, 2);
        grid.add(new Label("Telefonas:"), 0, 3);
        grid.add(telefonasField, 1, 3);
        grid.add(new Label("Slaptažodis:"), 0, 4);
        grid.add(slaptazodisField, 1, 4);
        grid.add(new Label("Imonė:"), 0, 5);
        grid.add(imonesBox, 1, 5);
        dialog.getDialogPane().setContent(grid);

        // 4) Kas ivyksta paspaudus mygtuka: tikriname ivesti ir kuriame objekta
        dialog.setResultConverter(paspaustas -> {
            if (paspaustas == issaugotiMygtukas) {
                if (vardasField.getText().isBlank() || pavardeField.getText().isBlank() || elpastasField.getText().isBlank() || telefonasField.getText().isBlank() || slaptazodisField.getText().isBlank()) {
                    rodytiKlaida("Visi laukai yra privalomi!");
                    return null;
                }

                if (telefonasField.getText().length() != 12){
                    rodytiKlaida("Telefono numeris turi būti sudarytas iš 12 simbolių");
                    return null;
                }

                if (telefonasField.getText().charAt(0) != '+'){
                    rodytiKlaida("Telefonas turi prasidėti simboliu +");
                    return null;
                }

                for (int i = 1; i < telefonasField.getText().length(); i++) {
                    if (!Character.isDigit(telefonasField.getText().charAt(i))) {
                        rodytiKlaida("Po + turi būti tik skaitmenys");
                        return null;
                    }
                }

                if (!elpastasField.getText().contains("@")) {
                    rodytiKlaida("Elpastas turi turėti simbolį @");
                    return null;
                }

                if (imonesBox.getValue() == null) {
                    rodytiKlaida("Pasirinkite imonę!");
                    return null;
                }

                return new Vadovas(vardasField.getText(), pavardeField.getText(), elpastasField.getText(), telefonasField.getText(), slaptazodisField.getText());
            }
            return null; // Paspaustas Cancel
        });

        // 5) Parodome langa, laukiame, ir jei objektas sukurtas, issaugome
        Optional<Vadovas> rezultatas = dialog.showAndWait();
        rezultatas.ifPresent(vadovas -> {
            PaslauguImone pasirinktaImone = imonesBox.getValue();
            pasirinktaImone.pridetiVadova(vadovas);
            DuomenuBaze.getVadovai().add(vadovas);
            rodytiPranesima("Vadovas " + vadovas.getVardas() + " priskirtas imonei "
                    + pasirinktaImone.getPavadinimas());
        });
    }

    // 4. AUGINTINIO REGISTRACIJA
    @FXML
    public void atidarytiAugintinioReg() {
        // Be savininko augintinio užregistruoti negalima
        if (DuomenuBaze.getSavininkai().isEmpty()) {
            rodytiKlaida("Pirmiausia užregistruokite savininką!");
            return;
        }

        Dialog<Augintinis> dialog = new Dialog<>();
        dialog.setTitle("Augintinio registracija");

        ButtonType issaugotiMygtukas = new ButtonType("Išsaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(issaugotiMygtukas, ButtonType.CANCEL);

        // Laukeliai
        TextField vardasField = new TextField();

        ComboBox<String> rusisBox = new ComboBox<>();
        rusisBox.getItems().addAll("Šuo", "Katė", "Triušis", "Paukštis");
        rusisBox.setPromptText("Pasirinkite rūšį...");

        TextField veisleField = new TextField();

        TextField svorisField = new TextField();
        svorisField.setPromptText("pvz. 12.5");

        TextField amziusField = new TextField();

        ComboBox<Savininkas> savininkoBox = new ComboBox<>();
        savininkoBox.getItems().addAll(DuomenuBaze.getSavininkai());
        savininkoBox.setPromptText("Pasirinkite savininką...");

        // Lentelė
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Vardas:"), 0, 0);
        grid.add(vardasField, 1, 0);
        grid.add(new Label("Rūšis:"), 0, 1);
        grid.add(rusisBox, 1, 1);
        grid.add(new Label("Veislė:"), 0, 2);
        grid.add(veisleField, 1, 2);
        grid.add(new Label("Svoris (kg):"), 0, 3);
        grid.add(svorisField, 1, 3);
        grid.add(new Label("Amžius:"), 0, 4);
        grid.add(amziusField, 1, 4);
        grid.add(new Label("Savininkas:"), 0, 5);
        grid.add(savininkoBox, 1, 5);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(paspaustas -> {
            if (paspaustas == issaugotiMygtukas) {
                if (vardasField.getText().isBlank()) {
                    rodytiKlaida("Įveskite vardą!");
                    return null;
                }
                if (rusisBox.getValue() == null || savininkoBox.getValue() == null) {
                    rodytiKlaida("Pasirinkite rūšį ir savininką!");
                    return null;
                }

                int amzius;
                try {
                    amzius = Integer.parseInt(amziusField.getText());
                } catch (NumberFormatException e) {
                    rodytiKlaida("Amžius turi būti skaičius!");
                    return null;
                }

                double svoris;
                try {
                    svoris = Double.parseDouble(svorisField.getText().replace(",", "."));
                } catch (NumberFormatException e) {
                    rodytiKlaida("Svoris turi būti skaičius!");
                    return null;
                }

                // Dydis nustatomas automatiškai pagal rūšį ir svorį
                String dydis = Augintinis.nustatytiDydi(rusisBox.getValue(), svoris);

                return new Augintinis(vardasField.getText(), rusisBox.getValue(),
                        veisleField.getText(), dydis, amzius);
            }
            return null;
        });

        Optional<Augintinis> rezultatas = dialog.showAndWait();
        rezultatas.ifPresent(augintinis -> {
            Savininkas pasirinktas = savininkoBox.getValue();
            pasirinktas.registruotiAugintini(augintinis);
            DuomenuBaze.getAugintiniai().add(augintinis);
            rodytiPranesima("Augintinis " + augintinis.getVardas() + " (dydis: " + augintinis.getDydis()
                    + ") priregistruotas savininkui " + pasirinktas.getVardas()
                    + ". Jis turi augintinių: " + pasirinktas.getAugintiniai().size());
        });
    }

    // 5. PRIŽIŪRĖTOJO REGISTRACIJA
    @FXML
    public void atidarytiPriziuretojoReg() {
        // Be imones vadovo uzregistruoti negalima, nes jis turi kazkam priklausyti
        if (DuomenuBaze.getImones().isEmpty()) {
            rodytiKlaida("Pirmiausia uzregistruokite imone!");
            return;
        }

        // 1) Sukuriame iskylanti langa, kuris grazins Savininko objekta
        Dialog<Priziuretojas> dialog = new Dialog<>();
        dialog.setTitle("Prižiūrėtojo registracija");

        // 2) Mygtukai apacioje: "Issaugoti" ir "Cancel"
        ButtonType issaugotiMygtukas = new ButtonType("Išsaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(issaugotiMygtukas, ButtonType.CANCEL);

        // 3) Laukeliai, sudeti i lentele (stulpelis, eilute)
        TextField vardasField = new TextField();
        vardasField.setPromptText("Vardas");
        TextField pavardeField = new TextField();
        pavardeField.setPromptText("Pavardė");
        TextField elpastasField = new TextField();
        elpastasField.setPromptText("...@...");
        TextField telefonasField = new TextField();
        telefonasField.setPromptText("+370...");
        PasswordField slaptazodisField = new PasswordField();
        TextArea aprasymasArea = new TextArea();
        aprasymasArea.setPrefRowCount(3);
        aprasymasArea.setWrapText(true);
        ComboBox<PaslauguImone> imonesBox = new ComboBox<>();
        imonesBox.getItems().addAll(DuomenuBaze.getImones());
        imonesBox.setPromptText("Pasirinkite imone...");
        imonesBox.setMaxWidth(Double.MAX_VALUE);

        // Vadovas gali registruoti tik i savo imone
        if (!Sesija.arAdministratorius()) {
            imonesBox.getItems().setAll(Sesija.getVadovas().getImone()); // sarase tik jo imone
            imonesBox.setValue(Sesija.getVadovas().getImone());          // is karto pasirinkta
            imonesBox.setDisable(true);                                  // pakeisti negalima
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Vardas:"), 0, 0);
        grid.add(vardasField, 1, 0);
        grid.add(new Label("Pavardė:"), 0, 1);
        grid.add(pavardeField, 1, 1);
        grid.add(new Label("Elpaštas:"), 0, 2);
        grid.add(elpastasField, 1, 2);
        grid.add(new Label("Telefonas:"), 0, 3);
        grid.add(telefonasField, 1, 3);
        grid.add(new Label("Slaptažodis:"), 0, 4);
        grid.add(slaptazodisField, 1, 4);
        grid.add(new Label("Aprašymas:"), 0, 5);
        grid.add(aprasymasArea, 1, 5);
        grid.add(new Label("Imonė:"), 0, 6);
        grid.add(imonesBox, 1, 6);
        dialog.getDialogPane().setContent(grid);

        // 4) Kas ivyksta paspaudus mygtuka: tikriname ivesti ir kuriame objekta
        dialog.setResultConverter(paspaustas -> {
            if (paspaustas == issaugotiMygtukas) {
                if (vardasField.getText().isBlank() || pavardeField.getText().isBlank() || elpastasField.getText().isBlank() || telefonasField.getText().isBlank() || slaptazodisField.getText().isBlank() || aprasymasArea.getText().isBlank() || imonesBox.getValue() == null) {
                    rodytiKlaida("Visi laukai yra privalomi!");
                    return null;
                }

                if (telefonasField.getText().length() != 12){
                    rodytiKlaida("Telefono numeris turi būti sudarytas iš 12 simbolių");
                    return null;
                }

                if (telefonasField.getText().charAt(0) != '+'){
                    rodytiKlaida("Telefonas turi prasidėti simboliu +");
                    return null;
                }

                for (int i = 1; i < telefonasField.getText().length(); i++) {
                    if (!Character.isDigit(telefonasField.getText().charAt(i))) {
                        rodytiKlaida("Po + turi būti tik skaitmenys");
                        return null;
                    }
                }

                if (!elpastasField.getText().contains("@")) {
                    rodytiKlaida("Elpastas turi turėti simbolį @");
                    return null;
                }

                return new Priziuretojas(vardasField.getText(), pavardeField.getText(), elpastasField.getText(), telefonasField.getText(), slaptazodisField.getText(), aprasymasArea.getText() );
            }
            return null; // Paspaustas Cancel
        });

        // 5) Parodome langa, laukiame, ir jei objektas sukurtas, issaugome
        Optional<Priziuretojas> rezultatas = dialog.showAndWait();
        rezultatas.ifPresent(priziuretojas -> {
            PaslauguImone pasirinktaImone = imonesBox.getValue();
            pasirinktaImone.pridetiPriziuretoja(priziuretojas);
            DuomenuBaze.getPriziuretojai().add(priziuretojas);
            rodytiPranesima("Prižiūrėtojas " + priziuretojas.getVardas() + " įdarbintas įmonėje " + pasirinktaImone.getPavadinimas());
        });
    }

    @FXML
    public void atsijungti(ActionEvent event) {
        Sesija.atsijungti();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
            Stage langas = (Stage) ((Node) event.getSource()).getScene().getWindow();
            langas.setScene(new Scene(loader.load(), 400, 300));
        } catch (Exception e) {
            rodytiKlaida("Nepavyko atsijungti: " + e.getMessage());
        }
    }

    // Pagalbiniai metodai pranesimams
    private void rodytiPranesima(String tekstas) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sekme");
        alert.setHeaderText(null);
        alert.setContentText(tekstas);
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
