package org.plech.javaFileJoiner;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * @author Dominik Plisek
 */
public class JavaFileJoiner {
	
	private JavaFileStripper javaFileStripper;
	private PublicToPackagePrivateConverter publicToPackagePrivateConverter;

	private String targetPackageName;
	private File topLevelDir;

	public JavaFileJoiner(String topLevelDirPath, String targetPackageName) {
		this.targetPackageName = targetPackageName;
		this.topLevelDir = getValidatedDirectory(topLevelDirPath);
		javaFileStripper = new JavaFileStripper();
		publicToPackagePrivateConverter = new PublicToPackagePrivateConverter();
	}

	private File getValidatedDirectory(String topLevelDirPath) {
		File topLevelDir = new File(topLevelDirPath);
		validateDirectory(topLevelDir);
		return topLevelDir;
	}

	private void validateDirectory(File topLevelDir) {
		if (!topLevelDir.isDirectory()) {
			throw new IllegalArgumentException("The specified top level directory path does not point to a directory.");
		}
	}

	private void execute() throws IOException {
		System.out.println(getPackageDeclaration());
		processFilesOrDirectories(topLevelDir.listFiles());
	}
	
	private String getPackageDeclaration() {
		return "package " + targetPackageName + ";\n";
	}

	private void processFilesOrDirectories(File[] filesOrDirectories) throws IOException {
		for (File fileOrDirectory : filesOrDirectories) {
			processFileOrDirectory(fileOrDirectory);
		}
	}

	private void processFileOrDirectory(File file) throws IOException {
		if (file.isDirectory()) {
			processDirectory(file);
		} else {
			processFile(file);
		}
	}

	private void processDirectory(File directory) throws IOException {
		processFilesOrDirectories(directory.listFiles());
	}

	private void processFile(File file) throws IOException {
		String contents = getContentsOfFile(file);
		contents = javaFileStripper.strip(contents);
		contents = publicToPackagePrivateConverter.convert(contents);
		System.out.println(contents);
	}

	private String getContentsOfFile(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtils.copy(in, out);
		return out.toString();
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			throw new IllegalArgumentException("Usage: <top level directory path> <target package name>");
		}
		new JavaFileJoiner(args[0], args[1]).execute();
	}

}
