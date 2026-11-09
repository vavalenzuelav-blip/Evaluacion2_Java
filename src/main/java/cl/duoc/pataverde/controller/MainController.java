package cl.duoc.pataverde.controller;

import cl.duoc.pataverde.dao.MascotaDAO;
import cl.duoc.pataverde.dao.MascotaDAOJson;
import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Mascota;
import cl.duoc.pataverde.model.Perro;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtNombre, txtEdad, txtPeso, txtRaza;
    @FXML private CheckBox chkVacunado, chkAdopcion, chkExterior;
    @FXML private Label lblMensaje;

    @FXML private TableView<Mascota> tablaMascotas;
    @FXML private TableColumn<Mascota, String> colTipo, colNombre, colCosto;
    @FXML private TableColumn<Mascota, Integer> colEdad;
    @FXML private TableColumn<Mascota, Double> colPeso;

    private final MascotaDAO mascotaDAO = new MascotaDAOJson();
    private final ObservableList<Mascota> listaTabla = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cmbTipo.getItems().addAll("Perro", "Gato");
        cmbTipo.setOnAction(e -> actualizarFormulario());

        colTipo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipo()));
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colEdad.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getEdad()));
        colPeso.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPeso()));
        colCosto.setCellValueFactory(cell -> new SimpleStringProperty(String.format("$%.3f", cell.getValue().calcularCostoConsulta()).replace(",000", ".000")));

        cmbTipo.getSelectionModel().selectFirst();
        actualizarFormulario();

        cargarDatosGuardados();
    }

    private void actualizarFormulario() {
        boolean esPerro = "Perro".equals(cmbTipo.getValue());
        txtRaza.setDisable(!esPerro);
        chkVacunado.setDisable(!esPerro);
        chkAdopcion.setDisable(!esPerro);
        chkExterior.setDisable(esPerro);
    }

    @FXML
    private void handleRegistrar() {
        // Validaciones requeridas por la Sección 4 de la pauta
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
            mostrarError("Nombre: no puede estar vacío ni contener solo espacios.");
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(txtEdad.getText().trim());
            if (edad < 0 || edad > 30) {
                mostrarError("Edad: debe ser un número entero entre 0 y 30.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarError("Edad: debe ser un número entero entre 0 y 30.");
            return;
        }

        double peso;
        try {
            peso = Double.parseDouble(txtPeso.getText().trim());
            if (peso <= 0) {
                mostrarError("Peso: debe ser mayor a 0 kg.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarError("Peso: debe ser un número mayor a 0.");
            return;
        }

        Mascota nuevaMascota;
        if ("Perro".equals(cmbTipo.getValue())) {
            if (txtRaza.getText() == null || txtRaza.getText().trim().isEmpty()) {
                mostrarError("Raza: no puede estar vacía ni contener solo espacios.");
                return;
            }
            Perro p = new Perro(txtNombre.getText().trim(), edad, peso, txtRaza.getText().trim(), chkVacunado.isSelected());
            if (chkAdopcion.isSelected()) {
                p.disponibleAdopcion();
            }
            nuevaMascota = p;
        } else {
            nuevaMascota = new Gato(txtNombre.getText().trim(), edad, peso, chkExterior.isSelected());
        }

        listaTabla.add(nuevaMascota);
        mascotaDAO.guardar(listaTabla);

        mostrarExito(nuevaMascota.getNombre() + " (" + nuevaMascota.getTipo() + ") registrado correctamente.");
        limpiar();
    }

    @FXML
    private void handleEliminar() {
        Mascota seleccionada = tablaMascotas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            listaTabla.remove(seleccionada);
            mascotaDAO.guardar(listaTabla);
            mostrarExito("Mascota eliminada correctamente.");
        } else {
            mostrarError("Debe seleccionar una mascota de la tabla para eliminarla.");
        }
    }

    private void cargarDatosGuardados() {
        listaTabla.clear();
        listaTabla.addAll(mascotaDAO.obtenerTodas());
        tablaMascotas.setItems(listaTabla);
    }

    private void mostrarExito(String mensaje) {
        lblMensaje.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        lblMensaje.setText(mensaje);
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setStyle("-fx-font-weight: bold; -fx-text-fill: #d32f2f;");
        lblMensaje.setText(mensaje);
    }

    private void limpiar() {
        txtNombre.clear();
        txtEdad.clear();
        txtPeso.clear();
        txtRaza.clear();
        chkVacunado.setSelected(false);
        chkAdopcion.setSelected(false);
        chkExterior.setSelected(false);
    }
}

