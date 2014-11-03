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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.xwikis.*; import gplx.xowa.langs.cases.*; import gplx.xowa.apps.ttls.*; import gplx.xowa.html.hzips.*;
import gplx.xowa.dbs.tbls.*; import gplx.dbs.*;
public class Xowv_wiki implements Xoa_ttl_parser {
	private Xoav_app app;
	public Xowv_wiki(Xoav_app app, String domain_str, Io_url wiki_root_dir) {
		this.app = app;
		this.domain_str = domain_str; this.domain_bry = Bry_.new_utf8_(domain_str);
		this.ns_mgr = Xow_ns_mgr_.default_(app.Utl_case_mgr()); // new Xow_ns_mgr(app.Utl_case_mgr()); // FIXME
		this.db_mgr = new Xowv_db_mgr(domain_str, wiki_root_dir);
		this.hdump_mgr = new Xowd_hdump_mgr(app, this);
		this.xwiki_mgr = new Xow_xwiki_mgr();
		this.hzip_mgr = new Xow_hzip_mgr(app.Usr_dlg(), this);
	}
	public byte[] Domain_bry() {return domain_bry;} private final byte[] domain_bry;
	public String Domain_str() {return domain_str;} private final String domain_str;
	public Xow_ns_mgr Ns_mgr() {return ns_mgr;} private final Xow_ns_mgr ns_mgr;
	public Xowv_db_mgr Db_mgr() {return db_mgr;} private final Xowv_db_mgr db_mgr;
	public Xowd_hdump_mgr Hdump_mgr() {return hdump_mgr;} private final Xowd_hdump_mgr hdump_mgr;
	public Xow_hzip_mgr Hzip_mgr() {return hzip_mgr;} private Xow_hzip_mgr hzip_mgr;
	public Xow_xwiki_mgr Xwiki_mgr() {return xwiki_mgr;} private Xow_xwiki_mgr xwiki_mgr;
	private boolean init_done = false;
	public Xowv_wiki Init_assert() {
		if (init_done) return this;
		init_done = true;
		Db_provider core_provider = app.Db_mgr().Get(db_mgr.Key__core());
		Xodb_xowa_ns_tbl ns_tbl = db_mgr.Tbl_mgr().Tbl__ns();
		ns_tbl.Provider_(core_provider);
		ns_tbl.Select_all(ns_mgr);
		return this;
	}
	public Xoa_ttl Ttl_parse(byte[] ttl) {return Xoa_ttl.parse(app.Utl_bfr_mkr(), app.Utl_amp_mgr(), app.Utl_case_mgr(), xwiki_mgr, ns_mgr, app.Utl_msg_log(), ttl, 0, ttl.length);}
	public Xoa_ttl Ttl_parse(int ns_id, byte[] ttl) {
		Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
		byte[] raw = Bry_.Add(ns.Name_db_w_colon(), ttl);
		return Xoa_ttl.parse(app.Utl_bfr_mkr(), app.Utl_amp_mgr(), app.Utl_case_mgr(), xwiki_mgr, ns_mgr, app.Utl_msg_log(), raw, 0, raw.length);
	}
}
