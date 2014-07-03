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
public class PostDrugsRoute extends JsonTransformerRoute {
    private Logger logger = Logger.getLogger(PostDrugsRoute.class);

    private DrugRepository drugRepository;

    public PostDrugsRoute(String path) {
        super(path);
        this.drugRepository = DrugRepositoryImpl.getInstance();
    }

    PostDrugsRoute(String path, DrugRepository repo){
        super(path);
        this.drugRepository = repo;
    }

    @Override
    public Object handle(Request request, Response response) {
        logger.info(request.requestMethod() + " " + getPath());
        Drug drug = Drug.parseFromJson(request.body());
        if (drug != null){
            drugRepository.save(drug);
            logger.info("Save drug with id " + drug.getId());
        } else {
            logger.info("Error creating drug from Json");
        }
        setCORS(response);
        return drug.toJson();
    }
}
