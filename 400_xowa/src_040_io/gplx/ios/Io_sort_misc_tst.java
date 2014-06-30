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
public class Io_sort_misc_tst {
	@Before public void init() {
	}
	@Test  public void Io_url_gen_dir() {
		tst_Io_url_gen_dir("mem/dir/", "{0}.xdat", 4, 3, "0000.xdat", "0001.xdat", "0002.xdat");
	}
	private void tst_Io_url_gen_dir(String dir_str, String fmt, int digits, int calls, String... expd) {
		Io_url dir = Io_url_.mem_dir_(dir_str);
		ListAdp actl_list = ListAdp_.new_();
		Io_url_gen wkr = Io_url_gen_.dir_(dir, fmt, digits);
		for (int i = 0; i < calls; i++)
			actl_list.Add(wkr.Nxt_url().Raw());
		String[] actl = actl_list.XtoStrAry();
		for (int i = 0; i < expd.length; i++)
			expd[i] = dir_str + expd[i];
		Tfds.Eq_ary_str(expd, actl);
	}
	@Test  public void Io_line_rdr_comparer_all() {
		tst_Io_line_rdr_fld_comparer(-1, "a", "b");
		tst_Io_line_rdr_fld_comparer( 0, "a", "a");
		tst_Io_line_rdr_fld_comparer( 1, "b", "a");
		tst_Io_line_rdr_fld_comparer(-1, "a", "ab");
		tst_Io_line_rdr_fld_comparer( 1, "ab", "a");
	}
	private void tst_Io_line_rdr_fld_comparer(int expd, String lhs_str, String rhs_str) {
		byte[] lhs = Bry_.new_utf8_(lhs_str), rhs = Bry_.new_utf8_(rhs_str);
		Tfds.Eq(expd, Bry_.Compare(lhs, 0, lhs.length, rhs, 0, rhs.length));
	}
	Io_line_rdr new_Io_line_rdr(String url_str, String text) {
		Io_url url = Io_url_.mem_fil_(url_str);
		Io_mgr._.SaveFilStr(url, text);
		Io_line_rdr rv = new Io_line_rdr(Gfo_usr_dlg_base.test_(), url);
		rv.Read_next();
		return rv;
	}
	@Test  public void ExternalSort() {
		// fxt("c", "a", "b")
	}
}
