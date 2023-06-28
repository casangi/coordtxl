//=== File Prolog =============================================================
// This is a derived class which isolates the dependency on nom.tam.fits for
// state management.
//=== End File Prolog =========================================================
package coordtlx.coords;

import nom.tam.fits.Header;
import nom.tam.fits.HeaderCardException;


public class NomTamWCSTransform extends WCSTransform {

    public NomTamWCSTransform(WCSKeywordProvider head) {
        super(head);
    }

    public NomTamWCSTransform(double cra,
                        double cdec,
                        double xsecpix,
                        double ysecpix,
                        double xrpix,
                        double yrpix,
                        int nxpix,
                        int nypix,
                        double rotate,
                        int equinox,
                        double epoch,
                        String proj) {
        super( cra, cdec, xsecpix, ysecpix, xrpix,
               yrpix, nxpix, nypix, rotate, equinox,
               epoch, proj );
    }

    /**
     * modify the header to add WCS information
     *
     * @param header FITS header to to fill
     * @throws HeaderCardException if there is trouble from the addValue method
     */
    public void fillHeader(Header header) throws HeaderCardException {
        // Added by Chris S.
        // Use lookup table from http://tdc-www.harvard.edu/software/wcstools/wcs.h.html
        // CRVAL1 is xref; CRVAL2 is yref
        header.deleteKey("CRVAL1");
        header.addValue("CRVAL1", xref, "X reference coordinate value (degrees)");
        header.deleteKey("CRVAL2");
        header.addValue("CRVAL2", yref, "Y reference coordinate value (degrees)");
        // CRPIX1 is xrefpix; CRPIX2 is yrefpix
        header.deleteKey("CRPIX1");
        header.addValue("CRPIX1", xrefpix, "X reference pixel");
        header.deleteKey("CRPIX2");
        header.addValue("CRPIX2", yrefpix, "Y reference pixel");
        //if (rotmat <= 0) {
        //  // CDi_j is cdij
        //header.deleteKey("CD1_1");
        //header.addValue("CD1_1", cd11, "rotation matrix element 1,1");
        //header.deleteKey("CD2_1");
        //header.addValue("CD2_1", cd21, "rotation matrix element 2,1");
        //header.deleteKey("CD1_2");
        //header.addValue("CD1_2", cd12, "rotation matrix element 1,2");
        //header.deleteKey("CD2_2");
        //header.addValue("CD2_2", cd22, "rotation matrix element 2,2");
        //} else {
        // CDELT1 is xinc; CDELT2 is yinc; CROTA2 is rot
        header.deleteKey("CDELT1");
        header.addValue("CDELT1", xinc, "X coordinate increment (degrees)");
        header.deleteKey("CDELT2");
        header.addValue("CDELT2", yinc, "Y coordinate increment (degrees)");
        header.deleteKey("CROTA2");
        header.addValue("CROTA2", rot, "counterclockwise rotation around axis (degrees)");
        //}
        // EPOCH is equinox; DATE-OBS is epoch (years)
        header.deleteKey("EPOCH");
        header.addValue("EPOCH", equinox, "Equinox of coordinates, e.g. 1950.0");
        header.deleteKey("DATE-OBS");
        header.addValue("DATE-OBS", epoch, "Epoch of coordinates");
        // NAXIS1 is nxpix; NAXIS2 is nypix
        //header.deleteKey("NAXIS1");
        //header.addValue("NAXIS1", nxpix, "Number of pixels in X-dimension");
        //header.deleteKey("NAXIS2");
        //header.addValue("NAXIS2", nypix, "Number of pixels in Y-dimension");
        // CTYPE1 is c1type; CTYPE2 is c2type; RADECSYS is radecsys
        header.deleteKey("CTYPE1");
        header.addValue("CTYPE1", c1type + ptype, "1st coordinate type code:  RA--, GLON, ELON...");
        header.deleteKey("CTYPE2");
        header.addValue("CTYPE2", c2type + ptype, "2nd coordinate type code:  DEC-, GLAT, ELAT...");
        header.deleteKey("RADECSYS");
        header.addValue("RADECSYS", radecsys, "Reference frame:  FK4, FK4-NO-E, FK5, GAPPT");
    }

    /**
     * @return the x pixel size in arcseconds
     */
    public double getXArcsecPerPix() {
        return xinc * 3600;
    }


}
