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
package gplx.xowa.parsers.uniqs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.commons.GfoRandom;
import gplx.types.commons.GfoRandomUtl;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
public class Xop_uniq_mgr {	// REF.MW:/parser/StripState.php
	private final Btrie_slim_mgr general_trie = Btrie_slim_mgr.cs(); private final Btrie_rv trv = new Btrie_rv();
	private final BryWtr tmp_bfr = BryWtr.NewWithSize(32);
	private int nxt_idx = -1;
	public void Clear() {
		nxt_idx = -1;
		general_trie.Clear();
	}
	public byte[] Get(byte[] key) {
		Xop_uniq_itm itm = (Xop_uniq_itm)general_trie.Match_exact(key, 0, key.length);
		return itm.Val();
	}
	public byte[] Add(boolean expand_after_template_parsing, byte[] type, byte[] val) {// "<b>" -> "\u007fUNIQ-item-1-QINU\u007f"
		int idx = ++nxt_idx;
		byte[] key = tmp_bfr	
			.Add(Bry__uniq__bgn_w_dash)          // "\u007f'\"`UNIQ-"
			.Add(type).AddByte(AsciiByte.Dash) // "ref-"
			.AddIntVariable(idx)               // "1"
			.Add(Bry__uniq__add__end)            // "-QINU`\"'\u007f"
			.ToBryAndClear();
		Xop_uniq_itm itm = new Xop_uniq_itm(expand_after_template_parsing, type, idx, key, val);
		general_trie.AddObj(key, itm);
		return key;
	}
	public void Parse(BryWtr bfr) {
		if (general_trie.Count() == 0) return;
		byte[] rv = Parse_recurse(BoolUtl.Y, tmp_bfr, bfr.ToBryAndClear());
		bfr.Add(rv);
	}
	public byte[] Parse(byte[] src) {return Parse_recurse(BoolUtl.Y, tmp_bfr, src);}
	private byte[] Parse_recurse(boolean template_parsing, BryWtr bfr, byte[] src) {
		int src_len = src.length;
		int pos = 0;
		int prv_bgn = 0;
		boolean dirty = false;

		while (true) {
			boolean is_last = pos == src_len;				
			byte b = is_last ? AsciiByte.Null : src[pos];
			Object o = general_trie.Match_at_w_b0(trv, b, src, pos, src_len);

			// match not found for "\x7fUNIQ"; move on to next
			if (o == null)
				pos++;
			// match found
			else {
				Xop_uniq_itm itm = (Xop_uniq_itm)o;
				int itm_end = trv.Pos();	// NOTE: must capture pos since trv is reused in the recursive call below
				// skip if template_parsing
				if (template_parsing
					&& !itm.Expand_after_template_parsing()) {
					pos = itm_end;
					continue;
				}

				// add everything from prv_bgn up to UNIQ
				bfr.AddMid(src, prv_bgn, pos);

				// expand UNIQ (can be recursive)
				byte[] val = Parse_recurse(template_parsing, BryWtr.New(), itm.Val());
//					val = gplx.xowa.parsers.xndes.Xop_xnde_tkn.Hack_ctx.Wiki().Parser_mgr().Main().Parse_text_to_html(gplx.xowa.parsers.xndes.Xop_xnde_tkn.Hack_ctx, val);	// CHART
				bfr.Add(val);
				dirty = true;
				pos = prv_bgn = itm_end;
			}
			if (is_last) {
				if (dirty)
					bfr.AddMid(src, prv_bgn, src_len);
				break;
			}
		}
		return dirty ? bfr.ToBryAndClear() : src;
	}
	public byte[] Uniq_bry_new() {
		return BryUtl.Add
		( Bry__uniq__bgn			// "\x7fUNIQ" where "\x7f" is (byte)127
		, Random_bry_new(16));		// random hexdecimal String
	}
	public void Random_int_ary_(int... v) {random_int_ary = v;} private int[] random_int_ary;	// TEST:
	public byte[] Random_bry_new(int len) {
		BryWtr tmp_bfr = BryWtr.New();
		GfoRandom random_gen = GfoRandomUtl.New();
		for (int i = 0; i < len; i += 7) {
			int rand = random_int_ary == null ? random_gen.Next(IntUtl.MaxValue) : random_int_ary[i / 7];
			String rand_str = IntUtl.ToStrHex(BoolUtl.N, BoolUtl.Y, rand & 0xfffffff);	// limits value to 268435455
			tmp_bfr.AddStrA7(rand_str);
		}
		byte[] rv = tmp_bfr.ToBry(0, len);
		tmp_bfr.Clear();
		return rv;
	}

	public static final byte[]
	  Bry__uniq__bgn		= BryUtl.NewA7("\u007f'\"`UNIQ-")
	, Bry__uniq__bgn_w_dash	= BryUtl.Add(Bry__uniq__bgn, AsciiByte.DashBry)
	, Bry__uniq__add__end	= BryUtl.NewA7("-QINU`\"'\u007f")
	;
}
