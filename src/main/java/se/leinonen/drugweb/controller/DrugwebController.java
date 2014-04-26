package se.leinonen.drugweb.controller;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Query;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import se.leinonen.drugweb.JsonTransformerRoute;
import se.leinonen.drugweb.model.Drug;
import spark.Request;
import spark.Response;
import spark.servlet.SparkApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;

/**
 * Created by leinonen on 2014-04-26.
 */
public class DrugwebController implements SparkApplication {

    @Override
    public void init() {

        // Provide some REST endpoints to access drug information.

        get(new JsonTransformerRoute("/rest/drugs") {
            @Override
            public Object handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", "*");
                return getDrugs(Ebean.find(Drug.class));
            }
        });

        get(new JsonTransformerRoute("/rest/drug/:name") {
            @Override
            public Object handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", "*");
                String name = request.params(":name");
                Query<Drug> nameQuery = Ebean.find(Drug.class).where(Expr.like("name", "%" + name + "%"));
                return getDrugs(nameQuery);
            }
        });

        get(new JsonTransformerRoute("/rest/mock") {
            @Override
            public Object handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", "*");
                System.out.println("mock");
                try {
                    JsonParser parser = new JsonParser();
                    InputStream stream = this.getClass().getClassLoader().getResourceAsStream("mock.json");
                    String jsonString = IOUtils.toString(stream, "UTF-8");
                    return parser.parse(jsonString);
                } catch (IOException e) {
                    return "Error";
                }
            }
        });
    }

    private static List<JsonObject> getDrugs(Query<Drug> query) {
        List<Drug> list = query.findList();
        List<JsonObject> data = new ArrayList<JsonObject>();
        for (Drug drug : list) {
            JsonObject drugJson = new JsonObject();
            drugJson.addProperty("name", drug.getName());
            drugJson.addProperty("description", drug.getDescription());
            drugJson.addProperty("imageUrl", drug.getImageUrl());
            drugJson.addProperty("chemicalName", drug.getChemicalName());
            drugJson.addProperty("commonName", drug.getCommonName());
            data.add(drugJson);
        }

        return data;
    }
}
