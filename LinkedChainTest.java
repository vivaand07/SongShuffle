import student.TestCase;

/**
 * Tests for LinkedChain. Every public method gets a normal test and a bad
 * input test. The last two tests use the same LinkedChain with Songs and
 * QueueEntries to show it works for more than one type.
 *
 * @author Angelo Pinillos-Sternberg
 * @version 2026.10.12
 */
public class LinkedChainTest
    extends TestCase
{
    private LinkedChain<String> chain;
    private LinkedChain<String> empty;

    /**
     * Sets up a chain with "a", "b", "c" in that order, and an empty one.
     */
    public void setUp()
    {
        chain = new LinkedChain<String>();
        chain.addToEnd("a");
        chain.addToEnd("b");
        chain.addToEnd("c");
        empty = new LinkedChain<String>();
    }


    // size() and isEmpty(): empty chain
    /**
     * A brand new chain should be empty with size 0.
     */
    public void testNewChainIsEmpty()
    {
        assertEquals(0, empty.size());
        assertTrue(empty.isEmpty());
    }


    // size() and isEmpty(): normal case
    /**
     * Checks size and isEmpty when there are three things in the chain.
     */
    public void testSizeAndIsEmpty()
    {
        assertEquals(3, chain.size());
        assertFalse(chain.isEmpty());
    }


    // add(): normal case
    /**
     * add should put the new entry at the front.
     */
    public void testAdd()
    {
        chain.add("z");
        assertEquals(4, chain.size());
        assertEquals("z", chain.get(0));
        assertEquals("a", chain.get(1));
    }


    // add(): bad input case
    /**
     * Adding null should throw, and the chain shouldn't change.
     */
    public void testAddNull()
    {
        boolean exceptionWasThrown = false;

        try
        {
            chain.add(null);
        }
        catch (IllegalArgumentException e)
        {
            exceptionWasThrown = true;
        }

        assertTrue(exceptionWasThrown);
        assertEquals(3, chain.size());
    }


    // addToEnd(): normal case
    /**
     * addToEnd should put the new entry at the back. Also checks that it
     * works on an empty chain.
     */
    public void testAddToEnd()
    {
        chain.addToEnd("d");
        assertEquals("d", chain.get(3));
        empty.addToEnd("only");
        assertEquals("only", empty.get(0));
        assertEquals(1, empty.size());
    }


    // addToEnd(): bad input case
    /**
     * addToEnd with null should throw, and the chain shouldn't change.
     */
    public void testAddToEndNull()
    {
        boolean exceptionWasThrown = false;

        try
        {
            chain.addToEnd(null);
        }
        catch (IllegalArgumentException e)
        {
            exceptionWasThrown = true;
        }

        assertTrue(exceptionWasThrown);
        assertEquals(3, chain.size());
    }


    // remove(): normal case
    /**
     * remove() should take things off the front one at a time.
     */
    public void testRemoveFirst()
    {
        assertEquals("a", chain.remove());
        assertEquals("b", chain.remove());
        assertEquals(1, chain.size());
    }


    // remove(): bad input case
    /**
     * remove() on an empty chain should just give back null.
     */
    public void testRemoveFirstEmpty()
    {
        assertNull(empty.remove());
        assertEquals(0, empty.size());
    }


    // remove(T): normal case
    /**
     * Removes from the middle, front, and back, and makes sure whatever's
     * left is still in the right order.
     */
    public void testRemoveEntry()
    {
        chain.addToEnd("d");
        assertTrue(chain.remove("b"));
        assertEquals("a", chain.get(0));
        assertEquals("c", chain.get(1));
        assertEquals("d", chain.get(2));

        assertTrue(chain.remove("a"));
        assertTrue(chain.remove("d"));
        assertEquals(1, chain.size());
        assertEquals("c", chain.get(0));
    }


    // remove(T): duplicates
    /**
     * If something is in there twice, remove should only take out the
     * first one.
     */
    public void testRemoveEntryDuplicate()
    {
        chain.addToEnd("a");
        assertTrue(chain.remove("a"));
        assertEquals(1, chain.count("a"));
        assertEquals("b", chain.get(0));
    }


    // remove(T): bad input case
    /**
     * Trying to remove something that isn't there, null, or anything from
     * an empty chain should return false and not change anything.
     */
    public void testRemoveEntryNotPresent()
    {
        assertFalse(chain.remove("x"));
        assertFalse(chain.remove((String)null));
        assertEquals(3, chain.size());
        assertFalse(empty.remove("a"));
        assertEquals(0, empty.size());
    }


    // contains(): normal case
    /**
     * contains should find things at the front and the back.
     */
    public void testContains()
    {
        assertTrue(chain.contains("a"));
        assertTrue(chain.contains("c"));
    }


    // contains(): bad input case
    /**
     * contains should say false for something missing, for null, and for
     * an empty chain.
     */
    public void testContainsBadInput()
    {
        assertFalse(chain.contains("x"));
        assertFalse(chain.contains(null));
        assertFalse(empty.contains("a"));
    }


    // count(): normal case
    /**
     * count should count duplicates too.
     */
    public void testCount()
    {
        chain.add("b");
        assertEquals(2, chain.count("b"));
        assertEquals(1, chain.count("a"));
    }


    // count(): bad input case
    /**
     * count should give 0 for something missing, for null, and for an
     * empty chain.
     */
    public void testCountBadInput()
    {
        assertEquals(0, chain.count("x"));
        assertEquals(0, chain.count(null));
        assertEquals(0, empty.count("a"));
    }


    // get(): normal case
    /**
     * get should give back the right thing at each index.
     */
    public void testGet()
    {
        assertEquals("a", chain.get(0));
        assertEquals("b", chain.get(1));
        assertEquals("c", chain.get(2));
    }


    // get(): bad input case
    /**
     * get should throw for an index below 0, an index that's too big, and
     * any index on an empty chain.
     */
    public void testGetOutOfBounds()
    {
        int[] badIndexes = { -1, 3 };
        for (int index : badIndexes)
        {
            boolean exceptionWasThrown = false;
            try
            {
                chain.get(index);
            }
            catch (IndexOutOfBoundsException e)
            {
                exceptionWasThrown = true;
            }
            assertTrue(exceptionWasThrown);
        }

        boolean emptyThrew = false;
        try
        {
            empty.get(0);
        }
        catch (IndexOutOfBoundsException e)
        {
            emptyThrew = true;
        }
        assertTrue(emptyThrew);
    }


    // toArray(): normal case
    /**
     * toArray should copy everything over in order. An empty chain should
     * give an empty array.
     */
    public void testToArray()
    {
        String[] result = chain.toArray(new String[3]);
        assertEquals(3, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
        assertEquals(0, empty.toArray(new String[0]).length);
    }


    // toArray(): bad input case
    /**
     * toArray should throw if the array is null or too small.
     */
    public void testToArrayBadInput()
    {
        boolean nullThrew = false;
        try
        {
            chain.toArray(null);
        }
        catch (IllegalArgumentException e)
        {
            nullThrew = true;
        }
        assertTrue(nullThrew);

        boolean tooSmallThrew = false;
        try
        {
            chain.toArray(new String[2]);
        }
        catch (IllegalArgumentException e)
        {
            tooSmallThrew = true;
        }
        assertTrue(tooSmallThrew);
    }


    // clear(): normal case
    /**
     * clear should empty everything out, and we should still be able to
     * use the chain after.
     */
    public void testClear()
    {
        chain.clear();
        assertTrue(chain.isEmpty());
        assertEquals(0, chain.size());
        assertNull(chain.remove());
        chain.add("new");
        assertEquals(1, chain.size());
    }


    // clear(): bad input case
    /**
     * Clearing a chain that's already empty shouldn't break anything.
     */
    public void testClearEmpty()
    {
        empty.clear();
        assertTrue(empty.isEmpty());
    }


    // LinkedChain<Song>
    /**
     * Uses the chain with Songs. Songs with the same title, artist, and
     * year should match even if the genre is different.
     */
    public void testChainOfSongs()
    {
        LinkedChain<Song> songs = new LinkedChain<Song>();
        Song song = new Song("Hey Jude", "The Beatles", 1968, "Rock");
        songs.add(song);
        songs.add(new Song("Hey Jude", "The Beatles", 1968, "NA"));

        assertEquals(2, songs.count(song));
        assertSame(song, songs.get(1));
        assertTrue(songs.remove(new Song("Hey Jude", "The Beatles", 1968,
            "Pop")));
        assertEquals(1, songs.size());
    }


    // LinkedChain<QueueEntry>
    /**
     * Uses the chain as a queue of QueueEntries with addToEnd and remove().
     */
    public void testChainOfQueueEntries()
    {
        Song first = new Song("Hey Jude", "The Beatles", 1968, "Rock");
        Song second = new Song("Yesterday", "The Beatles", 1965, "Rock");
        LinkedChain<QueueEntry> queue = new LinkedChain<QueueEntry>();
        queue.addToEnd(new QueueEntry(first));
        queue.addToEnd(new QueueEntry(second));

        assertSame(first, queue.remove().getSong());
        assertSame(second, queue.remove().getSong());
        assertNull(queue.remove());
    }
}
