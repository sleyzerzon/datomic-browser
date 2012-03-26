package app.browser.datomic;

import com.google.common.collect.Lists;
import datomic.Database;
import datomic.Entity;
import datomic.Peer;

import java.util.Collection;
import java.util.List;

public final class Entities {
    
    public static List<Entity> referers(Entity entity, Database db) {
        final Collection<List<Object>> results =
            Peer.q("[:find ?r :in $ ?e :where [?r _ ?e]]", db, entity.get(":db/id"));
        final List<Entity> referers = Lists.newArrayList();
        for (List<Object> result : results) {
            referers.add(db.entity(result.get(0)));
        }
        return referers;
    }
    
}
