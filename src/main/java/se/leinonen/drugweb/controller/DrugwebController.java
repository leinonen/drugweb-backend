package se.leinonen.drugweb.controller;

import org.apache.log4j.Logger;
import se.leinonen.drugweb.routes.*;
import se.leinonen.drugweb.util.EBeanConfig;
import se.leinonen.drugweb.util.Settings;
import spark.servlet.SparkApplication;

import static spark.Spark.*;

/**
 * Created by leinonen on 2014-04-26.
 */
public class DrugwebController implements SparkApplication {

    private static final Logger logger = Logger.getLogger(DrugwebController.class);

    public static final String API_DRUGS = "route.drugs";
    public static final String API_DRUGS_ID = "route.drugs.id";
    public static final String API_DRUGS_SEARCH = "route.drugs.search";
    public static final String API_DRUGS_RANDOM = "route.drugs.random";


    @Override
    public void init() {
        logger.info("Starting up!");
        EBeanConfig.getInstance().setup();

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

        // OPTIONS - Seems to be required for CORS
        options(new OptionsAllowRoute("/api"));

        options(new OptionsAllowRoute(getConfig(API_DRUGS)));
        get(new GetDrugsRoute(getConfig(API_DRUGS)));
        post(new PostDrugsRoute(getConfig(API_DRUGS)));

        options(new OptionsAllowRoute(getConfig(API_DRUGS_SEARCH)));
        get(new GetSearchRoute(getConfig(API_DRUGS_SEARCH)));

        options(new OptionsAllowRoute(getConfig(API_DRUGS_RANDOM)));
        get(new GetRandomRoute(getConfig(API_DRUGS_RANDOM)));

        get(new GetDrugByIdRoute(getConfig(API_DRUGS_ID)));
    }

    public String getConfig(String key){
        return Settings.getInstance().getString(key);
    }
}
