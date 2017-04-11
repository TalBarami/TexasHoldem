package TexasHoldem.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by Tal on 08/04/2017.
 */
public class JsonSerializer{
    private static ObjectMapper om;

    public JsonSerializer(){
        om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public String writeValueAsString(Object value){
        try {
            return om.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeValue(File file, Object value){
        try {
            om.writeValue(file, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T readValue(File file, Class<T> clazz){
        try {
            return om.readValue(file, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T readValue(String json, Class<T> clazz){
        try {
            return om.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
