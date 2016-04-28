package project;

import java.util.HashMap;

public class FontMap
{
	private static HashMap<String, String> map = new HashMap<String, String>();
	static
	{
		map.put("Arial", "Arial");
		map.put("Century Schoolbook", "Century-Schoolbook");
		map.put("Cooper Black", "Cooper-Black");
		map.put("Courier New", "Courier");
		map.put("Gill Sans MT", "Gill-Sans-MT");
		map.put("Goudy Old Style", "Goudy-Old-Style");
		map.put("SansSerif", "SansSerif");
		map.put("LetterGothicStd", "LetterGothicStd");
		map.put("Garamond", "Garamond");
		map.put("TektonPro", "TektonPro");
		map.put("Times New Roman", "Times-New-Roman");
	}
	public static String getEPSFont(String font)
	{
		return map.get(font);
	}
}
