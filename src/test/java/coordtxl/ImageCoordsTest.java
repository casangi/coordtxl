package coordtxl;

import org.testng.annotations.*;
import coordtxl.coords.ImageCoords;

class ImageCoordsTest {

    /**
     * Test cases
     */
    @Test public void testImageCoords( ) {
        ImageCoords c1 = new ImageCoords(100., 200.);
        ImageCoords c2 = new ImageCoords("100.", "200.");

        System.out.println("these coords should each be the same:");
        System.out.println(c1);
        System.out.println(c2);

        // test the "box" method (get 2 points given a radius)
        ImageCoords c3 = new ImageCoords(100, 100), c4, c5;
        ImageCoords[] ar1 = c3.box(10);
        c4 = ar1[0];
        c5 = ar1[1];
        System.out.println("box of radius 10 with center at (100, 100):");
        System.out.println(c4);
        System.out.println(c5);
    }
}
