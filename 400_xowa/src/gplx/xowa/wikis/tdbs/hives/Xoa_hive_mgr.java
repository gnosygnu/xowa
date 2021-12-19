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
package gplx.xowa.wikis.tdbs.hives;
import gplx.libs.files.Io_mgr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.files.Io_url;
import gplx.types.basics.wrappers.IntRef;
import gplx.xowa.*;
import gplx.xowa.wikis.tdbs.*;
import gplx.xowa.wikis.tdbs.xdats.*;
public class Xoa_hive_mgr {
	public Xoa_hive_mgr(Xoae_app app) {this.app = app;} private Xoae_app app;
	public Xob_xdat_itm Itm() {return xdat_itm;}
	public int Find_fil(Io_url hive_root, byte[] ttl) {
		Io_url hive_url = hive_root.GenSubFil(Xotdb_dir_info_.Name_reg_fil);
		if (!hive_url.Eq(regy_mgr.Fil()))
			regy_mgr.Init(hive_url);
		return regy_mgr.Files_find(ttl);
	}	private Xowd_regy_mgr regy_mgr = new Xowd_regy_mgr(); IntRef bry_len = IntRef.NewZero(); Xob_xdat_file xdat_rdr = new Xob_xdat_file(); Xob_xdat_itm xdat_itm = new Xob_xdat_itm();
	public Xowd_regy_mgr Regy_mgr() {return regy_mgr;}
	public Xob_xdat_file Get_rdr(Io_url hive_root, byte[] fil_ext_bry, int fil_idx) {
		BryWtr tmp_bfr = app.Utl__bfr_mkr().GetM001();
		byte[] tmp_bry = tmp_bfr.Bry(); bry_len.ValSetZero();
		Io_url file = Xotdb_fsys_mgr.Url_fil(hive_root, fil_idx, fil_ext_bry);
		tmp_bry = Io_mgr.Instance.LoadFilBry_reuse(file, tmp_bry, bry_len);
		xdat_rdr.Clear().Parse(tmp_bry, bry_len.Val(), file);
		tmp_bfr.ClearAndRls();
		return xdat_rdr;
	}
}
