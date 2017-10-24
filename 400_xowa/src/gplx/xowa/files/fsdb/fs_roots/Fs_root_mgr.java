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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.fsdb.meta.*;	
class Fs_root_mgr {
	private final    Xowe_wiki wiki;
	private final    Fs_root_wkr wkr = new Fs_root_wkr();
	private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	private final    Xof_img_size img_size = new Xof_img_size();
	private final    String_obj_ref tmp_resize_result = String_obj_ref.null_();
	private Xof_repo_itm repo;
	private Io_url orig_dir;
	public Fs_root_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
	}
	public Fs_root_wkr Wkr() {return wkr;}
	public boolean Find_file(Xof_fsdb_itm fsdb_itm) {
		// get orig; exit if not found in dir
		byte[] orig_ttl = fsdb_itm.Orig_ttl();
		Orig_fil_row orig_itm = wkr.Get_by_ttl(orig_ttl);
		if (orig_itm == Orig_fil_row.Null) return false;

		// update fsdb.orig metadata
		Xof_ext orig_ext = Xof_ext_.new_by_id_(orig_itm.Ext_id());
		fsdb_itm.Init_at_orig(Xof_repo_tid_.Tid__local, wiki.Domain_bry(), orig_ttl, orig_ext, orig_itm.W(), orig_itm.H(), null);			

		// gen cache_orig_url
		if (repo == null) repo = wiki.File__repo_mgr().Repos_get_by_wiki(wiki.Domain_bry()).Trg(); // assert repo
		Io_url cache_orig_url = url_bldr.To_url_trg(repo, fsdb_itm, Bool_.Y);
		fsdb_itm.Html_orig_url_(cache_orig_url);

		// update html_w, html_h, html_orig_url, html_view_url
		Io_url actl_orig_url = orig_itm.Url();
		fsdb_itm.Init_at_html(Xof_exec_tid.Tid_wiki_page, img_size, repo, url_bldr);

		// if cache_file doesn't exist, either copy file 
		if (fsdb_itm.File_is_orig()) {
			// copy orig to cache
			if (!Io_mgr.Instance.ExistsFil(cache_orig_url)) {
				Io_mgr.Instance.CopyFil(actl_orig_url, cache_orig_url, false);
				fsdb_itm.File_size_(Io_mgr.Instance.QueryFil(actl_orig_url).Size()); // set size for file_cache, else clear_cache won't delete items with 0 size
			}
		}
		else {
			// make thumb by imagemagick
			Io_url cache_thumb_url = url_bldr.To_url_trg(repo, fsdb_itm, Bool_.N);
			if (!Io_mgr.Instance.ExistsFil(cache_thumb_url)) {
				if (!wiki.Appe().File_mgr().Img_mgr().Wkr_resize_img().Resize_exec(actl_orig_url, cache_thumb_url, fsdb_itm.Html_w(), fsdb_itm.Html_h(), fsdb_itm.Orig_ext().Id(), tmp_resize_result))
					return false;
				fsdb_itm.File_size_(Io_mgr.Instance.QueryFil(cache_thumb_url).Size()); // set size for file_cache, else clear_cache won't delete items with 0 size
			}
		}
		return true;
	}
	public void Root_dir_(Io_url v) {
		Io_url root_dir = v;
		orig_dir = root_dir.GenSubDir("orig");
		orig_dir_mgr_init(orig_dir);
	}
	public void Orig_dir_(Io_url v) {
		orig_dir = v;
		orig_dir_mgr_init(orig_dir);
	}
	private void orig_dir_mgr_init(Io_url orig_dir) {
		wkr.Init(wiki.Appe().File_mgr().Img_mgr().Wkr_query_img_size(), orig_dir);
	}
}
