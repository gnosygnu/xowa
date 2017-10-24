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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.langs.htmls.entitys.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.xtns.cites.*;
public class Xoh_lnki_title_bldr {
	public static void Add(Bry_bfr bfr, byte[] src, Xop_tkn_itm tkn) {Add_recurse(bfr, src, tkn);}
	private static void Add_recurse(Bry_bfr bfr, byte[] src, Xop_tkn_itm tkn) {
		switch (tkn.Tkn_tid()) {				
			case Xop_tkn_itm_.Tid_newLine: case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_txt: // leaf tkns have no subs
				Write_atr_text(bfr, src, tkn.Src_bgn(), tkn.Src_end());
				break;				
			case Xop_tkn_itm_.Tid_arg_nde: // caption tkns have no subs; just a key and a val; recurse val
				Add_recurse(bfr, src, ((Arg_nde_tkn)tkn).Val_tkn());
				break;
			case Xop_tkn_itm_.Tid_lnki:
				Xop_lnki_tkn tkn_as_lnki = (Xop_lnki_tkn)tkn;
				if (tkn_as_lnki.Caption_exists())
					Add_recurse(bfr, src, tkn_as_lnki.Caption_tkn());
				else {
					if (tkn_as_lnki.Ttl() != null) {	// guard against invalid ttls
						byte[] ttl_bry = tkn_as_lnki.Ttl().Page_txt();
						Write_atr_text(bfr, ttl_bry, 0, ttl_bry.length); // handle titles with quotes; PAGE:s.w:Styx_(band) DATE:2015-11-29
					}
				}
				if (tkn_as_lnki.Tail_bgn() != -1)
					bfr.Add_mid(src, tkn_as_lnki.Tail_bgn(), tkn_as_lnki.Tail_end());
				break;				
			default: // all other tkns, just iterate over subs for txt tkns
				if (tkn.Tkn_tid() == Xop_tkn_itm_.Tid_xnde) {
					Xop_xnde_tkn xnde = (Xop_xnde_tkn)tkn; 
					if (xnde.Tag().Id() == Xop_xnde_tag_.Tid__ref) {	// if ref, disable tkn
						Ref_nde ref_xnde = (Ref_nde)xnde.Xnde_xtn();
						ref_xnde.Exists_in_lnki_title_(true);	// ref found during html_title_wkr's generation; mark ref; will be ignored by references_html_wtr later; DATE:2014-03-05
					}
				}
				int len = tkn.Subs_len();
				for (int i = 0; i < len; i++)
					Add_recurse(bfr, src, tkn.Subs_get(i));
				break;
		}
	}
	private static void Write_atr_text(Bry_bfr bfr, byte[] src, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Tab:		// NOTE: escape ws so that it renders correctly in tool tips
					bfr.Add_byte_space();
					break;
				case Byte_ascii.Quote:		bfr.Add(Gfh_entity_.Quote_bry); break;
				case Byte_ascii.Lt:			bfr.Add(Gfh_entity_.Lt_bry); break;
				case Byte_ascii.Gt:			bfr.Add(Gfh_entity_.Gt_bry); break;
				case Byte_ascii.Amp:		bfr.Add(Gfh_entity_.Amp_bry); break;
				default:					bfr.Add_byte(b); break;
			}
		}
	}
}
