package se.leinonen.drugweb.routes;

import org.apache.log4j.Logger;
import se.leinonen.drugweb.JsonTransformerRoute;
import se.leinonen.drugweb.model.Drug;
import se.leinonen.drugweb.repository.DrugRepository;
import se.leinonen.drugweb.repository.DrugRepositoryImpl;
import spark.Request;
import spark.Response;

/**
 * Created by leinonen on 2014-07-02.
 */
public class GetDrugsRoute extends JsonTransformerRoute {
    private Logger logger = Logger.getLogger(GetDrugsRoute.class);
    private DrugRepository drugRepository;
    public GetDrugsRoute(String path) {
        super(path);
        this.drugRepository = DrugRepositoryImpl.getInstance();
    }
    GetDrugsRoute(String path, DrugRepository repo){
        super(path);
        this.drugRepository = repo;
    }

    @Override
    public Object handle(Request request, Response response) {
        logger.info(request.requestMethod() + " " + getPath());
        setCORS(response);
        return Drug.drugsToJson(drugRepository.getAll());
    }
}
