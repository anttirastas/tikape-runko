package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.AnnosRaakaAineDao;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:reseptit.db");
        
        database.init();

        RaakaAineDao raakaAineet = new RaakaAineDao(database);
        AnnosDao annokset = new AnnosDao(database);
        AnnosRaakaAineDao annosRaakaAineet = new AnnosRaakaAineDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annokset.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/annokset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annokset.findAll());
            map.put("raakaAineet", raakaAineet.findAll());

            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());
        
        post("/annokset", (req, res) -> {
            Annos annos = new Annos(-1, req.queryParams("name"));
            annokset.saveOrUpdate(annos);

            res.redirect("/annokset");
            return "";
        });

        get("/annokset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annos", annokset.findOne(Integer.parseInt(req.params("id"))));
            // etsi kaikki kaikki annokseen kuuluvat raaka-aineet ja palauta ne oikeassa järjestyksessä
            List<AnnosRaakaAine> annoksenAnnosRaakaAineet = annosRaakaAineet.findByAnnosId(Integer.parseInt(req.params("id")));
            map.put("annosRaakaAineet", annoksenAnnosRaakaAineet);
            List<RaakaAine> annoksenRaakaAineet = raakaAineet.getRaakaAineet(annoksenAnnosRaakaAineet);
            map.put("raakaAineet", annoksenRaakaAineet);

            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
        
        post("/annokset/:id", (req, res) -> {
           Integer annosId = Integer.parseInt(req.params(":id"));
           String annosNimi = annokset.findOne(annosId).getNimi();
           Integer raakaAineId = Integer.parseInt(req.queryParams("raakaAineId"));
           String raakaAineNimi = raakaAineet.findOne(raakaAineId).getNimi();
           Integer jarjestys = Integer.parseInt(req.queryParams("raakaAineId"));
           String maara = (req.queryParams("raakaAineId"));
           String ohje = (req.queryParams("raakaAineId"));
           
           AnnosRaakaAine ar = new AnnosRaakaAine(-1, annosId, annosNimi, raakaAineId, raakaAineNimi, jarjestys, maara, ohje);
           
           annosRaakaAineet.saveOrUpdate(ar);
           
           res.redirect("/annokset");
           return "";
        });
        
        
        /*
        // polkuun määriteltävä parametri merkitään kaksoispisteellä ja 
        // parametrin nimellä. Parametrin arvoon pääsee käsiksi kutsulla
        // req.params
        Spark.post("/tasks/:id", (req, res) -> {
            Integer taskId = Integer.parseInt(req.params(":id"));
            Integer userId = Integer.parseInt(req.queryParams("userId"));

            TaskAssignment ta = new TaskAssignment(-1, taskId, userId, Boolean.FALSE);
            taskAssignments.saveOrUpdate(ta);

            res.redirect("/tasks");
            return "";
        });
        */
        
        
        
        get("/raaka-aineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineet", raakaAineet.findAll());

            return new ModelAndView(map, "raaka-aineet");
        }, new ThymeleafTemplateEngine());
        
        post("/raaka-aineet", (req, res) -> {
            RaakaAine raakaAine = new RaakaAine(-1, req.queryParams("name"));
            raakaAineet.saveOrUpdate(raakaAine);
            
            res.redirect("/raaka-aineet");

            return "";
        });
        
        
    }
}
