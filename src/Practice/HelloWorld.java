package Practice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloWorld {

	public static void main(String[] args) throws InterruptedException, MalformedURLException, IOException {

		String exePath = "H:\\SeleniumSetup\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", exePath);

		WebDriver driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://makemysushi.com/Recipes/how-to-make-california-sushi-rolls");

		// 1. Find all a tags and images and store in a list.
		List<WebElement> LinkLists = driver.findElements(By.tagName("a"));
		// 2. Adding the images TAGS
		LinkLists.addAll(driver.findElements(By.tagName("img")));

		System.out.println(LinkLists.size());

		List<WebElement> ActiveList = new ArrayList<WebElement>();

		for (int i = 0; i < LinkLists.size(); i++) {

			//LinkLists.get(i).getAttribute("href");
			if (LinkLists.get(i).getAttribute("href") != null &&!(LinkLists.get(i).getAttribute("href").contains("javascript"))&& !(LinkLists.get(i).getAttribute("href").contains("mailto:")) &&
					!(LinkLists.get(i).getAttribute("href").contains("plus.google.com"))) {
				ActiveList.add(LinkLists.get(i));
			}

		}

		System.out.println("No.of Links and Images ===>"+ActiveList.size());
		
		//3.Check the url with the HTTP connecttion API.
		
		for(int j=0; j<ActiveList.size();j++) {
			
			HttpURLConnection connection = (HttpURLConnection)new URL(ActiveList.get(j).getAttribute("href")).openConnection();
			connection.connect();
			String res = connection.getResponseMessage();
			connection.disconnect();
			System.out.println(ActiveList.get(j).getAttribute("href")+"------>" + res);
			
		}
		
		 
	}

}
