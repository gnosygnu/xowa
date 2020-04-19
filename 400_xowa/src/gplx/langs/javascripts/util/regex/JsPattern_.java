package gplx.langs.javascripts.util.regex;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class JsPattern_ {
	public static final int NONE = 0;
	public static Pattern getOrCompile(ConcurrentHashMap<String, Pattern> map, String pat) {return getOrCompile(map, pat, NONE);}
	public static Pattern getOrCompile(ConcurrentHashMap<String, Pattern> map, String pat, int patFlags) {
		// get pattern
		Pattern pattern = map.get(pat);
		if (pattern == null) {
			pattern = Pattern.compile(pat, patFlags);
			map.put(pat, pattern);
		}
		return pattern;
	}
}
