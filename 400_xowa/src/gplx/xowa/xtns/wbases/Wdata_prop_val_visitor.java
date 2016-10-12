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
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.hwtrs.*;
class Wdata_prop_val_visitor implements Wbase_claim_visitor {
	private Wdata_wiki_mgr wdata_mgr; private Xoae_app app; private Bry_bfr bfr; private byte[] lang_key;
	private final    Bry_bfr tmp_time_bfr = Bry_bfr_.Reset(255); private final    Bry_fmtr tmp_time_fmtr = Bry_fmtr.new_();
	private Wdata_hwtr_msgs msgs;
	public Wdata_prop_val_visitor(Xoae_app app, Wdata_wiki_mgr wdata_mgr) {this.app = app; this.wdata_mgr = wdata_mgr;}
	public void Init(Bry_bfr bfr, Wdata_hwtr_msgs msgs, byte[] lang_key) {
		this.bfr = bfr; this.msgs = msgs; this.lang_key = lang_key;
	}
	public void Visit_str(Wbase_claim_string itm) {
		bfr.Add(itm.Val_str());
	}
	public void Visit_time(Wbase_claim_time itm) {
		itm.Write_to_bfr(bfr, tmp_time_bfr, tmp_time_fmtr, msgs, Bry_.Empty);	// for now, don't bother passing ttl; only used for error msg; DATE:2015-08-03
	}
	public void Visit_monolingualtext(Wbase_claim_monolingualtext itm)	{bfr.Add(itm.Text());}			// phrase only; PAGE:en.w:Alberta; EX: {{#property:motto}} -> "Fortis et libre"; DATE:2014-08-28
	public void Visit_entity(Wbase_claim_entity itm) {
		Wdata_doc entity_doc = wdata_mgr.Doc_mgr.Get_by_xid_or_null(itm.Page_ttl_db());
		if (entity_doc == null) return;	// NOTE: wiki may refer to entity that no longer exists; EX: {{#property:p1}} which links to Q1, but p1 links to Q2 and Q2 was deleted; DATE:2014-02-01
		byte[] label = entity_doc.Label_list__get(lang_key);
		if (label == null && !Bry_.Eq(lang_key, Xol_lang_itm_.Key_en))	// NOTE: some properties may not exist in language of wiki; default to english; DATE:2013-12-19
			label = entity_doc.Label_list__get(Xol_lang_itm_.Key_en);
		if (label != null)	// if label is still not found, don't add null reference
			bfr.Add(label);
	}
	public void Visit_quantity(Wbase_claim_quantity itm) {
		// get val
		byte[] val_bry = itm.Amount();
		long val = Bry_.To_long_or(val_bry, Byte_ascii.Comma_bry, 0, val_bry.length, 0);	// NOTE: must cast to long for large numbers; EX:{{#property:P1082}} PAGE:en.w:Earth; DATE:2015-08-02

		// get lo, hi
		long lo = itm.Lbound_as_num().To_long();
		long hi = itm.Ubound_as_num().To_long();

		// fmt val
		Xol_lang_itm lang = app.Lang_mgr().Get_by(lang_key);
		if (lo == val && hi == val)	// lo, hi, val are same; print val only;
			bfr.Add(lang.Num_mgr().Format_num_by_long(val));		// amount; EX: 1,234
		else {
			long lo_dif = val - lo;
			long hi_dif = hi - val;
			if (lo_dif == hi_dif) {	// lo_dif, hi_dif are same; print val±dif
				bfr.Add(lang.Num_mgr().Format_num_by_long(val));	// amount;	EX: 1,234
				bfr.Add(Bry__quantity_margin_of_error);				// symbol:	EX: ±
				bfr.Add(lang.Num_mgr().Format_num_by_long(lo_dif));	// amount;	EX: 4
			}
			else {					// lo_dif, hi_dif are diff; print lo - hi; this may not be what MW does
				bfr.Add(lang.Num_mgr().Format_num_by_long(lo));		// lo;		EX: 1,230
				bfr.Add_byte(Byte_ascii.Dash);						// dash:	EX: -
				bfr.Add(lang.Num_mgr().Format_num_by_long(hi));		// hi;		EX: 1,238
			}
		}

		// output unit
		bfr.Add_byte_space();
		int unit_qid_bgn = Bry_find_.Find_fwd(itm.Unit(), Wikidata_url);
		if (unit_qid_bgn == Bry_find_.Not_found)			// entity missing; just output unit literally
			bfr.Add(itm.Unit());							// unit;   EX: 1
		else {												// entity exists; EX:"http://www.wikidata.org/entity/Q11573" (meter)
			byte[] xid = Bry_.Mid(itm.Unit(), Wikidata_url.length);
			Wdata_doc entity_doc = wdata_mgr.Doc_mgr.Get_by_xid_or_null(xid);
			bfr.Add(entity_doc.Label_list__get_or_fallback(lang));
		}
	}
	private static final    byte[] Wikidata_url = Bry_.new_a7("http://www.wikidata.org/entity/");
	public void Visit_globecoordinate(Wbase_claim_globecoordinate itm) {
		Wdata_prop_val_visitor_.Render__geo(bfr, itm.Lat(), itm.Lng());
	}
	public void Visit_system(Wbase_claim_value itm) {}
	public static final    byte[] Bry__quantity_margin_of_error = Bry_.new_u8("±");
}
