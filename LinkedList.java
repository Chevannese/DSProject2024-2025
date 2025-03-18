//package pack;
// Jonathan Blackwood (2306822), 20/2/2025, LinkedList.java


//package  pack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements java.io.Serializable, Iterable<T> {
	private static final long serialVersionUID = 1L;
	protected Node<T> head;
    
    public LinkedList() {
        this.head = null;
    }
    
    public LinkedList(Node<T> head) {
        this.head = head;
    }
    
    public LinkedList(Iterable<T> items) {
        this.head = null;
        
        for (T item: items) {
            this.append(item);
        }
    }
    
    public int len() {
        Node node = this.head;
        int len = 0;
        
        while (node != null) {
            node = node.next;
            len += 1;
        }
        
        return len;
    }
    
    public T get(int n) {
        if (this.head == null) {
            return null;
        }
        
        return this.node(n).data;
    }
    
    public T first() {
        if (this.head == null) {
            return null;
        }
        
        return this.head.data;
    }
    
    public T last() {
        if (this.head == null) {
            return null;
        }
        
        Node<T> node = this.head;
        
        while (node != null) {
            node = node.next;
        }
        
        return node.data;
    }
    
    public void append(T data) {
        if (this.head == null) {
            this.head = new Node(data);
        }
        else {
            Node node = this.head;
            
            while (node.next != null) {
                node = node.next;
            }
            
            node.next = new Node(data);
        }
    }
    
    public void insert(T data, int n) {
        if (n == 0) {
            this.head = new Node(data);
        }
        else {
            Node<T> node = this.head;
            Node<T> prev = null;
            int i = 0;
            
            while (i < n) {
                prev = node;
                node = node.next;
                i += 1;
            }
            
            Node<T> insert = new Node(data);
            prev.next = insert;
            insert.next = node;
        }
    }
    
    public T RemoveFirst() {
        if (this.head == null) {
            return null;
        }
        
        Node<T> node = this.head;
        
        this.head = node.next;
        return node.data;
    }
    
    public T RemoveLast() {
        Node<T> node = this.head;
        Node<T> prev = null;
        
        if (this.head == null) {
            return null;
        }
        
        while (node.next != null) {
            prev = node;
            node = node.next;
        }
        
        prev.next = null;
        return node.data;
    }
    
    public T Remove(int n) {
        Node<T> node = this.head;
        Node<T> prev = null;
        int i = 0;
        
        if (this.head == null) {
            return null;
        }
        
        while (i < n && node.next != null) {
            prev = node;
            node = node.next;
            i += 1;
        }
        
        prev.next = null;
        return node.data;
    }
    
    public T Remove(String name) {
        Node<T> node = this.head;
        Node<T> prev = null;

        // If the list is empty, return null
        if (this.head == null) {
            return null;
        }

        // Traverse the list to find the node with the matching data
        while (node != null) {
            if (node.data.equals(name)) {
                // If the node to be removed is the head
                if (prev == null) {
                    this.head = node.next;
                } else {
                    prev.next = node.next;
                }
                return node.data;
            }
            prev = node;
            node = node.next;
        }

        // If the target is not found, return null
        return null;
    }
    
    
    
    private Node<T> node(int n) {
        Node<T> node = this.head;
        int i = 0;
        
        while (i < n) {
            node = node.next;
            i += 1;
        }
        
        return node;
    }
    
    public String toString() {
        String output = new String();
        Node<T> node = this.head;
        
        while (node != null) {
            output += node.data + ", ";
            node = node.next;
        }
        
        if (output.length() >= 2) {
            return output.substring(0, output.length() - 2);
        }
        
        return output;
    }
    
    
    public LinkedListIter<T> iterator() {
        return new LinkedListIter(this.head);
    }
    
    public void Display() {
        System.out.println(this.toString());
    }
}

class LinkedListIter<T> implements Iterator<T> {
    private Node<T> node;
    
    public LinkedListIter(Node<T> head) {
        this.node = head;
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

