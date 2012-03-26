package app.browser.datomic;

import datomic.Database;
import datomic.Entity;

import java.util.List;

public final class Rules {
    
    public static String universalFullTextRule(Database db) {
        final List<Entity> attributes = Attributes.fulltextIn(db);
        final StringBuilder rules = new StringBuilder();
        rules.append("[");
        String separator = "";
        for (Entity attribute : attributes) {
            rules.append(separator).append("[[ft ?o ?c ?t] [(fulltext $ ")
                .append(dbIdent(attribute)).append(" ?t) [[?o ?c]]]]");
            separator = " ";
        }
        rules.append("]");
        return rules.toString();
    }

    private static String dbIdent(Entity e) {
        return e.get(":db/ident").toString();
    }

}
