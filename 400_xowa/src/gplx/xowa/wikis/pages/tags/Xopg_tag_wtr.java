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
package gplx.xowa.wikis.pages.tags;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.langs.htmls.*;
public class Xopg_tag_wtr {
	public static boolean Loader_as_script_static = true;		// TEST
	public static byte[] Write(BryWtr bfr, boolean write_loader_tag, Xopg_tag_wtr_cbk cbk, Xopg_tag_mgr tag_mgr) {
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
			if (write_loader_tag) bfr.Add(Gfh_tag_.Script_lhs_w_type).AddByteNl();
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
			if (write_loader_tag) bfr.Add(Gfh_tag_.Script_rhs).AddByteNl();

			for (int i = 0; i < len; ++i) {
				Xopg_tag_itm itm = tag_mgr.Get_at(i);
				switch (itm.Tid()) {
					case Xopg_tag_itm.Tid__js_file: continue;
				}
				cbk.Write_tag(bfr, itm);
			}
		}
		return bfr.ToBryAndClear();
	}
	static class Wtr { //#*nested
		public static void Add_loader_func(BryWtr bfr) {
			bfr.AddStrA7Nl("  function load_files_sequentially(files, idx, done_cbk) {");
			bfr.AddStrA7Nl("    if (files[idx]) { // idx is valid");
			bfr.AddStrA7Nl("      var script = document.createElement('script');");
			bfr.AddStrA7Nl("      script.setAttribute('type','text/javascript');");
			bfr.AddStrA7Nl("      script.setAttribute('src', files[idx]);");
			bfr.AddStrA7Nl("      script.onload = function(){");
			bfr.AddStrA7Nl("        load_files_sequentially(files, ++idx, done_cbk); // load next file");
			bfr.AddStrA7Nl("      };");
			bfr.AddStrA7Nl("      document.getElementsByTagName('head')[0].appendChild(script)");
			bfr.AddStrA7Nl("    }");
			bfr.AddStrA7Nl("    else {            // idx is not valid; finished;");
			bfr.AddStrA7Nl("      done_cbk();");
			bfr.AddStrA7Nl("    }");
			bfr.AddStrA7Nl("  }");
		}
		public static void Add_files_bgn(BryWtr bfr) {
			bfr.AddStrA7Nl("  var files =");
		}
		public static void Add_files_itm(BryWtr bfr, Xopg_tag_itm itm, int idx) {
			bfr.AddStrA7(idx == 0 ? "  [ '" : "  , '");
			bfr.Add(itm.Href());
			bfr.AddByteApos().AddByteNl();
		}
		public static void Add_files_end(BryWtr bfr, int files_count) {
			bfr.AddStrA7Nl("  ];");
			bfr.AddStrA7Nl("  load_files_sequentially(files, 0, function() {");
			bfr.AddStrA7Nl("    console.log('javascript files loaded: count=" + files_count + "');");
			bfr.AddStrA7Nl("  });");
		}
	}
}
