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
package gplx.xowa.addons.bldrs.infos; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.xowa.specials.*; import gplx.langs.mustaches.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*; import gplx.xowa.addons.bldrs.centrals.dbs.datas.imports.*; import gplx.xowa.addons.bldrs.centrals.hosts.*;
import gplx.xowa.wikis.domains.*; import gplx.core.ios.*;
class Xobc_info_html extends Xow_special_wtr__base {
	private final    Xobc_task_mgr task_mgr;
	private final    int task_id;
	public Xobc_info_html(Xobc_task_mgr task_mgr, int task_id) {this.task_mgr = task_mgr; this.task_id = task_id;}
	@Override protected Io_url Get_addon_dir(Xoa_app app)			{return app.Fsys_mgr().Http_root().GenSubDir_nest("bin", "any", "xowa", "addon", "bldr", "info");}
	@Override protected Io_url Get_mustache_fil(Io_url addon_dir)	{return addon_dir.GenSubFil_nest("bin", "xobc_info.mustache.html");}
	@Override protected Mustache_doc_itm Bld_mustache_root(Xoa_app app) {
		// get steps for task
		Xobc_data_db data_db = task_mgr.Data_db();
		List_adp list = data_db.Tbl__step_map().Select_all(task_id);

		// get underlying files
		Host_eval_itm host_eval = new Host_eval_itm();
		int len = list.Len();
		Xobc_info_url[] step_urls = new Xobc_info_url[len];
		Xow_domain_itm wiki_domain = null;
		int host_id = -1;
		long total_size = 0;
		Bry_bfr tmp_size_bfr = Bry_bfr_.New();
		for (int i = 0; i < len; ++i) {
			int step_id = Int_.cast(list.Get_at(i));
			Xobc_import_step_itm step_itm = data_db.Tbl__import_step().Select_one(step_id);
			if (i == 0) {
				wiki_domain = Xow_abrv_xo_.To_itm(step_itm.Wiki_abrv());	// ASSUME: 1st step's wiki is same for all steps
				host_id = step_itm.Host_id;									// ASSUME: 1st step's host_id is same for all steps
			}
			String src_fil = host_eval.Eval_src_fil(data_db, host_id, wiki_domain, step_itm.Import_name);
			Io_size_.To_bfr_new(tmp_size_bfr, step_itm.Import_size_zip, 2);
			total_size += step_itm.Import_size_raw;
			Xobc_info_url step_url = new Xobc_info_url(src_fil, tmp_size_bfr.To_bry_and_clear(), step_itm.Import_md5);
			step_urls[i] = step_url;
		}

		// get wiki data, total_size
		host_eval.Eval_dir_name(wiki_domain);
		Io_url trg_dir = app.Fsys_mgr().Wiki_dir().GenSubDir(wiki_domain.Domain_str());
		Io_size_.To_bfr_new(tmp_size_bfr, total_size, 2);
		byte[] total_size_bry = tmp_size_bfr.To_bry_and_clear();

		// get torrent
		String torrent_fil = null;
		String key = data_db.Tbl__task_regy().Select_key_by_id_or_null(task_id);
		if (key == null) torrent_fil = "failed to get torrent for " + Int_.To_str(task_id);
		else {// convert task_key to name; en.wikipedia.org|2016.06|html
			String src_dir = host_eval.Eval_src_dir(data_db, host_id, wiki_domain);
			String[] ary = String_.Split(key, "|");
			ary[0] = String_.new_u8(wiki_domain.Abrv_wm());
			torrent_fil = String_.Format("{0}Xowa_{1}wiki_latest_archive.torrent", src_dir, wiki_domain.Lang_orig_key()); // EX: https://archive.org/download/Xowa_dewiki_latest/Xowa_dewiki_latest_archive.torrent
		}
		
		return new Xobc_info_doc
		( wiki_domain.Domain_bry()
		, trg_dir.Raw()
		, total_size_bry
		, torrent_fil
		, step_urls
		);
	}
	public static String Make_torrent_fil(String src_dir, Xow_domain_itm domain) {
		return String_.Format("{0}Xowa_{1}wiki_latest_archive.torrent", src_dir, domain.Lang_orig_key()); // EX: https://archive.org/download/Xowa_dewiki_latest/Xowa_dewiki_latest_archive.torrent
	}
	@Override protected void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data page_data) {
		Xopg_tag_mgr head_tags = page_data.Head_tags();
		Xopg_tag_wtr_.Add__xocss	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xohelp	(head_tags, app.Fsys_mgr().Http_root());
		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xobc_info.css")));
	}
	@Override protected void Handle_invalid(Xoa_app app, Xoa_page page, Xow_special_page special) {
		new Xopage_html_data(special.Special__meta().Display_ttl(), Bry_.new_u8("task has been deleted")).Apply(page);
	}
}
