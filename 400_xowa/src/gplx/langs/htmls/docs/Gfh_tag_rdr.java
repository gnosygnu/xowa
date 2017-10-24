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
package gplx.langs.htmls.docs; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.xowa.parsers.htmls.*;
public class Gfh_tag_rdr {
	private final    Hash_adp_bry name_hash;
	private final    Mwh_atr_parser atr_parser = new Mwh_atr_parser();
	private final    Gfh_tag tag__tmp__move = new Gfh_tag(), tag__tmp__peek = new Gfh_tag(), tag__eos = new Gfh_tag(), tag__comment = new Gfh_tag();
	private final    Int_obj_ref tmp_depth = Int_obj_ref.New_zero();
	Gfh_tag_rdr(Hash_adp_bry name_hash) {this.name_hash = name_hash;}
	public byte[] Src() {return src;} private byte[] src;
	public int Src_end() {return src_end;} private int src_end;
	public Bry_err_wkr Err_wkr() {return err_wkr;} private final    Bry_err_wkr err_wkr = new Bry_err_wkr();
	public Gfh_tag_rdr Reg(String tag_name, int tag_id) {name_hash.Add_str_int(tag_name, tag_id); return this;}
	public void Init(byte[] ctx_name, byte[] src, int src_bgn, int src_end) {
		this.src = src; this.pos = src_bgn; this.src_end = src_end;
		tag__eos.Init(this, src, Bool_.N, Bool_.N, src_end, src_end, src_end, src_end, Gfh_tag_.Id__eos, Bry_.Empty);
		err_wkr.Init_by_page(String_.new_u8(ctx_name), src);
	}
	public void Src_rng_(int src_bgn, int src_end) {
		this.pos = src_bgn; this.src_end = src_end;
	}
	public int Pos() {return pos;} private int pos;
	public void Pos_(int v) {this.pos = v;}
	public void Atrs__make(Mwh_atr_wkr atr_wkr, int head_bgn, int head_end) {atr_parser.Parse(atr_wkr, -1, -1, src, head_bgn, head_end);}
	public Gfh_tag Tag__move_fwd_head()						{return Tag__find(Bool_.Y, Bool_.N, Bool_.N, pos, src_end, Gfh_tag_.Id__any);}
	public Gfh_tag Tag__move_fwd_head(int match_name_id)	{return Tag__find(Bool_.Y, Bool_.N, Bool_.N, pos, src_end, match_name_id);}
	public Gfh_tag Tag__move_fwd_tail(int match_name_id)	{return Tag__find(Bool_.Y, Bool_.N, Bool_.Y, pos, src_end, match_name_id);}
	public Gfh_tag Tag__peek_fwd_head()						{return Tag__find(Bool_.N, Bool_.N, Bool_.N, pos, src_end, Gfh_tag_.Id__any);}
	public Gfh_tag Tag__peek_fwd_head(int match_name_id)	{return Tag__find(Bool_.N, Bool_.N, Bool_.N, pos, src_end, match_name_id);}
	public Gfh_tag Tag__peek_fwd_tail(int match_name_id)	{return Tag__find(Bool_.N, Bool_.N, Bool_.Y, pos, src_end, match_name_id);}
	public Gfh_tag Tag__peek_bwd_tail(int match_name_id)	{return Tag__find(Bool_.N, Bool_.Y, Bool_.Y, pos, src_end, match_name_id);}
	public Gfh_tag Tag__peek_bwd_head()						{return Tag__find(Bool_.N, Bool_.Y, Bool_.Y, pos, src_end, Gfh_tag_.Id__any);}
	public Gfh_tag Tag__find_fwd_head(int bgn, int end, int match_name_id)	{return Tag__find(Bool_.N, Bool_.N, Bool_.N, bgn, end, match_name_id);}
	private Gfh_tag Tag__find(boolean move, boolean bwd, boolean tail, int rng_bgn, int rng_end, int match_name_id) {
		int tmp = rng_bgn;
		int stop_pos = rng_end; int adj = 1;
		if (bwd) {
			stop_pos = -1;
			adj = -1;
			--tmp;	// subtract 1 from tmp; needed when pos is at src_len, else array error below
		}
		tmp_depth.Val_zero_();
		Gfh_tag rv = null;
		while (tmp != stop_pos) {
			if (src[tmp] == Byte_ascii.Angle_bgn) {
				rv = Tag__extract(move, tail, match_name_id, tmp);
				if (rv.Name_id() == Gfh_tag_.Id__comment) {	// ignore comments DATE:2016-06-25
					tmp = rv.Src_end();
					rv = null;	// null rv, else rv will still be comment and may get returned to caller
					continue;
				}
				if (Tag__match(move, bwd, tail, match_name_id, tmp_depth, rv))
					break;
				else {
					tmp = bwd ? rv.Src_bgn() - 1 : rv.Src_end();
					rv = null;
				}
			}
			else
				tmp += adj;
		}
		if (rv == null) {
			if (move && tail && !bwd)
				err_wkr.Fail("move tag fwd failed", "tag_name", Gfh_tag_.To_str(match_name_id));
			else
				return Tag__eos(rng_bgn);
		}
		if (move) pos = rv.Src_end();
		return rv;
	}
	private boolean Tag__match(boolean move, boolean bwd, boolean tail, int match_name_id, Int_obj_ref depth_obj, Gfh_tag tag) {
		int tag_name_id = tag.Name_id();
		if (	tag_name_id != match_name_id												// tag doesn't match requested
			&&	match_name_id != Gfh_tag_.Id__any											// requested is not wildcard
			)	return false;
		if (tag_name_id == Gfh_tag_.Id__comment) return true;	// ignore comments
		int depth = depth_obj.Val();
		boolean tag_is_tail = tag.Tag_is_tail();
		if (tail == tag_is_tail) {
			if (depth == 0)
				return true;
			else {
				if (match_name_id == tag_name_id)
					depth_obj.Val_add(-1);
				return false;
			}
		}
		else {
			if (!bwd && tail && !tag_is_tail && !tag.Tag_is_inline()) {
				if (match_name_id == tag_name_id)
					depth_obj.Val_add(1);
				return false;
			}
			else
				return false;
		}
	}
	public Gfh_tag Tag__move_fwd_tail(byte[] find_tag_bry)	{return Tag__find(Bool_.Y, Bool_.N, Bool_.Y, pos, src_end, find_tag_bry);}
	public Gfh_tag Tag__peek_fwd_tail(byte[] find_tag_bry)	{return Tag__find(Bool_.N, Bool_.N, Bool_.Y, pos, src_end, find_tag_bry);}
	private Gfh_tag Tag__find(boolean move, boolean bwd, boolean tail, int rng_bgn, int rng_end, byte[] find_tag_bry) {
		int tmp = rng_bgn;
		int stop_pos = rng_end; int adj = 1;
		if (bwd) {
			stop_pos = -1;
			adj = -1;
			--tmp;	// subtract 1 from tmp; needed when pos is at src_len, else array error below
		}
		tmp_depth.Val_zero_();
		Gfh_tag rv = null;
		while (tmp != stop_pos) {
			if (src[tmp] == Byte_ascii.Angle_bgn) {
				rv = Tag__extract(move, tail, find_tag_bry, tmp);
				if (Bry_.Eq(rv.Name_bry(), Gfh_tag_.Bry__xowa_comment)) {	// ignore comments DATE:2016-06-25
					tmp = rv.Src_end();
					rv = null;	// null rv, else rv will still be comment and may get returned to caller
					continue;
				}
				if (Tag__match(move, bwd, tail, find_tag_bry, tmp_depth, rv))
					break;
				else {
					tmp = bwd ? rv.Src_bgn() - 1 : rv.Src_end();
					rv = null;
				}
			}
			else
				tmp += adj;
		}
		if (rv == null) {
			if (move && tail && !bwd)
				err_wkr.Fail("move tag fwd failed", "tag_name", find_tag_bry);
			else
				return Tag__eos(rng_bgn);
		}
		if (move) pos = rv.Src_end();
		return rv;
	}
	private boolean Tag__match(boolean move, boolean bwd, boolean tail, byte[] find_tag_bry, Int_obj_ref depth_obj, Gfh_tag tag) {
		byte[] cur_tag_bry = tag.Name_bry();
		if (	!Bry_.Eq(cur_tag_bry, find_tag_bry)				// tag doesn't match requested
			&&	find_tag_bry != Gfh_tag_.Bry__xowa_any			// requested is not wildcard
			)	return false;
		if (cur_tag_bry == Gfh_tag_.Bry__xowa_comment) return true;	// ignore comments
		int depth = depth_obj.Val();
		boolean tag_is_tail = tag.Tag_is_tail();
		if (tail == tag_is_tail) {
			if (depth == 0)
				return true;
			else {
				if (Bry_.Eq(cur_tag_bry, find_tag_bry))
					depth_obj.Val_add(-1);
				return false;
			}
		}
		else {
			if (!bwd && tail && !tag_is_tail && !tag.Tag_is_inline()) {
				if (Bry_.Eq(cur_tag_bry, find_tag_bry))
					depth_obj.Val_add(1);
				return false;
			}
			else
				return false;
		}
	}
	public Gfh_tag Tag__extract(boolean move, boolean tail, byte[] find_tag_bry, int tag_bgn) {
		int name_bgn = tag_bgn + 1; if (name_bgn == src_end) return Tag__eos(tag_bgn);		// EX: "<EOS"
		byte name_0 = src[name_bgn];
		boolean cur_is_tail = false;
		switch (name_0) {
			case Byte_ascii.Bang: 
				if (Bry_.Match(src, name_bgn + 1, name_bgn + 3, Bry__comment__mid))			// skip comment; EX: "<!"
					return Tag__comment(tag_bgn);
				break;
			case Byte_ascii.Slash:
				++name_bgn; if (name_bgn == src_end) return Tag__eos(tag_bgn);				// EX: "</EOS"
				name_0 = src[name_bgn];
				cur_is_tail = true;
				break;				
		}
		int name_end = -1, atrs_end = -1, tag_end = -1, name_pos = name_bgn;
		byte name_byte = name_0; boolean inline = false;
		boolean loop = true;
		while (true) {
			switch (name_byte) {
				case Byte_ascii.Angle_end:													// EX: "<a>"
					name_end = atrs_end = name_pos;
					tag_end = name_end + 1;
					loop = false;
					break;
				case Byte_ascii.Slash:														// EX: "<a/>"
					name_end = name_pos;
					tag_end = name_pos + 1; if (tag_end == src_end) return Tag__eos(tag_bgn);// EX: "<a/EOS"
					if (src[tag_end] == Byte_ascii.Angle_end) {
						atrs_end = name_end;
						inline = true;
						loop = false;
						++tag_end;	// move tag_end after >
					}
					else {
						name_end = tag_end = -1;
					}
					break;
				case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
					name_end = name_pos;
					loop = false;
					break;
			}
			if (!loop) break;
			++name_pos; if (name_pos == src_end) return Tag__eos(tag_bgn);					// EX: "<abEOS"
			name_byte = src[name_pos];
		}
		if (tag_end == -1) {
			tag_end = Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, name_end, src_end);
			if (tag_end == Bry_find_.Not_found) return Tag__eos(tag_bgn);
			int prv_pos = tag_end - 1;
			if (src[prv_pos] == Byte_ascii.Slash) {
				atrs_end = prv_pos;
				inline = true;
			}
			else
				atrs_end = tag_end;
			++tag_end;	// position after ">"
		}
		Gfh_tag tmp = move ? tag__tmp__move : tag__tmp__peek;
		return tmp.Init(this, src, cur_is_tail, inline, tag_bgn, tag_end, name_end, atrs_end, Gfh_tag_.Id__unknown, Bry_.Mid(src, name_bgn, name_end));
	}
	public Gfh_tag Tag__extract(boolean move, boolean tail, int match_name_id, int tag_bgn) {
		int name_bgn = tag_bgn + 1; if (name_bgn == src_end) return Tag__eos(tag_bgn);		// EX: "<EOS"
		byte name_0 = src[name_bgn];
		boolean cur_is_tail = false;
		switch (name_0) {
			case Byte_ascii.Bang: 
				if (Bry_.Match(src, name_bgn + 1, name_bgn + 3, Bry__comment__mid))			// skip comment; EX: "<!"
					return Tag__comment(tag_bgn);
				break;
			case Byte_ascii.Slash:
				++name_bgn; if (name_bgn == src_end) return Tag__eos(tag_bgn);				// EX: "</EOS"
				name_0 = src[name_bgn];
				cur_is_tail = true;
				break;				
		}
		int name_end = -1, atrs_end = -1, tag_end = -1, name_pos = name_bgn;
		byte name_byte = name_0; boolean inline = false;
		boolean loop = true;
		while (true) {
			switch (name_byte) {
				case Byte_ascii.Angle_end:													// EX: "<a>"
					name_end = atrs_end = name_pos;
					tag_end = name_end + 1;
					loop = false;
					break;
				case Byte_ascii.Slash:														// EX: "<a/>"
					name_end = name_pos;
					tag_end = name_pos + 1; if (tag_end == src_end) return Tag__eos(tag_bgn);// EX: "<a/EOS"
					if (src[tag_end] == Byte_ascii.Angle_end) {
						atrs_end = name_end;
						inline = true;
						loop = false;
						++tag_end;	// move tag_end after >
					}
					else {
						name_end = tag_end = -1;
					}
					break;
				case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
					name_end = name_pos;
					loop = false;
					break;
			}
			if (!loop) break;
			++name_pos; if (name_pos == src_end) return Tag__eos(tag_bgn);					// EX: "<abEOS"
			name_byte = src[name_pos];
		}
		if (tag_end == -1) {
			tag_end = Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, name_end, src_end);
			if (tag_end == Bry_find_.Not_found) return Tag__eos(tag_bgn);
			int prv_pos = tag_end - 1;
			if (src[prv_pos] == Byte_ascii.Slash) {
				atrs_end = prv_pos;
				inline = true;
			}
			else
				atrs_end = tag_end;
			++tag_end;	// position after ">"
		}
		Gfh_tag tmp = move ? tag__tmp__move : tag__tmp__peek;
		return tmp.Init(this, src, cur_is_tail, inline, tag_bgn, tag_end, name_end, atrs_end
			, name_hash.Get_as_int_or(src, name_bgn, name_end, -1)	// TODO_OLD: change from -1 to Unknown
			, Bry_.Mid(src, name_bgn, name_end));
	}
	public boolean Read_and_move(byte match) {
		byte b = src[pos];
		if (b == match) {
			++pos;
			return true;
		}
		else
			return false;
	}
	public int Read_int_to(byte to_char) {
		int rv = Read_int_to(to_char, Int_.Max_value); if (rv == Int_.Max_value) err_wkr.Fail("invalid int", "pos", pos);
		return rv;
	}
	public int Read_int_to(byte to_char, int or_int) {
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
	private Gfh_tag Tag__comment(int tag_bgn) {
		int tag_end = Bry_find_.Move_fwd(src, gplx.langs.htmls.Gfh_tag_.Comm_end, tag_bgn, src_end); if (tag_end == Bry_find_.Not_found) tag_end = src_end;
		return tag__comment.Init(this, src, Bool_.N, Bool_.N, tag_bgn, tag_end, tag_end, tag_end, Gfh_tag_.Id__comment, Bry_.Empty);
	}
	private Gfh_tag Tag__eos(int tag_bgn) {
		int tag_end = tag_bgn + 255; if (tag_end > src_end) tag_end = src_end;
		return tag__comment.Init(this, src, Bool_.N, Bool_.N, tag_bgn, tag_end, tag_end, tag_end, Gfh_tag_.Id__eos, Bry_.Empty);
	}
	private static final    byte[] Bry__comment__mid = Bry_.new_a7("--"); 
	public static Gfh_tag_rdr New__html()	{return new Gfh_tag_rdr(Gfh_tag_.Hash);}
	public static Gfh_tag_rdr New__custom()	{return new Gfh_tag_rdr(Hash_adp_bry.cs());}
}
