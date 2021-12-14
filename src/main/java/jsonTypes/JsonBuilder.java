package jsonTypes;

import java.util.HashMap;

import com.google.gson.JsonObject;

public class JsonBuilder {
    public final JsonObject json = new JsonObject();

    public String toJson() {
        return json.toString();
    }

    public JsonBuilder add(String key, String value) {
        json.addProperty(key, value);
        return this;
    }

    public JsonBuilder add(String key, JsonBuilder value) {
        json.add(key, value.json);
        return this;
    }


}

class GsonTest {

    public static void main(String[] args) {

        System.out.println(new JsonBuilder()
                .add("key1", "value1")
                .add("key2", "value2")
                .add("key3", new JsonBuilder()
                        .add("innerKey", "value3"))
                .toJson());
    }

}
