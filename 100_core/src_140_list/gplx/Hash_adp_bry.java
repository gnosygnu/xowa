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
package gplx;
public class Hash_adp_bry extends gplx.lists.HashAdp_base implements HashAdp {
	Hash_adp_bry(boolean case_match) {
		this.case_match = case_match;
		key_ref = new Hash_adp_bry_ref(case_match, null, -1, -1);
	} 	private final boolean case_match; private Hash_adp_bry_ref key_ref;
	public Object Get_by_bry(byte[] src) {return super.Fetch_base(key_ref.Src_all_(src));}
	public Object Get_by_mid(byte[] src, int bgn, int end) {return super.Fetch_base(key_ref.Src_all_set_(src, bgn, end));}
	public Hash_adp_bry Add_bry_bry(byte[] key) {this.Add_base(key, key); return this;}
	public Hash_adp_bry Add_str_byte(String key, byte val) {this.Add_base(Bry_.new_utf8_(key), Byte_obj_val.new_(val)); return this;}
	public Hash_adp_bry Add_str_obj(String key, Object val) {this.Add_base(Bry_.new_utf8_(key), val); return this;}
	public Hash_adp_bry Add_bry_byte(byte[] key, byte val) {this.Add_base(key, Byte_obj_val.new_(val)); return this;}
	public Hash_adp_bry Add_bry_obj(byte[] key, Object val) {this.Add_base(key, val); return this;}
	public Hash_adp_bry Add_many_str(String... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			byte[] bry = Bry_.new_utf8_(itm);
			Add_bry_bry(bry);
		}
		return this;
	}
	@Override protected void Add_base(Object key, Object val) {
		byte[] key_bry = (byte[])key;
		super.Add_base(new Hash_adp_bry_ref(case_match, key_bry, 0, key_bry.length), val);
	}
	@Override protected void Del_base(Object key) {super.Del_base(key_ref.Src_all_((byte[])key));}
	@Override protected boolean Has_base(Object key) {return super.Has_base(key_ref.Src_all_((byte[])key));}
	@Override protected Object Fetch_base(Object key) {return super.Fetch_base(key_ref.Src_all_((byte[])key));}
	public static Hash_adp_bry cs_() {return new Hash_adp_bry(true);}
	public static Hash_adp_bry ci_() {return new Hash_adp_bry(false);}
	public static Hash_adp_bry ci_ascii_() {return new Hash_adp_bry(false);}
}
class Hash_adp_bry_ref {
	public Hash_adp_bry_ref(boolean case_match, byte[] src, int src_bgn, int src_end) {this.case_match = case_match; this.src = src; this.src_bgn = src_bgn; this.src_end = src_end;}
	final boolean case_match;
	public byte[] Src() {return src;} private byte[] src;
	public Hash_adp_bry_ref Src_all_(byte[] v) {
		this.src = v;
		this.src_bgn = 0;
		this.src_end = v.length;
		return this;
	}
	public Hash_adp_bry_ref Src_all_set_(byte[] v, int src_bgn, int src_end) {
		this.src = v;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		return this;
	}
	public int Src_bgn() {return src_bgn;} int src_bgn;
	public int Src_end() {return src_end;} int src_end;
	@Override public int hashCode() {
		int rv = 0;
		for (int i = src_bgn; i < src_end; i++) {
			int b_int = src[i] & 0xFF;	// JAVA: patch
			if (!case_match && b_int > 64 && b_int < 91)	// 64=before A; 91=after Z; NOTE: lowering upper-case on PERF assumption that there will be more lower-case letters than upper-case
				b_int += 32;
			rv = (31 * rv) + b_int;
		}
		return rv;	
	}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;
		Hash_adp_bry_ref comp = (Hash_adp_bry_ref)obj;
		byte[] comp_src = comp.Src(); int comp_bgn = comp.Src_bgn(), comp_end = comp.Src_end();
		int comp_len = comp_end - comp_bgn, src_len = src_end - src_bgn;
		if (comp_len != src_len) return false;
		for (int i = 0; i < comp_len; i++) {
			int src_pos = src_bgn + i;
			if (src_pos >= src_end) return false;	// ran out of src; exit; EX: src=ab; find=abc
			if (case_match) {
				if (src[src_pos] != comp_src[i + comp_bgn]) return false;
			}
			else {
				byte src_byte = src[src_pos];
				if (src_byte > 64 && src_byte < 91) src_byte += 32;
				byte comp_byte = comp_src[i + comp_bgn];
				if (comp_byte > 64 && comp_byte < 91) comp_byte += 32;
				if (src_byte != comp_byte) return false;
			}
		}
		return true;
	}
}
