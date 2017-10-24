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
package gplx.xowa.apps.wms.apis.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
import gplx.core.ios.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.downloads.*;
public abstract class Xoapi_orig_base {
	public boolean Api_query_size(Xoapi_orig_rslts rv, Xof_download_wkr download_wkr, Xow_repo_mgr repo_mgr, byte[] ttl, int width, int height) {
		if (!gplx.core.ios.IoEngine_system.Web_access_enabled) return false;	// don't check api if download disabled else "download_failed" messages in log (particularly during pkg_make) DATE:2015-02-12
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
