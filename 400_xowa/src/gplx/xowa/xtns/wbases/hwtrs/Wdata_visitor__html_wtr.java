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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*;
class Wdata_visitor__html_wtr implements Wbase_claim_visitor {
	private byte[] ttl; private Bry_bfr tmp_bfr; private Wdata_hwtr_msgs msgs; private Wdata_lbl_mgr lbl_mgr;
	private final    Bry_fmtr tmp_time_fmtr = Bry_fmtr.new_(); private final    Bry_bfr tmp_time_bfr = Bry_bfr_.New_w_size(32);
	public Wdata_visitor__html_wtr Init(byte[] ttl, Bry_bfr tmp_bfr, Wdata_hwtr_msgs msgs, Wdata_lbl_mgr lbl_mgr) {
		this.ttl = ttl; this.tmp_bfr = tmp_bfr; this.msgs = msgs; this.lbl_mgr = lbl_mgr;
		return this;
	}
	public void Visit_str(Wbase_claim_string itm) {
		tmp_bfr.Add(itm.Val_bry());
	}
	public void Visit_entity(Wbase_claim_entity itm) {
		int entity_id = itm.Entity_id();
		byte[] text = itm.Entity_tid_is_qid() ? lbl_mgr.Get_text__qid(entity_id) : lbl_mgr.Get_text__pid(entity_id);
		if (text == null) {// handle incomplete wikidata dumps; DATE:2015-06-11
			Xoa_app_.Usr_dlg().Warn_many("", "", "wbase.html_visitor:page does not exists; page=~{0}", entity_id);
			return;
		}
		Wdata_hwtr_mgr.Write_link_wikidata(tmp_bfr, itm.Page_ttl_gui(), text);			
	}
	public void Visit_monolingualtext(Wbase_claim_monolingualtext itm) {
		tmp_bfr.Add(itm.Text());
		tmp_bfr.Add_byte(Byte_ascii.Space).Add_byte(Byte_ascii.Brack_bgn).Add(itm.Lang()).Add_byte(Byte_ascii.Brack_end);
	}
	public void Visit_quantity(Wbase_claim_quantity itm) {
		try {
			Decimal_adp val = itm.Amount_as_num();
			Decimal_adp hi = itm.Ubound_as_num();
			Decimal_adp lo = itm.Lbound_as_num();
			Decimal_adp hi_diff = hi.Subtract(val);
			Decimal_adp lo_diff = val.Subtract(lo);
			float hi_diff_val = (float)hi_diff.To_double();
			float lo_diff_val = (float)lo_diff.To_double();
			tmp_bfr.Add(itm.Amount()).Add_byte_space();
			if (hi_diff.Eq(lo_diff)) {		// delta is same in both directions; EX: val=50 hi=60 lo=40 -> hi_diff == lo_diff == 10
				if (hi_diff_val != 0)		// skip if 0
					tmp_bfr.Add(msgs.Sym_plusminus()).Add_str_a7(hi_diff.To_str());
			}
			else {							// delta is diff in both directions; EX: val=50 hi=60 lo=30 -> hi_diff == 10, lo_diff == 20
				if (hi_diff_val != 0)		// skip if 0
					tmp_bfr.Add(msgs.Sym_plus()).Add_str_a7(hi_diff.To_str());
				if (lo_diff_val != 0) {		// skip if 0
					if (hi_diff_val != 0) tmp_bfr.Add(Time_plus_minus_spr);
					tmp_bfr.Add(msgs.Sym_minus()).Add_str_a7(lo_diff.To_str());
				}
			}
			byte[] unit = itm.Unit();
			if (!Bry_.Eq(unit, Wbase_claim_quantity.Unit_1))
				tmp_bfr.Add_byte_space().Add(unit);				
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to write quantity; ttl=~{0} pid=~{1} err=~{2}", ttl, itm.Pid(), Err_.Message_gplx_full(e));
		}
	}	private static final    byte[] Time_plus_minus_spr = Bry_.new_a7(" / ");
	public void Visit_time(Wbase_claim_time itm) {itm.Write_to_bfr(tmp_bfr, tmp_time_bfr, tmp_time_fmtr, msgs, ttl);}
	public void Visit_globecoordinate(Wbase_claim_globecoordinate itm) {
		Wdata_prop_val_visitor.Write_geo(Bool_.Y, tmp_bfr, lbl_mgr, itm.Lat(), itm.Lng(), itm.Alt(), itm.Prc(), itm.Glb());
	}
	public void Visit_system(Wbase_claim_value itm) {
		switch (itm.Snak_tid()) {
			case Wbase_claim_value_type_.Tid__somevalue:		tmp_bfr.Add(msgs.Val_tid_somevalue()); break;
			case Wbase_claim_value_type_.Tid__novalue:		tmp_bfr.Add(msgs.Val_tid_novalue());   break;
		}
	}
}
