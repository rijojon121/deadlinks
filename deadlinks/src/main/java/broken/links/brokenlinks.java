package broken.links;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


/*
 Project Objective:

 1)Find Number of links in the given page. 
 2)Get the Status code of all the links 
 3)Check the links for broken links with status code is >400 

 */

public class brokenlinks {
    
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();


        //Driver Initiation- chrome Driver 
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("http://www.deadlinkcity.com");
        driver.manage().window().maximize();
        Thread.sleep(3000);

        //Get total number of links 
        int numberOfBrokenLinks = 0;
        List<WebElement> links = driver.findElements(By.tagName("a")); 
        System.out.println("Total Number of links are: " + links.size());

        for(WebElement linkElements:links)
        {
          String hrefvalue = linkElements.getAttribute("href");

          if(hrefvalue ==null || hrefvalue.isEmpty())
          {
            System.out.println("The link is broken and connection to server is not possible");
            continue;
          }
        

        // Connect to the Server. 
        try
        {
        URL urllink = new URL(hrefvalue);
        HttpURLConnection connLink = (HttpURLConnection) urllink.openConnection();
        connLink.connect();

        //Get Status/response code 

        if(connLink.getResponseCode()>=400)
        {
            System.out.println("The Link is Broken =========>"+ hrefvalue);
            numberOfBrokenLinks++;
        }else {
            System.out.println("The Link is not Broken =========> "+ hrefvalue);

        }
    }
    catch(Exception e)
    {

    }

        }

        System.out.println("Number of Broken Links: " + numberOfBrokenLinks);






        driver.quit();
        

    }
}
