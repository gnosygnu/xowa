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
package gplx.xowa.wmfs.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.xowa.files.downloads.*;
public class Xoapi_orig_mok extends Xoapi_orig_base {
	private String wiki_str = "", ttl_str = "", redirect_str = ""; private int orig_w, orig_h; private boolean fail = false;
	public Xoapi_orig_mok Ini(String wiki_str, String ttl_str, String redirect_str, int orig_w, int orig_h, boolean pass) {
		this.wiki_str = wiki_str; this.ttl_str = ttl_str; this.redirect_str = redirect_str; this.orig_w = orig_w; this.orig_h = orig_h; this.fail = !pass;
		return this;
	}
	public void Clear() {wiki_str = ttl_str = redirect_str = ""; orig_w = orig_h = 0;}
	@Override public boolean Api_query_size_exec(Xoapi_orig_rslts rv, Xof_download_wkr download_wkr, byte[] ttl, int width, int height, Gfo_usr_dlg usr_dlg, byte[] repo_wiki_key) {
		if (!Bry_.Eq(ttl, Bry_.new_utf8_(ttl_str))) return false;
		if (!String_.Eq(wiki_str, String_.new_ascii_(repo_wiki_key))) return false;
		if (fail) return false;
		byte[] orig_page = String_.Eq(redirect_str, "") ? ttl : Bry_.new_utf8_(redirect_str);
		rv.Init_all(repo_wiki_key, orig_page, orig_w, orig_h);
		return true;
	}
	public static final Xoapi_orig_mok _ = new Xoapi_orig_mok(); Xoapi_orig_mok() {}
}