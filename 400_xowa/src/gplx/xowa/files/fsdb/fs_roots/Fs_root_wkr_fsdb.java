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
public class Fs_root_wkr_fsdb {
	private Xowe_wiki wiki;
	private Io_url orig_dir, thumb_dir;
	private Fs_root_dir orig_dir_mgr = new Fs_root_dir();
	private String_obj_ref tmp_resize_result = String_obj_ref.null_();
	private Xof_img_size img_size = new Xof_img_size();
	public Fs_root_wkr_fsdb(Xowe_wiki wiki) {this.wiki = wiki;}
	public boolean Find_file(Xof_fsdb_itm fsdb_itm) {
		byte[] orig_ttl = fsdb_itm.Orig_ttl();
		Orig_fil_itm orig_itm = orig_dir_mgr.Get_by_ttl(orig_ttl);
		if (orig_itm == Orig_fil_itm.Null) return false;
		Io_url orig_url = orig_itm.Fil_url();
		if (fsdb_itm.File_is_orig()) {
			fsdb_itm.Html_size_(orig_itm.Fil_w(), orig_itm.Fil_h());
			fsdb_itm.Html_view_url_(orig_url);
			fsdb_itm.Html_orig_url_(orig_url);
			return true;
		}
		else {
			String thumb_rel = orig_url.GenRelUrl_orEmpty(orig_dir);
			int upright_patch = wiki.File_mgr().Patch_upright();
			img_size.Html_size_calc(fsdb_itm.Lnki_exec_tid(), fsdb_itm.Lnki_w(), fsdb_itm.Lnki_h(), fsdb_itm.Lnki_type(), upright_patch, fsdb_itm.Lnki_upright(), fsdb_itm.Orig_ext().Id(), orig_itm.Fil_w(), orig_itm.Fil_h(), Xof_img_size.Thumb_width_img);
			int html_w = img_size.Html_w(), html_h = img_size.Html_h();
			String thumb_name = Int_.Xto_str(html_w) + orig_url.Ext();
			Io_url thumb_url = thumb_dir.GenSubFil_ary(thumb_rel + orig_url.Info().DirSpr(), thumb_name);
			if (!Io_mgr.I.ExistsFil(thumb_url)) {
				if (!wiki.Appe().File_mgr().Img_mgr().Wkr_resize_img().Exec(orig_url, thumb_url, html_w, html_h, fsdb_itm.Orig_ext().Id(), tmp_resize_result))
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
		orig_dir_mgr.Init(orig_dir, new Orig_fil_tbl(), wiki.Appe().Usr_dlg(), wiki.Appe().File_mgr().Img_mgr().Wkr_query_img_size());
	}
	public void Thumb_dir_(Io_url v) {
		thumb_dir = v;
	}
}
