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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.primitives.*; import gplx.core.flds.*;
public class Xoctg_idx_mgr implements GfoInvkAble {
	List_adp itms = List_adp_.new_();
	public int Block_len() {return block_len;} public Xoctg_idx_mgr Block_len_(int v) {this.block_len = v; return this;} private int block_len = Io_mgr.Len_mb;
	public int Itms_len() {return itms.Count();}
	public Xoctg_idx_itm Itms_get_at(int i) {return (Xoctg_idx_itm)itms.Get_at(i);}
	public void Itms_add(Xoctg_idx_itm itm) {itms.Add(itm);}
	public byte[] Src() {return src;} public Xoctg_idx_mgr Src_(byte[] v) {src = v; return this;} private byte[] src;
	public int Total() {return total;} public Xoctg_idx_mgr Total_(int v) {total = v; return this;} private int total;
	public void Index(Gfo_usr_dlg usr_dlg, byte[] ctg, byte[] src) {
		int len = src.length;
		// if (len < block_len) return; NOTE: do not exit early; need at least one entry or Itms_binary_search will fail
		itms.Clear();
		fld_rdr.Data_(src);
		int pipe_pos_cur = -1, pipe_pos_prv = -1;
		for (int i = block_len; i < len; i += block_len) {
			usr_dlg.Prog_many("", "", "indexing ~{0} ~{1}", i, len);
			pipe_pos_cur = Bry_find_.Find_fwd(src, Byte_ascii.Pipe, i, len); if (pipe_pos_cur == Bry_find_.Not_found) throw Err_.new_wo_type("ctg_idx_mgr could not find pipe.next", "ctg", String_.new_a7(ctg), "pos", i);
			if (pipe_pos_cur == len - 1) break;
			Index_itm(ctg, src, pipe_pos_cur + 1, len);	// +1 to skip pipe
			pipe_pos_prv = pipe_pos_cur;
		}
		pipe_pos_cur = Bry_find_.Find_bwd(src, Byte_ascii.Pipe, len - 1, 0); if (pipe_pos_cur == Bry_find_.Not_found) pipe_pos_cur = 0; // 1 entry only; will not have preceding pipe
		if (pipe_pos_cur != pipe_pos_prv)	// if last itm was not indexed, index it
			Index_itm(ctg, src, pipe_pos_cur + 1, len);
	}
	private void Index_itm(byte[] ctg, byte[] src, int bgn, int len) {
		int end = Bry_find_.Find_fwd(src, Byte_ascii.Pipe, bgn, len); if (end == Bry_find_.Not_found) throw Err_.new_wo_type("Ctg_idx_mgr could not find pipe.end", "ctg", String_.new_a7(ctg), "pos", bgn);
		fld_rdr.Pos_(bgn);
		Xoctg_idx_itm itm = new Xoctg_idx_itm().Parse(fld_rdr, bgn);
		itms.Add(itm);
	}
	public Xoctg_idx_itm Itms_binary_search(byte[] find, Int_obj_ref comp_rslt) {
		int max = itms.Count() - 1;
		int dif = max / 2;
		int pos = dif;
		Xoctg_idx_itm rv = null;
		int comp_prv = Int_.Min_value, comp_cur = 0;
		while (true) {
			rv = (Xoctg_idx_itm)itms.Get_at(pos);
			comp_cur = Bry_.Compare(find, rv.Sortkey());
			// Tfds.Write(dif, pos, comp_cur, comp_prv, String_.new_a7(find), String_.new_a7(rv.Sortkey()));
			if (comp_cur == CompareAble_.Same) break;		// exact match; stop
			dif /= 2;
			if (dif == 0) dif = 1;							// make sure dif is at least 1
			if (dif == 1 && comp_cur != comp_prv) break;	// if dif == 1 && comp has switched; i.e: "interval is as small as it can be, and search has bounced over result (found item above and found item below)"
			pos = comp_cur == CompareAble_.More ? pos + dif : pos - dif;
			if (pos < 0 || pos > max) break;
			comp_prv = comp_cur;
		}
		comp_rslt.Val_(comp_cur);
		return rv;
	}
	public Xoctg_idx_itm Find_itm_near_bmk(byte[] src, int src_len, byte[] find, boolean arg_is_from, int bmk_comp, int bmk_bgn) {
		/*
		if		arg_is_from
		. if find > idx.key, search bwd until find < idx; EX: A50 is find; A48A is idx; search bwd; A49 (still >); A48 (< break); take prv
		. if find < idx.key, search fwd until find > idx; EX: A50 is find; A51A is idx; search fwd; A51 (still <); A52 (> break); take cur
		else if arg_is_until
		. if find > idx.key, search fwd until find < idx; EX: A50 is find; A48A is idx; search bwd; A49 (still >); A48 (< break); take cur
		. if find < idx.key, search bwd until find > idx; EX: A50 is find; A51A is idx; search fwd; A51 (still <); A52 (> break); take prv
		*/
		boolean dir_fwd = bmk_comp == CompareAble_.More;		// bmk is > than find; move forward			
		fld_rdr.Data_(src);
		int comp_prv = bmk_comp, comp_cur = Int_.Min_value; int pos_cur = bmk_bgn;
		tmp_prv_itm.Parse(fld_rdr.Pos_(pos_cur), pos_cur);	// fill prv_itm to whatever binary search found
		while (true) {
			int itm_bgn = dir_fwd ? Bry_find_.Find_fwd(src, Byte_ascii.Pipe, pos_cur, src_len) : Bry_find_.Find_bwd(src, Byte_ascii.Pipe, pos_cur);
			if (itm_bgn == Bry_find_.Not_found) {			// stop: at first && searched bwd; note that at last && searched fwd will never return Bry_find_.Not_found b/c all srcs are terminated with |
				if (!arg_is_from)	return null;		// arg is until and nothing found; return null: EX: range of B-Y and find of until=A; no results
				else				break;				// arg is from; stop loop; (will use first item)
			}
			++itm_bgn;									// position after pipe
			if (itm_bgn == src_len) {					// stop: at last && searched fwd
				if (arg_is_from)	return null;		// arg is from and nothing found; return null: EX: range of B-Y and find of from=Z; no results
				else				break;				// arg is until; stop loop; (will use last item)
			}
			tmp_cur_itm.Parse(fld_rdr.Pos_(itm_bgn), itm_bgn);
			comp_cur = Bry_.Compare(find, tmp_cur_itm.Sortkey());
			if (comp_cur != comp_prv) break;			// stop: comp_val switched; see note above
			pos_cur = dir_fwd ? itm_bgn : itm_bgn - 1;	// -1 to position at pipe (note that -1 in FindBwd will position before)
			tmp_prv_itm.Copy(tmp_cur_itm);
		}
		// Tfds.Write(arg_is_from, dir_fwd, String_.new_u8(find), String_.new_u8(tmp_cur_itm.Sortkey()), String_.new_u8(tmp_prv_itm.Sortkey()));
		if (comp_cur == CompareAble_.Same) return tmp_cur_itm;
		return (arg_is_from && dir_fwd) || (!arg_is_from && !dir_fwd) ? tmp_cur_itm : tmp_prv_itm;	// see note above
	}	private Xoctg_idx_itm tmp_cur_itm = new Xoctg_idx_itm(), tmp_prv_itm = new Xoctg_idx_itm(); Gfo_fld_rdr fld_rdr = Gfo_fld_rdr.xowa_();
	public void Find(List_adp rv, byte[] src, boolean arg_is_from, byte[] find, int find_count, Xoctg_view_itm last_plus_one) {
		int tmp_pos = 0; int src_len = src.length;
		last_plus_one.Clear();
		if (find != null) {	// find == null when no args passed; EX: "en.wikipedia.org/wiki/Category:A" (vs "en.wikipedia.org/wiki/Category:A?from=B")
			Xoctg_idx_itm itm_0 = Itms_binary_search(find, find_rslt.Val_(Byte_.Zero));
			if (find_rslt.Val() != CompareAble_.Same) {
				itm_0 = Find_itm_near_bmk(src, src_len, find, arg_is_from, find_rslt.Val(), itm_0.Pos());
				if (itm_0 == null) return;	// itm out of range; EX: pages are B-Y and find is A or Z
			}
			tmp_pos = itm_0.Pos();
			if (!arg_is_from && Bry_.Compare(find, itm_0.Sortkey()) != CompareAble_.More) {	// "until" means do *not* include last; go back one more; note: only do this logic if find is either < or == to slot; EX: find=AM && slot=AL; do not go back one more
				tmp_pos = Bry_find_.Find_bwd(src, Byte_ascii.Pipe, tmp_pos - 1);	// -1 to position before pipe
				if (tmp_pos == Bry_find_.Not_found) return;	// already 1st and nothing found
				else tmp_pos++;
			}
		}
		fld_rdr.Data_(src);
		for (int i = 0; i < find_count; i++) {
			fld_rdr.Pos_(tmp_pos);
			Xoctg_view_itm itm = new Xoctg_view_itm().Parse(fld_rdr.Pos_(tmp_pos), tmp_pos);
			rv.Add(itm);
			if (!arg_is_from && tmp_pos == 0) break;	// 1st item and moving bwd; stop; note that 1st item does not have preceding |
			tmp_pos = arg_is_from ? Bry_find_.Find_fwd(src, Byte_ascii.Pipe, tmp_pos, src_len) : Bry_find_.Find_bwd(src, Byte_ascii.Pipe, tmp_pos - 1);	// -1 to position before pipe
			if (tmp_pos == Bry_find_.Not_found) {
				if (arg_is_from)	// moving fwd and no pipe found; exit;
					break;
				else				// moving bwd and no pipe found; position at 1st item (which doesn't have a pipe); note that -1 will become 0
					tmp_pos = -1;
			}
			++tmp_pos;				// position after pipe
			if (tmp_pos == src_len) break; // last pipe; stop
		}
		if (!arg_is_from)
			rv.Sort_by(Xoctg_view_itm_sorter_sortkey.Instance);
		int rv_count = rv.Count();
		if (rv_count > 0) {
			Xoctg_view_itm last_itm = (Xoctg_view_itm)rv.Get_at(rv_count - 1);
			int last_itm_pos = last_itm.Sort_idx();
			tmp_pos = Bry_find_.Find_fwd(src, Byte_ascii.Pipe, last_itm_pos);
			if (tmp_pos != Bry_find_.Not_found && tmp_pos < src_len - 1) {
				++tmp_pos;	// position after pipe
				last_plus_one.Parse(fld_rdr.Pos_(tmp_pos), tmp_pos);
			}
		}
	}	Int_obj_ref find_rslt = Int_obj_ref.zero_();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_block_len)) 		return block_len;
		else if	(ctx.Match(k, Invk_block_len_)) 	block_len = m.ReadInt("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_block_len = "block_len", Invk_block_len_ = "block_len_";
}
