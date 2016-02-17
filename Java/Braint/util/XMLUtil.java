package Braint.util;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

import Braint.settings.BigSettings;

public class XMLUtil {

	public static void main(String[] args) {
		XMLUtil asd = new XMLUtil();
		TestClass example = new TestClass();
		
		SummaryStatistics asd1 = new SummaryStatistics();
		Random rnd = new Random();
		
		for (int i = 0; i < 1000; i++) {
			asd1.addValue(rnd.nextDouble() * 100 );
		}
		
		
		
		BigSettings.instance().agentMinColorValue = 99999;
		
		XMLUtil.serializeObject(BigSettings.instance(), "example2");
		
		
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		BigSettings test = XMLUtil.deserialzeObject(new BigSettings(), "example2");
		
//		SummaryStatistics test = XMLUtil.deserialzeObject(new SummaryStatistics(), "example2");
		
//		System.out.println(test.CALIBRATION_TIME);
//		System.out.println(test.THE_FRAMERATE);
//		System.out.println(test.OSC_PORT);
//		System.out.println(test.toString());
		
		
//		System.out.println(test.getStandardDeviation());

	}

	public static <T> void serializeObject(T objectToSerialize, String fileName) {

		Serializer serializer = new Persister();

		String filename = fileName;

		if (!filename.contains(".xml"))
			filename += ".xml";

		File result = new File("./settings/" + filename);

		if (!result.exists()) {
			try {
				result.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		try {
			serializer.write(objectToSerialize, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static <T> T deserialzeObject(T type, String fileName){
		
		Serializer serializer = new Persister();
		
		String filename = fileName;

		if (!filename.contains(".xml"))
			filename += ".xml";

		File source = new File("./settings/" + filename);
		
		T obj = null;

		try {
			obj = (T) serializer.read(type, source);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
		
	}

}
