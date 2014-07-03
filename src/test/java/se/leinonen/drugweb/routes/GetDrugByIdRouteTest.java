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
public class GetDrugByIdRouteTest {
    private GetDrugByIdRoute sut;
    @Mock
    private Response mockResponse;
    @Mock
    private Request mockRequest;

    @Mock
    private DrugRepository mockRepo;

    @BeforeMethod
    public void beforeMethod(){
        MockitoAnnotations.initMocks(this);
        sut = new GetDrugByIdRoute("/route", mockRepo);

        Mockito.when(mockRequest.params(":id")).thenReturn("10");

        Mockito.when(mockRepo.findById(Mockito.anyLong())).thenReturn(new Drug());
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
