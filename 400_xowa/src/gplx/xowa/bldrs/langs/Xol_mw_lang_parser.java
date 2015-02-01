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
package gplx.xowa.bldrs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.intl.*; import gplx.php.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.numbers.*;
public class Xol_mw_lang_parser {
	private Php_parser parser = new Php_parser(); private Php_evaluator evaluator;
	public Xol_mw_lang_parser(Gfo_msg_log msg_log) {evaluator = new Php_evaluator(msg_log);}
	public void Bld_all(Xoa_app app) {Bld_all(app, Xol_lang_transform_null._);}
	public static final String Dir_name_core = "core";
	public void Bld_all(Xoa_app app, Xol_lang_transform lang_transform) {
		Io_url lang_root = app.Fsys_mgr().Cfg_lang_core_dir().OwnerDir();
		Parse_mediawiki(app, lang_root.GenSubDir("mediawiki"), lang_transform);
		Save_langs(app, lang_root.GenSubDir(Xol_mw_lang_parser.Dir_name_core), OrderedHash_.new_bry_(), OrderedHash_.new_bry_());
	}
	public void Save_langs(Xoa_app app, Io_url xowa_root, OrderedHash manual_text_bgn, OrderedHash manual_text_end) {
		Xoa_lang_mgr lang_mgr = app.Lang_mgr();
		int len = lang_mgr.Len();
		Gfs_bldr bldr = new Gfs_bldr();
		for (int i = 0; i < len; i++) {
			Xol_lang lang = lang_mgr.Get_at(i);
			String lang_key = lang.Key_str();
			Io_url lang_url = xowa_root.GenSubFil(lang_key + ".gfs");
			Save_langs_by_manual_text(bldr, manual_text_bgn, lang_key);

			Xol_lang_srl.Save_num_mgr(bldr, lang.Num_mgr());
			bldr.Add_proc_init_many("this").Add_nl();
			if (lang.Fallback_bry() != null)	// NOTE: fallback will often be null; EX: en
				bldr.Add_proc_cont_one(Xol_lang.Invk_fallback_load).Add_parens_str(lang.Fallback_bry()).Add_nl();
			if (!lang.Dir_ltr())				// NOTE: only save dir_ltr if false; EX: en
				bldr.Add_proc_cont_one(Xol_lang.Invk_dir_rtl_).Add_parens_str(Yn.Xto_str(!lang.Dir_ltr())).Add_nl();
			Xol_lang_srl.Save_ns_grps(bldr, lang.Ns_names(), Xol_lang.Invk_ns_names);
			Xol_lang_srl.Save_ns_grps(bldr, lang.Ns_aliases(), Xol_lang.Invk_ns_aliases);
			Xol_lang_srl.Save_specials(bldr, lang.Specials_mgr());
			Xol_lang_srl.Save_keywords(bldr, lang.Kwd_mgr());
			Xol_lang_srl.Save_messages(bldr, lang.Msg_mgr(), true);
			bldr.Add_term_nl();

			Save_langs_by_manual_text(bldr, manual_text_end, lang_key);
			Io_mgr._.SaveFilBfr(lang_url, bldr.Bfr());
		}
	}
	private void Save_langs_by_manual_text(Gfs_bldr bldr, OrderedHash manual_text_hash, String lang_key) {
		byte[][] itm = (byte[][])manual_text_hash.Fetch(Bry_.new_utf8_(lang_key));
		if (itm != null) bldr.Bfr().Add(itm[1]);
	}
	public void Parse_mediawiki(Xoa_app app, Io_url mediawiki_root, Xol_lang_transform lang_transform) {
		Bry_bfr bfr = Bry_bfr.new_();
		Parse_file_core_php(app, mediawiki_root, bfr, lang_transform);
		Parse_file_xtns_php(app, mediawiki_root, bfr, lang_transform);
		Parse_file_json(app, bfr, lang_transform, mediawiki_root.GenSubDir("core_json"));
		Parse_file_json(app, bfr, lang_transform, mediawiki_root.GenSubDir("xtns_json"));
	}
	private void Parse_file_core_php(Xoa_app app, Io_url mediawiki_root, Bry_bfr bfr, Xol_lang_transform lang_transform) {
		Io_url dir = mediawiki_root.GenSubDir("core_php");
		Io_url[] urls = Io_mgr._.QueryDir_fils(dir);
		int len = urls.length;
		for (int i = 0; i < len; i++) {
			Io_url url = urls[i];
			try {
				String lang_key = String_.Replace(String_.Lower(String_.Mid(url.NameOnly(), 8)), "_", "-");	// 8=Messages.length; lower b/c format is MessagesEn.php (need "en")
				// if (String_.In(lang_key, "qqq", "enrtl", "bbc", "bbc-latn")) continue;
				String text = Io_mgr._.LoadFilStr(url);
				Xol_lang lang = app.Lang_mgr().Get_by_key_or_new(Bry_.new_utf8_(lang_key));
				this.Parse_core(text, lang, bfr, lang_transform);
			} catch (Exception exc) {Err_.Noop(exc); Tfds.WriteText("failed to parse " + url.NameOnly() + Err_.Message_gplx_brief(exc) + "\n");}
		}
	}
	private void Parse_file_xtns_php(Xoa_app app, Io_url mediawiki_root, Bry_bfr bfr, Xol_lang_transform lang_transform) {
		Io_url dir = mediawiki_root.GenSubDir("xtns_php");
		Io_url[] urls = Io_mgr._.QueryDir_fils(dir);
		int len = urls.length;
		for (int i = 0; i < len; i++) {
			Io_url url = urls[i];
			try {
			String text = Io_mgr._.LoadFilStr(url);
			boolean prepend_hash = String_.Eq("ParserFunctions.i18n.magic", url.NameOnly());
			this.Parse_xtn(text, url, app, bfr, prepend_hash, lang_transform);
			} catch (Exception exc) {Err_.Noop(exc); Tfds.WriteText("failed to parse " + url.NameOnly() + Err_.Message_gplx_brief(exc));}
		}
	}
	private void Parse_file_json(Xoa_app app, Bry_bfr bfr, Xol_lang_transform lang_transform, Io_url root_dir) {
		Io_url[] dirs = Io_mgr._.QueryDir_args(root_dir).DirOnly_().ExecAsUrlAry();
		int dirs_len = dirs.length;
		gplx.xowa.bldrs.langs.Xob_i18n_parser i18n_parser = app.Bldr().I18n_parser();
		for (int i = 0; i < dirs_len; i++) {
			Io_url dir = dirs[i];
			Io_url[] fils = Io_mgr._.QueryDir_args(dir).ExecAsUrlAry();
			int fils_len = fils.length;
			for (int j = 0; j < fils_len; ++j) {
				Io_url fil = fils[j];
				try {
					Xol_lang lang = app.Lang_mgr().Get_by_key_or_new(Bry_.new_utf8_(fil.NameOnly()));
					i18n_parser.Load_msgs(true, lang, fil);
				}	catch (Exception exc) {Err_.Noop(exc); Tfds.WriteText(String_.Format("failed to parse json file; url={0} err={1}", fil.Raw(), Err_.Message_gplx_brief(exc)));}
			}
		}
	}
	public void Parse_core(String text, Xol_lang lang, Bry_bfr bfr, Xol_lang_transform lang_transform) {
		evaluator.Clear();
		parser.Parse_tkns(text, evaluator);
		Php_line[] lines = (Php_line[])evaluator.List().Xto_ary(Php_line.class);
		int lines_len = lines.length;
		ListAdp bry_list = ListAdp_.new_();
		for (int i = 0; i < lines_len; i++) {
			Php_line_assign line = (Php_line_assign)lines[i];
			byte[] key = line.Key().Val_obj_bry();
			Object o = Tid_hash.Get_by_bry(key);
			if (o != null) {
				Byte_obj_val stub = (Byte_obj_val)o;
				switch (stub.Val()) {
					case Tid_namespaceNames:
						Parse_array_kv(bry_list, line);
						lang.Ns_names().Ary_set_(Parse_namespace_strings(bry_list, true));
						break;
					case Tid_namespaceAliases:
						Parse_array_kv(bry_list, line);
						lang.Ns_aliases().Ary_set_(Parse_namespace_strings(bry_list, false));
						break;
					case Tid_specialPageAliases:
						Parse_specials(line, lang.Key_bry(), lang.Specials_mgr());
						break;
					case Tid_magicwords:
						Parse_magicwords(line, lang.Key_bry(), lang.Kwd_mgr(), false, lang_transform);
						break;
					case Tid_messages:		
						Parse_array_kv(bry_list, line);
						Parse_messages(bry_list, lang, bfr);
						break;
					case Tid_fallback:
						byte[] fallback_bry = Php_itm_.Parse_bry(line.Val());
						if (!Bry_.Eq(fallback_bry, Bool_.True_bry))	// MessagesEn.php has fallback = false; ignore
							lang.Fallback_bry_(fallback_bry);
						break;
					case Tid_rtl:
						byte[] rtl_bry = Php_itm_.Parse_bry(line.Val());
						boolean dir_rtl = Bry_.Eq(rtl_bry, Bool_.True_bry);
						lang.Dir_ltr_(!dir_rtl);
						break;
					case Tid_separatorTransformTable:
						Parse_separatorTransformTable(line, lang.Num_mgr());
						break;
					case Tid_digitTransformTable:
						Parse_digitTransformTable(line, lang.Num_mgr());
						break;
					case Tid_digitGroupingPattern:
						byte[] digitGroupingPattern = Php_itm_.Parse_bry(line.Val());
						lang.Num_mgr().Num_grp_fmtr().Digit_grouping_pattern_(digitGroupingPattern);
						break;
				}
			}
		}
		lang.Evt_lang_changed();
	}
	// public static String[] Lang_skip = new String[] {"qqq", "enrtl", "akz", "sxu", "test", "mwv", "rup", "hu-formal", "tzm", "bbc", "bbc-latn", "lrc", "ttt", "gom", "gom-latn"};
	public static String[] Lang_skip = String_.Ary_empty;
	public void Parse_xtn(String text, Io_url url, Xoa_app app, Bry_bfr bfr, boolean prepend_hash, Xol_lang_transform lang_transform) {
		evaluator.Clear();
		parser.Parse_tkns(text, evaluator);
		ListAdp bry_list = ListAdp_.new_();
		Php_line[] lines = (Php_line[])evaluator.List().Xto_ary(Php_line.class);
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; i++) {
			Php_line_assign line = (Php_line_assign)lines[i];
			byte[] key = line.Key().Val_obj_bry();
			Object o = Tid_hash.Get_by_bry(key);
			if (o != null) {
				Php_key[] subs = line.Key_subs();
				if (subs.length == 0) continue;	// ignore if no lang_key; EX: ['en']
				byte[] lang_key = subs[0].Val_obj_bry();
				try {
					if (String_.In(String_.new_utf8_(lang_key), Lang_skip)) continue;
					Xol_lang lang = app.Lang_mgr().Get_by_key_or_new(lang_key);
					Byte_obj_val stub = (Byte_obj_val)o;
					switch (stub.Val()) {
						case Tid_messages:		
							Parse_array_kv(bry_list, line);
							Parse_messages(bry_list, lang, bfr);
							break;
						case Tid_magicwords:
							if (line.Key_subs().length == 0) continue;	// ignore lines like $magicWords = array();
							if (line.Key_subs().length > 1) throw Err_mgr._.fmt_(GRP_KEY, "parse_xtn", "magicWords in xtn must have only 1 accessor", line.Key_subs().length);
							Php_key accessor = line.Key_subs()[0];
							byte[] accessor_bry = accessor.Val_obj_bry();
							if (Bry_.Eq(accessor_bry, lang_key))	// accessor must match lang_key
								Parse_magicwords(line, lang.Key_bry(), lang.Kwd_mgr(), prepend_hash, lang_transform); 
							break;
					}
				} catch (Exception exc) {Err_.Noop(exc); Tfds.WriteText("failed to parse " + url.NameOnly() + Err_.Message_gplx_brief(exc) + "\n");}
			}
		}
	}
	private void Parse_array_kv(ListAdp rv, Php_line_assign line) {
		rv.Clear();
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			rv.Add(Php_itm_.Parse_bry(kv.Key()));
			rv.Add(Php_itm_.Parse_bry(kv.Val()));
		}		
	}
	public Xow_ns[] Parse_namespace_strings(ListAdp list, boolean ns_names) {
		byte[][] brys = (byte[][])list.Xto_ary(byte[].class);
		int brys_len = brys.length;
		Xow_ns[] rv = new Xow_ns[brys_len / 2];
		int key_dif = ns_names ? 0 : 1;
		int val_dif = ns_names ? 1 : 0;
		for (int i = 0; i < brys_len; i+=2) {
			byte[] kv_key = brys[i + key_dif];
			byte[] kv_val = brys[i + val_dif];
			Bry_.Replace_all_direct(kv_val, Byte_ascii.Underline, Byte_ascii.Space); // NOTE: siteInfo.xml names have " " not "_" (EX: "User talk"). for now, follow that convention
			int ns_id = Id_by_mw_name(kv_key);
//				if (ns_id == Xow_ns_.Id_null) throw Err_mgr._.fmt_auto_(GRP_KEY, "namespace_names", String_.new_utf8_(kv_key));
			rv[i / 2] = new Xow_ns(ns_id, Xow_ns_case_.Id_1st, kv_val, false);	// note that Xow_ns is being used as glorified id-name struct; case_match and alias values do not matter
		}
		return rv;
	}
	private void Parse_messages(ListAdp rv, Xol_lang lang, Bry_bfr bfr) {
		byte[][] brys = (byte[][])rv.Xto_ary(byte[].class);
		int brys_len = brys.length;
		Xol_msg_mgr mgr = lang.Msg_mgr();
		ListAdp quote_itm_list = ListAdp_.new_(); Byte_obj_ref quote_parse_result = Byte_obj_ref.zero_(); 
		for (int i = 0; i < brys_len; i+=2) {
			byte[] kv_key = brys[i];
			Xol_msg_itm itm = mgr.Itm_by_key_or_new(kv_key);
			if (itm == null) continue;
			byte[] kv_val = brys[i + 1];
			kv_val = php_quote_parser.Parse_as_bry(quote_itm_list, kv_val, quote_parse_result, bfr);
			Xol_msg_itm_.update_val_(itm, kv_val);
			itm.Dirty_(true);
		}
	}	private Php_text_itm_parser php_quote_parser = new Php_text_itm_parser();
	private void Parse_magicwords(Php_line_assign line, byte[] lang_key, Xol_kwd_mgr mgr, boolean prepend_hash, Xol_lang_transform lang_transform) {
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Php_itm_sub sub = ary.Subs_get(i);
			Php_itm_kv kv = (Php_itm_kv)sub;
			byte[] kv_key = kv.Key().Val_obj_bry();
			Php_itm_ary kv_ary = (Php_itm_ary)kv.Val();
			int kv_ary_len = kv_ary.Subs_len();
			boolean case_match = false;								// if 1 arg, default to false
			int kv_ary_bgn = 0; int words_len = kv_ary_len;			// if 1 arg, default to entire kv_ary; words_len
			int case_match_int = Php_itm_.Parse_int_or(kv_ary.Subs_get(0), Int_.MinValue);
			if (case_match_int != Int_.MinValue) {
				case_match = Parse_int_as_bool(kv_ary.Subs_get(0));	// arg[0] is case_match
				kv_ary_bgn = 1;										// arg[1] is 1st word
				words_len = kv_ary_len - 1;							// words.len = kv_len - 1 (skip case_match
			}
			byte[][] words = new byte[words_len][];
			for (int j = kv_ary_bgn; j < kv_ary_len; j++) {
				Php_itm_sub kv_sub = kv_ary.Subs_get(j);
				byte[] word = Php_itm_.Parse_bry(kv_sub);
//					if (prepend_hash && word[0] != Byte_ascii.Hash) word = Bry_.Add(Byte_ascii.Hash, word);
				words[j - kv_ary_bgn] = lang_transform.Kwd_transform(lang_key, kv_key, word);
			}
			int keyword_id = Xol_kwd_grp_.Id_by_bry(kv_key); if (keyword_id == -1) continue; //throw Err_mgr._.fmt_(Err_scope_keywords, "invalid_key", "key does not have id", String_.new_utf8_(kv_key));
			Xol_kwd_grp grp = mgr.Get_or_new(keyword_id);
			grp.Srl_load(case_match, words);
		}
	}
	private void Parse_specials(Php_line_assign line, byte[] lang_key, Xol_specials_mgr specials_mgr) {
		specials_mgr.Clear(); // NOTE: always clear, else will try to readd to en.gfs
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Php_itm_sub sub = ary.Subs_get(i);
			Php_itm_kv kv = (Php_itm_kv)sub;
			byte[] kv_key = kv.Key().Val_obj_bry();
			Php_itm_ary kv_ary = (Php_itm_ary)kv.Val();
			int kv_ary_len = kv_ary.Subs_len();
			byte[][] aliases = new byte[kv_ary_len][];
			for (int j = 0; j < kv_ary_len; j++) {
				Php_itm_sub kv_sub = kv_ary.Subs_get(j);
				aliases[j] = Php_itm_.Parse_bry(kv_sub);
			}
			specials_mgr.Add(kv_key, aliases);
		}
	}
	private boolean Parse_int_as_bool(Php_itm itm) {
		int rv = Php_itm_.Parse_int_or(itm, Int_.MinValue);
		if (rv == Int_.MinValue) throw Err_mgr._.fmt_(GRP_KEY, "parse_int_as_bool", "value must be 0 or 1", String_.new_utf8_(itm.Val_obj_bry()));
		return rv == 1;
	}
	private void Parse_separatorTransformTable(Php_line_assign line, Xol_num_mgr num_mgr) {
		if (line.Val().Itm_tid() == Php_itm_.Tid_null) return;// en is null; $separatorTransformTable = null;
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		ListAdp tmp_list = ListAdp_.new_(); Byte_obj_ref tmp_result = Byte_obj_ref.zero_(); Bry_bfr tmp_bfr = Bry_bfr.reset_(16); 
		for (int i = 0; i < subs_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			byte[] key_bry = Php_itm_.Parse_bry(kv.Key()), val_bry = Php_itm_.Parse_bry(kv.Val());
			val_bry = php_quote_parser.Parse_as_bry(tmp_list, val_bry, tmp_result, tmp_bfr);
			Xol_csv_parser._.Load(tmp_bfr, val_bry);
			val_bry = tmp_bfr.Xto_bry_and_clear();
			if 	(	Bry_.Eq(key_bry, Bry_separatorTransformTable_dot)
				||	Bry_.Eq(key_bry, Bry_separatorTransformTable_comma)
				)
				num_mgr.Separators_mgr().Set(key_bry, val_bry);
			else throw Err_mgr._.unhandled_(String_.new_utf8_(key_bry));	// NOTE: as of v1.22.2, all Messages only have a key of "." or "," DATE:2014-04-15
		}
	}	private static final byte[] Bry_separatorTransformTable_comma = new byte[] {Byte_ascii.Comma}, Bry_separatorTransformTable_dot = new byte[] {Byte_ascii.Dot};
	private void Parse_digitTransformTable(Php_line_assign line, Xol_num_mgr num_mgr) {
		if (line.Val().Itm_tid() == Php_itm_.Tid_null) return;// en is null; $digitTransformTable = null;
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		ListAdp tmp_list = ListAdp_.new_(); Byte_obj_ref tmp_result = Byte_obj_ref.zero_(); Bry_bfr tmp_bfr = Bry_bfr.reset_(16); 
		for (int i = 0; i < subs_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			byte[] key_bry = Php_itm_.Parse_bry(kv.Key()), val_bry = Php_itm_.Parse_bry(kv.Val());
			val_bry = php_quote_parser.Parse_as_bry(tmp_list, val_bry, tmp_result, tmp_bfr);
			num_mgr.Digits_mgr().Set(key_bry, val_bry);
		}
	}
	private static final String GRP_KEY = "xowa.lang.parser";
	private static final byte 
	  Tid_namespaceNames = 0, Tid_namespaceAliases = 1, Tid_specialPageAliases = 2
	, Tid_messages = 3, Tid_magicwords = 4
	, Tid_fallback = 5, Tid_rtl = 6
	, Tid_separatorTransformTable = 7, Tid_digitTransformTable = 8, Tid_digitGroupingPattern = 9
	;
	private static Hash_adp_bry Tid_hash = Hash_adp_bry.cs_()
	.Add_str_byte("namespaceNames", Tid_namespaceNames).Add_str_byte("namespaceAliases", Tid_namespaceAliases).Add_str_byte("specialPageAliases", Tid_specialPageAliases)
	.Add_str_byte("messages", Tid_messages).Add_str_byte("magicWords", Tid_magicwords)
	.Add_str_byte("fallback", Tid_fallback).Add_str_byte("rtl", Tid_rtl)
	.Add_str_byte("separatorTransformTable", Tid_separatorTransformTable)
	.Add_str_byte("digitTransformTable", Tid_digitTransformTable).Add_str_byte("digitGroupingPattern", Tid_digitGroupingPattern)
	;
	public static int Id_by_mw_name(byte[] src) {
		if (mw_names == null) {
			mw_names = Btrie_slim_mgr.cs_();
			mw_names.Add_obj("NS_MEDIA", Int_obj_val.new_(Xow_ns_.Id_media));
			mw_names.Add_obj("NS_SPECIAL", Int_obj_val.new_(Xow_ns_.Id_special));
			mw_names.Add_obj("NS_MAIN", Int_obj_val.new_(Xow_ns_.Id_main));
			mw_names.Add_obj("NS_TALK", Int_obj_val.new_(Xow_ns_.Id_talk));
			mw_names.Add_obj("NS_USER", Int_obj_val.new_(Xow_ns_.Id_user));
			mw_names.Add_obj("NS_USER_TALK", Int_obj_val.new_(Xow_ns_.Id_user_talk));
			mw_names.Add_obj("NS_PROJECT", Int_obj_val.new_(Xow_ns_.Id_project));
			mw_names.Add_obj("NS_PROJECT_TALK", Int_obj_val.new_(Xow_ns_.Id_project_talk));
			mw_names.Add_obj("NS_FILE", Int_obj_val.new_(Xow_ns_.Id_file));
			mw_names.Add_obj("NS_FILE_TALK", Int_obj_val.new_(Xow_ns_.Id_file_talk));
			mw_names.Add_obj("NS_MEDIAWIKI", Int_obj_val.new_(Xow_ns_.Id_mediawiki));
			mw_names.Add_obj("NS_MEDIAWIKI_TALK", Int_obj_val.new_(Xow_ns_.Id_mediaWiki_talk));
			mw_names.Add_obj("NS_TEMPLATE", Int_obj_val.new_(Xow_ns_.Id_template));
			mw_names.Add_obj("NS_TEMPLATE_TALK", Int_obj_val.new_(Xow_ns_.Id_template_talk));
			mw_names.Add_obj("NS_HELP", Int_obj_val.new_(Xow_ns_.Id_help));
			mw_names.Add_obj("NS_HELP_TALK", Int_obj_val.new_(Xow_ns_.Id_help_talk));
			mw_names.Add_obj("NS_CATEGORY", Int_obj_val.new_(Xow_ns_.Id_category));
			mw_names.Add_obj("NS_CATEGORY_TALK", Int_obj_val.new_(Xow_ns_.Id_category_talk));
		}
		Object o = mw_names.Match_exact(src, 0, src.length);
		return o == null ? Xow_ns_.Id_null : ((Int_obj_val)o).Val();
	}	private static Btrie_slim_mgr mw_names;
}
