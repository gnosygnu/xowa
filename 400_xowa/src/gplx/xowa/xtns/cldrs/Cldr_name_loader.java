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
public class Cldr_name_loader {
	private final    Json_parser parser = new Json_parser();
	private final    Io_url cldr_dir;
	private final    Hash_adp hash = Hash_adp_.New();

	public Cldr_name_loader(Io_url root_dir) {
		cldr_dir = root_dir.GenSubDir_nest("bin", "any", "xowa", "xtns", "cldr", "CldrNames");
	}

	public Cldr_name_file Load(String lang_key) {
		Cldr_name_file file = (Cldr_name_file)hash.Get_by(lang_key);
		if (file != null) return file;

		byte[] json = Io_mgr.Instance.LoadFilBry(cldr_dir.GenSubFil_ary("CldrNames", lang_key, ".json"));
		if (json == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "no cldrName file exists for lang; lang=~{lang}", lang_key);
			return null;
		}
		file = Load(lang_key, json);
		hash.Add(lang_key, file);
		return file;
	}
	public Cldr_name_file Load(String lang_key, byte[] json) {
		Cldr_name_file file = new Cldr_name_file(lang_key);

		Json_doc jdoc = parser.Parse(json);
		Json_nde root = jdoc.Root_nde();
		int nodes_len = root.Len();
		for (int i = 0; i < nodes_len; i++) {
			Json_kv node = root.Get_at_as_kv(i);
			String key = node.Key_as_str();
			Json_nde val = node.Val_as_nde();

			Ordered_hash hash = null;
			if		(String_.Eq(key, Cldr_name_converter.Node_languageNames))	hash = file.Language_names();
			else if	(String_.Eq(key, Cldr_name_converter.Node_currencyNames))	hash = file.Currency_names();
			else if	(String_.Eq(key, Cldr_name_converter.Node_currencySymbols))	hash = file.Currency_symbols();
			else if	(String_.Eq(key, Cldr_name_converter.Node_countryNames))	hash = file.Country_names();
			else if	(String_.Eq(key, Cldr_name_converter.Node_timeUnits))		hash = file.Time_units();
			else throw Err_.new_unhandled_default(key);
			Load_ary(file, hash, val);
		}
		return file; 
	}
	private void Load_ary(Cldr_name_file file, Ordered_hash hash, Json_nde nde) {
		int len = nde.Len();
		for (int i = 0; i < len; i++) {
			Json_kv kv = (Json_kv)nde.Get_at(i);
			String key = kv.Key_as_str();
			hash.Add(key, Keyval_.new_(key, String_.new_u8(kv.Val_as_bry())));
		}
	}
}
