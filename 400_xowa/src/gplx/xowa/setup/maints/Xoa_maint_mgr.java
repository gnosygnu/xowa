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
package gplx.xowa.setup.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.setup.*;
import gplx.ios.*; import gplx.xowa.wikis.*;
public class Xoa_maint_mgr implements GfoInvkAble {
	public Xoa_maint_mgr(Xoa_app app) {
		this.app = app;
		wmf_dump_status_url = Wmf_dump_status_url(app);
		wiki_mgr = new Xoa_maint_wikis_mgr(app);
	}	private Xoa_app app; private Io_url wmf_dump_status_url;
	public Xoa_maint_wikis_mgr Wiki_mgr() {return wiki_mgr;} private Xoa_maint_wikis_mgr wiki_mgr;
	public boolean Wmf_dump_status_loaded() {return wmf_dump_status_loaded;} private boolean wmf_dump_status_loaded;
	public void Wmf_dump_status_loaded_assert() {
		if (!wmf_dump_status_loaded) {
			Wmf_status_parse();
			wmf_dump_status_loaded = true;
		}
	}
	public void Wmf_status_update() {
		Wmf_status_download();
		Wmf_status_parse();
	}
	public boolean Wmf_status_download() {
		String[] server_urls = app.Setup_mgr().Dump_mgr().Server_urls();
		int len = server_urls.length;
		Xof_download_wkr download_wkr = app.File_mgr().Download_mgr().Download_wkr();
		for (int i = 0; i < len; i++) {
			String server_url = server_urls[i] + "backup-index.html";
			byte rslt = download_wkr.Download(true, server_url, wmf_dump_status_url, "downloading wmf status");
			if (rslt == IoEngine_xrg_downloadFil.Rslt_pass) return true;
		}
		app.Usr_dlg().Prog_many("", "", "could not download latest status");
		return false;
	}
	public boolean Wmf_status_parse() {
		Wmf_dump_list_parser parser = new Wmf_dump_list_parser();
		Hash_adp_bry itms_hash = Hash_adp_bry.cs_();		
		Wmf_dump_itm[] itms = parser.Parse(Io_mgr._.LoadFilBry(wmf_dump_status_url));
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Wmf_dump_itm itm = itms[i];
			byte[] wiki_abrv = itm.Wiki_abrv();
			byte[] wiki_domain = Xow_wiki_alias.Parse__domain_name(wiki_abrv, 0, wiki_abrv.length);
			if (wiki_domain == Xow_wiki_alias.Parse__domain_name_null) continue;	// invalid wiki-name; ex: nycwikimedia
			itms_hash.Add(wiki_domain, itm);
		}
		len = app.Wiki_mgr().Count();
		for (int i = 0; i < len; i++) {
			Xow_wiki wiki = app.Wiki_mgr().Get_at(i);
			Wmf_dump_itm itm = (Wmf_dump_itm)itms_hash.Get_by_bry(wiki.Domain_bry());
			if (itm == null) continue;
			wiki.Maint_mgr().Wmf_dump_date_(itm.Dump_date()).Wmf_dump_done_(itm.Status_tid() == Wmf_dump_itm.Status_tid_complete).Wmf_dump_status_(itm.Status_msg());
		}
		return true;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_wmf_status_update))		Wmf_status_update();
		else if (ctx.Match(k, Invk_wikis))					return wiki_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_wmf_status_update = "wmf_status_update", Invk_wikis = "wikis";
	public static Io_url Wmf_dump_status_url(Xoa_app app) {return app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("html", "xowa", "maint", "backup-index.html");}
}
