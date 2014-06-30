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
public class Xol_msg_itm_tst {		
	@Before public void init() {fxt.Clear();} private Xol_msg_itm_fxt fxt = new Xol_msg_itm_fxt();
	@Test   public void New_plain() 			{fxt.Test_new("a"					, Bool_.N, Bool_.N);}
	@Test   public void New_fmt() 				{fxt.Test_new("a~{0}b"				, Bool_.Y, Bool_.N);}
	@Test   public void New_tmpl() 				{fxt.Test_new("a{{b}}c"				, Bool_.N, Bool_.Y);}
	@Test   public void New_fmt_tmpl() 			{fxt.Test_new("a{{b}}c~{0}d"		, Bool_.Y, Bool_.Y);}
	@Test   public void New_space() 			{fxt.Test_val("a&#32;b"				, "a b");}
}
class Xol_msg_itm_fxt {
	public void Clear() {}
	public void Test_new(String val, boolean has_fmt_arg, boolean has_tmpl_txt) {
		Xol_msg_itm itm = Xol_msg_itm_.new_(0, "test", val);
		Tfds.Eq(has_fmt_arg, itm.Has_fmt_arg(), "has_fmt_arg");
		Tfds.Eq(has_tmpl_txt, itm.Has_tmpl_txt(), "has_tmpl_txt");
	}
	public void Test_val(String val, String expd) {
		Xol_msg_itm itm = Xol_msg_itm_.new_(0, "test", val);
		Tfds.Eq(expd, String_.new_utf8_(itm.Val()), "has_fmt_arg");
	}
}
