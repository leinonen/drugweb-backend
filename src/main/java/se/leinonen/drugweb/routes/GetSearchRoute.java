package se.leinonen.drugweb.routes;

import org.apache.log4j.Logger;
import se.leinonen.drugweb.JsonTransformerRoute;
import se.leinonen.drugweb.model.Drug;
import se.leinonen.drugweb.repository.DrugRepository;
import spark.Request;
import spark.Response;

import java.util.List;

/**
 * Created by leinonen on 2014-07-02.
 */
public class GetSearchRoute extends JsonTransformerRoute {

    private static Logger logger = Logger.getLogger(GetSearchRoute.class);

    public GetSearchRoute(String path) {
        super(path);
    }

    @Override
    public Object handle(Request request, Response response) {
        String q = request.queryParams("q");
        logger.info(request.requestMethod() + " " + getPath() + "?q=" + q);
        setCORS(response);
        List<Drug> result = DrugRepository.getInstance().getListByName(q);
        return Drug.drugsToJson(result);
    }
}
