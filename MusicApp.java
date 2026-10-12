import java.util.Scanner;
 
/**
 * The user interface for the program
 *
 * @author Will
 * @version 2026.10.10
 */
public class MusicApp
{
    /**
     * The genres the user can pick from.
     */
    public static final String[] GENRES = { "Pop", "Rock", "Hip-Hop",
        "R&B", "Country", "Jazz", "Classical", "Electronic", "Latin",
        "Indie", "Metal", "NA" };
 
    /**
     * Years must be greater than 1k
     */
    public static final int MIN_YEAR = 1000;
 
    /**
     * Years must be less than or equal current year
     */
    public static final int MAX_YEAR = 2026;
 
    private Scanner input;
    private Playlist playlist;
    private LinkedChain<QueueEntry> queue;
    private MusicAnalyzer analyzer;
 
    /**
     * Creates a MusicApp with an empty playlist and an empty queue O(1)
     */
    public MusicApp(Scanner input, String playlistName)
    {
        if (input == null)
        {
            throw new IllegalArgumentException("Scanner cannot be null.");
        }
        this.input = input;
        playlist = new Playlist(playlistName);
        queue = new LinkedChain<QueueEntry>();
        analyzer = new MusicAnalyzer(playlist);
    }
 
 
    /**
     * Asks for a playlist name creates a MusicApp and runs that jawn.
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name your playlist: ");
        String name = scanner.nextLine().trim();
        MusicApp app = new MusicApp(scanner, name);
        app.run();
    }
 
 
    /**
     * Shows the menu, reads the user choice, and calls the matching
     * method. Repeats until the user picks 9 and then it quits
     */
    public void run()
    {
        int choice = 0;
        while (choice != 9)
        {
            printMenu();
            choice = readOptions();
            if (choice == 1)
            {
                addSongToPlaylist();
            }
            else if (choice == 2)
            {
                removeSongFromPlaylist();
            }
            else if (choice == 3)
            {
                viewPlaylist();
            }
            else if (choice == 4)
            {
                playFromPlaylist();
            }
            else if (choice == 5)
            {
                queueSong();
            }
            else if (choice == 6)
            {
                skip();
            }
            else if (choice == 7)
            {
                viewQueue();
            }
            else if (choice == 8)
            {
                wrapped();
            }
            else
            {
                System.out.println("Goodbye!");
            }
        }
    }
 
 
    /**
     * Reads a menu choice, re-prompting until it is a whole number from
     * 1 to 9.
     */
    public int readOptions()
    {
        int choice = 0;
        while (choice < 1 || choice > 9)
        {
            String line = readLine("Choose an option (1-9): ");
            try
            {
                choice = Integer.parseInt(line);
            }
            catch (NumberFormatException e)
            {
                choice = 0; // not an option so it fails the check below
            }
            if (choice < 1 || choice > 9)
            {
                System.out.println("Please enter a whole number from 1 to 9.");
            }
        }
        return choice;
    }
 
 
    /**
     * Prints every song in the playlist in order and lets user know if empty
     * O(n).
     */
    public void viewPlaylist()
    {
        Song[] songs = playlist.getSongs();
        if (songs.length == 0)
        {
            System.out.println("Playlist is empty.");
            return;
        }
        System.out.println("Playlist: " + playlist.getName());
        for (int i = 0; i < songs.length; i++)
        {
            System.out.println("  " + (i + 1) + ". " + describe(songs[i]));
        }
    }
 
 
    /**
     * adds song to playlist
     */
    public void addSongToPlaylist()
    {
        String title = readTitle();
        String artist = readLine("Artist: ");
        int year = readYear();
        String genre = readGenre();
 
        // confirmnation
        boolean confirmed = false;
        while (!confirmed)
        {
            System.out.println(
                "You entered: " + title + " by " + artist + " in " + year);
            System.out.println("Please double check the title/artist/year.");
            System.out.println("  1. Re-enter title");
            System.out.println("  2. Re-enter artist");
            System.out.println("  3. Re-enter year");
            System.out.println("  4. Looks good");
            int fix = readNumberInRange("Choose 1-4: ", 1, 4);
            if (fix == 1)
            {
                title = readTitle();
            }
            else if (fix == 2)
            {
                artist = readLine("Artist: ");
            }
            else if (fix == 3)
            {
                year = readYear();
            }
            else
            {
                confirmed = true;
            }
        }
 
        Song candidate = new Song(title, artist, year, genre);
        if (playlist.containsSong(candidate))
        {
            System.out.println("That song is already in your playlist.");
            if (!readYesNo("Add it anyway? (y/n): "))
            {
                System.out.println("Song not added.");
                return;
            }
        }
        playlist.addSong(title, artist, year, genre);
        System.out.println("Added " + describe(candidate) + ".");
    }
 
 
    /**
     * Shows the playlist asks which song to remove and removes that jawn
     */
    public void removeSongFromPlaylist()
    {
        Song song = chooseSongFromPlaylist("remove");
        if (song == null)
        {
            return;
        }
        if (playlist.removeSong(song))
        {
            System.out.println("Removed " + describe(song) + ".");
        }
        else
        {
            System.out.println("Song not found.");
        }
    }
 
 
    /**
     * Prints "Now playing: " followed by the song. O(1).
     */
    public void playSong(Song song)
    {
        if (song == null)
        {
            throw new IllegalArgumentException("Cannot play a null song.");
        }
        System.out.println("Now playing: " + describe(song));
    }
 
 
    /**
     * Shows the playlist, asks which song to play, and plays it
     */
    public void playFromPlaylist()
    {
        Song song = chooseSongFromPlaylist("play");
        if (song != null)
        {
            playSong(song);
        }
    }
 
 
    /**
     * Shows the playlist, asks which song to queue, wraps it in a new
     * QueueEntry, and adds it to the end of the queue. O(n).
     */
    public void queueSong()
    {
        Song song = chooseSongFromPlaylist("queue");
        if (song == null)
        {
            return;
        }
        queue.addToEnd(new QueueEntry(song));
        System.out.println("Queued " + describe(song) + ".");
    }
 
 
    /**
     * Removes the first entry from the queue and plays its song.
     */
    public void skip()
    {
        QueueEntry next = queue.remove();
        if (next == null)
        {
            System.out.println("Queue is empty.");
            return;
        }
        playSong(next.getSong());
    }
 
 
    /**
     * Prints every song in the queue in play order, or "Queue is empty."
     * O(n).
     */
    public void viewQueue()
    {
        if (queue.isEmpty())
        {
            System.out.println("Queue is empty.");
            return;
        }
        QueueEntry[] entries =
            queue.toArray(new QueueEntry[queue.size()]);
        System.out.println("Up next:");
        for (int i = 0; i < entries.length; i++)
        {
            System.out.println(
                "  " + (i + 1) + ". " + describe(entries[i].getSong()));
        }
    }
 
 
    /**
     * Prints the song count, favorite artist, and favorite song from the
     * MusicAnalyzer, or "No songs to analyze." if the playlist is empty.
     */
    public void wrapped()
    {
        if (analyzer.getSongCount() == 0)
        {
            System.out.println("No songs to analyze.");
            return;
        }
        System.out.println("===== Your Wrapped =====");
        System.out.println("Songs in playlist: " + analyzer.getSongCount());
        System.out.println("Favorite artist: " + analyzer.getFavoriteArtist());
        System.out.println(
            "Favorite song: " + describe(analyzer.getFavoriteSong()));
    }
 
    // ----------------------------------------------------------
    // Private helpers
    // ----------------------------------------------------------
 
 
    /**
     * Prints the main menu.
     */
    private void printMenu()
    {
        System.out.println();
        System.out.println("1. Add song to playlist");
        System.out.println("2. Remove song from playlist");
        System.out.println("3. View playlist");
        System.out.println("4. Play song from playlist");
        System.out.println("5. Add song to queue");
        System.out.println("6. Skip");
        System.out.println("7. View queue");
        System.out.println("8. View Wrapped");
        System.out.println("9. Quit");
    }
 
 
    /**
     * Shows the playlist with numbers and asks the user to pick a song
     */
    private Song chooseSongFromPlaylist(String action)
    {
        Song[] songs = playlist.getSongs();
        if (songs.length == 0)
        {
            System.out.println("Playlist is empty.");
            return null;
        }
        viewPlaylist();
        int pick = readNumberInRange(
            "Which song do you want to " + action + "? (1-" + songs.length
                + "): ",
            1,
            songs.length);
        return songs[pick - 1];
    }
 
 
    /**
     * Reads a non-empty title, re-prompting if it is empty
     */
    private String readTitle()
    {
        String title = readLine("Title: ");
        while (title.isEmpty())
        {
            System.out.println("Title cannot be empty.");
            title = readLine("Title: ");
        }
        return title;
    }
 
 
    /**
     * Reads a year, re-prompting until it is a whole number with
     * MIN_YEAR < year <= MAX_YEAR.
     */
    private int readYear()
    {
        int year = 0;
        while (year <= MIN_YEAR || year > MAX_YEAR)
        {
            String line = readLine("Year released: ");
            try
            {
                year = Integer.parseInt(line);
            }
            catch (NumberFormatException e)
            {
                year = 0; // again not an option!!!
            }
            if (year <= MIN_YEAR || year > MAX_YEAR)
            {
                System.out.println("Year must be a whole number greater than "
                    + MIN_YEAR + " and at most " + MAX_YEAR + ".");
            }
        }
        return year;
    }
 
 
    /**
     * Shows the genre list and reads the users pick
     */
    private String readGenre()
    {
        System.out.println("Genres:");
        for (int i = 0; i < GENRES.length; i++)
        {
            System.out.println("  " + (i + 1) + ". " + GENRES[i]);
        }
        int pick = readNumberInRange(
            "Choose a genre (1-" + GENRES.length + "): ", 1, GENRES.length);
        return GENRES[pick - 1];
    }
 
 
    /**
     * Reads a whole number re-prompting until it is between low and high
     */
    private int readNumberInRange(String prompt, int low, int high)
    {
        int value = low - 1;
        while (value < low || value > high)
        {
            String line = readLine(prompt);
            try
            {
                value = Integer.parseInt(line);
            }
            catch (NumberFormatException e)
            {
                value = low - 1; // doesn't work
            }
            if (value < low || value > high)
            {
                System.out.println("Please enter a whole number from " + low
                    + " to " + high + ".");
            }
        }
        return value;
    }
 
 
    /**
     * Fck this method
     */
    private boolean readYesNo(String prompt)
    {
        String answer = readLine(prompt).toLowerCase();
        while (!answer.equals("y") && !answer.equals("n"))
        {
            System.out.println("Please enter y or n.");
            answer = readLine(prompt).toLowerCase();
        }
        return answer.equals("y");
    }
 
 
    /**
     * Prints a prompt and reads one line, trimming extra spaces
     */
    private String readLine(String prompt)
    {
        System.out.print(prompt);
        return input.nextLine().trim();
    }
 
 
    /**
     * Formats a song as title by artist in year
     */
    private String describe(Song song)
    {
        return song.getTitle() + " by " + song.getArtist() + " in "
            + song.getYear();
    }
}
