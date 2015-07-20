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
package gplx.xowa.bldrs.filters.dansguardians; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
import gplx.core.criterias.*;
class Crt__match_exact implements Criteria {
	public Crt__match_exact(boolean negated, byte[][] ary) {this.negated = negated; Val_as_bry_ary_(ary);}
	public byte Tid() {return Tid_match_exact;}
	public String To_str_name() {return "MATCH_EXACT";}
	public boolean Matches(Object comp_obj) {
		if (ary_len == 0) return false;	// empty array never matches
		byte[] comp = (byte[])comp_obj;
		boolean rv = false;
		for (int i = 0; i < ary_len; i++) {
			byte[] val = ary[i];
			if (Bry_.Eq(val, comp)) {
				rv = true;
				break;
			}
		}
		return negated ? !rv : rv;
	}
	public boolean				Negated() {return negated;} private boolean negated;
	public byte[][]		Val_as_bry_ary() {return ary;} protected byte[][] ary; protected int ary_len;
	protected void		Val_as_bry_ary_(byte[][] v) {
		this.ary = v;
		ary_len = v.length;
	}
	public void			Val_as_obj_(Object v) {Val_as_bry_ary_((byte[][])v);}
	public void			Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public String XtoStr() {return String_.Concat_any(this.To_str_name(), " ", String_.Ary(ary));}
	public byte Tid_match_exact = 12;
}
