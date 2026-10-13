package valdikliai;

import duomenys.DuomenuBaze;
import modelis.Vartotojas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VartotojaiController {

    @FXML private TableView<Vartotojas> vartotojaiTable;
    @FXML private TableColumn<Vartotojas, String> vardasCol;
    @FXML private TableColumn<Vartotojas, String> pavardeCol;
    @FXML private TableColumn<Vartotojas, String> elPastasCol;
    @FXML private TableColumn<Vartotojas, String> telefonasCol;
    @FXML private TableColumn<Vartotojas, String> roleCol;
    @FXML private TableColumn<Vartotojas, Boolean> uzblokuotasCol;

    @FXML
    public void initialize() {
        // 1) Kiekvienam stulpeliui pasakome, is kurio getterio imti reiksme
        vardasCol.setCellValueFactory(new PropertyValueFactory<>("vardas"));
        pavardeCol.setCellValueFactory(new PropertyValueFactory<>("pavarde"));
        elPastasCol.setCellValueFactory(new PropertyValueFactory<>("elPastas"));
        telefonasCol.setCellValueFactory(new PropertyValueFactory<>("telefonas"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        uzblokuotasCol.setCellValueFactory(new PropertyValueFactory<>("arUzblokuotas"));

        // 2) Uzpildome lentele
        atnaujintiLentele();
    }

    // Isvalo lentele ir sudeda visus vartotojus is DuomenuBaze
    private void atnaujintiLentele() {
        vartotojaiTable.getItems().clear();
        vartotojaiTable.getItems().addAll(DuomenuBaze.getAdministratoriai());
        vartotojaiTable.getItems().addAll(DuomenuBaze.getVadovai());
        vartotojaiTable.getItems().addAll(DuomenuBaze.getSavininkai());
        vartotojaiTable.getItems().addAll(DuomenuBaze.getPriziuretojai());
    }
}
