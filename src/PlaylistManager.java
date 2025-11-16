package csi213_project03;

import java.util.Scanner;

public class PlaylistManager {
	private static Scanner scanner = new Scanner(System.in);
	private static RecentlyPlayedQueue queue = new RecentlyPlayedQueue();
	
	private static boolean usingArray = true;
	private static PlaylistArrayList arrayPlaylist = new PlaylistArrayList();
	private static PlaylistLinkedList linkedPlaylist = new PlaylistLinkedList();
	
	public static void main(String[] args) {
		arrayPlaylist.setRecentlyPlayedQueue(queue);
		linkedPlaylist.setRecentlyPlayedQueue(queue);
		
		System.out.println("Welcome to the Playlist Manager!");
		boolean running = true;
		while (running) {
			printMenu();
			int choice = readInt("Choose an option: ");
			switch (choice) {
			case 1 -> addSongUI();
			case 2 -> insertSongUI();
			case 3 -> removeSongUI();
			case 4 -> viewSongUI();
			case 5 -> {
				if (usingArray) arrayPlaylist.shuffle();
				else linkedPlaylist.shuffle();
				System.out.println("Your Playlist has been Shuffled.");
			}
			case 6 -> {
				if (usingArray) arrayPlaylist.printPlaylist();
				else linkedPlaylist.printPlaylist();
			}
			case 7 -> sortUI();
			case 8 -> searchUI();
			case 9 -> queue.printRecentlyPlayed();
			case 10 -> switchPlaylistType();
			case 11 -> running = false;
			default -> System.out.println("Invalid option. Please try again.");
			}
		}
		System.out.println("Goodbye!");
	}
	
	private static void printMenu() {
		System.out.println("""
				
				--- Menu ---
				1. Add a Song
				2. Insert Song at Position
				3. Remove Song at Position
				4. View Song
				5. Shuffle Playlist
				6. Print Playlist
				7. Sort Playlist
				8. Search for a Song
				9. View Recently Played Songs
				10. Switch Playlist Type (ArrayList/LinkedList)
				11. Exit
				""");
				
	}
	
	private static void addSongUI() {
		Song s = readSong();
		if (usingArray) arrayPlaylist.addSong(s);
		else linkedPlaylist.addSong(s);
		System.out.println("The song has been added!");
	}
	
	private static void insertSongUI() {
		int index = readInt("Insert at index (0.." + getSize() + "): ");
		Song s = readSong();
		if (usingArray) arrayPlaylist.insertSong(index,  s);
		else linkedPlaylist.insertSong(index, s);
	}
	
	private static void removeSongUI() {
		if (getSize() == 0) {
			System.out.println("Playlist is empty.");
			return;
		}
		int index = readInt("Remove at index (0.." + (getSize()-1) + "): ");
		if (usingArray) arrayPlaylist.removeSong(index);
		else linkedPlaylist.removeSong(index);
		
	}
	
	private static void viewSongUI() {
		if (getSize() == 0) {
			System.out.println("Playlist is empty.");
			return;
		}
		int index = readInt("View at index (0.." + (getSize()-1) + "): ");
		Song s = usingArray ? arrayPlaylist.getSong(index) : linkedPlaylist.getSong(index);
		if (s != null) System.out.println("Currently Playing: " + s);
	}
	
	private static void sortUI() {
		System.out.println("""
				Sort by:
				1. Title
				2. Artist
				3. Duration
				4. Play Count
				""");
		int c = readInt("Choose: ");
		if (usingArray) {
			switch (c) {
			case 1 -> arrayPlaylist.sortByTitle();
			case 2 -> arrayPlaylist.sortByArtist();
			case 3 -> arrayPlaylist.sortByDuration();
			case 4 -> arrayPlaylist.sortByPlayCount();
			default -> System.out.println("Invalid.");
			}
		} else {
			switch (c) {
			case 1 -> linkedPlaylist.sortByTitle();
			case 2 -> linkedPlaylist.sortByArtist();
			case 3 -> linkedPlaylist.sortByDuration();
			case 4 -> linkedPlaylist.sortByPlayCount();
			default -> System.out.println("Invalid.");
			}
		}
		System.out.println("The Playlist has been Sorted.");
	}
	
	private static void searchUI() {
		System.out.println("""
				Search by:
				1. Title
				2. Artist
				3. Genre
				""");
		int c = readInt("Choose: ");
		scanner.nextLine();
		
		System.out.print("Enter search term: ");
		String term = scanner.nextLine().trim();
		
		if (usingArray) {
			if (c == 1) printTitleSearch(arrayPlaylist.searchByTitle(term));
			else if (c == 2) printSongs(arrayPlaylist.searchByArtist(term));
			else if (c == 3) printSongs(arrayPlaylist.searchByGenre(term));
			else System.out.println("Invalid choice. Please try again.");
		} else {
			if (c == 1) printTitleSearch(linkedPlaylist.searchByTitle(term));
			else if (c == 2) printSongs(linkedPlaylist.searchByArtist(term));
			else if (c == 3) printSongs(linkedPlaylist.searchByGenre(term));
			else System.out.println("Invalid choice. Please try again.");
		}
	}
	
	private static void printTitleSearch(int[] positions) {
		if (positions.length == 0) System.out.println("No Matches.");
		else {
			System.out.println("Found at indices: ");
			for (int i = 0; i < positions.length; i++) {
				if ( i > 0) System.out.print(", ");
				System.out.print(positions[i]);
			}
			System.out.println();
		}
	}
	
	private static void printSongs(Song[] songs) {
		if (songs.length == 0) System.out.println("No matches.");
		else for (Song s : songs) System.out.println(" - " + s);
	}
	
	private static void switchPlaylistType() {
		usingArray = !usingArray;
		System.out.println("Switched to " + (usingArray ? "ArrayList" : "LinkedList") + " mode.");
	}
	
	private static int getSize() {
		return usingArray ? arrayPlaylist.size() : linkedPlaylist.size();
	}
	
	private static Song readSong() {
		System.out.println("Title: ");
		String title = scanner.nextLine().trim();
		
		System.out.println("Artist: ");
		String artist = scanner.nextLine().trim();
		
		int duration = readPositiveInt("Duration in seconds: ");
		
		System.out.println("Genre: ");
		String genre = scanner.nextLine().trim();
		
		return new Song(title, artist, duration, genre);
	}
	
	private static int readInt(String prompt) {
		while (true) {
			System.out.println(prompt);
			String s = scanner.nextLine();
			try { return Integer.parseInt(s.trim()); }
			catch (Exception e) { System.out.println("Please enter a number. "); }
		}
	}
	
	private static int readPositiveInt(String prompt) {
		while (true) {
			System.out.println(prompt);
			String s = scanner.nextLine();
			try {
				int v = Integer.parseInt(s.trim());
				if (v >= 0) return v;
			} catch (Exception ignored) {}
			System.out.println("Please enter 0 or a positive number.");
		}
		
	}
	
}

