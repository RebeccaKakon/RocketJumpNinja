package automation.pages;


import automation.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class HomePage extends BasePage {
    private static By measureUnitLocator = By.id("measurement");
    private static By hLengthLocator = By.id("measurement");
    private static By hWidthLocator = By.id("measurement");
    private static By searchLeniencyLocator = By.xpath("//fieldset[text()='%s']"); //give me all options
    private static By gripTypeLocator = By.xpath("//input[@value='%s']");
    private static By shapeLocator = By.xpath("//input[@value='%s']");
    private static By optionsLocator = By.xpath("//input[@value='%s']");
    private static By searchButtonLocator = By.xpath("//*[text()='SEARCH']");
    private static By resultTableLocator = By.xpath("//table[@class='top sortable']");
    private By searchBox = By.name("q");
    private WebElement homeElement = driver.findElement(By.xpath("//div[@class=\"title_bar flex_container\"]"));

    static Logger logger = LoggerFactory.getLogger(BasePage.class);

    public HomePage(WebDriver driver) {
        super(driver);
    }


    public boolean isHomePageDisplayed() {
        if (homeElement.isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    public static HashMap searchMice(HashMap miceData) {
        WebElement measurementDropdown = driver.findElement(By.id("measurement"));
        try {
            selectMeasureUnit(measurementDropdown, miceData.get("measureUnit").toString());
            driver.findElement(hLengthLocator).sendKeys(miceData.get("hLength").toString());
            driver.findElement(hWidthLocator).sendKeys(miceData.get("lWidth").toString());
            driver.findElement(By.xpath(String.format(miceData.get("leniency").toString(), searchLeniencyLocator))).click();
            driver.findElement(By.xpath(String.format(miceData.get("gripType").toString(), gripTypeLocator))).click();
            driver.findElement(By.xpath(String.format(miceData.get("shape").toString(), shapeLocator))).click();
            clickOptions(miceData);
            driver.findElement(searchButtonLocator).click();
            return getResults();
        } catch (NoSuchElementException e) {
            // Handle the case where a suitable mouse is not found for any reason
            e.printStackTrace();
            String notification = getInvalidValueNotification();
            return (HashMap) new HashMap().put("notification", notification);
        }
    }

    private static String getInvalidValueNotification() {
        return driver.findElement(By.id("invalidValueNotification")).getText();
    }

    private static void clickOptions(HashMap miceData) {
        driver.findElement(By.xpath(String.format(miceData.get("option").toString(), optionsLocator))).click();

        //if the user want to choose more than one option
        if (miceData.containsKey("option2")) {
            driver.findElement(By.xpath(String.format(miceData.get("option2").toString(), optionsLocator))).click();
        }
    }


    private static HashMap getResults() {
        HashMap<String, String> tableData = new HashMap<>();

        // Locate all rows within the table
        List<WebElement> rows = resultTableLocator.findElements((SearchContext) By.xpath("//tr"));

        // Iterate through each row
        for (WebElement row : rows) {
            // Locate all columns within the row
            List<WebElement> columns = row.findElements(By.tagName("td"));

            // Assuming the first column is the key and the second column is the value
            if (columns.size() >= 2) {
                String key = columns.get(0).getText();
                String value = columns.get(1).getText();
                tableData.put(key, value);
            }
        }

        return tableData;
    }

    private static void selectMeasureUnit(WebElement dropdownElement, String optionText) {
        // Create a Select object by passing the dropdown element to its constructor
        Select dropdown = new Select(dropdownElement);

        // Use the selectByVisibleText method to choose an option by its visible text
        dropdown.selectByVisibleText(optionText);
    }


    public void searchFor(String query) {
        driver.findElement(searchBox).sendKeys(query);
        driver.findElement(searchBox).submit();
    }

    public boolean confirmResultMeasurements(String columnKey, HashMap<String, String> resultTable, int val) {
        String lengthColumnKey = columnKey;

        // Check if the table contains the specified column
        if (resultTable.containsKey(lengthColumnKey)) {
            List<String> lengthColumnValues = Collections.singletonList(resultTable.get(lengthColumnKey));

            // Check each value in the "Length (cm)" column
            for (String value : lengthColumnValues) {
                try {
                    double lengthValue = Double.parseDouble(value.trim());
                    if (lengthValue > val) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where a value is not a valid number
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public boolean verifyResult(HashMap<String, String> resultTable) {
        String firstRowKey = resultTable.keySet().iterator().next();
        String[] expectedColumns = {"Rank", "Name", "Weight(g)", "Size", "Length(cm)", "Grid Width(cm)", "Review"};

        if (!firstRowKey.equals(String.join(", ", expectedColumns))) {
            logger.info("First key row does not contain the expected values");
            return false;
        }

        return resultTable.values().stream()
                .allMatch(row -> {
                    String[] values = row.split(", ");
                    return values.length == expectedColumns.length && allValuesNotEmpty(values);
                });
    }

    private boolean allValuesNotEmpty(String[] values) {
        return java.util.Arrays.stream(values).allMatch(value -> !value.isEmpty());
    }
}
