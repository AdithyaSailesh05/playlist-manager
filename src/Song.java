package csi213_project03;

public class Song {
	
	private String title;
	private String artist;
	private String genre;
	private int duration;
	private int playCount;
	
	public Song(String title, String artist, int duration, String genre) {
		
		if (title == null) title = "";
		if (artist == null) artist = "";
		if (genre == null) genre = "";
		if (duration < 0) duration = 0;
		this.title = title;
		this.artist = artist;
		this.duration = duration;
		this.genre = genre;
		this.playCount = 0;
		
	}
	
	public Song(Song other) {
		this.title = other.title;
		this.artist = other.artist;
		this.duration = other.duration;
		this.genre = other.genre;
		this.playCount = other.playCount;
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getPlayCount() {
		return playCount;
	}
	
	public void setTitle(String title) {
		this.title = (title == null ? "" : title);
	}
	
	public void setArtist(String artist) {
		this.artist = (artist == null ? "" : artist);
	}
	
	public void setGenre(String genre) {
		this.genre = (genre == null ? "" : genre);
	}
	
	public void setDuration(int duration) {
		this.duration = Math.max(0, duration);
	}
	
	public void incrementPlayCount() {
		this.playCount++;
	}
	
	@Override
	public String toString() {
		int minutes = duration / 60;
		int seconds = duration % 60;
		String dur = String.format("%d:%02d", minutes, seconds);
		return String.format("\"%s\" by %s | %s | %s | plays=%d", title, artist, dur, genre, playCount);
	}
	
	
}
