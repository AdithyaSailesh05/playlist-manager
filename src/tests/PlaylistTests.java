package csi213_project03;

public class PlaylistTests {
	
	public static void main (String[] args) {
		
		RecentlyPlayedQueue queue = new RecentlyPlayedQueue();
		
		PlaylistArrayList arr = new PlaylistArrayList();
		arr.setRecentlyPlayedQueue(queue);
		
		arr.addSong(new Song("Water Fountain", "Alec Benjamin", 218, "Indie"));
		arr.addSong(new Song("Numb", "Linkin Park", 185, "Rock"));
		arr.addSong(new Song("Out of Time", "The Weeknd", 233, "R&B"));
		arr.addSong(new Song("Timeless", "The Weeknd", 256, "Hip-hop"));
		arr.insertSong(1, new Song("Space Cadet", "Metro Boomin", 203, "Hip-hop"));
		
		System.out.println("Initial (Array):");
		arr.printPlaylist();
		
		System.out.println("\nView index 2 twice:");
		arr.getSong(2);
		arr.getSong(2);
		queue.printRecentlyPlayed();
		
		System.out.println("\nSearch by title = 'Water Fountain':");
		int[] pos = arr.searchByTitle("Water Fountain");
		for (int p : pos) System.out.print(p + " ");		
		System.out.println();
		
		System.out.println("\nSort by title then print:");
		arr.sortByTitle();
		arr.printPlaylist();
		
		System.out.println("\nShuffle then print:");
		arr.shuffle();
		arr.printPlaylist();
		
		PlaylistLinkedList list = new PlaylistLinkedList();
		list.setRecentlyPlayedQueue(queue);
		for (int i = 0; i < arr.size(); i++) {
			list.addSong(arr.peekSong(i));
		}
		
		System.out.println("\nNow Linkedlist version:)");
		list.printPlaylist();
		
		System.out.println("\nSort by play count then print:");
		list.sortByPlayCount();
		list.printPlaylist();
		
		System.out.println("\nSearch artist = 'The Weeknd':");
		Song[] found = list.searchByArtist("The Weeknd");
		for (Song s : found) System.out.println(" - " + s);
		
		System.out.println("\nRemove index 1 and print:");
		list.removeSong(1);
		list.printPlaylist();
		System.out.println("\nAll tests complete (manual verification).");
		
	}
	

}
