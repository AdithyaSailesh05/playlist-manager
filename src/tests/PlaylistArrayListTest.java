package csi213_project03;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class PlaylistArrayListTest {
	
	private PlaylistArrayList p;
	
	@Before
	public void setUp() {
		p = new PlaylistArrayList();
		p.setRecentlyPlayedQueue(new RecentlyPlayedQueue());
		p.addSong(new Song("Numb", "Linkin Park", 185, "Rock"));
		p.addSong(new Song("Water Fountain", "Alec Benjamin", 218, "Indie"));
		p.addSong(new Song("Out of Time", "The Weeknd", 233, "R&B"));
		
	}
	
	@Test
	public void testInsertSongAndOrder() {
		p.insertSong(1,  new Song("Space Cadet", "Metro Boomin", 203, "Hip-Hop"));
		assertEquals(4, p.size());
		assertEquals("Space Cadet", p.peekSong(1).getTitle());
	}
	
	@Test
	public void testAddSongIncreasesSize() {
		int before = p.size();
		p.addSong(new Song("Timeless", "The Weeknd", 256, "Hip-Hop"));
		assertEquals(before + 1, p.size());
		
	}
	
	@Test
	public void testRemoveSongByIndex() {
		int before = p.size();
		p.removeSong(1);
		assertEquals(before - 1, p.size());
		assertEquals("Out of Time", p.peekSong(1).getTitle());
	}
	
	@Test
	public void testGetSongIncrementsPlayCountandQueues() {
		Song s0 = p.getSong(0);
		assertEquals(1, s0.getPlayCount());
		p.getSong(0);
		assertEquals(2, p.peekSong(0).getPlayCount());
	}
	
	@Test
	public void testSortByTitle() {
		p.sortByTitle();
		assertEquals("Numb", p.peekSong(0).getTitle());
		assertEquals("Out of Time", p.peekSong(1).getTitle());
		assertEquals("Water Fountain", p.peekSong(2).getTitle());
	}
	
	@Test
	public void testSearches() {
		assertArrayEquals(new int[] {1}, p.searchByTitle("Water Fountain"));
		assertEquals(1, p.searchByArtist("The Weeknd").length);
		assertEquals(1, p.searchByGenre("Indie").length);
	}
	
	@Test
	public void testShuffleKeepsAllElements() {
		String t0 = p.peekSong(0).getTitle();
		String t1 = p.peekSong(1).getTitle();
		String t2 = p.peekSong(2).getTitle();
		p.shuffle();
		assertEquals(3, p.size());
	}

}
