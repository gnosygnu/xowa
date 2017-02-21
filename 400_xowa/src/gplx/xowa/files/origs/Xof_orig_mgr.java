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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*; import gplx.dbs.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.downloads.*;
import gplx.xowa.apps.wms.apis.*; import gplx.xowa.apps.wms.apis.origs.*;
public class Xof_orig_mgr {
	private Xof_orig_wkr[] wkrs; private int wkrs_len;
	private Xof_url_bldr url_bldr; private Xow_repo_mgr repo_mgr; private final    Xof_img_size img_size = new Xof_img_size();
	public Xof_orig_mgr() {this.Wkrs__clear();}
	public void Init_by_wiki(Xow_wiki wiki, Xof_fsdb_mode fsdb_mode, Xof_orig_tbl[] orig_tbls, Xof_url_bldr url_bldr) {
		this.repo_mgr = wiki.File__repo_mgr(); this.url_bldr = url_bldr;
//			if (!fsdb_mode.Tid_v0()) {		// add view,make; don't add if wmf
			int orig_tbls_len = orig_tbls.length;
			for (int i = 0; i < orig_tbls_len; ++i) {
				Xof_orig_tbl orig_tbl = orig_tbls[i];
				this.Wkrs__add(new Xof_orig_wkr__orig_db(orig_tbl, i == orig_tbls_len - 1));
			}
//			}
		if (!fsdb_mode.Tid__v2__bld()) {	// add if gui, but not if bld
			Io_url wiki_meta_dir = wiki.App().Fsys_mgr().File_dir().GenSubDir_nest("#meta", wiki.Domain_str());
			if (Io_mgr.Instance.ExistsDir(wiki_meta_dir)) {
				Xof_orig_wkr__xo_meta xo_meta = new Xof_orig_wkr__xo_meta(wiki_meta_dir);
				this.Wkrs__add(xo_meta);
			}
			this.Wkrs__add(new Xof_orig_wkr__wmf_api(new Xoapi_orig_wmf(), wiki.App().Wmf_mgr().Download_wkr(), repo_mgr, wiki.Domain_bry()));
		}
	}
	public Xof_orig_itm Find_by_ttl_or_null(byte[] ttl) {return Find_by_ttl_or_null(ttl, 0, 1);}
	public Xof_orig_itm Find_by_ttl_or_null(byte[] ttl, int list_idx, int list_len) {
		for (int i = 0; i < wkrs_len; i++) {
			Xof_orig_wkr wkr = wkrs[i];
			Xof_orig_itm orig = wkr.Find_as_itm(ttl, list_idx, list_len); if (orig == Xof_orig_itm.Null) continue;
			if (orig.Insert_new()) this.Insert(orig.Repo(), ttl, orig.Ext_id(), orig.W(), orig.H(), orig.Redirect()); // NOTE: orig_page must be same as find_arg not orig.Page() else will not be found for next call; DATE:2015-04-14
			return orig;
		}
		return Xof_orig_itm.Null;
	}
	public void Find_by_list(Ordered_hash rv, List_adp itms, int exec_tid) {
		for (int i = 0; i < wkrs_len; i++) {
			Xof_orig_wkr wkr = wkrs[i];
			wkr.Find_by_list(rv, itms);
		}
		int len = itms.Count();
		for (int i = 0; i < len; i++) {
			try {
				Xof_fsdb_itm fsdb = (Xof_fsdb_itm)itms.Get_at(i);
				fsdb.Orig_exists_n_();	// default to status = missing
				Xof_orig_itm orig = (Xof_orig_itm)rv.Get_by(fsdb.Lnki_ttl()); if (orig == Xof_orig_itm.Null) continue;
				if (orig.Insert_new()) this.Insert(orig.Repo(), fsdb.Lnki_ttl(), orig.Ext_id(), orig.W(), orig.H(), orig.Redirect());	// NOTE: orig_page must be same as find_arg not orig.Page() else will not be found for next call; DATE:2015-04-14
				Xof_file_wkr.Eval_orig(orig, fsdb, url_bldr, repo_mgr, img_size);
				if (!Io_mgr.Instance.ExistsFil(fsdb.Html_view_url()))
					fsdb.File_exists_n_();
			} catch (Exception e) {
				Xoa_app_.Usr_dlg().Warn_many("", "", "orig: exc=~{0}", Err_.Message_gplx_full(e));
			}
		}
	}
	public void Insert(byte repo, byte[] page, int ext, int w, int h, byte[] redirect) {
		for (int i = 0; i < wkrs_len; i++) {
			Xof_orig_wkr wkr = wkrs[i];
			if (wkr.Add_orig(repo, page, ext, w, h, redirect)) break;
		}			
	}
	private void		Wkrs__clear() {wkrs = Xof_orig_wkr_.Ary_empty; wkrs_len = 0;}
	public void			Wkrs__add(Xof_orig_wkr... v) {
		wkrs = (Xof_orig_wkr[])Array_.Resize_add(wkrs, v);
		wkrs_len += v.length;
	}
	public void			Wkrs__set(Xof_orig_wkr... v) {
		wkrs = v;
		wkrs_len = v.length;
	}
	public void			Wkrs__del(byte tid) {
		List_adp list = List_adp_.New();
		for (int i = 0; i < wkrs_len; ++i) {
			Xof_orig_wkr wkr = wkrs[i];
			if (wkr.Tid() == tid) continue;	// do not add deleted wkr
			list.Add(wkr);
		}
		wkrs = (Xof_orig_wkr[])list.To_ary_and_clear(Xof_orig_wkr.class);
		wkrs_len = wkrs.length;
	}
}
