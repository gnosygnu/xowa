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
package gplx.xowa.langs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.parsers.*;
public class Xobc_utl_make_lang_kwds implements Gfo_invk, Xol_lang_transform {
	private final    Xoa_lang_mgr lang_mgr;
	public Xobc_utl_make_lang_kwds(Xoa_lang_mgr lang_mgr) {this.lang_mgr = lang_mgr;}		
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_keep_trailing_colon))	Parse_keep_trailing_colon(m.ReadBry("langs"), m.ReadBry("text"));
		else if	(ctx.Match(k, Invk_prepend_hash))			Parse_prepend_hash(m.ReadBry("langs"), m.ReadBry("text"));
		else if	(ctx.Match(k, Invk_add_words))				Parse_add_words(m.ReadBry("langs"), m.ReadBry("text"));
		else												return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_keep_trailing_colon = "keep_trailing_colon", Invk_prepend_hash = "prepend_hash", Invk_add_words = "add_words";

	public byte[] Kwd_transform(byte[] lang_key, byte[] kwd_key, byte[] kwd_word) {
		byte[] rv = kwd_word;
		if (!Hash_itm_applies(trailing_colons, lang_key, kwd_key, kwd_word)) {
			int kwd_last = rv.length - 1;
			if (kwd_last > 0 && rv[kwd_last] == Byte_ascii.Colon)
				rv = Bry_.Mid(rv, 0, rv.length - 1);
		}
		if (Hash_itm_applies(prepend_hash, lang_key, kwd_key, kwd_word)) {
			if (rv.length > 0 && rv[0] != Byte_ascii.Hash)
				rv = Bry_.Add(Byte_ascii.Hash, rv);
		}
		return rv;
	}
	public void Add_words() {
		Ordered_hash hash = add_words_hash;
		Ordered_hash tmp = Ordered_hash_.New_bry();
		int hash_len = hash.Count();
		for (int i = 0; i < hash_len; i++) {
			Xobcl_kwd_lang cfg_lang = (Xobcl_kwd_lang)hash.Get_at(i); 
			Xol_lang_itm lang = lang_mgr.Get_by(cfg_lang.Key_bry()); if (lang == null) continue;
			int cfg_grp_len = cfg_lang.Grps().length;
			for (int j = 0; j < cfg_grp_len; j++) {					
				Xobcl_kwd_row cfg_grp = cfg_lang.Grps()[j];
				int kwd_id = Xol_kwd_grp_.Id_by_bry(cfg_grp.Key());
				if (kwd_id == -1) throw Err_.new_wo_type("could not find kwd for key", "key", String_.new_u8(cfg_grp.Key()));
				Xol_kwd_grp kwd_grp = lang.Kwd_mgr().Get_at(kwd_id);
				tmp.Clear();
				if (kwd_grp == null) {
					kwd_grp = lang.Kwd_mgr().Get_or_new(kwd_id);
					kwd_grp.Srl_load(Bool_.N, Bry_.Ary_empty);	// ASSUME: kwd explicitly added, but does not exist in language; default to !case_match
				}

				for (Xol_kwd_itm itm : kwd_grp.Itms())
					tmp.Add(itm.Val(), itm.Val());
				if (cfg_grp.Itms().length == 0) {
					if (!tmp.Has(cfg_grp.Key())) tmp.Add(cfg_grp.Key(), cfg_grp.Key());
				}
				else {
					for (byte[] itm : cfg_grp.Itms()) {
						if (!tmp.Has(itm)) tmp.Add(itm, itm);
					}
				}
				byte[][] words = (byte[][])tmp.To_ary(byte[].class);
				kwd_grp.Srl_load(kwd_grp.Case_match(), words);
			}
		}
	}
	boolean Hash_itm_applies(Ordered_hash hash, byte[] lang_key, byte[] kwd_key, byte[] kwd_word) {
		Xobcl_kwd_lang cfg_lang = (Xobcl_kwd_lang)hash.Get_by(lang_key); if (cfg_lang == null) return false;
		Xobcl_kwd_row cfg_grp = cfg_lang.Grps_get_by_key(kwd_key); if (cfg_grp == null) return false;
		return cfg_grp.Itms().length == 0 || cfg_grp.Itms_has(kwd_word);
	}
	public void Parse_add_words(byte[] langs_bry, byte[] kwds) {Parse(langs_bry, kwds, add_words_hash);}	private Ordered_hash add_words_hash = Ordered_hash_.New_bry();
	public void Parse_prepend_hash(byte[] langs_bry, byte[] kwds) {Parse(langs_bry, kwds, prepend_hash);}	private Ordered_hash prepend_hash = Ordered_hash_.New_bry();
	public void Parse_keep_trailing_colon(byte[] langs_bry, byte[] kwds) {Parse(langs_bry, kwds, trailing_colons);}	private Ordered_hash trailing_colons = Ordered_hash_.New_bry();
	private void Parse(byte[] langs_bry, byte[] kwds, Ordered_hash hash) {
		Xobcl_kwd_row[] itms = Parse(kwds);
		Xol_lang_stub[] stub_ary = Xol_lang_stub_.Ary(); // NOTE: was "lang_mgr.To_hash(langs_bry);" which was effectively "wiki_types" -> all langs; DATE:2015-10-07
		int len = stub_ary.length;
		for (int i = 0; i < len; ++i) {
			Xol_lang_stub stub_itm = stub_ary[i];
			byte[] key = stub_itm.Key();
			Xobcl_kwd_lang grp = (Xobcl_kwd_lang)hash.Get_by(key);
			if (grp == null) {
				grp = new Xobcl_kwd_lang(key, itms); 
				hash.Add(key, grp);
			}
			else
				grp.Merge(itms);
		}
	}
	@gplx.Internal protected static Xobcl_kwd_row[] Parse(byte[] src) {
		int src_len = src.length, pos = 0, fld_bgn = 0;
		byte[] cur_key = Bry_.Empty;
		Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		List_adp rv = List_adp_.New(); int fld_idx = 0;
		while (true) {
			boolean last = pos == src_len;	// NOTE: logic occurs b/c of \n}~-> dlm which gobbles up last \n
			byte b = last ? Byte_ascii.Nl : src[pos];
			switch (b) {
				case Byte_ascii.Pipe:
					cur_key = csv_parser.Load(src, fld_bgn, pos);
					fld_bgn = pos + 1;
					++fld_idx;
					break;
				case Byte_ascii.Nl:
					if (pos - fld_bgn > 0 || fld_idx == 1) {
						byte[] cur_val = csv_parser.Load(src, fld_bgn, pos);
						Xobcl_kwd_row row = new Xobcl_kwd_row(cur_key, Bry_split_.Split(cur_val, Byte_ascii.Tilde));
						rv.Add(row);
					}
					fld_bgn = pos + 1;
					fld_idx = 0;
					break;
				default:
					break;
			}
			if (last) break;
			++pos;
		}		
		return (Xobcl_kwd_row[])rv.To_ary(Xobcl_kwd_row.class);
	}
}
class Xobcl_kwd_lang {
	public Xobcl_kwd_lang(byte[] key_bry, Xobcl_kwd_row[] grps) {
		this.key_bry = key_bry; this.grps = grps;
		for (Xobcl_kwd_row grp : grps)
			grps_hash.Add(grp.Key(), grp);
	}
	public void Merge(Xobcl_kwd_row[] v) {
		grps = (Xobcl_kwd_row[])Array_.Resize_add(grps, v);
		for (Xobcl_kwd_row grp : v) {
			grps_hash.Add_if_dupe_use_nth(grp.Key(), grp);	// NOTE: Add_if_dupe_use_nth instead of Add b/c kwds may be expanded; EX: lst is added to all langs but de requires #lst~#section~Abschnitt~; DATE:2013-06-02
		}
	}
	public Xobcl_kwd_row Grps_get_by_key(byte[] key) {return (Xobcl_kwd_row)grps_hash.Get_by(key);} private Ordered_hash grps_hash = Ordered_hash_.New_bry();
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public Xobcl_kwd_row[] Grps() {return grps;} private Xobcl_kwd_row[] grps;
}
