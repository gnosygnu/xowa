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
public abstract class Wdata_claim_itm_core extends Wdata_claim_itm_base {
	public byte[] Wguid() {return wguid;} public void Wguid_(byte[] v) {this.wguid = v;} private byte[] wguid;
	public String Prop_type() {return Prop_type_statement;} private static final String Prop_type_statement = "statement";
	public Wdata_claim_itm_base[] Qual_ary() {return qual_ary;} public void Qual_ary_(Wdata_claim_itm_base[] v) {this.qual_ary = v;} private Wdata_claim_itm_base[] qual_ary;
	public Wdata_claim_itm_base[] Ref_ary() {return ref_ary;} public void Ref_ary_(Wdata_claim_itm_base[] v) {this.ref_ary = v;} private Wdata_claim_itm_base[] ref_ary;
}
