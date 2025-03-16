// Jonathan Blackwood (2306822), 15/3/2025, Queue.java
import java.util.*;

public class Queue<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int len;
    
    public Queue() {
        this.first = null;
        this.last = null;
        this.len = 0;
    }
    
    public int len() {
        return this.len;
    }
    
    public void add(T data) {
        Node<T> node = new Node(data);
        
        if (this.last == null || this.first == null) {
            this.last = node;
            this.first = node;
        }
        else {
            this.last.next = node;
            this.last = node;
        }
        
        this.len += 1;
    }
    
    public T next() {
        if (this.first == null) {
            return null;
        }
        else {
            Node<T> node = this.first;
            if (this.first == this.last) {
                this.first = null;
                this.last = null;
            }
            else {
                this.first = this.first.next;
            }
            
            len -= 1;
            return node.data;
        }
    }
    
    public QueueItertor iterator() {
        return new QueueItertor(this.first);
    }
}

class QueueItertor<T> implements Iterator<T> {
    private Node<T> node;
    
    public QueueItertor(Node<T> first) {
        this.node = first;
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
