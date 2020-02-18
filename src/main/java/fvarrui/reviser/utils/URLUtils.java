package fvarrui.reviser.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class URLUtils {
	
	private static final Pattern URL_PATTERN = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");

	public static String getFile(String uri) {
		String [] fragments = uri.split("/");
		return fragments[fragments.length - 1];		
	}
	
	/**
	 * Finds first URL in a string
	 * @param text String
	 * @return URL
	 */
	public static String findUrl(String text) {
		Matcher m = URL_PATTERN.matcher(text);
		if (m.find()) {
			return m.group();
		}
		return null;
	}
	
	/**
	 * Extracts first URL in a file
	 * @param file
	 * @return URL
	 */
	public static String extractUrl(File file) {
		try {
			String content = FileUtils.readFileToString(file, Charset.defaultCharset());
			return findUrl(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}

}