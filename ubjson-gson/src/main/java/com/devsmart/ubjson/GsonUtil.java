package com.devsmart.ubjson;

import com.devsmart.ubjson.UBObject;
import com.devsmart.ubjson.UBValue;
import com.devsmart.ubjson.UBValueFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

public class GsonUtil {


    public static UBValue toUBValue(JsonElement element) {
        if(element.isJsonNull()) {
            return UBValueFactory.createNull();
        } else if(element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if(primitive.isBoolean()){
                return UBValueFactory.createBool(primitive.getAsBoolean());
            } else if(primitive.isString()) {
                return UBValueFactory.createString(primitive.getAsString());
            } else if(primitive.isNumber()) {
                return UBValueFactory.createNumber(primitive.getAsNumber());
            }
        } else if(element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            final int size = array.size();
            UBValue[] ubArray = new UBValue[size];
            for(int i=0;i<size;i++) {
                ubArray[i] = toUBValue(array.get(i));
            }
            return UBValueFactory.createArray(ubArray);
        } else if(element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();
            UBObject retval = UBValueFactory.createObject();
            for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                retval.put(entry.getKey(), toUBValue(entry.getValue()));
            }
            return retval;
        } else {
            throw new RuntimeException("unknown json element: " + element);
        }

        return null;
    }
}
