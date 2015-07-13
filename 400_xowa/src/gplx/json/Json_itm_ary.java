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
package gplx.json; import gplx.*;
public class Json_itm_ary extends Json_itm_base implements Json_grp {
	public Json_itm_ary(int src_bgn, int src_end) {this.Ctor(src_bgn, src_end);}
	@Override public byte Tid() {return Json_itm_.Tid_array;}
	public void Src_end_(int v) {this.src_end = v;}
	@Override public Object Data() {return null;}
	@Override public byte[] Data_bry() {return null;}
	public int Subs_len() {return subs_len;} private int subs_len = 0, subs_max = 0;
	public Json_itm Subs_get_at(int i) {return subs[i];}
	public Json_itm_ary Subs_add_many(Json_itm... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			Subs_add(ary[i]);
		return this;
	}
	public void Subs_add(Json_itm itm) {
		int new_len = subs_len + 1;
		if (new_len > subs_max) {	// ary too small >>> expand
			subs_max = new_len * 2;
			Json_itm[] new_subs = new Json_itm[subs_max];
			Array_.CopyTo(subs, 0, new_subs, 0, subs_len);
			subs = new_subs;
		}
		subs[subs_len] = itm;
		subs_len = new_len;
	}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {
		if (subs_len == 0) {	// empty grp; print on one line (rather than printing across 3)
			bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Brack_end);
			return;
		}
		bfr.Add_byte_nl();
		Json_grp_.Print_indent(bfr, depth);
		bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Space);
		for (int i = 0; i < subs_len; i++) {
			if (i != 0) {
				Json_grp_.Print_nl(bfr); Json_grp_.Print_indent(bfr, depth);
				bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space);
			}
			subs[i].Print_as_json(bfr, depth + 1);
		}
		Json_grp_.Print_nl(bfr); Json_grp_.Print_indent(bfr, depth);
		bfr.Add_byte(Byte_ascii.Brack_end).Add_byte_nl();
	}
	public byte[][] Xto_bry_ary() {
		if (subs_len == 0) return Bry_.Ary_empty;
		byte[][] rv = new byte[subs_len][];
		for (int i = 0; i < subs_len; ++i)
			rv[i] = subs[i].Data_bry();
		return rv;
	}
	private Json_itm[] subs = Json_itm_.Ary_empty;
	public static Json_itm_ary cast_(Json_itm v) {return v == null || v.Tid() != Json_itm_.Tid_array ? null : (Json_itm_ary)v;}
}
