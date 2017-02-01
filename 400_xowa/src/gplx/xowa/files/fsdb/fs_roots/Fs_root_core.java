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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.xowa.guis.cbks.js.*;
public class Fs_root_core implements Xof_fsdb_mgr, Gfo_invk {	// reads images from file-system dir
	private Xowe_wiki wiki;
	private final    Fs_root_mgr mgr;
	public Fs_root_core(Xowe_wiki wiki) {
		this.Init_by_wiki(wiki);
		this.mgr = new Fs_root_mgr(wiki);
	}
	public gplx.xowa.files.bins.Xof_bin_mgr Bin_mgr() {throw Err_.new_unimplemented();}
	public gplx.fsdb.meta.Fsm_mnt_mgr Mnt_mgr()       {return null;}
	public void Init_by_wiki(Xow_wiki wiki) {
		this.wiki = (Xowe_wiki)wiki;
	}
	public void Fsdb_search_by_list(List_adp itms, Xow_wiki wiki, Xoa_page page, Xog_js_wkr js_wkr) {
		int itms_len = itms.Count();

		// NOTE: do not cache in /xowa/file b/c files will already exist in /xowa/wiki/wiki_name/file/thumb
		gplx.xowa.files.caches.Xou_cache_mgr cache_mgr = wiki.App().User().User_db_mgr().Cache_mgr();
		for (int i = 0; i < itms_len; i++) {
			Xof_fsdb_itm itm = (Xof_fsdb_itm)itms.Get_at(i);
			if (mgr.Find_file(itm)) {
				Js_img_mgr.Update_img(page, js_wkr, itm);
				cache_mgr.Update(itm);
			}
		}
		cache_mgr.Db_save();
	}
	public void Rls() {}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_root_dir_))		mgr.Root_dir_(To_url(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_orig_dir_))		mgr.Orig_dir_(To_url(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_thumb_dir_))		{}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_root_dir_ = "root_dir_", Invk_orig_dir_ = "orig_dir_", Invk_thumb_dir_ = "thumb_dir_";
	private Io_url To_url(byte[] v) {
		if (gplx.core.envs.Op_sys.Cur().Tid_is_wnt())
			v = Bry_.Replace(v, Byte_ascii.Slash, Byte_ascii.Backslash);
		return gplx.core.brys.fmtrs.Bry_fmtr_eval_mgr_.Eval_url(wiki.Appe().Url_cmd_eval(), v);
	}
	public static Fs_root_core New(Xow_file_mgr file_mgr, Xowe_wiki wiki) {
		Fs_root_core rv = new Fs_root_core(wiki);
		file_mgr.Fsdb_mgr_(rv);

		file_mgr.Orig_mgr().Wkrs__del(gplx.xowa.files.origs.Xof_orig_wkr_.Tid_wmf_api);
		file_mgr.Orig_mgr().Wkrs__set(new Xof_orig_wkr__fs_root(rv.mgr.Wkr()));
		return rv;
	}
}
