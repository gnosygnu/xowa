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
package gplx.xowa.wikis.pages.tags; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.guis.cbks.*; import gplx.core.gfobjs.*;
public class Xopg_alertify_ {
	public static void Add_tags(Xopg_tag_mgr head_tags, Io_url http_root) {
		Io_url alertify_root = http_root.GenSubDir_nest("bin", "any", "xowa", "html", "res", "lib", "alertify");
		head_tags.Add(Xopg_tag_itm.New_css_file(alertify_root.GenSubFil_nest("themes", "alertify.core.css")));
		head_tags.Add(Xopg_tag_itm.New_css_file(alertify_root.GenSubFil_nest("themes", "alertify.bootstrap.css")));
		head_tags.Add(Xopg_tag_itm.New_js_file(alertify_root.GenSubFil_nest("lib", "alertify-0.3.11.js")));
		head_tags.Add(Xopg_tag_itm.New_js_file(alertify_root.GenSubFil_nest("lib", "xo-alertify-0.0.1.js")));
	}
	public static void Exec_log(Xog_json_wkr wkr, String msg) {
		wkr.Send_json("xo.alertify.log_by_str", Gfobj_nde.New().Add_str("msg", msg));
	}
}
