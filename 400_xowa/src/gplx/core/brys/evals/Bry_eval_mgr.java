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
package gplx.core.brys.evals;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
public class Bry_eval_mgr {
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	private final byte sym_escape, sym_key_end, sym_grp_bgn, sym_grp_end;
	public Bry_eval_mgr(byte sym_escape, byte sym_key_end, byte sym_grp_bgn, byte sym_grp_end) {
		this.sym_escape = sym_escape;
		this.sym_key_end = sym_key_end;
		this.sym_grp_bgn = sym_grp_bgn;
		this.sym_grp_end = sym_grp_end;
	}
	public Bry_eval_mgr Add_many(Bry_eval_wkr... ary) {
		for (Bry_eval_wkr itm : ary)
			hash.Add(BryUtl.NewU8(itm.Key()), itm);
		return this;
	}
	public byte[] Eval(byte[] src) {
		Bry_eval_rslt rv = Eval_txt(0, src, 0, src.length);
		return rv == null ? src : rv.Bry();
	}
	private Bry_eval_rslt Eval_txt(int depth, byte[] src, int src_bgn, int src_end) {
		BryWtr cur_bfr = null;
		int cur_pos = src_bgn;
		while (true) {
			if (cur_pos == src_end) break;
			byte cur_byte = src[cur_pos];
			// cur_byte is ~
			if (cur_byte == sym_escape) {
				// create cur_bfr
				if (cur_bfr == null) {cur_bfr = BryWtr.New(); cur_bfr.AddMid(src, src_bgn, cur_pos);}

				// eval nxt_byte
				int nxt_pos = cur_pos + 1;
				if (nxt_pos == src_end) throw ErrUtl.NewArgs("bry_eval:escape at eos", "src", src);
				byte nxt_byte = src[nxt_pos];
				if	(nxt_byte == sym_grp_bgn) {	// ~{key|} -> eval;
					Bry_eval_rslt sub = Eval_txt(depth + 1, src, nxt_pos + 1, src_end); // get "}"
					cur_bfr.Add(Eval_grp(sub.Bry()));
					cur_pos = sub.Pos();
					continue;
				}
				else if (nxt_byte == sym_escape) {
					cur_bfr.AddByte(nxt_byte);
					cur_pos = nxt_pos + 1;
					continue;
				}
			}
			else if (depth > 0 && cur_byte == sym_grp_end) {
				return cur_bfr == null 
					? new Bry_eval_rslt(BryLni.Mid(src, src_bgn, cur_pos), cur_pos + 1)
					: new Bry_eval_rslt(cur_bfr.ToBryAndClear(), cur_pos + 1);
			}					
			if (cur_bfr != null) cur_bfr.AddByte(cur_byte);
			++cur_pos;
		}
		return cur_bfr == null ? null : new Bry_eval_rslt(cur_bfr.ToBryAndClear(), src_end);
	}
	private byte[] Eval_grp(byte[] src) {
		// search for "|" or "}"
		boolean args_is_empty = true;
		int src_end = src.length;
		int key_bgn = 0, key_end = src_end;
		for (int i = key_bgn; i < src_end; ++i) {
			int key_end_byte = src[i];
			if		(key_end_byte == sym_key_end) {
				key_end = i;
				args_is_empty = false;
				break;
			}
		}

		// get wkr
		Bry_eval_wkr wkr = (Bry_eval_wkr)hash.Get_by_mid(src, key_bgn, key_end);
		if (wkr == null) throw ErrUtl.NewArgs("bry_eval:key not found", "src", src);
		BryWtr bfr = BryWtr.New();
		if (args_is_empty) {
			wkr.Resolve(bfr, src, -1, -1);
		}
		else {
			wkr.Resolve(bfr, src, key_end + 1, src_end);
		}
		return bfr.ToBryAndClear();
	}
	public static Bry_eval_mgr Dflt() {return new Bry_eval_mgr(AsciiByte.Tilde, AsciiByte.Pipe, AsciiByte.CurlyBgn, AsciiByte.CurlyEnd);}
}
class Bry_eval_rslt {
	public Bry_eval_rslt(byte[] bry, int pos) {
		this.bry = bry; this.pos = pos;
	}
	public byte[] Bry() {return bry;} private final byte[] bry;
	public int Pos() {return pos;} private final int pos;
}
