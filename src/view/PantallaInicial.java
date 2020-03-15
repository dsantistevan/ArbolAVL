/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import colores.GamaColores;
import java.security.SecureRandom;
import java.util.Comparator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modelo.ArbolAVL;

/**
 *
 * @author David Santistevan
 */
public class PantallaInicial {
    private final BorderPane root;
    private final BTView arbolGrafico;
    private final HBox funciones;
    private SeccionBoton[] secciones;
    private Label error;
    private Label retroalimentacion;
    private final ScrollPane sp;
    private Label titulo;
    private BorderPane top;
    private static final String ERRORNUM= "Ingrese un valor numérico por favor";
    
    public BorderPane getRoot(){
        return root;
    }
    
    public PantallaInicial(){
        arbolGrafico=new BTView((ArbolAVL<Integer>) new ArbolAVL(Comparator.naturalOrder()));
        root=new BorderPane();
        funciones=getFunciones();
        root.setTop(getTop());
        actualizarFondoTop(arbolGrafico.getGama());
        sp=new ScrollPane();
        sp.setContent(arbolGrafico);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        root.setCenter(sp);
        root.setRight((new GamasPane()).root);
        root.setBottom(getBottom());
        activarBotones();
        
    }
    
    private HBox getFunciones(){
        secciones=new SeccionBoton[3];
        secciones[0]=new SeccionBoton("Agregar");
        secciones[1]=new SeccionBoton("Eliminar");
        secciones[2]=new SeccionBoton("Buscar");
        HBox hb=new HBox();
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.setSpacing(40);
        for(int i=0;i<3;i++){
            hb.getChildren().add(secciones[i].getRoot());
        }
        
        hb.setPadding(new Insets(10, 0, 10, 50));
        return hb;
    }
    
    private BorderPane getTop(){
        BorderPane bp=new BorderPane();
        bp.setBottom(funciones);
        bp.setCenter(getTitulo());
        bp.setRight(getRightTop());
        top=bp;
        return bp;
    }
    
    private VBox getRightTop(){
        VBox vb=new VBox();
        Button nuevo=new Button("Rellenar");
        activarRelleno(nuevo);
        Button vaciar=new Button("Vaciar");
        vaciar.setOnAction(e ->{
            arbolGrafico.getTree().vaciar();
            actualizar();
        });
        vb.getChildren().addAll(nuevo,vaciar);
        vb.setPadding(new Insets(50,50,0,0));
        vb.setSpacing(30);
        vb.setAlignment(Pos.CENTER);
        return vb;
    }
    
    private VBox getTitulo(){
        titulo=new Label("Árbol AVL");
        titulo.setMinHeight(80);
        VBox vb=new VBox(titulo);
        vb.setPadding(new Insets(10,0,10,50));
        titulo.setFont(Font.font("Arial Bold Italic",FontWeight.BOLD, 60));
        vb.setAlignment(Pos.CENTER_LEFT);
        return vb;
    }
    
    private void activarRelleno(Button nuevo){
        nuevo.setOnAction(e ->{
            Thread t=new Thread(new RellenoRunnable());
            t.setDaemon(true);
            t.start();
            
            nuevo.setOnAction(ev ->{
                t.interrupt();
                activarRelleno(nuevo);
            });
        });
    }
    
    private void actualizarFondoTop(Color c,Color c2){
        top.setStyle("-fx-background-color:" + getStringColor(c) + ";");
        titulo.setTextFill(c2);
        for(SeccionBoton sb:secciones){
            sb.boton.setStyle("-fx-border-color: "+getStringColor(c2)+"; -fx-border-width: 2px;");
        }
    }
    
    private void actualizarFondoTop(GamaColores gc){
        actualizarFondoTop(gc.getRelleno(), gc.getBorde());
    }
    
    private String getStringColor(Color c){
        return "#" + c.toString().substring(2, 8);
    }
    
    private VBox getBottom(){
        error=new Label("");
        error.setTextFill(Color.RED);
        retroalimentacion=new Label("");
        VBox hb=new VBox();
        hb.getChildren().addAll(error,retroalimentacion);
        hb.setAlignment(Pos.CENTER);
        return hb;
    }
    
    private void actualizar(){
        arbolGrafico.mostrarArbol();
        sp.setFitToWidth(arbolGrafico.getGrosor()<=sp.getWidth());
        sp.setFitToHeight(arbolGrafico.getAltura()<=sp.getHeight());
            
    }
    
    private void activarBotonAgregar(){
        secciones[0].getBoton().setOnAction(e ->{
            try{
                error.setText("");
                int valor=Integer.valueOf(secciones[0].getValor());
                if(arbolGrafico.getTree().add(valor)) retroalimentacion.setText("Se agregó el número "+valor);
                else error.setText("No se pudo agregar el número "+valor);
                actualizar();
                secciones[0].clear();
            }catch(NumberFormatException nfe){
                error.setText(ERRORNUM);
            }
        });
    }
    
    private void activarBotonBorrar(){
        secciones[1].getBoton().setOnAction(e ->{
            try{
                error.setText("");
                int valor=Integer.valueOf(secciones[1].getValor());
                if(arbolGrafico.getTree().remove(valor)) retroalimentacion.setText("Se eliminó el número "+valor);
                else error.setText("No se pudo eliminar el número "+valor);
                actualizar();
                secciones[1].clear();
            }catch(NumberFormatException nfe){
                error.setText(ERRORNUM);
            }
        });
    }
    
    private void activarBotonBuscar(){
        secciones[2].getBoton().setOnAction(e ->{
            try{
                error.setText("");
                int valor=Integer.valueOf(secciones[2].getValor());
                if(arbolGrafico.getTree().contains(valor)) retroalimentacion.setText("Se encontró el número "+valor);
                else retroalimentacion.setText("No se encontró el número "+valor);
                actualizar();
                secciones[2].clear();
            }catch(NumberFormatException nfe){
                error.setText(ERRORNUM);
            }
        });
    }
    
    private void activarBotones(){
        activarBotonAgregar();
        activarBotonBorrar();
        activarBotonBuscar();
        for(SeccionBoton s:secciones){
            s.activar();
        }
    }
    
    class GamasPane{
        ScrollPane root;

        public GamasPane(){
            root=new ScrollPane();
            VBox vb=new VBox();
            for(GamaColores gama:GamaColores.getGamas()){
                Rectangle r=new Rectangle(120, 40, gama.getRelleno());
                r.setStroke(gama.getBorde());
                r.setStrokeWidth(5);
                vb.getChildren().add(r);
                r.setOnMouseClicked(e ->{
                    arbolGrafico.setGama(gama);
                    actualizarFondoTop(gama.getRelleno(),gama.getBorde());
                });
            }
            root.setContent(vb);
            root.setFitToHeight(true);
        }
    }
    
    class RellenoRunnable implements Runnable{

        @Override
        public void run() {
            boolean b=true;
            do{
                int valor = (new SecureRandom()).nextInt(250);
                Platform.runLater(() ->
                {
                    if(arbolGrafico.getTree().add(valor)) retroalimentacion.setText("Se agregó el número "+valor);
                    else error.setText("No se pudo agregar el número "+valor);
                    actualizar();
                });
                try {
                    Thread.sleep(150);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    b=false;
                }
                
            }while(arbolGrafico.getTree().getRoot() !=null && b);
        }
        
    }
    
}

