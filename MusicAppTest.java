import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
 
/**
 * Tests the MusicApp class.
 *
 * @author Will
 * @version 2026.10.12
 */
public class MusicAppTest
{
   
    private static String espressoInput;
    private static String lightsInput;
 
    // A song to use when a test needs a Song object directly.
    private static Song espresso;
 
    /**
     * Sets up the song input and Song object used by the tests. 
     */
    public static void setUp()
    {
        espressoInput = "Espresso\nSabrina Carpenter\n2024\n1\n4\n";
        lightsInput = "Blinding Lights\nThe Weeknd\n2020\n1\n4\n";
        espresso = new Song("Espresso", "Sabrina Carpenter", 2024, "Pop");
    }
 
    // ----------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: a new MusicApp starts with an empty playlist and an
     * empty queue. 
     */
    @Test
    public void testConstructor()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        assertNotNull(app.getPlaylist());
        assertEquals("Gym", app.getPlaylist().getName());
        assertEquals(0, app.getPlaylist().getSongCount());
        assertTrue(app.getQueue().isEmpty());
    }
 
 
    /**
     * Bad input: a null Scanner throws an IllegalArgumentException.
     */
    @Test
    public void testConstructorNullInput()
    {
        try
        {
            new MusicApp(null, "Gym");
            fail("Expected an IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // this is what we expected
        }
    }
 
    // ----------------------------------------------------------
    // readOptions
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: a valid option is returned.
     */
    @Test
    public void testReadOptions()
    {
        MusicApp app = new MusicApp(new Scanner("5\n"), "Gym");
        assertEquals(5, app.readOptions());
    }
 
 
    /**
     * Bad input: a word, a number too small, and a number too big are all
     * skipped, and the first valid option (7) is returned.
     */
    @Test
    public void testReadOptionsInvalid()
    {
        MusicApp app = new MusicApp(new Scanner("abc\n0\n10\n7\n"), "Gym");
        assertEquals(7, app.readOptions());
    }
 
    // ----------------------------------------------------------
    // addSongToPlaylist
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: adding a song puts it in the playlist.
     */
    @Test
    public void testAddSongToPlaylist()
    {
        MusicApp app = new MusicApp(new Scanner(espressoInput), "Gym");
        app.addSongToPlaylist();
        assertEquals(1, app.getPlaylist().getSongCount());
        assertTrue(app.getPlaylist().containsSong(espresso));
    }
 
 
    /**
     * Bad input: an empty title, a year that isn't a number, a year that's
     * too small, a year that's too big, and a genre number that's out of
     * range are all re-prompted.
     */
    @Test
    public void testAddSongToPlaylistBadInput()
    {
        String typed = "\n"                     // empty title
            + "  Espresso  \n"                  // title with extra spaces
            + "Sabrina Carpenter\n"
            + "abc\n999\n2027\n2024\n"          // three bad years, then good
            + "0\n1\n"                          // bad genre, then Pop
            + "4\n";                            // looks good
        MusicApp app = new MusicApp(new Scanner(typed), "Gym");
        app.addSongToPlaylist();
        assertEquals(1, app.getPlaylist().getSongCount());
        assertTrue(app.getPlaylist().containsSong(espresso));
    }
 
 
    /**
     * Normal case: the user makes typos, then fixes the title, artist,
     * and year at the double check step.
     */
    @Test
    public void testAddSongToPlaylistReenter()
    {
        String typed = "Espreso\nSabrina\n2023\n1\n" // typos
            + "1\nEspresso\n"                         // fix title
            + "2\nSabrina Carpenter\n"                // fix artist
            + "3\n2024\n"                             // fix year
            + "4\n";                                  // looks good
        MusicApp app = new MusicApp(new Scanner(typed), "Gym");
        app.addSongToPlaylist();
        assertTrue(app.getPlaylist().containsSong(espresso));
    }
 
 
    /**
     * Duplicate case: the user adds the same song twice and says "n" when
     * warned, so the playlist only has it once.
     */
    @Test
    public void testAddDuplicateSong()
    {
        String typed = espressoInput + espressoInput + "n\n";
        MusicApp app = new MusicApp(new Scanner(typed), "Gym");
        app.addSongToPlaylist();
        app.addSongToPlaylist();
        assertEquals(1, app.getPlaylist().getSongCount());
    }
 
 
    /**
     * Duplicate case: the user types something that isn't y or n, then
     * says "y", so the song is added a second time.
     */
    @Test
    public void testAddDuplicateSongAnyway()
    {
        String typed = espressoInput + espressoInput + "maybe\ny\n";
        MusicApp app = new MusicApp(new Scanner(typed), "Gym");
        app.addSongToPlaylist();
        app.addSongToPlaylist();
        assertEquals(2, app.getPlaylist().getSongCount());
    }
 
    // ----------------------------------------------------------
    // removeSongFromPlaylist
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: removing song 1 from a one-song playlist empties it.
     */
    @Test
    public void testRemoveSongFromPlaylist()
    {
        MusicApp app = new MusicApp(new Scanner(espressoInput + "1\n"), "Gym");
        app.addSongToPlaylist();
        app.removeSongFromPlaylist();
        assertEquals(0, app.getPlaylist().getSongCount());
        assertFalse(app.getPlaylist().containsSong(espresso));
    }
 
 
    /**
     * Removing something that isn't there: removing from an empty playlist
     * does nothing and doesn't crash.
     */
    @Test
    public void testRemoveSongFromPlaylistEmpty()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        app.removeSongFromPlaylist();
        assertEquals(0, app.getPlaylist().getSongCount());
    }
 
    // ----------------------------------------------------------
    // viewPlaylist
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: viewing the playlist doesn't change it.
     */
    @Test
    public void testViewPlaylist()
    {
        MusicApp app = new MusicApp(new Scanner(espressoInput + lightsInput),
            "Gym");
        app.addSongToPlaylist();
        app.addSongToPlaylist();
        app.viewPlaylist();
        assertEquals(2, app.getPlaylist().getSongCount());
    }
 
 
    /**
     * Empty collection: viewing an empty playlist doesn't crash.
     */
    @Test
    public void testViewPlaylistEmpty()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        app.viewPlaylist();
        assertEquals(0, app.getPlaylist().getSongCount());
    }
 
    // ----------------------------------------------------------
    // playSong and playFromPlaylist
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: playing a song doesn't change the playlist or queue.
     */
    @Test
    public void testPlaySong()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        app.playSong(espresso);
        assertEquals(0, app.getPlaylist().getSongCount());
        assertTrue(app.getQueue().isEmpty());
    }
 
 
    /**
     * Bad input: playing a null song throws an IllegalArgumentException.
     */
    @Test
    public void testPlaySongNull()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        try
        {
            app.playSong(null);
            fail("Expected an IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // this is what we expected
        }
    }
 
 
    /**
     * Normal case: playing from the playlist leaves the playlist and the
     * queue the same. "5" is out of range, so it is re-prompted first.
     */
    @Test
    public void testPlayFromPlaylist()
    {
        MusicApp app = new MusicApp(new Scanner(espressoInput + "5\n1\n"),
            "Gym");
        app.addSongToPlaylist();
        app.playFromPlaylist();
        assertEquals(1, app.getPlaylist().getSongCount());
        assertTrue(app.getQueue().isEmpty());
    }
 
 
    /**
     * Empty collection: playing from an empty playlist doesn't crash.
     */
    @Test
    public void testPlayFromPlaylistEmpty()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        app.playFromPlaylist();
        assertEquals(0, app.getPlaylist().getSongCount());
    }
 
    // ----------------------------------------------------------
    // queueSong
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: queueing song 1 adds it to the queue.
     */
    @Test
    public void testQueueSong()
    {
        MusicApp app = new MusicApp(new Scanner(espressoInput + "1\n"), "Gym");
        app.addSongToPlaylist();
        app.queueSong();
        assertEquals(1, app.getQueue().size());
        assertEquals(espresso, app.getQueue().get(0).getSong());
    }
 
 
    /**
     * Empty collection: queueing from an empty playlist adds nothing.
     */
    @Test
    public void testQueueSongEmptyPlaylist()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        app.queueSong();
        assertTrue(app.getQueue().isEmpty());
    }
 
    // ----------------------------------------------------------
    // skip
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: skipping takes the song out of the queue but leaves it
     * in the playlist.
     */
    @Test
    public void testSkip()
    {
        MusicApp app = new MusicApp(new Scanner(espressoInput + "1\n"), "Gym");
        app.addSongToPlaylist();
        app.queueSong();
        app.skip();
        assertTrue(app.getQueue().isEmpty());
        assertEquals(1, app.getPlaylist().getSongCount());
    }
 
 
    /**
     * Removing something that isn't there: skipping with an empty queue
     * doesn't crash and the queue stays empty.
     */
    @Test
    public void testSkipEmpty()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        app.skip();
        assertTrue(app.getQueue().isEmpty());
    }
 
    // ----------------------------------------------------------
    // viewQueue
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: viewing the queue doesn't change it.
     */
    @Test
    public void testViewQueue()
    {
        MusicApp app = new MusicApp(new Scanner(espressoInput + "1\n1\n"),
            "Gym");
        app.addSongToPlaylist();
        app.queueSong();
        app.queueSong();
        app.viewQueue();
        assertEquals(2, app.getQueue().size());
    }
 
 
    /**
     * Empty collection: viewing an empty queue doesn't crash.
     */
    @Test
    public void testViewQueueEmpty()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        app.viewQueue();
        assertTrue(app.getQueue().isEmpty());
    }
 
    // ----------------------------------------------------------
    // wrapped
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: showing Wrapped doesn't change the playlist.
     */
    @Test
    public void testWrapped()
    {
        MusicApp app = new MusicApp(new Scanner(espressoInput + lightsInput),
            "Gym");
        app.addSongToPlaylist();
        app.addSongToPlaylist();
        app.wrapped();
        assertEquals(2, app.getPlaylist().getSongCount());
    }
 
 
    /**
     * Empty collection: Wrapped on an empty playlist doesn't crash.
     */
    @Test
    public void testWrappedEmpty()
    {
        MusicApp app = new MusicApp(new Scanner(""), "Gym");
        app.wrapped();
        assertEquals(0, app.getPlaylist().getSongCount());
    }
 
    // ----------------------------------------------------------
    // run
    // ----------------------------------------------------------
 
 
    /**
     * Normal case: goes through every menu option, then quits with 9.
     * In the end, one song was added, queued, skipped, and removed.
     */
    @Test
    public void testRun()
    {
        String typed = "1\n" + espressoInput   // add song
            + "3\n"                            // view playlist
            + "4\n1\n"                         // play song 1
            + "5\n1\n"                         // queue song 1
            + "7\n"                            // view queue
            + "6\n"                            // skip
            + "8\n"                            // wrapped
            + "2\n1\n"                         // remove song 1
            + "9\n";                           // quit
        MusicApp app = new MusicApp(new Scanner(typed), "Gym");
        app.run();
        assertEquals(0, app.getPlaylist().getSongCount());
        assertTrue(app.getQueue().isEmpty());
    }
 
 
    /**
     * Bad input: invalid menu choices are re-prompted, then 5 tries to
     * queue from an empty playlist, then 9 quits.
     */
    @Test
    public void testRunInvalidOption()
    {
        MusicApp app = new MusicApp(new Scanner("abc\n0\n5\n9\n"), "Gym");
        app.run();
        assertEquals(0, app.getPlaylist().getSongCount());
        assertTrue(app.getQueue().isEmpty());
    }
}
