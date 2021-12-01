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
package gplx.xowa.xtns.cldrs; import gplx.*;
import gplx.core.primitives.*;
import gplx.langs.phps.*;
import gplx.langs.jsons.*;
class Cldr_name_converter {
	private final Php_parser parser = new Php_parser();
	private final Php_evaluator eval = new Php_evaluator(new gplx.core.log_msgs.Gfo_msg_log("test"));
	private final Php_text_itm_parser text_itm_parser = new Php_text_itm_parser().Quote_is_single_(true);
	private final Json_doc_wtr doc_wtr = new Json_doc_wtr();
	private final List_adp tmp_list = List_adp_.New();
	private final Byte_obj_ref tmp_result = Byte_obj_ref.zero_();
	private final Bry_bfr tmp_bfr = Bry_bfr_.New();
	public void Convert(Io_url src, Io_url trg) {
		Cldr_name_file[] fils = Parse_dir(src);
		for (Cldr_name_file fil : fils) {
			Io_mgr.Instance.SaveFilStr(trg.GenSubDir(File_CldrNames).GenSubFil_ary(File_CldrNames, fil.Key(), ".json"), To_json(fil));
			Gfo_usr_dlg_.Instance.Note_many("", "", "converted file; key={0}", fil.Key());
		}
	}
	public Cldr_name_file[] Parse_dir(Io_url dir) {
		List_adp rv = List_adp_.New();
		Io_url[] fils = Io_mgr.Instance.QueryDir_fils(dir);
		for (Io_url fil : fils) {
			String key = Extract_key_or_fail(fil.NameAndExt());
			byte[] src = Io_mgr.Instance.LoadFilBry(fil);			
			rv.Add(Parse_fil(key, src));
		}
		return (Cldr_name_file[])rv.ToAryAndClear(Cldr_name_file.class);
	}
	public String Extract_key_or_fail(String fil_name) {
		if (!String_.Has_at_bgn(fil_name, File_CldrNames) || !String_.Has_at_end(fil_name, ".php")) throw Err_.new_wo_type("file name must have a format of CldrNamesLANG.php", "fil_name", fil_name);
		return String_.Mid(fil_name, String_.Len(File_CldrNames), String_.Len(fil_name) - String_.Len(".php"));
	}
	public Cldr_name_file Parse_fil(String key, byte[] src) {
		Cldr_name_file file = new Cldr_name_file(key);
		parser.Parse_tkns(src, eval);
		Php_line[] lines = (Php_line[])eval.List().ToAry(Php_line.class);
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; i++) {
			Php_line line = lines[i];
			Php_line_assign assign_line = (Php_line_assign)line;
			String assign_key = String_.new_u8(assign_line.Key().Val_obj_bry());
			
			Ordered_hash hash = null;
			if		(String_.Eq(assign_key, Node_languageNames))	hash = file.Language_names();
			else if	(String_.Eq(assign_key, Node_currencyNames))	hash = file.Currency_names();
			else if	(String_.Eq(assign_key, Node_currencySymbols))	hash = file.Currency_symbols();
			else if	(String_.Eq(assign_key, Node_countryNames))		hash = file.Country_names();
			else if	(String_.Eq(assign_key, Node_timeUnits))		hash = file.Time_units();
			else throw Err_.new_unhandled_default(assign_key);
			Parse_assign_line(key, assign_key, assign_line, hash);
		}
		eval.Clear();
		return file;
	}
	private void Parse_assign_line(String lang_key, String assign_key, Php_line_assign assign, Ordered_hash hash) {
		Php_itm_ary ary = (Php_itm_ary)assign.Val();
		int ary_len = ary.Subs_len();
		for (int i = 0; i < ary_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			String key_str = String_.new_u8(kv.Key().Val_obj_bry());
			String val_str = String_.new_u8(text_itm_parser.Parse_as_bry(tmp_list, kv.Val().Val_obj_bry(), tmp_result, tmp_bfr));
			if (hash.Has(key_str)) throw Err_.new_wo_type("key already exists", "lang", lang_key, "type", assign_key, "key", key_str);
			hash.Add(key_str, Keyval_.new_(key_str, val_str)); 
		}
	}
	public String To_json(Cldr_name_file name_file) {
		Int_obj_ref written = Int_obj_ref.New_zero();
		doc_wtr.Nde_bgn();
		To_json(written, Node_languageNames		, name_file.Language_names());
		To_json(written, Node_currencyNames		, name_file.Currency_names());
		To_json(written, Node_currencySymbols	, name_file.Currency_symbols());
		To_json(written, Node_countryNames		, name_file.Country_names());
		To_json(written, Node_timeUnits			, name_file.Time_units());
		doc_wtr.Nde_end();
		return doc_wtr.Bld_as_str();
	}
	private void To_json(Int_obj_ref written, String node_name, Ordered_hash hash) {
		int len = hash.Len();
		if (len == 0) return;
		doc_wtr.Key(written.Val() != 0, node_name);
		doc_wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			Keyval kv = (Keyval)hash.Get_at(i);
			doc_wtr.Kv(i != 0, Bry_.new_u8(kv.Key()), Bry_.new_u8(kv.Val_to_str_or_null()));
		}
		doc_wtr.Nde_end();
		written.Val_add_post();
	}
	public static final String 
	  File_CldrNames		= "CldrNames"
	, Node_languageNames	= "languageNames"
	, Node_currencyNames	= "currencyNames"
	, Node_currencySymbols	= "currencySymbols"
	, Node_countryNames		= "countryNames"
	, Node_timeUnits		= "timeUnits"
	;
}
