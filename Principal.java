import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.telaController;

public class Principal extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Principal.class.getResource("view/tela.fxml"));
        Parent root = loader.load();
        telaController tela = new telaController();
        loader.setController(tela);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sistema Shinobi de Controle de Missões");
        stage.show();
    }
    public static void main(String[] args) {
        System.out.println ("Hola Mundo!");
        launch(args);
        
    }
}