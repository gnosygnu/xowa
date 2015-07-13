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
package gplx.xowa2.wikis; import gplx.*; import gplx.xowa2.*;
import gplx.xowa.*; import gplx.xowa.files.repos.*;
public class Xowv_repo_mgr implements Xow_repo_mgr {
	private final List_adp repos = List_adp_.new_();
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
			if (pair.Repo_idx() == id) return pair;
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
//			if (!Bry_.Eq(src_wiki_key, trg_wiki_key) && !Bry_.Eq(src_wiki_key, Xow_domain_type_.Key_bry_home)) throw Err_mgr._.fmt_(GRP_KEY, "add_repo", "wiki keys do not match: ~{0} ~{1}", String_.new_u8(src_wiki_key), String_.new_u8(trg_wiki_key));
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
