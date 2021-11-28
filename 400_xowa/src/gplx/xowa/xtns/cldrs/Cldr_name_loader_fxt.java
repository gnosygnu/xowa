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
package gplx.xowa.xtns.cldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.tests.*;
import gplx.langs.jsons.*;
public class Cldr_name_loader_fxt {
	private static final String Dir_name = "mem/CldrNames/";
	private final Cldr_name_loader name_loader = new Cldr_name_loader(Io_url_.mem_dir_(Dir_name));

	public void Clear() {name_loader.Clear();}
	public void Init__file(String fil_name, String txt) {
		Io_mgr.Instance.SaveFilStr(Io_url_.new_fil_(Dir_name + fil_name), txt);
	}
	public void Test__load_file_is_empty(boolean expd, String lang_key) {
		Cldr_name_file name_file = name_loader.Load_or_empty(lang_key);
		Gftest.Eq__bool(expd, name_file == Cldr_name_file.Empty);
	}
	public static void Create_file_w_langs(String lang_name, Keyval... lang_ary) {
		String root_dir = "mem/xowa/bin/any/xowa/xtns/cldr/CldrNames/";
		String file = String_.Concat(root_dir, "CldrNames", lang_name, ".json");
		
		Json_doc_wtr wtr = new Json_doc_wtr();
		wtr.Nde_bgn();
		wtr.Key(Bool_.N, "languageNames");
		wtr.Nde_bgn();
		for (int i = 0; i < lang_ary.length; i++) {
			Keyval lang = lang_ary[i];
			wtr.Kv(i != 0, Bry_.new_u8(lang.Key()), Bry_.new_u8(lang.Val_to_str_or_null()));
		}
		wtr.Nde_end();
		wtr.Nde_end();
		String json = wtr.Bld_as_str();

		Io_mgr.Instance.SaveFilStr(file, json);
	}
}
