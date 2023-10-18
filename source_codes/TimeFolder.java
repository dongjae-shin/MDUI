package Components;

import java.io.*;
import java.util.Calendar;

public class TimeFolder {
	
	Calendar cal = Calendar.getInstance();
	String dateString;
	
	//Constructor
	public TimeFolder() {
		
		dateString = String.format("%04d-%02d-%02d-%02d.%02d.%02d", 
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		
		File folder = new File("data_folder/" + dateString);
		if(!folder.mkdirs())
			System.err.println("Directory Generation failed!");
	}
	
	//Accessor to folder name	
	public String getFolderName() {
		return dateString;
	}
	
}
