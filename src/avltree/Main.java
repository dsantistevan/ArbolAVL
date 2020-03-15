/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.PantallaInicial;

/**
 *
 * @author David Santistevan
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Scene scene = new Scene((new PantallaInicial()).getRoot(), 800, 600);
        
        primaryStage.setTitle("Arbol AVL");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(new String[1]);
    }
    
}
