package com.example.filesearch;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSearchApp {
	String path;
	String regex;
	String zipFileName;
	
	public static void main(String[] args) {
		FileSearchApp app = new FileSearchApp();
		
		switch( Math.min(args.length, 3)) {
		case 0:
			System.out.println("USAGE: FileSearchApp path [regex] [zipFile]");
			return;
		case 3: app.setZipFileName(args[2]);	
		case 2: app.setRegex(args[1]);
		case 1: app.setPath(args[0]);
		
		}
		
		try {
			app.walkDirectoryJava8(app.getPath());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	public void walkDirectory(String path) {
		System.out.println("walkDirectory: " + path);
		searchFile(null);
		addFileToZip(null);
	}
	*/
	
	public void walkDirectoryJava8(String path) throws IOException {
		Files.walk(Paths.get(path))
			.forEach(f -> processFile(f.toFile()));
	}
	
	public boolean searchFile(File file) throws IOException {
		//System.out.println("SearchFile: " + file);
		//return true;
		return searchFileJava8(file);
	}
	
	public boolean searchFileJava8(File file) throws IOException {
		return Files.lines(file.toPath(), StandardCharsets.UTF_8)
				.anyMatch(t -> searchText(t));
	}
	
	public boolean searchText(String text) {
		return true;
	}
	
	public void addFileToZip(File file) {
		System.out.println("addFileToZip: " + file);
	}
	
	public void processFile(File file) {
		//System.out.println("processFile: " + file);
		try {
			if(searchFile(file)) {
				addFileToZip(file);
			}
		} catch (IOException|UncheckedIOException e) {
			System.out.println("Error processing the file: "
					+ file + ": " + e);
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getZipFileName() {
		return zipFileName;
	}

	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}

}
