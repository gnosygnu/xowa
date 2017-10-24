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
package gplx.xowa.langs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
public class Xol_num_fmtr_base implements Gfo_invk {
	private final    Btrie_fast_mgr dlm_trie = Btrie_fast_mgr.cs(); private final    Btrie_rv trv = new Btrie_rv();
	private Xol_num_grp[] grp_ary = Xol_num_grp.Ary_empty; int grp_ary_len;
	private Gfo_num_fmt_wkr[] cache; int cache_len = 16;
	private Bry_bfr tmp = Bry_bfr_.New();
	public boolean Standard() {return standard;} private boolean standard = true;
	public byte[] Dec_dlm() {return dec_dlm;} public Xol_num_fmtr_base Dec_dlm_(byte[] v) {this.dec_dlm = v; dlm_trie.Add_bry_byte(v, Raw_tid_dec); return this;} private byte[] dec_dlm = Dec_dlm_default;
	private byte[] grp_dlm;
	public byte[] Raw(byte tid, byte[] src) {
		int src_len = src.length;
		for (int i = 0; i < src_len; i++) {
			byte b = src[i];
			Object o = dlm_trie.Match_at(trv, src, i, src_len);
			if (o == null)
				tmp.Add_byte(b);
			else {
				byte dlm_tid = ((Byte_obj_val)o).Val();
				int dlm_match_pos = trv.Pos();
				switch (dlm_tid) {
					case Raw_tid_dec: 
						if (tid == Tid_raw)
							tmp.Add_byte(Byte_ascii.Dot);	// NOTE: dec_dlm is always outputted as dot, not regional dec_spr; EX: for dewiki, 12,34 -> 12.34
						else
							tmp.Add(dec_dlm);
						break; 
					case Raw_tid_grp: {
						if (tid == Tid_raw) {}	// never add grp_sep for raw
						else					// add raw grp_spr
							tmp.Add_mid(src, i, dlm_match_pos);
						break;
					}
				}
				i = dlm_match_pos - 1; // NOTE: handle multi-byte delims
			}
		}
		return tmp.To_bry_and_clear();
	}
	public byte[] Fmt(int val) {return Fmt(Bry_.new_a7(Int_.To_str(val)));}
	public byte[] Fmt(byte[] src) {	// SEE: DOC_1:Fmt
		int src_len = src.length;
		int num_bgn = -1, dec_pos = -1;
		for (int i = 0; i < src_len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:						
					if (dec_pos == -1) {	// no decimal seen
						if (num_bgn == -1)	// num_bgn hasn't started
							num_bgn = i;	// set num_bgn
					}
					else					// decimal seen; add rest of src literally
						tmp.Add_byte(b);
					break;
				default:					// non-number; includes alpha chars, as well as ".", "," and other potential separators
					if (num_bgn != -1) {	// number started; format group; EX: 1234. -> 1,234.
						Gfo_num_fmt_wkr wkr = Get_or_new(i - num_bgn);
						wkr.Fmt(src, num_bgn, i, tmp);
						num_bgn = dec_pos = -1;	// reset vars
						if (b == Byte_ascii.Dot			// current char is "."; NOTE: all languages treat "." as decimal separator for parse; EX: for de, "1.23" is "1,23" DATE:2013-10-21
							//|| Bry_.Has_at_bgn(src, dec_dlm, i, src_len)
							) {	// current char is languages's decimal delimiter; note this can be "," or any other multi-byte separator
							dec_pos = i;
//								i += dec_dlm.length - 1;
							tmp.Add(dec_dlm);
							continue;
						}
					}
					if (b == Byte_ascii.Comma)
						tmp.Add(grp_dlm);
					else
						tmp.Add_byte(b);
					break;
			}
		}
		if (num_bgn != -1) {			// digits left unprocessed
			Gfo_num_fmt_wkr wkr = Get_or_new(src_len - num_bgn);
			wkr.Fmt(src, num_bgn, src_len, tmp);
		}
		return tmp.To_bry_and_clear();
	}
	private Gfo_num_fmt_wkr Get_or_new(int src_len) {
		Gfo_num_fmt_wkr rv = null;
		if (src_len < cache_len) {
			rv = cache[src_len];
			if (rv != null) return rv;
		}
		rv = new Gfo_num_fmt_wkr(grp_ary, grp_ary_len, src_len);
		if (src_len < cache_len) cache[src_len] = rv;
		return rv;
	}
	public Xol_num_grp Grps_get_last() {return grp_ary[grp_ary_len - 1];}
	public Xol_num_grp Grps_get(int i) {return grp_ary[i];}
	public int Grps_len() {return grp_ary_len;}
	public void Grps_add(Xol_num_grp dat_itm) {
		standard = false;
		this.grp_ary = (Xol_num_grp[])Array_.Resize(grp_ary, grp_ary_len + 1);
		grp_ary[grp_ary_len] = dat_itm;
		grp_ary_len = grp_ary.length;
		for (int i = 0; i < grp_ary_len; i++) {
			Xol_num_grp itm = grp_ary[i];
			byte[] itm_dlm = itm.Dlm();
			Object o = dlm_trie.Match_exact(itm_dlm, 0, itm_dlm.length);	// check for existing Object
			if (o == null) {
				dlm_trie.Add_bry_byte(itm_dlm, Raw_tid_grp);
				grp_dlm = itm_dlm;
			}
		}
	}
	public Xol_num_fmtr_base Clear() {
		this.grp_ary = Xol_num_grp.Ary_empty;
		grp_ary_len = 0;
		cache = new Gfo_num_fmt_wkr[cache_len];
		dlm_trie.Clear();
		return this;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_dec_dlm_))		this.Dec_dlm_(m.ReadBry("v"));	// NOTE: must call mutator
		else if	(ctx.Match(k, Invk_clear))			this.Clear();
		else if	(ctx.Match(k, Invk_grps_add))		this.Grps_add(new Xol_num_grp(m.ReadBry("dlm"), m.ReadInt("digits"), m.ReadYn("repeat")));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_dec_dlm_ = "dec_dlm_", Invk_clear = "clear", Invk_grps_add = "grps_add";
	private static final byte Raw_tid_dec = 0, Raw_tid_grp = 1;
	private static final    byte[] Dec_dlm_default = new byte[] {Byte_ascii.Dot};
	public static final    byte[] Grp_dlm_default = new byte[] {Byte_ascii.Comma};
	public static final byte Tid_format = 0, Tid_raw = 1, Tid_nosep = 2;
}
class Gfo_num_fmt_wkr {
	public void Fmt(byte[] src, int bgn, int end, Bry_bfr bb) {
		if (itm_max == 0) {bb.Add_mid(src, bgn, end); return;}; // NOTE: small numbers (<=3) will have a 0-len ary
		int cur_idx = itm_max - 1;
		Gfo_num_fmt_bldr cur = itm_ary[cur_idx];
		int cur_pos = cur.Pos();
		for (int i = bgn; i < end; i++) {
			if (i == cur_pos + bgn) {
				cur.Gen(bb);
				if (cur_idx > 0) cur = itm_ary[--cur_idx];
				cur_pos = cur.Pos();
			}
			bb.Add_byte(src[i]);
		}
	}
	public Gfo_num_fmt_wkr(Xol_num_grp[] grp_ary, int grp_ary_len, int src_len) {
		itm_ary = new Gfo_num_fmt_bldr[src_len];					// default to src_len; will resize below;
		int src_pos = src_len, dat_idx = 0, dat_repeat = -1;
		while (true) {
			if (dat_idx == grp_ary_len) dat_idx = dat_repeat;	// no more itms left; return to repeat
			Xol_num_grp dat = grp_ary[dat_idx];
			src_pos -= dat.Digits();
			if (src_pos < 1) break;								// no more digits needed; stop
			byte[] dat_dlm = dat.Dlm();
			itm_ary[itm_max++] = dat_dlm.length == 1 ? new Gfo_num_fmt_bldr_one(src_pos, dat_dlm[0]) : (Gfo_num_fmt_bldr)new Gfo_num_fmt_bldr_many(src_pos, dat_dlm);
			if (dat.Repeat() && dat_repeat == -1) dat_repeat = dat_idx;
			++dat_idx;
		}
		itm_ary = (Gfo_num_fmt_bldr[])Array_.Resize(itm_ary, itm_max);
	}
	private Gfo_num_fmt_bldr[] itm_ary; private int itm_max;
}
interface Gfo_num_fmt_bldr {
	int Pos();
	void Gen(Bry_bfr bb);
}
class Gfo_num_fmt_bldr_one implements Gfo_num_fmt_bldr {
	public int Pos() {return pos;} private int pos;
	public void Gen(Bry_bfr bb) {bb.Add_byte(b);}
	public Gfo_num_fmt_bldr_one(int pos, byte b) {this.pos = pos; this.b = b;} private byte b;
}
class Gfo_num_fmt_bldr_many implements Gfo_num_fmt_bldr {
	public int Pos() {return pos;} private int pos;
	public void Gen(Bry_bfr bb) {bb.Add(ary);}
	public Gfo_num_fmt_bldr_many(int pos, byte[] ary) {this.pos = pos; this.ary = ary;} private byte[] ary;
}
/*
DOC_1:Fmt
. mediawiki does the following (from Language.php|commafy
.. split the number by digitGoupingPattern: ###,###,### -> 3,3,3
.. use regx to search for number groups
.. for each number group, format with "," and "."
.. replace final result with languages's decimal / grouping entry from separatorTransformTable
. XOWA does the following
.. iterate over bytes until non-number reached
.. take all seen numbers and format according to lang
*/
