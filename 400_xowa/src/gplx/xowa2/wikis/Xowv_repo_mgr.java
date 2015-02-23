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
import gplx.xowa.files.repos.*;
public class Xowv_repo_mgr implements Xow_repo_mgr {
	private final ListAdp repos = ListAdp_.new_();
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
}
