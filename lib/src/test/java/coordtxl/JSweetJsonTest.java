package coordtxl;
//------------------------------------------------------------------------------------------------------------------------
//-- This class is the Java version of the demo example from j4js-json: https://github.com/j4ts/j4ts-json               --
//--                                                                                                                    --
//-- The idea with this is to have an example of Gson parsing that is believed to work in both Java and JavaScript so   --
//-- there is an example that is believed to work for testing in JAVA. It's not clear why the native Json parsing       --
//-- cannot be used to parse the JSON in JavaScript in a browser.                                                       --
//--                                                                                                                    --
//-- In the casagui use case, the CASA image will be used to create a JSON string that represents the FITS header for   --
//-- image. This will either be passed during construction of the GUI or as a message to the GUI. The JSON will be      --
//-- unpacked and used to initialize coordtxl for conversion of pixel coordinates to world coordinates... at least      --
//-- that's the hope.                                                                                                   --
//--                                                                                                                    --
//-- j4js-json should not be required by the coordtxl JavaScript library.                                               --
//------------------------------------------------------------------------------------------------------------------------
import org.testng.annotations.*;
import static org.testng.Assert.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

public class JSweetJsonTest {
    //private final int num;
    //private final String msg;

    public static class Test2 {
        int num;
        String msg;
        public Test2(int n, String m ) {
            this.num = n;
            this.msg = m;
        }
    };

    public static class TestDeserializer implements JsonDeserializer<Test2> {
        @Override
        public Test2 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonArray()) {
                JsonArray asJsonArray = json.getAsJsonArray();
                if (asJsonArray.size() >= 2)
                    return new Test2(context.deserialize(asJsonArray.get(0), Integer.class), context.deserialize(asJsonArray.get(1), String.class));
            }
            throw new JsonParseException("not a valid Test");
        }
    }

    public static class TestSerializer implements JsonSerializer<Test2> {
        @Override
        public JsonElement serialize(Test2 src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(src.msg, src.num);
            return jsonObject;
        }

        // currently need this hack :'(
        public JsonElement serialize(Test2 src, Object typeOfSrc, JsonSerializationContext context) {
            return serialize(src, (Type) typeOfSrc, context);
        }
    }
    @Test public static void doesItWork( ) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Test2.class, new TestDeserializer())
                .registerTypeAdapter(Test2.class, new TestSerializer())
                .create();

        Test2 test = gson.fromJson("[5, \"test\"]", Test2.class);

        String s = gson.toJson(test);
        System.err.println(s);

        assertEquals( "{\"test\":5}", s, "json encoding failed" );

        Map<?, ?> listSerializable = gson.fromJson("{\"a\": 1.1, \"b\": \"x\", \"c\": []}", Map.class);
        List<?> values = new LinkedList<>(listSerializable.values());
        String s1 = gson.toJson(values);
        System.err.println(s1);

        assertEquals( "[1.1,\"x\",[]]", s1, "json decoding failed" );
    }
}
