/* ***************************************************************
 * Autor............: Guilherme Dias Sousa
 * Matricula........: 202211033
 * Inicio...........: 20/03/2024    
 * Ultima alteracao.: 06/04/2024
 * Nome.............: telaController
 * Funcao...........: Controlador da interface gráfica da aplicação	
 *************************************************************** */
package view;

import java.net.URL; // Importa a classe URL do pacote java.net
import java.util.Iterator; // Importa a classe Iterator do pacote java.util
import java.util.LinkedList;// Importa a classe LinkedList do pacote java.util
import java.util.Queue; // Importa a classe Queue do pacote java.util
import java.util.ResourceBundle; // Importa a classe ResourceBundle do pacote java.util

import javafx.animation.PauseTransition; // Importa a classe PauseTransition do pacote javafx.animation
import javafx.concurrent.Task; // Importa a classe Task do pacote javafx.concurrent
import javafx.event.ActionEvent; // Importa a classe ActionEvent do pacote javafx.event
import javafx.fxml.FXML; // Importa a classe FXML do pacote javafx.fxml
import javafx.fxml.Initializable; // Importa a classe Initializable do pacote javafx.fxml
import javafx.scene.control.Alert; // Importa a classe Alert do pacote javafx.scene.control
import javafx.scene.control.Alert.AlertType; // Importa a classe AlertType do pacote javafx.scene.control
import javafx.scene.control.Button; // Importa a classe Button do pacote javafx.scene.control
import javafx.scene.control.MenuItem; // Importa a classe MenuItem do pacote javafx.scene.control
import javafx.scene.control.ProgressBar; // Importa a classe ProgressBar do pacote javafx.scene.control
import javafx.scene.control.SplitMenuButton; // Importa a classe SplitMenuButton do pacote javafx.scene.control
import javafx.scene.control.TextArea; // Importa a classe TextArea do pacote javafx.scene.control
import javafx.scene.image.ImageView; // Importa a classe ImageView do pacote javafx.scene.image
import javafx.util.Duration; // Importa a classe Duration do pacote javafx.util
import model.AplicacaoTransmissora; // Importa a classe AplicacaoTransmissora do pacote model

/*
 * Método: telaController
 * Função: Controlador da interface gráfica da aplicação
 * Parametros: void
 * Retorno: void;
 */
public class telaController implements Initializable {

  @FXML
  private Button enviar;

  // Declaração das variáveis que representam as áreas de texto da interface
  // gráfica
  @FXML
  private TextArea transmissor;
  @FXML
  private TextArea receptor;
  @FXML
  private TextArea ascii;
  @FXML
  private TextArea binary;

  // Veriáveis responsáveis pelo menu de seleção do protocolo de codificação
  @FXML
  private SplitMenuButton menu;
  @FXML
  private MenuItem binario;
  @FXML
  private MenuItem manchester;
  @FXML
  private MenuItem manchesterDiferencial;

  // Variável responsável pela barra de progresso
  @FXML
  private ProgressBar progressBar;

  // Declaração das variáveis que representam as ondas binárias da interface
  // gráfica
  @FXML
  private ImageView onda_1;
  @FXML
  private ImageView onda_2;
  @FXML
  private ImageView onda_3;
  @FXML
  private ImageView onda_4;
  @FXML
  private ImageView onda_5;
  @FXML
  private ImageView onda_6;

  /*
   * Método: enviar
   * Função: Enviar a mensagem escrita na TextBox
   * Parametros: String mensagem
   * Retorno: void;
   */

  public void enviar(String mensagem) {
    // Método para enviar a mensagem escrita na TextBox
    if (mensagem.isEmpty()) {
      // Abrir uma janela de alerta contendo a mensagem "O campo de texto está vazio!"
      Alerta();
    } else {
      menu.setDisable(false);
      addMesage(mensagem); // Adiciona a mensagem na TextArea
      String bitSequence = messageToBinary(mensagem); // Faz a conversão da mensagem para binário
      int protocol = AplicacaoTransmissora.getTipoDeCodificacao(); // Retorna o protocolo de codificação
      updateImageViews(bitSequence, protocol); // Atualiza as imagens das ondas binárias
      binary.setText(bitSequence); // Adiciona a mensagem binária na TextArea
      new Thread() {
        public void run() {
          try {
            Thread.sleep(1000);
            AplicacaoTransmissora.aplicacaoTransmissora(mensagem); // Envia a mensagem
          } catch (Exception e) {
            System.out.println("Erro ao Enviar a Mensagem");
          } // Fim do bloco try/catch
        } // Fim do método run
      }.start(); // Inicia a Thread e fim da Thread
    } // Fim do bloco if/else
  } // Fim do método enviar

  /*
   * Método: messageToBinary
   * Função: Converter a mensagem para binário
   * Parametros: String message
   * Retorno: String;
   */
  public String messageToBinary(String message) {
    StringBuilder binary = new StringBuilder(); // Cria um StringBuilder para armazenar a mensagem binária
    for (char c : message.toCharArray()) { // Percorre cada caractere da mensagem
      binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
    } // Fim do bloco for
    return binary.toString();
  } // Fim do método messageToBinary

  /*
   * Método: updateImageViews
   * Função: Atualizar as imagens das ondas binárias
   * Parametros: String bitSequence, int protocol
   * Retorno: void;
   */
  public void updateImageViews(String bitSequence, int protocol) {
    // Cria uma fila de ImageViews para armazenar as imagens das ondas binárias
    Queue<ImageView> imageViewQueue = new LinkedList<>();
    // Adiciona as imagens das ondas binárias na fila
    imageViewQueue.add(onda_1);
    imageViewQueue.add(onda_2);
    imageViewQueue.add(onda_3);
    imageViewQueue.add(onda_4);
    imageViewQueue.add(onda_5);
    imageViewQueue.add(onda_6);

    // Cria uma transição de pausa para controlar o tempo de transmissão das ondas
    PauseTransition pause = new PauseTransition(Duration.seconds(0.2));

    // Cria um iterador para percorrer a sequência de bits
    Iterator<Character> bitIterator = bitSequence.chars().mapToObj(c -> (char) c).iterator();

    // Define o evento que será executado quando a transição de pausa terminar
    pause.setOnFinished(event -> {
      if (bitIterator.hasNext()) {
        char bit = bitIterator.next();

        ImageView imageView = imageViewQueue.poll(); // Remove a primeira ImageView da fila

        // Laço para atualizar a ImageView das ondas de acordo com o protocolo
        // selecionado
        // Nesse caso apaenas para os protocolos binário e manchester
        if (protocol == AplicacaoTransmissora.BINARIA) {
          imageView.setImage(new ImageView(bit == '0' ? "binario-0.png" : "binario-1.png").getImage());
        } else if (protocol == AplicacaoTransmissora.MANCHESTER) {
          imageView.setImage(new ImageView(bit == '0' ? "baixo-alto.png" : "alto-baixo.png").getImage());
        }

        // Adiciona a ImageView atualizada na fila
        imageViewQueue.add(imageView);

        // Reinicia a transição de pausa
        pause.playFromStart();
      } else {
        // Limpa a fila de imagens após o fim da transmissão de dados
        for (ImageView imageView : imageViewQueue) {
          imageView.setImage(null);
        } // Fim do bloco for
      } // Fim do bloco if/else
    }); // Fim do evento setOnFinished
    pause.play();
  } // Fim do método updateImageViews

  /*
   * Método: enviarButton
   * Função: Enviar a mensagem escrita na TextBox
   * Parametros: ActionEvent event
   * Retorno: void;
   */
  public void enviarButton(ActionEvent event) {

    if (transmissor != null && transmissor.getText() != null && !transmissor.getText().isEmpty()) {
      String message = transmissor.getText(); // Pega a mensagem escrita na TextBox
      this.transmissor.setText(""); // Limpa a TextBox

      // Cria uma task para simular o tempo de encio da mensagem
      Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          // Variavel que armazena o tamanho da mensagem para estabelecer o tempo de
          // transmissão
          int totalWork = message.length();
          for (int i = 0; i < totalWork; i++) {
            // Simula o trabalho de transmissão
            Thread.sleep(1000);

            // Atualiza o progresso da barra de progresso
            updateProgress(i + 1, totalWork);
          } // Fim do bloco for
          return null; // Retorna null
        } // Fim do método call
      }; // Fim da task

      // Atualiza a barra de progresso de acordo com o progresso da task
      progressBar.progressProperty().bind(task.progressProperty());

      task.setOnSucceeded(e -> {
        // Quando a task finaliza, significa que a barra de progresso chegou a 100% e a
        // mensagem é enviada
        this.enviar(message);
      }); // Fim do evento setOnSucceeded
      // Cria uma nova Thread para executar a task
      new Thread(task).start();
    } else {
      Alerta();
    } // Fim do bloco if/else
  } // Fim do método enviarButton

  /*
   * Método: addMesage
   * Função: Adicionar a mensagem na TextArea
   * Parametros: String mensagem
   * Retorno: void;
   */

  public void addMesage(String mensagem) {
    String oldText = receptor.getText();
    receptor.setText(oldText + mensagem + "\n\n");
    receptor.appendText("");
  } // Fim do método addMesage

  /*
   * Método: Alerta
   * Função: Exibir uma janela de alerta para caso o campo de texto esteja vazio
   * Parametros: void
   * Retorno: void;
   */
  public void Alerta() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Erro");
    alert.setHeaderText(null);
    alert.setContentText("Erro, o campo de texto está vazio!");
    alert.showAndWait();
  } // Fim do método Alerta

  /*
   * Método: initialize
   * Função: Inicializar a interface gráfica
   * Parametros: URL location, ResourceBundle resources
   * Retorno: void;
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    binario.setOnAction(event -> AplicacaoTransmissora.setTipoDeCodificacao(AplicacaoTransmissora.BINARIA));
    manchester.setOnAction(event -> AplicacaoTransmissora.setTipoDeCodificacao(AplicacaoTransmissora.MANCHESTER));
    manchesterDiferencial
        .setOnAction(event -> AplicacaoTransmissora.setTipoDeCodificacao(AplicacaoTransmissora.MANCHESTER_DIFERENCIAL));

  } // Fim do método initialize
} // Fim da classe telaController
