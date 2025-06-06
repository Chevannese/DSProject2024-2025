/*Jonathan Blackwood 2306822  20/2/2025, Node.java
*/
class Node<T> {
    public static final String ELEMENT_NODE = null;
	public T data;
    public Node next;
    
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
    
    public void display() {
        System.out.println(this.toString());
    }
    
    public String toString() {
        return String.valueOf(this.data);
    }
    
    
}
