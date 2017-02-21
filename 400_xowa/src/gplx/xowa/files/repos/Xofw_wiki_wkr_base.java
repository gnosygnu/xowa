/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.*; import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.parsers.utils.*;
public class Xofw_wiki_wkr_base implements Xofw_wiki_finder {
	public Xofw_wiki_wkr_base(Xowe_wiki wiki, Xoae_wiki_mgr wiki_mgr) {this.wiki = wiki; this.wiki_mgr = wiki_mgr;} private Xowe_wiki wiki; Xoae_wiki_mgr wiki_mgr;
	public void Find(List_adp repo_pairs, Xof_xfer_itm file) {
		byte[] ttl_bry = file.Lnki_ttl();
		int repo_pairs_len = repo_pairs.Count();
		for (int i = 0; i < repo_pairs_len; i++) {
			Xof_repo_pair repo_pair = (Xof_repo_pair)repo_pairs.Get_at(i);
			byte[] wiki_key = repo_pair.Src().Wiki_domain();
			if (repo_pair.Src().Wmf_api()) continue;
			Xowe_wiki repo_wiki = (Xowe_wiki)wiki_mgr.Get_by_or_null(wiki_key); if (repo_wiki == null) {continue;}
			Xoa_ttl ttl = Xoa_ttl.Parse(repo_wiki, ttl_bry);
			Xow_ns file_ns = repo_wiki.Ns_mgr().Ns_file();
			boolean found = repo_wiki.Db_mgr().Load_mgr().Load_by_ttl(tmp_db_page, file_ns, ttl.Page_db());
			if (!found) {continue;}
			byte[] redirect = Get_redirect(repo_wiki, file_ns, tmp_db_page);
			file.Orig_ttl_and_redirect_(ttl.Page_txt(), redirect);
			file.Orig_repo_id_(i);
			return;
		}
		file.Orig_repo_id_(-1);
	}
	public boolean Locate(Xofw_file_finder_rslt rv, List_adp repo_pairs, byte[] ttl_bry) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);	// NOTE: parse(ttl_bry) should be the same across all wikis; i.e.: there should be no aliases/namespaces
		Xow_ns file_ns = wiki.Ns_mgr().Ns_file();		// NOTE: file_ns should also be the same across all wikis; being used for data_mgr.Parse below
		byte[] ttl_db_key = ttl.Page_db();
		rv.Init(ttl_db_key);
		int repo_pairs_len = repo_pairs.Count();
		for (int i = 0; i < repo_pairs_len; i++) {
			Xof_repo_pair repo_pair = (Xof_repo_pair)repo_pairs.Get_at(i);
			byte[] src_wiki_key = repo_pair.Src().Wiki_domain();
			Xowe_wiki src_wiki = (Xowe_wiki)wiki_mgr.Get_by_or_null(src_wiki_key); if (src_wiki == null) continue;	 // src_wiki defined as repo_pair in cfg, but it has not been downloaded; continue; EX: commons set up but not downloaded
			boolean found = src_wiki.Db_mgr().Load_mgr().Load_by_ttl(tmp_db_page, file_ns, ttl_db_key);
			if (!found) continue;				// ttl does not exist in src_wiki; continue; EX: file does not exist in commons, but exists in en_wiki
			byte[] redirect = Get_redirect(src_wiki, file_ns, tmp_db_page);
			rv.Done(i, src_wiki_key, redirect);
			return true;
		}
		return false;
	}
	private byte[] Get_redirect(Xowe_wiki wiki, Xow_ns file_ns, Xowd_page_itm db_page) {
		if (db_page.Redirected()) {
			wiki.Db_mgr().Load_mgr().Load_page(db_page, file_ns);
			byte[] src = db_page.Text();
			Xoa_ttl redirect_ttl = wiki.Redirect_mgr().Extract_redirect(src);
			return redirect_ttl == Xop_redirect_mgr.Redirect_null_ttl ? Xop_redirect_mgr.Redirect_null_bry : redirect_ttl.Page_db();
		}
		else
			return Xop_redirect_mgr.Redirect_null_bry;
	}
	private static final    Xowd_page_itm tmp_db_page = Xowd_page_itm.new_tmp();
}
