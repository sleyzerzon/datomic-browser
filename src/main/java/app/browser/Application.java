package app.browser;

import app.browser.datomic.Attributes;
import app.browser.datomic.Databases;
import app.browser.datomic.Entities;
import app.browser.datomic.Rules;
import com.google.common.collect.Lists;
import datomic.Connection;
import datomic.Database;
import datomic.Entity;
import datomic.Peer;
import datomic.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class Application {

    @RequestMapping(value = "/", method = GET)
    public ModelAndView databases() {
        return new ModelAndView("databases")
            .addObject("dbnames", Databases.dbnames());
    }
    
    @RequestMapping(value = "/database/{dbname}", method = {GET, POST})
    public ModelAndView database(@PathVariable String dbname) {
        final Connection connection = connect(dbname);
        return new ModelAndView("database")
            .addObject("dbname", dbname)
            .addObject("uri", toUri(dbname))
            .addObject("attributes", Attributes.in(connection.db()));
    }

    @RequestMapping(value = "/database/{dbname}/entity/{id}", method = {GET, POST})
    public ModelAndView entity(@PathVariable String dbname, @PathVariable String id) {
        final Connection connection = connect(dbname);
        final Entity entity = connection.db().entity(Long.valueOf(id));
        final List<Entity> instances =
            Attributes.is(entity) ? Attributes.instances(entity, connection.db()) : Collections.<Entity>emptyList();
        final List<Entity> referers = Entities.referers(entity, connection.db());
        return new ModelAndView("entity")
            .addObject("dbname", dbname)
            .addObject("uri", toUri(dbname))
            .addObject("entity", entity)
            .addObject("instances", instances)
            .addObject("referers", referers);
    }
    
    @RequestMapping(value = "/database/{dbname}/search", method = GET)
    public ModelAndView search(@PathVariable String dbname, @RequestParam String terms) {
        final Connection connection = connect(dbname);
        final Database db = connection.db();
        final Collection<List<Object>> results =
            Peer.q("[:find ?o ?c :in $ % ?t :where (ft ?o ?c ?t)]",
                connection.db(),
                Rules.universalFullTextRule(connection.db()),
                terms);
        final List<Entity> entities = Lists.newArrayListWithCapacity(results.size());
        for (List<Object> result : results) {
            entities.add(db.entity(result.get(0)));
        }
        return new ModelAndView("search")
            .addObject("dbname", dbname)
            .addObject("uri", toUri(dbname))
            .addObject("terms", terms)
            .addObject("results", entities);
    }
    
    @RequestMapping(value = "/database/{dbname}/upload", method = POST)
    public ModelAndView upload(@PathVariable String dbname, @RequestParam MultipartFile file) throws IOException {
        final Connection connection = connect(dbname);
        final List transaction = (List) Util.readAll(new BufferedReader(new InputStreamReader(file.getInputStream()))).get(0);
        connection.transact(transaction);
        return new ModelAndView("database")
            .addObject("dbname", dbname)
            .addObject("uri", toUri(dbname))
            .addObject("attributes", Attributes.in(connection.db()));
    }
    
    private static Connection connect(String dbname) {
        Databases.create(dbname); // idempotent
        return Databases.connect(dbname);
    }
    
    private static String toUri(String name) {
        return "datomic:mem://" + name;
    }

}
