import com.sun.tools.corba.se.idl.constExpr.LessThan;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Test1 {
    @BeforeMethod
    public void setUp(){
        useRelaxedHTTPSValidation();
    }

    @Test
    public void testTesterHome(){

       given().param("q","appium")
               .when().get("https://testerhome.com/search").prettyPeek()
               .then().statusCode(200).body("html.head.title",equalTo("appium · 搜索结果 · TesterHome"));

    }

    @Test
    public void testTesterHomeApi(){

        Response response = given().get("https://testerhome.com/api/v3/topics.json");
        System.out.println(response.jsonPath().getList("topics").size());
    }

    @Test
    public void testTesterHomeJson(){
//        given().when().get("https://testerhome.com/api/v3/topics.json").prettyPeek()
//                .then().statusCode(200)
//                .body("topics.title",hasItem("TesterHome 北京管理沙龙·第二期"));

        given().when().get("https://testerhome.com/api/v3/topics.json").prettyPeek()
                .then().statusCode(200)
                .body("topics.findAll{topic->topic.id==16175}.title",hasItem("[深圳] 阿里巴巴 Lazada 深圳研发中心：招聘高级测试开发工程师 / 专家 [工具平台开发方向，可以年前面试，年后入职]"));
    }

    @Test
    public void testXML() {
        given().when().get("http://localhost:8000/index.xml")
                .then().statusCode(200)
                .body("shopping.category[0].item[0].name", equalTo("Chocolate"))
                .body("shopping.category[0].item.size()", equalTo(2))
                .body("shopping.category.findAll { it.@type == 'groceries' }.size()",equalTo(1))
                .body("**.find { it.name ==  'Chocolate'}.price",equalTo("10"))
                .time(lessThan(500L));
    }

    @Test
    public void testSpec(){
        ResponseSpecification responseSpecification = new ResponseSpecBuilder().build();
        responseSpecification.statusCode(200);
        responseSpecification.time(lessThan(1500L));

        given()
                .when()
                    .get("https://testerhome.com/api/v3/topics.json")
                .then()
                    .spec(responseSpecification);

    }


}
