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
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.files.repos.*; import gplx.fsdb.meta.*;
class Fs_root_mgr {
	private final    Xowe_wiki wiki;
	private final    Fs_root_wkr wkr = new Fs_root_wkr();
	private final    Xof_img_size img_size = new Xof_img_size();
	private final    String_obj_ref tmp_resize_result = String_obj_ref.null_();
	private Io_url orig_dir, thumb_dir;
	public Fs_root_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
	}
	public Fs_root_wkr Wkr() {return wkr;}
	public boolean Find_file(Xof_fsdb_itm fsdb_itm) {
		byte[] orig_ttl = fsdb_itm.Orig_ttl();
		Orig_fil_row orig_itm = wkr.Get_by_ttl(orig_ttl);
		if (orig_itm == Orig_fil_row.Null) return false;
		fsdb_itm.Change_repo(Xof_repo_tid_.Tid__local, wiki.Domain_bry());
		Io_url orig_url = orig_itm.Url();
		if (fsdb_itm.File_is_orig()) {
			fsdb_itm.Html_size_(orig_itm.W(), orig_itm.H());
			fsdb_itm.Html_view_url_(orig_url);
			fsdb_itm.Html_orig_url_(orig_url);
			return true;
		}
		else {
			String thumb_rel = orig_url.GenRelUrl_orEmpty(orig_dir);
			int upright_patch = wiki.File_mgr().Patch_upright();
			img_size.Html_size_calc(fsdb_itm.Lnki_exec_tid(), fsdb_itm.Lnki_w(), fsdb_itm.Lnki_h(), fsdb_itm.Lnki_type(), upright_patch, fsdb_itm.Lnki_upright(), fsdb_itm.Orig_ext().Id(), orig_itm.W(), orig_itm.H(), Xof_img_size.Thumb_width_img);
			int html_w = img_size.Html_w(), html_h = img_size.Html_h();
			String thumb_name = Int_.To_str(html_w) + "." + String_.new_u8(fsdb_itm.Orig_ext().Ext_view());
			Io_url thumb_url = thumb_dir.GenSubFil_ary(thumb_rel + orig_url.Info().DirSpr(), thumb_name);
			if (!Io_mgr.Instance.ExistsFil(thumb_url)) {
				if (!wiki.Appe().File_mgr().Img_mgr().Wkr_resize_img().Resize_exec(orig_url, thumb_url, html_w, html_h, fsdb_itm.Orig_ext().Id(), tmp_resize_result))
					return false;
			}
			fsdb_itm.Html_size_(html_w, html_h);
			fsdb_itm.Html_view_url_(thumb_url);
			fsdb_itm.Html_orig_url_(orig_url);
			return true;
		}
	}
	public void Root_dir_(Io_url v) {
		Io_url root_dir = v;
		orig_dir = root_dir.GenSubDir("orig");
		thumb_dir = root_dir.GenSubDir("thumb");
		orig_dir_mgr_init(orig_dir);
	}
	public void Orig_dir_(Io_url v) {
		orig_dir = v;
		orig_dir_mgr_init(orig_dir);
	}
	private void orig_dir_mgr_init(Io_url orig_dir) {
		wkr.Init(wiki.Appe().File_mgr().Img_mgr().Wkr_query_img_size(), orig_dir);
	}
	public void Thumb_dir_(Io_url v) {
		thumb_dir = v;
	}
}
