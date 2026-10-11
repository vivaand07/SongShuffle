
public class Song {

	private String title;
	
	public Song (String title, String artist, int year, String genre) {
		this.title = title;
		this.artist = artist;
		this.year = year;
		this.genre = genre;
	}
	
	public void playSong() {
		System.out.println("playing: "+ title);
		System.out.println("-----");
		System.out.println("this is a change made in web");
		System.out.println("this is to create an error");
		System.out.println("blah blah");
	}
	

	
}

