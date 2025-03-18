// Jonathan Blackwood (2306822), 20/2/2025, Node.java

//package pack


class Node<T> implements java.io.Serializable {
	public static final String ELEMENT_NODE = null;
	public T data;
	public Node next;
	public Node left;
	public Node right;

	public Node() {
		this.data = null;
		this.next = null;
		this.right = null;
		this.left = null;
	}

	public Node(T data) {
		this.data = data;
		this.next = null;
		this.left = null;
		this.right = null;
	}

	public void display() {
		System.out.println(this.toString());
	}

	public String toString() {
		return String.valueOf(this.data);
	}
}

