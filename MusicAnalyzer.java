/**
 * Analyzes the songs in a Playlist.
 *
 * @author Will
 * @version 2026.10.19
 */
public class MusicAnalyzer
{
    private Playlist playlist;


    /**
     * Creates an analyzer for the given playlist. O(1).
     *
     * @param playlist the playlist to analyze
     * @throws IllegalArgumentException if playlist is null
     */
    public MusicAnalyzer(Playlist playlist)
    {
        if (playlist == null)
        {
            throw new IllegalArgumentException("Playlist cannot be null.");
        }
        this.playlist = playlist;
    }


    /**
     * Returns the number of entries, including repeated songs. O(1).
     *
     * @return the number of songs in the supplied playlist
     */
    public int getSongCount()
    {
        return playlist.getSongCount();
    }


    /**
     * Returns the most frequent song, or null for an empty playlist.
     * For a tie, returns the first song in playlist order. O(n^2).
     *
     * @return the favorite song, or null if there are no songs
     */
    public Song getFavoriteSong()
    {
        Song[] favorites = getFavoriteSongs();
        if (favorites.length == 0)
        {
            return null;
        }
        return favorites[0];
    }


    /**
     * Returns all songs tied for the greatest number of appearances.
     * Each distinct song appears once, in playlist order. O(n^2)
     *
     * @return the favorite songs, or an empty array if there are no songs
     */
    public Song[] getFavoriteSongs()
    {
        Song[] songs = playlist.getSongs();
        LinkedChain<Song> favorites = new LinkedChain<Song>();
        int highestCount = 0;

        for (int i = 0; i < songs.length; i++)
        {
            int count = 0;
            for (int j = 0; j < songs.length; j++)
            {
                if (songs[i].equals(songs[j]))
                {
                    count++;
                }
            }

            if (count > highestCount)
            {
                highestCount = count;
                favorites.clear();
                favorites.add(songs[i]);
            }
            else if (count == highestCount && !favorites.contains(songs[i]))
            {
                favorites.addToEnd(songs[i]);
            }
        }

        return favorites.toArray(new Song[favorites.size()]);
    }


    /**
     * Returns the artist appearing on the most entries. Repeated plays of
     * a song each count toward its artist. O(n^2).
     *
     * @return the favorite artist
     */
    public String getFavoriteArtist()
    {
        Song[] songs = playlist.getSongs();
        String[] artists = new String[songs.length];
        for (int i = 0; i < songs.length; i++)
        {
            artists[i] = songs[i].getArtist();
        }
        return mostCommon(artists);
    }


    /**
     * Returns the genre appearing on the most entries. Repeated plays of
     * a song each count toward its genre. O(n^2).
     *
     * @return the favorite genre
     */
    public String getFavoriteGenre()
    {
        Song[] songs = playlist.getSongs();
        String[] genres = new String[songs.length];
        for (int i = 0; i < songs.length; i++)
        {
            genres[i] = songs[i].getGenre();
        }
        return mostCommon(genres);
    }


    /**
     * Finds the most common non-null value, ignoring caps.
     * Keeps the first value found when counts are tied. O(n^2).
     *
     * @param values the artist names or genres to count
     * @return the most common value
     */
    private String mostCommon(String[] values)
    {
        String favorite = null;
        int highestCount = 0;

        for (int i = 0; i < values.length; i++)
        {
            if (values[i] != null)
            {
                int count = 0;
                for (int j = 0; j < values.length; j++)
                {
                    if (values[i].equalsIgnoreCase(values[j]))
                    {
                        count++;
                    }
                }
                if (count > highestCount)
                {
                    highestCount = count;
                    favorite = values[i];
                }
            }
        }

        return favorite;
    }
}

