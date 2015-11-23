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
package gplx.core.brys; import gplx.*; import gplx.core.*;
import gplx.core.errs.*;
public class Bry_rdr {
	private final gplx.core.primitives.Int_obj_ref pos_ref = gplx.core.primitives.Int_obj_ref.neg1_();
	private String ctx; private String wkr; private int err_bgn;		
	public byte[] Src() {return src;} private byte[] src;
	public int Pos() {return pos;} private int pos;
	public int Src_end() {return src_end;} private int src_end; 
	public Bry_rdr Dflt_dlm_(byte b) {this.dflt_dlm = b; return this;} private byte dflt_dlm;
	public Bry_rdr Fail_throws_err_(boolean v) {this.fail_throws_err = v; return this;} private boolean fail_throws_err = true;
	public Bry_rdr Init_by_page(byte[] ctx, byte[] src, int src_len)			{this.ctx = Quote(String_.new_u8(ctx)); this.src = src; this.src_end = src_len; this.pos = 0; return this;}
	public Bry_rdr Init_by_hook(String wkr, int err_bgn, int pos)				{this.wkr = Quote(wkr); this.err_bgn = err_bgn; this.pos = pos; return this;}
	public Bry_rdr Init_by_sub(Bry_rdr rdr, String wkr, int pos, int src_end)   {
		this.src = rdr.src; this.ctx = rdr.ctx; this.wkr = Quote(wkr); this.err_bgn = pos; this.pos = pos; this.src_end = src_end;
		return this;
	}
	public int Move_to(int v)			{this.pos = v; return pos;}
	public int Move_by_one()			{return Move_by(1);}
	public int Move_by(int v)			{this.pos += v; return pos;}
	public int Find_fwd_lr()			{return Find_fwd(dflt_dlm	, Bool_.Y, Bool_.N);}
	public int Find_fwd_lr(byte find)	{return Find_fwd(find		, Bool_.Y, Bool_.N);}
	public int Find_fwd_lr(byte[] find) {return Find_fwd(find		, Bool_.Y, Bool_.N);}
	public int Find_fwd_rr()			{return Find_fwd(dflt_dlm	, Bool_.N, Bool_.N);}
	public int Find_fwd_rr(byte find)	{return Find_fwd(find		, Bool_.N, Bool_.N);}
	public int Find_fwd_rr(byte[] find) {return Find_fwd(find		, Bool_.N, Bool_.N);}
	private int Find_fwd(byte find, boolean ret_lhs, boolean pos_lhs) {
		int find_pos = Bry_find_.Find_fwd(src, find, pos, src_end); if (find_pos == Bry_find_.Not_found) {Fail("find failed", "find", Byte_ascii.To_str(find)); return Bry_find_.Not_found;}
		pos = find_pos + (pos_lhs ? 0 : 1);
		return ret_lhs ? find_pos : pos;
	}
	private int Find_fwd(byte[] find, boolean ret_lhs, boolean pos_lhs) {
		int find_pos = Bry_find_.Find_fwd(src, find, pos, src_end); if (find_pos == Bry_find_.Not_found) {Fail("find failed", "find", String_.new_u8(find)); return Bry_find_.Not_found;}
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
			if (src[pos] != to_char) {Fail("read byte to failed", "to", Byte_ascii.To_str(to_char)); return Byte_.Max_value_127;}
			++pos;
		}
		return rv;
	}
	public double Read_double_to() {return Read_double_to(dflt_dlm);}
	public double Read_double_to(byte to_char) {
		byte[] bry = Read_bry_to(to_char);
		return Double_.parse(String_.new_a7(bry));
	}
	public int Read_int_to() {return Read_int_to(dflt_dlm);}
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
						Fail("invalid int", "mid", String_.new_u8(src, bgn, pos));
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
						Fail("invalid int", "mid", String_.new_u8(src, bgn, pos));
						return Int_.Min_value;
					}
					return rv * negative;
				}
			}
		}
		if (bgn == pos) {Fail("int is empty", String_.Empty, String_.Empty); return Int_.Min_value;}
		return rv * negative;
	}
	public byte Read_byte_as_a7_int() {
		byte rv = Byte_ascii.To_a7_int(src[pos]);
		++pos;
		return rv;
	}
	public int Read_int_by_base85(int reqd) {
		int rv = gplx.xowa.htmls.core.hzips.Xoh_hzip_int_.Decode(reqd, src, src_end, pos, pos_ref);
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
		if (src[pos] != find) {Fail("failed check", "chk", Byte_.To_str(find)); return Bry_find_.Not_found;}
		++pos;
		return pos;
	}
	public int Chk(byte[] find) {
		int find_end = pos + find.length;
		if (!Bry_.Match(src, pos, find_end, find)) {Fail("failed check", "chk", String_.new_u8(find)); return Bry_find_.Not_found;}
		pos = find_end;
		return pos;
	}
	public byte Chk(gplx.core.btries.Btrie_slim_mgr trie)				{return Chk(trie, pos, src_end);}
	public byte Chk_or(gplx.core.btries.Btrie_slim_mgr trie, byte or)	{return Chk_or(trie, pos, src_end, or);}
	public byte Chk(gplx.core.btries.Btrie_slim_mgr trie, int itm_bgn, int itm_end) {
		byte rv = Chk_or(trie, itm_bgn, itm_end, Byte_.Max_value_127);
		if (rv == Byte_.Max_value_127) {Fail("failed trie check", "mid", String_.new_u8(Bry_.Mid_by_len_safe(src, pos, 16))); return Byte_.Max_value_127;}
		return rv;
	}
	public byte Chk_or(gplx.core.btries.Btrie_slim_mgr trie, int itm_bgn, int itm_end, byte or) {
		Object rv_obj = trie.Match_bgn(src, itm_bgn, itm_end);
		if (rv_obj == null) return or;
		pos = trie.Match_pos();
		return ((gplx.core.primitives.Byte_obj_val)rv_obj).Val();
	}
	public int Fail(String msg, String arg_key, Object arg_val) {return Fail(msg, arg_key, arg_val, err_bgn, err_bgn + 255);}
	public int Fail(String msg, String arg_key, Object arg_val, int excerpt_bgn, int excerpt_end) {
		arg_val = Quote(Object_.Xto_str_strict_or_null_mark(arg_val));
		String err_msg = Msg_make(msg, arg_key, arg_val, excerpt_bgn, excerpt_end);
		Gfo_usr_dlg_.Instance.Warn_many("", "", err_msg);
		if (fail_throws_err) throw Err_.new_("Bry_rdr", err_msg).Logged_y_();
		return Bry_find_.Not_found;
	}
	public Err Err_make(String msg, String arg_key, Object arg_val, int excerpt_bgn, int excerpt_end) {return Err_.new_("Bry_rdr", Msg_make(msg, arg_key, arg_val, excerpt_bgn, excerpt_end));}
	private String Msg_make(String msg, String arg_key, Object arg_val, int excerpt_bgn, int excerpt_end) {
		if (String_.EqEmpty(arg_key))
			return Err_msg.To_str(msg, "ctx", ctx, "wkr", wkr, "excerpt", Bry_.Escape_ws(Bry_.Mid_safe(src, excerpt_bgn, excerpt_end)));
		else
			return Err_msg.To_str(msg, arg_key, arg_val, "ctx", ctx, "wkr", wkr, "excerpt", Bry_.Escape_ws(Bry_.Mid_safe(src, excerpt_bgn, excerpt_end)));
	}
	private static String Quote(String v) {return "'" + v + "'";}
}
