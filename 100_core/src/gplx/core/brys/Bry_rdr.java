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
import gplx.core.errs.*; import gplx.core.btries.*;
public class Bry_rdr {
	private final    gplx.core.primitives.Int_obj_ref pos_ref = gplx.core.primitives.Int_obj_ref.New_neg1();
	private final    Btrie_rv trv = new Btrie_rv();
	public byte[] Src() {return src;} protected byte[] src;
	public int Src_end() {return src_end;} protected int src_end; 
	public int Pos() {return pos;} protected int pos;
	public boolean Pos_is_eos() {return pos == src_end;}
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
	public int Move_by_one()			{return Move_by(1);}
	public int Move_by(int v)			{this.pos += v; return pos;}
	public int Find_fwd_lr()			{return Find_fwd(dflt_dlm	, Bool_.Y, Bool_.N, Fail_if_missing);}
	public int Find_fwd_lr(byte find)	{return Find_fwd(find		, Bool_.Y, Bool_.N, Fail_if_missing);}
	public int Find_fwd_lr_or(byte find, int or)
										{return Find_fwd(find		, Bool_.Y, Bool_.N, or);}
	public int Find_fwd_lr(byte[] find) {return Find_fwd(find		, Bool_.Y, Bool_.N, Fail_if_missing);}
	public int Find_fwd_rr()			{return Find_fwd(dflt_dlm	, Bool_.N, Bool_.N, Fail_if_missing);}
	public int Find_fwd_rr(byte find)	{return Find_fwd(find		, Bool_.N, Bool_.N, Fail_if_missing);}
	public int Find_fwd_rr(byte[] find) {return Find_fwd(find		, Bool_.N, Bool_.N, Fail_if_missing);}
	public int Find_fwd_rr_or(byte[] find, int or)
										{return Find_fwd(find		, Bool_.N, Bool_.N, or);}
	private int Find_fwd(byte find, boolean ret_lhs, boolean pos_lhs, int or) {
		int find_pos = Bry_find_.Find_fwd(src, find, pos, src_end);
		if (find_pos == Bry_find_.Not_found) {
			if (or == Fail_if_missing) {	
				err_wkr.Fail("find failed", "find", Byte_ascii.To_str(find));
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
			if (src[pos] != to_char) {err_wkr.Fail("read byte to failed", "to", Byte_ascii.To_str(to_char)); return Byte_.Max_value_127;}
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
	public int Read_int_to_non_num()	{return Read_int_to(Byte_ascii.Null);}
	public int Read_int_to(byte to_char) {
		int bgn = pos;
		int rv = 0;
		int negative = 1;
		while (pos < src_end) {
			byte b = src[pos++];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					rv = (rv * 10) + (b - Byte_ascii.Num_0);
					break;
				case Byte_ascii.Dash:
					if (negative == -1) {	// 2nd negative
						err_wkr.Fail("invalid int", "mid", String_.new_u8(src, bgn, pos));
						return Int_.Min_value;
					}
					else					// 1st negative
						negative = -1;		// flag negative
					break;
				default: {
					boolean match = b == to_char;
					if (to_char == Byte_ascii.Null) {// hack for Read_int_to_non_num
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
	@gplx.Virtual public Bry_rdr Skip_ws() {
		while (pos < src_end) {
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
	public Bry_rdr Skip_alpha_num_under() {
		while (pos < src_end) {
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
	private static final int Fail_if_missing = Int_.Min_value;
}
