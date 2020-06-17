package application;


public class Node {

	private Object element;
	private Node next;
	private int key;
	
	public Node(Object element,Node next,int key) {
		this.element = element;
		this.next = next;
		this.key = key;
	}
	public void setValue(Object element) {
		this.element = element;
	}
	
	public void setNext(Node next) {
		this.next = next;
	}
	
	public void setkey(int key) {
		this.key = key;
	}
	
	public Object getValue() {
		return this.element;
	}
	
	public Node getNext() {
		return this.next;
	}
	
	public int getKey() {
		return this.key;
	}
}
