package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import model.AplicacaoTransmissora;

public class telaController implements Initializable{


    @FXML private Button enviar;

    @FXML private TextArea transmissor;
    @FXML private TextArea receptor;

    @FXML private SplitMenuButton menu;

    @FXML private MenuItem binario;
    @FXML private MenuItem manchester;
    @FXML private MenuItem manchesterDiferencial;
    @FXML private ProgressBar progressBar;

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
                        System.out.println("Erro ao Enviar a Mensagem");
                    }
                }
            }.start();
        }
    }


public void enviarButton(ActionEvent event) {
    // Method to send the message written in the TextBox
    if (transmissor != null && transmissor.getText() != null && !transmissor.getText().isEmpty()) {
        String message = transmissor.getText();
        this.transmissor.setText("");

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Assuming that the length of the message is the total work to be done
                int totalWork = message.length();
                for (int i = 0; i < totalWork; i++) {
                    // Simulate work (e.g., sending a part of the message)
                    Thread.sleep(1000);

                    // Update progress
                    updateProgress(i + 1, totalWork);
                }
                return null;
            }
        };

        // Bind the progress property of the progress bar to the progress property of the task
        progressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(e -> {
            // When the task is done, send the message
            this.enviar(message);
        });

        // Start the task in a new thread
        new Thread(task).start();
    } else {
        Alerta();
    }
}

    public void addMesage(String mensagem) {
        // Método para adcionar a mensagem na TextArea
        String oldText = receptor.getText();
        receptor.setText(oldText + mensagem + "\n\n");
        receptor.appendText("");
    }

    public void Alerta () {
        // Método para exibir uma janela de alerta
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText("Erro, o campo de texto está vazio!");
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        binario.setOnAction(event -> AplicacaoTransmissora.setTipoDeCodificacao(AplicacaoTransmissora.BINARIA));
        manchester.setOnAction(event -> AplicacaoTransmissora.setTipoDeCodificacao(AplicacaoTransmissora.MANCHESTER));
        manchesterDiferencial.setOnAction(event -> AplicacaoTransmissora.setTipoDeCodificacao(AplicacaoTransmissora.MANCHESTER_DIFERENCIAL));
    }
    
}
