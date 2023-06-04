package com.proyecto_salones;

import Model.Persona;
import com.PersonaDAO.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label asig;

    @FXML
    private Button btClose;

    @FXML
    private Button btDelete;

    @FXML
    private Button btEdit;

    @FXML
    private Button btSave;


    @FXML
    private Label fecha;

    @FXML
    private Label hora;

    @FXML
    private Label name;

    @FXML
    private Label salon;

    @FXML
    private Label sede;

    @FXML
    private Label semestre;

    @FXML
    private TableView<Persona> table;

    @FXML
    TableColumn<Persona, Integer> tbId;
    @FXML
    private TableColumn<Persona, String> tbAsig;

    @FXML
    private TableColumn<Persona, DatePicker> tbFecha;

    @FXML
    private TableColumn<Persona, String> tbHora;

    @FXML
    private TableColumn<Persona, String> tbProfesor;

    @FXML
    private TableColumn<Persona, String> tbSalon;

    @FXML
    private TableColumn<Persona, String> tbSede;

    @FXML
    private TableColumn<Persona, String> tbSemestre;

    @FXML
    private TextField txtAsig;

    @FXML
    private DatePicker txtFecha;

    @FXML
    private ComboBox<String> txtHora;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> txtSalon;

    @FXML
    private ComboBox<String> txtSede;

    @FXML
    private ComboBox<String> txtSemestre;

    private DAO dao;

    private ContextMenu contextMenu;

    private Persona personaSelect;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] h = {"7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM"};
        String[] sem = {"1", "2", "3", "4", "5", "6", "7", "8"};
        String[] sed = {"Principal", "Nogal", "Anova", "Alcazar"};
        String[] sal = {"101", "102", "103", "104"};

        ObservableList<String> itemsH = FXCollections.observableArrayList(h);
        ObservableList<String> itemsSem = FXCollections.observableArrayList(sem);
        ObservableList<String> itemsSed = FXCollections.observableArrayList(sed);
        ObservableList<String> itemsSal = FXCollections.observableArrayList(sal);

        txtHora.setItems(itemsH);
        txtHora.setValue("Selecione");
        txtSemestre.setItems(itemsSem);
        txtSemestre.setValue("Selecione");
        txtSede.setItems(itemsSed);
        txtSede.setValue("Selecione");
        txtSalon.setItems(itemsSal);
        txtSalon.setValue("Selecione");


        this.dao = new DAO();

        load();



        contextMenu = new ContextMenu();

        MenuItem myEdit = new MenuItem("Editar");
        MenuItem myDelete = new MenuItem("Eliminar");

        contextMenu.getItems().addAll(myEdit,myDelete);
        myEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int index = table.getSelectionModel().getSelectedIndex();
                personaSelect = table.getItems().get(index);
                System.out.println(personaSelect);
                txtName.setText(personaSelect.getName());
                txtAsig.setText(personaSelect.getAsignatura());
                txtSede.getSelectionModel().select(personaSelect.getSede());
                txtSalon.getSelectionModel().select(personaSelect.getSalon());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                txtFecha.setValue(LocalDate.parse(personaSelect.getFecha(), formatter));
                txtHora.getSelectionModel().select(personaSelect.getHora());
                txtSemestre.getSelectionModel().select(personaSelect.getSemestre());

                btEdit.setDisable(false);

            }

        });

        myDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = table.getSelectionModel().getSelectedIndex();

                Persona personaDelete = table.getItems().get(index);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmated :D");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to DELETE "+personaDelete.getName()+ " ?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK){
                    boolean rsp = dao.delete(personaDelete.getId());

                    if (rsp) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

                        alert2.setTitle("Succes :D");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Delete Succed");
                        alert2.initStyle(StageStyle.UTILITY);
                        alert2.showAndWait();

                        load();


                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);

                        alert2.setTitle("Error :(");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Delete Error");
                        alert2.initStyle(StageStyle.UTILITY);
                        alert2.showAndWait();
                    }

                }


            }
        });

        table.setContextMenu(contextMenu);


    }
    public void load(){
        table.getItems().clear( );
        table.getColumns().clear();


        List<Persona> persona = this.dao.list();
        ObservableList<Persona> data = FXCollections.observableArrayList(persona);


        TableColumn<Persona,Integer> tbId = new TableColumn("Id");
        tbId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn<Persona,String> tbProfesor = new TableColumn<Persona,String>("Profesor");
        tbProfesor.setCellValueFactory(new PropertyValueFactory<Persona,String>("name"));

        TableColumn tbSemestre = new TableColumn("Semestre");
        tbSemestre.setCellValueFactory(new PropertyValueFactory<Persona,String>("semestre"));

        TableColumn tbAsig = new TableColumn("Asignatura");
        tbAsig.setCellValueFactory(new  PropertyValueFactory("asignatura"));

        TableColumn tbSede = new TableColumn("Sede");
        tbSede.setCellValueFactory(new PropertyValueFactory("sede"));

        TableColumn tbSalon = new TableColumn("Salon");
        tbSalon.setCellValueFactory((new PropertyValueFactory("salon")));

        TableColumn tbHora = new TableColumn("Hora");
        tbHora.setCellValueFactory(new PropertyValueFactory("hora"));

        TableColumn tbFecha = new TableColumn("Fecha");
        tbFecha.setCellValueFactory(new PropertyValueFactory("fecha"));




        table.setItems(data);
        table.getColumns().addAll(tbId,tbProfesor,tbSemestre,tbAsig,tbSede,tbSalon,tbHora,tbFecha);
    }

    @FXML
    void save(ActionEvent event) {

        if (personaSelect == null) {

            Persona persona = new Persona();

            persona.setName(txtName.getText());
            persona.setSemestre(txtSemestre.getSelectionModel().getSelectedItem());
            persona.setAsignatura(txtAsig.getText());
            persona.setSede(txtSede.getSelectionModel().getSelectedItem());
            persona.setSalon(txtSalon.getSelectionModel().getSelectedItem());
            persona.setHora(txtHora.getSelectionModel().getSelectedItem());
            persona.setFecha(txtFecha.getValue().toString());

            System.out.println(persona.toString());

            boolean rsp = this.dao.register(persona);

            if (rsp) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Succes :D");
                alert.setHeaderText(null);
                alert.setContentText("Register Succed");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();

                clean();
                load();


            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error :(");
                alert.setHeaderText(null);
                alert.setContentText("Register Error");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }
        }else {

            personaSelect.setName(txtName.getText());
            personaSelect.setSemestre(txtSemestre.getSelectionModel().getSelectedItem());
            personaSelect.setAsignatura(txtAsig.getText());
            personaSelect.setSede(txtSede.getSelectionModel().getSelectedItem());
            personaSelect.setSalon(txtSalon.getSelectionModel().getSelectedItem());
            personaSelect.setHora(txtHora.getSelectionModel().getSelectedItem());
            personaSelect.setFecha(txtFecha.getValue().toString());

            boolean rsp = this.dao.edit(personaSelect);

            if (rsp) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Succes :D");
                alert.setHeaderText(null);
                alert.setContentText("Register Succed");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();

                clean();
                load();
                personaSelect = null;
                btEdit.setDisable(true);


            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error :(");
                alert.setHeaderText(null);
                alert.setContentText("Register Error");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }

        }

    }

    @FXML
    void edit(ActionEvent event) { //Cancelar
        personaSelect = null;
        clean();
        btEdit.setDisable(true);

    }





    private void clean() {
        txtName.setText("");
        txtAsig.setText("");
        txtSede.getSelectionModel().select("Seleccione");
        txtSalon.getSelectionModel().select("Seleccione");
        txtFecha.setValue(null);
        txtHora.getSelectionModel().select("Seleccione");
        txtSemestre.getSelectionModel().select("Seleccione");


    }

}