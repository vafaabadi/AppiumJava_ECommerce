import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static java.time.Duration.ofSeconds;

/////////////////////////////////////////////////////////////
//		Proudly written ba Vafa Abadi
//	    https://www.linkedin.com/in/vafaabadi/
/////////////////////////////////////////////////////////////

public class ECommrce_Hybrid extends baseECommerce {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		
		AndroidDriver<AndroidElement> driver = Capabilities();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//select the country where you want to shop
		driver.findElementByXPath("//android.widget.TextView[@text='Afghanistan']").click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Malta\"));").click();
		//gender: Female
		driver.findElementByXPath("//android.widget.RadioButton[@text='Female']").click(); 
		// Let's Shop
		driver.findElementByAndroidUIAutomator("text(\"Let's  Shop\")").click();
		// expecting validation message for not entering Name.
		String toastMessage = driver.findElementByXPath("//android.widget.Toast").getAttribute("name");
		//assertion to check the actual validation message is the same at the expected validation message
		Assert.assertEquals("Please enter your name", toastMessage);
		//fill in your name bar
		driver.findElementById("com.androidsample.generalstore:id/nameField").sendKeys("mobile automation");   			// <<<<<<--------- use of especial characters in class name.
		// Let's Shop
		driver.findElementByAndroidUIAutomator("text(\"Let's  Shop\")").click();
		// find Jordan 6 Rings - last item on the list
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(new UiSelector().textMatches(\"Jordan 6 Rings\").instance(0))"));
		//number of elements on the list
		int count_1 = driver.findElementsById("com.androidsample.generalstore:id/productName").size();
		//find Jordan 6 Rings and if found, check the price is correct and then click on Add To Cart:
		for(int i=0; i<count_1; i++) 
		{
			String text_1 = driver.findElementsById("com.androidsample.generalstore:id/productName").get(i).getText();
			String price_1 = driver.findElementsById("com.androidsample.generalstore:id/productPrice").get(i).getText();
				if(text_1.equalsIgnoreCase("Jordan 6 Rings")) 
				{
					if(price_1.equalsIgnoreCase("$165.0"))
					{
						driver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(i).click();
						break;
						
					}
				}
		}
		// find Nike boots - last item on the list
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(new UiSelector().textMatches(\"Nike SFB Jungle\").instance(0))"));
		//driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Nike SFB Jungle\"));");
		//number of elements on the list
		int count = driver.findElementsById("com.androidsample.generalstore:id/productName").size();
		//find Nike SFB Jungle and if found, check the price is correct and then click on Add To Cart:
		for(int i=0; i<count; i++) 
		{
			String text = driver.findElementsById("com.androidsample.generalstore:id/productName").get(i).getText();
			String price = driver.findElementsById("com.androidsample.generalstore:id/productPrice").get(i).getText();
				if(text.equalsIgnoreCase("Nike SFB Jungle")) 
				{
					if(price.equalsIgnoreCase("$116.97"))
					{
						driver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(i).click();
						break;
					}
				}
		}
		
		
		// check out page
		driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();
		Thread.sleep(3000);
		
		//validate the selected items
		int count2 = driver.findElementsById("com.androidsample.generalstore:id/productName").size();
		for (int i=0; i<count2; i++)
		{
		String item = driver.findElementsById("com.androidsample.generalstore:id/productName").get(i).getText();
		System.out.println(item);
		}
		
		
		//calculate the sum amount
		int count1 = driver.findElementsById("com.androidsample.generalstore:id/productPrice").size();
		double sum=0;
		for (int i=0; i<count1; i++)
		{
		String amount = driver.findElementsById("com.androidsample.generalstore:id/productPrice").get(i).getText();
		double amountvalue = getAmount(amount);
		sum = sum + amountvalue;
		}
		//picking Total amount
		String Total = driver.findElementsById("com.androidsample.generalstore:id/totalAmountLbl").get(0).getText();
		Total = Total.substring(1);
		double Totalvalue = Double.parseDouble(Total);
		//validating the Sum and Total are equal
		Assert.assertEquals(Totalvalue, sum);
		
		//check box - TAP (not click)
		WebElement checkbox = driver.findElementByClassName("android.widget.CheckBox");
		TouchAction t = new TouchAction(driver);
		t.tap(tapOptions().withElement(element(checkbox))).perform();
		driver.findElementByClassName("android.widget.CheckBox").isSelected();
		//long press on Terms of Conditions
		WebElement Terms = driver.findElementById("com.androidsample.generalstore:id/termsButton");
		t.longPress(longPressOptions().withElement(element(Terms)).withDuration(ofSeconds(2))).release().perform();
		//close the pop up
		driver.findElementById("android:id/button1").click();
		//Visit to the website to complete purchase
		driver.findElementById("com.androidsample.generalstore:id/btnProceed").click();
		
		//switch from native app to web app
		Thread.sleep(4000);
		Set<String> contexts=driver.getContextHandles();
		for(String contextName :contexts)
		{
		System.out.println(contextName);
		}
		driver.context("WEBVIEW_com.androidsample.generalstore");
		Thread.sleep(4000);
		driver.findElementByXPath("//input[@name='q']").sendKeys("hello");
		driver.findElementByXPath("//input[@name='q']").sendKeys(Keys.ENTER);
		// switch back from web app to native app
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.context("NATIVE_APP");   
		
		System.out.println("\n\nTest case completed.");
		System.out.println("\nWell done. You successfully automated this end-to-end journey from Native to Hybrid and back to Native again.");
		
		
		
		
		
	}

	
	public static double getAmount(String value)
	{
		value = value.substring(1);
		double amount2value = Double.parseDouble(value);
		return amount2value;
	}
	
	
}




//android.widget.Toast                                         		 <<<<------ CLASS NAME given to a TOAST MESSAGE
//String toastMessage = driver.findElementByXPath("//android.widget.Toast[0]").getAttribute("name");		System.out.println("toastMessage")
//to hide keyboard:     	driver.hidekeyboard();    				 <<<<------ use it anywhere to hide an open keyboard
