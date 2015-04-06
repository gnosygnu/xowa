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
package gplx.xowa; import gplx.*;
import gplx.xowa.wikis.*; import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.wikis.data.tbls.*;
public class Xofw_wiki_wkr_base implements Xofw_wiki_finder {
	public Xofw_wiki_wkr_base(Xowe_wiki wiki, Xoa_wiki_mgr wiki_mgr) {this.wiki = wiki; this.wiki_mgr = wiki_mgr;} private Xowe_wiki wiki; Xoa_wiki_mgr wiki_mgr;
	public void Find(ListAdp repo_pairs, Xof_xfer_itm file) {
		byte[] ttl_bry = file.Lnki_ttl();
		int repo_pairs_len = repo_pairs.Count();
		for (int i = 0; i < repo_pairs_len; i++) {
			Xof_repo_pair repo_pair = (Xof_repo_pair)repo_pairs.FetchAt(i);
			byte[] wiki_key = repo_pair.Src().Wiki_key();
			if (repo_pair.Src().Wmf_api()) continue;
			Xowe_wiki repo_wiki = wiki_mgr.Get_by_key_or_null(wiki_key);
			if (repo_wiki == null) {continue;}
			Xoa_ttl ttl = Xoa_ttl.parse_(repo_wiki, ttl_bry);
			Xow_ns file_ns = repo_wiki.Ns_mgr().Ns_file();
			boolean found = repo_wiki.Db_mgr().Load_mgr().Load_by_ttl(tmp_db_page, file_ns, ttl.Page_db());
			if (!found) {continue;}
			byte[] redirect = Get_redirect(repo_wiki, file_ns, tmp_db_page);
			file.Set__ttl(ttl.Page_txt(), redirect);
			file.Trg_repo_idx_(i);
			return;
		}
		file.Trg_repo_idx_(-1);
	}
	public boolean Locate(Xofw_file_finder_rslt rv, ListAdp repo_pairs, byte[] ttl_bry) {
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, ttl_bry);	// NOTE: parse_(ttl_bry) should be the same across all wikis; i.e.: there should be no aliases/namespaces
		Xow_ns file_ns = wiki.Ns_mgr().Ns_file();		// NOTE: file_ns should also be the same across all wikis; being used for data_mgr.Parse below
		byte[] ttl_db_key = ttl.Page_db();
		rv.Init(ttl_db_key);
		int repo_pairs_len = repo_pairs.Count();
		for (int i = 0; i < repo_pairs_len; i++) {
			Xof_repo_pair repo_pair = (Xof_repo_pair)repo_pairs.FetchAt(i);
			byte[] src_wiki_key = repo_pair.Src().Wiki_key();
			Xowe_wiki src_wiki = wiki_mgr.Get_by_key_or_null(src_wiki_key);
			if (src_wiki == null) continue;		// src_wiki defined as repo_pair in cfg, but it has not been downloaded; continue; EX: commons set up but not downloaded
			boolean found = src_wiki.Db_mgr().Load_mgr().Load_by_ttl(tmp_db_page, file_ns, ttl_db_key);
			if (!found) continue;				// ttl does not exist in src_wiki; continue; EX: file does not exist in commons, but exists in en_wiki
			byte[] redirect = Get_redirect(src_wiki, file_ns, tmp_db_page);
			rv.Done(i, src_wiki_key, redirect);
			return true;
		}
		return false;
	}	static final Xowd_page_itm tmp_db_page = Xowd_page_itm.new_tmp();
	byte[] Get_redirect(Xowe_wiki wiki, Xow_ns file_ns, Xowd_page_itm db_page) {
		if (db_page.Redirected()) {
			wiki.Db_mgr().Load_mgr().Load_page(db_page, file_ns, false);
			byte[] src = db_page.Text();
			Xoa_ttl redirect_ttl = wiki.Redirect_mgr().Extract_redirect(src, src.length);
			return redirect_ttl == Xop_redirect_mgr.Redirect_null_ttl ? Xop_redirect_mgr.Redirect_null_bry : redirect_ttl.Page_db();
		}
		else
			return Xop_redirect_mgr.Redirect_null_bry;
	}
}
