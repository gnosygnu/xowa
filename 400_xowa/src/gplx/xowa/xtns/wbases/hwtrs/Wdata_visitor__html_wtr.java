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
import gplx.xowa.langs.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*;
class Wdata_visitor__html_wtr implements Wbase_claim_visitor {
	private Wdata_wiki_mgr wdata_mgr; private Wdata_hwtr_msgs msgs; private Wdata_lbl_mgr lbl_mgr;
	private Xol_lang_itm lang;
	private byte[] ttl; private Bry_bfr tmp_bfr;
	private final    Bry_fmtr tmp_time_fmtr = Bry_fmtr.new_(); private final    Bry_bfr tmp_time_bfr = Bry_bfr_.New_w_size(32);
	public Wdata_visitor__html_wtr Init(Bry_bfr tmp_bfr, Wdata_wiki_mgr wdata_mgr, Wdata_hwtr_msgs msgs, Wdata_lbl_mgr lbl_mgr, Xol_lang_itm lang, byte[] ttl) {
		this.wdata_mgr = wdata_mgr; this.msgs = msgs; this.lbl_mgr = lbl_mgr; this.lang = lang;
		this.tmp_bfr = tmp_bfr; this.ttl = ttl;
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
		Wdata_prop_val_visitor.Write_quantity(tmp_bfr, wdata_mgr, lang, itm.Amount(), itm.Lbound(), itm.Ubound(), itm.Unit());
	}
	public void Visit_globecoordinate(Wbase_claim_globecoordinate itm) {
		Wdata_prop_val_visitor.Write_geo(Bool_.Y, tmp_bfr, lbl_mgr, msgs, itm.Lat(), itm.Lng(), itm.Alt(), itm.Prc(), itm.Glb());
	}
	public void Visit_time(Wbase_claim_time itm) {
		itm.Write_to_bfr(tmp_bfr, tmp_time_bfr, tmp_time_fmtr, msgs, ttl);
	}
	public void Visit_system(Wbase_claim_value itm) {
		switch (itm.Snak_tid()) {
			case Wbase_claim_value_type_.Tid__somevalue:	tmp_bfr.Add(msgs.Val_tid_somevalue()); break;
			case Wbase_claim_value_type_.Tid__novalue:		tmp_bfr.Add(msgs.Val_tid_novalue());   break;
		}
	}
}
