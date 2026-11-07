package coordtxl.coords;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import java.util.HashMap;
import coordtxl.geom.Point2D;

/**
 * The projection is read from CTYPE1 (0.1.6).  Until then WCSTransform took
 * characters 3-6 ("--SI" for "RA---SIN"), recognized no projection, and
 * treated every image as linear.  Expected world coordinates are from astropy.
 */
public class ProjectionTypeTest {

    private static WCSTransform transform( String ctype1, String ctype2 ) {
        HashMap<String,Object> kw = new HashMap<String,Object>( );
        kw.put("NAXIS1", 128.0); kw.put("NAXIS2", 128.0);
        kw.put("CTYPE1", ctype1); kw.put("CTYPE2", ctype2);
        kw.put("CRVAL1", 299.86875); kw.put("CRVAL2", 40.6666667);
        kw.put("CRPIX1", 65.0); kw.put("CRPIX2", 65.0);
        kw.put("CDELT1", -12.0 / 3600.0); kw.put("CDELT2", 12.0 / 3600.0);
        kw.put("EQUINOX", 2000.0);
        return new WCSTransform( new MapKeywordProvider( kw ) );
    }

    @Test
    public void projectionCodes( ) {
        String[][] cases = { { "RA---SIN", "-SIN" }, { "RA--SIN", "-SIN" }, { "RA---TAN", "-TAN" },
                             { "RA---TAN-SIP", "-TAN" }, { "GLON-SIN", "-SIN" }, { "ELON-ARC", "-ARC" },
                             { "RA---NCP", "-NCP" } };
        for ( String[] c : cases ) {
            WCSTransform w = transform( c[0], "DEC--SIN" );
            assertTrue( w.isWCS( ), c[0] + ": not a WCS" );
            assertEquals( w.ptype, c[1], c[0] );                 // package-private
        }
    }

    @Test
    public void sinCorner( ) {
        // FITS pixel (1,1) of a 128x128, 12"/pixel SIN image at dec +40.67:
        // astropy gives RA 300.14910666, Dec 40.45299275
        WCSTransform w = transform( "RA---SIN", "DEC--SIN" );
        Point2D p = new Point2D( 1.0, 1.0 );
        w.imageToWorldCoords( p, false );
        assertEquals( p.getX( ), 300.14910666, 1e-7 );
        assertEquals( p.getY( ), 40.45299275, 1e-7 );
    }
}
