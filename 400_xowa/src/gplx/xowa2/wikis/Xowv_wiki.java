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
package gplx.xowa2.wikis; import gplx.*; import gplx.xowa2.*;
import gplx.xowa.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.langs.cases.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.html.hzips.*;
import gplx.xowa.dbs.tbls.*; import gplx.dbs.*; import gplx.xowa.hdumps.*; import gplx.xowa.wikis.*; import gplx.xowa.files.repos.*;
import gplx.xowa2.apps.*; import gplx.xowa2.wikis.specials.*; import gplx.xowa2.wikis.data.*; import gplx.xowa2.gui.*;
public class Xowv_wiki implements Xow_wiki, Xow_ttl_parser {
	public Xowv_wiki(Xoav_app app, byte[] domain_bry, Io_url wiki_root_dir) {
		this.app = app;
		this.domain_bry = domain_bry; this.domain_str = String_.new_utf8_(domain_bry); 
		this.domain_itm = Xow_domain_.parse(domain_bry);
		this.domain_tid = domain_itm.Domain_tid();
		this.domain_abrv = Xow_wiki_alias.Build_alias(Xow_domain_.parse(domain_bry));
		this.ns_mgr = Xow_ns_mgr_.default_(app.Utl_case_mgr()); // new Xow_ns_mgr(app.Utl_case_mgr()); // FIXME
		this.data_mgr = new Xowd_data_mgr(domain_str, wiki_root_dir, ns_mgr);
		this.hdump_mgr = new Xowd_hdump_mgr(app, this);
		this.xwiki_mgr = new Xow_xwiki_mgr();
		this.hzip_mgr = new Xow_hzip_mgr(app.Usr_dlg(), this);
		this.special_mgr = new Xosp_special_mgr(this);
		this.fsys_mgr = new Xow_fsys_mgr(wiki_root_dir, wiki_root_dir);
	}
	public Xoa_app				App() {return app;}
	public byte[]				Domain_bry() {return domain_bry;} private final byte[] domain_bry;
	public String				Domain_str() {return domain_str;} private final String domain_str;
	public Xow_domain			Domain_itm() {return domain_itm;} private final Xow_domain domain_itm;
	public int					Domain_tid() {return domain_tid;} private final int domain_tid;
	public byte[]				Domain_abrv() {return domain_abrv;} private final byte[] domain_abrv;
	public Xow_ns_mgr			Ns_mgr() {return ns_mgr;} private final Xow_ns_mgr ns_mgr;
	public Xow_fsys_mgr			Fsys_mgr() {return fsys_mgr;} private Xow_fsys_mgr fsys_mgr;
	public boolean					File_mgr__version_is_1() {return Bool_.Y;}
	public Xow_repo_mgr			File_mgr__repo_mgr() {return file_mgr__repo_mgr;} private Xowv_repo_mgr file_mgr__repo_mgr = new Xowv_repo_mgr();
	public Xol_lang				Lang() {throw Err_.not_implemented_();}
	public Xosp_special_mgr Special_mgr() {return special_mgr;} private Xosp_special_mgr special_mgr;
	public Xowd_data_mgr Db_mgr() {return data_mgr;} private final Xowd_data_mgr data_mgr;
	public Xowd_hdump_mgr Hdump_mgr() {return hdump_mgr;} private final Xowd_hdump_mgr hdump_mgr;
	public Xow_hzip_mgr Hzip_mgr() {return hzip_mgr;} private Xow_hzip_mgr hzip_mgr;
	public Xow_xwiki_mgr Xwiki_mgr() {return xwiki_mgr;} private Xow_xwiki_mgr xwiki_mgr;
	public Xoav_app Appv() {return app;} private final Xoav_app app;
	public void Pages_get(Xog_page rv, Gfo_url url, Xoa_ttl ttl) {
		data_mgr.Init_assert();
		if (ttl.Ns().Id_special())
			special_mgr.Get_by_ttl(rv, url, ttl);
		else
			hdump_mgr.Get_by_ttl(rv, url, ttl);
	}
	public Xoa_ttl Ttl_parse(byte[] ttl) {return Xoa_ttl.parse(app.Utl_bfr_mkr(), app.Utl_amp_mgr(), app.Utl_case_mgr(), xwiki_mgr, ns_mgr, app.Utl_msg_log(), ttl, 0, ttl.length);}
	public Xoa_ttl Ttl_parse(int ns_id, byte[] ttl) {
		Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
		byte[] raw = Bry_.Add(ns.Name_db_w_colon(), ttl);
		return Xoa_ttl.parse(app.Utl_bfr_mkr(), app.Utl_amp_mgr(), app.Utl_case_mgr(), xwiki_mgr, ns_mgr, app.Utl_msg_log(), raw, 0, raw.length);
	}
}
