/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Io_sort_misc_tst {
	@Before public void init() {
	}
	@Test  public void Io_url_gen_dir() {
		tst_Io_url_gen_dir("mem/dir/", "{0}.xdat", 4, 3, "0000.xdat", "0001.xdat", "0002.xdat");
	}
	private void tst_Io_url_gen_dir(String dir_str, String fmt, int digits, int calls, String... expd) {
		Io_url dir = Io_url_.mem_dir_(dir_str);
		List_adp actl_list = List_adp_.New();
		Io_url_gen wkr = Io_url_gen_.dir_(dir, fmt, digits);
		for (int i = 0; i < calls; i++)
			actl_list.Add(wkr.Nxt_url().Raw());
		String[] actl = actl_list.To_str_ary();
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
		byte[] lhs = Bry_.new_u8(lhs_str), rhs = Bry_.new_u8(rhs_str);
		Tfds.Eq(expd, Bry_.Compare(lhs, 0, lhs.length, rhs, 0, rhs.length));
	}
	Io_line_rdr new_Io_line_rdr(String url_str, String text) {
		Io_url url = Io_url_.mem_fil_(url_str);
		Io_mgr.Instance.SaveFilStr(url, text);
		Io_line_rdr rv = new Io_line_rdr(Gfo_usr_dlg_.Test(), url);
		rv.Read_next();
		return rv;
	}
	@Test  public void ExternalSort() {
		// fxt("c", "a", "b")
	}
}
