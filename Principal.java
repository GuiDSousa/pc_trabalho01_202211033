import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.telaController;

public class Principal extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Principal.class.getResource("view/tela.fxml"));
        Parent root = loader.load();
        telaController tela = new telaController();
        loader.setController(tela);
        // colocar icone na janela
        

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("pc_transmissor.png"));
        stage.setTitle("Camada Física da Computação - Projeto 1");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}