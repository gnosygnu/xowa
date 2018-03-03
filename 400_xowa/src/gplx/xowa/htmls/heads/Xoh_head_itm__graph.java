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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.guis.*;
import gplx.xowa.wikis.pages.tags.*;
public class Xoh_head_itm__graph extends Xoh_head_itm__base {
	private boolean version_is_1, version_is_2;
	@Override public byte[] Key() {return Xoh_head_itm_.Key__graph;}
	@Override public int Flags() {return Flag__js_include | Flag__js_window_onload;}
	public void Version_(Xoa_url url, int v) {
		if (v == 1)
			version_is_1 = true;
		else
			version_is_2 = true;
		if (version_is_1 && version_is_2)
			Gfo_usr_dlg_.Instance.Warn_many("", "", "page should not have both version 1 and version 2 graphs; page=~{0}", url.To_bry_full_wo_qargs());
	}
	@Override public void Write_js_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		// collect tags
		Io_url http_root = app.Fsys_mgr().Http_root();
		Xopg_tag_mgr tags = new Xopg_tag_mgr(true);
		Xopg_tag_wtr_.Add__jquery(tags, http_root);
		Xopg_tag_wtr_.Add__xologger(tags, http_root);
		Xopg_tag_wtr_.Add__xolog(tags, http_root, false);
		Xopg_tag_wtr_.Add__xoajax(tags, http_root, app);
		Add__xograph(tags, http_root, page, version_is_1);

		// write tags
		int len = tags.Len();
		for (int i = 0; i < len; i++) {
			Xopg_tag_itm tag = tags.Get_at(i);
			Io_url tag_url = Io_url_.New__http_or_fail(tag.Href());
			wtr.Write_js_include(tag_url);
		}
	}
	@Override public void Write_js_window_onload(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_line(Js__graph_exec);
	}

	private static final    byte[] Js__graph_exec = Bry_.new_a7("xo.xtns.graph.exec();");
	private static void Add__xograph(Xopg_tag_mgr tags, Io_url http_root, Xoae_page page, boolean version_is_1) {
		Io_url base_dir = http_root.GenSubDir_nest("bin", "any", "xowa", "html", "res", "src", "xowa", "core");
		tags.Add(Xopg_tag_itm.New_js_file(base_dir.GenSubFil_nest("Html_.js")));

		// REF: /graph/extension.json/ext.graph.vega1|ext.graph.vega2
		Io_url xtn_dir = http_root.GenSubDir_nest("bin", "any", "xowa", "xtns", "Graph");
		tags.Add(Xopg_tag_itm.New_js_file(xtn_dir.GenSubFil_nest("lib", "d3.js")));
		// tags.Add(Xopg_tag_itm.New_js_file(xtn_dir.GenSubFil_nest("lib", "d3-global.js"))); // XOWA: unused b/c module is not available
		if (version_is_1) {
			tags.Add(Xopg_tag_itm.New_js_file(xtn_dir.GenSubFil_nest("lib", "vega1", "vega.js")));
		}
		else {
		tags.Add(Xopg_tag_itm.New_js_file(xtn_dir.GenSubFil_nest("lib", "d3.layout.cloud.js")));
			tags.Add(Xopg_tag_itm.New_js_file(xtn_dir.GenSubFil_nest("lib", "vega2", "vega.js")));
			tags.Add(Xopg_tag_itm.New_js_file(xtn_dir.GenSubFil_nest("lib", "graph2.compiled.js")));
			// tags.Add(Xopg_tag_itm.New_js_file(xtn_dir.GenSubFil_nest("modules", "graph2.js")));
		}
		tags.Add(Xopg_tag_itm.New_js_file(xtn_dir.GenSubFil_nest("Xograph.js")));
	}
}
