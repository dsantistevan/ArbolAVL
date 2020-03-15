/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

/**
 *
 * @author David Santistevan
 */
public class SeccionBoton {
    protected HBox root;
    protected TextField campo;
    protected Button boton;
    
    public SeccionBoton(String nom) {
        root = new HBox();
        campo = new TextField();
        campo.setMaxWidth(50);
        boton=new Button(nom);
        root.setSpacing(7);
        root.getChildren().addAll(campo,boton);
        
    }
    
    public HBox getRoot() {
        return root;
    }
    
    public void setRoot(HBox root) {
        this.root = root;
    }
    
    
    public TextField getCampo() {
        return campo;
    }
    
    public void setCampo(TextField campo) {
        this.campo = campo;
    }
    
    public Button getBoton() {
        return boton;
    }
    
    public void setBoton(Button nombre) {
        this.boton = nombre;
    }
    
    
    public void clear() {
        campo.setText("");
    }
    
    public String getValor(){
        return campo.getText();
    }
    
    public void setValor(String s){
        campo.setText(s);
    }
    
    public void activar(){
        campo.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.ENTER))
                boton.fire();
        });
    }
}
