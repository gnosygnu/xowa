/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases;

import gplx.objects.primitives.BoolUtl;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Bry_find_;
import gplx.objects.strings.AsciiByte;
import gplx.Decimal_adp;
import gplx.Decimal_adp_;
import gplx.Double_;
import gplx.Err_;
import gplx.Gfo_usr_dlg_;
import gplx.Math_;
import gplx.String_;
import gplx.core.brys.fmtrs.Bry_fmtr;
import gplx.xowa.Xoae_app;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.Xol_lang_itm_;
import gplx.xowa.xtns.mapSources.Map_dd2dms_func;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_visitor;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_entity;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_globecoordinate;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_monolingualtext;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_quantity;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_string;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_time;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_value;
import gplx.xowa.xtns.wbases.claims.itms.times.Wbase_date;
import gplx.xowa.xtns.wbases.claims.itms.times.Wbase_date_;
import gplx.xowa.xtns.wbases.core.Wdata_langtext_itm;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_hwtr_mgr;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_hwtr_msgs;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_lbl_itm;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_lbl_mgr;

public class Wdata_prop_val_visitor implements Wbase_claim_visitor { // THREAD.UNSAFE; callers must do synchronized
	private Wdata_wiki_mgr wdata_mgr; private Xoae_app app; private Bry_bfr bfr;
	private Xol_lang_itm lang;
	private final Bry_bfr tmp_time_bfr = Bry_bfr_.Reset(255); private final Bry_fmtr tmp_time_fmtr = Bry_fmtr.new_();
	private Wdata_hwtr_msgs msgs;
	private boolean mode_is_statements;
	public Wdata_prop_val_visitor(Xoae_app app, Wdata_wiki_mgr wdata_mgr) {
		this.app = app; this.wdata_mgr = wdata_mgr;
	}
	public void Init(Bry_bfr bfr, Wdata_hwtr_msgs msgs, byte[] lang_key, boolean mode_is_statements) {
		// init some member variables; 
		this.bfr = bfr; this.msgs = msgs;
		this.lang = app.Lang_mgr().Get_by_or_null(lang_key);
		if (lang == null) lang = app.Lang_mgr().Lang_en();	// TEST: needed for one test; DATE:2016-10-20
		this.mode_is_statements = mode_is_statements;
	}
	public void Visit_str(Wbase_claim_string itm) {Write_str(bfr, itm.Val_bry());}
	public static void Write_str(Bry_bfr bfr, byte[] bry) {bfr.Add(bry);}
	public void Visit_time(Wbase_claim_time itm) {
		Write_time(bfr, tmp_time_bfr, tmp_time_fmtr, msgs, Bry_.Empty, -1, itm.Time_as_date());	// for now, don't bother passing ttl; only used for error msg; DATE:2015-08-03
	}
	public static void Write_time(Bry_bfr bfr, Bry_bfr tmp_bfr, Bry_fmtr tmp_fmtr, Wdata_hwtr_msgs msgs, byte[] page_url, int pid, Wbase_date date) {
		try {
			Wbase_date_.To_bfr(bfr, tmp_fmtr, tmp_bfr, msgs, date);
			if (date.Calendar_is_julian()) bfr.Add_byte_space().Add(msgs.Time_julian());
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to write time; ttl=~{0} pid=~{1} err=~{2}", page_url, pid, Err_.Message_gplx_log(e));
		}
	}
	public void Visit_monolingualtext(Wbase_claim_monolingualtext itm)	{Write_langtext(bfr, itm.Text());}
	public static void Write_langtext(Bry_bfr bfr, byte[] text) {bfr.Add(text);}			// phrase only; PAGE:en.w:Alberta; EX: {{#property:motto}} -> "Fortis et libre"; DATE:2014-08-28
	public void Visit_entity(Wbase_claim_entity itm) {Write_entity(bfr, wdata_mgr, lang.Key_bry(), itm.Page_ttl_db(), mode_is_statements);}
	public static void Write_entity(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, byte[] lang_key, byte[] entity_ttl_db, boolean mode_is_statements) {
		// get entity
		Wdata_doc entity_doc = wdata_mgr.Doc_mgr.Get_by_xid_or_null(entity_ttl_db);

		// NOTE: wiki may refer to entity that no longer exists; EX: {{#property:p1}} which links to Q1, but p1 links to Q2 and Q2 was deleted; DATE:2014-02-01
		if (entity_doc == null) 
			return;

		// get label
		byte[] label = entity_doc.Get_label_bry_or_null(lang_key);

		// NOTE: some properties may not exist in language of wiki; default to english; DATE:2013-12-19
		if (label == null && !Bry_.Eq(lang_key, Xol_lang_itm_.Key_en))
			label = entity_doc.Get_label_bry_or_null(Xol_lang_itm_.Key_en);

		// if label is still not found, don't add null reference
		if (label != null) {
			// if statements, add "[[entity_val]]"; DATE:2017-04-04
			if (mode_is_statements) {
				bfr.Add(gplx.xowa.parsers.tmpls.Xop_tkn_.Lnki_bgn);
				bfr.Add(label);
				bfr.Add(gplx.xowa.parsers.tmpls.Xop_tkn_.Lnki_end);
			}
			// else, just add "entity_val"
			else
				bfr.Add(label);
		}
	}
	public void Visit_quantity(Wbase_claim_quantity itm) {Write_quantity(bfr, wdata_mgr, lang, itm.Amount(), itm.Lbound(), itm.Ubound(), itm.Unit());}
	public static void Write_quantity(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, Xol_lang_itm lang, byte[] val_bry, byte[] lo_bry, byte[] hi_bry, byte[] unit) {
		// get val, lo, hi; NOTE: must handle large numbers; EX:{{#property:P1082}} PAGE:en.w:Earth; DATE:2015-08-02; NOTE: must handle decimals; PAGE:en.w:Malinao,_Aklan; DATE:2016-11-08
		Decimal_adp val = Decimal__parse_or(val_bry, null); if (val == null) throw Err_.new_wo_type("wbase:quanity val can not be null");
		Decimal_adp lo = Decimal__parse_or(lo_bry, val);
		Decimal_adp hi = Decimal__parse_or(hi_bry, val);

		// 2021-02-14|ISSUE#:839|Only print value without ± if lo and hi are null; PAGE:en.w:2019_FIVB_Volleyball_Women%27s_Challenger_Cup#Pool_A;
		// TOMBSTONE: if (lo.Eq(hi) && hi.Eq(val))
		if (lo_bry == null && hi_bry == null)
			bfr.Add(lang.Num_mgr().Format_num_by_decimal(val));			// amount; EX: 1,234
		else {
			Wdata_hwtr_msgs msgs = wdata_mgr.Hwtr_mgr().Msgs();
			Decimal_adp lo_dif = val.Subtract(lo);
			Decimal_adp hi_dif = hi.Subtract(val);
			if (lo_dif.Eq(hi_dif)) {	// lo_dif, hi_dif are same; print val±dif
				bfr.Add(lang.Num_mgr().Format_num_by_decimal(val));		// amount;	EX: 1,234
				bfr.Add(msgs.Sym_plusminus());							// symbol:	EX: ±
				bfr.Add(lang.Num_mgr().Format_num_by_decimal(lo_dif));	// amount;	EX: 4
			}
			else {					// lo_dif, hi_dif are diff; print lo - hi; this may not be what MW does
				bfr.Add(lang.Num_mgr().Format_num_by_decimal(lo));		// lo;		EX: 1,230
				bfr.Add_byte(AsciiByte.Dash);							// dash:	EX: -
				bfr.Add(lang.Num_mgr().Format_num_by_decimal(hi));		// hi;		EX: 1,238
			}
		}

		// output unit
		int unit_qid_bgn = unit == null ? Bry_find_.Not_found : Bry_find_.Find_fwd(unit, Wikidata_url);
		if (unit_qid_bgn == Bry_find_.Not_found) {}			// entity missing; output nothing; EX:"unit":"1"; PAGE:en.w:Malinao,_Aklan DATE:2016-11-08																
		else {												// entity exists; EX:"http://www.wikidata.org/entity/Q11573" (meter)
			bfr.Add_byte_space();
			byte[] xid = Bry_.Mid(unit, Wikidata_url.length);
			Wdata_doc entity_doc = wdata_mgr.Doc_mgr.Get_by_xid_or_null(xid);
			if (entity_doc != null) {
				Wdata_langtext_itm label = entity_doc.Get_label_itm_or_null(lang);
				if (label != null)
					bfr.Add(label.Text());
			}
		}
	}
	private static Decimal_adp Decimal__parse_or(byte[] bry, Decimal_adp or) {	// handle missing lbound / ubound; DATE:2016-12-03
		return bry == null ? or : Decimal_adp_.parse(String_.new_u8(Normalize_for_decimal(bry)));
	}
	public static byte[] Normalize_for_decimal(byte[] bry) { // remove leading "+" and any commas; was Bry_.To_long_or(val_bry, Byte_ascii.Comma_bry, 0, val_bry.length, 0)
		if (bry == null) return null;
		Bry_bfr bfr = null;
		int len = bry.length;
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			switch (b) {
				case AsciiByte.Plus:
					if (i == 0) {
						if (bfr == null) bfr = Bry_bfr_.New();
					}
					else {
						throw Err_.new_wo_type("invalid decimal format; plus must be at start of String", "raw", bry);
					}
					break;
				case AsciiByte.Comma:
					if (bfr == null) {
						bfr = Bry_bfr_.New();
						bfr.Add_mid(bry, 0, i);
					}
					break;
				default:
					if (bfr != null)
						bfr.Add_byte(b);
					break;
			}
		}
		return bfr == null ? bry : bfr.To_bry_and_clear();
	}
	public void Visit_globecoordinate(Wbase_claim_globecoordinate itm) {Write_geo(BoolUtl.N, bfr, wdata_mgr.Hwtr_mgr().Lbl_mgr(), msgs, itm.Lat(), itm.Lng(), itm.Alt(), itm.Prc(), itm.Glb());}
	public static void Write_geo(boolean wikidata_page, Bry_bfr bfr, Wdata_lbl_mgr lbl_mgr, Wdata_hwtr_msgs msgs, byte[] lat, byte[] lng, byte[] alt, byte[] prc, byte[] glb) {
		// 2020-09-06|ISSUE#:792|rewrite based on https://en.wikipedia.org/w/index.php?title=Module:Wd&action=edit; REF.MW: https://github.com/DataValues/Geo/blob/master/src/Formatters/LatLongFormatter.php
		// normalize precision
		double precision = Double_.parse_or(String_.new_a7(prc), Double_.NaN);
		if (Double_.IsNaN(precision) || precision <= 0) { // "null" or "0" should be 1; PAGE:ru.w:Лысково_(Калужская_область) DATE:2016-11-24
			precision = PRECISION_1_OVER_3600;
		}

		double latitude = Double_.parse_or(String_.new_a7(lat), Double_.NaN);
		double longitude = Double_.parse_or(String_.new_a7(lng), Double_.NaN);
		latitude = Math_.Floor(latitude / precision + 0.5) * precision;
		longitude = Math_.Floor(longitude / precision + 0.5) * precision;

		if (precision >= 1 - (PRECISION_1_OVER_60) && precision < 1) {
			precision = 1;
		}
		else if (precision >= (PRECISION_1_OVER_60) - (PRECISION_1_OVER_3600) && precision < PRECISION_1_OVER_60) {
			precision = PRECISION_1_OVER_60;
		}

		int unitsPerDegree = 1;
		if (precision >= 1) {
			unitsPerDegree = 1;
		}
		else if (precision >= PRECISION_1_OVER_60) {
			unitsPerDegree = 60;
		}
		else {
			unitsPerDegree = 3600;
		}

		int numDigits = (int)Math_.Ceil(-Math.log10(((double)(unitsPerDegree) * precision)));
		numDigits += 4; // +4 b/c Map_dd2dms_func.Deg_to_dms needs 4 places to evaluate MS while fracs are evaulated as numDigits

		// write lat / lng
		Map_dd2dms_func.Deg_to_dms(bfr, BoolUtl.Y, BoolUtl.N, Bry_.new_a7(Double_.To_str(latitude)), numDigits);
		bfr.Add_byte_comma().Add_byte_space();
		Map_dd2dms_func.Deg_to_dms(bfr, BoolUtl.Y, BoolUtl.Y, Bry_.new_a7(Double_.To_str(longitude)), numDigits);

		// write globe
		if (wikidata_page) {
			byte[] glb_ttl = Wdata_lbl_itm.Extract_ttl(glb);
			if (glb_ttl != null) {
				byte[] glb_lbl = lbl_mgr.Get_text__ttl(glb_ttl, glb);
				bfr.Add_byte_space().Add_byte(AsciiByte.ParenBgn);
				Wdata_hwtr_mgr.Write_link_wikidata(bfr, glb_ttl, glb_lbl);
				bfr.Add_byte(AsciiByte.ParenEnd);
			}
		}
	}
	private static final double
	    PRECISION_1_OVER_3600 = 1d / 3600d
	  , PRECISION_1_OVER_60 = 1d / 60d
	;

	private static final byte[] Wikidata_url = Bry_.new_a7("http://www.wikidata.org/entity/");
	public void Visit_system(Wbase_claim_value itm) {}
}
