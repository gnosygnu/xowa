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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.ios.*;
import gplx.xowa.files.origs.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.gui.*;
public class Xou_file_itm_finder {
	private final Xou_cache_mgr cache_mgr; private final Xof_img_size img_size = new Xof_img_size(); private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public Xou_file_itm_finder(Xou_cache_mgr cache_mgr) {this.cache_mgr = cache_mgr;}
	public boolean Find(Xowe_wiki wiki, int exec_tid, Xof_file_itm xfer, byte[] page_url) {
		byte[] lnki_ttl = xfer.Lnki_ttl();
		try {
			if (wiki.File__fsdb_mode().Tid_v2_bld()) return false;	// disable during build
			Xou_cache_itm cache_itm = cache_mgr.Get_or_null(wiki.Domain_itm().Abrv_xo(), lnki_ttl, xfer.Lnki_type(), xfer.Lnki_upright(), xfer.Lnki_w(), xfer.Lnki_h(), xfer.Lnki_time(), xfer.Lnki_page(), Xof_img_size.Thumb_width_img);
			Xof_repo_itm repo = null;
			if (cache_itm == null) {// itm not in cache;
				Xof_ext lnki_ext = Xof_ext_.new_by_ttl_(lnki_ttl);
				if (lnki_ext.Id_is_ogg()) {	// look up orig; needed for identifying .ogg to vid for html_wtr to write; PAGE:en.w:WWI; DATE:2015-05-19
					Xof_orig_itm orig = wiki.File__orig_mgr().Find_by_ttl_or_null(lnki_ttl);
					if (orig != Xof_orig_itm.Null) {	// orig found
						repo = wiki.File__repo_mgr().Get_trg_by_id_or_null(orig.Repo(), lnki_ttl, page_url);
						if (repo != null) {
							xfer.Init_at_orig(orig.Repo(), repo.Wiki_domain(), orig.Ttl(), orig.Ext(), orig.W(), orig.H(), orig.Redirect());
							img_size.Html_size_calc(exec_tid, xfer.Lnki_w(), xfer.Lnki_h(), (byte)xfer.Lnki_type(), Xof_patch_upright_tid_.Tid_all, xfer.Lnki_upright(), orig.Ext().Id(), orig.W(), orig.H(), Xof_img_size.Thumb_width_img);	// calc size for html
							xfer.Init_at_gallery_end(img_size.Html_w(), img_size.Html_h(), url_bldr.To_url_trg(repo, xfer, Bool_.N), url_bldr.To_url_trg(repo, xfer, Bool_.Y));
						}
					}
				}
				return false;
			}
			repo = wiki.File__repo_mgr().Get_trg_by_id_or_null(cache_itm.Orig_repo_id(), lnki_ttl, page_url);
			if (repo == null) return false;	// unknown repo; shouldn't happen, but exit, else null ref
			xfer.Init_at_orig((byte)cache_itm.Orig_repo_id(), repo.Wiki_domain(), cache_itm.Orig_ttl(), cache_itm.Orig_ext_itm(), cache_itm.Orig_w(), cache_itm.Orig_h(), Bry_.Empty);
//				img_size.Html_size_calc(exec_tid, xfer.Lnki_w(), xfer.Lnki_h(), (byte)xfer.Lnki_type(), Xof_patch_upright_tid_.Tid_all, xfer.Lnki_upright(), cache_itm.Orig_ext_id(), cache_itm.Orig_w(), cache_itm.Orig_h(), Xof_img_size.Thumb_width_img);
//				xfer.Init_at_gallery_end(img_size.Html_w(), img_size.Html_h(), url_bldr.To_url_trg(repo, cache_itm, Bool_.N), url_bldr.To_url_trg(repo, cache_itm, Bool_.Y));
			xfer.Init_at_html(exec_tid, img_size, repo, url_bldr);
			if (Io_mgr.Instance.ExistsFil(xfer.Html_view_url())) {
				cache_itm.Update_view_stats();
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to find img: img=~{0} err=~{1}", lnki_ttl, Err_.Message_gplx_log(e));
			return false;
		}
	}
}
