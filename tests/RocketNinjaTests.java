package automation.tests;

import automation.base.BaseTest;
import automation.pages.HomePage;
import automation.pages.RestClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class RocketNinjaTests extends BaseTest {

    HashMap<String, String> miceData = new HashMap<>();
    HashMap<String, String> resultTable;
    HomePage homePage = new HomePage(driver);
    RestClient restClient = new RestClient();

    private static String invalidNotification = "Width and Length can only contain digits";
    private static String miceNotFoundNotification = "mice not found";


    @Test
    public void searchMice() {
        setup();
        homePage.waitXSeconds(5);
        String lengthColumn = "Length (cm)";
        String widthColumn = "Length (cm)";
        //verify page is display
        Assert.assertTrue(homePage.isHomePageDisplayed());

        resultTable = homePage.searchMice(miceData);
        //should be true if false mice is not found
        Assert.assertTrue(resultTable.size() > 0, "mice is not found!");

        int valLength = parseInt(miceData.get("hLength"));
        int valWidth = parseInt(miceData.get("hWidth"));
        //verify that the hand Length measurements of the returned mice do not exceed the measurements we entered.
        Assert.assertTrue(homePage.confirmResultMeasurements(lengthColumn, resultTable, valLength));

        //verify that the hand Width measurements of the returned mice do not exceed the measurements we entered.
        Assert.assertTrue(homePage.confirmResultMeasurements(widthColumn, resultTable, valWidth));
    }

    @Test
    public void verifyResult() {
        setup();
        resultTable = homePage.searchMice(miceData);
        Assert.assertTrue(homePage.verifyResult(resultTable), "result table is not presenting in the correct way");
    }

    @Test
    public void searchMice2Options() {
        setUp3();
        resultTable = homePage.searchMice(miceData);
        //should be true , mice found with 2 options.if false mice is not found
        Assert.assertTrue(resultTable.size() > 0, "mice is not found!");
    }

    @Test(description = "c")
    public void searchMiceFalse() {
        setUp2();
        resultTable = homePage.searchMice(miceData);
        //should get mice not found
        Assert.assertEquals(resultTable.get("notification"), miceNotFoundNotification, "didn't get the correct notification : " + miceNotFoundNotification);
    }

    @Test(description = "c")
    public void searchMiceInvalidData() {
        setup();
        miceData.put("hLength", "XX");
        miceData.put("hWidth", "XX");
        resultTable = homePage.searchMice(miceData);
        //should get invalid val notification for not inserting digit val to hLength and hWidth
        Assert.assertEquals(resultTable.get("notification"), invalidNotification, "didn't get the correct notification : " + invalidNotification);
    }

    @Test
    public void ApiTest() {
        String apiUrl = "https://www.rocketjumpninja.com/api/search/mice";

        try {
            int statusCode = restClient.get(apiUrl);
            Assert.assertEquals(statusCode, 200, "api request wasn't successfully preformed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setup() {
        miceData.put("measureUnit", "CN");
        miceData.put("leniency", "low");
        miceData.put("hLength", "18");
        miceData.put("hWidth", "9");
        miceData.put("gridType", "claw");
        miceData.put("shape", "both");
        miceData.put("options", "Wireless");
    }

    public void setUp2() {
        setup();
        miceData.put("hLength", "20");
        miceData.put("hWidth", "20");
    }

    public void setUp3() {
        setup();
        miceData.put("option2", "Left-handed");
    }
}

