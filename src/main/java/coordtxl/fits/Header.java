package coordtxl.fits;

public abstract class Header {
    abstract public double AltRPix( );
    abstract public double AltRVal( );
    abstract public long BitPix( );
    abstract public double BScale( );
    abstract public String BType( );
    abstract public String BUnit( );
    abstract public double BZero( );
    abstract public double[] CDelt( );
    abstract public long ChnChnks( );
    abstract public String[] Comment( );
    abstract public double[] CRPix( );
    abstract public double[] CRVal( );
    abstract public String[] CType( );
    abstract public String CUnit1( );
    abstract public String CUnit2( );
    abstract public String CUnit3( );
    abstract public String CUnit4( );
    abstract public String Date( );
    abstract public String DateObs( );
    abstract public double Distance( );
    abstract public String End( );
    abstract public double Equinox( );
    abstract public boolean Extend( );
    abstract public String[] History( );
    abstract public String Instrume( );
    abstract public double LatPole( );
    abstract public double LonPole( );
    abstract public double MemAvail( );
    abstract public double MemReq( );
    abstract public long MPIProcs( );
    abstract public long[] NAxis( );
    abstract public String Object( );
    abstract public double ObsDec( );
    abstract public String Observer( );
    abstract public double ObsGeoX( );
    abstract public double ObsGeoY( );
    abstract public double ObsGeoZ( );
    abstract public double ObsRA( );
    abstract public String Origin( );
    abstract public double PC11( );
    abstract public double PC12( );
    abstract public double PC13( );
    abstract public double PC14( );
    abstract public double PC21( );
    abstract public double PC22( );
    abstract public double PC23( );
    abstract public double PC24( );
    abstract public double PC31( );
    abstract public double PC32( );
    abstract public double PC33( );
    abstract public double PC34( );
    abstract public double PC41( );
    abstract public double PC42( );
    abstract public double PC43( );
    abstract public double PC44( );
    abstract public double PV21( );
    abstract public double PV22( );
    abstract public String RaDeSys( );
    abstract public double RestFrq( );
    abstract public boolean Simple( );
    abstract public String SpecSys( );
    abstract public String Telescop( );
    abstract public String TimeSys( );
    abstract public long VelRef( );
    abstract public String ImageNme( );

    private static final String leadingSpace = "       ";
    private static final int lineLimit = 90;
    private static String str( String[] array ) {
        StringBuilder result = new StringBuilder( );
        StringBuilder line = new StringBuilder( );
        for ( String elem : array ) {
            String c = elem.replaceAll("\"","\\\\\"");
            if ( line.length( ) == 0 ) {
                line.append("\"").append(c).append("\"");
            } else if ( line.length( ) + c.length( ) > lineLimit ) {
                result.append(line).append(",\n").append(leadingSpace);
                line.setLength(0);
                line.append("\"").append(c).append("\"");
            } else {
                line.append(", \"").append(c).append("\"");
            }
        }
        result.append(line);
        return "[ " + result + " ]";
    }

    private static String dbl( double[] array ) {
        StringBuilder result = new StringBuilder( );
        StringBuilder line = new StringBuilder( );
        for ( double c : array ) {
            if ( line.length( ) == 0 ) {
                line.append(c);
            } else if ( line.length( ) + String.valueOf(c).length( ) > lineLimit ) {
                result.append(line).append(",\n").append(leadingSpace);
                line.setLength(0);
                line.append(c);
            } else {
                line.append(", ").append(c);
            }
        }
        result.append(line);
        return "[ " + result + " ]";
    }

    private static String lng( long[] array ) {
        StringBuilder result = new StringBuilder( );
        StringBuilder line = new StringBuilder( );
        for ( long c : array ) {
            if ( line.length( ) == 0 ) {
                line.append(c);
            } else if ( line.length( ) + String.valueOf(c).length( ) > lineLimit ) {
                result.append(line).append(",\n").append(leadingSpace);
                line.setLength(0);
                line.append(c);
            } else {
                line.append(", ").append(c);
            }
        }
        result.append(line);
        return "[ " + result + " ]";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ALTRPIX=").append(AltRPix( )).append("\n");
        builder.append("ALTRVAL=").append(AltRVal( )).append("\n");
        builder.append("BITPIX=").append(BitPix( )).append("\n");
        builder.append("BSCALE=").append(BScale( )).append("\n");
        builder.append("BTYPE=\"").append(BType( )).append("\"\n");
        builder.append("BUNIT=\"").append(BUnit( )).append("\"\n");
        builder.append("BZERO=").append(BZero( )).append("\n");
        builder.append("CDELT[").append(CDelt( ).length).append("]=").append(dbl(CDelt( ))).append("\n");
        builder.append("CHNCHNKS=").append(ChnChnks( )).append("\n");
        builder.append("COMMENT[").append(Comment( ).length).append("]=").append(str(Comment())).append("\n");
        builder.append("CRPIX[").append(CRPix( ).length).append("]=").append(dbl(CRPix( ))).append("\n");
        builder.append("CRVAL[").append(CRVal( ).length).append("]=").append(dbl(CRVal( ))).append("\n");
        builder.append("CTYPE[").append(CType( ).length).append("]=").append(str(CType( ))).append("\n");
        builder.append("CUNIT1=\"").append(CUnit1( )).append("\"\n");
        builder.append("CUNIT2=\"").append(CUnit2( )).append("\"\n");
        builder.append("CUNIT3=\"").append(CUnit3( )).append("\"\n");
        builder.append("CUNIT4=\"").append(CUnit4( )).append("\"\n");
        builder.append("DATE=\"").append(Date( )).append("\"\n");
        builder.append("DATEOBS=\"").append(DateObs( )).append("\"\n");
        builder.append("DISTANCE=").append(Distance( )).append("\n");
        builder.append("END=\"").append(End( )).append("\"\n");
        builder.append("EQUINOX=").append(Equinox( )).append("\n");
        builder.append("EXTEND=").append(Extend( )).append("\n");
        builder.append("HISTORY[").append(History( ).length).append("]=").append(str(History( ))).append("\n");
        builder.append("INSTRUME=\"").append(Instrume( )).append("\"\n");
        builder.append("LATPOLE=").append(LatPole( )).append("\n");
        builder.append("LONPOLE=").append(LonPole( )).append("\n");
        builder.append("MEMAVAIL=").append(MemAvail( )).append("\n");
        builder.append("MEMREQ=").append(MemReq( )).append("\n");
        builder.append("MPIPROCS=").append(MPIProcs( )).append("\n");
        builder.append("NAXIS[").append(NAxis( ).length).append("]=").append(lng(NAxis( ))).append("\n");
        builder.append("OBJECT=\"").append(Object( )).append("\"\n");
        builder.append("OBSDEC=").append(ObsDec( )).append("\n");
        builder.append("OBSERVER=\"").append(Observer( )).append("\"\n");
        builder.append("OBSGEOX=").append(ObsGeoX( )).append("\n");
        builder.append("OBSGEOY=").append(ObsGeoY( )).append("\n");
        builder.append("OBSGEOZ=").append(ObsGeoZ( )).append("\n");
        builder.append("OBSRA=").append(ObsRA( )).append("\n");
        builder.append("ORIGIN=\"").append(Origin( )).append("\"\n");
        builder.append("PC11=").append(PC11( )).append("\n");
        builder.append("PC12=").append(PC12( )).append("\n");
        builder.append("PC13=").append(PC13( )).append("\n");
        builder.append("PC14=").append(PC14( )).append("\n");
        builder.append("PC21=").append(PC21( )).append("\n");
        builder.append("PC22=").append(PC22( )).append("\n");
        builder.append("PC23=").append(PC23( )).append("\n");
        builder.append("PC24=").append(PC24( )).append("\n");
        builder.append("PC31=").append(PC31( )).append("\n");
        builder.append("PC32=").append(PC32( )).append("\n");
        builder.append("PC33=").append(PC33( )).append("\n");
        builder.append("PC34=").append(PC34( )).append("\n");
        builder.append("PC41=").append(PC41( )).append("\n");
        builder.append("PC42=").append(PC42( )).append("\n");
        builder.append("PC43=").append(PC43( )).append("\n");
        builder.append("PC44=").append(PC44( )).append("\n");
        builder.append("PV21=").append(PV21( )).append("\n");
        builder.append("PV22=").append(PV22( )).append("\n");
        builder.append("RADESYS=\"").append(RaDeSys( )).append("\"\n");
        builder.append("RESTFRQ=").append(RestFrq( )).append("\n");
        builder.append("SIMPLE=").append(Simple( )).append("\n");
        builder.append("SPECSYS=\"").append(SpecSys( )).append("\"\n");
        builder.append("TELESCOP=\"").append(Telescop( )).append("\"\n");
        builder.append("TIMESYS=\"").append(TimeSys( )).append("\"\n");
        builder.append("VELREF=").append(VelRef( )).append("\n");
        builder.append("IMAGENME=\"").append(ImageNme( )).append("\"\n");
        return builder.toString( );
    }
        
}
