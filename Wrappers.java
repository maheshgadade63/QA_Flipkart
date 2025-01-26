package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    public static void enterTextWrapper(WebDriver driver, By locator, String textToEnetr) {
        System.out.println("Sending keys");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement inputBox = driver.findElement(locator);
            inputBox.clear();
            inputBox.sendKeys(textToEnetr, Keys.ENTER);
        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
        }
    }

    public static void clickOnElementWrapper(WebDriver driver, By locator) {
        System.out.println("Clicking");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            driver.findElement(locator).click();
        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
        }
    }

    public static boolean searchStarRatingAndPrintCount(WebDriver driver, By locator, double starRating,
            String productName) {
        int productCount = 0;
        boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            List<WebElement> starRatingElements = driver.findElements(locator);
            for (WebElement starRatingElement : starRatingElements) {
                if (Double.parseDouble(starRatingElement.getText()) <= starRating) {
                    productCount++;
                }
            }
            System.out.println("count of " + productName + " which has star rating less than or equals to " + starRating
                    + " : " + productCount);
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured!");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static boolean printTitleAndDiscountIPhone(WebDriver driver, By locator, int discount) {
        boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            List<WebElement> productRows = driver.findElements(locator);
            HashMap<String, String> iphoneTitleDiscountMap = new HashMap<>();
            for (WebElement productRow : productRows) {
                String discountPercent = productRow.findElement(By.xpath("//div[@class='UkUFwK']/span")).getText();
                int discountValue = Integer.parseInt(discountPercent.replaceAll("[^\\d]", ""));
                if (discountValue > discount) {
                    String iphoneTitle = productRow.findElement(By.xpath(".//div[@class='KzDlHZ']")).getText();
                    iphoneTitleDiscountMap.put(discountPercent, iphoneTitle);
                }
            }

            for (Map.Entry<String, String> iphoneTitleDiscount : iphoneTitleDiscountMap.entrySet()) {
                System.out.println("Iphone discount percentage :: " + iphoneTitleDiscount.getKey()
                        + " and its title :: " + iphoneTitleDiscount.getValue());
            }
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured!");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static boolean printTitleAndImageUrlOfCoffMug(WebDriver driver, By locator) {
        boolean success;
        try {
            List<WebElement> userReviewElements = driver.findElements(locator);
            Set<Integer> userReviewSet = new HashSet<>();
            for (WebElement userReviewElement : userReviewElements) {
                int userReview = Integer.parseInt(userReviewElement.getText().replaceAll("[^\\d]", ""));
                userReviewSet.add(userReview);
            }
            List<Integer> userReviewCountList = new ArrayList<>(userReviewSet);
            Collections.sort(userReviewCountList, Collections.reverseOrder());
            System.out.println("*****************************************************");
            System.out.println(userReviewCountList);
            System.out.println("*****************************************************");
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            LinkedHashMap<String, String> productDetailsMap = new LinkedHashMap<>();
            for (int i = 0; i < 5; i++) {
                String formattedUserReviewCount = "(" + numberFormat.format(userReviewCountList.get(i)) + ")";
                String productTitle = driver
                        .findElement(By.xpath(
                                "//div[@class='slAVV4']//span[contains(text(),'" + formattedUserReviewCount + "')]"))
                        .getText();
                String productImgURL = driver.findElement(By.xpath("//div[@class='slAVV4']//span[contains(text(),'"
                        + formattedUserReviewCount + "')]/../..//img[@class='DByuf4']")).getAttribute("src");
                String hightestReviewCountAndProductTitle = String.valueOf(i + 1) + " hightest review count : "
                        + formattedUserReviewCount + "Title of Product : " + productTitle;
                productDetailsMap.put(hightestReviewCountAndProductTitle, productImgURL);
            }
            for(Map.Entry<String, String> productDitails : productDetailsMap.entrySet()){
                System.out.println(productDitails.getKey()+" and Product img URL: "+productDitails.getValue());
            }
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured!");
            e.printStackTrace();
            success = false;            
        }
        return success;
        
    }
}
