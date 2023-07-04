// Copyright (C) 2023
// Associated Universities, Inc. Washington DC, USA.
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Library General Public License as published by
// the Free Software Foundation; either version 2 of the License, or (at your
// option) any later version.
//
// This library is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Library General Public
// License for more details.
//
// You should have received a copy of the GNU Library General Public License
// along with this library; if not, write to the Free Software Foundation,
// Inc., 675 Massachusetts Ave, Cambridge, MA 02139, USA.
//
// Correspondence concerning AIPS++ should be addressed as follows:
//        Internet email: aips2-request@nrao.edu.
//        Postal address: AIPS++ Project Office
//                        National Radio Astronomy Observatory
//                        520 Edgemont Road
//                        Charlottesville, VA 22903-2475 USA
//
package coordtxl.coords;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.AbstractMap;
import coordtxl.coords.WCSKeywordProvider;

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

    public boolean findKey(String key) { return wcsMap.containsKey(key); }

    public String getStringValue(String key) { return getStringValue(key,null); }
    public String getStringValue(String key, String defaultValue)
        { return wcsMap.containsKey(key) ? wcsMap.get(key).toString( ) : defaultValue; }

    public double getDoubleValue(String key) { return getDoubleValue( key, 0.0 ); }
    public double getDoubleValue(String key, double defaultValue) {
        if ( ! wcsMap.containsKey(key) )
            return defaultValue;
        Object result = wcsMap.get(key);
        if ( result instanceof Number )
            return ((Number) result).doubleValue( );
        else
            return defaultValue;
    }

    public float getFloatValue(String key) { return getFloatValue(key,(float)0.0); }
    public float getFloatValue(String key, float defaultValue) {
        if ( ! wcsMap.containsKey(key) )
            return defaultValue;
        Object result = wcsMap.get(key);
        if ( result instanceof Number )
            return ((Number) result).floatValue( );
        else
            return defaultValue;
    }

    public int getIntValue(String key) { return getIntValue(key,0); }
    public int getIntValue(String key, int defaultValue) {
        if ( ! wcsMap.containsKey(key) )
            return defaultValue;
        Object result = wcsMap.get(key);
        if ( result instanceof Number )
            return ((Number) result).intValue( );
        else
            return defaultValue;
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
/*              if ( o instanceof List &&
                     ((List)o).size( ) > 0 &&
                     ((List)o).get(0) instanceof String ) { */
                if ( o instanceof List ) {
                    List lst = (List) o;
                    String str = "[";
                    for ( int i=0; i < lst.size( ); ++i ) {
                        if ( i != 0 ) str += ", ";
                        Object ele = lst.get(i);
                        if ( ele instanceof String )
                            str += "\"" + ele + "\"";
                        else
                            str += ele;
                    }
                    str += "]";
                    sb.append(str);
                } else sb.append(o);
            }
        }
        sb.append(" }");
        return sb.toString( );
    }

}

