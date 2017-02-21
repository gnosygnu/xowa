/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx;
import gplx.core.errs.*;
public class Err extends RuntimeException {
	private final    boolean is_gplx;
	private final    String trace;
	private Err_msg[] msgs_ary = new Err_msg[8]; private int msgs_len = 8, msgs_idx = 0;
	public Err(boolean is_gplx, String trace, String type, String msg, Object... args) {
		this.is_gplx = is_gplx;
		this.trace = is_gplx ? Err_.Trace_lang(this) : trace;	// NOTE: Err_ factory methods pass in null stack trace for gplx excs; call Stack_trace here, note that trace will not show constructor
		Msgs_add(type, msg, args);
	}
	public boolean Logged() {return logged;} public Err Logged_y_() {logged = true; return this;} private boolean logged;
	public int Trace_ignore() {return trace_ignore;} public Err Trace_ignore_add_1_() {++trace_ignore; return this;} private int trace_ignore = 0;
	public Err Args_add(Object... args) {msgs_ary[msgs_idx - 1].Args_add(args); return this;}	// i - 1 to get current
	@gplx.Internal protected void Msgs_add(String type, String msg, Object[] args) {
		if (msgs_idx == msgs_len) {
			int new_len = msgs_len * 2;
			Err_msg[] new_ary = new Err_msg[new_len];
			Array_.Copy_to(msgs_ary, new_ary, 0);
			this.msgs_ary = new_ary;
			this.msgs_len = new_len;
		}
		msgs_ary[msgs_idx] = new Err_msg(type, msg, args);
		++msgs_idx;
	}
	public String To_str__full()	{return To_str(Bool_.N, Bool_.Y);}
	public String To_str__log()		{return To_str(Bool_.Y, Bool_.Y);}
	public String To_str__msg_only(){
		return msgs_idx == 0 ? "<<MISSING ERROR MESSAGE>>" : msgs_ary[0].To_str_wo_type();	// take 1st message only
	}
	public String To_str__top_wo_args() {
		return msgs_idx == 0 ? "<<MISSING ERROR MESSAGE>>" : msgs_ary[0].To_str_wo_args();
	}
	private String To_str(boolean called_by_log, boolean include_trace) {
		String nl_str = called_by_log ? "\t" : "\n";
		String rv = "";
		for (int i = 0; i < msgs_idx; ++i) {
			rv += "[err " + Int_.To_str(i) + "] " + String_.Replace(msgs_ary[i].To_str(), "\n", nl_str) + nl_str;
		}
		if (include_trace)
			rv += "[trace]:" + Trace_to_str(is_gplx, called_by_log, trace_ignore, trace == null ? Err_.Trace_lang(this) : trace);
		return rv;
	}
	@Override public String getMessage() {return To_str__msg_only();}
	public static String Trace_to_str(boolean is_gplx, boolean called_by_log, int ignore_lines, String trace) {
		if (trace == null) return "";	// WORKAROUND:.NET: StackTrace is only available when error is thrown; can't do "Console.Write(new Exception().StackTrace);
		String[] lines = String_.Split_lang(trace, '\n'); int lines_len = lines.length; 
		int line_bgn = 0;
		if (is_gplx) {	// remove Err_.new_wo_type lines from trace for gplx exceptions
			for (int i = 0; i < lines_len; ++i) {
				String line = lines[i];
				if (String_.Has_at_bgn(line, "gplx.Err_.new")) continue;	// ignore trace frames with "gplx.Err_.new"; EX: throw Err_.new_unimplemented
				line_bgn = i + ignore_lines;
				break;
			}
		}
		// concat lines
		String rv = ""; 
		String line_bgn_dlm = called_by_log ? "\t  " : "\n  ";	// "\n  " indents
		for (int i = line_bgn; i < lines_len; ++i)
			rv += line_bgn_dlm + lines[i];
		return rv;
	}
}
