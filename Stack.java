package pack;

public class Stack<T> extends LinkedList<T> {
	public Stack() {
		// TODO Auto-generated constructor stub
	}

	public Stack(Node<T> head) {
		super(head);
		// TODO Auto-generated constructor stub
	}

	public Stack(T[] array) {
		super(array);
		// TODO Auto-generated constructor stub
	}

	public void add(T data) {
		return super.insert(0, data);
	}
	
	public T remove() {
		return super.RemoveFirst();
	}
}
