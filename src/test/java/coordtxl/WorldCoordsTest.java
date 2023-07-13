package coordtxl;

import org.testng.annotations.*;
import coordtxl.coords.WorldCoordinates;
import coordtxl.coords.WorldCoords;
import coordtxl.coords.HMS;
import coordtxl.coords.DMS;

class WorldCoordsTest {

    /**
     * Test cases
     */
    @Test public void testWorldCoords( ) {
        WorldCoords c1 = new WorldCoords(49.95096, 41.51173);
        WorldCoords c2 = new WorldCoords(3, 19, 48.2304, 41, 30, 42.228);
        WorldCoords c3 = new WorldCoords(new HMS(3, 19, 48.2304), new DMS(41, 30, 42.228));
        WorldCoords c4 = new WorldCoords(new HMS(c1.getRA()), new DMS(c1.getDec()));
        WorldCoords c5 = new WorldCoords("3 19 48.2304", "+41 30 42.228", 2000.0);
        WorldCoords c6 = new WorldCoords("3:19:48.2304", "+41:30:42.228", 2000.0);
        WorldCoords c7 = new WorldCoords(Double.toString(49.95096 / 15.), "41.51173", 2000.0);

        System.out.println("these coords should all be the same (or very close):");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c4);
        System.out.println(c5);
        System.out.println(c6);
        System.out.println(c7);

        c1 = new WorldCoords(49.95096, -41.51173);
        c2 = new WorldCoords(3, 19, 48.2304, -41, 30, 42.228);
        c3 = new WorldCoords(new HMS(3, 19, 48.2304), new DMS(-41, 30, 42.228));
        c4 = new WorldCoords(new HMS(c1.getRA()), new DMS(c1.getDec()));
        c5 = new WorldCoords("3 19 48.2304", "-41 30 42.228", 2000.0);
        c6 = new WorldCoords("3:19:48.2304", "-41:30:42.228", 2000.0);
        c7 = new WorldCoords(Double.toString(49.95096 / 15.), "-41.51173", 2000.0);

        System.out.println("Here is the same with negative dec:");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c4);
        System.out.println(c5);
        System.out.println(c6);
        System.out.println(c7);

        WorldCoords c8 = new WorldCoords("3:19", "+41:30", 2000.0);
        WorldCoords c9 = new WorldCoords("3", "+41", 2000.0);
        System.out.println("And with missing minutes, ... seconds, ...:");
        System.out.println(c8);
        System.out.println(c9);

        // test the "box" method (get 2 points given a radius)
        WorldCoordinates c10 = new WorldCoords("03:19:48.243", "+41:30:40.31"), c11, c12;
        WorldCoordinates[] ar1 = c10.box(7.05);
        c11 = ar1[0];
        c12 = ar1[1];
        System.out.println("box of radius 7.05 with center at (03:19:48.243, +41:30:40.31):");
        System.out.println(c11);
        System.out.println(c12);

        // test values at or near 0,0
        WorldCoords c13 = new WorldCoords("0", "+41:30:40.31");
        System.out.println("With ra = 0.0: " + c13 + " ("
                           + c13.getRA().getVal() + ", " + c13.getDec().getVal() + ")");

        WorldCoords c14 = new WorldCoords("0.0", "-0.0");
        System.out.println("With ra = 0.0, dec = -0.0: " + c14 + " ("
                           + c14.getRA().getVal() + ", " + c14.getDec().getVal() + ")");

        WorldCoords c15 = new WorldCoords("0:0:1", "-0:1:1");
        System.out.println("With ra = 0:0:1, dec = -0:1:1: " + c15 + " ("
                           + c15.getRA().getVal() + ", " + c15.getDec().getVal() + ")");

        // test conversion between h:m:s and deg and back
        WorldCoords c16 = new WorldCoords("22:45:22.74", "-39:34:14.63");
        System.out.println("test conversion between h:m:s and deg and back");
        System.out.println("22:45:22.74 -39:34:14.63 == " + c16 + " == " + c16.toString());

        String[] ar2 = c16.format();
        System.out.println(" == " + ar2[0] + " " + ar2[1]);
        WorldCoords c17 = new WorldCoords(ar2[0], ar2[1]);
        System.out.println(" == " + c17);

        // test equinox conversion
        WorldCoords c18 = new WorldCoords(0.0, 0.0, 1950.);
        String[] ar3 = c18.format(1950.);
        System.out.println("00:00:00 B1950 == " + c18 + " J2000 == " + ar3[0] + " " + ar3[1] + " B1950");
    }
}

