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
import gplx.langs.jsons.*;
public class Language_name_loader {
	private final    Json_parser parser = new Json_parser();
	private final    Io_url names_url;

	public Language_name_loader(Io_url root_dir) {
		names_url = root_dir.GenSubFil_nest("bin", "any", "xowa", "cfg", "lang", "data", "names.json");
	}
	public Ordered_hash Load_as_hash() {
		byte[] json = Io_mgr.Instance.LoadFilBry(names_url);
		if (json == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "no names file exists");
			return Ordered_hash_.New();
		}
		Language_name[] ary = Load(json);

		// convert
		Ordered_hash rv = Ordered_hash_.New();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Language_name itm = ary[i];
			rv.Add(itm.Code(), Keyval_.new_(String_.new_u8(itm.Code()), itm.Name()));
		}
		return rv;
	}
	public Language_name[] Load(byte[] json) {
		List_adp list = List_adp_.New();

		Json_doc jdoc = parser.Parse(json);
		if (jdoc == null) return Language_name.Ary_empty;
		Json_ary root = jdoc.Root_ary();
		int len = root.Len();
		for (int i = 0; i < len; i++) {
			Json_nde node = root.Get_as_nde(i);
			byte[] code = node.Get_as_bry("code");
			byte[] name = node.Get_as_bry("name");
			Language_name itm = new Language_name(code, name, null);
			list.Add(itm);
		}
		return (Language_name[])list.To_ary_and_clear(Language_name.class);
	}
}
