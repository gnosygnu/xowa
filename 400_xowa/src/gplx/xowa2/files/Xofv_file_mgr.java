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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.wiki_orig.*;
import gplx.xowa2.files.orig_regy.*;
public class Xofv_file_mgr {
	private final Xop_xfer_itm_hash lnki_hash = new Xop_xfer_itm_hash();
	private final Xofv_file_itm_list html_list = new Xofv_file_itm_list();
	private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_(); private final Xof_img_size img_size = new Xof_img_size();
	public Xofv_file_mgr(byte[] wiki_bry) {this.wiki_bry = wiki_bry;}
	public byte[] Wiki_bry() {return wiki_bry;} private final byte[] wiki_bry;
	public Xofv_repo_mgr Repo_mgr() {return repo_mgr;} private final Xofv_repo_mgr repo_mgr = new Xofv_repo_mgr();
	public Xof_orig_mgr Orig_mgr() {return orig_mgr;} public Xofv_file_mgr Orig_mgr_(Xof_orig_mgr v) {orig_mgr = v; return this;} private Xof_orig_mgr orig_mgr;
	public Xof_fsdb_mgr Fsdb_mgr() {return fsdb_mgr;} public Xofv_file_mgr Fsdb_mgr_(Xof_fsdb_mgr v) {fsdb_mgr = v; return this;} private Xof_fsdb_mgr fsdb_mgr;
	public Xou_cache_mgr Cache_mgr() {return cache_mgr;} public Xofv_file_mgr Cache_mgr_(Xou_cache_mgr v) {cache_mgr = v; return this;} private Xou_cache_mgr cache_mgr;
	public void Clear() {lnki_hash.Clear();}
	public void Reg(Xof_xfer_itm xfer_itm) {lnki_hash.Add(xfer_itm);}
	public void Process_lnki() {
		while (true) {
			Xof_xfer_itm xfer = lnki_hash.Pop_at_or_null(0); if (xfer == null) break;	// no more items; stop
			Xof_orig_regy_itm orig = orig_mgr.Get_by_ttl(xfer.Lnki_ttl());
			if (orig == null) {
				Gfo_usr_dlg_._.Warn_many("", "", "orig not found for lnki; lnki_ttl=~{0}", xfer.Lnki_ttl());
				continue;
			}
			boolean fsdb_download = true; // default download to true
			Xofv_file_itm itm = Xofv_file_itm.new_(xfer, orig, repo_mgr, img_size, url_bldr);
			Xou_cache_fil cache_fil = cache_mgr.Fil__get_or_null(itm.File_repo(), itm.File_ttl(), itm.Lnki_is_orig(), itm.Html_w(), itm.Lnki_time(), itm.Lnki_page());
			if (cache_fil != null) {
				if (Io_mgr._.ExistsFil(itm.File_url()))
					fsdb_download = false;	// cache exists and itm exists; don't download file
			}
			if (fsdb_download) {
				if (!fsdb_mgr.Download(itm)) {
					Gfo_usr_dlg_._.Warn_many("", "", "itm not found in fsdb; lnki_ttl=~{0}", xfer.Lnki_ttl());
					continue;
				}
			}
			if (cache_fil == null)
				cache_fil = new Xou_cache_fil(1, 1, itm.File_repo(), itm.File_ttl(), Xof_ext_.new_by_ttl_(itm.File_ttl()).Id(), itm.Lnki_is_orig(), itm.Html_w(), itm.Html_w(), itm.Lnki_time(), itm.Lnki_page(), itm.File_size(), 0);
			cache_mgr.Fil__update(cache_fil);
			html_list.Add(itm);
		}
	}
	@gplx.Internal protected void Process_html(Xog_html_gui html_gui) {
		while (true) {
			Xofv_file_itm itm = html_list.Pop_at_or_null(0); if (itm == null) break;
			html_gui.Update(itm.Html_uid(), itm.File_url().To_http_file_str(), itm.Html_w(), itm.Html_h());
		}
	}
}
