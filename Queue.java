package pack;

public class Queue<T> extends LinkedList<T> {
	private Node<T> rear;
	
	public Queue() {
		super();
		this.rear = null;
	}
	
	public void add(T data) {
		if (this.head == null) {
			this.head = new Node<T>(data);
			this.rear = this.head;
		}
		else {
			this.rear.next = new Node<T>(data);
			this.rear = this.rear.next;
		}
	}
	
	public T remove(T data) {
		if (this.head == null) {
			return null;
		}
		else {
			Node<T> node = this.head;
			
			if (node == this.rear) {
				this.head = null;
				this.rear = null;
			}
			else {
				this.head = node.next;
			}
			return node.data;
		}
	}	
	//Queues for patrons
	//stacks for books
	//
}
