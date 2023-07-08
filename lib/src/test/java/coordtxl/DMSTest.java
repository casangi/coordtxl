package coordtxl;

import org.testng.annotations.*;
import coordtxl.coords.DMS;

public class DMSTest {

    /**
     * Test cases
     */
    @Test public void testDMS( ) {

        DMS d = new DMS(3, 19, 48.23);
        System.out.println("DMS(3, 19, 48.23) == " + d + " == " + d.getVal());

        if (!(d.equals(new DMS(d.getVal())))) {
            System.out.println("Equality test failed: " + d + " != " + new DMS(d.getVal()));
        }

        d = new DMS(41, 30, 42.2);
        System.out.println("41 30 42.2 = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS(-41, 30, 2.2);
        System.out.println("-41 30 2.2 = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS("-41 30 42.2");
        System.out.println("-41 30 42.2 = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS("1:01:02.34567");
        System.out.println("1:01:02.34567 = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS("1:01:02.34567");
        System.out.println("1:01:02.34567 = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS(-0., 15, 33.3333);
        System.out.println("-0 15 33.3333 = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS(-0.0001);
        System.out.println("-0.0001 = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS(121.39583332 / 15.);
        System.out.println("121.39583332/15. = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS(121.09583332 / 15.);
        System.out.println("121.09583332/15. = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS(-121.39583332 / 15.);
        System.out.println("-121.39583332/15. = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));

        d = new DMS(-121.09583332 / 15.);
        System.out.println("-121.09583332/15. = " + d + " = " + d.getVal() + " = " + new DMS(d.getVal()));
    }
}
