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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.files.fsdb.*; import gplx.xowa.files.repos.*;
public class Xof_orig_rdr_func {
	public static void Eval(OrderedHash itms_by_ttl, ListAdp itms_all, byte exec_tid, Xof_url_bldr url_bldr, Xow_repo_mgr repo_mgr) {
		Xof_img_size img_size = new Xof_img_size();
		int len = itms_all.Count();
		for (int i = 0; i < len; i++) {
			Xof_fsdb_itm fsdb = (Xof_fsdb_itm)itms_all.FetchAt(i);
			fsdb.Orig_status_(Xof_orig_wkr_.Status_missing_orig);													// default to status = missing
			Xof_orig_itm orig = (Xof_orig_itm)itms_by_ttl.Fetch(fsdb.Lnki_ttl()); if (orig == null) continue;		// not in orig; skip; will do full search later
			fsdb.Orig_status_(orig.Status());
			if (orig.Status() > Xof_orig_wkr_.Status_found) continue;												// only ignore if marked missing; DATE:2014-02-01
			byte repo_id = orig.Repo();
			Xof_repo_itm repo = null;
			if (Xof_repo_itm.Repo_is_known(repo_id)) { // bounds check
				fsdb.Orig_repo_id_(repo_id);
				Xof_repo_pair repo_pair = repo_mgr.Repos_get_by_id(repo_id);
				if (repo_pair == null)	// shouldn't happen, but try to avoid null ref;
					repo_pair = repo_mgr.Repos_get_at(repo_id);
				fsdb.Orig_repo_name_(repo_pair.Wiki_domain());
				repo = repo_pair.Trg();
			}
			fsdb.Lnki_ext_(Xof_ext_.new_by_id_(orig.Ext()));	// overwrite ext with whatever's in file_orig; needed for ogg -> oga / ogv
			fsdb.Orig_size_(orig.W(), orig.H());
			fsdb.Orig_status_(Xof_orig_wkr_.Status_found);
			if (Bry_.Len_gt_0(orig.Redirect()))	// redirect exists;
				fsdb.Ctor_by_orig_redirect(orig.Redirect());
			fsdb.Html_size_calc(img_size, exec_tid);
			Io_url html_url = url_bldr.Init_for_trg_file(fsdb.Lnki_type_as_mode(), repo, fsdb.Lnki_ttl(), fsdb.Lnki_md5(), fsdb.Lnki_ext(), fsdb.Html_w(), fsdb.Lnki_time(), fsdb.Lnki_page()).Xto_url();
			fsdb.Html_view_url_(html_url);
			if (!Io_mgr._.ExistsFil(html_url))
				fsdb.Orig_status_(Xof_orig_wkr_.Status_missing_orig);
			// build url; check if exists;
		}
	}
}
