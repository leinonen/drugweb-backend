package se.leinonen.drugweb.routes;

import org.apache.log4j.Logger;
import se.leinonen.drugweb.util.CorsUtil;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by leinonen on 2014-07-02.
 */
public class OptionsAllowRoute extends Route {

    public static final String OPTIONS_ALLOW = "Allow: HEAD,GET,PUT,POST,DELETE,OPTIONS";

    private static Logger logger = Logger.getLogger(OptionsAllowRoute.class);

    private String path;

    public OptionsAllowRoute(String path) {
        super(path);
        this.path = path;
    }

    @Override
    public Object handle(Request request, Response response) {
        logger.info(request.requestMethod() + " " + getPath());
        setCORS(response);
        return OPTIONS_ALLOW;
    }

    protected void setCORS(Response response) {
        //log("Setting Cross Origin Resource Sharing Headers.");
        response.header("Access-Control-Allow-Origin", CorsUtil.getAllowOrigin());
        response.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }

    public String getPath() {
        return path;
    }
}
