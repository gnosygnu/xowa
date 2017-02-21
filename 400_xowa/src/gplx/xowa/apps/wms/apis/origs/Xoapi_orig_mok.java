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
import gplx.xowa.files.downloads.*;
public class Xoapi_orig_mok extends Xoapi_orig_base {
	private String wiki_str = "", ttl_str = "", redirect_str = ""; private int orig_w, orig_h; private boolean fail = false;
	public Xoapi_orig_mok Ini(String wiki_str, String ttl_str, String redirect_str, int orig_w, int orig_h, boolean pass) {
		this.wiki_str = wiki_str; this.ttl_str = ttl_str; this.redirect_str = redirect_str; this.orig_w = orig_w; this.orig_h = orig_h; this.fail = !pass;
		return this;
	}
	public void Clear() {wiki_str = ttl_str = redirect_str = ""; orig_w = orig_h = 0;}
	@Override public boolean Api_query_size_exec(Xoapi_orig_rslts rv, Xof_download_wkr download_wkr, byte[] ttl, int width, int height, Gfo_usr_dlg usr_dlg, byte[] repo_wiki_key) {
		if (!Bry_.Eq(ttl, Bry_.new_u8(ttl_str))) return false;
		if (!String_.Eq(wiki_str, String_.new_a7(repo_wiki_key))) return false;
		if (fail) return false;
		byte[] orig_page = String_.Eq(redirect_str, "") ? ttl : Bry_.new_u8(redirect_str);
		rv.Init_all(repo_wiki_key, orig_page, orig_w, orig_h);
		return true;
	}
	public static final    Xoapi_orig_mok Instance = new Xoapi_orig_mok(); Xoapi_orig_mok() {}
}