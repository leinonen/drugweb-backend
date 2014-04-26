package se.leinonen.drugweb;

import com.google.gson.Gson;
import spark.ResponseTransformerRoute;

/**
 * Created by leinonen on 2014-04-26.
 */
public abstract class JsonTransformerRoute extends ResponseTransformerRoute {

    private Gson gson = new Gson();

    protected JsonTransformerRoute(String path) {
        super(path, "application/json");
    }

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

}