package pack;


public class LinkedList<T> implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Node<T> head;
    // private int len;
    
    public LinkedList() {
        this.head = null;
    }
    
    public LinkedList(Node<T> head) {
        this.head = head;
    }
    
    public LinkedList(T[] array) {
        this.head = null;
        
        for (T item: array) {
            this.append(item);
        }
    }
    
    public int len() {
        /*
        return this.len;
        */
        
        Node node = this.head;
        int len = 0;
        
        while (node != null) {
            node = node.next;
            len += 1;
        }
        
        return len;
    }
    
    public T get(int n) {
        return this.node(n).data;
    }
    
    public T first() {
        return this.head.data;
    }
    
    public T last() {
        /*
        return this.node(this.len - 1).data;
        */
        
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
            
            /*
            this.node(this.len - 1).next = new Node(data);
            */
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
            }
            
            Node<T> ins = new Node(data);
            prev.next = ins;
            ins.next = node;
        }
        
        /*
        if (n == 0) {
            this.head = new Node(data);
        }
        else {
            this
        }
        
        Node<T> prev = this.node()
        */
    }
    
    public T RemoveFirst() {
        Node<T> node = this.head;
        
        this.head = node.next;
        return node.data;
    }
    
    public T RemoveLast() {
        Node<T> node = this.head;
        Node<T> prev = null;
        
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
        
        while (i < n) {
            prev = node;
            node = node.next;
            i += 1;
        }
        
        prev.next = null;
        return node.data;
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
    
    // To loop through each Node that contains objects - Chev
    public void forEach(java.util.function.Consumer<T> action) {
        Node<T> current = head;
        while (current != null) {
            action.accept(current.data);
            current = current.next;
        }
    }
    public void Display() {
        System.out.println(this.toString());
    }
}


