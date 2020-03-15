/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author David Santistevan
 * @param <E>
 */
public class Node<E> {
    private E valor;
    private Node<E> left;
    private Node<E> right;
    private int altura;
    

    public Node(E element){
        valor=element;
        altura=1;
    }


    public boolean isFull(){
        return left!=null && right != null;
    }

    public boolean isEmpty(){
        return left==null && right==null;
    }

    
    public boolean hasLeft(){
        return left!=null;
    }
    
    public boolean hasRight(){
        return right!=null;
    }



    public boolean equalsTree(Object obj) {
        if (this == obj) {
            return true;
        }
        if(!(obj instanceof Node))
            return false;
        final Node<?> other = (Node<?>) obj;
        if(!this.valor.equals(other.valor))
            return false;
        if(this.isEmpty()&&other.isEmpty())
            return true;
        else if (this.isEmpty()||other.isEmpty())
            return false;
        return this.left.equalsTree(other.left) && this.right.equalsTree(other.right);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(!(obj instanceof Node))
            return false;
        final Node<?> other = (Node<?>) obj;
        return this.valor.equals(other.valor);

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.valor);
        return hash;
    }
    
    

    public E getValor() {
        return valor;
    }


    public Node<E> getLeft() {
        return left;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
    }

    public Node<E> getRight() {
        return right;
    }

    public void setRight(Node<E> right) {
        this.right = right;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
    
    public void setValor(E e){
        valor=e;
    }
    
    public int getBalance(){
        if(isEmpty())
            return 0;
        else if (left==null)
            return right.altura;
        else if (right==null)
            return -left.altura;
        else
            return right.altura-left.altura;
    }
   
    public void actualizarAltura(){
        if(isEmpty())
            altura = 1;
        else if (left==null)
            altura = 1 + right.altura;
        else if (right==null)
            altura = 1 + left.altura;
        else
            altura = 1+ Math.max(right.altura,left.altura);
    }
}
