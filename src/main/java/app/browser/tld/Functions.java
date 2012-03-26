package app.browser.tld;

import datomic.Entity;
import datomic.query.EntityMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Set;

public class Functions {

    public static String urlEncode(String path) throws URISyntaxException {
        return new URI(null, null, path, null).toASCIIString();
    }
    
    public static Object eget(Entity e, String key) {
        return e.get(key);
    }
    
    public static Set<String> keySet(Entity e) {
        return e.keySet();
    }

    public static boolean isCollection(Object o) {
        return o instanceof Collection;
    }
    
    public static boolean isEntityMap(Object o) {
        return o instanceof EntityMap;
    }
    
    public static boolean hasDbIdent(Entity e) {
        return e.get(":db/ident") != null;
    }

    public static long size(Collection c) {
        return c.size();
    }

}
