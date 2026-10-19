/**
 * One song sitting in the listening queue. Two entries can hold the same
 * Song, since you might want to queue a song you like more than once.
 *
 * @author Angelo Pinillos-Sternberg
 * @version 2026.10.12
 */
public class QueueEntry
{
    private Song song;

    /**
     * Makes a new entry for song. O(1).
     *
     * @param song
     *            the song we're queueing
     * @throws IllegalArgumentException
     *            if song is null, since an entry with no song doesn't make
     *            sense
     */
    public QueueEntry(Song song)
    {
        if (song == null)
        {
            throw new IllegalArgumentException(
                "A QueueEntry must contain a Song.");
        }
        this.song = song;
    }


    /**
     * Gives back the song in this entry. O(1).
     *
     * @return the song
     */
    public Song getSong()
    {
        return song;
    }


    /**
     * Two entries are equal if their songs are equal. We use Song.equals()
     * for that, so genre doesn't count. LinkedChain uses this when it
     * searches the queue. O(1).
     *
     * @param other
     *            what we're comparing to
     * @return true if other is a QueueEntry with the same song
     */
    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (other == null || getClass() != other.getClass())
        {
            return false;
        }
        return song.equals(((QueueEntry)other).song);
    }
}
