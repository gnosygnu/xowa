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
public class ConsoleAdp implements GfoInvkAble, ConsoleDlg {
	public boolean Enabled() {return true;}
	public boolean Canceled() {return canceled;} public void Canceled_set(boolean v) {canceled = v;} private boolean canceled = false;
	public boolean CanceledChk() {if (canceled) throw Err_.op_canceled_usr_(); return canceled;}
	public int CharsPerLineMax() {return chars_per_line_max;} public void CharsPerLineMax_set(int v) {chars_per_line_max = v;} int chars_per_line_max = 80;
	public boolean Backspace_by_bytes() {return backspace_by_bytes;} public ConsoleAdp Backspace_by_bytes_(boolean v) {backspace_by_bytes = v; return this;} private boolean backspace_by_bytes;
	public void WriteText(String s)										{ClearTempText(); WriteText_lang(s);}
	public void WriteLine(String s)										{ClearTempText(); WriteLine_lang(s);}
	public void WriteLineOnly()											{ClearTempText(); WriteLine("");}
	public void WriteLineFormat(String format, Object... args)	{ClearTempText(); WriteLine_lang(String_.Format(format, args));}
	public char ReadKey(String m)										{WriteText(m); return ReadKey_lang();}
	public String ReadLine(String m)									{WriteText(m); return ReadLine_lang();}
	public void WriteTempText(String s) {
		ClearTempText();
		if (String_.Has(s, "\r")) s = String_.Replace(s, "\r", " ");
		if (String_.Has(s, "\n")) s = String_.Replace(s, "\n", " ");
		if (String_.Len(s) >= chars_per_line_max) s = String_.Mid(s, 0, chars_per_line_max - String_.Len("...") - 1) + "...";	// NOTE: >= and -1 needed b/c line needs to be 1 less than max; ex: default cmd is 80 width, but writing 80 chars will automatically create lineBreak
		tempText = s;
		WriteText_lang(s);
	}	String tempText;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
	void ClearTempText() {
		if (tempText == null) return;
		if (Env_.Mode_debug()) {WriteText_lang(String_.CrLf); return;}
		int count = backspace_by_bytes ? Bry_.new_u8(tempText).length : String_.Len(tempText);
		String moveBack = String_.Repeat("\b", count);
		this.WriteText_lang(moveBack);						// move cursor back to beginning of line
		this.WriteText_lang(String_.Repeat(" ", count));	// overwrite tempText with space
		this.WriteText_lang(moveBack);						// move cursor back to beginning of line (so next Write will start at beginning)
		tempText = null;
	}
	void WriteText_lang(String s)	{System.out.print(s);}			
	void WriteLine_lang(String s)	{System.out.println(s);}		
	String ReadLine_lang()			{return System.console() == null ? "" : System.console().readLine();}	
	char ReadKey_lang() {
		String text = ReadLine_lang();
		return String_.Len(text) == 0 ? '\0' : String_.CharAt(text, 0);
	}
	public void WriteLine_utf8(String s) {
				java.io.PrintStream ps;
		try {ps = new java.io.PrintStream(System.out, true, "UTF-8");}
		catch (java.io.UnsupportedEncodingException e) {throw Err_.new_("unsupported exception");}
	    ps.println(s);
			}
        public static final ConsoleAdp _ = new ConsoleAdp();
	public ConsoleAdp() {
		if (Op_sys.Cur().Tid_is_lnx())
			backspace_by_bytes = true;	// bash shows UTF8 by default; backspace in bytes, else multi-byte characters don't show; DATE:2014-03-04
	}
}
