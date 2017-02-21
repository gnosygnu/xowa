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
package gplx.core.brys; import gplx.*; import gplx.core.*;
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
	public int Find_fwd__pos_at_lhs(byte[] find_bry) {return Find_fwd__pos_at(find_bry, Bool_.N);}
	public int Find_fwd__pos_at_rhs(byte[] find_bry) {return Find_fwd__pos_at(find_bry, Bool_.Y);}
	public int Find_fwd__pos_at(byte[] find_bry, boolean pos_at_rhs) {
		int find_pos = Bry_find_.Find_fwd(src, find_bry, pos, src_len);
		if (pos_at_rhs) find_pos += find_bry.length;
		if (find_pos != Bry_find_.Not_found) pos = find_pos;
		return find_pos;
	}
	public byte Read_byte()			{return src[pos];}
	public int Read_int_to_semic()	{return Read_int_to(Byte_ascii.Semic);}
	public int Read_int_to_comma()	{return Read_int_to(Byte_ascii.Comma);}
	public int Read_int_to_pipe()	{return Read_int_to(Byte_ascii.Pipe);}
	public int Read_int_to_nl()		{return Read_int_to(Byte_ascii.Nl);}
	public int Read_int_to_quote()	{return Read_int_to(Byte_ascii.Quote);}
	public int Read_int_to_non_num(){return Read_int_to(Byte_ascii.Null);}
	public int Read_int_to(byte to_char) {
		int bgn = pos;
		int rv = 0;
		int negative = 1;
		while (pos < src_len) {
			byte b = src[pos++];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					rv = (rv * 10) + (b - Byte_ascii.Num_0);
					break;
				case Byte_ascii.Dash:
					if (negative == -1)		// 2nd negative
						return or_int;		// return or_int
					else					// 1st negative
						negative = -1;		// flag negative
					break;
				default: {
					boolean match = b == to_char;
					if (to_char == Byte_ascii.Null) {// hack for Read_int_to_non_num
						--pos;
						match = true;
					}
					return match ? rv * negative : or_int;
				}
			}
		}
		return bgn == pos ? or_int : rv * negative;
	}
	public byte[] Read_bry_to_nl()		{return Read_bry_to(Byte_ascii.Nl);}
	public byte[] Read_bry_to_semic()	{return Read_bry_to(Byte_ascii.Semic);}
	public byte[] Read_bry_to_pipe()	{return Read_bry_to(Byte_ascii.Pipe);}
	public byte[] Read_bry_to_quote()	{return Read_bry_to(Byte_ascii.Quote);}
	public byte[] Read_bry_to_apos()	{return Read_bry_to(Byte_ascii.Apos);}
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
	public boolean Read_yn_to_pipe() {return Read_byte_to_pipe() == Byte_ascii.Ltr_y;}
	public byte Read_byte_to_pipe() {
		byte rv = src[pos];
		pos += 2;	// 1 for byte; 1 for pipe;
		return rv;
	}
	public double Read_double_to_pipe() {return Read_double_to(Byte_ascii.Pipe);}
	public double Read_double_to(byte to_char) {
		byte[] double_bry = Read_bry_to(to_char);
		return Double_.parse(String_.new_a7(double_bry));	// double will never have utf8
	}
	@gplx.Virtual public Bry_rdr_old Skip_ws() {
		while (pos < src_len) {
			switch (src[pos]) {
				case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
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
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
				case Byte_ascii.Underline:
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