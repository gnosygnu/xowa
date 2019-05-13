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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.lnkes.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_anchorencode_mgr {
	private final    Xop_parser parser; // create a special-parser for handling wikitext inside {{anchorencode:}}
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Xop_ctx ctx;
	private final    byte[] src;
	public Pfunc_anchorencode_mgr(Xop_parser parser, Xop_ctx owner_ctx, byte[] src) {
		this.parser = parser;
		this.ctx = Xop_ctx.New__sub__reuse_page(owner_ctx);
		this.ctx.Lnki().Build_args_list_(true);
		this.src = src;
	}
	public void Encode_anchor(Bry_bfr bfr) {
		// parse
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(src);
		parser.Parse_wtxt_to_wdom(root, ctx, tkn_mkr, src, Xop_parser_.Doc_bgn_bos);
		int subs_len = root.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Xop_tkn_itm sub = root.Subs_get(i);
			Tkn(sub);
		}

		// write to bfr and encode it
		byte[] unencoded = tmp_bfr.To_bry_and_clear();
		Gfo_url_encoder_.Id.Encode(tmp_bfr, unencoded);
		bfr.Add_bfr_and_clear(tmp_bfr);
	}
	private void Tkn(Xop_tkn_itm sub) {
		switch (sub.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_apos: // noop
				break;
			case Xop_tkn_itm_.Tid_html_ncr:
				tmp_bfr.Add_u8_int(((Xop_amp_tkn_num)sub).Val());
				break;
			case Xop_tkn_itm_.Tid_html_ref:
				tmp_bfr.Add_u8_int(((Xop_amp_tkn_ent)sub).Char_int());
				break;
			case Xop_tkn_itm_.Tid_lnke: { // FUTURE: need to move number to lnke_tkn so that number will be correct/consistent? 
				Xop_lnke_tkn lnke = (Xop_lnke_tkn)sub;
				int subs_len = lnke.Subs_len();
				for (int i = 0; i < subs_len; i++) {
					Xop_tkn_itm lnke_sub = lnke.Subs_get(i);
					tmp_bfr.Add_mid(src, lnke_sub.Src_bgn(), lnke_sub.Src_end());
				}
				break;
			}
			case Xop_tkn_itm_.Tid_xnde: {
				Xop_xnde_tkn xnde = (Xop_xnde_tkn)sub;
				int subs_len = xnde.Subs_len();
				for (int i = 0; i < subs_len; i++) {
					Tkn(xnde.Subs_get(i));
				}
				break;
			}
			case Xop_tkn_itm_.Tid_lnki:
				Lnki((Xop_lnki_tkn)sub);
				break;
			default:
				tmp_bfr.Add_mid(src, sub.Src_bgn(), sub.Src_end());
				break;
		}		
	}
	private void Lnki(Xop_lnki_tkn lnki) {
		if (lnki.Pipe_count_is_zero()) { // trg only; EX: [[A]]
			int trg_bgn = lnki.Trg_tkn().Src_bgn();
			if (lnki.Ttl().ForceLiteralLink()) // literal link; skip colon; EX: [[:a]] -> a
				++trg_bgn;
			tmp_bfr.Add_mid(src, trg_bgn, lnki.Trg_tkn().Src_end()); // pos after last trg char; EX: "]" in "[[A]]"
		}
		else { // trg + caption + other; EX: [[A|b]]; [[File:A.png|thumb|caption]]
			List_adp args_list = lnki.Args_list();
			int len = args_list.Len();
			for (int i = 0; i < len; i++) {
				if (i != 0) tmp_bfr.Add_byte_pipe();
				Arg_nde_tkn arg = (Arg_nde_tkn)args_list.Get_at(i);
				switch (arg.Arg_tid()) {
					case Xop_lnki_arg_parser.Tid_caption:
						Xop_tkn_itm caption_tkn = lnki.Caption_val_tkn();
						int caption_subs_len = caption_tkn.Subs_len();
						for (int j = 0; j < caption_subs_len; j++) {
							Tkn(caption_tkn.Subs_get(j));
						}
						break;
					default:
						tmp_bfr.Add_mid(src, arg.Src_bgn(), arg.Src_end());
						break;
				}
			}
		}

		// add tail; EX: [[A]]b
		if (lnki.Tail_bgn() != -1)
			tmp_bfr.Add_mid(src, lnki.Tail_bgn(), lnki.Tail_end());
	}
}
