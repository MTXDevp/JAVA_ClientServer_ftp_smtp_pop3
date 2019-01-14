package Peval4.ServidorFtpStmpPop3Maven;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
public class SeleniumWebDriver {
	
	
	
	public SeleniumWebDriver() {
		
		  ChromeDriverService service = new ChromeDriverService.Builder()
	                .usingDriverExecutable(new File("C:\\Users\\USUARIO\\Desktop\\chromedriver.exe"))
	                .usingAnyFreePort()
	                .build();
	        		try {
						service.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
	        

	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        ChromeOptions options = new ChromeOptions();

	        // The address string should be in the form of "hostname/ip:port". 9222 is the port that you specified in the
	        // --remote-debugging-port Chromium switcher before starting JxBrowser.
	        String remoteDebuggingAddress = "localhost:9222";

	        // Set the address to the appropriate ChromeOption option and apply it to the DesiredCapabilities.
	        // The full list of these options you can find at
	        // https://sites.google.com/a/chromium.org/chromedriver/capabilities
	        options.setExperimentalOption("debuggerAddress", remoteDebuggingAddress);
	        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

	        // Create a WebDriver instance using URL provided by the ChromeDriverService and capabilities with
	        // JxBrowser remote debugging address.
	        WebDriver driver = new RemoteWebDriver(service.getUrl(), capabilities);

	        // Now you can use WebDriver.
	        System.out.println(driver.getCurrentUrl());

	        // Quit from the current session to prevent Chromium remote debugging server hang.
	        driver.quit();
	        service.stop();

		
	}

}
