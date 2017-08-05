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
	public static void Exec_log(Xog_json_wkr wkr, String msg, int wait) {
		wkr.Send_json("xo.alertify.log_by_str", Gfobj_nde.New().Add_str("msg", msg).Add_str("wait", Int_.To_str(wait * 1000)));
	}
}
