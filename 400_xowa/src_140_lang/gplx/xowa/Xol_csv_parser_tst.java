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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xol_csv_parser_tst {
	Xol_csv_parser_fxt fxt = new Xol_csv_parser_fxt();
	@Before public void init()	{fxt.Clear();}
	@Test  public void Save()			{fxt.Tst_save("a\r\n\t|d", "a\\r\\n\\t\\u007Cd");}
	@Test  public void Load()			{fxt.Tst_load("a\r\n\t|d", "a\\r\\n\\t\\u007Cd");}
	@Test  public void Save_backslash()	{fxt.Tst_save("a\\\\n", "a\\\\\\\\n");}
	@Test  public void Load_backslash()	{fxt.Tst_load("a\\\\n", "a\\\\\\\\n");}
	@Test  public void Utf()			{fxt.Tst_load(" ", "\\u00c2\\u00a0");}	// NOTE: 1st String is nbsp;
}
class Xol_csv_parser_fxt {
	Xol_csv_parser parser = Xol_csv_parser._; Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Clear() {}
	public void Tst_save(String raw, String expd) {		
		parser.Save(tmp_bfr, Bry_.new_utf8_(raw));
		Tfds.Eq(expd, tmp_bfr.Xto_str_and_clear());
	}
	public void Tst_load(String expd, String raw_str) {
		byte[] raw = Bry_.new_utf8_(raw_str);
		parser.Load(tmp_bfr, raw, 0, raw.length);
		Tfds.Eq(expd, tmp_bfr.Xto_str_and_clear());
	}
}
