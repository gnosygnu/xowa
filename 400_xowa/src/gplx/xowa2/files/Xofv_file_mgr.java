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
import gplx.dbs.*;
import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.caches.*;
import gplx.xowa.files.origs.*; import gplx.xowa.wmfs.apis.*;
public class Xofv_file_mgr {
	private final Xop_xfer_itm_hash lnki_hash = new Xop_xfer_itm_hash();
	private final Xofv_file_itm_list html_list = new Xofv_file_itm_list();
	private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_(); private final Xof_img_size img_size = new Xof_img_size();
	public Xofv_file_mgr(byte[] wiki_bry) {
		this.wiki_bry = wiki_bry;
		this.cache_mgr = new Xof_cache_mgr(Gfo_usr_dlg_._, null, null);
	}
	public byte[] Wiki_bry() {return wiki_bry;} private final byte[] wiki_bry;
	public Xofv_repo_mgr Repo_mgr() {return repo_mgr;} private final Xofv_repo_mgr repo_mgr = new Xofv_repo_mgr();
	public Xof_orig_wkr__orig_db Orig_wkr() {return orig_wkr;} private Xof_orig_wkr__orig_db orig_wkr = new Xof_orig_wkr__orig_db();
	public Xof_fsdb_mgr Fsdb_mgr() {return fsdb_mgr;} public Xofv_file_mgr Fsdb_mgr_(Xof_fsdb_mgr v) {fsdb_mgr = v; return this;} private Xof_fsdb_mgr fsdb_mgr;
	public Xof_cache_mgr Cache_mgr() {return cache_mgr;} private final Xof_cache_mgr cache_mgr;
	public void Clear() {lnki_hash.Clear();}
	public void Reg(Xof_xfer_itm xfer_itm) {lnki_hash.Add(xfer_itm);}
	public void Process_lnki() {
		while (true) {
			Xof_xfer_itm xfer = lnki_hash.Pop_at_or_null(0); if (xfer == null) break;	// no more items; stop
			Xof_orig_itm orig = orig_wkr.Find_as_itm(xfer.Lnki_ttl());
			if (orig == Xof_orig_itm.Null) {
				Gfo_usr_dlg_._.Warn_many("", "", "orig not found for lnki; lnki_ttl=~{0}", xfer.Lnki_ttl());
				continue;
			}
			boolean fsdb_download = true; // default download to true
			Xofv_file_itm itm = Xofv_file_itm.new_(xfer, orig, repo_mgr, img_size, url_bldr);
			Xofc_fil_itm cache_fil = cache_mgr.Fil__get_or_null(itm.File_repo(), itm.File_ttl(), itm.Lnki_is_orig(), itm.Html_w(), itm.Lnki_time(), itm.Lnki_page());
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
			if (cache_fil == null) {
				cache_fil = cache_mgr.Fil__make(itm.File_repo(), itm.File_ttl(), itm.Lnki_is_orig(), itm.Html_w(), itm.Html_w(), itm.Lnki_time(), itm.Lnki_page(), itm.File_size());
			}
			cache_mgr.Fil__update(cache_fil);
			html_list.Add(itm);
		}
		cache_mgr.Db_save();
	}
	@gplx.Internal protected void Process_html(Xog_html_gui html_gui) {
		while (true) {
			Xofv_file_itm itm = html_list.Pop_at_or_null(0); if (itm == null) break;
			html_gui.Update(itm.Html_uid(), itm.File_url().To_http_file_str(), itm.Html_w(), itm.Html_h());
		}
	}
}
