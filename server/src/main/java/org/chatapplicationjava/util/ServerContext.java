package org.chatapplicationjava.util;



import java.util.HashMap;
import java.util.Map;

public class ServerContext {
    public static Map<String, Object> context = new HashMap<>();

    public static void bind(String name, Object object) {
        context.put(name, object);
    }

    public static Object lookup(String name) {
        return context.get(name);
    }


}
