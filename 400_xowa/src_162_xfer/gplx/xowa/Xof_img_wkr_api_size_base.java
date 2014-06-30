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
import gplx.ios.*;
public abstract class Xof_img_wkr_api_size_base {
	public boolean Api_query_size(Xof_img_wkr_api_size_base_rslts rv, Xow_wiki wiki, byte[] ttl, int width, int height) {
		Gfo_usr_dlg usr_dlg = wiki.App().Usr_dlg();
		Xow_repo_mgr repo_mgr = wiki.File_mgr().Repo_mgr();
		int len = repo_mgr.Repos_len();
		for (int i = 0; i < len; i++) {
			Xof_repo_pair repo_pair = repo_mgr.Repos_get_at(i);
			if (Api_query_size_exec(rv, wiki, ttl, width, height, usr_dlg, repo_pair.Wiki_key())) return true;
		}
		usr_dlg.Warn_many(Xof_img_wkr_api_size_base_wmf.GRP_KEY, "download_failed", "download_failed: ~{0}", String_.new_utf8_(ttl));
		return false;
	}
	public abstract boolean Api_query_size_exec(Xof_img_wkr_api_size_base_rslts rv, Xow_wiki wiki, byte[] ttl, int width, int height, Gfo_usr_dlg usr_dlg, byte[] repo_wiki_key);
}
