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
import gplx.core.ios.*; import gplx.core.brys.fmtrs.*;
import gplx.fsdb.*; import gplx.fsdb.data.*; import gplx.fsdb.meta.*;	
import gplx.xowa.files.gui.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.caches.*;
public class Fs_root_fsdb_mgr implements Xof_fsdb_mgr, GfoInvkAble {	// read images from file-system dir
	private Xowe_wiki wiki; private Fs_root_wkr_fsdb fsdb_wkr;
	public Fs_root_fsdb_mgr(Xowe_wiki wiki) {this.Init_by_wiki(wiki); fsdb_wkr = new Fs_root_wkr_fsdb(wiki);}		
	public void Init_by_wiki(Xow_wiki wiki) {this.wiki = (Xowe_wiki)wiki;}
	public void Fsdb_search_by_list(List_adp itms, Xow_wiki wiki, Xoa_page page, Xog_js_wkr js_wkr) {
		int itms_len = itms.Count();
//			Xou_cache_mgr cache_mgr = wiki.App().User().User_db_mgr().Cache_mgr(); // repo_id is 127; DATE:2015-08-23
		for (int i = 0; i < itms_len; i++) {
			Xof_fsdb_itm itm = (Xof_fsdb_itm)itms.Get_at(i);
			if (fsdb_wkr.Find_file(itm)) {
				Js_img_mgr.Update_img(page, js_wkr, itm);
//					cache_mgr.Update(itm);
			}
		}
//			cache_mgr.Db_save();
	}
	private Io_url Xto_url(byte[] v) {
		if (Op_sys.Cur().Tid_is_wnt())
			v = Bry_.Replace(v, Byte_ascii.Slash, Byte_ascii.Backslash);
		return Bry_fmtr_eval_mgr_.Eval_url(wiki.Appe().Url_cmd_eval(), v);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_root_dir_))		fsdb_wkr.Root_dir_(Xto_url(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_orig_dir_))		fsdb_wkr.Orig_dir_(Xto_url(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_thumb_dir_))		fsdb_wkr.Thumb_dir_(Xto_url(m.ReadBry("v")));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_root_dir_ = "root_dir_", Invk_orig_dir_ = "orig_dir_", Invk_thumb_dir_ = "thumb_dir_";
	public Xof_bin_mgr Bin_mgr() {throw Err_.new_unimplemented();}
	public Fsm_mnt_mgr Mnt_mgr() {return null;}
	public void Rls() {}
}
