// Jonathan Blackwood (2306822), 15/3/2025, Queue.java
import java.util.*;

public class Queue<T> implements Iterable<T> {
	private Node<T> first; // The first node in the queue (where we dequeue).
	private Node<T> last;  // The last node in the queue (where we enqueue).
	private int len;       // The number of elements in the queue.

	// Constructor: Creates an empty queue.
	public Queue() {
		this.first = null; // Initially, the queue is empty.
		this.last = null;  // No first or last node.
		this.len = 0;      // Zero elements in the queue.
	}

	// len: Returns the number of elements in the queue.
	public int len() {
		return this.len; // Return the current length.
	}

	// enqueue: Adds an element to the end of the queue.
	public T enqueue(T data) {
		Node<T> node = new Node(data); // Create a new node for the data.

		// If the queue is empty, the new node becomes both the first and last.
		if (this.last == null || this.first == null) {
			this.last = node;
			this.first = node;
		} else {
			// Otherwise, add the new node to the end.
			this.last.next = node; // Link the current last node to the new node.
			this.last = node;      // Make the new node the new last node.
		}

		this.len += 1; // Increment the length of the queue.

		return node.data; // Return the data that was enqueued.
	}

	// dequeue: Removes and returns the element at the front of the queue.
	public T dequeue() {
		if (this.first == null) {
			return null; // Return null if the queue is empty.
		}

		T data = this.first.data; // Store the data of the first node.
		this.first = this.first.next; // Move the first pointer to the next node.

		// If the queue becomes empty after dequeueing, update the last pointer.
		if (this.first == null) {
			this.last = null;
		}

		this.len -= 1; // Decrement the length of the queue.
		return data; // Return the dequeued data.
	}

	// next: Alias for dequeue, removes and returns the first element.
	public T next() {
		return dequeue(); // Just call dequeue.
	}

	// peek: Returns the element at the front of the queue without removing it.
	public T peek() {
		if (this.first == null) {
			return null; // Return null if the queue is empty.
		}
		return this.first.data; // Return the data of the first node.
	}

	// isEmpty: Checks if the queue is empty.
	public boolean isEmpty() {
		return this.first == null; // If the first node is null, the queue is empty.
	}

	// clear: Removes all elements from the queue.
	public void clear() {
		this.first = null; // Set first to null.
		this.last = null;  // Set last to null.
		this.len = 0;      // Reset the length.
	}

	// toString: Returns a string representation of the queue.
	@Override
	public String toString() {
		String result = "[";

		Node<T> current = this.first; // Start at the first node.

		// Loop through the queue and add each element to the string.
		while (current != null) {
			result += current.data; // Add the data to the string.
			if (current.next != null) {
				result += " ";
			}
			current = current.next; // Move to the next node.
		}

		result += "]";

		return result; // Return the string.
	}

	// iterator: Returns an iterator for the queue.
	public QueueIterator<T> iterator() {
		return new QueueIterator(this.first); // Create and return a new QueueIterator.
	}
}

// QueueIterator: An iterator for the Queue class.
class QueueIterator<T> implements Iterator<T> {
	private Node<T> node; // The current node in the iteration.

	// Constructor: Initializes the iterator with the first node of the queue.
	public QueueIterator(Node<T> first) {
		this.node = first;
	}

	// next: Returns the next element in the queue.
	@Override
	public T next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException(); // Throw an exception if there's no next element.
		}

		Node<T> item = this.node; // Store the current node.
		this.node = item.next; // Move to the next node.
		return item.data; // Return the data of the current node.
	}

	// hasNext: Checks if there's a next element in the queue.
	@Override
	public boolean hasNext() {
		return this.node != null; // If the current node is not null, there's a next element.
	}
}
