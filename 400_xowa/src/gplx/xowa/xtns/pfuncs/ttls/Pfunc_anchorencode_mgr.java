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
public class Pfunc_anchorencode_mgr {	// TS
	private final    Xop_parser parser; // create a special-parser for handling wikitext inside {{anchorencode:}}
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);		
	public Pfunc_anchorencode_mgr(Xowe_wiki wiki) {
		this.parser = Xop_parser.new_(wiki, wiki.Parser_mgr().Main().Tmpl_lxr_mgr(), Xop_lxr_mgr.new_anchor_encoder());
		parser.Init_by_wiki(wiki);
		parser.Init_by_lang(wiki.Lang());
	}
	public boolean Used() {return used;} private boolean used;
	public void Used_(boolean v) {used = v;}
	public void Encode_anchor(Bry_bfr bfr, Xop_ctx ctx, byte[] src) {
		// parse {{anchorencode:}}; note that wikitext inside anchorencode gets serialized by different rules
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		boolean para_enabled = ctx.Para().Enabled();
		ctx.Para().Enabled_n_();	// HACK: disable para
		try {
			Xop_root_tkn root = tkn_mkr.Root(src);
			parser.Parse_wtxt_to_wdom(root, ctx, tkn_mkr, src, Xop_parser_.Doc_bgn_bos);
			int subs_len = root.Subs_len();
			for (int i = 0; i < subs_len; i++) {
				Xop_tkn_itm sub = root.Subs_get(i);
				Tkn(ctx, src, sub, root, i, tmp_bfr);
			}
		} finally {ctx.Para().Enabled_(para_enabled);}

		// write to bfr and encode it
		byte[] unencoded = tmp_bfr.To_bry_and_clear();
		Gfo_url_encoder_.Id.Encode(tmp_bfr, unencoded);
		bfr.Add_bfr_and_clear(tmp_bfr);
	}
	private static void Tkn(Xop_ctx ctx, byte[] src, Xop_tkn_itm sub, Xop_tkn_grp grp, int sub_idx, Bry_bfr tmp_bfr) {
		switch (sub.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_lnke: Lnke(src, (Xop_lnke_tkn)sub, tmp_bfr); break;	// FUTURE: need to move number to lnke_tkn so that number will be correct/consistent? 
			case Xop_tkn_itm_.Tid_lnki: Lnki(src, (Xop_lnki_tkn)sub, tmp_bfr); break;
			case Xop_tkn_itm_.Tid_apos: break; // noop
			case Xop_tkn_itm_.Tid_xnde: Xnde(ctx, src, (Xop_xnde_tkn)sub, tmp_bfr); break;
			case Xop_tkn_itm_.Tid_html_ncr: tmp_bfr.Add_u8_int(((Xop_amp_tkn_num)sub).Val()); break;
			case Xop_tkn_itm_.Tid_html_ref: tmp_bfr.Add_u8_int(((Xop_amp_tkn_ent)sub).Char_int()); break;
			case Xop_tkn_itm_.Tid_tmpl_invk:
				Xot_invk_tkn invk_tkn = (Xot_invk_tkn)sub;
				Arg_itm_tkn name_tkn = invk_tkn.Name_tkn().Key_tkn();
				int name_ary_bgn = name_tkn.Src_bgn() + 1, name_ary_end = name_tkn.Src_end();
				byte[] name_ary = Bry_.Mid(src, name_ary_bgn, name_ary_end);	// + 1 to skip :
				int name_ary_len = name_ary_end - name_ary_bgn; 
				if (name_ary_len > 0 && name_ary[0] == Byte_ascii.Colon)			// has initial colon; EX: {{:a}
					tmp_bfr.Add_mid(name_ary, 1, name_ary_len);						// 1 to skip initial colon
				else																// regular tmpl; EX: {{a}}
					tmp_bfr.Add(ctx.Wiki().Ns_mgr().Ns_template().Gen_ttl(name_ary));
				break;
			default: tmp_bfr.Add_mid(src, sub.Src_bgn_grp(grp, sub_idx), sub.Src_end_grp(grp, sub_idx)); break;
		}		
	}
	private static void Lnke(byte[] src, Xop_lnke_tkn lnke, Bry_bfr tmp_bfr) {
		int subs_len = lnke.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Xop_tkn_itm lnke_sub = lnke.Subs_get(i);
			tmp_bfr.Add_mid(src, lnke_sub.Src_bgn_grp(lnke, i), lnke_sub.Src_end_grp(lnke, i));
		}
	}
	private static void Lnki(byte[] src, Xop_lnki_tkn lnki, Bry_bfr tmp_bfr) {
		int src_end = lnki.Src_end();
		int trg_end = lnki.Trg_tkn().Src_end();
		
		if (trg_end == src_end - Xop_tkn_.Lnki_end_len) {		// only trg
			int trg_bgn = lnki.Trg_tkn().Src_bgn();
			if (lnki.Ttl().ForceLiteralLink()) ++trg_bgn;		// literal link; skip colon; EX: [[:a]] -> a
			tmp_bfr.Add_mid(src, trg_bgn, trg_end);			
		}
		else {
			tmp_bfr.Add_mid(src, trg_end + 1, src_end - Xop_tkn_.Lnki_end_len); //+1 is len of pipe
		}
	}
	private static void Xnde(Xop_ctx ctx, byte[] src, Xop_xnde_tkn xnde, Bry_bfr tmp_bfr) {
		int subs_len = xnde.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Tkn(ctx, src, xnde.Subs_get(i), xnde, i, tmp_bfr);
		}		
	}
}
