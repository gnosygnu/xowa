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
package gplx.xowa.langs.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.intls.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.langs.numbers.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.bldrs.*; import gplx.xowa.langs.specials.*;
import gplx.xowa.wikis.nss.*;
public class Xol_lang_srl {
	public static Xow_ns[] Load_ns_grps(byte[] src) {
		int src_len = src.length, pos = 0, fld_bgn = 0;
		int cur_id = -1;
		List_adp rv = List_adp_.New(); Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		while (true) {
			boolean last = pos == src_len;	// NOTE: logic occurs b/c of \n}~-> dlm which gobbles up last \n
			byte b = last ? Byte_ascii.Nl : src[pos];
			switch (b) {
				case Byte_ascii.Pipe:
					cur_id = Bry_.To_int_or(src, fld_bgn, pos, Int_.Min_value);
					if (cur_id == Int_.Min_value) throw Err_.new_wo_type("invalid_id", "id", String_.new_u8(src, fld_bgn, pos));					
					fld_bgn = pos + 1;
					break;
				case Byte_ascii.Nl:
					byte[] cur_name = csv_parser.Load(src, fld_bgn, pos);
					cur_name = Xoa_ttl.Replace_spaces(cur_name);	// NOTE: *.gfs files will have names with \s instead of _; this comes from Language.php which also has same \s convention; EX: "Template talk" instead of "Template_talk"
					Xow_ns ns = new Xow_ns(cur_id, Xow_ns_case_.Tid__1st, cur_name, false);
					rv.Add(ns);
					fld_bgn = pos + 1;
					cur_id = -1;
					break;
				default:
					break;
			}
			if (last) break;
			++pos;
		}
		return (Xow_ns[])rv.To_ary(Xow_ns.class);
	}
	public static void Load_keywords(Xol_kwd_mgr keyword_mgr, byte[] src) {
		int src_len = src.length, pos = 0, fld_bgn = 0, fld_idx = 0;
		boolean cur_cs = false; byte[] cur_key = Bry_.Empty;
		List_adp cur_words = List_adp_.New();
		Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		while (true) {
			boolean last = pos == src_len;	// NOTE: logic occurs b/c of \n}~-> dlm which gobbles up last \n
			byte b = last ? Byte_ascii.Nl : src[pos];
			switch (b) {
				case Byte_ascii.Pipe:
					switch (fld_idx) {
						case 0:
							cur_key = csv_parser.Load(src, fld_bgn, pos);
							break;
						case 1:
							byte cs_byte = src[pos - 1]; 
							switch (cs_byte) {
								case Byte_ascii.Num_0: cur_cs = false; break;
								case Byte_ascii.Num_1: cur_cs = true; break;
								default: throw Err_.new_wo_type("case sensitive should be 0 or 1", "cs", Byte_.To_str(cs_byte)); 
							}
							break;
					}
					fld_bgn = pos + 1;
					++fld_idx;
					break;
				case Byte_ascii.Tilde:
					byte[] word = csv_parser.Load(src, fld_bgn, pos);
					cur_words.Add(word);
					fld_bgn = pos + 1;
					break;
				case Byte_ascii.Nl:
					if (cur_words.Count() > 0) {	// guard against blank line wiping out entries; EX: "toc|0|toc1\n\n"; 2nd \n will get last grp and make 0 entries
						int cur_id = Xol_kwd_grp_.Id_by_bry(cur_key); if (cur_id == -1) throw Err_.new_wo_type("key does not have id", "id", cur_id);
						Xol_kwd_grp grp = keyword_mgr.Get_or_new(cur_id);
						grp.Srl_load(cur_cs, (byte[][])cur_words.To_ary(byte[].class));
					}
					fld_bgn = pos + 1;
					fld_idx = 0;
					cur_words.Clear();
					break;
				default:
					break;
			}
			if (last) break;
			++pos;
		}
//			return (Xol_kwd_grp[])rv.To_ary(typeof(Xol_kwd_grp));
	}
	public static void Load_messages(Xol_msg_mgr msg_mgr, byte[] src) {
		int src_len = src.length, pos = 0, fld_bgn = 0;
		byte[] cur_key = Bry_.Empty;
		Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		while (true) {
			boolean last = pos == src_len;	// NOTE: logic occurs b/c of \n}~-> dlm which gobbles up last \n
			byte b = last ? Byte_ascii.Nl : src[pos];
			switch (b) {
				case Byte_ascii.Pipe:
					cur_key = csv_parser.Load(src, fld_bgn, pos);
					fld_bgn = pos + 1;
					break;
				case Byte_ascii.Nl:
					byte[] cur_val = csv_parser.Load(src, fld_bgn, pos);
					Xol_msg_itm itm = msg_mgr.Itm_by_key_or_new(cur_key).Defined_in_(Xol_msg_itm.Defined_in__lang);	// NOTE: this proc should only be called when loading lang.gfs
					Xol_msg_itm_.update_val_(itm, cur_val);
					itm.Dirty_(true);
					fld_bgn = pos + 1;
					break;
				default:
					break;
			}
			if (last) break;
			++pos;
		}
	}
	public static void Load_specials(Xol_specials_mgr special_mgr, byte[] src) {
		int src_len = src.length, pos = 0, fld_bgn = 0;
		byte[] cur_key = Bry_.Empty;
		Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		while (true) {
			boolean last = pos == src_len;	// NOTE: logic occurs b/c of \n}~-> dlm which gobbles up last \n
			byte b = last ? Byte_ascii.Nl : src[pos];
			switch (b) {
				case Byte_ascii.Pipe:
					cur_key = csv_parser.Load(src, fld_bgn, pos);
					fld_bgn = pos + 1;
					break;
				case Byte_ascii.Nl:
					byte[] cur_val_raw = csv_parser.Load(src, fld_bgn, pos);
					byte[][] cur_vals = Bry_split_.Split(cur_val_raw, Byte_ascii.Tilde);
					special_mgr.Add(cur_key, cur_vals);
					fld_bgn = pos + 1;
					break;
				default:
					break;
			}
			if (last) break;
			++pos;
		}
	}
	public static void Save_num_mgr(Xoa_gfs_bldr bldr, Xol_num_mgr num_mgr) {
		Xol_transform_mgr separators_mgr = num_mgr.Separators_mgr(); int separators_len = separators_mgr.Len();
		Xol_transform_mgr digits_mgr = num_mgr.Digits_mgr(); int digits_len = digits_mgr.Len();
		byte[] digit_grouping_pattern = num_mgr.Num_grp_fmtr().Digit_grouping_pattern();
		if (separators_len > 0 || digits_len > 0 || digit_grouping_pattern != null) {
			bldr.Add_proc_init_one(Xol_lang_itm.Invk_numbers).Add_curly_bgn_nl();													// numbers {
			if (digit_grouping_pattern != null) {
				bldr.Add_indent(1).Add_eq_str(Xol_num_mgr.Invk_digit_grouping_pattern, digit_grouping_pattern);
			}
			if (separators_len > 0) {
				bldr.Add_indent(1).Add_proc_init_one(Xol_num_mgr.Invk_separators).Add_curly_bgn_nl();							//   separators {
				bldr.Add_indent(2).Add_proc_init_one(Xol_num_mgr.Invk_clear).Add_term_nl();										//     clear;
				for (int i = 0; i < separators_len; i++) {
					Keyval kv = separators_mgr.Get_at(i);
					String k = kv.Key(), v = kv.Val_to_str_or_empty();
					bldr.Add_indent(2).Add_proc_init_many(Xol_transform_mgr.Invk_set).Add_parens_str_many(k, v).Add_term_nl();	//     set('k', 'v');
				}
				bldr.Add_indent(1).Add_curly_end_nl();																			//   }
			}
			if (digits_len > 0) {
				bldr.Add_indent(1).Add_proc_init_one(Xol_num_mgr.Invk_digits).Add_curly_bgn_nl();								//   digits {
				bldr.Add_indent(2).Add_proc_init_one(Xol_num_mgr.Invk_clear).Add_term_nl();										//     clear;
				for (int i = 0; i < digits_len; i++) {
					Keyval kv = digits_mgr.Get_at(i);
					String k = kv.Key(), v = kv.Val_to_str_or_empty();
					bldr.Add_indent(2).Add_proc_init_many(Xol_transform_mgr.Invk_set).Add_parens_str_many(k, v).Add_term_nl();	//     set('k', 'v');
				}
				bldr.Add_indent(1).Add_curly_end_nl();																			//   }
			}
			bldr.Add_curly_end_nl();																							// }
		}
	}
	public static void Save_ns_grps(Xoa_gfs_bldr bldr, Xol_ns_grp ns_grp, String proc_invk) {
		int ns_grp_len = ns_grp.Len(); Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		if (ns_grp_len == 0) return;
		Bry_bfr bfr = bldr.Bfr();
		bldr.Add_proc_cont_one(proc_invk).Add_nl();
		bldr.Add_indent().Add_proc_cont_one(Invk_load_text).Add_paren_bgn().Add_nl();		//   .load_text(\n
		bldr.Add_quote_xtn_bgn();															//	<~{\n'
		for (int i = 0; i < ns_grp_len; i++) {
			Xow_ns ns = ns_grp.Get_at(i);
			bfr.Add_int_variable(ns.Id()).Add_byte_pipe();									// id|
			csv_parser.Save(bfr, ns.Name_db());												// name
			bfr.Add_byte_nl();																// \n
		}
		bldr.Add_quote_xtn_end();															// ']:>\n
		bldr.Add_paren_end().Add_proc_cont_one(Invk_lang).Add_nl();							// ).lang\n
	}
	public static void Save_specials(Xoa_gfs_bldr bldr, Xol_specials_mgr specials_mgr) {
		int specials_len = specials_mgr.Len(); Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		if (specials_len == 0) return;
		Bry_bfr bfr = bldr.Bfr();
		bldr.Add_proc_cont_one(Xol_lang_itm.Invk_specials).Add_nl();
		bldr.Add_indent().Add_proc_cont_one(Xol_specials_mgr.Invk_clear).Add_nl();
		bldr.Add_indent().Add_proc_cont_one(Invk_load_text).Add_paren_bgn().Add_nl();		//   .load_text(\n
		bldr.Add_quote_xtn_bgn();															//	<~{\n'
		for (int i = 0; i < specials_len; i++) {
			Xol_specials_itm itm = specials_mgr.Get_at(i);
			bfr.Add(itm.Special()).Add_byte_pipe();											// id|
			int aliases_len = itm.Aliases().length;
			for (int j = 0; j < aliases_len; j++) {
				if (j != 0) bfr.Add_byte(Byte_ascii.Tilde);
				csv_parser.Save(bfr, itm.Aliases()[j]);										// name
			}
			bfr.Add_byte_nl();																// \n
		}
		bldr.Add_quote_xtn_end();															// ']:>\n
		bldr.Add_paren_end().Add_proc_cont_one(Invk_lang).Add_nl();							// ).lang\n
	}
	public static void Save_keywords(Xoa_gfs_bldr bldr, Xol_kwd_mgr kwd_mgr) {
		int len = kwd_mgr.Len(); Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		int count = 0;
		for (int i = 0; i < len; i++) {
			Xol_kwd_grp grp = kwd_mgr.Get_at(i); if (grp == null) continue; // some items may not be loaded/set by lang
			++count;
		}
		if (count == 0) return;
		Bry_bfr bfr = bldr.Bfr();
		bldr.Add_proc_cont_one(Xol_lang_itm.Invk_keywords).Add_nl();							// .keywords\n
		bldr.Add_indent().Add_proc_cont_one(Invk_load_text).Add_paren_bgn().Add_nl();		//   .load_text(\n
		bldr.Add_quote_xtn_bgn();															//	<~{\n'
		for (int i = 0; i < len; i++) {
			Xol_kwd_grp grp = kwd_mgr.Get_at(i); if (grp == null) continue;					// some items may not be loaded/set by lang
			csv_parser.Save(bfr, grp.Key());												// key
			bfr.Add_byte_pipe();															// |
			bfr.Add_int_bool(grp.Case_match()).Add_byte_pipe();								// 1|
			int word_len = grp.Itms().length;
			for (int j = 0; j < word_len; j++) {
				Xol_kwd_itm word = grp.Itms()[j];
				csv_parser.Save(bfr, word.Val());											// word
				bfr.Add_byte(Byte_ascii.Tilde);												// ~
			}
			bldr.Add_nl();																	// \n
		}
		bldr.Add_quote_xtn_end();															// ']:>\n
		bldr.Add_paren_end().Add_proc_cont_one(Invk_lang).Add_nl();							// ).lang\n
	}
	public static void Save_messages(Xoa_gfs_bldr bldr, Xol_msg_mgr msg_mgr, boolean dirty) {
		int len = msg_mgr.Itms_max(); Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		int count = 0;
		for (int i = 0; i < len; i++) {
			Xol_msg_itm itm = msg_mgr.Itm_by_id_or_null(i); if (itm == null) continue;		// some items may not be loaded/set by lang			
			if (dirty && !itm.Dirty()) continue;	// TEST:
			++count;
		}
		if (count == 0) return;
		Bry_bfr bfr = bldr.Bfr();
		bldr.Add_proc_cont_one(Xol_lang_itm.Invk_messages).Add_nl();							// .keywords\n
		bldr.Add_indent().Add_proc_cont_one(Invk_load_text).Add_paren_bgn().Add_nl();		//   .load_text(\n
		bldr.Add_quote_xtn_bgn();															//	<~{\n'
		for (int i = 0; i < len; i++) {
			Xol_msg_itm itm = msg_mgr.Itm_by_id_or_null(i); if (itm == null) continue;		// some items may not be loaded/set by lang			
			if (dirty && !itm.Dirty()) continue;	// TEST:
			csv_parser.Save(bfr, itm.Key());												// key
			bfr.Add_byte_pipe();															// |
			csv_parser.Save(bfr, itm.Val());												// val
			bfr.Add_byte_nl();																// \n
		}
		bldr.Add_quote_xtn_end();															// ']:>\n
		bldr.Add_paren_end().Add_proc_cont_one(Invk_lang).Add_nl();							// ).lang\n
	}
	public static final String Invk_load_text = "load_text", Invk_lang = "lang";
}
