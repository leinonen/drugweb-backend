package se.leinonen.drugweb.routes;

import com.google.gson.JsonObject;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.leinonen.drugweb.model.Drug;
import se.leinonen.drugweb.repository.DrugRepository;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leinonen on 2014-07-02.
 */
public class GetDrugsRouteTest {

    private GetDrugsRoute sut;
    @Mock
    private Response mockResponse;
    @Mock
    private Request mockRequest;

    @Mock
    private DrugRepository mockRepo;
    // Mocka DrugRepositoryImpl!

    @BeforeMethod
    public void beforeMethod(){
        MockitoAnnotations.initMocks(this);
        sut = new GetDrugsRoute("/route", mockRepo);

        List<Drug> all = new ArrayList<Drug>();
        all.add(new Drug());
        all.add(new Drug());
        Mockito.when(mockRepo.getAll()).thenReturn(all);
    }

    @Test
    public void handle(){
        List<Drug> result = (List<Drug>) sut.handle(mockRequest, mockResponse);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2);
    }

    @Test
    public void getPath(){
        Assert.assertEquals(sut.getPath(), "/route");
    }
}
