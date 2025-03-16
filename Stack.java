// Jonathan Blackwood (2306822), 15/3/2025, Stack.java
import java.util.*;

public class Stack<T> implements Iterable<T> {
    private Node<T> top;
    
    public Stack() {
        this.top = null;
    }
    
    public void add(T data) {
        Node<T> top = new Node(data);
        
        if (this.top == null) {
            this.top = top;
        }
        else {
            top.next = this.top;
            this.top = top;
        }
    }
    
    public T remove() {
        if (this.top == null) {
            return null;
        }
        else {
            Node<T> top = this.top;
            this.top = top.next;
            return top.data;
        }
    }
    
    public StackItertor<T> iterator() {
        return new StackItertor(this.top);
    }
}

class StackItertor<T> implements Iterator<T> {
    private Node<T> node;
    
    public StackItertor(Node<T> top) {
        this.node = top;
    }
    
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        
        Node<T> item = this.node;
        this.node = item.next;
        return item.data;
    }
    
    public boolean hasNext() {
        return this.node != null;
    }
}
