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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import gplx.xowa.files.repos.*;
public class Xowv_repo_mgr implements Xow_repo_mgr {
	private final    List_adp repos = List_adp_.New();
	public Xof_repo_pair[]		Repos_ary() {return (Xof_repo_pair[])repos.To_ary(Xof_repo_pair.class);}
	public Xof_repo_pair		Repos_get_by_wiki(byte[] wiki) {
		int len = repos.Count();
		for (int i = 0; i < len; i++) {
			Xof_repo_pair pair = (Xof_repo_pair)repos.Get_at(i);
			if (Bry_.Eq(wiki, pair.Wiki_domain()))
				return pair;
		}
		return null;
	}
	public Xof_repo_pair		Repos_get_at(int i) {return (Xof_repo_pair)repos.Get_at(i);}
	private Xof_repo_pair		Repos_get_by_id(int id) {
		int len = repos.Count();
		for (int i = 0; i < len; i++) {
			Xof_repo_pair pair = (Xof_repo_pair)repos.Get_at(i);
			if (pair.Id() == id) return pair;
		}
		return null;
	}
	public Xof_repo_itm Get_trg_by_id_or_null(int id, byte[] lnki_ttl, byte[] page_url) {
		Xof_repo_pair pair = Repos_get_by_id(id);
		if (pair == null) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "repo_mgr.invalid_repo: repo=~{0} lnki_ttl=~{1} page_url=~{2}", id, lnki_ttl, page_url);
			return null;
		}
		else
			return pair.Trg();
	}
	public Xof_repo_itm Get_src_by_id_or_null(int id, byte[] lnki_ttl, byte[] page_url) {
		Xof_repo_pair pair = Repos_get_by_id(id);
		if (pair == null) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "repo_mgr.invalid_repo: repo=~{0} lnki_ttl=~{1} page_url=~{2}", id, lnki_ttl, page_url);
			return null;
		}
		else
			return pair.Src();
	}
	public Xof_repo_pair Add_repo(Xoa_app app, Io_url wiki_root, byte[] src_repo_key, byte[] trg_repo_key) {
		Xof_repo_itm src_repo = Add(app, wiki_root, src_repo_key), trg_repo = Add(app, wiki_root, trg_repo_key);
		byte[] src_wiki_key = src_repo.Wiki_domain();//, trg_wiki_key = trg_repo.Wiki_key();
//			if (!Bry_.Eq(src_wiki_key, trg_wiki_key) && !Bry_.Eq(src_wiki_key, Xow_domain_tid_.Bry__home)) throw Err_mgr.Instance.fmt_(GRP_KEY, "add_repo", "wiki keys do not match: ~{0} ~{1}", String_.new_u8(src_wiki_key), String_.new_u8(trg_wiki_key));
		Xof_repo_pair pair = new Xof_repo_pair((byte)repos.Count(), src_wiki_key, src_repo, trg_repo);
		repos.Add(pair);
		return pair;
	}
	private Xof_repo_itm Add(Xoa_app app, Io_url wiki_root, byte[] wiki_domain) {
		Xof_repo_itm itm = new Xof_repo_itm(wiki_domain, app.Fsys_mgr(), null, wiki_domain);
		itm.Root_str_(wiki_root.Raw());
		return itm;
	}
}
