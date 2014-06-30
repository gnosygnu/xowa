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
public class Xof_img_wkr_api_size_base_mok extends Xof_img_wkr_api_size_base {
	public Xof_img_wkr_api_size_base_mok Ini(String wiki_str, String ttl_str, String redirect_str, int orig_w, int orig_h, boolean pass) {
		this.wiki_str = wiki_str; this.ttl_str = ttl_str; this.redirect_str = redirect_str; this.orig_w = orig_w; this.orig_h = orig_h; this.fail = !pass;
		return this;
	}	String wiki_str = ""; String ttl_str = ""; String redirect_str = ""; int orig_w; int orig_h; boolean fail = false;
	public void Clear() {wiki_str = ttl_str = redirect_str = ""; orig_w = orig_h = 0;}
	@Override public boolean Api_query_size_exec(Xof_img_wkr_api_size_base_rslts rv, Xow_wiki wiki, byte[] ttl, int width, int height, Gfo_usr_dlg usr_dlg, byte[] repo_wiki_key) {
		if (!Bry_.Eq(ttl, Bry_.new_utf8_(ttl_str))) return false;
		if (!String_.Eq(wiki_str, String_.new_ascii_(repo_wiki_key))) return false;
		if (fail) return false;
		rv.Orig_w_(orig_w);
		rv.Orig_h_(orig_h);
		rv.Reg_wiki_(repo_wiki_key);
		rv.Reg_page_(String_.Eq(redirect_str, "") ? ttl : Bry_.new_utf8_(redirect_str));
		return true;
	}
	public static final Xof_img_wkr_api_size_base_mok _ = new Xof_img_wkr_api_size_base_mok(); Xof_img_wkr_api_size_base_mok() {}
}