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
package gplx.langs.htmls.parsers; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.xowa.parsers.htmls.*;
public class Html_tag_rdr {
	private final Hash_adp_bry name_hash = Html_tag_.Hash;
	private final Mwh_atr_parser atr_parser = new Mwh_atr_parser();
	private final Html_tag tag__tmp__move = new Html_tag(), tag__tmp__peek = new Html_tag(), tag__eos = new Html_tag(), tag__comment = new Html_tag();
	private final Int_obj_ref tmp_depth = Int_obj_ref.zero_();
	public byte[] Src() {return src;} private byte[] src;
	public int Src_end() {return src_end;} private int src_end;
	public Bry_rdr Rdr() {return rdr;} private final Bry_rdr rdr = new Bry_rdr();		
	public void Init(byte[] src, int src_bgn, int src_end) {
		this.src = src; this.pos = src_bgn; this.src_end = src_end;
		tag__eos.Init(this, Bool_.N, Bool_.N, src_end, src_end, src_end, src_end, Html_tag_.Id__eos);
		rdr.Init_by_page(Bry_.Empty, src, src_end);
	}
	public int Pos() {return pos;} private int pos;
	public void Pos_(int v) {this.pos = v;}
	public void Atrs__make(Mwh_atr_wkr atr_wkr, int head_bgn, int head_end) {atr_parser.Parse(atr_wkr, -1, -1, src, head_bgn, head_end);}
	public void Fail(String msg, Html_tag tag) {rdr.Fail(msg, String_.Empty, String_.Empty, tag.Src_bgn(), tag.Src_end());}
	public Html_tag Tag__move_fwd_head()					{return Tag__find(Bool_.Y, Bool_.N, Bool_.N, pos, src_end, Html_tag_.Id__any);}
	public Html_tag Tag__move_fwd_head(int match_name_id)	{return Tag__find(Bool_.Y, Bool_.N, Bool_.N, pos, src_end, match_name_id);}
//		public Html_tag Tag__move_fwd_tail()					{return Tag__find(Bool_.Y, Bool_.N, Bool_.Y, pos, src_end, Html_tag_.Id__any);}
	public Html_tag Tag__move_fwd_tail(int match_name_id)	{return Tag__find(Bool_.Y, Bool_.N, Bool_.Y, pos, src_end, match_name_id);}
	public Html_tag Tag__peek_fwd_head()					{return Tag__find(Bool_.N, Bool_.N, Bool_.N, pos, src_end, Html_tag_.Id__any);}
	public Html_tag Tag__peek_fwd_head(int match_name_id)	{return Tag__find(Bool_.N, Bool_.N, Bool_.N, pos, src_end, match_name_id);}
	public Html_tag Tag__peek_fwd_tail(int match_name_id)	{return Tag__find(Bool_.N, Bool_.N, Bool_.Y, pos, src_end, match_name_id);}
	public Html_tag Tag__peek_bwd_tail(int match_name_id)	{return Tag__find(Bool_.N, Bool_.Y, Bool_.Y, pos, src_end, match_name_id);}
	public Html_tag Tag__peek_bwd_head()					{return Tag__find(Bool_.N, Bool_.Y, Bool_.Y, pos, src_end, Html_tag_.Id__any);}
	public Html_tag Tag__find_fwd_head(int bgn, int end, int match_name_id)	{return Tag__find(Bool_.N, Bool_.N, Bool_.N, bgn, end, match_name_id);}
	private Html_tag Tag__find(boolean move, boolean bwd, boolean tail, int rng_bgn, int rng_end, int match_name_id) {
		int tmp = rng_bgn;
		int stop_pos = rng_end; int adj = 1;
		if (bwd) {
			stop_pos = -1;
			adj = -1;
			--tmp;	// subtract 1 from tmp; needed when pos is at src_len, else array error below
		}
		tmp_depth.Val_zero_();
		Html_tag rv = null;
		while (tmp != stop_pos) {
			if (src[tmp] == Byte_ascii.Angle_bgn) {
				rv = Tag__extract(move, tail, match_name_id, tmp);
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
				rdr.Fail("move failed", "tag_name", Html_tag_.To_str(match_name_id));
			else
				return Tag__eos(rng_bgn);
		}
		if (move) pos = rv.Src_end();
		return rv;
	}
	private boolean Tag__match(boolean move, boolean bwd, boolean tail, int match_name_id, Int_obj_ref depth_obj, Html_tag tag) {
		int tag_name_id = tag.Name_id();
		if (	tag_name_id != match_name_id												// tag doesn't match requested
			&&	match_name_id != Html_tag_.Id__any											// requested is not wildcard
			)	return false;
		if (tag_name_id == Html_tag_.Id__comment) {
			if (match_name_id == Html_tag_.Id__comment)
				return true;
			else
				return false;
		}

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
	public Html_tag Tag__extract(boolean move, boolean tail, int match_name_id, int tag_bgn) {
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
		Html_tag tmp = move ? tag__tmp__move : tag__tmp__peek;
		return tmp.Init(this, cur_is_tail, inline, tag_bgn, tag_end, name_end, atrs_end, name_hash.Get_as_int_or(src, name_bgn, name_end, -1));
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
		int rv = Read_int_to(to_char, Int_.Max_value); if (rv == Int_.Max_value) rdr.Fail("invalid int", "pos", pos);
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
	private Html_tag Tag__comment(int tag_bgn) {
		int tag_end = Bry_find_.Move_fwd(src, gplx.langs.htmls.Html_tag_.Comm_end, tag_bgn, src_end); if (tag_end == Bry_find_.Not_found) tag_end = src_end;
		return tag__comment.Init(this, Bool_.N, Bool_.N, tag_bgn, tag_end, tag_end, tag_end, Html_tag_.Id__comment);
	}
	private Html_tag Tag__eos(int tag_bgn) {
		int tag_end = tag_bgn + 255; if (tag_end > src_end) tag_end = src_end;
		return tag__comment.Init(this, Bool_.N, Bool_.N, tag_bgn, tag_end, tag_end, tag_end, Html_tag_.Id__eos);
	}
	private static final byte[] Bry__comment__mid = Bry_.new_a7("--"); 
}
