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
package gplx.xowa.guis.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.xowa.files.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.repos.*;
import gplx.xowa.htmls.doms.*;
import gplx.xowa.parsers.lnkis.*;
class Xog_url_wkr__file {
	private final    Xoae_app app;
	private final    Xowe_wiki page_wiki;
	private final    Xoae_page page;
	private final    Xof_img_size img_size = new Xof_img_size();
	private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public Xog_url_wkr__file(Xoae_app app, Xowe_wiki page_wiki, Xoae_page page) {
		this.app = app;
		this.page_wiki = page_wiki;
		this.page = page;
	}
	public byte[] File_page_db() {return file_page_db;} private byte[] file_page_db;
	public Io_url File_url() {return file_url;} private Io_url file_url;
	public Xof_fsdb_itm Fsdb() {return fsdb;} private Xof_fsdb_itm fsdb;
	public Xowe_wiki File_wiki() {return file_wiki;} private Xowe_wiki file_wiki;
	public void Extract_data(String html_doc, byte[] href_bry) {	// EX: file:///xowa/A.png
		// find xowa_ttl from html_doc for given file; EX: file:///xowa/A.png -> xowa_ttl = "A.png"
		String xowa_ttl = Xoh_dom_.Title_by_href(href_bry, Bry_.new_u8(html_doc));
		if (xowa_ttl == null) {
			app.Gui_mgr().Kit().Ask_ok("", "", "could not find anchor with href in html: href=~{0}", href_bry);
			return;
		}

		// get fsdb itm
		this.file_page_db = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Decode(Xoa_ttl.Replace_spaces(Bry_.new_u8(xowa_ttl)));
		this.file_wiki = (Xowe_wiki)page.Commons_mgr().Source_wiki_or(page_wiki);
		this.fsdb = Xog_url_wkr__file.Make_fsdb(file_wiki, file_page_db, img_size, url_bldr);

		this.file_url = Io_url_.New__http_or_fail(String_.new_u8(gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Decode(href_bry)));
	}
	public void Download_and_run() {
		// download file if it doesn't exist
		if (!Io_mgr.Instance.ExistsFil(file_url)) {
			if (!Xof_file_wkr.Show_img(fsdb, Xoa_app_.Usr_dlg(), file_wiki.File__bin_mgr(), file_wiki.File__mnt_mgr(), file_wiki.App().User().User_db_mgr().Cache_mgr(), file_wiki.File__repo_mgr(), gplx.xowa.guis.cbks.js.Xog_js_wkr_.Noop, img_size, url_bldr, page))
				return;
		}

		// try to launch file
		gplx.core.ios.IoItmFil fil = Io_mgr.Instance.QueryFil(file_url);
		if (fil.Exists()) {
			gplx.core.envs.Process_adp media_player = app.Prog_mgr().App_by_ext(file_url.Ext());
			media_player.Args__include_quotes_(gplx.core.envs.Op_sys.Cur().Tid_is_wnt());	// NOTE:Windows "cmd /c start" requies first quoted argument to be title; note that url must be 2nd arg and quoted; DATE:2016-11-14
			media_player.Run(file_url);

			fsdb.File_size_(fil.Size());
			gplx.xowa.files.caches.Xou_cache_mgr cache_mgr = file_wiki.Appe().User().User_db_mgr().Cache_mgr();
			cache_mgr.Update(fsdb);
			cache_mgr.Db_save();
		}
	}
	public static Xof_fsdb_itm Make_fsdb(Xowe_wiki wiki, byte[] lnki_ttl, Xof_img_size img_size, Xof_url_bldr url_bldr) {
		Xof_fsdb_itm fsdb = new Xof_fsdb_itm();
		lnki_ttl = Xoa_ttl.Replace_spaces(gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Decode(lnki_ttl));
		fsdb.Init_at_lnki(Xof_exec_tid.Tid_viewer_app, wiki.Domain_itm().Abrv_xo(), lnki_ttl, Xop_lnki_type.Id_none, Xop_lnki_tkn.Upright_null, Xof_img_size.Size__neg1, Xof_img_size.Size__neg1, Xof_lnki_time.Null, Xof_lnki_page.Null, Xof_patch_upright_tid_.Tid_all);
		fsdb.Init_at_hdoc(Int_.Max_value, Xof_html_elem.Tid_img);// NOTE: set elem_id to "impossible" number, otherwise it will auto-update an image on the page with a super-large size; [[File:Alfred Sisley 062.jpg]]
		Xof_orig_itm orig = wiki.File__orig_mgr().Find_by_ttl_or_null(lnki_ttl); if (orig == Xof_orig_itm.Null) return null;	// orig not found; need orig in order to get repo
		Xof_repo_itm repo = wiki.File__repo_mgr().Get_trg_by_id_or_null(orig.Repo(), lnki_ttl, Bry_.Empty); if (repo == null) return null; // repo not found
		fsdb.Init_at_orig(orig.Repo(), repo.Wiki_domain(), orig.Ttl(), orig.Ext(), orig.W(), orig.H(), orig.Redirect());
		fsdb.Init_at_html(Xof_exec_tid.Tid_viewer_app, img_size, repo, url_bldr);
		fsdb.File_is_orig_(true);
		return fsdb;
	}
}
