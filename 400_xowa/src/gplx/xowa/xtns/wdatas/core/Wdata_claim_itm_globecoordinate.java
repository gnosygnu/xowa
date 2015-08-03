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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.xtns.wdatas.hwtrs.*;
public class Wdata_claim_itm_globecoordinate extends Wdata_claim_itm_core { 	public Wdata_claim_itm_globecoordinate(int pid, byte snak_tid, byte[] lat, byte[] lng, byte[] alt, byte[] prc, byte[] glb) {
		this.Ctor(pid, snak_tid);
		this.lat = lat; this.lng = lng; this.alt = alt; this.prc = prc; this.glb = glb;
	}
	@Override public byte Val_tid() {return Wdata_dict_val_tid.Tid_globecoordinate;}
	public byte[] Lat() {return lat;} private final byte[] lat;
	public byte[] Lng() {return lng;} private final byte[] lng;
	public byte[] Alt() {return alt;} private final byte[] alt;
	public byte[] Prc() {return prc;} private final byte[] prc;
	public Decimal_adp Prc_as_num() {
		if (prc_as_num == null)
			prc_as_num = Bry_.Eq(prc, null_bry) ? Decimal_adp_.One : Decimal_adp_.parse_(String_.new_a7(prc));
		return prc_as_num;
	}	private Decimal_adp prc_as_num;
	public byte[] Glb() {return glb;} private final byte[] glb;
	public byte[] Glb_ttl() {return glb_ttl;} public void Glb_ttl_(byte[] v) {glb_ttl = v;} private byte[] glb_ttl;
	@Override public void Welcome(Wdata_claim_visitor visitor) {visitor.Visit_globecoordinate(this);}
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wdata_dict_snak_tid.Xto_str(this.Snak_tid()), Wdata_dict_val_tid.Xto_str(this.Val_tid()), String_.new_u8(lat), String_.new_u8(lng), String_.new_u8(alt), String_.new_u8(prc), String_.new_u8(glb));
	}
	private static final byte[] null_bry = Bry_.new_a7("null");
}
