import java.io.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class newGuessRandom {
    public static void main(String[] args) throws IOException, ParseException {
        boolean restart = true;
	while (restart) {
	    
	if (args.length > 0) {
            System.err.println("Usage: java -cp . newGuessRandom.java");
            System.exit(1);
        }

	long seed;
	int guess = 0;
	long init_date;

	Process process = Runtime.getRuntime().exec("adb shell am start -n com.mobilehackinglab.guessme/.MainActivity");
	try {
		Thread.sleep(1000);
	
		String[] commands = {"/bin/bash", "-c", """ 
		adb shell input keyevent KEYCODE_TAB && \
			adb shell input keyevent KEYCODE_TAB"""};
	
		process = Runtime.getRuntime().exec(commands);
		Thread.sleep(1000);
	
		process = Runtime.getRuntime().exec("adb shell input keyevent KEYCODE_ENTER");
		process = Runtime.getRuntime().exec("./timestamp.sh");
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = reader.readLine();
		init_date = Long.parseLong(line);
		System.out.println("Init Timestamp: " + init_date +"\r\n");
		
		String[] commands2 = {"/bin/bash", "-c", """ 
		adb shell input keyevent KEYCODE_TAB && \
		adb shell input keyevent KEYCODE_TAB && \
		adb shell input keyevent KEYCODE_TAB"""};

		process = Runtime.getRuntime().exec(commands2);
		Thread.sleep(1000);

       		for ( seed = init_date+10; seed < init_date+21 ; seed++) {
	    		if (uiDisplayCheck(args)) {
				if (seed != init_date+20) {
					System.out.println("Secret found: " + guess);
					restart = false;
				} else {
					System.out.println("Secret not found");
       				}
				break;
				
			}
            		System.out.println("Using seed: " + seed);

            		Random random = new Random(seed);
	    		guess = generateRandomString(random);
	    		System.out.println("Current Guess:" + guess);
	    		uiDisplayCheck(args);
		    	Thread.sleep(800);
		}
	} catch (InterruptedException e) {
		e.printStackTrace();
    	}
}
    }
    
    public static int generateRandomString(Random random) throws IOException {
	int number = 0;
        number = random.nextInt(1,101);
	
	Process process = Runtime.getRuntime().exec("adb shell input text " + number);
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}	
	String[] commands = {"/bin/bash", "-c", """
		adb shell input keyevent KEYCODE_ENTER && \
		adb shell input keyevent KEYCODE_ENTER && \
		adb shell input keyevent KEYCODE_TAB && \
		adb shell input keyevent KEYCODE_TAB && \
		adb shell input keyevent KEYCODE_TAB && \
		adb shell input keyevent KEYCODE_TAB"""};
	Process  process1 = Runtime.getRuntime().exec(commands);
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
        return number;
    }
        
    public static boolean uiDisplayCheck(String[] args) {
	boolean foundDisabled = false;
        try {
            Process process = Runtime.getRuntime().exec("adb shell uiautomator dump /data/local/tmp/window.xml");
            process.waitFor();

            process = Runtime.getRuntime().exec("adb pull /data/local/tmp/window.xml .");
            process.waitFor();

            File xmlFile = new File("window.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("node");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String resourceId = element.getAttribute("resource-id");

                if (resourceId.equals("com.mobilehackinglab.guessme:id/guessButton")) {
                        if ("false".equals(element.getAttribute("enabled"))) {
                            foundDisabled = true;
                        }
                    }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	return foundDisabled;
    }
}
