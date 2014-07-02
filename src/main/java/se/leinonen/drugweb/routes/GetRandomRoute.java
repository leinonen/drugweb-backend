package se.leinonen.drugweb.routes;

import org.apache.log4j.Logger;
import se.leinonen.drugweb.JsonTransformerRoute;
import se.leinonen.drugweb.repository.DrugRepository;
import spark.Request;
import spark.Response;

/**
 * Created by leinonen on 2014-07-02.
 */
public class GetRandomRoute extends JsonTransformerRoute {

    private static Logger logger = Logger.getLogger(GetRandomRoute.class);

    public GetRandomRoute(String path) {
        super(path);
    }

    @Override
    public Object handle(Request request, Response response) {
        logger.info(request.requestMethod() + " " + getPath());
        setCORS(response);
        return DrugRepository.getInstance().findRandom().toJson();
    }
}
