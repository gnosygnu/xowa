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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.parsers.htmls.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.amps.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.langs.vnts.converts.*;
import gplx.xowa.htmls.*;
class Vnt_html_doc_wkr implements Mwh_doc_wkr {
	private final    Hash_adp_bry atr_hash = Hash_adp_bry.ci_a7();
	private final    Xol_convert_mgr convert_mgr; private final    Xol_vnt_regy vnt_regy;
	private Vnt_convert_lang atr_converter;
	private Xol_vnt_itm vnt_itm; private int convert_vnt_idx;
	private Bry_bfr bfr;
	public Vnt_html_doc_wkr(Xol_convert_mgr convert_mgr, Xol_vnt_regy vnt_regy) {
		this.convert_mgr = convert_mgr; this.vnt_regy = vnt_regy;
		atr_hash.Add_many_str("title", "alt");
	}
	public Hash_adp_bry Nde_regy() {return nde_regy;} private final    Hash_adp_bry nde_regy = Mwh_doc_wkr_.Nde_regy__mw();
	public void Init(Bry_bfr bfr, Xol_vnt_itm vnt_itm) {this.bfr = bfr; this.vnt_itm = vnt_itm; this.convert_vnt_idx = vnt_itm.Idx();}
	public void On_atr_each			(Mwh_atr_parser mgr, byte[] src, int nde_tid, boolean valid, boolean repeated, boolean key_exists, byte[] key_bry, byte[] val_bry_manual, int[] itm_ary, int itm_idx) {
		boolean literal = true;
		if (atr_hash.Get_by_mid(key_bry, 0, key_bry.length) != null) {		// title, alt
			int val_bgn = itm_ary[itm_idx + Mwh_atr_mgr.Idx_val_bgn];
			int val_end = itm_ary[itm_idx + Mwh_atr_mgr.Idx_val_end];
			if (Bry_find_.Find_fwd(src, Bry__url_frag, val_bgn, val_end) == Bry_find_.Not_found) {	// do not convert if urls are present
				literal = false;
				byte[] val_bry = val_bry_manual == null ? Bry_.Mid(src, val_bgn, val_end) : val_bry_manual;
				if (atr_converter == null) atr_converter = new Vnt_convert_lang(convert_mgr, vnt_regy);// NOTE: late instantiation, or else StackOverflow error
				val_bry = atr_converter.Parse_bry(vnt_itm, val_bry);
				bfr.Add_byte_space();
				bfr.Add(key_bry);
				bfr.Add_byte(Byte_ascii.Eq);
				byte quote_byte = Mwh_atr_itm_.Calc_qte_byte(itm_ary, itm_idx);
				bfr.Add_byte(quote_byte);
				bfr.Add(val_bry);
				bfr.Add_byte(quote_byte);
			}
		}
		if (literal) {
			int atr_bgn = itm_ary[itm_idx + Mwh_atr_mgr.Idx_atr_bgn];
			int atr_end = itm_ary[itm_idx + Mwh_atr_mgr.Idx_atr_end];
			bfr.Add_mid(src, atr_bgn, atr_end);
		}
	}
	public void On_txt_end		(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {
		switch (nde_tid) {
			case Xop_xnde_tag_.Tid__code:
			case Xop_xnde_tag_.Tid__script:
			case Xop_xnde_tag_.Tid__pre:
				bfr.Add_mid(src, itm_bgn, itm_end);
				break;
			default:					
				bfr.Add(convert_mgr.Convert_text(convert_vnt_idx, src, itm_bgn, itm_end));
				break;
		}
	}
	public void On_nde_head_bgn(Mwh_doc_parser mgr, byte[] src, int nde_tid, int key_bgn, int key_end) {
		bfr.Add_byte(Byte_ascii.Angle_bgn).Add_mid(src, key_bgn, key_end);	// EX: "<span"
	}
	public void On_nde_head_end(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end, boolean inline) {
		bfr.Add(inline ? Xoh_consts.__inline : Xoh_consts.__end);			// add "/>" or ">"
	}
	public void On_nde_tail_end	(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {bfr.Add_mid(src, itm_bgn, itm_end);}
	public void On_comment_end  (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {bfr.Add_mid(src, itm_bgn, itm_end);}
	public void On_entity_end  (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end)	{bfr.Add_mid(src, itm_bgn, itm_end);}
	private static final    byte[] Bry__url_frag = Bry_.new_a7("://");	// REF.MW: if ( !strpos( $attr, '://' ) ) {
}
