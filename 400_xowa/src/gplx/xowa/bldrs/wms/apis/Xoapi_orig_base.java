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
package gplx.xowa.bldrs.wms.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.ios.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.downloads.*;
public abstract class Xoapi_orig_base {
	public boolean Api_query_size(Xoapi_orig_rslts rv, Xof_download_wkr download_wkr, Xow_repo_mgr repo_mgr, byte[] ttl, int width, int height) {
		if (!gplx.ios.IoEngine_system.Web_access_enabled) return false;	// don't check api if download disabled else "download_failed" messages in log (particularly during pkg_make) DATE:2015-02-12
		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance;
		Xof_repo_pair[] repos = repo_mgr.Repos_ary();
		int len = repos.length;
		for (int i = 0; i < len; i++) {
			Xof_repo_pair repo_pair = repos[i];
			if (Api_query_size_exec(rv, download_wkr, ttl, width, height, usr_dlg, repo_pair.Wiki_domain())) return true;
		}
		usr_dlg.Warn_many(Xoapi_orig_wmf.GRP_KEY, "download_failed", "download_failed: ~{0}", String_.new_u8(ttl));
		return false;
	}
	public abstract boolean Api_query_size_exec(Xoapi_orig_rslts rv, Xof_download_wkr download_wkr, byte[] ttl, int width, int height, Gfo_usr_dlg usr_dlg, byte[] repo_wiki_key);
}
