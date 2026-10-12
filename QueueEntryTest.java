import student.TestCase;

/**
 * Tests for QueueEntry. These follow the test plan from our spec.
 *
 * @author Angelo Pinillos-Sternberg
 * @version 2026.10.12
 */
public class QueueEntryTest
    extends TestCase
{
    private Song song;
    private Song sameSong;
    private Song otherSong;
    private QueueEntry entry;

    /**
     * Sets up a few songs and one entry to use in the tests.
     */
    public void setUp()
    {
        song = new Song("Bohemian Rhapsody", "Queen", 1975, "Rock");
        sameSong = new Song("Bohemian Rhapsody", "Queen", 1975, "NA");
        otherSong = new Song("Hey Jude", "The Beatles", 1968, "Rock");
        entry = new QueueEntry(song);
    }


    // QueueEntry(): normal case
    /**
     * Makes sure the constructor actually keeps the song we gave it.
     */
    public void testConstructor()
    {
        assertSame(song, entry.getSong());
    }


    // QueueEntry(): bad input case
    /**
     * Passing in null should throw an IllegalArgumentException.
     */
    public void testConstructorNullSong()
    {
        boolean exceptionWasThrown = false;

        try
        {
            new QueueEntry(null);
        }
        catch (IllegalArgumentException e)
        {
            exceptionWasThrown = true;
        }

        assertTrue(exceptionWasThrown);
    }


    // getSong(): normal case
    /**
     * getSong should give back the song that's in the entry.
     */
    public void testGetSong()
    {
        QueueEntry other = new QueueEntry(otherSong);
        assertSame(otherSong, other.getSong());
        assertEquals("Hey Jude", other.getSong().getTitle());
    }


    // getSong(): bad input case
    /**
     * getSong doesn't take anything, so there's no bad input to give it.
     * Instead we check that two entries don't mix up their songs.
     */
    public void testGetSongTwoEntries()
    {
        QueueEntry other = new QueueEntry(otherSong);
        assertSame(song, entry.getSong());
        assertSame(otherSong, other.getSong());
    }


    // equals(): normal case
    /**
     * Entries with the same song should be equal. The genres don't match
     * here on purpose, since Song.equals ignores genre.
     */
    public void testEqualsSameSong()
    {
        assertTrue(entry.equals(entry));
        assertTrue(entry.equals(new QueueEntry(song)));
        assertTrue(entry.equals(new QueueEntry(sameSong)));
    }


    // equals(): normal case
    /**
     * Entries with different songs shouldn't be equal.
     */
    public void testEqualsDifferentSong()
    {
        assertFalse(entry.equals(new QueueEntry(otherSong)));
    }


    // equals(): bad input case
    /**
     * An entry shouldn't equal null, a String, or a plain Song.
     */
    public void testEqualsBadInput()
    {
        assertFalse(entry.equals(null));
        assertFalse(entry.equals("Bohemian Rhapsody"));
        assertFalse(entry.equals(song));
    }


    // LinkedChain<QueueEntry>: duplicates
    /**
     * You can queue the same song twice. Removing it once should only take
     * out one of them.
     */
    public void testDuplicateEntriesInQueue()
    {
        LinkedChain<QueueEntry> queue = new LinkedChain<QueueEntry>();
        queue.addToEnd(new QueueEntry(song));
        queue.addToEnd(new QueueEntry(otherSong));
        queue.addToEnd(new QueueEntry(song));

        assertEquals(3, queue.size());
        assertEquals(2, queue.count(new QueueEntry(song)));

        assertTrue(queue.remove(new QueueEntry(song)));
        assertEquals(1, queue.count(new QueueEntry(song)));
        assertTrue(queue.contains(new QueueEntry(sameSong)));
    }


    // LinkedChain<QueueEntry>: play order
    /**
     * Songs should come out of the queue in the same order they went in.
     */
    public void testQueuePlaysInOrder()
    {
        LinkedChain<QueueEntry> queue = new LinkedChain<QueueEntry>();
        queue.addToEnd(new QueueEntry(song));
        queue.addToEnd(new QueueEntry(otherSong));

        assertSame(song, queue.remove().getSong());
        assertSame(otherSong, queue.remove().getSong());
        assertTrue(queue.isEmpty());
    }


    // LinkedChain<QueueEntry>: empty queue
    /**
     * Skipping or removing when the queue is empty shouldn't crash.
     */
    public void testEmptyQueue()
    {
        LinkedChain<QueueEntry> queue = new LinkedChain<QueueEntry>();
        assertNull(queue.remove());
        assertFalse(queue.remove(new QueueEntry(song)));
        assertFalse(queue.contains(new QueueEntry(song)));
    }
}
