/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.brys;
import gplx.Bry_;
import gplx.Bry_find_;
import gplx.Double_;
import gplx.Err_;
import gplx.Gfo_usr_dlg_;
import gplx.Int_;
import gplx.String_;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.AsciiByte;
public class Bry_rdr_old {		
	private byte[] scope = Bry_.Empty;
	public byte[] Src() {return src;} protected byte[] src;
	public int Src_len() {return src_len;} protected int src_len;
	public void Init(byte[] src)			{this.Init(Bry_.Empty, src, 0);}
	public void Init(byte[] src, int pos)	{this.Init(Bry_.Empty, src, pos);}
	public void Init(byte[] scope, byte[] src, int pos) {
		this.src = src; this.src_len = src.length; this.pos = pos;
		this.scope = scope;
	} 
	public int Pos() {return pos;} public Bry_rdr_old Pos_(int v) {this.pos = v; return this;} protected int pos;
	public void Pos_add(int v) {pos += v;}
	public boolean Pos_is_eos()		{return pos == src_len;}
	public boolean Pos_is_reading()	{return pos <  src_len;}
	public void Pos_add_one() {++pos;}
	public int Or_int() {return or_int;} public void Or_int_(int v) {or_int = v;} private int or_int = Int_.Min_value;
	public byte[] Or_bry() {return or_bry;} public void Or_bry_(byte[] v) {or_bry = v;} private byte[] or_bry;
	public int Find_fwd(byte find) {return Bry_find_.Find_fwd(src, find, pos);}
	public int Find_fwd_ws() {return Bry_find_.Find_fwd_until_ws(src, pos, src_len);}
	public int Find_fwd__pos_at_lhs(byte[] find_bry) {return Find_fwd__pos_at(find_bry, BoolUtl.N);}
	public int Find_fwd__pos_at_rhs(byte[] find_bry) {return Find_fwd__pos_at(find_bry, BoolUtl.Y);}
	public int Find_fwd__pos_at(byte[] find_bry, boolean pos_at_rhs) {
		int find_pos = Bry_find_.Find_fwd(src, find_bry, pos, src_len);
		if (pos_at_rhs) find_pos += find_bry.length;
		if (find_pos != Bry_find_.Not_found) pos = find_pos;
		return find_pos;
	}
	public byte Read_byte()			{return src[pos];}
	public int Read_int_to_semic()	{return Read_int_to(AsciiByte.Semic);}
	public int Read_int_to_comma()	{return Read_int_to(AsciiByte.Comma);}
	public int Read_int_to_pipe()	{return Read_int_to(AsciiByte.Pipe);}
	public int Read_int_to_nl()		{return Read_int_to(AsciiByte.Nl);}
	public int Read_int_to_quote()	{return Read_int_to(AsciiByte.Quote);}
	public int Read_int_to_non_num(){return Read_int_to(AsciiByte.Null);}
	public int Read_int_to(byte to_char) {
		int bgn = pos;
		int rv = 0;
		int negative = 1;
		while (pos < src_len) {
			byte b = src[pos++];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					rv = (rv * 10) + (b - AsciiByte.Num0);
					break;
				case AsciiByte.Dash:
					if (negative == -1)		// 2nd negative
						return or_int;		// return or_int
					else					// 1st negative
						negative = -1;		// flag negative
					break;
				default: {
					boolean match = b == to_char;
					if (to_char == AsciiByte.Null) {// hack for Read_int_to_non_num
						--pos;
						match = true;
					}
					return match ? rv * negative : or_int;
				}
			}
		}
		return bgn == pos ? or_int : rv * negative;
	}
	public byte[] Read_bry_to_nl()		{return Read_bry_to(AsciiByte.Nl);}
	public byte[] Read_bry_to_semic()	{return Read_bry_to(AsciiByte.Semic);}
	public byte[] Read_bry_to_pipe()	{return Read_bry_to(AsciiByte.Pipe);}
	public byte[] Read_bry_to_quote()	{return Read_bry_to(AsciiByte.Quote);}
	public byte[] Read_bry_to_apos()	{return Read_bry_to(AsciiByte.Apos);}
	public byte[] Read_bry_to(byte to_char) {
		int bgn = pos;
		while (pos < src_len) {
			byte b = src[pos];
			if	(b == to_char) 
				return Bry_.Mid(src, bgn, pos++);
			else
				++pos;
		}
		return bgn == pos ? or_bry : Bry_.Mid(src, bgn, src_len);
	}
	public boolean Read_yn_to_pipe() {return Read_byte_to_pipe() == AsciiByte.Ltr_y;}
	public byte Read_byte_to_pipe() {
		byte rv = src[pos];
		pos += 2;	// 1 for byte; 1 for pipe;
		return rv;
	}
	public double Read_double_to_pipe() {return Read_double_to(AsciiByte.Pipe);}
	public double Read_double_to(byte to_char) {
		byte[] double_bry = Read_bry_to(to_char);
		return Double_.parse(String_.new_a7(double_bry));	// double will never have utf8
	}
	public Bry_rdr_old Skip_ws() {
		while (pos < src_len) {
			switch (src[pos]) {
				case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
					++pos;
					break;
				default:
					return this;
			}
		}
		return this;
	}
	public Bry_rdr_old Skip_alpha_num_under() {
		while (pos < src_len) {
			switch (src[pos]) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
				case AsciiByte.Ltr_A: case AsciiByte.Ltr_B: case AsciiByte.Ltr_C: case AsciiByte.Ltr_D: case AsciiByte.Ltr_E:
				case AsciiByte.Ltr_F: case AsciiByte.Ltr_G: case AsciiByte.Ltr_H: case AsciiByte.Ltr_I: case AsciiByte.Ltr_J:
				case AsciiByte.Ltr_K: case AsciiByte.Ltr_L: case AsciiByte.Ltr_M: case AsciiByte.Ltr_N: case AsciiByte.Ltr_O:
				case AsciiByte.Ltr_P: case AsciiByte.Ltr_Q: case AsciiByte.Ltr_R: case AsciiByte.Ltr_S: case AsciiByte.Ltr_T:
				case AsciiByte.Ltr_U: case AsciiByte.Ltr_V: case AsciiByte.Ltr_W: case AsciiByte.Ltr_X: case AsciiByte.Ltr_Y: case AsciiByte.Ltr_Z:
				case AsciiByte.Ltr_a: case AsciiByte.Ltr_b: case AsciiByte.Ltr_c: case AsciiByte.Ltr_d: case AsciiByte.Ltr_e:
				case AsciiByte.Ltr_f: case AsciiByte.Ltr_g: case AsciiByte.Ltr_h: case AsciiByte.Ltr_i: case AsciiByte.Ltr_j:
				case AsciiByte.Ltr_k: case AsciiByte.Ltr_l: case AsciiByte.Ltr_m: case AsciiByte.Ltr_n: case AsciiByte.Ltr_o:
				case AsciiByte.Ltr_p: case AsciiByte.Ltr_q: case AsciiByte.Ltr_r: case AsciiByte.Ltr_s: case AsciiByte.Ltr_t:
				case AsciiByte.Ltr_u: case AsciiByte.Ltr_v: case AsciiByte.Ltr_w: case AsciiByte.Ltr_x: case AsciiByte.Ltr_y: case AsciiByte.Ltr_z:
				case AsciiByte.Underline:
					++pos;
					break;
				default:
					return this;
			}
		}
		return this;
	}
	public void Chk_bry_or_fail(byte[] bry) {
		int bry_len = bry.length;
		boolean match = Bry_.Match(src, pos, pos + bry_len, bry);
		if (match) pos += bry_len;
		else throw Err_.new_wo_type("bry.rdr:chk failed", "bry", bry, "pos", pos);
	}
	public void Chk_byte_or_fail(byte b) {
		boolean match = pos < src_len ? src[pos] == b : false;
		if (match) ++pos;
		else throw Err_.new_wo_type("bry.rdr:chk failed", "byte", b, "pos", pos);
	}
	public byte[] Mid_by_len_safe(int len) {
		int end = pos + len; if (end > src_len) end = src_len;
		return Bry_.Mid(src, pos, end);
	}
	public boolean Chk_bry_wo_move(byte[] bry, int pos) {
		int bry_len = bry.length;
		return Bry_.Match(src, pos, pos + bry_len, bry);
	}
	public int Warn(String err, int bgn, int rv) {
		int end = rv + 256; if (end > src_len) end = src_len;
		Gfo_usr_dlg_.Instance.Warn_many("", "", "read failed: scope=~{0} err=~{1} mid=~{2}", scope, err, String_.new_u8(src, bgn, end));
		return rv + 1;	// rv is always hook_bgn; add +1 to set at next character, else infinite loop
	}
}