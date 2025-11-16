package csi213_project03;

public class RecentlyPlayedQueue {
	
	private static class Node {
		
		Song data;
		Node next;
		
		Node(Song s) {
			data = s;
			}
	}
	
	private Node head;
	private Node tail;
	private int size;
	private final int capacity;
	
	public RecentlyPlayedQueue() {
		this(10);
	}
	
	public RecentlyPlayedQueue(int capacity) {
		this.capacity =  (capacity <= 0 ? 10 : capacity);
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	public void addRecentlyPlayed(Song song) {
		if (song == null) return;
		Node node = new Node(song);
		if (tail == null) {
			head = tail = node;
		} else {
			tail.next = node;
			tail = node;
		}
		size++;
		
		while (size > capacity) {
			if (head != null) {
				head = head.next;
				size--;
				if (head == null) tail = null;
			}
		}
	}
	
	public void printRecentlyPlayed() {
		System.out.println("--- Recently Played (newest last) ---");
		if (head == null) {
			System.out.println("(none)");
			return;
		}
		int idx = 1;
		Node cur = head;
		while (cur != null) {
			System.out.println(idx + ". " + cur.data.toString());
			cur = cur.next;
			idx++;
		}
	}

}
