
// -------------------------------------------------------------------------
/**
 *  Creates Song object
 * 
 *  @author catherinearockia
 *  @version Oct 10, 2026
 */
public class Song {

    private String title;
    private String artist;
    private int year;
    private String genre;
    
    // ----------------------------------------------------------
    /**
     * Create a new Song object.
     * @param title
     * @param artist
     * @param year
     * @param genre
     */
    
    public Song (String title, String artist, int year, String genre) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
    }
    
    // ----------------------------------------------------------
    /**
     * @return title
     */
    public String getTitle(){
        return title;
    }
    
    // ----------------------------------------------------------
    /**
     * @return artist
     */
    public String getArtist() {
        return artist;
    }

    // ----------------------------------------------------------
    /**
     * @return year
     */
    public int getYear() {
        return year;
    }

    // ----------------------------------------------------------
    /**
     * @return genre
     */
    public String getGenre() {
        return genre;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Checks to see if two song objects are equal based on title,
     *  artist, and year. Returns true if yes, returns false otherwise.
     * 
     */
    @Override
    public boolean equals(Object other) {
        
       if (this == other){
           return true;
       }
       
       if (other == null) {
           return false;
       }
       
       if (this.getClass() != other.getClass()) {
           
           return false;
       }
       
       if (this.getTitle().equalsIgnoreCase(((Song)other).getTitle()) && this.getYear() == ((Song)other).getYear() && this.getArtist().equalsIgnoreCase(((Song)other).getArtist())) {
           return true;
       }
       
       return false;
    }
}

