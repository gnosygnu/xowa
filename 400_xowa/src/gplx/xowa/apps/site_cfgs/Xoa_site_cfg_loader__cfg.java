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
package gplx.xowa.apps.site_cfgs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDateNow;
import gplx.xowa.*;
import gplx.dbs.cfgs.*;
class Xoa_site_cfg_loader__db implements Xoa_site_cfg_loader {
	private Db_cfg_tbl cfg_tbl;
	public int Tid() {return Xoa_site_cfg_loader_.Tid__cfg;}
	public void Load_csv__bgn(Xoa_site_cfg_mgr mgr, Xow_wiki wiki) {
		this.cfg_tbl = wiki.Data__core_mgr().Tbl__cfg();
		cfg_tbl.Select_as_hash_bry(mgr.Data_hash(), Grp__xowa_wm_api);
	}
	public byte[] Load_csv(Xoa_site_cfg_mgr mgr, Xow_wiki wiki, Xoa_site_cfg_itm__base itm) {
		byte[] rv = (byte[])mgr.Data_hash().Get_by_bry(itm.Key_bry()); if (rv == null) return null;
		int meta_end = BryFind.FindFwd(rv, AsciiByte.Nl);
		if (meta_end == BryFind.NotFound) {// fallback will only log one line; ignore; EX: //#xowa|fallback
			return null;
		}
		return BryLni.Mid(rv, meta_end + 1);
	}
	public void Save_bry(int loader_tid, String db_key, byte[] val) {
		byte[] meta = BryUtl.NewA7(Bld_meta(loader_tid));
		byte[] data = BryUtl.IsNullOrEmpty(val) ? meta : BryUtl.Add(meta, AsciiByte.NlBry, val);
		cfg_tbl.Upsert_bry(Grp__xowa_wm_api, db_key, data);
	}
	public static String Bld_meta(int loader_tid) {
		return StringUtl.Format("//#xowa|{0}|{1}|{2}", Xoa_app_.Version, Xoa_site_cfg_loader_.Get_key(loader_tid), GfoDateNow.Get().ToUtc().ToStrFmt_yyyyMMdd_HHmmss());
	}
	public static final String Grp__xowa_wm_api = "xowa.site_cfg";
}
