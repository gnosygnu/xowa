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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
public class Console_adp__mem implements Console_adp {
	private final    List_adp written = List_adp_.New();
	private final    Hash_adp ignored = Hash_adp_.New();
	public boolean Enabled() {return true;}
	public boolean Canceled_chk() {return false;}
	public int Chars_per_line_max() {return 80;} public void Chars_per_line_max_(int v) {}
	public Console_adp__mem Ignore_add(String s) {ignored.Add_as_key_and_val(s); return this;}
	public void Write_str(String s) {WriteString(s);}
	public void Write_fmt_w_nl(String s, Object... args) {WriteString(String_.Format(s, args) + String_.CrLf);}
	public void Write_tmp(String s) {WriteString(s);}
	public String Read_line(String msg) {return "";}
	public char Read_key(String msg) {return '\0';}
	public Console_adp__mem CancelWhenTextWritten(String val) {
		cancelVal = val;
		return this;
	}
	void WriteString(String s) {
		if (ignored.Has(s)) return;
		written.Add(s);
		if (cancelVal != null && String_.Has(s, cancelVal)) throw Err_.new_wo_type("canceled", "cancel_val", s);
	}
	String cancelVal;

	public List_adp Written() {return written;}
	public void tst_WrittenStr(String... expd) {
		String[] actl = new String[written.Count()];
		int actlLength = Array_.Len(actl);
		for (int i = 0; i < actlLength; i++)
			actl[i] = written.Get_at(i).toString();
		Tfds.Eq_ary(actl, expd);
	}
}
