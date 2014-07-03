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
public class PostDrugsRouteTest {

    private PostDrugsRoute sut;
    @Mock
    private Response mockResponse;
    @Mock
    private Request mockRequest;

    @Mock
    private DrugRepository mockRepo;

    @BeforeMethod
    public void beforeMethod(){
        MockitoAnnotations.initMocks(this);
        sut = new PostDrugsRoute("/route", mockRepo);

        Mockito.when(mockRequest.body()).thenReturn("{\"id\":10}");
    }

    @Test
    public void handle(){
        Object result = sut.handle(mockRequest, mockResponse);
        Assert.assertNotNull(result);
        Mockito.verify(mockRepo).save(Mockito.any(Drug.class));
    }

    @Test
    public void getPath(){
        Assert.assertEquals(sut.getPath(), "/route");
    }
}
