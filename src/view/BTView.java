/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import colores.GamaAzul;
import colores.GamaColores;
import modelo.Node;
import modelo.ArbolAVL;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author David Santistevan
 * @param <E>
 */
public final class BTView<E> extends Pane {
    private final ArbolAVL<E> tree;
    private static final double RADIO = 15; // Radio del nodo
    private static final double VGAP = 50; //Separacion vertical
    private GamaColores gama;
    private double grosor;
    private double altura;
    private static final String ARBOLVACIO="El arbol esta vacio.";
    
    public BTView(ArbolAVL<E> tree) {
        gama=new GamaAzul();
        this.tree = tree;
        setStatus(ARBOLVACIO);
    }
    
    public void setStatus(String msg) {
        Label l=new Label(msg);
        l.setLayoutX(20);
        l.setLayoutY(20);
        getChildren().add(l);
    }
    
    public void mostrarArbol() {
        this.getChildren().clear(); // Elimina los elementos
        if (!tree.isEmpty()) {
            // Muestra el arbol de forma recursiva
            if(getWidth()/Math.pow(2,tree.getRoot().getAltura())>RADIO*1.5)
                grosor=getWidth();
            else
                grosor=RADIO*Math.pow(2,tree.getRoot().getAltura())*1.5;
            mostrarArbol(tree.getRoot(), grosor/2, 0+VGAP, grosor/4);
            altura=tree.getRoot().getAltura()*(VGAP+2*RADIO);
        }
        else{
            setStatus(ARBOLVACIO);
            grosor=getWidth();
            altura=getHeight();
        }
    }

    public double getGrosor(){
        return grosor;
    }
    
    public double getAltura(){
        return altura;
    }
    
    /* Muestra el subarbol en la posicion (x, y) */
    private void mostrarArbol(Node<E> nodo, double x, double y,double hGap) {
        if (nodo.hasLeft()) {
            // Dibuja una linea hacia el nodo izquierdo
            Line line=new Line(x - hGap, y + VGAP, x, y);
            line.setStroke(gama.getBorde());
            getChildren().add(line);
            // Dibuja el arbol izquierdo de forma recursiva
            mostrarArbol(nodo.getLeft(), x - hGap, y + VGAP, hGap / 2);
        }
        if (nodo.hasRight()) {
            // Dibuja una linea hacia el nodo derecho
            Line line=new Line(x + hGap, y + VGAP, x, y);
            line.setStroke(gama.getBorde());
            getChildren().add(line);
            // Dibuja el arbol derecho de forma recursiva
            mostrarArbol(nodo.getRight(), x + hGap, y + VGAP, hGap / 2);
        }
        // Dibuja el nodo respectivo
        Circle circle = new Circle(x, y, RADIO);
        circle.setFill(gama.getRelleno());
        circle.setStroke(gama.getBorde());
        String s=nodo.getValor().toString();
        int i= s.length()>2 ? 2+s.length() : 2;
        getChildren().addAll(circle, new Text(x - i*2, y + 4, s));
    }
    
    public ArbolAVL<E> getTree(){
        return tree;
    }
    
    public void setGama(GamaColores gc){
        gama=gc;
        mostrarArbol();
    }
    
    public GamaColores getGama(){
        return gama;
    }
}