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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.xtns.wdatas.core.*;
class Wdata_visitor__lbl_gatherer implements Wdata_claim_visitor {
	private Wdata_lbl_mgr lbl_mgr;
	public Wdata_visitor__lbl_gatherer(Wdata_lbl_mgr lbl_mgr) {this.lbl_mgr = lbl_mgr;}
	public void Visit_entity(Wdata_claim_itm_entity itm) {
		lbl_mgr.Queue_if_missing__qid(itm.Entity_id());
	}
	public void Visit_time(Wdata_claim_itm_time itm) {
		byte[] ttl = Wdata_lbl_itm.Extract_ttl(itm.Calendar());
		itm.Calendar_ttl_(ttl);
		lbl_mgr.Queue_if_missing__ttl(ttl);
	}
	public void Visit_globecoordinate(Wdata_claim_itm_globecoordinate itm) {
		byte[] ttl = Wdata_lbl_itm.Extract_ttl(itm.Glb());
		itm.Glb_ttl_(ttl);
		lbl_mgr.Queue_if_missing__ttl(ttl);
	}
	public void Visit_str(Wdata_claim_itm_str itm) {}
	public void Visit_monolingualtext(Wdata_claim_itm_monolingualtext itm) {}
	public void Visit_quantity(Wdata_claim_itm_quantity itm) {}
	public void Visit_system(Wdata_claim_itm_system itm) {}
}
