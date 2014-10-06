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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import org.junit.*;
import gplx.json.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*;
public class Wdata_date_tst {
	@Before public void init() {} private Wdata_date_fxt fxt = new Wdata_date_fxt();
	@Test   public void Parse() {
		fxt.Test_parse("+00000002001-02-03T04:05:06Z",          2001, 2, 3, 4, 5, 6);
		fxt.Test_parse("-98765432109-02-03T04:05:06Z", -98765432109L, 2, 3, 4, 5, 6);
	}
	@Test   public void Julian() {
		fxt.Test_julian(Int_.Ary(1600, 1, 2), Int_.Ary(1600, 1, 18));
	}
	@Test   public void Xto_str() {
		String date = "+00000002001-02-03T04:05:06Z;";
		fxt.Test_xto_str(date, Wdata_date.Fmt_ym		, "Feb 2001");
		fxt.Test_xto_str(date, Wdata_date.Fmt_ymd		, "3 Feb 2001");
		fxt.Test_xto_str(date, Wdata_date.Fmt_ymdh		, "4:00 3 Feb 2001");
		fxt.Test_xto_str(date, Wdata_date.Fmt_ymdhn		, "4:05 3 Feb 2001");
		fxt.Test_xto_str(date, Wdata_date.Fmt_ymdhns	, "4:05:06 3 Feb 2001");
		fxt.Test_xto_str(date, 0						, "2001");
	}
}
class Wdata_date_fxt {
	private Bry_bfr tmp_bfr = Bry_bfr.new_(16);
	private static final byte[][] months_ary = Bry_.Ary("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
	private static final byte[] dt_spr = Byte_ascii.Space_bry, time_spr = Byte_ascii.Colon_bry;
	public void Test_parse(String raw, long expd_y, int expd_m, int expd_d, int expd_h, int expd_n, int expd_s) {
		Wdata_date actl_date = Wdata_date.parse(Bry_.new_ascii_(raw));
		Tfds.Eq(expd_y, actl_date.Year());
		Tfds.Eq(expd_m, actl_date.Month());
		Tfds.Eq(expd_d, actl_date.Day());
		Tfds.Eq(expd_h, actl_date.Hour());
		Tfds.Eq(expd_n, actl_date.Minute());
		Tfds.Eq(expd_s, actl_date.Second());
	}
	public void Test_julian(int[] orig_ary, int[] expd) {
		Wdata_date orig = new Wdata_date(orig_ary[0], orig_ary[1], orig_ary[2], 0, 0, 0);
		Wdata_date actl = Wdata_date.Xto_julian(orig);
		Tfds.Eq(expd[0], (int)actl.Year(), "y");
		Tfds.Eq(expd[1], actl.Month(), "m");
		Tfds.Eq(expd[2], actl.Day(), "d");
	}
	public void Test_xto_str(String raw, int precision, String expd) {
		Wdata_date date = Wdata_date.parse(Bry_.new_ascii_(raw));
		Wdata_date.Xto_str(tmp_bfr, date, precision, months_ary, 0, dt_spr, time_spr);
		Tfds.Eq(expd, tmp_bfr.XtoStrAndClear());
	}
}
