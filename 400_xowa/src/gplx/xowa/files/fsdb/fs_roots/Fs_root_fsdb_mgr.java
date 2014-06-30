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
import gplx.ios.*; import gplx.fsdb.*;
import gplx.xowa.files.qrys.*; import gplx.xowa.files.bins.*;
import gplx.xowa.files.fsdb.caches.*;
import gplx.xowa.files.gui.*;
public class Fs_root_fsdb_mgr implements Xof_fsdb_mgr, GfoInvkAble {
	public boolean Tid_is_mem() {return false;}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public Xof_qry_mgr Qry_mgr() {throw Err_.not_implemented_();}
	public Xof_bin_mgr Bin_mgr() {throw Err_.not_implemented_();}
	public Xof_bin_wkr Bin_wkr_fsdb() {throw Err_.not_implemented_();}
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} private Gfo_usr_dlg usr_dlg;
	public Cache_mgr Cache_mgr() {throw Err_.not_implemented_();}
	public void Db_bin_max_(long v) {throw Err_.not_implemented_();}
	public boolean Patch_upright() {return true;}
	public Fsdb_mnt_mgr Mnt_mgr() {throw Err_.not_implemented_();}
	public void Img_insert(Fsdb_xtn_img_itm rv, byte[] dir, byte[] fil, int ext_id, int img_w, int img_h, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {throw Err_.not_implemented_();}
	public void Thm_insert(Fsdb_xtn_thm_itm rv, byte[] dir, byte[] fil, int ext_id, int thm_w, int thm_h, double thumbtime, int page, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {throw Err_.not_implemented_();}
	public void Fil_insert(Fsdb_fil_itm rv    , byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {throw Err_.not_implemented_();}
	public void Reg_insert(Xof_fsdb_itm itm, byte repo_id, byte status) {throw Err_.not_implemented_();}
	private Fs_root_wkr_fsdb fsdb_wkr;
	public Fs_root_fsdb_mgr(Xow_wiki wiki) {this.Init_by_wiki(wiki); fsdb_wkr = new Fs_root_wkr_fsdb(wiki);}
	public boolean Init_by_wiki(Xow_wiki wiki) {
		this.wiki = wiki;
		this.usr_dlg = wiki.App().Usr_dlg();
		return true;
	}
	public boolean Init_by_wiki__add_bin_wkrs(Xow_wiki wiki) {return this.Init_by_wiki(wiki);}
	public void Init_by_wiki(Xow_wiki wiki, Io_url db_dir, Io_url fs_dir, Xow_repo_mgr repo_mgr) {this.Init_by_wiki(wiki);}
	public void Reg_select(Xoa_page page, byte exec_tid, ListAdp itms) {
		int itms_len = itms.Count();
		for (int i = 0; i < itms_len; i++) {
			Xof_fsdb_itm itm = (Xof_fsdb_itm)itms.FetchAt(i);
			if (fsdb_wkr.Find_file(exec_tid, itm))
				Js_img_mgr.Update_img(page, itm);
		}
	}
	public void Reg_select_only(Xoa_page page, byte exec_tid, ListAdp itms, OrderedHash hash) {
	}
	public void Rls() {}
	private Io_url Xto_url(byte[] v) {
		if (Op_sys.Cur().Tid_is_wnt())
			v = Bry_.Replace(v, Byte_ascii.Slash, Byte_ascii.Backslash);
		return Bry_fmtr_eval_mgr_.Eval_url(wiki.App().Url_cmd_eval(), v);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_root_dir_))		fsdb_wkr.Root_dir_(Xto_url(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_orig_dir_))		fsdb_wkr.Orig_dir_(Xto_url(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_thumb_dir_))		fsdb_wkr.Thumb_dir_(Xto_url(m.ReadBry("v")));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_root_dir_ = "root_dir_", Invk_orig_dir_ = "orig_dir_", Invk_thumb_dir_ = "thumb_dir_";
}
