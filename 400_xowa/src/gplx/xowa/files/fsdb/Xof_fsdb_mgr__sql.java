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
import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*;	
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.imgs.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.caches.*; import gplx.xowa.files.gui.*;
public class Xof_fsdb_mgr__sql implements Xof_fsdb_mgr, GfoInvkAble {
	private boolean init = false; private boolean fsdb_enabled = false;
	private Xof_cache_mgr cache_mgr; private Xow_repo_mgr repo_mgr; private Xof_url_bldr url_bldr; private final Xof_img_size img_size = new Xof_img_size();
	public Xof_bin_mgr Bin_mgr() {return bin_mgr;} private Xof_bin_mgr bin_mgr;
	public Fsm_mnt_mgr Mnt_mgr() {return mnt_mgr;} private Fsm_mnt_mgr mnt_mgr = new Fsm_mnt_mgr();
	public void Init_by_wiki(Xow_wiki wiki) {
		if (init) return;
		try {
			init = true;
			if (wiki.File_mgr__fsdb_mode().Tid_wmf()) return;
			Xoa_app app = wiki.App();
			this.cache_mgr = app.File_mgr__cache_mgr(); this.url_bldr = Xof_url_bldr.new_v2_(); 
			this.repo_mgr = wiki.File_mgr__repo_mgr(); Xof_img_mgr img_mgr = app.File_mgr__img_mgr();
			Fsdb_db_mgr core = Fsdb_db_mgr_.new_detect(wiki, wiki.Fsys_mgr().Root_dir(), wiki.Fsys_mgr().File_dir());
			if (core == null) return;
			fsdb_enabled = true;
			mnt_mgr.Ctor_by_load(core);
			this.bin_mgr = new Xof_bin_mgr(mnt_mgr, repo_mgr, cache_mgr, img_mgr.Wkr_resize_img());
			bin_mgr.Wkrs__add(Xof_bin_wkr__fsdb_sql.new_(mnt_mgr));
			bin_mgr.Wkrs__add(Xof_bin_wkr__http_wmf.new_(wiki));
		}	catch (Exception exc) {throw Err_.new_fmt_("failed to initialize fsdb_mgr: wiki={0) err={1}", wiki.Domain_str(), Err_.Message_gplx_brief(exc));}
	}
	public void Fsdb_search_by_list(byte exec_tid, ListAdp itms, Xoa_page page, Xog_js_wkr js_wkr) {
		if (!fsdb_enabled) return;
		int len = itms.Count();
		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.I;
		for (int i = 0; i < len; i++) {
			if (usr_dlg.Canceled()) return;
			Xof_fsdb_itm itm = (Xof_fsdb_itm)itms.FetchAt(i);
			Xof_file_wkr.Show_img(exec_tid, itm, usr_dlg, bin_mgr, mnt_mgr, cache_mgr, repo_mgr, js_wkr, img_size, url_bldr, page);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_mnt_mgr))	return mnt_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_mnt_mgr = "mnt_mgr";
}
