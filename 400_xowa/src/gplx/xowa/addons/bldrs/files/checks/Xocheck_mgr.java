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
package gplx.xowa.addons.bldrs.files.checks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.core.ios.streams.*;
import gplx.dbs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*;
import gplx.xowa.addons.bldrs.wmdumps.imglinks.*;
import gplx.xowa.htmls.*;
// TODO.XO:cache files in memory, else commonly used files (Wiki.png) will be loaded from fsdb for every usage on page
// TODO.XO:save results to db to verify unused images (images in fsdb, but not loaded during this code)
class Xocheck_mgr {
	private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2(); private final    Xof_img_size img_size = new Xof_img_size(); 	
	private Xowe_wiki wiki;
	public void Exec(Xowe_wiki wiki) {
		// init
		this.wiki = wiki;
		wiki.File__bin_mgr().Wkrs__del(gplx.xowa.files.bins.Xof_bin_wkr_.Key_http_wmf);			// must happen after init_file_mgr_by_load; remove wmf wkr, else will try to download images during parsing
		wiki.File_mgr().Fsdb_mode().Tid__v2__mp__y_();
		wiki.App().Cfg().Set_bool_app("xowa.app.web.enabled", false); // never enable inet; rely solely on local dbs;

		// select list of pages
		Xoh_page hpg = new Xoh_page();
		Xowd_page_tbl page_tbl = wiki.Data__core_mgr().Db__core().Tbl__page();
		Db_rdr rdr = page_tbl.Conn().Stmt_sql("SELECT page_id, page_namespace, page_title, page_html_db_id FROM page WHERE page_html_db_id != -1;").Exec_select__rls_auto();
		int page_count = 0, file_count = 0;
		
		// loop over each page
		while (rdr.Move_next()) {
			// init page meta
			Xoa_ttl page_ttl = wiki.Ttl_parse(rdr.Read_int("page_namespace"), rdr.Read_bry_by_str("page_title"));
			Xoa_url page_url = Xoa_url.New(wiki, page_ttl);
			Xow_db_file html_db = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(rdr.Read_int("page_html_db_id"));
			int page_id = rdr.Read_int("page_id");

			// load html
			hpg.Ctor_by_hview(wiki, page_url, page_ttl, page_id);
			if (!html_db.Tbl__html().Select_by_page(hpg)) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "could not load html for page; page_id=~{0}", page_id);
				continue;
			}
			wiki.Html__hdump_mgr().Load_mgr().Parse(hpg, hpg.Db().Html().Zip_tid(), hpg.Db().Html().Hzip_tid(), hpg.Db().Html().Html_bry());

			// load images
			int imgs_len = hpg.Img_mgr().Len();
			for (int i = 0; i < imgs_len; i++) {
				Xof_fsdb_itm fsdb = hpg.Img_mgr().Get_at(i);
				try {Check_images(page_ttl, fsdb);}
				catch (Exception e) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "file failed; page_ttl=~{0} img_name=~{1} err=~{2}", page_ttl.Page_db(), fsdb.Lnki_ttl(), Err_.Message_gplx_log(e));
				}
				file_count++;
			}

			// prog
			page_count++;
			if ((page_count % 10000) == 0) {
				Gfo_usr_dlg_.Instance.Prog_many("", "", "checking pages; pages=~{0} files=~{1}", page_count, file_count);
			}
		}
	}
	private void Check_images(Xoa_ttl page_ttl, Xof_fsdb_itm fsdb) {
		// get orig
		Xof_orig_itm orig = wiki.File__orig_mgr().Find_by_ttl_or_null(fsdb.Lnki_ttl());
		if (orig == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "file missing; page_ttl=~{0} img_name=~{1}", page_ttl.Page_db(), fsdb.Lnki_ttl());
			return;
		}
		Xof_file_wkr.Eval_orig(orig, fsdb, url_bldr, wiki.File__repo_mgr(), img_size);

		Io_stream_rdr img_rdr = wiki.File__bin_mgr().Find_as_rdr(Xof_exec_tid.Tid_wiki_page, fsdb);
		img_rdr.Rls();
	}
}
