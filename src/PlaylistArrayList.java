package csi213_project03;

import java.util.Random;

public class PlaylistArrayList {
	private Song[] data;
	private int size;
	private RecentlyPlayedQueue queue;
	
	public PlaylistArrayList() {
		data = new Song[10];
		size = 0;
	}
	
	public void setRecentlyPlayedQueue(RecentlyPlayedQueue queue) {
		this.queue = queue;
	}
	
	private void ensureCapacity(int minCapacity) {
		if (data.length >= minCapacity) return;
		int newCap = Math.max(minCapacity,  data.length * 2);
		Song[] newArr = new Song[newCap];
		for (int i = 0; i < size; i++) newArr[i] = data[i];
		data = newArr;
	}
	
	public void addSong(Song song) {
		if (song == null) return;
		ensureCapacity(size + 1);
		data[size++] = song;
	}
	
	public Song peekSong(int index) {
		if (index < 0 || index >= size) return null;
		return data[index];
	}
	
	public void insertSong(int index, Song song) {
		if (song == null) return;
		if (index < 0 || index > size) {
			System.out.println("Invalid index.");
			return;
		}
		ensureCapacity(size + 1);
		for (int i = size; i > index; i--) {
			data[i] = data[i-1];
		}
		data[index] = song;
		size++;
	}
	
	public void removeSong(int index) {
		if (index < 0 || index >= size) {
			System.out.println("Invalid index.");
			return;
		}
		for (int i = index; i < size - 1; i++) {
			data[i] = data[i + 1];
		}
		data[size - 1] = null;
		size--;
	}
	
	public Song getSong(int index) {
		if (index < 0 || index >= size) {
			System.out.println("Invalid index.");
			return null;
		}
		Song s = data[index];
		if (s != null) {
			s.incrementPlayCount();
			if (queue != null) queue.addRecentlyPlayed(s);			
		}
		return s;
		
	}
	
	public void shuffle() {
		Random rand = new Random();
		for (int i = size - 1; i > 0; i--) {
			int j = rand.nextInt(i + 1);
			Song tmp = data[i];
			data[i] = data[j];
			data[j] = tmp;
		}
	}
	
	public void printPlaylist() {
		System.out.println("--- Playlist (Array) ---");
		if (size == 0) {
			System.out.println("(empty)");
			return;
		}
		for (int i = 0; i < size; i++) {
			System.out.println(i + ": " + data[i]);
		}
	}
	
	private interface songComparator {
		int compare(Song a, Song b);
	}
	
	private void selectionSort(songComparator cmp) {
		for (int i = 0; i < size - 1; i++) {
			int minIdx = i;
			for (int j = i + 1; j < size; j++) {
				if (cmp.compare(data[j], data[minIdx]) < 0) minIdx = j;
			}
			Song tmp = data[i];
			data[i] = data[minIdx];
			data[minIdx] = tmp;
		}
	}
	
	public void sortByTitle() {
		selectionSort((a,b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
	}
	
	public void sortByArtist() {
		selectionSort((a,b) -> a.getArtist().compareToIgnoreCase(b.getArtist()));
	}
	
	public void sortByDuration() {
		selectionSort((a,b) -> Integer.compare(a.getDuration(), b.getDuration()));
	}
	
	public void sortByPlayCount() {
		selectionSort((a,b) -> Integer.compare(a.getPlayCount(), b.getPlayCount()));
	}
	
	public int[] searchByTitle(String title) {
		if (title == null) title = "";
		int count = 0;
		for (int i = 0; i < size; i++) 
			if (data[i].getTitle().equalsIgnoreCase(title)) count++;
		int [] positions = new int[count];
		int idx = 0;
		for (int i = 0; i < size; i++)
			if (data[i].getTitle().equalsIgnoreCase(title)) positions[idx++] = i;
		return positions;
		
	}
		
		
	public Song[] searchByArtist(String artist) {
		if (artist == null) artist = "";
		int count = 0;
		for (int i = 0; i < size; i++)
			if (data[i].getArtist().equalsIgnoreCase(artist)) count++;
		Song[] result = new Song[count];
		int idx = 0;
		for (int i = 0; i < size; i++)
			if (data[i].getArtist().equalsIgnoreCase(artist)) result[idx++] = data[i];
		return result;
	}
	
	public Song[] searchByGenre(String genre) {
		if (genre == null) genre = "";
		int count = 0;
		for (int i = 0; i < size; i++)
			if (data[i].getGenre().equalsIgnoreCase(genre)) count++;
		Song[] result = new Song[count];
		int idx = 0;
		for (int i = 0; i < size; i++)
			if (data[i].getGenre().equalsIgnoreCase(genre)) result[idx++] = data[i];
		return result;
	}
	
	public int size() {
		return size;
	}
	
	
}
