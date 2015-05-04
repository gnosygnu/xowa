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
import gplx.xowa.files.repos.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.bins.*;
import gplx.xowa.parsers.lnkis.*;
public class Xof_lnki_file_mgr {
	private boolean page_init_needed = true;
	private final ListAdp fsdb_list = ListAdp_.new_(); private final OrderedHash orig_regy = OrderedHash_.new_bry_(), fsdb_hash = OrderedHash_.new_bry_(); 
	private final Xof_img_size img_size = new Xof_img_size(); private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_();
	public void Clear() {
		page_init_needed = true;
		fsdb_list.Clear(); fsdb_hash.Clear(); orig_regy.Clear();			
	}
	public boolean Find(Xowe_wiki wiki, Xoae_page page, byte exec_tid, Xof_xfer_itm xfer) {
		try {
			if (page_init_needed) {
				page_init_needed = false;					
				wiki.File_mgr().Init_file_mgr_by_load(wiki);						// NOTE: fsdb_mgr may not be init'd for wiki; assert that that it is
				Make_fsdb_list(page.Lnki_list(), wiki.File_mgr().Patch_upright());	// NOTE: Patch_upright check must occur after Init_by_wiki; DATE:2014-05-31
				wiki.File__orig_mgr().Find_by_list(orig_regy, fsdb_list, exec_tid);
				Make_fsdb_hash();
			}
			Xof_fsdb_itm fsdb = (Xof_fsdb_itm)fsdb_hash.Fetch(xfer.Lnki_ttl());
			xfer.File_exists_n_();
			if (fsdb == null) return false;						// no orig_data found for the ttl
			Xof_repo_itm repo = wiki.File_mgr().Repo_mgr().Repos_get_by_wiki(fsdb.Orig_repo_name()).Trg();
			fsdb.Lnki_size_(xfer.Lnki_w(), xfer.Lnki_h());		// NOTE: must overwrite fsdb.size with xfer.size when the same image shows up in multiple sizes on a page; (only one item in wiki_orig); EX: w:Portal:Canada; [[File:Flag of Canada.svg|300x150px]]; [[File:Flag of Canada.svg|23px]]; DATE:2014-02-14
			fsdb.Ctor_for_html(exec_tid, img_size, repo, url_bldr);
			xfer.Url_bldr_(url_bldr);							// default Url_bldr for xfer uses @ for thumbtime; switch to -; DATE:2014-02-02
			xfer.Trg_repo_(repo);
			xfer.Lnki_ext_(fsdb.Lnki_ext());					// WORKAROUND: hacky, but fsdb knows when ogg is ogv whereas xfer does not; so, always override xfer.ext with fsdb's; DATE:2014-02-02
			xfer.Init_by_orig(fsdb.Orig_repo_id(), fsdb.Orig_repo_name(), fsdb.Orig_ttl(), fsdb.Orig_ext(), fsdb.Orig_w(), fsdb.Orig_h(), fsdb.Orig_redirect(), xfer.Orig_file_len());	// copy orig props from orig_itm to xfer
			xfer.Html_orig_url_(Bry_.new_utf8_(fsdb.Html_orig_url().To_http_file_str()));	// always set orig_url; note that w,h are not necessary for orig url; orig url needed for [[Media:]] links; DATE:2014-01-19				
			gplx.ios.IoItmFil fil = Io_mgr._.QueryFil(fsdb.Html_view_url());
			if (fil.Exists()) {
				if  (fil.Size() == 0)	// NOTE: fix; XOWA used to write 0 byte files if file was missing, delete them and do not return true; DATE:2014-06-21
					Io_mgr._.DeleteFil(fsdb.Html_view_url());
				else {
					xfer.Calc_by_fsdb(fsdb.Html_w(), fsdb.Html_h(), fsdb.Html_view_url(), fsdb.Html_orig_url());
					xfer.File_exists_y_();
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to find img: img=~{0} err=~{1}", String_.new_utf8_(xfer.Lnki_ttl()), Err_.Message_gplx_brief(e));
			return false;
		}
	}
	private void Make_fsdb_list(ListAdp lnki_list, int upright_patch) {
		int len = lnki_list.Count();
		for (int i = 0; i < len; i++) {
			Xop_lnki_tkn lnki_tkn = (Xop_lnki_tkn)lnki_list.FetchAt(i);
			Xof_fsdb_itm fsdb = new Xof_fsdb_itm();
			fsdb.Ctor_by_lnki(lnki_tkn.Ttl().Page_db(), lnki_tkn.Lnki_type(), lnki_tkn.W(), lnki_tkn.H(), upright_patch, lnki_tkn.Upright(), lnki_tkn.Time(), lnki_tkn.Page());
			fsdb_list.Add(fsdb);
		}
	}
	private void Make_fsdb_hash() {
		int len = fsdb_list.Count();
		for (int i = 0; i < len; i++) {
			Xof_fsdb_itm fsdb = (Xof_fsdb_itm)fsdb_list.FetchAt(i);
			Make_fsdb_hash_add(fsdb.Lnki_ttl(), fsdb);
			Make_fsdb_hash_add(fsdb.Orig_ttl(), fsdb);	// redirect
		}
	}
	private void Make_fsdb_hash_add(byte[] key, Xof_fsdb_itm itm) {
		if (	Bry_.Len_gt_0(key)		// ignore null / empty itms; needed for redirects
			&&	!fsdb_hash.Has(key)		// don't add if already there
			&&	orig_regy.Has(key)		// add if found in orig_regy
			)
			fsdb_hash.Add(key, itm);
	}
}
