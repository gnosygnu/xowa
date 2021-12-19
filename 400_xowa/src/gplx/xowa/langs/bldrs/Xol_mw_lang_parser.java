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
package gplx.xowa.langs.bldrs;
import gplx.Yn;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.core.consoles.Console_adp__sys;
import gplx.core.log_msgs.Gfo_msg_log;
import gplx.langs.phps.Php_evaluator;
import gplx.langs.phps.Php_itm;
import gplx.langs.phps.Php_itm_;
import gplx.langs.phps.Php_itm_ary;
import gplx.langs.phps.Php_itm_kv;
import gplx.langs.phps.Php_itm_sub;
import gplx.langs.phps.Php_key;
import gplx.langs.phps.Php_line;
import gplx.langs.phps.Php_line_assign;
import gplx.langs.phps.Php_parser;
import gplx.langs.phps.Php_text_itm_parser;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.ByteRef;
import gplx.types.basics.wrappers.ByteVal;
import gplx.types.basics.wrappers.IntVal;
import gplx.types.errs.ErrUtl;
import gplx.xowa.apps.fsys.Xoa_fsys_mgr;
import gplx.xowa.apps.gfs.Xoa_gfs_bldr;
import gplx.xowa.langs.Xoa_lang_mgr;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.kwds.Xol_kwd_grp;
import gplx.xowa.langs.kwds.Xol_kwd_grp_;
import gplx.xowa.langs.kwds.Xol_kwd_mgr;
import gplx.xowa.langs.msgs.Xol_msg_itm;
import gplx.xowa.langs.msgs.Xol_msg_itm_;
import gplx.xowa.langs.msgs.Xol_msg_mgr;
import gplx.xowa.langs.numbers.Xol_num_mgr;
import gplx.xowa.langs.parsers.Xol_csv_parser;
import gplx.xowa.langs.parsers.Xol_lang_srl;
import gplx.xowa.langs.specials.Xol_specials_mgr;
import gplx.xowa.wikis.nss.Xow_ns;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.wikis.nss.Xow_ns_case_;
public class Xol_mw_lang_parser {
	private Php_parser parser = new Php_parser(); private Php_evaluator evaluator;
	public Xol_mw_lang_parser(Gfo_msg_log msg_log) {evaluator = new Php_evaluator(msg_log);}
	public void Bld_all(Xoa_lang_mgr lang_mgr, Xoa_fsys_mgr fsys_mgr) {Bld_all(lang_mgr, fsys_mgr, Xol_lang_transform_null.Instance);}
	public void Bld_all(Xoa_lang_mgr lang_mgr, Xoa_fsys_mgr fsys_mgr, Xol_lang_transform lang_transform) {
		Io_url lang_root = fsys_mgr.Cfg_lang_core_dir().OwnerDir();
		Parse_mediawiki(lang_mgr, lang_root.GenSubDir("mediawiki"), lang_transform);
		Save_langs(lang_mgr, lang_root.GenSubDir(Xol_mw_lang_parser.Dir_name_core), Ordered_hash_.New_bry(), Ordered_hash_.New_bry());
	}
	public void Save_langs(Xoa_lang_mgr lang_mgr, Io_url xowa_root, Ordered_hash manual_text_bgn, Ordered_hash manual_text_end) {
		int len = lang_mgr.Len();
		Xoa_gfs_bldr bldr = new Xoa_gfs_bldr();
		for (int i = 0; i < len; i++) {
			Xol_lang_itm lang = lang_mgr.Get_at(i);
			String lang_key = lang.Key_str();
			Io_url lang_url = xowa_root.GenSubFil(lang_key + ".gfs");
			Save_langs_by_manual_text(bldr, manual_text_bgn, lang_key);

			Xol_lang_srl.Save_num_mgr(bldr, lang.Num_mgr());
			bldr.Add_proc_init_many("this").Add_nl();
			if (lang.Fallback_bry() != null)	// NOTE: fallback will often be null; EX: en
				bldr.Add_proc_cont_one(Xol_lang_itm.Invk_fallback_load).Add_parens_str(lang.Fallback_bry()).Add_nl();
			if (!lang.Dir_ltr())				// NOTE: only save dir_ltr if false; EX: en
				bldr.Add_proc_cont_one(Xol_lang_itm.Invk_dir_rtl_).Add_parens_str(Yn.To_str(!lang.Dir_ltr())).Add_nl();
			Xol_lang_srl.Save_ns_grps(bldr, lang.Ns_names(), Xol_lang_itm.Invk_ns_names);
			Xol_lang_srl.Save_ns_grps(bldr, lang.Ns_aliases(), Xol_lang_itm.Invk_ns_aliases);
			Xol_lang_srl.Save_specials(bldr, lang.Specials_mgr());
			Xol_lang_srl.Save_keywords(bldr, lang.Kwd_mgr());
			Xol_lang_srl.Save_messages(bldr, lang.Msg_mgr(), true);
			bldr.Add_term_nl();

			Save_langs_by_manual_text(bldr, manual_text_end, lang_key);
			Io_mgr.Instance.SaveFilBfr(lang_url, bldr.Bfr());
		}
	}
	private void Save_langs_by_manual_text(Xoa_gfs_bldr bldr, Ordered_hash manual_text_hash, String lang_key) {
		byte[][] itm = (byte[][])manual_text_hash.GetByOrNull(BryUtl.NewU8(lang_key));
		if (itm != null) bldr.Bfr().Add(itm[1]);
	}
	public void Parse_mediawiki(Xoa_lang_mgr lang_mgr, Io_url mediawiki_root, Xol_lang_transform lang_transform) {
		BryWtr bfr = BryWtr.New();
		Parse_file_core_php(lang_mgr, mediawiki_root, bfr, lang_transform);
		Parse_file_xtns_php(lang_mgr, mediawiki_root, bfr, lang_transform);
		Parse_file_json(lang_mgr, bfr, lang_transform, mediawiki_root.GenSubDir("core_json"));
		Parse_file_json(lang_mgr, bfr, lang_transform, mediawiki_root.GenSubDir("xtns_json"));
	}
	private void Parse_file_core_php(Xoa_lang_mgr lang_mgr, Io_url mediawiki_root, BryWtr bfr, Xol_lang_transform lang_transform) {
		Io_url dir = mediawiki_root.GenSubDir("core_php");
		Io_url[] urls = Io_mgr.Instance.QueryDir_fils(dir);
		int len = urls.length;
		for (int i = 0; i < len; i++) {
			Io_url url = urls[i];
			try {
				String lang_key = StringUtl.Replace(StringUtl.Lower(StringUtl.Mid(url.NameOnly(), 8)), "_", "-");	// 8=Messages.length; lower b/c format is MessagesEn.php (need "en")
				// if (String_.In(lang_key, "qqq", "enrtl", "bbc", "bbc-latn")) continue;
				String text = Io_mgr.Instance.LoadFilStr(url);
				Xol_lang_itm lang = lang_mgr.Get_by_or_new(BryUtl.NewU8(lang_key)); // NOTE: cannot be Get_by_or_load else will load from XO; actual definitions will be loaded from MW below
				this.Parse_core(text, lang, bfr, lang_transform);
			} catch (Exception exc) {Console_adp__sys.Instance.Write_str_w_nl("failed to parse " + url.NameOnly() + ErrUtl.ToStrLog(exc));}
		}
	}
	private void Parse_file_xtns_php(Xoa_lang_mgr lang_mgr, Io_url mediawiki_root, BryWtr bfr, Xol_lang_transform lang_transform) {
		Io_url dir = mediawiki_root.GenSubDir("xtns_php");
		Io_url[] urls = Io_mgr.Instance.QueryDir_fils(dir);
		int len = urls.length;
		for (int i = 0; i < len; i++) {
			Io_url url = urls[i];
			try {
			String text = Io_mgr.Instance.LoadFilStr(url);
			boolean prepend_hash = StringUtl.Eq("ParserFunctions.i18n.magic", url.NameOnly());
			this.Parse_xtn(text, url, lang_mgr, bfr, prepend_hash, lang_transform);
			} catch (Exception exc) {Console_adp__sys.Instance.Write_str_w_nl("failed to parse " + url.NameOnly() + ErrUtl.ToStrLog(exc));}
		}
	}
	private void Parse_file_json(Xoa_lang_mgr lang_mgr, BryWtr bfr, Xol_lang_transform lang_transform, Io_url root_dir) {
		Io_url[] dirs = Io_mgr.Instance.QueryDir_args(root_dir).DirOnly_().ExecAsUrlAry();
		int dirs_len = dirs.length;
		for (int i = 0; i < dirs_len; i++) {
			Io_url dir = dirs[i];
			Io_url[] fils = Io_mgr.Instance.QueryDir_args(dir).ExecAsUrlAry();
			int fils_len = fils.length;
			for (int j = 0; j < fils_len; ++j) {
				Io_url fil = fils[j];
				try {
					Xol_lang_itm lang = lang_mgr.Get_by_or_new(BryUtl.NewU8(fil.NameOnly()));
					Xob_i18n_parser.Load_msgs(true, lang, fil);
				}	catch (Exception exc) {Console_adp__sys.Instance.Write_str_w_nl(StringUtl.Format("failed to parse json file; url={0} err={1}\n", fil.Raw(), ErrUtl.ToStrLog(exc)));}
			}
		}
	}
	public void Parse_core(String text, Xol_lang_itm lang, BryWtr bfr, Xol_lang_transform lang_transform) {
		evaluator.Clear();
		parser.Parse_tkns(text, evaluator);
		Php_line[] lines = (Php_line[])evaluator.List().ToAry(Php_line.class);
		int lines_len = lines.length;
		List_adp bry_list = List_adp_.New();
		for (int i = 0; i < lines_len; i++) {
			Php_line_assign line = (Php_line_assign)lines[i];
			byte[] key = line.Key().Val_obj_bry();
			Object o = Tid_hash.Get_by_bry(key);
			if (o != null) {
				ByteVal stub = (ByteVal)o;
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
						if (!BryLni.Eq(fallback_bry, BoolUtl.TrueBry))	// MessagesEn.php has fallback = false; ignore
							lang.Fallback_bry_(fallback_bry);
						break;
					case Tid_rtl:
						byte[] rtl_bry = Php_itm_.Parse_bry(line.Val());
						boolean dir_rtl = BryLni.Eq(rtl_bry, BoolUtl.TrueBry);
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
	public static String[] Lang_skip = StringUtl.AryEmpty;
	public void Parse_xtn(String text, Io_url url, Xoa_lang_mgr lang_mgr, BryWtr bfr, boolean prepend_hash, Xol_lang_transform lang_transform) {
		evaluator.Clear();
		parser.Parse_tkns(text, evaluator);
		List_adp bry_list = List_adp_.New();
		Php_line[] lines = (Php_line[])evaluator.List().ToAry(Php_line.class);
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
					if (StringUtl.In(StringUtl.NewU8(lang_key), Lang_skip)) continue;
					Xol_lang_itm lang = lang_mgr.Get_by_or_new(lang_key);
					ByteVal stub = (ByteVal)o;
					switch (stub.Val()) {
						case Tid_messages:		
							Parse_array_kv(bry_list, line);
							Parse_messages(bry_list, lang, bfr);
							break;
						case Tid_magicwords:
							if (line.Key_subs().length == 0) continue;	// ignore lines like $magicWords = array();
							if (line.Key_subs().length > 1) throw ErrUtl.NewArgs("magicWords in xtn must have only 1 accessor", "len", line.Key_subs().length);
							Php_key accessor = line.Key_subs()[0];
							byte[] accessor_bry = accessor.Val_obj_bry();
							if (BryLni.Eq(accessor_bry, lang_key))	// accessor must match lang_key
								Parse_magicwords(line, lang.Key_bry(), lang.Kwd_mgr(), prepend_hash, lang_transform); 
							break;
					}
				} catch (Exception exc) {Console_adp__sys.Instance.Write_str_w_nl("failed to parse " + url.NameOnly() + ErrUtl.ToStrLog(exc));}
			}
		}
	}
	private void Parse_array_kv(List_adp rv, Php_line_assign line) {
		rv.Clear();
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			rv.Add(Php_itm_.Parse_bry(kv.Key()));
			rv.Add(Php_itm_.Parse_bry(kv.Val()));
		}		
	}
	public Xow_ns[] Parse_namespace_strings(List_adp list, boolean ns_names) {
		byte[][] brys = (byte[][])list.ToAry(byte[].class);
		int brys_len = brys.length;
		Xow_ns[] rv = new Xow_ns[brys_len / 2];
		int key_dif = ns_names ? 0 : 1;
		int val_dif = ns_names ? 1 : 0;
		for (int i = 0; i < brys_len; i+=2) {
			byte[] kv_key = brys[i + key_dif];
			byte[] kv_val = brys[i + val_dif];
			BryUtl.ReplaceAllDirect(kv_val, AsciiByte.Underline, AsciiByte.Space); // NOTE: siteInfo.xml names have " " not "_" (EX: "User talk"). for now, follow that convention
			int ns_id = Id_by_mw_name(kv_key);
//				if (ns_id == Xow_ns_.Tid__null) throw Err_mgr.Instance.fmt_auto_(GRP_KEY, "namespace_names", String_.new_u8(kv_key));
			rv[i / 2] = new Xow_ns(ns_id, Xow_ns_case_.Tid__1st, kv_val, false);	// note that Xow_ns is being used as glorified id-name struct; case_match and alias values do not matter
		}
		return rv;
	}
	private void Parse_messages(List_adp rv, Xol_lang_itm lang, BryWtr bfr) {
		byte[][] brys = (byte[][])rv.ToAry(byte[].class);
		int brys_len = brys.length;
		Xol_msg_mgr mgr = lang.Msg_mgr();
		List_adp quote_itm_list = List_adp_.New(); ByteRef quote_parse_result = ByteRef.NewZero();
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
			int case_match_int = Php_itm_.Parse_int_or(kv_ary.Subs_get(0), IntUtl.MinValue);
			if (case_match_int != IntUtl.MinValue) {
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
			int keyword_id = Xol_kwd_grp_.Id_by_bry(kv_key); if (keyword_id == -1) continue; //throw Err_mgr.Instance.fmt_(Err_scope_keywords, "invalid_key", "key does not have id", String_.new_u8(kv_key));
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
		int rv = Php_itm_.Parse_int_or(itm, IntUtl.MinValue);
		if (rv == IntUtl.MinValue) throw ErrUtl.NewArgs("value must be 0 or 1", "val", StringUtl.NewU8(itm.Val_obj_bry()));
		return rv == 1;
	}
	private void Parse_separatorTransformTable(Php_line_assign line, Xol_num_mgr num_mgr) {
		if (line.Val().Itm_tid() == Php_itm_.Tid_null) return;// en is null; $separatorTransformTable = null;
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		List_adp tmp_list = List_adp_.New(); ByteRef tmp_result = ByteRef.NewZero(); BryWtr tmp_bfr = BryWtr.NewAndReset(16);
		for (int i = 0; i < subs_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			byte[] key_bry = Php_itm_.Parse_bry(kv.Key()), val_bry = Php_itm_.Parse_bry(kv.Val());
			val_bry = php_quote_parser.Parse_as_bry(tmp_list, val_bry, tmp_result, tmp_bfr);
			Xol_csv_parser.Instance.Load(tmp_bfr, val_bry);
			val_bry = tmp_bfr.ToBryAndClear();
			if 	(	BryLni.Eq(key_bry, Bry_separatorTransformTable_dot)
				||	BryLni.Eq(key_bry, Bry_separatorTransformTable_comma)
				)
				num_mgr.Separators_mgr().Set(key_bry, val_bry);
			else throw ErrUtl.NewUnhandled(StringUtl.NewU8(key_bry));	// NOTE: as of v1.22.2, all Messages only have a key of "." or "," DATE:2014-04-15
		}
	}	private static final byte[] Bry_separatorTransformTable_comma = new byte[] {AsciiByte.Comma}, Bry_separatorTransformTable_dot = new byte[] {AsciiByte.Dot};
	private void Parse_digitTransformTable(Php_line_assign line, Xol_num_mgr num_mgr) {
		if (line.Val().Itm_tid() == Php_itm_.Tid_null) return;// en is null; $digitTransformTable = null;
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		List_adp tmp_list = List_adp_.New(); ByteRef tmp_result = ByteRef.NewZero(); BryWtr tmp_bfr = BryWtr.NewAndReset(16);
		for (int i = 0; i < subs_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			byte[] key_bry = Php_itm_.Parse_bry(kv.Key()), val_bry = Php_itm_.Parse_bry(kv.Val());
			val_bry = php_quote_parser.Parse_as_bry(tmp_list, val_bry, tmp_result, tmp_bfr);
			num_mgr.Digits_mgr().Set(key_bry, val_bry);
		}
	}
	private static final byte 
	  Tid_namespaceNames = 0, Tid_namespaceAliases = 1, Tid_specialPageAliases = 2
	, Tid_messages = 3, Tid_magicwords = 4
	, Tid_fallback = 5, Tid_rtl = 6
	, Tid_separatorTransformTable = 7, Tid_digitTransformTable = 8, Tid_digitGroupingPattern = 9
	;
	private static Hash_adp_bry Tid_hash = Hash_adp_bry.cs()
	.Add_str_byte("namespaceNames", Tid_namespaceNames).Add_str_byte("namespaceAliases", Tid_namespaceAliases).Add_str_byte("specialPageAliases", Tid_specialPageAliases)
	.Add_str_byte("messages", Tid_messages).Add_str_byte("magicWords", Tid_magicwords)
	.Add_str_byte("fallback", Tid_fallback).Add_str_byte("rtl", Tid_rtl)
	.Add_str_byte("separatorTransformTable", Tid_separatorTransformTable)
	.Add_str_byte("digitTransformTable", Tid_digitTransformTable).Add_str_byte("digitGroupingPattern", Tid_digitGroupingPattern)
	;
	public static int Id_by_mw_name(byte[] src) {
		if (mw_names == null) {
			mw_names = Btrie_slim_mgr.cs();
			mw_names.AddObj("NS_MEDIA", new IntVal(Xow_ns_.Tid__media));
			mw_names.AddObj("NS_SPECIAL", new IntVal(Xow_ns_.Tid__special));
			mw_names.AddObj("NS_MAIN", new IntVal(Xow_ns_.Tid__main));
			mw_names.AddObj("NS_TALK", new IntVal(Xow_ns_.Tid__talk));
			mw_names.AddObj("NS_USER", new IntVal(Xow_ns_.Tid__user));
			mw_names.AddObj("NS_USER_TALK", new IntVal(Xow_ns_.Tid__user_talk));
			mw_names.AddObj("NS_PROJECT", new IntVal(Xow_ns_.Tid__project));
			mw_names.AddObj("NS_PROJECT_TALK", new IntVal(Xow_ns_.Tid__project_talk));
			mw_names.AddObj("NS_FILE", new IntVal(Xow_ns_.Tid__file));
			mw_names.AddObj("NS_FILE_TALK", new IntVal(Xow_ns_.Tid__file_talk));
			mw_names.AddObj("NS_MEDIAWIKI", new IntVal(Xow_ns_.Tid__mediawiki));
			mw_names.AddObj("NS_MEDIAWIKI_TALK", new IntVal(Xow_ns_.Tid__mediawiki_talk));
			mw_names.AddObj("NS_TEMPLATE", new IntVal(Xow_ns_.Tid__template));
			mw_names.AddObj("NS_TEMPLATE_TALK", new IntVal(Xow_ns_.Tid__template_talk));
			mw_names.AddObj("NS_HELP", new IntVal(Xow_ns_.Tid__help));
			mw_names.AddObj("NS_HELP_TALK", new IntVal(Xow_ns_.Tid__help_talk));
			mw_names.AddObj("NS_CATEGORY", new IntVal(Xow_ns_.Tid__category));
			mw_names.AddObj("NS_CATEGORY_TALK", new IntVal(Xow_ns_.Tid__category_talk));
		}
		Object o = mw_names.Match_exact(src, 0, src.length);
		return o == null ? Xow_ns_.Tid__null : ((IntVal)o).Val();
	}	private static Btrie_slim_mgr mw_names;
	public static final String Dir_name_core = "core";
}
