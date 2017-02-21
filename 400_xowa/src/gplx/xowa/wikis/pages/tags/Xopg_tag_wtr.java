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
import gplx.langs.htmls.*;
public class Xopg_tag_wtr {
	public static boolean Loader_as_script_static = true;		// TEST
	public static byte[] Write(Bry_bfr bfr, boolean write_loader_tag, Xopg_tag_wtr_cbk cbk, Xopg_tag_mgr tag_mgr) {
		boolean loader_as_script = Loader_as_script_static;	// allow disabling for dekstop later
		boolean embed_loader = !gplx.core.envs.Op_sys.Cur().Tid_is_drd();	// PERF: drd will embed in bundle.js

		// get files_count
		int len = tag_mgr.Len();
		int files_total = 0;
		for (int i = 0; i < len; ++i) {
			Xopg_tag_itm itm = tag_mgr.Get_at(i);
			if (loader_as_script) {
				switch (itm.Tid()) {
					case Xopg_tag_itm.Tid__js_file:
						++files_total;
						break;
				}
			}
		}

		// bgn loader_as_script
		if (loader_as_script) {
			if (write_loader_tag) bfr.Add(Gfh_tag_.Script_lhs_w_type).Add_byte_nl();
			if (embed_loader && tag_mgr.Pos_is_head())
				Wtr.Add_loader_func(bfr);
			if (files_total > 0) Wtr.Add_files_bgn(bfr);
		}
		
		// write tags
		len = tag_mgr.Len();
		int files_idx = 0;
		for (int i = 0; i < len; ++i) {
			Xopg_tag_itm itm = tag_mgr.Get_at(i);
			if (loader_as_script) {
				switch (itm.Tid()) {
					case Xopg_tag_itm.Tid__js_file:
						Wtr.Add_files_itm(bfr, itm, files_idx++);
						break;
				}
				continue;
			}
			else
				cbk.Write_tag(bfr, itm);
		}

		// end loader_as_script
		if (loader_as_script) {
			if (files_total > 0) Wtr.Add_files_end(bfr, files_total);
			if (write_loader_tag) bfr.Add(Gfh_tag_.Script_rhs).Add_byte_nl();

			for (int i = 0; i < len; ++i) {
				Xopg_tag_itm itm = tag_mgr.Get_at(i);
				switch (itm.Tid()) {
					case Xopg_tag_itm.Tid__js_file: continue;
				}
				cbk.Write_tag(bfr, itm);
			}
		}
		return bfr.To_bry_and_clear();
	}
	static class Wtr { //#*nested
		public static void Add_loader_func(Bry_bfr bfr) {
			bfr.Add_str_a7_w_nl("  function load_files_sequentially(files, idx, done_cbk) {");
			bfr.Add_str_a7_w_nl("    if (files[idx]) { // idx is valid");
			bfr.Add_str_a7_w_nl("      var script = document.createElement('script');");
			bfr.Add_str_a7_w_nl("      script.setAttribute('type','text/javascript');");
			bfr.Add_str_a7_w_nl("      script.setAttribute('src', files[idx]);");
			bfr.Add_str_a7_w_nl("      script.onload = function(){");
			bfr.Add_str_a7_w_nl("        load_files_sequentially(files, ++idx, done_cbk); // load next file");
			bfr.Add_str_a7_w_nl("      };");
			bfr.Add_str_a7_w_nl("      document.getElementsByTagName('head')[0].appendChild(script)");
			bfr.Add_str_a7_w_nl("    }");
			bfr.Add_str_a7_w_nl("    else {            // idx is not valid; finished;");
			bfr.Add_str_a7_w_nl("      done_cbk();");
			bfr.Add_str_a7_w_nl("    }");
			bfr.Add_str_a7_w_nl("  }");
		}
		public static void Add_files_bgn(Bry_bfr bfr) {
			bfr.Add_str_a7_w_nl("  var files =");
		}
		public static void Add_files_itm(Bry_bfr bfr, Xopg_tag_itm itm, int idx) {
			bfr.Add_str_a7(idx == 0 ? "  [ '" : "  , '");
			bfr.Add(itm.Href());
			bfr.Add_byte_apos().Add_byte_nl();
		}
		public static void Add_files_end(Bry_bfr bfr, int files_count) {
			bfr.Add_str_a7_w_nl("  ];");
			bfr.Add_str_a7_w_nl("  load_files_sequentially(files, 0, function() {");
			bfr.Add_str_a7_w_nl("    console.log('javascript files loaded: count=" + files_count + "');");
			bfr.Add_str_a7_w_nl("  });");
		}
	}
}
