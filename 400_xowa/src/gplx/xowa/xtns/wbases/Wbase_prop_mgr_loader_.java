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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Wbase_prop_mgr_loader_ {
	public static Wbase_prop_mgr_loader New_mock(Keyval... pairs) {
		return new Wbase_prop_mgr_loader__mock(pairs);
	}
	public static Wbase_prop_mgr_loader New_db(Wdata_wiki_mgr wbase_mgr) {
		return new Wbase_prop_mgr_loader__db(wbase_mgr);
	}
}
class Wbase_prop_mgr_loader__mock implements Wbase_prop_mgr_loader {
	private final    Keyval[] pairs;
	public Wbase_prop_mgr_loader__mock(Keyval[] pairs) {
		this.pairs = pairs;
	}
	public Ordered_hash Load() {
		Ordered_hash rv = Ordered_hash_.New();
		for (Keyval kv : pairs) 
			rv.Add(kv.Key(), kv.Val_to_str_or_empty());
		return rv;
	}
}
class Wbase_prop_mgr_loader__db implements Wbase_prop_mgr_loader {
	private final    Wdata_wiki_mgr wbase_mgr;
	public Wbase_prop_mgr_loader__db(Wdata_wiki_mgr wbase_mgr) {
		this.wbase_mgr = wbase_mgr;
	}
	public Ordered_hash Load() {
		gplx.xowa.wikis.data.Xow_db_file wbase_db = wbase_mgr.Wdata_wiki().Data__core_mgr().Db__wbase();
		if (!wbase_db.Conn().Meta_tbl_exists(wbase_db.Tbl__wbase_prop().Tbl_name())) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wbase:prop tbl missing");
			return null;
		}
		return wbase_db.Tbl__wbase_prop().Select_all();
	}
}
