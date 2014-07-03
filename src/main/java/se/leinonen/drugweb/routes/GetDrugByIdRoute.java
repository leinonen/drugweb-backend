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
public class GetDrugByIdRoute extends JsonTransformerRoute {
    private Logger logger = Logger.getLogger(GetDrugByIdRoute.class);

    private DrugRepository drugRepository;

    public GetDrugByIdRoute(String path) {
        super(path);
        this.drugRepository = DrugRepositoryImpl.getInstance();
    }

    GetDrugByIdRoute(String path, DrugRepository repo){
        super(path);
        this.drugRepository = repo;
    }

    @Override
    public Object handle(Request request, Response response) {
        setCORS(response);
        Long id = Long.parseLong(request.params(":id"));
        logger.info(request.requestMethod() + " " + getPath() + " -> " + id);
        Drug drug = drugRepository.findById(id);
        if (drug != null) {
            return drug.toJson();
        } else {
            logger.info("Drug not found!");
            return null;
        }
    }
}
