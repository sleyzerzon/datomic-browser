package app.browser.datomic;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import datomic.Database;
import datomic.Entity;
import datomic.Peer;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Attributes {
    
    public static List<Entity> fulltextIn(Database db) {
        final Collection<List<Object>> attributes =
            Peer.q("[:find ?a :where [:db.part/db :db.install/attribute ?a]" +
                "[?a :db/fulltext true]]", db);
        return toSortedEntities(attributes, db);
    }
    
    public static boolean is(Entity entity) {
        return entity.get(":db.install/_attribute") != null;
    }
    
    public static List<Entity> instances(Entity attribute, Database db) {
        final Collection<List<Object>> results =
            Peer.q("[:find ?i :in $ ?a :where [?i ?a _]]", db, attribute.get(":db/id"));
        final List<Entity> instances = Lists.newArrayList();
        for (List<Object> result : results) {
            instances.add(db.entity(result.get(0)));
        }
        return instances;
    }
    
    public static List<Entity> in(final Database db) {
        final Collection<List<Object>> attributes =
            Peer.q("[:find ?a :where [:db.part/db :db.install/attribute ?a]]", db);
        return toSortedEntities(attributes, db);
    }

    private static List<Entity> toSortedEntities(Collection<List<Object>> attributes, final Database db) {
        final List<Entity> entities = Lists.newArrayList(
            Collections2.transform(attributes, new Function<List<Object>, Entity>() {
                @Override
                public Entity apply(List<Object> result) {
                return db.entity(result.get(0));
            }
        })
        );
        Collections.sort(entities, new Comparator<Entity>() {
            @Override
            public int compare(Entity a, Entity b) {
            return ((Long) a.get(":db/id")).compareTo((Long) b.get(":db/id"));
            }
        });
        return entities;
    }

}
