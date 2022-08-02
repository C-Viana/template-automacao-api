package barrigarest;

import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import common.StaticResources;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class _BarrigaRestHooks extends BarrigaRestUtils implements StaticResources {
	
    @BeforeClass
    public static void beforeAll() {
        RestAssured.baseURI = BARRIGAREST_BASE_URL;
        RestAssured.port = BARRIGAREST_PORT;
        RestAssured.basePath = BARRIGAREST_BASE_PATH;

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(BARRIGAREST_CONTENT_TYPE);
        RestAssured.requestSpecification = reqBuilder.build();
        
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(DEFAULT_WAIT_TIME.toNanos()));
        RestAssured.responseSpecification = resBuilder.build();
        
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.requestSpecification.header("Authorization", "JWT " + generateToken());
    }

    @AfterClass
    public static void afterAll() {
        getTransactions().stream().forEach( e -> deleteTransaction(e.getId()) );
        getAccounts().stream().forEach( e -> deleteAccount(e.getId()) );
    }

}
