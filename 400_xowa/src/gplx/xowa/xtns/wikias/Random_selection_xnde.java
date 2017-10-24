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
package gplx.xowa.xtns.wikias; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*; import gplx.xowa.parsers.tmpls.*;
import gplx.core.btries.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
public class Random_selection_xnde implements Xox_xnde, Mwh_atr_itm_owner2 {
	private byte[] val = Bry_.Empty;
	private byte[] choicetemplate = null, atr_before = null, atr_after = null;
	private Rndsel_option_itm[] options_ary;
	private int weight_total = 0;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, byte xatr_id) {
		switch (xatr_id) {
			case Xatr__before:		atr_before = xatr.Val_as_bry(); break;
			case Xatr__after:		atr_after = xatr.Val_as_bry(); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);

		Xox_xnde_.Parse_xatrs(wiki, this, xatrs_hash, src, xnde);

		// parse <choose>
		List_adp option_list = List_adp_.New();
		Gfh_tag_rdr tag_rdr = Xox_xnde_.New_tag_rdr(ctx, src, xnde).Reg("option", Tag__option).Reg("choicetemplate", Tag__choicetemplate);
		while (true) {
			Gfh_tag head_tag = tag_rdr.Tag__move_fwd_head(Gfh_tag_.Id__any);
			int tag_tid = head_tag.Name_id();
			boolean eos = false;
			switch (tag_tid) {
				case Gfh_tag_.Id__eos:			eos = true; break;
				default:						continue;
				case Tag__option:
				case Tag__choicetemplate:		break;
			}
			if (eos) break;

			int head_tag_end = head_tag.Src_end();
			Gfh_tag tail_tag = tag_rdr.Tag__move_fwd_tail(tag_tid);
			int tail_tag_bgn = tail_tag.Src_bgn();
			switch (tag_tid) {
				case Tag__choicetemplate: {
					if (choicetemplate == null) {	// only one <choicetemplate> should be specified; if many, always take 1st
						choicetemplate = Bry_.Mid(src, head_tag_end, tail_tag_bgn);
					}
					break;
				}
				case Tag__option: {
					int weight = tail_tag.Atrs__get_as_int_or(Atr__weight, 1);
					option_list.Add(new Rndsel_option_itm(weight, Bry_.Mid(src, head_tag_end, tail_tag_bgn)));
					weight_total += weight;
					break;
				}
			}
		}
		if (weight_total == 0) return; // empty <choose> will be 0
		this.options_ary = (Rndsel_option_itm[])option_list.To_ary_and_clear(Rndsel_option_itm.class);

		// randomly select option_bry from all <option>s
		byte[] option_bry = null;
		int rnd = Rnd_test == -1 ? RandomAdp_.new_().Next(weight_total) + 1 : Rnd_test;
		int options_len = options_ary.length;
		for (int i = 0; i < options_len; ++i) {
			Rndsel_option_itm option = options_ary[i];
			rnd -= option.Weight;
			if (rnd <= 0) {
				option_bry = option.Text;
				break;
			}
		}

		// decorate option_bry
		if (choicetemplate != null) {
			option_bry = Bry_.Add(Xop_curly_bgn_lxr.Hook, choicetemplate, Byte_ascii.Pipe_bry, option_bry, Xop_curly_end_lxr.Hook);
		}
		if (!Bry_.Eq(atr_before, Bry_.Empty)) option_bry = Bry_.Add(atr_before, option_bry);
		if (!Bry_.Eq(atr_after , Bry_.Empty)) option_bry = Bry_.Add(option_bry, atr_after);

		this.val = Xop_parser_.Parse_text_to_html(wiki, ctx, ctx.Page(), ctx.Page().Ttl(), option_bry, false);
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (val != null) bfr.Add(val);
	}
	public static final byte Xatr__before = 0, Xatr__after = 1;
	private static final    Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7().Add_str_byte("before", Xatr__before).Add_str_byte("after", Xatr__after);
	private static final byte Tag__option = 0, Tag__choicetemplate = 1;
	private static final    byte[] Atr__weight = Bry_.new_a7("weight");
	public static int Rnd_test = -1;
}
class Rndsel_option_itm {
	public Rndsel_option_itm(int weight, byte[] text) {this.Weight = weight; this.Text = text;}
	public final    int Weight;
	public final    byte[] Text;
}
