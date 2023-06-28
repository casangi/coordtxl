package coordtxl;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import com.google.gson.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;
import coordtlx.coords.WCSTransform;
import coordtlx.coords.WCSKeywordProvider;
import coordtlx.coords.WorldCoords;
import java.util.Map;
import java.util.AbstractMap;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.awt.geom.Point2D;

public class CasaImageLabelTest {


    public class MapKeywordProvider implements WCSKeywordProvider {

        private AbstractMap<String,Object> wcsMap;

        public MapKeywordProvider( AbstractMap<String,Object> values ) {
            wcsMap = new HashMap<String,Object>( );
            for ( String key : values.keySet( ) ) {
                if ( values.get(key) instanceof ArrayList ) {
                    ArrayList contain = (ArrayList) values.get(key);
                    wcsMap.put( key, contain.clone( ) );
                    int count = 1;
                    for ( Object v : contain ) {
                        wcsMap.put( key+count, v );
                        count += 1;
                    }
                } else {
                    wcsMap.put( key, values.get(key) );
                }
            }
        }

        public boolean findKey(String key) {
            System.out.println("findKey:\t" + key);
            return wcsMap.containsKey(key);
        }

        public String getStringValue(String key) { return getStringValue(key,null); }
        public String getStringValue(String key, String defaultValue) {
            System.out.println("getStringValue:\t" + key + ", " + (wcsMap.containsKey(key) ? wcsMap.get(key).toString( ) : ("<default: " + defaultValue + ">")) );
            return wcsMap.containsKey(key) ? wcsMap.get(key).toString( ) : defaultValue;
        }

        public double getDoubleValue(String key) { return getDoubleValue( key, 0.0 ); }
        public double getDoubleValue(String key, double defaultValue) {
            if ( ! wcsMap.containsKey(key) ) {
                System.out.println("getDoubleValue:\t" + key + ", " + ("<default: " + defaultValue + ">") );
                return defaultValue;
            }
            Object result = wcsMap.get(key);
            if ( result instanceof Number ) {
                System.out.println("getDoubleValue:\t" + key + ", " + ((Number) wcsMap.get(key)).doubleValue( ));
                return ((Number) result).doubleValue( );
            } else {
                System.out.println("getDoubleValue:\t" + key + ", " + "<default: " + defaultValue + ">");
                return defaultValue;
            }
        }

        public float getFloatValue(String key) { return getFloatValue(key,(float)0.0); }
        public float getFloatValue(String key, float defaultValue) {
            if ( ! wcsMap.containsKey(key) ) {
                System.out.println("getFloatValue:\t" + key + ", " + ("<default: " + defaultValue + ">") );
                return defaultValue;
            }
            Object result = wcsMap.get(key);
            if ( result instanceof Number ) {
                System.out.println("getFloatValue:\t" + key + ", " + ((Number) wcsMap.get(key)).floatValue( ));
                return ((Number) result).floatValue( );
            } else {
                System.out.println("getFloatValue:\t" + key + ", " + "<default: " + defaultValue + ">");
                return defaultValue;
            }
        }

        public int getIntValue(String key) { return getIntValue(key,0); }
        public int getIntValue(String key, int defaultValue) {
            if ( ! wcsMap.containsKey(key) ) {
                System.out.println("getIntValue:\t" + key + ", " + ("<default: " + defaultValue + ">") );
                return defaultValue;
            }
            Object result = wcsMap.get(key);
            if ( result instanceof Number ) {
                System.out.println("getIntValue:\t" + key + ", " + ((Number) wcsMap.get(key)).intValue( ));
                return ((Number) result).intValue( );
            } else {
                System.out.println("getIntValue:\t" + key + ", " + "<default: " + defaultValue + ">");
                return defaultValue;
            }
        }

        public String toString( ) {
            boolean comma_needed = false;
            StringBuilder sb = new StringBuilder( "{ " );
            for ( String key : wcsMap.keySet( ) ) {
                if ( comma_needed ) sb.append( ", ");
                else comma_needed = true;
                sb.append(key);
                sb.append("=");
                Object o = wcsMap.get(key);
                if ( o instanceof String ) {
                    // Quote strings
                    sb.append("\"");
                    sb.append(o);
                    sb.append("\"");
                } else {
                    // Quote elements of list of strings
                    if ( o instanceof List &&
                         ((List)o).size( ) > 0 &&
                         ((List)o).get(0) instanceof String ) {
                        sb.append( "[" );
                        sb.append( ((List<String>)o).stream( )
                                   .map(s -> "\"" + s + "\"")
                                   .collect(Collectors.joining(", ")) );
                        sb.append( "]" );
                    } else sb.append(o);
                }
            }
            sb.append(" }");
            return sb.toString( );
        }
    }

    //
    // The expectation is that GSON will not be used in JavaScript and instead
    // the native JSON encode/decode will be used...
    public class GsonHeader extends coordtxl.fits.Header {
        @SerializedName("ALTRPIX") private double _AltRPix;
        public double AltRPix( ) { return _AltRPix; }
        @SerializedName("ALTRVAL") private double _AltRVal;
        public double AltRVal( ) { return _AltRVal; }
        @SerializedName("BITPIX") private long _BitPix;
        public long BitPix( ) { return _BitPix; }
        @SerializedName("BSCALE") private double _BScale;
        public double BScale( ) { return _BScale; }
        @SerializedName("BTYPE") private String _BType;
        public String BType( ) { return _BType; }
        @SerializedName("BUNIT") private String _BUnit;
        public String BUnit( ) { return _BUnit; }
        @SerializedName("BZERO") private double _BZero;
        public double BZero( ) { return _BZero; }
        @SerializedName("CDELT") private double _CDelt[];
        public double[] CDelt( ) { return _CDelt == null ? new double[0] : _CDelt; }
        @SerializedName("CHNCHNKS") private long _ChnChnks;
        public long ChnChnks( ) { return _ChnChnks; }
        @SerializedName("COMMENT") private String _Comment[];
        public String[] Comment( ) { return _Comment == null ? new String[0] : _Comment; }
        @SerializedName("CRPIX") private double _CRPix[];
        public double[] CRPix( ) { return _CRPix == null ? new double[0] : _CRPix; }
        @SerializedName("CRVAL") private double _CRVal[];
        public double[] CRVal( ) { return _CRVal == null ? new double[0] : _CRVal; }
        @SerializedName("CTYPE") private String[] _CType;
        public String[] CType( ) { return _CType == null ? new String[0] : _CType; }
        @SerializedName("CUNIT1") private String _CUnit1;
        public String CUnit1( ) { return _CUnit1; }
        @SerializedName("CUNIT2") private String _CUnit2;
        public String CUnit2( ) { return _CUnit2; }
        @SerializedName("CUNIT3") private String _CUnit3;
        public String CUnit3( ) { return _CUnit3; }
        @SerializedName("CUNIT4") private String _CUnit4;
        public String CUnit4( ) { return _CUnit4; }
        @SerializedName("DATE") private String _Date;
        public String Date( ) { return _Date; }
        @SerializedName("DATE-private OBS") String _DateObs;
        public String DateObs( ) { return _DateObs; }
        @SerializedName("DISTANCE") private double _Distance;
        public double Distance( ) { return _Distance; }
        @SerializedName("END") private String _End;
        public String End( ) { return _End; }
        @SerializedName("EQUINOX") private double _Equinox;
        public double Equinox( ) { return _Equinox; }
        @SerializedName("EXTEND") private boolean _Extend;
        public boolean Extend( ) { return _Extend; }
        @SerializedName("HISTORY") private String _History[];
        public String[] History( ) { return _History == null ? new String[0] : _History; }
        @SerializedName("INSTRUME") private String _Instrume;
        public String Instrume( ) { return _Instrume; }
        @SerializedName("LATPOLE") private double _LatPole;
        public double LatPole( ) { return _LatPole; }
        @SerializedName("LONPOLE") private double _LonPole;
        public double LonPole( ) { return _LonPole; }
        @SerializedName("MEMAVAIL") private double _MemAvail;
        public double MemAvail( ) { return _MemAvail; }
        @SerializedName("MEMREQ") private double _MemReq;
        public double MemReq( ) { return _MemReq; }
        @SerializedName("MPIPROCS") private long _MPIProcs;
        public long MPIProcs( ) { return _MPIProcs; }
        @SerializedName("NAXIS") private long _NAxis[];
        public long[] NAxis( ) { return _NAxis == null ? new long[0] : _NAxis; }
        @SerializedName("OBJECT") private String _Object;
        public String Object( ) { return _Object; }
        @SerializedName("OBSDEC") private double _ObsDec;
        public double ObsDec( ) { return _ObsDec; }
        @SerializedName("OBSERVER") private String _Observer;
        public String Observer( ) { return _Observer; }
        @SerializedName("OBSGEO-private X") double _ObsGeoX;
        public double ObsGeoX( ) { return _ObsGeoX; }
        @SerializedName("OBSGEO-private Y") double _ObsGeoY;
        public double ObsGeoY( ) { return _ObsGeoY; }
        @SerializedName("OBSGEO-private Z") double _ObsGeoZ;
        public double ObsGeoZ( ) { return _ObsGeoZ; }
        @SerializedName("OBSRA") private double _ObsRA;
        public double ObsRA( ) { return _ObsRA; }
        @SerializedName("ORIGIN") private String _Origin;
        public String Origin( ) { return _Origin; }
        @SerializedName("PC11") private double _PC11;
        public double PC11( ) { return _PC11; }
        @SerializedName("PC12") private double _PC12;
        public double PC12( ) { return _PC12; }
        @SerializedName("PC13") private double _PC13;
        public double PC13( ) { return _PC13; }
        @SerializedName("PC14") private double _PC14;
        public double PC14( ) { return _PC14; }
        @SerializedName("PC21") private double _PC21;
        public double PC21( ) { return _PC21; }
        @SerializedName("PC22") private double _PC22;
        public double PC22( ) { return _PC22; }
        @SerializedName("PC23") private double _PC23;
        public double PC23( ) { return _PC23; }
        @SerializedName("PC24") private double _PC24;
        public double PC24( ) { return _PC24; }
        @SerializedName("PC31") private double _PC31;
        public double PC31( ) { return _PC31; }
        @SerializedName("PC32") private double _PC32;
        public double PC32( ) { return _PC32; }
        @SerializedName("PC33") private double _PC33;
        public double PC33( ) { return _PC33; }
        @SerializedName("PC34") private double _PC34;
        public double PC34( ) { return _PC34; }
        @SerializedName("PC41") private double _PC41;
        public double PC41( ) { return _PC41; }
        @SerializedName("PC42") private double _PC42;
        public double PC42( ) { return _PC42; }
        @SerializedName("PC43") private double _PC43;
        public double PC43( ) { return _PC43; }
        @SerializedName("PC44") private double _PC44;
        public double PC44( ) { return _PC44; }
        @SerializedName("PV21") private double _PV21;
        public double PV21( ) { return _PV21; }
        @SerializedName("PV22") private double _PV22;
        public double PV22( ) { return _PV22; }
        @SerializedName("RADESYS") private String _RaDeSys;
        public String RaDeSys( ) { return _RaDeSys; }
        @SerializedName("RESTFRQ") private double _RestFrq;
        public double RestFrq( ) { return _RestFrq; }
        @SerializedName("SIMPLE") private boolean _Simple;
        public boolean Simple( ) { return _Simple; }
        @SerializedName("SPECSYS") private String _SpecSys;
        public String SpecSys( ) { return _SpecSys; }
        @SerializedName("TELESCOP") private String _Telescop;
        public String Telescop( ) { return _Telescop; }
        @SerializedName("TIMESYS") private String _TimeSys;
        public String TimeSys( ) { return _TimeSys; }
        @SerializedName("VELREF") private long _VelRef;
        public long VelRef( ) { return _VelRef; }
        @SerializedName("IMAGENME") private String _ImageNme;
        public String ImageNme( ) { return _ImageNme; }
    }

    private static class NaturalDeserializer implements JsonDeserializer<Object> {
        public Object deserialize( JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) {
            if(json.isJsonNull()) return null;
            else if(json.isJsonPrimitive()) return handlePrimitive(json.getAsJsonPrimitive());
            else if(json.isJsonArray()) return handleArray(json.getAsJsonArray(), context);
            else return handleObject(json.getAsJsonObject(), context);
        }

        private Object handlePrimitive(JsonPrimitive json) {
            if(json.isBoolean())
                return json.getAsBoolean();
            else if(json.isString())
                return json.getAsString();
            else {
                BigDecimal bigDec = json.getAsBigDecimal();
                // Find out if it is an int type
                try {
                    bigDec.toBigIntegerExact();
                    try { return bigDec.intValueExact(); }
                    catch(ArithmeticException e) {}
                    return bigDec.longValue();
                } catch(ArithmeticException e) {}
                // Just return it as a double
                return bigDec.doubleValue();
            }
        }

        private Object handleArray(JsonArray json, JsonDeserializationContext context) {
            Object[] array = new Object[json.size()];
            for(int i = 0; i < array.length; i++)
                array[i] = context.deserialize(json.get(i), Object.class);
            return array;
        }

        private Object handleObject(JsonObject json, JsonDeserializationContext context) {
            Map<String, Object> map = new HashMap<String, Object>();
            for(Map.Entry<String, JsonElement> entry : json.entrySet())
                map.put(entry.getKey(), context.deserialize(entry.getValue(), Object.class));
            return map;
        }
    }

    // If this deserializer encounters a element instead of an array of strings it
    // will convert single element to an array of one string.
    class StringVecDeserializer implements JsonDeserializer<String[]> {
        @Override
        public String[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException {
            if ( json.isJsonArray( ) ) {
                JsonArray array = json.getAsJsonArray( );
                String[] result = new String[array.size()];
                for ( int i=0; i < array.size(); ++i ) {
                    result[i] = array.get(i).getAsString( );
                }
                return result;
            } else {
                String[] result = new String[1];
                result[0] = json.getAsString( );
                return result;
            }
        }
    }

    public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return -1;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                break;
            }
        }
        if (i < cs2.length() || i < cs1.length()) {
            return i;
        }
        return -1;
    }

    @Test public void gsonLoading( ) {
        String expected = "ALTRPIX=1.0\nALTRVAL=1.4749850032841744E9\nBITPIX=-32\nBSCALE=1.0\nBTYPE=\"Intensity\"\nBUNIT=\"        \"\nBZERO=0.0\nCDELT[4]=[ -0.0033333333333333335, 0.0033333333333333335, 1.0, -1.2112841821747687E8 ]\nCHNCHNKS=1\nCOMMENT[1]=[ \"casacore non-standard usage: 4 LSD, 5 GEO, 6 SOU, 7 GAL\" ]\nCRPIX[4]=[ 257.0, 257.0, 1.0, 1.0 ]\nCRVAL[4]=[ 299.86875000000003, 40.73375000000001, 1.0, -0.0 ]\nCTYPE[4]=[ \"RA---SIN\", \"DEC--SIN\", \"STOKES  \", \"VOPT    \" ]\nCUNIT1=\"deg     \"\nCUNIT2=\"deg     \"\nCUNIT3=\"        \"\nCUNIT4=\"m/s     \"\nDATE=\"2023-06-27T16:23:16.059171\"\nDATEOBS=\"null\"\nDISTANCE=0.0\nEND=\"\"\nEQUINOX=2000.0\nEXTEND=true\nHISTORY[0]=[  ]\nINSTRUME=\"VLA     \"\nLATPOLE=40.73375000000001\nLONPOLE=180.0\nMEMAVAIL=12.914434432983398\nMEMREQ=0.01373291015625\nMPIPROCS=1\nNAXIS[5]=[ 4, 512, 512, 1, 1 ]\nOBJECT=\"fake    \"\nOBSDEC=40.73375000000001\nOBSERVER=\"CASA simulator\"\nOBSGEOX=0.0\nOBSGEOY=0.0\nOBSGEOZ=0.0\nOBSRA=299.86875\nORIGIN=\"casacore-@PROJECT_VERSION@\"\nPC11=0.0\nPC12=0.0\nPC13=0.0\nPC14=0.0\nPC21=0.0\nPC22=0.0\nPC23=0.0\nPC24=0.0\nPC31=0.0\nPC32=0.0\nPC33=0.0\nPC34=0.0\nPC41=0.0\nPC42=0.0\nPC43=0.0\nPC44=0.0\nPV21=0.0\nPV22=0.0\nRADESYS=\"FK5     \"\nRESTFRQ=1.4749850032841744E9\nSIMPLE=true\nSPECSYS=\"LSRK    \"\nTELESCOP=\"VLA     \"\nTIMESYS=\"UTC     \"\nVELREF=1\nIMAGENME=\"/tmp/iclean-demo/test.residual\"\n";

        System.out.println("<gsonLoading>");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream js_stream = classloader.getResourceAsStream("coordtxl/test-residual-header.json");
        assertNotNull(js_stream,"json resource file not found");
        //=====================================================================================================================================================
        // This approach loads the JSON file into a class with each member of a custom class
        // linked to a JSON element via @SerializedName.
        //---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        gsonBuilder.registerTypeAdapter(String[].class, new StringVecDeserializer( ));
        Gson gson = gsonBuilder.create();
        GsonHeader fh = gson.fromJson(new BufferedReader( new InputStreamReader( js_stream ) ), GsonHeader.class);
        int index = indexOfDifference(fh.toString( ), expected);
        if ( index != -1 ) {
            System.out.println("----------------expected------------------------------------------------------------------------------------------------");
            System.out.println(expected);
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            System.out.println("----------------loaded--------------------------------------------------------------------------------------------------");
            System.out.println(fh.toString( ));
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
        }
        assertTrue( index == -1, "header mismatch at character " + String.valueOf(index) );
        //=====================================================================================================================================================

        //=====================================================================================================================================================
        // This approach converts all nested types and loads the JSON file into a map with the
        // name of each JSON element mapped to it's value. The NaturalDeserializer class is
        // used to handle the deseralization.
        //---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----
        InputStream js_stream2 = classloader.getResourceAsStream("coordtxl/test-residual-header.json");
        assertNotNull(js_stream2,"json resource file not found");
        GsonBuilder gsonBuilder2 = new GsonBuilder( );
        gsonBuilder2.registerTypeAdapter( Object.class, new NaturalDeserializer( ) );
        Gson gson2 = gsonBuilder2.create( );
        //com.google.gson.internal.LinkedTreeMap natural = (com.google.gson.internal.LinkedTreeMap) gson2.fromJson( new BufferedReader( new InputStreamReader(js_stream2)), Object.class );
        AbstractMap<String,Object> natural = (AbstractMap<String,Object>) gson2.fromJson( new BufferedReader( new InputStreamReader(js_stream2)), Object.class );
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.println(natural.getClass( ));
        for ( String x : natural.keySet( ) ) {
            System.out.println("\t>>>>----->> " + x + "=" + natural.get(x) + " (" + natural.get(x).getClass( ) + ")");
        }
        System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- -----");
        System.out.println(natural);
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        //=====================================================================================================================================================
    }

    @Test public void testConversion( ) {
        String expected = "{ NAXIS=[4.0, 512.0, 512.0, 1.0, 1.0], SPECSYS=\"LSRK    \", RESTFRQ=1.4749850032841744E9, BUNIT=\"        \", PC4_4=1.0, PV2_2=0.0, PV2_1=0.0, PC2_4=0.0, PC4_2=0.0, ALTRVAL=1.4749850032841744E9, PC4_3=0.0, CDELT3=1.0, PC2_2=1.0, CDELT2=0.0033333333333333335, OBSDEC=40.73375000000001, PC2_3=0.0, PC4_1=0.0, CDELT1=-0.0033333333333333335, PC2_1=0.0, CDELT4=-1.2112841821747687E8, ALTRPIX=1.0, DISTANCE=0.0, IMAGENME=\"/tmp/iclean-demo/test.residual\", INSTRUME=\"VLA     \", BZERO=0.0, EXTEND=true, CTYPE=[\"RA---SIN\", \"DEC--SIN\", \"STOKES  \", \"VOPT    \"], OBJECT=\"fake    \", DATE=\"2023-06-27T16:23:16.059171\", BTYPE=\"Intensity\", RADESYS=\"FK5     \", BITPIX=-32.0, END=\"\", TIMESYS=\"UTC     \", MPIPROCS=1.0, OBSERVER=\"CASA simulator\", LATPOLE=40.73375000000001, CRVAL1=299.86875000000003, DATE-OBS=\"2008-11-23T18:58:00.275202\", CRVAL2=40.73375000000001, CRVAL3=1.0, CRVAL4=-0.0, CTYPE3=\"STOKES  \", CTYPE4=\"VOPT    \", CTYPE1=\"RA---SIN\", CTYPE2=\"DEC--SIN\", COMMENT=\"casacore non-standard usage: 4 LSD, 5 GEO, 6 SOU, 7 GAL\", TELESCOP=\"VLA     \", ORIGIN=\"casacore-@PROJECT_VERSION@\", PC3_3=1.0, CHNCHNKS=1.0, MEMAVAIL=12.914434432983398, PC3_4=0.0, PC1_3=0.0, PC3_1=0.0, PC1_4=0.0, PC3_2=0.0, PC1_1=1.0, PC1_2=0.0, CDELT=[-0.0033333333333333335, 0.0033333333333333335, 1.0, -1.2112841821747687E8], OBSGEO-Z=3554875.8700000006, OBSGEO-Y=-5041977.546999999, OBSGEO-X=-1601185.3650000019, EQUINOX=2000.0, CRVAL=[299.86875000000003, 40.73375000000001, 1.0, -0.0], CRPIX=[257.0, 257.0, 1.0, 1.0], VELREF=1.0, CUNIT4=\"m/s     \", CUNIT3=\"        \", CUNIT2=\"deg     \", CUNIT1=\"deg     \", NAXIS5=1.0, NAXIS4=1.0, NAXIS3=512.0, NAXIS2=512.0, NAXIS1=4.0, OBSRA=299.86875, BSCALE=1.0, CRPIX4=1.0, SIMPLE=true, LONPOLE=180.0, CRPIX1=257.0, MEMREQ=0.01373291015625, CRPIX3=1.0, CRPIX2=257.0 }";
        System.out.println("<testConversion>");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream js_stream = classloader.getResourceAsStream("coordtxl/test-residual-header.json");
        assertNotNull(js_stream,"json resource file not found");
        GsonBuilder gsonBuilder = new GsonBuilder( );
        gsonBuilder.registerTypeAdapter( Object.class, new NaturalDeserializer( ) );
        Gson gson = gsonBuilder.create( );
        AbstractMap<String,Object> map = (AbstractMap<String,Object>) gson.fromJson( new BufferedReader( new InputStreamReader(js_stream)), Object.class );
        MapKeywordProvider kwp = new MapKeywordProvider(map);
        int index = indexOfDifference(kwp.toString( ), expected);
        if ( index != -1 ) {
            System.out.println("----------------expected------------------------------------------------------------------------------------------------");
            System.out.println(expected);
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            System.out.println("----------------loaded--------------------------------------------------------------------------------------------------");
            System.out.println(kwp);
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("----------------loaded--------------------------------------------------------------------------------------------------");
            System.out.println(kwp);
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
        }
        assertTrue( index == -1, "header mismatch at character " + String.valueOf(index) );
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.println(" FITS header access by WCSTransform constructor");
        System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- -----");
        WCSTransform wcs = new WCSTransform(kwp);
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        assertTrue( wcs.isWCS( ), "WCSTransform is not correctly configured" );
        Point2D.Double p = new Point2D.Double(0.0,0.0);
        wcs.imageToWorldCoords(p,false);
        System.out.println(p);
        System.out.println(new WorldCoords(p.getX( ),p.getY( )));
        assertTrue( indexOfDifference( new WorldCoords(p.getX( ),p.getY( )).toString( ), "20:02:54.100, +39:52:37.50 J2000" ) == -1,
                    "World coordinate string conversion failed." );
        String[] posstrings = new WorldCoords(p.getX( ),p.getY( )).format( );
        assertTrue( posstrings.length == 2, "Coordinate string length was not two." );
        System.out.println( "[ " + (Arrays.stream(posstrings).map(s -> "\"" + s + "\"").collect(Collectors.joining(", "))) + " ]" );
        assertTrue( indexOfDifference( posstrings[0], "20:02:54.100" ) == -1, "X coordinate was wrong" );
        assertTrue( indexOfDifference( posstrings[1], "+39:52:37.50" ) == -1, "Y coordinate was wrong" );

        System.out.println( new WorldCoords(p.getX( ),p.getY( )).getRA( ).toString( ) );
        assertTrue( indexOfDifference( new WorldCoords(p.getX( ),p.getY( )).getRA( ).toString( ), "20:02:54.100" ) == -1, "X coordinate was wrong" );
        System.out.println( new WorldCoords(p.getX( ),p.getY( )).getDec( ).toString( ) );
        assertTrue( indexOfDifference( new WorldCoords(p.getX( ),p.getY( )).getDec( ).toString( ), "+39:52:37.50" ) == -1, "Y coordinate was wrong" );
    }

}
