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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Io_size__tst {
	private Io_size__fxt fxt = new Io_size__fxt();
	@Test    public void XtoLong() {
		fxt.Test_XtoLong("1", 1);
		fxt.Test_XtoLong("1 KB", 1024);
		fxt.Test_XtoLong("1 MB", 1024 * 1024);
		fxt.Test_XtoLong("1 GB", 1024 * 1024 * 1024);
		fxt.Test_XtoLong("12 kb", 12 * 1024);
		fxt.Test_XtoLong("1.5 kb", 1024 + 512);	// 1536
		fxt.Test_XtoLong("1.5 mb", (long)(1024 * 1024 * 1.5));
		fxt.Test_XtoLong("-1", -1);	// NOTE: negative bytes allowed

		fxt.Test_XtoLongFail("1 kilobite");
		fxt.Test_XtoLongFail("1 BB");
		// fxt.Test_XtoLongFail("1.1");	// DELETED:do not check for fractional bytes; EX: 10.7 GB DATE:2015-01-06
		// fxt.Test_XtoLongFail("1.51 kb");
	}
	@Test    public void To_str() {
		fxt.Test_XtoStr(1, "1.000  B");
		fxt.Test_XtoStr(1024, "1.000 KB");
		fxt.Test_XtoStr(1536, "1.500 KB");
		fxt.Test_XtoStr(1024 * 1024, "1.000 MB");
		fxt.Test_XtoStr(1016, "1,016.000  B");	// NOTE: 1016 is not 1.016 KB
	}
	@Test    public void Xto_str_full() {
		fxt.Test_Xto_str(       500, 1, "#,###", " ", Bool_.Y,             "1 KB");
		fxt.Test_Xto_str(      1000, 1, "#,###", " ", Bool_.Y,             "1 KB");
		fxt.Test_Xto_str(      2000, 1, "#,###", " ", Bool_.Y,             "2 KB");
		fxt.Test_Xto_str(   1234567, 1, "#,###", " ", Bool_.Y,         "1,206 KB");
		fxt.Test_Xto_str(1234567890, 1, "#,###", " ", Bool_.Y,     "1,205,633 KB");
	}
	@Test    public void EqualsTest() {
		fxt.Test_Equals("1", "1");
		fxt.Test_Equals("1 kb", "1 kb");
		fxt.Test_Equals("1024", "1 kb");
		fxt.Test_Equals("1048576", "1 mb");
		fxt.Test_Equals("1024 kb", "1 mb");
		fxt.Test_Equals("1.5 kb", "1536 b");
	}
}
class Io_size__fxt {
	public void Test_XtoLong(String raw, long expd) {Tfds.Eq(expd, Io_size_.parse_or(raw, Long_.Min_value));}
	public void Test_XtoLongFail(String raw) {
		long val = Io_size_.parse_or(raw, Long_.Min_value);
		if (val != Long_.Min_value) Tfds.Fail("expd parse failure; raw=" + raw);
	}
	public void Test_Equals(String lhs, String rhs) {Tfds.Eq(Io_size_.parse_or(lhs, Long_.Min_value), Io_size_.parse_or(rhs, Long_.Min_value));}
	public void Test_XtoStr(long val, String expd) {Tfds.Eq(expd, Io_size_.To_str(val));}
	public void Test_Xto_str(long val, int exp_1024, String val_fmt, String unit_pad, boolean round_0_to_1, String expd) {Tfds.Eq(expd, Io_size_.To_str(val, exp_1024, val_fmt, unit_pad, round_0_to_1));}
}
