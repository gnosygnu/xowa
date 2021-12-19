package gplx.langs.javascripts;
import gplx.langs.javascripts.util.regex.JsPattern_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsString_ {
	public static boolean charAtEq(String s, int i, char m) {
		int len = s.length();
		if (i < 0 || i >= len) {
			return false;
		} else {
			return s.charAt(i) == m;
		}
	}
	public static boolean charAtEqNot(String s, int i, char m) {
		int len = s.length();
		if (i < 0 || i >= len) {
			return false;
		} else {
			return s.charAt(i) != m;
		}
	}
	public static String charAt(String s, int i) {
		int len = s.length();
		if (i < 0 || i >= len) {
			return StringUtl.Empty;
		} else {
			return Character.toString(s.charAt(i));
		}
	}
	public static String slice(String str, int beginIndex) {
		return slice(str, beginIndex, str.length());
	}
	// REF.JOS:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/slice
	public static String slice(String str, int beginIndex, int endIndex) {
		int srcLen = str.length();

		// bgn
		if (beginIndex < 0) {
			beginIndex = srcLen + beginIndex;
			if (beginIndex < 0) {
				beginIndex = 0;
			}
		}
		else if (beginIndex > srcLen) {
			return "";
		}

		// end
		if (endIndex < 0) {
			endIndex = srcLen + endIndex;
			if (endIndex < 0) {
				endIndex = 0;
			}
		}
		else if (endIndex > srcLen) {
			endIndex = srcLen;
		}
		if (endIndex < beginIndex) {
			return "";
		}

		return str.substring(beginIndex, endIndex);
	}
	private static final int
		  REPLACE_TYPE_STR = 0
		, REPLACE_TYPE_MATCHER = 1
		, REPLACE_TYPE_ARG1 = 2
		, REPLACE_TYPE_ARG2 = 3
		, REPLACE_TYPE_ARG3 = 4
		;
	public interface JsStringReplaceArg1 {
		String processMatcher(String all);
	}
	public interface JsStringReplaceArg2 {
		String processMatcher(String all, String arg1);
	}
	public interface JsStringReplaceArg3 {
		String processMatcher(String all, String arg1, String arg2);
	}
	public interface JsStringReplaceMatcher {
		String processMatcher(Matcher m);
	}
	public static String replace(String s, ConcurrentHashMap<String, Pattern> map, String p, String repl) {
		return replace(s, map, p, JsPattern_.NONE, repl);
	}
	public static String replace(String src, ConcurrentHashMap<String, Pattern> map, String pat, int patFlags, String repl) {
		return replaceObject(src, map, pat, patFlags, repl, REPLACE_TYPE_STR);
	}
	public static String replaceArg1(String src, ConcurrentHashMap<String, Pattern> map, String pat, JsStringReplaceArg1 func) {
		return replaceObject(src, map, pat, JsPattern_.NONE, func, REPLACE_TYPE_ARG1);
	}
	public static String replaceArg1(String src, ConcurrentHashMap<String, Pattern> map, String pat, int patFlags, JsStringReplaceArg1 func) {
		return replaceObject(src, map, pat, patFlags, func, REPLACE_TYPE_ARG1);
	}
	public static String replaceArg2(String src, ConcurrentHashMap<String, Pattern> map, String pat, JsStringReplaceArg2 func) {
		return replaceObject(src, map, pat, JsPattern_.NONE, func, REPLACE_TYPE_ARG2);
	}
	public static String replaceArg2(String src, ConcurrentHashMap<String, Pattern> map, String pat, int patFlags, JsStringReplaceArg2 func) {
		return replaceObject(src, map, pat, patFlags, func, REPLACE_TYPE_ARG2);
	}
	public static String replaceArg3(String src, ConcurrentHashMap<String, Pattern> map, String pat, int patFlags, JsStringReplaceArg3 func) {
		return replaceObject(src, map, pat, patFlags, func, REPLACE_TYPE_ARG3);
	}
	public static String replaceMatcher(String src, ConcurrentHashMap<String, Pattern> map, String pat, int patFlags, JsStringReplaceMatcher func) {
		return replaceObject(src, map, pat, patFlags, func, REPLACE_TYPE_MATCHER);
	}
	private static String replaceObject(String src, ConcurrentHashMap<String, Pattern> map, String pat, int patFlags, Object replObj, int replType) {
		Pattern pattern = JsPattern_.getOrCompile(map, pat, patFlags);

		// match
		Matcher m = pattern.matcher(src);
		StringBuffer sb = null;
		while (m.find()) {
			// get repl
			String repl = null;
			switch (replType) {
				case REPLACE_TYPE_STR:
					repl = (String)replObj;
					break;
				case REPLACE_TYPE_MATCHER:
					repl = ((JsStringReplaceMatcher)replObj).processMatcher(m);
					break;
				case REPLACE_TYPE_ARG1:
					repl = ((JsStringReplaceArg1)replObj).processMatcher(m.group(0));
					break;
				case REPLACE_TYPE_ARG2:
					repl = ((JsStringReplaceArg2)replObj).processMatcher(m.group(0), m.group(1));
					break;
				case REPLACE_TYPE_ARG3:
					repl = ((JsStringReplaceArg3)replObj).processMatcher(m.group(0), m.group(1), m.group(2));
					break;
				default:
					throw ErrUtl.NewUnhandled(replType);
			}

			if (sb == null) {
				sb = new StringBuffer();
			}
			m.appendReplacement(sb, repl);
		}

		// return
		if (sb != null) {
			m.appendTail(sb);
			return sb.toString();
		}
		else {
			return src;
		}
	}
}
