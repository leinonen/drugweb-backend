package se.leinonen.drugweb.routes;

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
public class GetRandomRouteTest {
    private GetRandomRoute sut;
    @Mock
    private Response mockResponse;
    @Mock
    private Request mockRequest;

    @Mock
    private DrugRepository mockRepo;

    @BeforeMethod
    public void beforeMethod(){
        MockitoAnnotations.initMocks(this);
        sut = new GetRandomRoute("/route", mockRepo);

        Mockito.when(mockRepo.findRandom()).thenReturn(new Drug());
    }

    @Test
    public void handle(){
        Object result = sut.handle(mockRequest, mockResponse);
        Assert.assertNotNull(result);
    }

    @Test
    public void getPath(){
        Assert.assertEquals(sut.getPath(), "/route");
    }
}
