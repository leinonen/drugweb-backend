package se.leinonen.drugweb.controller;

import com.avaje.ebean.Ebean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import se.leinonen.drugweb.JsonTransformerRoute;
import se.leinonen.drugweb.model.Drug;
import se.leinonen.drugweb.repository.DrugRepository;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by leinonen on 2014-04-26.
 */
public class DrugwebController implements SparkApplication {

    public static final String API_DRUGS = "/api/drugs/";
    public static final String API_DRUGS_ID = "/api/drugs/:id";
    public static final String API_DRUGS_SEARCH = "/api/drugs/search";
    public static final String API_DRUGS_RANDOM = "/api/drugs/random";

    public static final String API_MOCK = "/api/mock";

    public static final String ALLOW_ORIGIN = "http://localhost:9001";
    public static final String OPTIONS_ALLOW = "Allow: HEAD,GET,PUT,POST,DELETE,OPTIONS";

    @Override
    public void init() {

        final DrugRepository repo = new DrugRepository();

        // Provide some REST endpoints to access drug information.

        /*
            OPTIONS               Måste man ha den?

            GET    /drugs         Retrieve a list of drugs
            GET    /drugs/12      Get specific Drug
            GET    /drugs?q=dmt   Search drugs containing the word dmt

            POST   /drugs         Create new drug
            PUT    /drugs/12      Update drug #12
            PATCH  /drugs/12      Partially update drug #12
            DELETE /drugs/12      Deletes drug #12

            Sub-resources:
                /drugs/12/effects

        * */


        options(new Route(API_DRUGS) {
            @Override
            public Object handle(Request request, Response response) {
                setCORS(response);
                return OPTIONS_ALLOW;
            }
        });

        options(new Route(API_DRUGS_SEARCH) {
            @Override
            public Object handle(Request request, Response response) {
                setCORS(response);
                return OPTIONS_ALLOW;
            }
        });

        options(new Route(API_DRUGS_RANDOM) {
            @Override
            public Object handle(Request request, Response response) {
                setCORS(response);
                return OPTIONS_ALLOW;
            }
        });


        get(new JsonTransformerRoute(API_DRUGS) {
            @Override
            public Object handle(Request request, Response response) {
                log("GET " + API_DRUGS);
                setCORS(response);
                return drugsToJson(repo.getAll());
            }
        });


        get(new JsonTransformerRoute(API_DRUGS_SEARCH) {
            @Override
            public Object handle(Request request, Response response) {
                String q = request.queryParams("q");
                log("GET " + API_DRUGS_SEARCH + "?q=" + q);
                setCORS(response);
                return drugsToJson(repo.getListByName(q));
            }
        });


        get(new JsonTransformerRoute(API_DRUGS_RANDOM) {
            @Override
            public Object handle(Request request, Response response) {
                log("GET " + API_DRUGS_RANDOM);
                setCORS(response);
                return drugToJson(repo.findRandom());
            }
        });


        post(new JsonTransformerRoute(API_DRUGS) {
            @Override
            public Object handle(Request request, Response response) {
                log("POST " + API_DRUGS);
                Drug drug = getDrugFromJson(request);
                if (drug != null){
                    repo.save(drug);
                    log("SAVED! " + drug.getId());
                } else {
                    log("Error creating drug from Json");
                }
                setCORS(response);
                return drugToJson(drug);
            }
        });


        get(new JsonTransformerRoute(API_DRUGS_ID) {
            @Override
            public Object handle(Request request, Response response) {
                setCORS(response);
                Long id = Long.parseLong(request.params(":id"));
                log("GET " + API_DRUGS_ID + " -> " + id);
                Drug drug = repo.findById(id);
                if (drug != null) {
                    return drugToJson(drug);
                } else {
                    log("Drug not found!");
                    return null;
                }
            }
        });


        get(new JsonTransformerRoute(API_MOCK) {
            @Override
            public Object handle(Request request, Response response) {
                log("mock");
                setCORS(response);
                return getMockedJson("mock.json");
            }
        });
    }


    /*
        Helper methods
     */

    private JsonElement getMockedJson(String mockJsonFile){
        try {
            JsonParser parser = new JsonParser();
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("mock.json");
            String jsonString = IOUtils.toString(stream, "UTF-8");
            return parser.parse(jsonString);
        } catch (IOException e) {
            return null;
        }
    }

    private Drug getDrugFromJson(Request request) {
        String json = request.body();
        Gson gson = new Gson();
        return gson.fromJson(json, Drug.class);
    }

    private static void log(String msg) {
        System.out.println(msg);
    }


    private static void setCORS(Response response) {
        //log("Setting Cross Origin Resource Sharing Headers.");
        response.header("Access-Control-Allow-Origin", ALLOW_ORIGIN);
        response.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }

    private static JsonObject drugToJson(Drug drug) {
        JsonObject drugJson = new JsonObject();
        Gson gson = new Gson();
        String json = gson.toJson(drug);
        drugJson.addProperty("id", drug.getId());
        drugJson.addProperty("name", drug.getName());
        drugJson.addProperty("simpleName", drug.getSimpleName());
        drugJson.addProperty("description", drug.getDescription());
        drugJson.addProperty("imageUrl", drug.getImageUrl());
        drugJson.addProperty("chemicalName", drug.getChemicalName());
        drugJson.addProperty("commonName", drug.getCommonName());
        drugJson.addProperty("effects", drug.getEffects());
        return drugJson;
    }

    private static List<JsonObject> drugsToJson(List<Drug> list) {
        List<JsonObject> data = new ArrayList<JsonObject>();
        for (Drug drug : list) {
            data.add(drugToJson(drug));
        }
        log("Found " + list.size() + " drug(s).");
        return data;
    }
}
