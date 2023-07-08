package coordtxl;

import org.testng.annotations.*;
import coordtxl.coords.HMS;

public class HMSTest {
    /**
     * Test cases
     */
    @Test public static void testHMS( ) {

        HMS h = new HMS(3, 19, 48.23);
        System.out.println("HMS(3, 19, 48.23) == " + h + " == " + h.getVal());

        if (!(h.equals(new HMS(h.getVal())))) {
            System.out.println("Equality test failed: " + h + " != " + new HMS(h.getVal()));
        }

        h = new HMS(41, 30, 42.2);
        System.out.println("41 30 42.2 = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS(-41, 30, 2.2);
        System.out.println("-41 30 2.2 = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS("-41 30 42.2");
        System.out.println("-41 30 42.2 = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS("1:01:02.34567");
        System.out.println("1:01:02.34567 = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS("1:01:02.34567");
        System.out.println("1:01:02.34567 = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS(-0., 15, 33.3333);
        System.out.println("-0 15 33.3333 = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS(-0.0001);
        System.out.println("-0.0001 = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS(121.39583332 / 15.);
        System.out.println("121.39583332/15. = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS(121.09583332 / 15.);
        System.out.println("121.09583332/15. = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS(-121.39583332 / 15.);
        System.out.println("-121.39583332/15. = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));

        h = new HMS(-121.09583332 / 15.);
        System.out.println("-121.09583332/15. = " + h + " = " + h.getVal() + " = " + new HMS(h.getVal()));
    }
}
