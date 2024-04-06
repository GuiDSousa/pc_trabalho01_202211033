package view;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
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
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.AplicacaoTransmissora;

public class telaController implements Initializable{


    @FXML private Button enviar;

    @FXML private TextArea transmissor;
    @FXML private TextArea receptor;
    @FXML private TextArea ascii;
    @FXML private TextArea binary;

    @FXML private SplitMenuButton menu;

    @FXML private MenuItem binario;
    @FXML private MenuItem manchester;
    @FXML private MenuItem manchesterDiferencial;
    @FXML private ProgressBar progressBar;

    @FXML private ImageView onda_1;
    @FXML private ImageView onda_2;
    @FXML private ImageView onda_3;
    @FXML private ImageView onda_4;
    @FXML private ImageView onda_5;
    @FXML private ImageView onda_6;

    private String selectedProtocol = "binario";

    public void enviar(String mensagem) {
        // Método para enviar a mensagem escrita na TextBox
        if (mensagem.isEmpty()) {
            // Abrir uma janela de alerta contendo a mensagem "O campo de texto está vazio!"
            Alerta();
        } else {
            menu.setDisable(false);
            addMesage(mensagem);
            String bitSequence = messageToBinary(mensagem); // Convert the message to a binary string
            int protocol = AplicacaoTransmissora.getTipoDeCodificacao(); // Get the selected protocol
            updateImageViews(bitSequence, protocol); // Update the ImageViews based on the bit sequence
            binary.setText(bitSequence);
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

    public String messageToBinary(String message) {
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        return binary.toString();
    }

    public void updateImageViews(String bitSequence, int protocol) {
        // Create a queue of ImageViews
        Queue<ImageView> imageViewQueue = new LinkedList<>();
        imageViewQueue.add(onda_1);
        imageViewQueue.add(onda_2);
        imageViewQueue.add(onda_3);
        imageViewQueue.add(onda_4);
        imageViewQueue.add(onda_5);
        imageViewQueue.add(onda_6);
    
        // Create a new PauseTransition with a duration of 0.2 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
    
        // Create an iterator for the bit sequence
        Iterator<Character> bitIterator = bitSequence.chars().mapToObj(c -> (char) c).iterator();
    
        // Set the action to be performed on each tick of the PauseTransition
        pause.setOnFinished(event -> {
            if (bitIterator.hasNext()) {
                char bit = bitIterator.next();
    
                // Get the next ImageView from the queue
                ImageView imageView = imageViewQueue.poll();

                // Update the ImageView based on the bit and the protocol
                if (protocol == AplicacaoTransmissora.BINARIA) {
                    imageView.setImage(new ImageView(bit == '0' ? "binario-0.png" : "binario-1.png").getImage());
                } else if (protocol == AplicacaoTransmissora.MANCHESTER) {
                    imageView.setImage(new ImageView(bit == '0' ? "baixo-alto.png" : "alto-baixo.png").getImage());
                }

                // Add the ImageView back to the end of the queue
                imageViewQueue.add(imageView);

                // Restart the PauseTransition
                pause.playFromStart();
            } else {
                // Clear all ImageViews after the transmission has ended
                for (ImageView imageView : imageViewQueue) {
                    imageView.setImage(null);
                }
            }
        });
    
        // Start the PauseTransition
        pause.play();
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

    // Método para alterar a imagem da onda binária
    // Nesse método, as 6 imageview são alteradas para demonstrar o fluxo de bits  '0' e '1' das mensagens transmitidas
    // Caso a mensagem seja '0', a imagem da onda binária é alterada para a imagem de binário-0.png
    // Caso a mensagem seja '1', a imagem da onda binária é alterada para a imagem de binário-1.png
 
}
