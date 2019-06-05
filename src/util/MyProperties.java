package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class MyProperties extends Properties{
	
	private MyProperties() throws IOException{
		InputStream in = MyProperties.class.getClassLoader().getResourceAsStream("my.properties");
		try {
			this.load(in);
		} catch (IOException e) {		
			e.printStackTrace();
			throw e;
		}
	}
	
	public static MyProperties getInstance() throws IOException{
		return new MyProperties();
	}
	
	//–¥»ÎProperties–≈œ¢
	public static void WriteProperties (String filePath, String pKey, String pValue) throws IOException {
		Properties pps = new Properties();
		InputStream in = new FileInputStream(filePath);
		pps.load(in);
		OutputStream out = new FileOutputStream(filePath);
		pps.setProperty(pKey, pValue);
		pps.store(out, "Update " + pKey + " name");
	}

}
