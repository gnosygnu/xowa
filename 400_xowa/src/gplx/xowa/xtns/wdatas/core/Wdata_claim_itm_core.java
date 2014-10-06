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
public abstract class Wdata_claim_itm_core extends Wdata_claim_itm_base {
	public byte[] Wguid() {return wguid;} public void Wguid_(byte[] v) {this.wguid = v;} private byte[] wguid;
	public String Prop_type() {return Prop_type_statement;} private static final String Prop_type_statement = "statement";
	public Wdata_claim_grp_list Qualifiers() {return qualifiers;} public Wdata_claim_itm_core Qualifiers_(Wdata_claim_grp_list v) {qualifiers = v; return this;} private Wdata_claim_grp_list qualifiers;
	public int[] Qualifiers_order() {return qualifiers_order;} public void Qualifiers_order_(int[] v) {qualifiers_order = v;} private int[] qualifiers_order;
	public Wdata_references_grp[] References() {return references;} public void References_(Wdata_references_grp[] v) {references = v;} private Wdata_references_grp[] references;
}
