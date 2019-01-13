package Java;
import java.util.UUID;

import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;

import crack.JxBrowserHackUtil;
import crack.JxVersion;


public class Main {
	
    public static void main(String[] args) {

    	  JxBrowserHackUtil.hack(JxVersion.V6_22);

          String identity = UUID.randomUUID().toString();
          BrowserContextParams params = new BrowserContextParams("temp/browser/" + identity);
          BrowserContext context1 = new BrowserContext(params);
          
          ControladorLogin cl = new ControladorLogin(context1);
    	
    }

}
