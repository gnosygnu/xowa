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
package gplx.xowa.apps.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.nss.*;
import gplx.xowa.wms.sites.*;
public class Xoa_meta_mgr {
	private final Xoa_app app;
	private final Hash_adp_bry ns__hash = Hash_adp_bry.cs();
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
		}
		return rv;
	}
	public void Init_by_wiki(Xow_wiki wiki) {
		Core_db__assert();
		core_db.Load_extensiontag(wiki.Domain_itm(), wiki.Mw_parser_mgr().Xnde_tag_regy());
	}
	private void Core_db__assert() {
		if (core_db == null) core_db = new Site_core_db(app.Fsys_mgr().Cfg_site_meta_fil());
	}
}
