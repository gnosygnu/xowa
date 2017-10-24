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
package gplx.xowa.apps.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.bldrs.wms.sites.*;
public class Xoa_meta_mgr {
	private final    Xoa_app app;
	private final    Hash_adp_bry ns__hash = Hash_adp_bry.cs();
	private Site_core_db core_db;
	public Xoa_meta_mgr(Xoa_app app) {
		this.app = app;
	}
	public void Ns__add(byte[] wiki_domain, Xow_ns_mgr ns_mgr) {ns__hash.Add(wiki_domain, ns_mgr);}	// TEST:public
	public Xow_ns_mgr Ns__get_or_load(byte[] wiki_domain) {
		Xow_ns_mgr rv = (Xow_ns_mgr)ns__hash.Get_by_bry(wiki_domain);
		if (rv == null) {
			Core_db__assert();
			rv = core_db.Load_namespace(wiki_domain);
			Ns__add(wiki_domain, rv);
			rv.Init();	// must init to fill Ords
		}
		return rv;
	}
	private void Core_db__assert() {
		if (core_db == null) core_db = new Site_core_db(app.Fsys_mgr().Cfg_site_meta_fil());
	}
}
