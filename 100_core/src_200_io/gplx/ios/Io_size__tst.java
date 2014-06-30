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
package gplx.ios; import gplx.*;
import org.junit.*;
public class Io_size__tst {
	@Test  public void XtoLong() {
		tst_XtoLong("1", 1);
		tst_XtoLong("1 KB", 1024);
		tst_XtoLong("1 MB", 1024 * 1024);
		tst_XtoLong("1 GB", 1024 * 1024 * 1024);
		tst_XtoLong("12 kb", 12 * 1024);
		tst_XtoLong("1.5 kb", 1024 + 512);	// 1536
		tst_XtoLong("1.5 mb", (long)(1024 * 1024 * 1.5));
		tst_XtoLong("-1", -1);	// NOTE: negative bytes allowed

		tst_XtoLongFail("1 kilobite");
		tst_XtoLongFail("1 BB");
		tst_XtoLongFail("1.1");
		tst_XtoLongFail("1.51 kb");
	}
	void tst_XtoLong(String raw, long expd) {Tfds.Eq(expd, Io_size_.parse_or_(raw, Long_.MinValue));}
	void tst_XtoLongFail(String raw) {
		long val = Io_size_.parse_or_(raw, Long_.MinValue);
		if (val != Long_.MinValue) Tfds.Fail("expd parse failure; raw=" + raw);
	}
	@Test  public void XtoStr() {
		tst_XtoStr(1, "1.000  B");
		tst_XtoStr(1024, "1.000 KB");
		tst_XtoStr(1536, "1.500 KB");
		tst_XtoStr(1024 * 1024, "1.000 MB");
		tst_XtoStr(1016, "1,016.000  B");	// NOTE: 1016 is not 1.016 KB
	}	void tst_XtoStr(long val, String expd) {Tfds.Eq(expd, Io_size_.Xto_str(val));}
	@Test  public void EqualsTest() {
		tst_Equals("1", "1");
		tst_Equals("1 kb", "1 kb");
		tst_Equals("1024", "1 kb");
		tst_Equals("1048576", "1 mb");
		tst_Equals("1024 kb", "1 mb");
		tst_Equals("1.5 kb", "1536 b");
	}	void tst_Equals(String lhs, String rhs) {Tfds.Eq(Io_size_.parse_or_(lhs, Long_.MinValue), Io_size_.parse_or_(rhs, Long_.MinValue));}
}
