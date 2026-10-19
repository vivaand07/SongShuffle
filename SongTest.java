import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// -------------------------------------------------------------------------
/**
 * Tests Song
 * 
 *  @author catherinearockia
 *  @version Oct 10, 2026
 */
class SongTest {

    
    private Song a;
    private Song b;
    private Song c;
    private Song d;
    private Song e;
    private Song f;
    
    /**
     * Sets up test cases
     * 
     */
    
    @BeforeEach
    public void setUp() {
        
        a = new Song("Money so Big", "Yeat", 2021, "Rap");
        b = new Song("Money so Big", "Yeat", 2022, "Rap");
        c = new Song("Money so Big", "Not Yeat", 2021, "Rap");
        d = new Song("Money so Big", "Not Yeat", 2022, "Rap");
        e = new Song("Money not so Big", "Yeat", 2021, "Rap");
        f = new Song("Money so Big", "Yeat", 2021, "Rap");
        
    }
    
    // ----------------------------------------------------------
    /**
     * Tests getTitle()
     */
    @Test
    public void testGetTitle() {
        assertEquals("Money so Big", a.getTitle());
    }
    
    // ----------------------------------------------------------
    /**
     * Tests getArtist()
     */
    @Test
    public void testGetArtist() {
        assertEquals("Yeat", a.getArtist());
    }
    
    // ----------------------------------------------------------
    /**
     * Tests getYear()
     */
    @Test
    public void testGetYear() {
        assertEquals(2021, a.getYear());
    }
    
    // ----------------------------------------------------------
    /**
     * Tests getGenre()
     */
    @Test
    public void testGetGenre() {
        assertEquals("Rap", a.getGenre());
        
    }
    
    // ----------------------------------------------------------
    /**
     * Tests equals()
     */
    @Test
    public void testEquals() {
        assertTrue(a.equals(a));
        assertFalse(a.equals(b));
        assertFalse(a.equals(c));
        assertFalse(a.equals(d));
        assertFalse(a.equals(e));
        assertTrue(a.equals(f));
        assertFalse(a.equals(null));
        assertFalse(a.equals("hi"));
    }
}
