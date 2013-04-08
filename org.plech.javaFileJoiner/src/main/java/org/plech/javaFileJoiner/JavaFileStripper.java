package org.plech.javaFileJoiner;

import java.util.regex.Pattern;

/**
 * @author Dominik Plisek
 */
public class JavaFileStripper {
	
	private static final String PACKAGE_LINE_PATTERN = "^\\s*package\\s.*$";
	private static final String IMPORT_LINE_PATTERN = "^\\s*import\\s.*$";
	
	public String strip(String contents) {
		contents = Pattern.compile(PACKAGE_LINE_PATTERN, Pattern.MULTILINE).matcher(contents).replaceAll("");
		contents = Pattern.compile(IMPORT_LINE_PATTERN, Pattern.MULTILINE).matcher(contents).replaceAll("");
		return contents;
	}


}
