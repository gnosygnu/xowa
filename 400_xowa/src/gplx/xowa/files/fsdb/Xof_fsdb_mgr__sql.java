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
package gplx.xowa.files.fsdb; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.imgs.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.caches.*;
public class Xof_fsdb_mgr__sql implements Xof_fsdb_mgr, GfoInvkAble {
	private Xof_fsdb_wkr wkr; private boolean init = false;
	public Xof_orig_mgr Orig_mgr() {return orig_mgr;} private final Xof_orig_mgr orig_mgr = new Xof_orig_mgr();
	public Xof_bin_mgr Bin_mgr() {return bin_mgr;} private Xof_bin_mgr bin_mgr;
	public Fsm_mnt_mgr Mnt_mgr() {return mnt_mgr;} private final Fsm_mnt_mgr mnt_mgr = new Fsm_mnt_mgr();
	public void Init_by_wiki(Xow_wiki wiki) {
		if (init) return;
		try {
			init = true;
			// wiki.Rls_list().Add(this);
			boolean version_is_1 = wiki.File_mgr__version_is_1();
			Xoa_app app = wiki.App();
			Xof_cache_mgr cache_mgr = app.File_mgr__cache_mgr(); Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_(); 
			Xow_repo_mgr repo_mgr = wiki.File_mgr__repo_mgr(); Xof_img_mgr img_mgr = app.File_mgr__img_mgr();
			Io_url db_dir = wiki.Fsys_mgr().File_dir();
			orig_mgr.Init_by_wiki(db_dir, version_is_1, repo_mgr, url_bldr);
			mnt_mgr.Init_by_wiki(db_dir, version_is_1);
			this.bin_mgr = new Xof_bin_mgr(mnt_mgr, repo_mgr, cache_mgr, app.Wmf_mgr(), url_bldr);
			bin_mgr.Init_by_wiki(img_mgr.Wkr_resize_img());
			wkr = new Xof_fsdb_wkr(this, Xoa_app_.Usr_dlg(), cache_mgr, repo_mgr, url_bldr);
		}	catch (Exception exc) {throw Err_.new_fmt_("failed to initialize fsdb_mgr: wiki={0) err={1}", wiki.Domain_str(), Err_.Message_gplx_brief(exc));}
	}
	public void Fsdb_search_by_list(byte exec_tid, ListAdp itms, Xoae_page page) {
		OrderedHash rv = OrderedHash_.new_bry_();
		orig_mgr.Find_by_list(rv, itms, exec_tid);
		wkr.Fsdb_search(exec_tid, itms, page);
	}
	public void Txn_save() {
		mnt_mgr.Txn_save();
		orig_mgr.Txn_save();
	}
	public void Rls() {
		this.Txn_save();	// NOTE: must call save, else user db will not update next id; DATE:2014-02-11
		mnt_mgr.Rls();
		orig_mgr.Rls();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_mnt_mgr))	return mnt_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_mnt_mgr = "mnt_mgr";
}
