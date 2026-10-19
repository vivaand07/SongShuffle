import student.TestCase;

public class PlaylistTest extends TestCase {
    
    private Playlist playlist;
    private Song song1;
    private Song song2;
    private Song song3;
    
    // Sets up objects before tests
    public void setUp() {
        playlist = new Playlist("Playlist 1");
        
        song1 = new Song("DUCKWORTH.", "Kendrick Lamar", 2017, "Hip-Hop");
        song2 = new Song("Mirror", "Kendrick Lamar", 2022, "Hip-Hop");
        song3 = new Song("Wesley's Theory", "Kendrick Lamar", 2012, "Hip-Hop");
    }
    
    // Tests the constructor
    public void testConstructor() {
        assertEquals("Playlist 1", playlist.getName());
        assertEquals(0, playlist.getSongCount());
        assertEquals(0, playlist.getSongs().length);
    }
    
    // Tests getSongs()
    // addSong uses LinkedChain.add, which puts new songs at the front,
    // so the newest song comes back first
    public void testGetSongs() {
        playlist.addSong("DUCKWORTH.", "Kendrick Lamar", 2017, "Hip-Hop");
        playlist.addSong("Mirror", "Kendrick Lamar", 2022, "Hip-Hop");
        playlist.addSong("Wesley's Theory", "Kendrick Lamar", 2012, "Hip-Hop");

        Song[] temp = playlist.getSongs();
        assertEquals(3, temp.length);
        assertEquals(song3, temp[0]);
        assertEquals(song2, temp[1]);
        assertEquals(song1, temp[2]);
    }
    
    // Tests getName()
    public void testGetName() {
        assertEquals("Playlist 1", playlist.getName());
        
        Playlist emptyPlaylist = new Playlist("");
        assertEquals("", emptyPlaylist.getName());
    }
    
    // Tests getSongCount()
    public void testGetSongCount() {
        assertEquals(0, playlist.getSongCount());
        
        playlist.addSong("DUCKWORTH.", "Kendrick Lamar", 2017, "Hip-Hop");
        playlist.addSong("Mirror", "Kendrick Lamar", 2022, "Hip-Hop");
        playlist.addSong("Wesley's Theory", "Kendrick Lamar", 2012, "Hip-Hop");
        
        assertEquals(3, playlist.getSongCount());
    }
    
    // Tests addSong()
    public void testAddSong() {
        playlist.addSong("Wesley's Theory", "Kendrick Lamar", 
            2012, "Hip-Hop");
        assertEquals(1, playlist.getSongCount());
        assertTrue(playlist.containsSong(song3));

        playlist.addSong("DUCKWORTH.", "Kendrick Lamar", 2017, "Hip-Hop");
        assertEquals(2, playlist.getSongCount());
        assertTrue(playlist.containsSong(song1));
    }
    
    // Tests removeSong()
    public void testRemoveSong() {
        playlist.addSong("DUCKWORTH.", "Kendrick Lamar", 2017, "Hip-Hop");
        playlist.addSong("Mirror", "Kendrick Lamar", 2022, "Hip-Hop");
        assertEquals(2, playlist.getSongCount());
        
        assertTrue(playlist.removeSong(song2));
        assertEquals(1, playlist.getSongCount());
        assertTrue(playlist.containsSong(song1));
        assertFalse(playlist.containsSong(song2));
        
        assertFalse(playlist.removeSong(song2));
    }
    
    // Tests containsSong()
    public void testContainsSong() {
        assertFalse(playlist.containsSong(song1));
        playlist.addSong("DUCKWORTH.", "Kendrick Lamar", 2017, "Hip-Hop");
        assertTrue(playlist.containsSong(song1));
    }
}
