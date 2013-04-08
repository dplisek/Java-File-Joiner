package org.plech.javaFileJoiner;

/**
 * @author Dominik Plisek
 */
public class PublicToPackagePrivateConverter {

	public String convert(String strippedFile) {
		if (strippedFile.contains("public static void main")) return strippedFile;
		return strippedFile.replaceFirst("public ", "");
	}

}
