package se.leinonen.drugweb;

import com.google.gson.Gson;
import se.leinonen.drugweb.util.CorsUtil;
import se.leinonen.drugweb.util.Settings;
import spark.Response;
import spark.ResponseTransformerRoute;

/**
 * Created by leinonen on 2014-04-26.
 */
public abstract class JsonTransformerRoute extends ResponseTransformerRoute {

    private Gson gson = new Gson();

    private String path;

    protected JsonTransformerRoute(String path) {

        super(path, "application/json");

        this.path = path;
    }

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    protected void setCORS(Response response) {
        response.header("Access-Control-Allow-Origin", CorsUtil.getAllowOrigin());
        response.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}