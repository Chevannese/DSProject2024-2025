// Jonathan Blackwood (2306822), 20/2/2025, LinkedList.java

import java.util.*;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> head; // Store the first node of the list. <T> means we can use any data type!

    // Default constructor: creates an empty list.
    public LinkedList() {
        this.head = null; // Start with nothing in the list.
    }

    // Constructor: creates a list with a given head node.
    public LinkedList(Node<T> head) {
        this.head = head; // Start with the given node as the head.
    }

    // Constructor: creates a list from an iterable (like a collection).
    public LinkedList(Iterable<T> items) {
        this.head = null; // Start empty, then add items.

        // Loop through all the items and add them to the end of the list.
        for (T item : items) {
            this.append(item); // Add each item to the end.
        }
    }

    // Returns the number of elements in the list.
    public int len() {
        Node<T> node = this.head; // Start at the beginning.
        int len = 0; // Keep track of the length.

        // Go through each node until we reach the end (null).
        while (node != null) {
            len += 1; // Increment the length.
            node = node.next; // Move to the next node.
        }

        return len; // Return the total length.
    }

    // Jada: Checks if the list is empty.
    public boolean isEmpty() {
        return this.head == null; // If the head is null, the list is empty!
    }

    // Gets the data at a specific index.
    public T get(int n) {
        // Check if the index is valid.
        if (this.head == null || n < 0 || n >= len()) {
            return null; // Return null if the index is out of bounds or the list is empty.
        }

        Node<T> node = this.node(n); // Find the node at the given index.
        return node.data; // Return the data from that node.
    }

    // Gets the data of the first node.
    public T first() {
        if (this.head == null) {
            return null; // Return null if the list is empty.
        }

        return this.head.data; // Return the data of the first node.
    }

    // Gets the data of the last node.
    public T last() {
        if (this.head == null) {
            return null; // Return null if the list is empty.
        }

        Node<T> node = this.head; // Start at the beginning.

        // Go through the list until we reach the last node.
        while (node.next != null) {
            node = node.next; // Move to the next node.
        }

        return node.data; // Return the data of the last node.
    }

    // Adds a new node with the given data to the end of the list.
    public void append(T data) {
        if (this.head == null) {
            this.head = new Node<>(data); // If the list is empty, make the new node the head.
        } else {
            Node<T> node = this.head; // Start at the beginning.

            // Go to the last node.
            while (node.next != null) {
                node = node.next; // Move to the next node.
            }

            node.next = new Node<>(data); // Add the new node to the end.
        }
    }

    // Inserts a new node with the given data at a specific index.
    public void insert(T data, int n) {
        // Check if the index is valid.
        if (n < 0 || n > len()) {
            return;
        }

        if (n == 0) {
            // Insert at the beginning.
            Node<T> newNode = new Node<>(data);
            newNode.next = this.head; // Link the new node to the old head.
            this.head = newNode; // Make the new node the head.
        } else {
            Node<T> node = this.head; // Start at the beginning.
            Node<T> prev = null; // Keep track of the previous node.
            int i = 0; // Counter for the index.

            // Find the node at the insertion point.
            while (i < n) {
                prev = node; // Store the previous node.
                node = node.next; // Move to the next node.
                i += 1;
            }

            // Insert the new node.
            Node<T> insert = new Node<>(data);
            prev.next = insert; // Link the previous node to the new node.
            insert.next = node; // Link the new node to the next node.
        }
    }

    // Removes the first node and returns its data.
    public T RemoveFirst() {
        if (this.head == null) {
            return null; // Return null if the list is empty.
        }

        Node<T> node = this.head; // Store the first node.

        this.head = node.next; // Make the second node the new head.
        return node.data; // Return the data of the removed node.
    }

    // Removes the last node and returns its data.
    public T RemoveLast() {
        if (this.head == null) {
            return null; // Return null if the list is empty.
        }

        if (this.head.next == null) {
            // If there's only one node, remove it.
            T data = this.head.data;
            this.head = null;
            return data;
        }

        Node<T> node = this.head; // Start at the beginning.
        Node<T> prev = null; // Keep track of the previous node.

        // Go to the last node.
        while (node.next != null) {
            prev = node; // Store the previous node.
            node = node.next; // Move to the next node.
        }

        prev.next = null; // Remove the last node.
        return node.data; // Return the data of the removed node.
    }

    // Removes the node at a specific index and returns its data.
    public T Remove(int n) {
        // Basic error check.
        if (n <= 0 || n > this.len()) {
            System.out.println("Error: Index out of bounds.");
            return null;
        }

        Node<T> node = this.node(n);
        Node<T> prev = this.node(n - 1);
        
        if (node == null || prev == null) {
            System.out.println("Error: Node or previous node is null.");
            return null;
        }

        prev.next = node.next;
        return node.data;
    }

    // Removes the first node with the given data and returns its data.
    public T Remove(T data) {
        Node<T> node = this.head;
        Node<T> prev = null;

        while (node != null && !node.data.equals(data)) {
            prev = node;
            node = node.next;
        }

        if (node == null)
            return null;

        if (prev == null)
            this.head = node.next;
        else
            prev.next = node.next;

        return node.data;
    }
    
    public void clear() {
        this.head = null; // Set the head to null, effectively clearing the list
    }
    
    // Returns the node at an index
    private Node<T> node(int n) {
    	if (n < 0) {
    		return null;
    	}
    	
        Node<T> node = this.head;
        int i = 0;
        
        while (i < n && node != null) {
            node = node.next;
            i += 1;
        }
        
        return node;
    }

    // Returns a string representation of the list.
    public String toString() {
        String output = new String();
        Node<T> node = this.head;

        // Loop through the list and add each element to the string.
        while (node != null) {
            output += node.data.toString() + " ";
            node = node.next;
        }

        if (output.length() >= 2) {
            // Remove the trailing space.
            return output.substring(0, output.length() - 1);
        }

        return output; // Return the string.
    }

    // Returns an iterator for the list.
    public LinkedListIter<T> iterator() {
        return new LinkedListIter<>(this.head);
    }

    // Prints the list to the console.
    public void Display() {
        System.out.println(this.toString());
    }
}

// Iterator class for the LinkedList.
class LinkedListIter<T> implements Iterator<T> {
    private Node<T> node;

    // Constructor: initializes the iterator with the head node.
    public LinkedListIter(Node<T> head) {
        this.node = head;
    }

    // Returns the next element in the list.
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(); // Throw an exception if there's no next element.
        }

        Node<T> item = this.node; // Store the current node.
        this.node = item.next; // Move to the next node.
        return item.data; // Return the data of the current node.
    }

    // Checks if there's a next element in the list.
    public boolean hasNext() {
        return this.node != null; // If the current node is not null, there's a next element.
    }
}
