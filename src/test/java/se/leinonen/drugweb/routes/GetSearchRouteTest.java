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
 * Created by leinonen on 2014-07-03.
 */
public class GetSearchRouteTest {
    private GetSearchRoute sut;
    @Mock
    private Response mockResponse;
    @Mock
    private Request mockRequest;

    @Mock
    private DrugRepository mockRepo;

    @BeforeMethod
    public void beforeMethod(){
        MockitoAnnotations.initMocks(this);
        sut = new GetSearchRoute("/route", mockRepo);

        Mockito.when(mockRequest.queryParams("q")).thenReturn("lsd");

        List<Drug> list = new ArrayList<Drug>();
        list.add(new Drug());
        list.add(new Drug());
        Mockito.when(mockRepo.getListByName(Mockito.anyString())).thenReturn(list);
    }

    @Test
    public void handle(){
        List<JsonObject> result = (List<JsonObject>) sut.handle(mockRequest, mockResponse);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2);
    }

    @Test
    public void getPath(){
        Assert.assertEquals(sut.getPath(), "/route");
    }
}
