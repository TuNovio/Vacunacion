import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Mundo.Usuario;
import Conexion.CitaDAO;
import Conexion.UsuarioDAO;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Mundo.Cita;
public class panelCita implements Initializable{

    /**
     * cita del usuario 
     */
    CitaDAO miCitaDAO = new CitaDAO();
    /**
     * operaciones del usuario
     */
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    /**
     * boton que realiza la accion de consultar cita
     */
    @FXML
    private Button btnConsultar;
    /**
     * boton que realiza la accion de eliminar cita
     */
    @FXML
    private Button btnEliminar;
    /**
     * boton para realizar el registro de usuario y cita
     */
    @FXML
    private Button btnRegistrar;
    /**
     * lista de usuarios ordenados por documento
     */
    @FXML
    private ComboBox<Integer> cbxUsuarios;
    /**
     * columna que representa el codigo de la cita
     */
    @FXML
    private TableColumn<Cita, Integer> colCodigo;
    /**
     * columna que representa la fecha de la cita
     */
    @FXML
    private TableColumn<Cita, String> colFecha;
    /**
     * columna que representa el lugar de la cita
     */
    @FXML
    private TableColumn<Cita, String> colLugar;
    /**
     * columna que representa el turno de la cita
     */
    @FXML
    private TableColumn<Cita, Integer> colTurno;
    /**
     * elemento para elegir la fecha de la cita
     */
    @FXML
    private DatePicker dtaFecha;
    /**
     * etiqueta de la tabla de citas
     */
    @FXML
    private Label lblCitas;
    /**
     * etiqueta del codigo de cita
     */
    @FXML
    private Label lblCodigo;
    /**
     * etiqueta de la fecha de cita
     */
    @FXML
    private Label lblFecha;
    /**
     * etiqueta del lugar de la cita
     */
    @FXML
    private Label lblLugar;
    /**
     * etiqueta del turno de cita
     */
    @FXML
    private Label lblTurno;
    /**
     * etiqueta del numero de documento del usuario
     */
    @FXML
    private Label lblUsuairo;
    /**
     * tabla de registros de cita
     */
    @FXML
    private TableView<Cita> tblCitas;
    /**
     * elemento con el codigo de cita
     */
    @FXML
    private TextField txtCodigo;
    /**
     * elemento con el lugar de la cita
     */
    @FXML
    private TextField txtLugar;
    /**
     * elemento con el turno de la cita
     */
    @FXML
    private TextField txtTurno;
    /**
     * consultar la cita dado el documento del usuario
     * @param event accion de consultar cita
     */
    @FXML
    void btnConsultarOnClicked(ActionEvent event) {
        String documento = String.valueOf(cbxUsuarios.getSelectionModel().getSelectedItem());
        if(!documento.isEmpty()){
            Usuario mio = usuarioDAO.buscarUsuario(documento);
            if(mio!=null){
                buscarCita(mio.getCita());
            }else{
                Alertar.display("Buscar Usuario", "El usuario no existe");
            }
        }else{
            Alertar.display("Buscar", "Documento mal digitado");
        }
    }
    /**
     * eliminar la cita del usuario
     * @param event accion de eliminar la cita
    */
    @FXML
    void btnEliminarOnClicked(ActionEvent event) {
        if(txtCodigo.getText()!=null){
            Cita buscada = miCitaDAO.buscarCita(txtCodigo.getText());
            if(buscada!=null){
                if(JOptionPane.showConfirmDialog(null, "Seguro quiere eliminar la cita del usuario" + JOptionPane.YES_NO_OPTION)==0){
                    miCitaDAO.eliminarCita(buscada);
                    Alertar.display("Eliminar", "Se elimino la cita");
                }
            }else{
                Alertar.display("Eliminar", "La cita no existe");
            }
        }else{
            Alertar.display("Validar", "El codigo es null");
        }
    }
    /**
     * registrar la cita al usuario sin cita
     * @param event accion de registrar cita
     */
    @FXML
    void btnRegistrarOnClicked(ActionEvent event) {
        Alertar.display("Mensaje", "Proximamente");
    }
    /**
     * se inicializan las columnas de la tabla
     * <b> pre: </b> la tabla se encuentra inicializada
     * <b> post: </b> se inicializan las columnas de la tabla
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        agregarElemento();
    }
    /**
     * buscar la cita dado el codigo de la cita
     * <b> pre: </b> la cita se encuentra inicializada
     * <b> post: </b> se busca la cita dado el codigo
     * @param nCodigo, es el codigo de la cita a buscar
     * @return true si la encontro de lo contrario false
     */
    public Boolean buscarCita(String nCodigo){
        Boolean encontrar = false;
        Cita consultar = miCitaDAO.buscarCita(nCodigo);
        if(consultar != null){
            tblCitas.getItems().add(consultar);
            actualizarElementos(consultar);
        }else{
            Alertar.display("Consultar", "La cita \n no se encuentra registrada");
        }
        return encontrar;
    }
    /**
     * actualizar los elementos de la interfaz con la información consultada
     * <b> pre: </b> los elementos de la interfaz se encuentran inicializados
     * <b> post: </b>  se actualizan los elementos de la interfaz
     * @param nCita, es el objeto con la informacion para los elementos de la interfaz
     */
    public void actualizarElementos(Cita nCita){
        txtCodigo.setText(String.valueOf(nCita.getCodigo()));
        txtLugar.setText(nCita.getLugar());
        txtTurno.setText(String.valueOf(nCita.getTurno()));
        dtaFecha.setValue(LocalDate.parse(nCita.getFecha()));
    }
    /**
     * añadir elemento al comboBox
     * <b> pre: </b> la lista de elementos se encuentra inicializada
     * <b> post: </b> se añade el elemento al ComboBox
     */
    public void agregarElemento(){
        ObservableList<Integer> usuarios = usuarioDAO.seleccionarUsuario();
        cbxUsuarios.setItems(usuarios);
    }
}