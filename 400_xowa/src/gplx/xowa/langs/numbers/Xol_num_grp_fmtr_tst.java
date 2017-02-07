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
package gplx.xowa.langs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Xol_num_grp_fmtr_tst {
	@Before public void init() {fxt.Reset();} private Xol_num_grp_fmtr_fxt fxt = new Xol_num_grp_fmtr_fxt();
	@Test   public void Num() {
		fxt.Test_fmt_regx(""					, "");
		fxt.Test_fmt_regx("1"					, "1");
		fxt.Test_fmt_regx("12"					, "12");
		fxt.Test_fmt_regx("123"					, "123");
		fxt.Test_fmt_regx("1234"				, "1,234");
		fxt.Test_fmt_regx("12345"				, "12,345");
		fxt.Test_fmt_regx("123456"				, "123,456");
		fxt.Test_fmt_regx("1234567"				, "1,234,567");
		fxt.Test_fmt_regx("1234567890"			, "1,234,567,890");
	}
	@Test   public void Dec() {
		fxt.Test_fmt_regx("1.9876"				, "1.9876");
		fxt.Test_fmt_regx("1234.9876"			, "1,234.9876");
	}
	@Test   public void Neg() {
		fxt.Test_fmt_regx("-1234.5678"			, "-1,234.5678");
	}
	@Test   public void Char() {
		fxt.Test_fmt_regx("1,234"				, "1,234");
		fxt.Test_fmt_regx("1a2345"				, "1a2,345");
		fxt.Test_fmt_regx("1234a5678b2345c.3456d7890e3210.f5432", "1,234a5,678b2,345c.3456d7,890e3,210.f5,432");
	}
}
class Xol_num_grp_fmtr_fxt {
	private Xol_num_grp_fmtr grouper = new Xol_num_grp_fmtr();
	private Bry_bfr bfr = Bry_bfr_.New();
	public void Reset() {}
	public void Test_fmt_regx(String raw, String expd) {
		byte[] actl = grouper.Fmt_regx(bfr, Bry_.new_a7(raw));
		Tfds.Eq(expd, String_.new_u8(actl));
	}
}
