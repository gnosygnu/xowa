package gplx.types.basics.utls;
import gplx.types.errs.Err;
public class ErrLni {
	public static Err NewMsg(String msg) {return new Err("GENERAL", msg);}
	public static final String Message(Throwable e) {
		return Error.class.isAssignableFrom(e.getClass())
			? e.toString()    // NOTE: java.lang.Error returns null for "getMessage()"; return "toString()" instead
			: e.getMessage();
	}
	public static final String Trace(Throwable e) {
		StackTraceElement[] ary = e.getStackTrace();
		String rv = "";
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) rv += "\n";
			rv += ary[i].toString();
		}
		return rv;
	}
//	private static String Trace_to_str(boolean is_gplx, boolean called_by_log, int ignore_lines, String trace) {
//		if (trace == null) return "";    // WORKAROUND:.NET: StackTrace is only available when error is thrown; can't do "Console.Write(new Exception().StackTrace);
//		String[] lines = StringUtl.SplitLang(trace, '\n'); int lines_len = lines.length;
//		int line_bgn = 0;
//		if (is_gplx) {    // remove ErrUtl.NewArgs lines from trace for gplx exceptions
//			for (int i = 0; i < lines_len; ++i) {
//				String line = lines[i];
//				if (StringUtl.HasAtBgn(line, "gplx.Err_.new")) continue;    // ignore trace frames with "gplx.Err_.new"; EX: throw Err_.new_unimplemented
//				line_bgn = i + ignore_lines;
//				break;
//			}
//		}
//		// concat lines
//		String rv = "";
//		String line_bgn_dlm = called_by_log ? "\t  " : "\n  ";    // "\n  " indents
//		for (int i = line_bgn; i < lines_len; ++i)
//			rv += line_bgn_dlm + lines[i];
//		return rv;
//	}
//
}
