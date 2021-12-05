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
package gplx.core.brys; import gplx.objects.primitives.BoolUtl;
import gplx.Bry_;
import gplx.Bry_find_;
import gplx.Byte_;
import gplx.objects.strings.AsciiByte;
import gplx.Double_;
import gplx.Int_;
import gplx.String_;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
public class Bry_rdr {
	private final gplx.core.primitives.Int_obj_ref pos_ref = gplx.core.primitives.Int_obj_ref.New_neg1();
	private final Btrie_rv trv = new Btrie_rv();
	public byte[] Src() {return src;} protected byte[] src;
	public int Src_end() {return src_end;} protected int src_end; 
	public int Pos() {return pos;} protected int pos;
	public boolean Pos_is_eos() {return pos == src_end;}
	public boolean Pos_is_reading() {return pos > -1 && pos < src_end;}
	public byte Cur() {return src[pos];}
	public byte Nxt() {return pos + 1 >= src_end ? Not_found : src[pos + 1];}
	public Bry_rdr Dflt_dlm_(byte b) {this.dflt_dlm = b; return this;} private byte dflt_dlm;
	public Bry_rdr Fail_throws_err_(boolean v) {err_wkr.Fail_throws_err_(v); return this;}
	public Bry_rdr Init_by_src(byte[] src)										{err_wkr.Init_by_page("", src);						this.pos = 0; this.src = src; this.src_end = src.length; return this;}
	public Bry_rdr Init_by_page(byte[] page, byte[] src, int src_len)			{err_wkr.Init_by_page(String_.new_u8(page), src);	this.pos = 0; this.src = src; this.src_end = src_len; return this;}
	public Bry_rdr Init_by_sect(String sect, int sect_bgn, int pos)				{err_wkr.Init_by_sect(sect, sect_bgn);				this.pos = pos; return this;}
	public Bry_rdr Init_by_wkr (Bry_err_wkr wkr, String sect, int pos, int src_end)   {
		this.pos = pos; this.src = wkr.Src(); this.src_end = src_end;
		err_wkr.Init_by_page(wkr.Page(), src); 
		err_wkr.Init_by_sect(sect, pos); 
		return this;
	}
	public Bry_err_wkr Err_wkr()		{return err_wkr;} private Bry_err_wkr err_wkr = new Bry_err_wkr();
	public int Move_to(int v)			{this.pos = v; return pos;}
	public int Move_to_last()           {this.pos = src_end - 1; return pos;}
	public int Move_to_end()            {this.pos = src_end; return pos;}
	public int Move_by_one()			{return Move_by(1);}
	public int Move_by(int v)			{this.pos += v; return pos;}
	public int Find_fwd_lr()			{return Find_fwd(dflt_dlm	, BoolUtl.Y, BoolUtl.N, Fail_if_missing);}
	public int Find_fwd_lr(byte find)	{return Find_fwd(find		, BoolUtl.Y, BoolUtl.N, Fail_if_missing);}
	public int Find_fwd_lr_or(byte find, int or)
										{return Find_fwd(find		, BoolUtl.Y, BoolUtl.N, or);}
	public int Find_fwd_lr(String find) {return Find_fwd(Bry_.new_u8(find), BoolUtl.Y, BoolUtl.N, Fail_if_missing);}
	public int Find_fwd_lr(byte[] find) {return Find_fwd(find		, BoolUtl.Y, BoolUtl.N, Fail_if_missing);}
	public int Find_fwd_lr_or(String find, int or) {return Find_fwd(Bry_.new_u8(find), BoolUtl.Y, BoolUtl.N, or);}
	public int Find_fwd_lr_or(byte[] find, int or)
										{return Find_fwd(find		, BoolUtl.Y, BoolUtl.N, or);}
	public int Find_fwd_rr()			{return Find_fwd(dflt_dlm	, BoolUtl.N, BoolUtl.N, Fail_if_missing);}
	public int Find_fwd_rr(byte find)	{return Find_fwd(find		, BoolUtl.N, BoolUtl.N, Fail_if_missing);}
	public int Find_fwd_rr(byte[] find) {return Find_fwd(find		, BoolUtl.N, BoolUtl.N, Fail_if_missing);}
	public int Find_fwd_rr_or(byte[] find, int or)
										{return Find_fwd(find		, BoolUtl.N, BoolUtl.N, or);}
	private int Find_fwd(byte find, boolean ret_lhs, boolean pos_lhs, int or) {
		int find_pos = Bry_find_.Find_fwd(src, find, pos, src_end);
		if (find_pos == Bry_find_.Not_found) {
			if (or == Fail_if_missing) {	
				err_wkr.Fail("find failed", "find", AsciiByte.ToStr(find));
				return Bry_find_.Not_found;
			}
			else
				return or;
		}
		pos = find_pos + (pos_lhs ? 0 : 1);
		return ret_lhs ? find_pos : pos;
	}
	private int Find_fwd(byte[] find, boolean ret_lhs, boolean pos_lhs, int or) {
		int find_pos = Bry_find_.Find_fwd(src, find, pos, src_end);
		if (find_pos == Bry_find_.Not_found) {
			if (or == Fail_if_missing) {
				err_wkr.Fail("find failed", "find", String_.new_u8(find));
				return Bry_find_.Not_found;
			}
			else
				return or;
		}
		pos = find_pos + (pos_lhs ? 0 : find.length);
		return ret_lhs ? find_pos : pos;
	}
	public byte Read_byte() {
		byte rv = src[pos];
		++pos;
		return rv;
	}
	public byte Read_byte_to() {return Read_byte_to(dflt_dlm);}
	public byte Read_byte_to(byte to_char) {
		byte rv = src[pos];
		++pos;
		if (pos < src_end) {
			if (src[pos] != to_char) {err_wkr.Fail("read byte to failed", "to", AsciiByte.ToStr(to_char)); return Byte_.Max_value_127;}
			++pos;
		}
		return rv;
	}
	public double Read_double_to()			{return Read_double_to(dflt_dlm);}
	public double Read_double_to(byte to_char) {
		byte[] bry = Read_bry_to(to_char);
		return Double_.parse(String_.new_a7(bry));
	}
	public int Read_int_to()			{return Read_int_to(dflt_dlm);}
	public int Read_int_to_non_num()	{return Read_int_to(AsciiByte.Null);}
	public int Read_int_to(byte to_char) {
		int bgn = pos;
		int rv = 0;
		int negative = 1;
		while (pos < src_end) {
			byte b = src[pos++];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					rv = (rv * 10) + (b - AsciiByte.Num0);
					break;
				case AsciiByte.Dash:
					if (negative == -1) {	// 2nd negative
						err_wkr.Fail("invalid int", "mid", String_.new_u8(src, bgn, pos));
						return Int_.Min_value;
					}
					else					// 1st negative
						negative = -1;		// flag negative
					break;
				default: {
					boolean match = b == to_char;
					if (to_char == AsciiByte.Null) {// hack for Read_int_to_non_num
						--pos;
						match = true;
					}
					if (!match) {
						err_wkr.Fail("invalid int", "mid", String_.new_u8(src, bgn, pos));
						return Int_.Min_value;
					}
					return rv * negative;
				}
			}
		}
		if (bgn == pos) {err_wkr.Fail("int is empty"); return Int_.Min_value;}
		return rv * negative;
	}
	public int Read_hzip_int(int reqd) {
		int rv = gplx.core.encoders.Gfo_hzip_int_.Decode(reqd, src, src_end, pos, pos_ref);
		pos = pos_ref.Val();
	 	return rv;
	}
	public byte[] Read_bry_to() {return Read_bry_to(dflt_dlm);}
	public byte[] Read_bry_to(byte b) {
		int bgn = pos;
		return Bry_.Mid(src, bgn, Find_fwd_lr(b));
	}
	public int Move_fwd_while(byte b) {
		while (pos < src_end) {
			if (src[pos] != b) {
				break;
			}
			pos++;
		}
		return pos;
	}
	public int Move_bwd_while(byte b) {
		while (pos > -1) {
			if (src[pos] != b) {
				return pos + 1; // return position which is start of b sequence
			}
			pos--;
		}
		return -1;
	}
	public boolean Match(byte[] find) { // same as Is but no auto-move
		int find_len = find.length;
		int find_end = pos + find_len;
		return Bry_.Match(src, pos, find_end, find, 0, find_len);
	}
	public boolean Is(byte find) {
		boolean rv = src[pos] == find;
		if (rv) ++pos;	// only advance if match;
		return rv;
	}
	public boolean Is(byte[] find) {
		int find_len = find.length;
		int find_end = pos + find_len;
		boolean rv = Bry_.Match(src, pos, find_end, find, 0, find_len);
		if (rv) pos = find_end;	// only advance if match;
		return rv;
	}
	public int Chk(byte find) {
		if (src[pos] != find) {err_wkr.Fail("failed check", "chk", Byte_.To_str(find)); return Bry_find_.Not_found;}
		++pos;
		return pos;
	}
	public int Chk(byte[] find) {
		int find_end = pos + find.length;
		if (!Bry_.Match(src, pos, find_end, find)) {err_wkr.Fail("failed check", "chk", String_.new_u8(find)); return Bry_find_.Not_found;}
		pos = find_end;
		return pos;
	}
	public byte Chk(Btrie_slim_mgr trie)				{return Chk(trie, pos, src_end);}
	public void Chk_trie_val(Btrie_slim_mgr trie, byte val) {
		byte rv = Chk_or(trie, Byte_.Max_value_127);
		if (rv == Byte_.Max_value_127) err_wkr.Fail("failed trie check", "mid", String_.new_u8(Bry_.Mid_by_len_safe(src, pos, 16)));
	}
	public Object Chk_trie_as_obj(Btrie_rv trv, Btrie_slim_mgr trie) {
		Object rv = trie.Match_at(trv, src, pos, src_end); if (rv == null) err_wkr.Fail("failed trie check", "mid", String_.new_u8(Bry_.Mid_by_len_safe(src, pos, 16)));
		return rv;
	}
	public byte Chk_or(Btrie_rv trv, Btrie_slim_mgr trie, byte or)	{
		Object rv_obj = trie.Match_at(trv, src, pos, src_end);
		return rv_obj == null ? or : ((gplx.core.primitives.Byte_obj_val)rv_obj).Val();
	}
	public byte Chk_or(Btrie_slim_mgr trie, byte or)	{return Chk_or(trie, pos, src_end, or);}
	public byte Chk(Btrie_slim_mgr trie, int itm_bgn, int itm_end) {
		byte rv = Chk_or(trie, itm_bgn, itm_end, Byte_.Max_value_127);
		if (rv == Byte_.Max_value_127) {err_wkr.Fail("failed trie check", "mid", String_.new_u8(Bry_.Mid_by_len_safe(src, pos, 16))); return Byte_.Max_value_127;}
		return rv;
	}
	public byte Chk_or(Btrie_slim_mgr trie, int itm_bgn, int itm_end, byte or) {
		Object rv_obj = trie.Match_at(trv, src, itm_bgn, itm_end);
		if (rv_obj == null) return or;
		pos = trv.Pos();
		return ((gplx.core.primitives.Byte_obj_val)rv_obj).Val();
	}
	public Bry_rdr Skip_ws() {
		while (pos < src_end) {
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
	public Bry_rdr Skip_alpha_num_under() {
		while (pos < src_end) {
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
	private static final int Fail_if_missing = Int_.Min_value;
	public static final int Not_found = Bry_find_.Not_found;
}
