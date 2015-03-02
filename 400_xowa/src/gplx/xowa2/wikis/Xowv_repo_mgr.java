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
	private final ListAdp repos = ListAdp_.new_();
	public Xof_repo_pair[]		Repos_ary() {return (Xof_repo_pair[])repos.Xto_ary(Xof_repo_pair.class);}
	public Xof_repo_pair		Repos_get_by_wiki(byte[] wiki) {
		int len = repos.Count();
		for (int i = 0; i < len; i++) {
			Xof_repo_pair pair = (Xof_repo_pair)repos.FetchAt(i);
			if (Bry_.Eq(wiki, pair.Wiki_domain()))
				return pair;
		}
		return null;
	}
	public Xof_repo_pair		Repos_get_at(int i) {return (Xof_repo_pair)repos.FetchAt(i);}
	public Xof_repo_pair		Repos_get_by_id(int id) {
		int len = repos.Count();
		for (int i = 0; i < len; i++) {
			Xof_repo_pair pair = (Xof_repo_pair)repos.FetchAt(i);
			if (pair.Repo_idx() == id) return pair;
		}
		return null;
	}
	public Xof_repo_pair Add_repo(Xoa_app app, byte[] src_repo_key, byte[] trg_repo_key) {
		Xof_repo_itm src_repo = Add(app, src_repo_key), trg_repo = Add(app, trg_repo_key);
		byte[] src_wiki_key = src_repo.Wiki_key();//, trg_wiki_key = trg_repo.Wiki_key();
//			if (!Bry_.Eq(src_wiki_key, trg_wiki_key) && !Bry_.Eq(src_wiki_key, Xow_domain_.Tid_bry_home)) throw Err_mgr._.fmt_(GRP_KEY, "add_repo", "wiki keys do not match: ~{0} ~{1}", String_.new_utf8_(src_wiki_key), String_.new_utf8_(trg_wiki_key));
		Xof_repo_pair pair = new Xof_repo_pair((byte)repos.Count(), src_wiki_key, src_repo, trg_repo);
		repos.Add(pair);
		return pair;
	}
	private Xof_repo_itm Add(Xoa_app app, byte[] key) {
//			Xof_repo_itm itm = (Xof_repo_itm)hash.Fetch(key_bry);
//			if (itm == null) {
			Xof_repo_itm itm = new Xof_repo_itm(key, app.Fsys_mgr(), null);
		String url_str = app.Fsys_mgr().File_dir().GenSubDir(String_.new_utf8_(key)).Raw();
//				this.Add(itm);
//			}
		itm.Root_str_(url_str).Wiki_key_(key);
		return itm;
	}
}
