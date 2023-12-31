/*
 * $Id: HMS.java,v 1.4 2009/04/21 13:31:17 abrighto Exp $
 */

package coordtxl.coords;

import coordtxl.util.StringUtil;
import java.io.Serializable;

/**
 * Class representing a value of the form "hours:min:sec".
 *
 * @author Allan Brighton
 * @version $Revision: 1.4 $
 *
 * Portions of this file Copyright (C) 2023
 * Associated Universities, Inc. Washington DC, USA.
 *
 */
public class HMS implements Serializable {

    /**
     * On the handling of -0: from the javadoc for Double.equals():
     * "If d1 represents +0.0 while d2 represents -0.0, or vice versa,
     * the equal test has the value false, even though +0.0==-0.0 has the
     * value true."
     * The test for 0.0 != -0.0 only works with Double.equals(minusZero).
     * This case shows up in HMS values with zero hours and negative values,
     * such as "-00 24 32"
     */
    private static final Double MINUS_ZERO = -0.0;

    // Number formats for 2 digit hours and minutes. Replaces:
    //
    //     NumberFormat NF = NumberFormat.getInstance(Locale.US)
    //     NF.setMinimumIntegerDigits(2);
    //     NF.setMaximumIntegerDigits(2);
    //     NF.setMaximumFractionDigits(0);
    //
    private static String fmt_hr_min( double d ) {
        double dbl = Math.round(d);
        // Java's String.split(regex) takes a regular expression, but the code
        // that JSweet generates uses a split which takes a literal string so
        // split("\\.") works in Java but fails in the generated JavaScript
        String dblv[] = Double.toString(dbl).replace(".","@").split("@");
        if ( dblv.length < 1 ) return "00";

        String result = "";
        for ( int padding = dblv[0].length( ); padding < 2; ++padding ) {
            result += "0";
        }
        for ( int number = dblv[0].length( ) > 2 ? dblv[0].length( ) - 2 : 0;
              number < dblv[0].length( ); ++number ) {
            result += dblv[0].charAt(number);
        }
        return result;
    }

    // Number formats for seconds with 2 digits and 3 decimal
    // places. Replaces:
    //
    //     NumberFormat NF_SEC = NumberFormat.getInstance(Locale.US);
    //     NF_SEC.setMinimumIntegerDigits(2);
    //     NF_SEC.setMaximumIntegerDigits(2);
    //     NF_SEC.setMinimumFractionDigits(3);
    //     NF_SEC.setMaximumFractionDigits(3);
    //
    private static String fmt_sec( double d ) {
        double dbl = Math.round(d * 1000.0) / 1000.0;
        // Java's String.split(regex) takes a regular expression, but the code
        // that JSweet generates uses a split which takes a literal string so
        // split("\\.") works in Java but fails in the generated JavaScript
        String dblv[] = Double.toString(dbl).replace(".","@").split("@");

        if ( dblv.length == 0 ) return "00.000";
        else if ( dblv.length < 2 ) return dblv[0] + ".000";
        String result = "";
        for ( int padding = dblv[0].length( ); padding < 2; ++padding ) {
            result += "0";
        }
        for ( int number = dblv[0].length( ) > 2 ? dblv[0].length( ) - 2 : 0;
              number < dblv[0].length( ); ++number ) {
            result += dblv[0].charAt(number);
        }
        result += ".";
        int decimal = 0;
        for ( ; decimal < 3 && decimal < dblv[1].length( ); ++decimal ) {
            result += dblv[1].charAt(decimal);
        }
        for ( ; decimal < 3; ++decimal ) {
            result += "0";
        }
        return result;
    }

    /**
     * number of hours
     */
    private int hours;

    /**
     * number of minutes
     */
    private int min;

    /**
     * number of seconds
     */
    private double sec;

    /**
     * value converted to decimal
     */
    private double val;

    /**
     * set to 1 or -1
     */
    private byte sign = 1;

    /* true if value has been initialized */
    private boolean initialized = false;


    /**
     * Default constructor: initialize to null values
     */
    public HMS() {
    }

    /**
     * Initialize with the given hours, minutes and seconds.
     */
    public HMS(double hours, int min, double sec) {
        set(hours, min, sec);
    }

    /**
     * Initialize from a decimal hours value and calculate H:M:S.sss.
     */
    public HMS(double val) {
        setVal(val);
    }

    /**
     * Copy constructor
     */
    public HMS(HMS hms) {
        setVal(hms.val);
    }

    /**
     * Initialize from a string value, in format H:M:S.sss, hh, or H M
     * S.  If the value is not in H:M:S and is not an integer (has a
     * decimal point), assume the value is in deg convert to hours by
     * dividing by 15. (Reason: some catalog servers returns RA in h:m:s
     * while others return it in decimal deg.)
     */
    public HMS(String s) {
        this(s, false);
    }

    /**
     * Initialize from a string value, in format H:M:S.sss, hh, or
     * H M S.  If the value is not in H:M:S and is not an
     * integer (has a decimal point), and hflag is true,
     * assume the value is in deg and convert to hours by dividing by 15.
     *
     * @param s the RA string
     * @param hflag if true, assume RA is always in hours, otherwise, if it has a decimal point,
     * assume deg
     */
    public HMS(String s, boolean hflag) {
        s = StringUtil.replace(s, ",", "."); // Treat ',' like '.', by request
        double[] vals = {0.0, 0.0, 0.0};
        // Java's String.split(regex) takes a regular expression, but the code
        // that JSweet generates uses a split which takes a literal string so
        // split(":| ") works in Java but fails in the generated JavaScript
        String[] toks = s.replace(":","@").replace(" ","@").replace("@@","@").split("@");
        int n = 0;
        while ( n < 3 && n < toks.length ) {
            vals[n] = Double.valueOf(toks[n]);
            n += 1;
        }
        if (n >= 2) {
            set(vals[0], (int) vals[1], vals[2]);
        } else if (n == 1) {
            if (!hflag && s.indexOf('.') != -1) {
                setVal(vals[0] / 15.);
            } else {
                setVal(vals[0]);
            }
        } else {
            throw new RuntimeException("Expected a string of the form hh:mm:ss.sss, but got: '" + s + "'");
        }
    }

    /**
     * Set the hours, minutes and seconds.
     */
    public void set(double hours, int min, double sec) {
        this.hours = (int) hours;
        this.min = min;
        this.sec = sec;

        val = (sec / 60.0 + min) / 60.0;

        if (hours < 0.0 || Double.valueOf(hours).equals(MINUS_ZERO)) {
            val = hours - val;
            this.hours = -this.hours;
            sign = -1;
        } else {
            val = this.hours + val;
            sign = 1;
        }
        initialized = true;
    }

    /**
     * Set from a decimal value (hours) and calculate H:M:S.sss.
     */
    public void setVal(double val) {
        this.val = val;

        double v = val; // check also for neg zero
        if (v < 0.0 || Double.valueOf(v).equals(MINUS_ZERO)) {
            sign = -1;
            v = -v;
        } else {
            sign = 1;
        }

        double dd = v + 0.0000000001;
        hours = (int) dd;
        double md = (dd - hours) * 60.;
        min = (int) md;
        sec = (md - min) * 60.;
        initialized = true;
    }

    /**
     * Return the value as a String in the form hh:mm:ss.sss.
     * Seconds are formatted with leading zero if needed.
     * The seconds are formatted with 3 digits of precision.
     */
    public String toString() {
        String secs = fmt_sec(sec);

        // sign
        String signStr;
        if (sign == -1) {
            signStr = "-";
        } else {
            signStr = "";
        }

        return signStr
                + fmt_hr_min(hours)
                + ":"
                + fmt_hr_min(min)
                + ":"
                + secs;
    }

    /**
     * Return the value as a String in the form hh:mm:ss.sss,
     * or if showSeconds is false, hh:mm.
     */
    public String toString(boolean showSeconds) {
        if (showSeconds) {
            return toString();
        }

        // sign
        String signStr;
        if (sign == -1) {
            signStr = "-";
        } else {
            signStr = " ";
        }

        return signStr
                + fmt_hr_min(hours)
                + ":"
                + fmt_hr_min(min);
    }

    /**
     * Return true if this object has been initialized with a valid value
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Return the number of hours (not including minutes or seconds)
     */
    public int getHours() {
        return hours;
    }

    /**
     * Return the number of minutes (not including hours or seconds)
     */
    public int getMin() {
        return min;
    }

    /**
     * Return the number of seconds (not including hours and minutes)
     */
    public double getSec() {
        return sec;
    }

    /**
     * Return the value (fractional number of hours) as a double
     */
    public double getVal() {
        return val;
    }

    /**
     * Return the sign of the value
     */
    public byte getSign() {
        return sign;
    }

    /**
     * Define equality based on the value
     */
    public boolean equals(Object obj) {
        return (obj instanceof HMS && val == ((HMS) obj).val);
    }
}
