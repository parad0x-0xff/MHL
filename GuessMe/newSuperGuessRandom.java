import java.io.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class newSuperGuessRandom {
    public static void main(String[] args) throws IOException, ParseException {
        boolean restart = true;
	while (restart) {
	    
	if (args.length > 0) {
            System.err.println("Usage: java -cp . newSuperGuessRandom.java ");
            System.exit(1);
        }

	long seed;
	int guess = 0;
	long init_date;
	int lowEnd = 1;
	int highEnd = 101;

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
		
		String[] commands2 = {"/bin/bash", "-c", """ 
		adb shell input keyevent KEYCODE_TAB && \
		adb shell input keyevent KEYCODE_TAB && \
		adb shell input keyevent KEYCODE_TAB"""};

		process = Runtime.getRuntime().exec(commands2);
		Thread.sleep(1000);

       		for ( seed = init_date; seed < init_date+11 ; seed++) {
	    		if (uiDisplayCheck(args)) {
				//System.out.println("Button is disabled");
				if (seed != init_date+10) {
					System.out.println("Secret found: " + guess);
					restart = false;
				} else {
					System.out.println("Secret not found");
       				}
				break;
				
			}
            		System.out.println("Using seed: " + seed);

            		Random random = new Random(seed);
	    		guess = generateRandomString(random, lowEnd, highEnd);
	    		System.out.println("Current Guess:" + guess);
	    		uiDisplayCheck(args);
			String tooWhat = numberCheck(args);
			if ("Too high! Try again.".equals(tooWhat)) {
				highEnd = guess;
				System.out.println("The guess is: "+ tooWhat);
			} else {
				lowEnd = guess;
				System.out.println("The guess is: "+ tooWhat);
			}
		    	Thread.sleep(800);
		}
	} catch (InterruptedException e) {
		e.printStackTrace();
    	}
}
    }
    
    public static int generateRandomString(Random random,int lowEnd, int highEnd) throws IOException {
	int number = 0;
        number = random.nextInt(lowEnd, highEnd);
	System.out.println("The Random range: " + lowEnd + " " + highEnd);
	
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
    public static String numberCheck(String[] args) {
        String level = "";
        try {


            File xmlFile = new File("window.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("node");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
		if ("Too high! Try again.".equals(element.getAttribute("text"))) {
			level = element.getAttribute("text");
			break;
		}
		if ("Too low! Try again.".equals(element.getAttribute("text"))) {
			level = element.getAttribute("text");
			break;
	    }
	}

        } catch (Exception e) {
            e.printStackTrace();
	}
	return level;
    } 
}
