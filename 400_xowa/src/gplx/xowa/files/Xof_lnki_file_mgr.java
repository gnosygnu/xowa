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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.xowa.files.fsdb.*; import gplx.xowa.files.bins.*;
import gplx.xowa.parsers.lnkis.*;
public class Xof_lnki_file_mgr {
	private boolean page_init_needed = true;
	private ListAdp fsdb_list = ListAdp_.new_();
	private OrderedHash xfer_list = OrderedHash_.new_bry_();
	private Xof_url_bldr url_bldr = new Xof_url_bldr().Thumbtime_dlm_dash_();
	private Xof_img_size tmp_img_size = new Xof_img_size();
	public void Clear() {
		page_init_needed = true;
		fsdb_list.Clear();
		xfer_list.Clear();
		orig_regy.Clear();			
	}
	private OrderedHash orig_regy = OrderedHash_.new_bry_();
	public boolean Find(Xow_wiki wiki, Xoa_page page, byte exec_tid, Xof_xfer_itm xfer_itm) {
		try {
			if (page_init_needed) {
				page_init_needed = false;					
				wiki.File_mgr().Fsdb_mgr().Init_by_wiki__add_bin_wkrs(wiki);	// NOTE: fsdb_mgr may not be init'd for wiki; assert that that it is
				Create_xfer_itms(page.Lnki_list(), wiki.File_mgr().Fsdb_mgr().Patch_upright());	// NOTE: Patch_upright check must occur after Init_by_wiki; DATE:2014-05-31
				wiki.File_mgr().Fsdb_mgr().Reg_select_only(page, exec_tid, fsdb_list, orig_regy);
				Hash_xfer_itms();
			}
			Xof_fsdb_itm fsdb_itm = (Xof_fsdb_itm)xfer_list.Fetch(xfer_itm.Lnki_ttl());
			if (fsdb_itm == null)	// no orig_data found for the current item
				return false;
			else {
				if (fsdb_itm.Orig_wiki() == null) return false;		// itm not found; return now, else null exception later;
				xfer_itm.Lnki_ext_(fsdb_itm.Lnki_ext());			// WORKAROUND: hacky, but fsdb_itm knows when ogg is ogv whereas xfer_itm does not; so, always override xfer_itm.ext with fsdb's; DATE:2014-02-02
				xfer_itm.Url_bldr_(url_bldr);						// default Url_bldr for xfer_itm uses @ for thumbtime; switch to -; DATE:2014-02-02
				Init_fsdb_by_xfer(fsdb_itm, xfer_itm);				// copy xfer itm props to fsdb_itm;
				fsdb_itm.Html__init(wiki.File_mgr().Repo_mgr(), url_bldr, tmp_img_size, exec_tid);
				xfer_itm.Html_orig_src_(Bry_.new_utf8_(fsdb_itm.Html_orig_url().To_http_file_str()));	// always set orig_url; note that w,h are not necessary for orig url; orig url needed for [[Media:]] links; DATE:2014-01-19
				gplx.ios.IoItmFil fil = Io_mgr._.QueryFil(fsdb_itm.Html_url());
				if (fil.Exists()) {
					if  (fil.Size() == 0)	// NOTE: fix; XOWA used to write 0 byte files if file was missing, delete them and do not return true; DATE:2014-06-21
						Io_mgr._.DeleteFil(fsdb_itm.Html_url());
					else {
						xfer_itm.Atrs_calc_by_fsdb(fsdb_itm.Html_w(), fsdb_itm.Html_h(), fsdb_itm.Html_url(), fsdb_itm.Html_orig_url());
						return true;
					}
				}
				// TODO: replace above with this block; WHEN: mocking file database tests; DATE:2014-05-03
				// boolean found = Io_mgr._.ExistsFil(fsdb_itm.Html_url());
				// Io_url html_url = found ? fsdb_itm.Html_url() : null;
				// xfer_itm.Atrs_calc_by_fsdb(fsdb_itm.Html_w(), fsdb_itm.Html_h(), html_url, fsdb_itm.Html_orig_url());
				// return found;
			}
			return false;
		} catch (Exception e) {
			wiki.App().Usr_dlg().Warn_many("", "", "failed to find img: img=~{0} err=~{1}", String_.new_utf8_(xfer_itm.Lnki_ttl()), Err_.Message_gplx_brief(e));
			return false;
		}
	}
	private void Create_xfer_itms(ListAdp lnki_list, boolean upright_patch) {
		int len = lnki_list.Count();
		for (int i = 0; i < len; i++) {
			Xop_lnki_tkn lnki_tkn = (Xop_lnki_tkn)lnki_list.FetchAt(i);
			Xof_fsdb_itm fsdb_itm = new Xof_fsdb_itm();
			Init_fsdb_by_lnki(fsdb_itm, lnki_tkn, upright_patch);
			fsdb_list.Add(fsdb_itm);
		}
	}
	private void Hash_xfer_itms() {
		int len = fsdb_list.Count();
		for (int i = 0; i < len; i++) {
			Xof_fsdb_itm fsdb_itm = (Xof_fsdb_itm)fsdb_list.FetchAt(i);
			Hash_xfer_itms_add(fsdb_itm.Lnki_ttl(), fsdb_itm);
			Hash_xfer_itms_add(fsdb_itm.Orig_ttl(), fsdb_itm);	// redirect
		}
	}
	private void Hash_xfer_itms_add(byte[] key, Xof_fsdb_itm itm) {
		if (	Bry_.Len_gt_0(key)	// ignore null / empty itms; needed for redirects
			&&	!xfer_list.Has(key)		// don't add if already there
			&&	orig_regy.Has(key)		// add if found in orig_regy
			)
			xfer_list.Add(key, itm);
	}
	private void Init_fsdb_by_lnki(Xof_fsdb_itm fsdb_itm, Xop_lnki_tkn lnki_tkn, boolean lnki_upright_patch) {
		byte[] lnki_ttl = lnki_tkn.Ttl().Page_db();
		Xof_ext lnki_ext = Xof_ext_.new_by_ttl_(lnki_ttl);
		byte[] lnki_md5 = Xof_xfer_itm_.Md5_(lnki_ttl);
		fsdb_itm.Init_by_lnki(lnki_ttl, lnki_ext, lnki_md5, lnki_tkn.Lnki_type(), lnki_tkn.Lnki_w(), lnki_tkn.Lnki_h(), lnki_upright_patch, lnki_tkn.Upright(), lnki_tkn.Thumbtime(), lnki_tkn.Page());
	}
	private void Init_fsdb_by_xfer(Xof_fsdb_itm fsdb_itm, Xof_xfer_itm xfer_itm) {	// DELETE: DATE:2014-02-04
		fsdb_itm.Lnki_size_(xfer_itm.Lnki_w(), xfer_itm.Lnki_h());	// NOTE: must overwrite fsdb_itm.size with xfer_itm.size when the same image shows up in multiple sizes on a page; (only one item in wiki_orig); EX: w:Portal:Canada; [[File:Flag of Canada.svg|300x150px]]; [[File:Flag of Canada.svg|23px]]; DATE:2014-02-14
		fsdb_itm.Lnki_type_(xfer_itm.Lnki_type());					// NOTE: must overwrite lnki_type, else multiple images on same page with different type wont show; EX:en.w:History_of_painting; DATE:2014-03-06
		fsdb_itm.Lnki_page_(xfer_itm.Lnki_page());
		fsdb_itm.Lnki_thumbtime_(xfer_itm.Lnki_thumbtime());
//			byte[] lnki_ttl = xfer_itm.Lnki_ttl();
//			Xof_ext lnki_ext = xfer_itm.Lnki_ext();
//			byte[] lnki_md5 = Xof_xfer_itm_.Md5_(lnki_ttl);
//			fsdb_itm.Init_by_lnki(lnki_ttl, lnki_ext, lnki_md5, xfer_itm.Lnki_type(), xfer_itm.Lnki_w(), xfer_itm.Lnki_h(), xfer_itm.Lnki_upright(), xfer_itm.Lnki_thumbtime(), xfer_itm.Lnki_page());
	}
}
