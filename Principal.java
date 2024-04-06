/* ***************************************************************
 * Autor............: Guilherme Dias Sousa
 * Matricula........: 202211033
 * Inicio...........: 20/03/2024    
 * Ultima alteracao.: 06/04/2024
 * Nome.............: Principal
 * Funcao...........: Aplicação Main que inicia o de transmissão e recepção de dados da camada física	
 *************************************************************** */

import javafx.application.Application; // Importa a classe Application do pacote javafx.application
import javafx.fxml.FXMLLoader; // Importa a classe FXMLLoader do pacote javafx.fxml
import javafx.scene.Parent; // Importa a classe Parent do pacote javafx.scene
import javafx.scene.Scene; // Importa a classe Scene do pacote javafx.scene
import javafx.scene.image.Image; // Importa a classe Image do pacote javafx.scene.image
import javafx.stage.Stage; // Importa a classe Stage do pacote javafx.stage
import view.telaController; // Importa a classe telaController do pacote view

/*
 * Método: start
 * Função: Iniciar a aplicação
 * Parametros: Stage primaryStage
 * Retorno: void;
 */
public class Principal extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(Principal.class.getResource("view/tela.fxml")); // Carrega o arquivo fxml
    Parent root = loader.load();
    telaController tela = new telaController(); // Instancia a classe telaController
    loader.setController(tela);

    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.getIcons().add(new Image("pc_transmissor.png")); // Adiciona o icone da aplicação
    stage.setTitle("Camada Física da Computação - Projeto 1"); // Define o titulo da aplicação
    stage.show();
  } // Fim do método start

  /*
   * Método: main
   * Função: Iniciar a aplicação
   * Parametros: String[] args
   * Retorno: void;
   */
  public static void main(String[] args) {
    launch(args);
  } // Fim do método main
}