package app.browser.datomic;

import com.google.common.collect.Sets;
import datomic.Connection;
import datomic.Peer;

import java.util.Set;

public final class Databases {

    private static final String prefix = "datomic:mem://";
    private static final Set<String> dbnames = Sets.newLinkedHashSet();
    
    public static Set<String> dbnames() {
        return dbnames;
    }
    
    public static boolean create(String name) {
        dbnames.add(name);
        return Peer.createDatabase(prefix + name);
    }
    
    public static boolean delete(String name) {
        dbnames.remove(name);
        return Peer.deleteDatabase(prefix + name);
    }
    
    public static Connection connect(String name) {
        return Peer.connect(prefix + name);
    }
    

}
