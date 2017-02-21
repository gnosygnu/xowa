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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
import gplx.xowa.langs.vnts.*;
class Vnt_convert_rule {	// REF.MW: /languages/LanguageConverter.php|ConverterRule
	private final    Vnt_flag_parser flag_parser = new Vnt_flag_parser(); private final    Vnt_flag_code_mgr flag_codes = new Vnt_flag_code_mgr(); private final    Vnt_flag_lang_mgr flag_langs = new Vnt_flag_lang_mgr();
	private final    Vnt_rule_parser rule_parser = new Vnt_rule_parser(); private final    Vnt_rule_undi_mgr rule_undis = new Vnt_rule_undi_mgr(); private final    Vnt_rule_bidi_mgr rule_bidis = new Vnt_rule_bidi_mgr();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();		
	private final    Ordered_hash cnv_marked_hash = Ordered_hash_.New_bry();
	private Vnt_convert_lang converter;
	private Xol_vnt_regy vnt_regy; private byte[] vnt_key;
	private Vnt_log_mgr log_mgr;
	private byte[] rule_raw;
	private boolean rule_parser_init = true;
	public byte[] Display() {return display;} private byte[] display;
	public byte[] Title() {return title;} private byte[] title;
	public byte Action() {return action;} private byte action;
	public Vnt_rule_undi_mgr Cnv_tbl() {return cnv_tbl;} private final    Vnt_rule_undi_mgr cnv_tbl = new Vnt_rule_undi_mgr();
	public Vnt_convert_rule(Vnt_convert_lang converter, Xol_vnt_regy vnt_regy, Vnt_log_mgr log_mgr) {
		this.converter = converter; this.log_mgr = log_mgr;
		this.vnt_regy = vnt_regy;
		flag_parser.Init(log_mgr);
		rule_parser.Init(log_mgr, vnt_regy);
	}
	public void Parse(Xol_vnt_itm vnt_itm, byte[] src, int src_bgn, int src_end) {
		if (rule_parser_init) {	// WORKAROUND: initialized vnt_regy on first parse; initializing during constructor doesn't work b/c vnt_regy == 0 due to load order of gfs script; DATE:2016-11-09
			rule_parser_init = false;
			rule_parser.Init(log_mgr, vnt_regy);
		}
		this.vnt_key = vnt_itm.Key();
		this.display = this.title = null;
		this.action = Byte_ascii.Null;
		int pipe_pos = Bry_find_.Find_fwd(src, Byte_ascii.Pipe, src_bgn, src_end);
		flag_parser.Parse(flag_codes, flag_langs, vnt_regy, src, src_bgn, pipe_pos);
		int rule_bgn = pipe_pos == -1 ? src_bgn : pipe_pos + 1;
		this.rule_raw = Bry_.Mid(src, rule_bgn, src_end);
		int flag_langs_count = flag_langs.Count();
		if (flag_langs_count > 0) {	// vnts exist in flag; EX: -{zh-hans;zh-hant|text}-
			if (flag_langs.Has(vnt_key))
				rule_raw = converter.Auto_convert(vnt_itm, rule_raw);	// convert rule text to current language; EX:-{|convert}-
			else {
				byte[][] fallbacks = vnt_itm.Fallback_ary();
				int fallbacks_len = fallbacks.length;
				for (int i = 0; i < fallbacks_len; ++i) {
					byte[] fallback = fallbacks[i];
					if (flag_langs.Has(fallback)) {
						Xol_vnt_itm fallback_itm = (Xol_vnt_itm)vnt_regy.Get_by(fallback);
						rule_raw = converter.Auto_convert(fallback_itm, rule_raw);
						break;
					}
				}
			}
			flag_codes.Limit(Vnt_flag_code_.Tid_raw);
		}
		rule_parser.Clear(rule_undis, rule_bidis, rule_raw);
		if (!flag_codes.Get(Vnt_flag_code_.Tid_raw) && !flag_codes.Get(Vnt_flag_code_.Tid_name))
			rule_parser.Parse(src, rule_bgn, src_end);
		if (log_mgr != null) log_mgr.Log_rule(src_bgn, src_end, Bry_.Mid(src, src_bgn, src_end), flag_codes, flag_langs, rule_undis, rule_bidis);
		if (rule_undis.Has_none() && rule_bidis.Has_none()) {
			if		(	 flag_codes.Get(Vnt_flag_code_.Tid_add)
					||	 flag_codes.Get(Vnt_flag_code_.Tid_del)
					) {	// fill all variants if text in -{A/H/-|text} without rules
				for (int i = 0; i < flag_langs_count; ++i) {
					Xol_vnt_itm itm = flag_langs.Get_at(i);
					rule_bidis.Set(itm.Key(), rule_raw);
				}
			}
			else if (	!flag_codes.Get(Vnt_flag_code_.Tid_name)
					&&	!flag_codes.Get(Vnt_flag_code_.Tid_title)
					) {
				flag_codes.Limit(Vnt_flag_code_.Tid_raw);
			}
		}
		int flag_count = Vnt_flag_code_.Tid__max;
		for (int flag = 0; flag < flag_count; ++flag) {
			if (!flag_codes.Get(flag)) continue;
			switch (flag) {
				case Vnt_flag_code_.Tid_raw:		display = rule_parser.Raw(); break;		// if we don't do content convert, still strip the -{}- tags
				case Vnt_flag_code_.Tid_name: // process N flag: output current variant name
					byte[] vnt_key_trim = Bry_.Trim(rule_parser.Raw());
					Xol_vnt_itm vnt_itm_trim = vnt_regy.Get_by(vnt_key_trim);
					display = vnt_itm_trim == null ? display = Bry_.Empty : vnt_itm_trim.Name();
					break;
				case Vnt_flag_code_.Tid_descrip:	display = Make_descrip(); break;			// process D flag: output rules description
				case Vnt_flag_code_.Tid_hide:		display = Bry_.Empty; break;				// process H,- flag or T only: output nothing
				case Vnt_flag_code_.Tid_del:		display = Bry_.Empty; action = Byte_ascii.Dash; break;
				case Vnt_flag_code_.Tid_add:		display = Bry_.Empty; action = Byte_ascii.Plus; break;
				case Vnt_flag_code_.Tid_show:		display = Make_converted(vnt_itm); break;
				case Vnt_flag_code_.Tid_title:		display = Bry_.Empty; title = Make_title(vnt_itm); break;
				default:							break;									// ignore unknown flags (but see error case below)
			}
		}
		if (display == null)
			display = Bry_.Add(Bry__error_bgn, Bry__error_end);	// wfMessage( 'converter-manual-rule-error' )->inContentLanguage()->escaped()
		Make_conv_tbl();			
	}
	private void Make_conv_tbl() {
		if (rule_undis.Has_none() && rule_bidis.Has_none()) return; // Special case optimisation
		cnv_tbl.Clear(); cnv_marked_hash.Clear();
		int vnt_regy_len = vnt_regy.Len();
		for (int i = 0; i < vnt_regy_len; ++i) {
			Xol_vnt_itm vnt = vnt_regy.Get_at(i);
			byte[] vnt_key = vnt.Key();
			// bidi: fill in missing variants with fallbacks
			byte[] bidi_bry = rule_bidis.Get_text_by_key_or_null(vnt_key);
			if (bidi_bry == null) {
				bidi_bry = rule_bidis.Get_text_by_ary_or_null(vnt.Fallback_ary());
				if (bidi_bry != null) rule_bidis.Set(vnt_key, bidi_bry);
			}
			if (bidi_bry != null) {
				int marked_len = cnv_marked_hash.Count();
				for (int j = 0; j < marked_len; ++j) {
					Xol_vnt_itm marked_itm = (Xol_vnt_itm)cnv_marked_hash.Get_at(j);
					byte[] marked_key = marked_itm.Key();
					byte[] marked_bry = rule_bidis.Get_text_by_key_or_null(marked_key);
					byte[] cur_bidi_bry = rule_bidis.Get_text_by_key_or_null(vnt_key);
					if (vnt.Dir() == Xol_vnt_dir_.Tid__bi)
						cnv_tbl.Set(vnt_key, marked_bry, cur_bidi_bry);
					if (marked_itm.Dir() == Xol_vnt_dir_.Tid__bi) 
						cnv_tbl.Set(marked_key, cur_bidi_bry, marked_bry);
				}
				cnv_marked_hash.Add(vnt_key, vnt);
			}
			// undi: fill to convert tables
			byte[] undi_bry = rule_undis.Get_text_by_key_or_null(vnt_key);
			if (vnt.Dir() != Xol_vnt_dir_.Tid__none && undi_bry != null) {
				Vnt_rule_undi_grp undi_grp = rule_undis.Get_by(vnt_key);
				int undi_grp_len = undi_grp.Len();
				for (int j = 0; j < undi_grp_len; ++j) {
					Vnt_rule_undi_itm undi_itm = undi_grp.Get_at(j);
					cnv_tbl.Set(vnt_key, undi_itm.Src(), undi_itm.Trg());
				}
			}				
		}
	}
	private byte[] Make_descrip() {
		int len = rule_bidis.Len();
		for (int i = 0; i < len; ++i) {
			Vnt_rule_bidi_itm bidi_itm = rule_bidis.Get_at(i);
			Xol_vnt_itm vnt_itm = vnt_regy.Get_by(bidi_itm.Vnt());
			tmp_bfr.Add(vnt_itm.Name()).Add_byte_colon().Add(bidi_itm.Text()).Add_byte_semic();
		}
		len = rule_undis.Len();
		for (int i = 0; i < len; ++i) {
			Vnt_rule_undi_grp undi_grp = rule_undis.Get_at(i);
			int sub_len = undi_grp.Len();
			for (int j = 0; j < sub_len; ++j) {
				Vnt_rule_undi_itm undi_itm = (Vnt_rule_undi_itm)undi_grp.Get_at(j);
				Xol_vnt_itm undi_vnt = vnt_regy.Get_by(undi_grp.Vnt());
				tmp_bfr.Add(undi_itm.Src()).Add(Bry__undi_spr).Add(undi_vnt.Name()).Add_byte_colon().Add(undi_itm.Trg()).Add_byte_semic();
			}
		}
		return tmp_bfr.To_bry_and_clear();
	}
	private byte[] Make_title(Xol_vnt_itm vnt) {
		if (vnt.Idx() == 0) {	// for mainLanguageCode; EX: "zh"
			byte[] rv = rule_bidis.Get_text_by_key_or_null(vnt.Key());
			return rv == null ? rule_undis.Get_text_by_key_or_null(vnt.Key()) : rv;
		}
		else
			return Make_converted(vnt);
	}
	private byte[] Make_converted(Xol_vnt_itm vnt) {
		if (rule_bidis.Len() == 0 && rule_undis.Len() == 0) return rule_raw;
		byte[] rv = rule_bidis.Get_text_by_key_or_null(vnt.Key());							// display current variant in bidirectional array
		if (rv == null) rv = rule_bidis.Get_text_by_ary_or_null(vnt.Fallback_ary());		// or display current variant in fallbacks
		if (rv == null) rv = rule_undis.Get_text_by_key_or_null(vnt.Key());					// or display current variant in unidirectional array
		if (rv == null && vnt.Dir() == Xol_vnt_dir_.Tid__none) {							// or display first text under disable manual convert
			rv = (rule_bidis.Len() > 0) ? rule_bidis.Get_text_at(0) : rule_undis.Get_text_at(0);
		}
		return rv;
	}
	private final    static byte[] 
	  Bry__error_bgn = Bry_.new_a7("<span class=\"error\">vnt error")
	, Bry__error_end = Bry_.new_a7("</span>")
	, Bry__undi_spr = Bry_.new_u8("⇒")
	;
}
