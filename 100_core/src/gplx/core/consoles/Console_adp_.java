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
public class Console_adp_ {
	public static final Console_adp Noop = new Console_adp__noop();
	public static Console_adp__mem Dev() {return new Console_adp__mem();}
}
class Console_adp__noop implements Console_adp {
	public boolean Enabled() {return false;}
	public boolean Canceled_chk() {return false;}
	public int Chars_per_line_max() {return 80;} public void Chars_per_line_max_(int v) {}
	public void Write_str(String s) {}
	public void Write_fmt_w_nl(String s, Object... args) {} 
	public void Write_tmp(String s) {}
	public char Read_key(String msg) {return '\0';}
	public String Read_line(String msg) {return "";}
}
