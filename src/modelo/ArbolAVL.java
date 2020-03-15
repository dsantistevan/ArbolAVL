/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Comparator;

/**
 *
 * @author David Santistevan
 * @param <E>
 */
public class ArbolAVL<E> {
    
    Node<E> root;
    Comparator<E> f;
    int efectivo;
    
    public ArbolAVL(Comparator<E> c){
        f=c;
        root=null;
    }
    
    public boolean isEmpty(){
        return root==null;
    }
    
    public void vaciar(){
        root=null;
    }
    
    private Node<E> rotacionII(Node<E> n){
        Node<E> tmp=n.getLeft();
        n.setLeft(tmp.getRight());
        tmp.setRight(n);
        n.actualizarAltura();
        tmp.actualizarAltura();
        return tmp;
    }
    
    private Node<E> rotacionDD(Node<E> n){
        Node<E> tmp=n.getRight();
        n.setRight(tmp.getLeft());
        tmp.setLeft(n);
        n.actualizarAltura();
        tmp.actualizarAltura();
        return tmp;
    }
    
    private Node<E> rotacionID(Node<E> n){
        n.setLeft(rotacionDD(n.getLeft()));
        return rotacionII(n);
    }
    
    private Node<E> rotacionDI(Node<E> n){
        n.setRight(rotacionII(n.getRight()));
        return rotacionDD(n);
    }
    
    private Node<E> balancear(Node<E> n){
        n.actualizarAltura();
        int balance=n.getBalance();
        if(balance<-1 && n.getLeft().getBalance()<=0) return rotacionII(n);         //peso a la izquierda y el nodo de la izquierda tiene balance negativo
        else if(balance>1 && n.getRight().getBalance()>=0) return rotacionDD(n);    //peso a la derecha y el nodo de la derecha tiene balance positivo
        else if( balance<-1 && n.getLeft().getBalance()>0) return rotacionID(n);    //peso a la izquierda y el nodo de la izquierda tiene balance positivo
        else if(balance>1 && n.getRight().getBalance()<0) return rotacionDI(n);     //peso a la derecha y el nodo de la derecha tiene balance negativo
        return n;
    }
    
    public boolean add(E element){
        int num=efectivo;
        if(element==null)
            return false;
        root=add(element,root);
        efectivo++;
        return num < efectivo;  //Si tienen el mismo valor, el efectivo no cambia y se retorna falso
    }
    
    private Node<E> add(E e,Node<E> n){
        if(n==null)
            n= new Node(e);
        else if(f.compare(e, n.getValor())<0){
            n.setLeft(add(e,n.getLeft()));
        }else if(f.compare(e, n.getValor())>0){
            n.setRight(add(e,n.getRight()));
        }else
            efectivo--;
        
        
        return balancear(n);
    }
    
    
    public boolean contains(E element){
        return contains(element,root);
    }
    
    private boolean contains(E e,Node<E> n){
        if(n==null)
            return false;
        else if(f.compare(e, n.getValor())<0){
            return contains(e,n.getLeft());
        }else if(f.compare(e, n.getValor())>0){
            return contains(e,n.getRight());
        }else
            return true;
    }
    
    
    public E min(){
        return min(root);
    }
    
    private E min(Node n){
        if(n!=null&&n.getLeft()==null)
            return (E) n.getValor();
        return n==null ? null : min(n.getLeft());
    }
    
    public E max(){
        return max(root);
    }
    
    private E max(Node n){
        if(n!=null&&n.getRight()==null)
            return (E) n.getValor();
        return n==null ? null : max(n.getRight());
    }
    
    public boolean remove(E element){
        int num=efectivo;
        if(element==null||isEmpty()) return false;
        root=remove(element,root);
        efectivo--;
        return num > efectivo;  //Si el elemento no está, el efectivo no cambia y se retorna falso
    }
    
    private Node<E> remove(E e,Node<E> n){
        if(n==null){
            efectivo++;
            return n;
        }
        else if(f.compare(e, n.getValor())<0)
            n.setLeft(remove(e,n.getLeft()));
        else if(f.compare(e, n.getValor())>0)
            n.setRight(remove(e,n.getRight()));
        else if(n.isFull()){
            n.setValor(max(n.getLeft()));
            n.setLeft(remove(n.getValor(),n.getLeft()));
        }else
            n = (n.getLeft()!=null) ? n.getLeft() : n.getRight();
        return n==null ? null : balancear(n);
        
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.root.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(!(obj instanceof ArbolAVL))
            return false;
        final ArbolAVL<?> other = (ArbolAVL<?>) obj;
        return this.root.equalsTree(other.root);
    }
    
    public Node<E> getRoot(){
        return root;
    }
}
