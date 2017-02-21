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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
import gplx.core.envs.*;
public class Console_adp__sys implements Console_adp {
	private String tmp_txt;
	public Console_adp__sys() {
		this.backspace_by_bytes = Op_sys.Cur().Tid_is_lnx();	// bash shows UTF8 by default; backspace in bytes, else multi-byte characters don't show; DATE:2014-03-04
	}
	public boolean Enabled() {return true;}
	public boolean Canceled() {return canceled;} public void Canceled_set(boolean v) {canceled = v;} private boolean canceled = false;
	public boolean Canceled_chk() {if (canceled) throw Err_.new_op_canceled(); return canceled;}
	public int Chars_per_line_max() {return chars_per_line_max;} public void Chars_per_line_max_(int v) {chars_per_line_max = v;} int chars_per_line_max = 80;
	public boolean Backspace_by_bytes() {return backspace_by_bytes;} public Console_adp__sys Backspace_by_bytes_(boolean v) {backspace_by_bytes = v; return this;} private boolean backspace_by_bytes;
	public void Write_str(String s)										{Clear_tmp(); Write_str_lang(s);}
	public void Write_str_w_nl(String s)								{Clear_tmp(); Write_str_w_nl_lang(s);}
	public void Write_fmt_w_nl(String fmt, Object... args)		{Clear_tmp(); Write_str_w_nl_lang(String_.Format(fmt, args));}
	public char Read_key(String s)										{Write_str(s); return Read_key_lang();}
	public String Read_line(String s)									{Write_str(s); return Read_line_lang();}
	public void Write_tmp(String s) {
		Clear_tmp();
		if (String_.Has(s, "\r")) s = String_.Replace(s, "\r", " ");
		if (String_.Has(s, "\n")) s = String_.Replace(s, "\n", " ");
		if (String_.Len(s) >= chars_per_line_max) s = String_.Mid(s, 0, chars_per_line_max - String_.Len("...") - 1) + "...";	// NOTE: >= and -1 needed b/c line needs to be 1 less than max; ex: default cmd is 80 width, but writing 80 chars will automatically create lineBreak
		tmp_txt = s;
		Write_str_lang(s);
	}
	private void Clear_tmp() {
		if (tmp_txt == null) return;
		if (Env_.Mode_debug()) {Write_str_lang(String_.CrLf); return;}
		int count = backspace_by_bytes ? Bry_.new_u8(tmp_txt).length : String_.Len(tmp_txt);
		String moveBack = String_.Repeat("\b", count);
		this.Write_str_lang(moveBack);						// move cursor back to beginning of line
		this.Write_str_lang(String_.Repeat(" ", count));	// overwrite tmp_txt with space
		this.Write_str_lang(moveBack);						// move cursor back to beginning of line (so next Write will start at beginning)
		tmp_txt = null;
	}
	private void Write_str_lang(String s)		{System.out.print(s);}			
	private void Write_str_w_nl_lang(String s)	{System.out.println(s);}		
	private String Read_line_lang()				{return System.console() == null ? "" : System.console().readLine();}	
	private char Read_key_lang() {
		String text = Read_line_lang();
		return String_.Len(text) == 0 ? '\0' : String_.CharAt(text, 0);
	}
	public void Write_str_w_nl_utf8(String s) {
				java.io.PrintStream ps;
		try {ps = new java.io.PrintStream(System.out, true, "UTF-8");}
		catch (java.io.UnsupportedEncodingException e) {throw Err_.new_wo_type("unsupported exception");}
	    ps.println(s);
	    	}
	public static final    Console_adp__sys Instance = new Console_adp__sys();
}
