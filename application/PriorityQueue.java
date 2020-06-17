package application;
import java.util.NoSuchElementException;


public class PriorityQueue implements IPriorityQueue {

	Node header;
	int size;
	
	PriorityQueue(){
        header = new Node(null,null,0);
		size = 0;
	}
	
	@Override
	public void insert(Object item, int key) {
		Node node = new Node(item,null,key);
        if(header.getNext() == null) {
        	header.setNext(node);
        }
        else {
        	Node temp = header.getNext();
        	Node prev = header;
        	while(temp.getNext()!=null) {
        		if(key <= temp.getKey()) {
        			break;
        		}
        		prev = temp;
        		temp = temp.getNext();
        	}
        	if(key < temp.getKey()) {
        		prev.setNext(node);
        		node.setNext(temp);
        	}
        	else {
        		node.setNext(temp.getNext());
        		temp.setNext(node);
        	}
        }
        size++;
	}

	@Override
	public Object removeMin() {
		if(header.getNext() == null) {
			throw new NoSuchElementException("Empty priority queue");
		}
        Node temp = header.getNext();
        Object min = temp.getValue();
        header.setNext(temp.getNext());
		return min;
	}

	@Override
	public Object min() {
		if(header.getNext() == null) {
			throw new NoSuchElementException("Empty priority queue");
		}
        Node temp = header.getNext();
        Object min = temp.getValue();
		return min;
	}

	@Override
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

}
