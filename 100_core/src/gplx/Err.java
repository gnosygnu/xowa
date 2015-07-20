/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
public class Err extends RuntimeException {
	private final boolean is_gplx;
	private final String trace;
	private Err_msg[] msgs_ary = new Err_msg[8]; private int msgs_len = 8, msgs_idx = 0;
	public Err(boolean is_gplx, String trace, String type, String msg, Object... args) {
		this.is_gplx = is_gplx;
		this.trace = is_gplx ? Err_.Trace_lang(this) : trace;	// NOTE: Err_ factory methods pass in null stack trace for gplx excs; call Stack_trace here, note that trace will not show constructor
		Msgs_add(type, msg, args);
	}
	public int Trace_ignore() {return trace_ignore;} public Err Trace_ignore_add_1_() {++trace_ignore; return this;} private int trace_ignore = 0;
	public Err Args_add(Object... args) {msgs_ary[msgs_idx - 1].Args_add(args); return this;}	// i - 1 to get current
	@gplx.Internal protected boolean Type_match(String type) {
		for (int i = 0; i < msgs_len; ++i) {
			if (String_.Eq(type, msgs_ary[i].Type())) return true;
		}
		return false;
	}
	@gplx.Internal protected void Msgs_add(String type, String msg, Object[] args) {
		if (msgs_idx == msgs_len) {
			int new_len = msgs_len * 2;
			Err_msg[] new_ary = new Err_msg[new_len];
			Array_.CopyTo(msgs_ary, new_ary, 0);
			this.msgs_ary = new_ary;
			this.msgs_len = new_len;
		}
		msgs_ary[msgs_idx] = new Err_msg(type, msg, args);
		++msgs_idx;
	}
	public String To_str__full()	{return To_str(Bool_.N);}
	public String To_str__log()		{return To_str(Bool_.Y);}
	private String To_str(boolean called_by_log) {
		String nl_str = called_by_log ? "\t" : "\n";
		String rv = ""; //nl_str + "----------------------------------------------------------------------" + nl_str;
		for (int i = 0; i < msgs_idx; ++i) {
			rv += "[err " + Int_.Xto_str(i) + "] " + msgs_ary[i].To_str() + nl_str;
		}
		rv += "[trace]:" + Trace_to_str(is_gplx, called_by_log, trace_ignore, trace == null ? Err_.Trace_lang(this) : trace);
		return rv;
	}
	@Override public String getMessage() {return To_str__full();}
	public static String Trace_to_str(boolean is_gplx, boolean called_by_log, int ignore_lines, String trace) {
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
