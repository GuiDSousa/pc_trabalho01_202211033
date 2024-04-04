package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.AplicacaoTransmissora;

public class telaController implements Initializable{


    @FXML private Button enviar;

    @FXML private TextArea transmissor;
    @FXML private TextField mensagem;

    @FXML private Label receptor;

    @FXML private SplitMenuButton menu;

    @FXML private MenuItem binario;
    @FXML private MenuItem manchester;
    @FXML private MenuItem manchesterDiferencial;

    public void enviar(String mensagem) {
        // Método para enviar a mensagem escrita na TextBox
        if (mensagem.isEmpty()) {
            // Abrir uma janela de alerta contendo a mensagem "O campo de texto está vazio!"
            Alerta();
        } else {
            menu.setDisable(false);
            addMesage(mensagem);

            new Thread () {
                public void run() {
                    try {
                        Thread.sleep(1000);
                        AplicacaoTransmissora.aplicacaoTransmissora(mensagem);
                    } catch (Exception e) {
                        System.out.println("[ERRO] - Enviar Mensagem");
                    }
                }
            }.start();
        }
    }


    public void enviarButton(ActionEvent event) {
        // Método para enviar a mensagem escrita na TextBox
        if (transmissor != null && transmissor.getText() != null) {
           this.enviar(transmissor.getText());
           this.transmissor.setText("");
        } else {
            Alerta();
        }
    }

    public void addMesage(String mensagem) {
        // Método para adcionar a mensagem na TextArea
        String oldText = transmissor.getText();
        transmissor.setText(oldText + mensagem + "\n\n");
        transmissor.appendText("");
    }

    public void Alerta () {
        // Método para exibir uma janela de alerta
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText("Erro, o campo de texto está vazio!");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        binario.setOnAction(event -> AplicacaoTransmissora.setTipoDeCodificacao(AplicacaoTransmissora.BINARIA));
        manchester.setOnAction(event -> AplicacaoTransmissora.setTipoDeCodificacao(AplicacaoTransmissora.MANCHESTER));
        manchesterDiferencial.setOnAction(event -> AplicacaoTransmissora.setTipoDeCodificacao(AplicacaoTransmissora.MANCHESTER_DIFERENCIAL));
    }
    
}
