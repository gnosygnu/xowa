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
public class ConsoleDlg_ {
	public static final ConsoleDlg Null = new ConsoleDlg_null();
	public static ConsoleDlg_dev Dev() {return new ConsoleDlg_dev();}
}
class ConsoleDlg_null implements ConsoleDlg {
	public boolean Enabled() {return false;}
	public boolean CanceledChk() {return false;}
	public int CharsPerLineMax() {return 80;} public void CharsPerLineMax_set(int v) {}
	public void WriteText(String s) {}
	public void WriteLineFormat(String s, Object... args) {} 
	public void WriteTempText(String s) {}
	public char ReadKey(String msg) {return '\0';}
	public String ReadLine(String msg) {return "";}
}
