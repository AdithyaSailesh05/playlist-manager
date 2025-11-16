package csi213_project03;

import java.util.Random;

public class PlaylistLinkedList {
	
	private static class Node {
		Song data;
		Node next;
		Node(Song s) {
			data = s;
		}
	}
	
	private Node head;
	private int size;
	private RecentlyPlayedQueue queue;
	
	public PlaylistLinkedList() {
		head = null;
		size = 0;
	}
	
	public void setRecentlyPlayedQueue(RecentlyPlayedQueue queue) {
		this.queue = queue;
	}
	
	public void addSong(Song song) {
		if (song == null) return;
		Node n = new Node(song);
		if (head == null) {
			head = n;
		} else {
			Node cur = head;
			while (cur.next != null) cur = cur.next;
			cur.next = n;
			}
		size ++;
		}
	
	public void insertSong(int index, Song song) {
		if (song == null) return;
		if (index < 0 || index > size) {
			System.out.println("Invalid index.");
			return;
		}
		Node n = new Node(song);
		if (index == 0) {
			n.next = head;
			head = n;
		} else {
			Node prev = nodeAt(index - 1);
			n.next = prev.next;
			prev.next = n;
		}
		size++;
	}
	
	public void removeSong(int index) {
		if (index < 0 || index >= size) {
			System.out.println("Invalid index.");
			return;
		}
		if (index == 0) head = head.next;
		else {
			Node prev = nodeAt(index - 1);
			prev.next = prev.next.next;
		}
		size--;
	}
	
	public Song getSong(int index) {
		if (index < 0 || index >= size) {
			System.out.println("Invalid index.");
			return null;
		}
		Node n = nodeAt(index);
		n.data.incrementPlayCount();
		if (queue != null) queue.addRecentlyPlayed(n.data);
		return n.data;
	}
	
	public Song peekSong(int index) {
		if (index < 0 || index >= size) return null;
		Node n = nodeAt(index);
		return n == null ? null : n.data;
	}
	
	private Node nodeAt(int index) {
		Node cur = head;
		int i = 0;
		while (cur != null && i < index) {
			cur = cur.next;
			i++;
		}
		return cur;
	}
	
	public void shuffle() {
		if (size <= 1) return;
		Random rand = new Random();
		for (int i = size - 1; i > 0; i--) {
			int j = rand.nextInt(i + 1);
			Node ni = nodeAt(i);
			Node nj = nodeAt(j);
			Song tmp = ni.data;
			ni.data = nj.data;
			nj.data = tmp;
		}
	}
	
	public void printPlaylist() {
		System.out.println("--- Playlist (Linked List) ---");
		if (size == 0) {
			System.out.println("(empty)");
			return;
		}
		Node cur = head;
		int idx = 0;
		while (cur != null) {
			System.out.println(idx + ": " + cur.data);
			cur = cur.next;
			idx++;
		}
	}
	
	private interface songComparator { int compare(Song a, Song b); }
	
	private void insertionSort(songComparator cmp) {
		if (head == null || head.next == null) return;
		Node sorted = null;
		Node cur = head;
		while (cur != null) {
			Node next = cur.next;
			if (sorted == null || cmp.compare(cur.data, sorted.data) <= 0) {
				cur.next = sorted;
				sorted = cur;
			} else {
				Node s = sorted;
				while (s.next != null && cmp.compare(s.next.data, cur.data) < 0) s = s.next;
				cur.next = s.next;
				s.next = cur;
			}
			cur = next;
		}
		head = sorted;
	}
	
	public void sortByTitle() {
		insertionSort((a,b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
	}
	
	public void sortByArtist() {
		insertionSort((a,b) -> a.getArtist().compareToIgnoreCase(b.getArtist()));
	}
	
	public void sortByDuration() {
		insertionSort((a,b) -> Integer.compare(a.getDuration(), b.getDuration()));
	}
	
	public void sortByPlayCount() {
		insertionSort((a,b) -> Integer.compare(a.getPlayCount(), b.getPlayCount()));
	}
	
	public int[] searchByTitle(String title) {
		if (title == null) title = "";
		int count = 0; Node cur = head;
		while (cur != null) { if (cur.data.getTitle().equalsIgnoreCase(title)) count++; cur = cur.next; }
		int[] res = new int[count];
		int i = 0;
		int idx = 0;
		cur = head;
		while (cur != null) {
			if (cur.data.getTitle().equalsIgnoreCase(title)) res[i++] = idx;
			cur = cur.next;
			idx++;
		}
		return res;
	}
	
	public Song[] searchByArtist(String artist) {
		if (artist == null) artist = "";
		int count = 0;
		Node cur = head;
		while (cur != null) { if (cur.data.getArtist().equalsIgnoreCase(artist)) count++; cur = cur.next; }
		Song[] res = new Song[count];
		int i = 0;
		cur = head;
		while (cur != null) {
			if (cur.data.getArtist().equalsIgnoreCase(artist)) res[i++] = cur.data;
			cur = cur.next;
		}
		return res;
	}
	
	public Song[] searchByGenre(String genre) {
		if (genre == null) genre = "";
		int count = 0;
		Node cur = head;
		while (cur != null) { if (cur.data.getGenre().equalsIgnoreCase(genre)) count++; cur = cur.next; }
		Song[] res = new Song[count];
		int i = 0;
		cur = head;
		while (cur != null) {
			if (cur.data.getGenre().equalsIgnoreCase(genre)) res[i++] = cur.data;
			cur = cur.next;
		}
		return res;
	}
	
	public int size() {
		return size;
	}
	
}













