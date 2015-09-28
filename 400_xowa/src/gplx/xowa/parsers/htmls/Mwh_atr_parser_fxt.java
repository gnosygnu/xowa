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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
class Mwh_atr_parser_fxt {
	private final Bry_bfr expd_bfr = Bry_bfr.new_(), actl_bfr = Bry_bfr.new_();
	private final Mwh_atr_parser parser = new Mwh_atr_parser();
	private final Mwh_doc_wkr__atr_bldr wkr = new Mwh_doc_wkr__atr_bldr();
	public Mwh_atr_itm Make_pair(String key, String val)	{return new Mwh_atr_itm(Bry_.Empty, Bool_.Y, Bool_.N, Bool_.Y,  -1,  -1, -1, -1, Bry_.new_u8(key)	, -1, -1, Bry_.new_u8(val)	, -1, -1);}
	public Mwh_atr_itm Make_name(String key)				{return new Mwh_atr_itm(Bry_.Empty, Bool_.Y, Bool_.N, Bool_.N,  -1,  -1, -1, -1, Bry_.new_u8(key)	, -1, -1, null				, -1, -1);}
	public Mwh_atr_itm Make_fail(int bgn, int end)			{return new Mwh_atr_itm(Bry_.Empty, Bool_.N, Bool_.N, Bool_.N, bgn, end, -1, -1, null				, -1, -1, null				, -1, -1);}
	public void Test_val_as_int(String raw, int expd) {
		byte[] src = Bry_.new_u8(raw);
		Mwh_atr_itm itm = new Mwh_atr_itm(src, true, false, false, 0, src.length, -1, -1, null, 0, src.length, src, -1, -1);
		Tfds.Eq_int(expd, itm.Val_as_int_or(-1));
	}
	public void Test_parse(String raw, Mwh_atr_itm... expd) {
		Mwh_atr_itm[] actl = Exec_parse(raw);
		Test_print(expd, actl);
	}
	private Mwh_atr_itm[] Exec_parse(String raw) {
		byte[] bry = Bry_.new_u8(raw);
		parser.Parse(wkr, -1, -1, bry, 0, bry.length);
		return wkr.To_atr_ary();
	}
	public void Test_print(Mwh_atr_itm[] expd_ary, Mwh_atr_itm[] actl_ary) {
		int expd_len = expd_ary.length;
		int actl_len = actl_ary.length;
		int len = expd_len > actl_len ? expd_len : actl_len;
		for (int i = 0; i < len; ++i) {
			To_bfr(expd_bfr, i < expd_len ? expd_ary[i] : null, actl_bfr, i < actl_len ? actl_ary[i] : null);
		}
		Tfds.Eq_str_lines(expd_bfr.Xto_str_and_clear(), actl_bfr.Xto_str_and_clear());
	}
	private void To_bfr(Bry_bfr expd_bfr, Mwh_atr_itm expd_itm, Bry_bfr actl_bfr, Mwh_atr_itm actl_itm) {
		To_bfr__main(expd_bfr, expd_itm);
		To_bfr__main(actl_bfr, actl_itm);
		To_bfr__head(expd_bfr, expd_itm);
		To_bfr__head(actl_bfr, actl_itm);
		if (expd_itm.Atr_bgn() != -1) {
			To_bfr__atr_rng(expd_bfr, expd_itm);
			To_bfr__atr_rng(actl_bfr, actl_itm);
		}
	}
	private void To_bfr__head(Bry_bfr bfr, Mwh_atr_itm itm) {
		if (itm == null) return;
		bfr.Add_str_a7("head:").Add_yn(itm.Valid()).Add_byte_semic().Add_yn(itm.Repeated()).Add_byte_semic().Add_yn(itm.Key_exists()).Add_byte_nl();			
	}
	private void To_bfr__main(Bry_bfr bfr, Mwh_atr_itm itm) {
		if (itm == null) return;
		if (itm.Valid()) {
			bfr.Add_str_a7("key:").Add(itm.Key_bry()).Add_byte_nl();
			bfr.Add_str_a7("val:").Add(itm.Val_as_bry()).Add_byte_nl();
		}
//			else
//				To_bfr__atr_rng(bfr, itm);
	}
	private void To_bfr__atr_rng(Bry_bfr bfr, Mwh_atr_itm itm) {
		if (itm == null) return;
		bfr.Add_str_a7("rng:").Add_int_variable(itm.Atr_bgn()).Add_byte_semic().Add_int_variable(itm.Atr_end()).Add_byte_nl();
	}
}
class Mwh_doc_wkr__atr_bldr implements Mwh_doc_wkr {
	private final List_adp list = List_adp_.new_();
	public Hash_adp_bry Nde_regy() {return null;}
	public void On_atr_each(Mwh_atr_parser mgr, byte[] src, int nde_tid, boolean valid, boolean repeated, boolean key_exists, byte[] key_bry, byte[] val_bry_manual, int[] data_ary, int itm_idx) {
		int atr_bgn = data_ary[itm_idx + Mwh_atr_mgr.Idx_atr_bgn];
		int atr_end = data_ary[itm_idx + Mwh_atr_mgr.Idx_atr_end];
		int key_bgn = data_ary[itm_idx + Mwh_atr_mgr.Idx_key_bgn];
		int key_end = data_ary[itm_idx + Mwh_atr_mgr.Idx_key_end];
		int val_bgn = data_ary[itm_idx + Mwh_atr_mgr.Idx_val_bgn];
		int val_end = data_ary[itm_idx + Mwh_atr_mgr.Idx_val_end];
		int eql_pos = data_ary[itm_idx + Mwh_atr_mgr.Idx_eql_pos];
		int qte_tid = data_ary[itm_idx + Mwh_atr_mgr.Idx_atr_utl];
		qte_tid = Mwh_atr_itm.Calc_qte_tid(qte_tid);
		Mwh_atr_itm atr = new Mwh_atr_itm(src, valid, repeated, key_exists, atr_bgn, atr_end, key_bgn, key_end, key_bry, val_bgn, val_end, val_bry_manual, eql_pos, qte_tid);
		list.Add(atr);
	}
	public void On_txt_end(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public void On_nde_head_bgn(Mwh_doc_parser mgr, byte[] src, int nde_tid, int key_bgn, int key_end) {}
	public void On_nde_head_end(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end, boolean inline) {}
	public void On_nde_tail_end(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public void On_comment_end (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public Mwh_atr_itm[] To_atr_ary() {return (Mwh_atr_itm[])list.To_ary_and_clear(Mwh_atr_itm.class);}
}
