/**
 *  Creates a playlist holding Song objects
 * 
 *  @author vivaandutt
 *  @version Oct 12, 2026
 */
public class Playlist {
    
    private String name;
    private LinkedChain<Song> songs;
    private int songCount;
    
    /**
     * Creates a new Playlist
     */
    public LinkedChain<Song> Playlist(String name) {
        this.name = name;
        songCount = 0;
        songs = new LinkedChain<Song>();
    }
    
    /**
     * Returns playlist name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the songs in the playlist as an array
     */
    public Song[] getSongs() {
        Song[] result = new Song[songCount];
        
        for (int i = 0; i < songCount; i++) {
            result[i] = songs.getEntry(i+1);
        }
        
        return result;
    }
    
    /**
     * Returns the current number of songs
     */
    public int getSongCount() {
        return songCount;
    }
    
    /**
     * Adds a new song
     */
    public void addSong(String title, String artist, int year, String genre) {
        Song newSong = new Song(title, artist, year, genre);
        songs.add(newSong);
        songCount++;
    }
    
    /**
     * Removes the first matching song
     */
    public boolean removeSong(Song song) {
        boolean removed = songs.remove(song);
        if(removed) {
            songCount--;
        }
        return removed;
    }
    
    /**
     * Checks if a playlist contains a song
     */
    public boolean containsSong(Song song) {
        for (int i = 0; i < songCount; i++) {
            if ((songs.getEntry(i+1)).equals(song)) {
                return true;
            }
        }
        return false;
    }
}
