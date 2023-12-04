package tests;

import automation.base.BaseTest;
import automation.pages.HomePage;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static java.lang.Integer.parseInt;


public class TestOne extends BaseTest {
    HashMap<String, String> miceData = new HashMap<>();
    HashMap<String, String> resultTable;
    HomePage homePage = new HomePage(driver);


    @Test
    public void searchMice() {
        setUp();
        homePage.waitXSeconds(20);
        String lengthColumn = "Length (cm)";
        String widthColumn = "Length (cm)";

        //searchMice
        resultTable = homePage.searchMice(miceData);
        //should be true if false mice is not found
        Assert.assertTrue("mice is not found!", resultTable.size() > 0);

        int valLength = parseInt(miceData.get("hLength"));
        int valWidth = parseInt(miceData.get("hWidth"));
        //verify that the hand Length measurements of the returned mice do not exceed the measurements we entered.
        Assert.assertTrue(homePage.confirmResultMeasurements(lengthColumn, resultTable, valLength));

        //verify that the hand Width measurements of the returned mice do not exceed the measurements we entered.
        Assert.assertTrue(homePage.confirmResultMeasurements(widthColumn, resultTable, valWidth));
    }

    @Test
    public void searchMice2Options() {

        setUp3();
        resultTable = homePage.searchMice(miceData);
        //should be true , mice found with 2 options.if false mice is not found
        Assert.assertTrue("mice is not found!", resultTable.size() > 0);

    }

    @Test
    public void searchMiceFalse() {

        setUp2();
        resultTable = homePage.searchMice(miceData);
        //should be false, if true so mice is found
        Assert.assertFalse("mice is found", resultTable.size() > 0);

    }


    public void setUp() {
        miceData.put("measureUnit", "CN");
        miceData.put("leniency", "low");
        miceData.put("hLength", "18");
        miceData.put("hWidth", "9");
        miceData.put("gridType", "claw");
        miceData.put("shape", "both");
        miceData.put("options", "Wireless");
    }

    public void setUp2() {
        setUp();
        miceData.put("hLength", "20");
        miceData.put("hWidth", "20");
    }

    public void setUp3() {
        setUp();
        miceData.put("option2", "Left-handed");

    }
}

