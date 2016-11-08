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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.hwtrs.*; import gplx.xowa.xtns.wbases.claims.itms.times.*;
public class Wdata_prop_val_visitor implements Wbase_claim_visitor {
	private Wdata_wiki_mgr wdata_mgr; private Xoae_app app; private Bry_bfr bfr;
	private Xol_lang_itm lang;
	private final    Bry_bfr tmp_time_bfr = Bry_bfr_.Reset(255); private final    Bry_fmtr tmp_time_fmtr = Bry_fmtr.new_();
	private Wdata_hwtr_msgs msgs;
	public Wdata_prop_val_visitor(Xoae_app app, Wdata_wiki_mgr wdata_mgr) {this.app = app; this.wdata_mgr = wdata_mgr;}
	public void Init(Bry_bfr bfr, Wdata_hwtr_msgs msgs, byte[] lang_key) {
		this.bfr = bfr; this.msgs = msgs;
		this.lang = app.Lang_mgr().Get_by(lang_key);
		if (lang == null) lang = app.Lang_mgr().Lang_en();	// TEST: needed for one test; DATE:2016-10-20
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
	public void Visit_entity(Wbase_claim_entity itm) {Write_entity(bfr, wdata_mgr, lang.Key_bry(), itm.Page_ttl_db());}
	public static void Write_entity(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, byte[] lang_key, byte[] entity_ttl_db) {
		Wdata_doc entity_doc = wdata_mgr.Doc_mgr.Get_by_xid_or_null(entity_ttl_db);
		if (entity_doc == null) return;	// NOTE: wiki may refer to entity that no longer exists; EX: {{#property:p1}} which links to Q1, but p1 links to Q2 and Q2 was deleted; DATE:2014-02-01
		byte[] label = entity_doc.Label_list__get(lang_key);
		if (label == null && !Bry_.Eq(lang_key, Xol_lang_itm_.Key_en))	// NOTE: some properties may not exist in language of wiki; default to english; DATE:2013-12-19
			label = entity_doc.Label_list__get(Xol_lang_itm_.Key_en);
		if (label != null)	// if label is still not found, don't add null reference
			bfr.Add(label);
	}
	public void Visit_quantity(Wbase_claim_quantity itm) {Write_quantity(bfr, wdata_mgr, lang, itm.Amount(), itm.Lbound(), itm.Ubound(), itm.Unit());}
	public static void Write_quantity(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, Xol_lang_itm lang, byte[] val_bry, byte[] lo_bry, byte[] hi_bry, byte[] unit) {
		// get val, lo, hi
		Decimal_adp val = Decimal_adp_.parse(String_.new_u8(Normalize_for_decimal(val_bry))); // NOTE: must cast to long for large numbers; EX:{{#property:P1082}} PAGE:en.w:Earth; DATE:2015-08-02
		Decimal_adp lo = Decimal_adp_.parse(String_.new_u8(Normalize_for_decimal(lo_bry)));
		Decimal_adp hi = Decimal_adp_.parse(String_.new_u8(Normalize_for_decimal(hi_bry)));

		// fmt val
		if (lo.Eq(hi) && hi.Eq(val))// lo, hi, val are same; print val only;
			bfr.Add(lang.Num_mgr().Format_num_by_decimal(val));			// amount; EX: 1,234
		else {
			Decimal_adp lo_dif = val.Subtract(lo);
			Decimal_adp hi_dif = hi.Subtract(val);
			if (lo_dif.Eq(hi_dif)) {	// lo_dif, hi_dif are same; print val±dif
				bfr.Add(lang.Num_mgr().Format_num_by_decimal(val));		// amount;	EX: 1,234
				bfr.Add(Bry__quantity_margin_of_error);					// symbol:	EX: ±
				bfr.Add(lang.Num_mgr().Format_num_by_decimal(lo_dif));	// amount;	EX: 4
			}
			else {					// lo_dif, hi_dif are diff; print lo - hi; this may not be what MW does
				bfr.Add(lang.Num_mgr().Format_num_by_decimal(lo));		// lo;		EX: 1,230
				bfr.Add_byte(Byte_ascii.Dash);							// dash:	EX: -
				bfr.Add(lang.Num_mgr().Format_num_by_decimal(hi));		// hi;		EX: 1,238
			}
		}

		// output unit
		bfr.Add_byte_space();
		int unit_qid_bgn = Bry_find_.Find_fwd(unit, Wikidata_url);
		if (unit_qid_bgn == Bry_find_.Not_found)			// entity missing; just output unit literally
			bfr.Add(unit);									// unit; EX: "meter"
		else {												// entity exists; EX:"http://www.wikidata.org/entity/Q11573" (meter)
			byte[] xid = Bry_.Mid(unit, Wikidata_url.length);
			Wdata_doc entity_doc = wdata_mgr.Doc_mgr.Get_by_xid_or_null(xid);
			bfr.Add(entity_doc.Label_list__get_or_fallback(lang));
		}
	}
	public static byte[] Normalize_for_decimal(byte[] bry) { // remove leading "+" and any commas; was Bry_.To_long_or(val_bry, Byte_ascii.Comma_bry, 0, val_bry.length, 0)
		Bry_bfr bfr = null;
		int len = bry.length;
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Plus:
					if (i == 0) {
						if (bfr == null) bfr = Bry_bfr_.New();
					}
					else {
						throw Err_.new_wo_type("invalid decimal format; plus must be at start of String", "raw", bry);
					}
					break;
				case Byte_ascii.Comma:
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
	public void Visit_globecoordinate(Wbase_claim_globecoordinate itm) {Write_geo(bfr, wdata_mgr, lang, itm.Lat(), itm.Lng());}
	public static void Write_geo(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, Xol_lang_itm lang, byte[] lat, byte[] lng) {
		bfr.Add(lat);
		bfr.Add(Bry__geo_dlm);
		bfr.Add(lng);
	}
	private static final    byte[] Wikidata_url = Bry_.new_a7("http://www.wikidata.org/entity/");
	private static final    byte[] Bry__geo_dlm = Bry_.new_a7(", ");
	public void Visit_system(Wbase_claim_value itm) {}
	public static final    byte[] Bry__quantity_margin_of_error = Bry_.new_u8("±");
}
