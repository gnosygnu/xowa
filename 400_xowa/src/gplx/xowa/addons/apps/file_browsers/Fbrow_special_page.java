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
package gplx.xowa.addons.apps.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.xowa.wikis.pages.*;
import gplx.core.ios.*;
public class Fbrow_special_page implements Xows_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		Xopage_html_data html_data = Write_html(wiki.App(), url.Qargs_ary());			
		html_data.Apply(page);
	}
	private static Xopage_html_data Write_html(Xoa_app app, Gfo_qarg_itm[] args_ary) {
		// scan owner_dir
		Xoa_url_arg_mgr arg_mgr = new Xoa_url_arg_mgr(null).Init(args_ary);
		String owner_str = arg_mgr.Read_str_or_null("path"); if (owner_str == null) return Xopage_html_data.err_("url has unknown path");
		IoItmDir owner_dir = Io_mgr.Instance.QueryDir_args(Io_url_.new_any_(owner_str)).DirInclude_(true).ExecAsDir();
		Fbrow_file_itm owner_itm = Fbrow_file_itm.New(owner_dir);

		// write body
		Io_url addon_dir = app.Fsys_mgr().Bin_addon_dir().GenSubDir_nest("import", "file_browser");
		byte[] template_src = Io_mgr.Instance.LoadFilBry(addon_dir.GenSubFil_nest("bin", "file_browser.mustache.html"));
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		byte[] body = gplx.langs.mustaches.Mustache_wtr_.Write_to_bry(tmp_bfr, template_src, owner_itm);

		// write head
		Xopage_html_data rv = new Xopage_html_data(Display_ttl, body);
		rv.Head_tags().Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "file_browser.css")));
		return rv;
	}

	public static final String SPECIAL_KEY = "XowaFileBrowser";
	public static final    byte[] Display_ttl = Bry_.new_a7("Import XOWA Wikis");
	public Xows_special_meta Special__meta() {return new Xows_special_meta(Xows_special_meta_.Src__xowa, SPECIAL_KEY);}
	public static final    Xows_page Prototype = new Fbrow_special_page();
	public Xows_page Special__clone() {return this;}
}
