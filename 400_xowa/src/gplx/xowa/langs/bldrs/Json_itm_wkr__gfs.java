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
package gplx.xowa.langs.bldrs;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.apps.gfs.*; import gplx.xowa.langs.parsers.*;
class Json_itm_wkr__gfs extends Json_itm_wkr__base {
	private Xoa_gfs_bldr gfs_bldr = new Xoa_gfs_bldr();
	private Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
	private BryWtr bfr;
	public byte[] Xto_bry() {return gfs_bldr.Xto_bry();}
	@Override public void Exec_bgn() {
		bfr = gfs_bldr.Bfr();
		gfs_bldr.Add_proc_init_many("this", "messages", "load_text").Add_paren_bgn().Add_nl();
		gfs_bldr.Add_quote_xtn_bgn();
	}
	@Override public void Exec_end() {
		gfs_bldr.Add_quote_xtn_end().Add_paren_end().Add_term_nl();
	}
	@Override public void Read_kv_sub(byte[] key, byte[] val) {
		csv_parser.Save(bfr, key);					// key
		bfr.AddBytePipe();						// |
		csv_parser.Save(bfr, val);					// val
		bfr.AddByteNl();							// \n
	}
}
