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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
class Mwh_doc_parser_fxt {
	private final    Bry_bfr expd_bfr = Bry_bfr_.New(), actl_bfr = Bry_bfr_.New();
	private final    Mwh_doc_parser parser = new Mwh_doc_parser();
	private final    Mwh_doc_wkr__itm_bldr wkr = new Mwh_doc_wkr__itm_bldr();
	public Mwh_doc_itm Make_txt		(String raw) {return new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__txt		, -1, Bry_.new_u8(raw));}
	public Mwh_doc_itm Make_txt		(String raw, int nde_tid) {return new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__txt		, nde_tid, Bry_.new_u8(raw));}
	public Mwh_doc_itm Make_comment (String raw) {return new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__comment	, -1, Bry_.new_u8(raw));}
	public Mwh_doc_itm Make_entity  (String raw) {return new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__entity	, -1, Bry_.new_u8(raw));}
	public Mwh_doc_itm Make_nde_head(String raw) {return new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__nde_head	, -1, Bry_.new_u8(raw));}
	public Mwh_doc_itm Make_nde_tail(String raw) {return new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__nde_tail	, -1, Bry_.new_u8(raw));}
	public void Test_parse(String raw, Mwh_doc_itm... expd) {
		Mwh_doc_itm[] actl = Exec_parse(raw);
		Test_print(expd, actl);
	}
	public Mwh_doc_itm[] Exec_parse(String raw) {
		byte[] bry = Bry_.new_u8(raw);
		parser.Parse(wkr, bry, 0, bry.length);
		return wkr.To_atr_ary();
	}
	public void Test_print(Mwh_doc_itm[] expd_ary, Mwh_doc_itm[] actl_ary) {
		int expd_len = expd_ary.length;
		int actl_len = actl_ary.length;
		int len = expd_len > actl_len ? expd_len : actl_len;
		for (int i = 0; i < len; ++i) {
			To_bfr(expd_bfr, i < expd_len ? expd_ary[i] : null, actl_bfr, i < actl_len ? actl_ary[i] : null);
		}
		Tfds.Eq_str_lines(expd_bfr.To_str_and_clear(), actl_bfr.To_str_and_clear());
	}
	private void To_bfr(Bry_bfr expd_bfr, Mwh_doc_itm expd_itm, Bry_bfr actl_bfr, Mwh_doc_itm actl_itm) {
		To_bfr__main(expd_bfr, expd_itm); To_bfr__main(actl_bfr, actl_itm);
		if (expd_itm != null && expd_itm.Nde_tid() != -1) {
			To_bfr__nde_tid(expd_bfr, expd_itm); To_bfr__nde_tid(actl_bfr, actl_itm);
		}
	}
	private void To_bfr__main(Bry_bfr bfr, Mwh_doc_itm itm) {
		if (itm == null) return;
		bfr.Add_str_a7("itm_tid:").Add_int_variable(itm.Itm_tid()).Add_byte_nl();
		bfr.Add_str_a7("txt:").Add(itm.Itm_bry()).Add_byte_nl();
	}
	private void To_bfr__nde_tid(Bry_bfr bfr, Mwh_doc_itm itm) {
		if (itm == null) return;
		bfr.Add_str_a7("nde_tid:").Add_int_variable(itm.Nde_tid()).Add_byte_nl();
	}
}
class Mwh_doc_wkr__itm_bldr implements Mwh_doc_wkr {
	private final    List_adp list = List_adp_.New();		
	public Hash_adp_bry Nde_regy() {return nde_regy;} private final    Hash_adp_bry nde_regy = Mwh_doc_wkr_.Nde_regy__mw();
	public void On_atr_each		(Mwh_atr_parser mgr, byte[] src, int nde_tid, boolean valid, boolean repeated, boolean key_exists, byte[] key_bry, byte[] val_bry_manual, int[] itm_ary, int itm_idx) {}
	public void On_txt_end		(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {list.Add(new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__txt		, nde_tid, Bry_.Mid(src, itm_bgn, itm_end)));}
	public void On_nde_head_bgn (Mwh_doc_parser mgr, byte[] src, int nde_tid, int key_bgn, int key_end) {}
	public void On_nde_head_end	(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end, boolean inline) {list.Add(new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__nde_head	, nde_tid, Bry_.Mid(src, itm_bgn, itm_end)));}
	public void On_nde_tail_end	(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {list.Add(new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__nde_tail	, nde_tid, Bry_.Mid(src, itm_bgn, itm_end)));}
	public void On_comment_end  (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {list.Add(new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__comment	, nde_tid, Bry_.Mid(src, itm_bgn, itm_end)));}
	public void On_entity_end	(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end)	{list.Add(new Mwh_doc_itm(Mwh_doc_itm.Itm_tid__entity	, nde_tid, Bry_.Mid(src, itm_bgn, itm_end)));}

	public Mwh_doc_itm[] To_atr_ary() {return (Mwh_doc_itm[])list.To_ary_and_clear(Mwh_doc_itm.class);}
}
