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
package gplx.xowa.wikis.tdbs.xdats; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import org.junit.*;
public class Xob_xdat_file_wtr_tst {
	@Test  public void Write() {
		Io_mgr.Instance.InitEngine_mem();
		Io_url dir = Io_url_.mem_dir_("mem/dir");
		Xob_xdat_file_wtr wtr = Xob_xdat_file_wtr.new_file_(1000, dir);
		tst_Write(wtr, "<1", "<1");
		tst_Write(wtr, ">a", "<1>a");
		tst_Write(wtr, ">b", "<1>a>b");
		tst_Add_idx(wtr, 7);
		wtr.Bfr().Add(Bry_.new_a7("<2>b>cc"));
		tst_Add_idx(wtr, 15);
		wtr.Bfr().Add(Bry_.new_a7("<3>c>dd"));
		tst_Add_idx(wtr, 23);
		tst_Flush(wtr, String_.Concat
			( "!!!!(|!!!!)|!!!!)|\n"
			, "<1>a>b\n"
			, "<2>b>cc\n"
			, "<3>c>dd\n"
			));
	}
	private void tst_Write(Xob_xdat_file_wtr wtr, String val, String expd) {
		wtr.Bfr().Add(Bry_.new_u8(val));
		Tfds.Eq(expd, String_.new_u8(wtr.Bfr().Bfr(), 0, wtr.Bfr().Len()));
	}
	private void tst_Add_idx(Xob_xdat_file_wtr wtr, int expd) {
		wtr.Add_idx(Byte_ascii.Nl);
		Tfds.Eq(expd, wtr.Idx()[wtr.Idx_pos() - 1]);
	}
	private void tst_Flush(Xob_xdat_file_wtr wtr, String expd) {
		Io_url url = wtr.Fil_url();
		wtr.Flush(Gfo_usr_dlg_.Test());
		String actl = Io_mgr.Instance.LoadFilStr(url);
		Tfds.Eq(expd, actl);
	}
}
