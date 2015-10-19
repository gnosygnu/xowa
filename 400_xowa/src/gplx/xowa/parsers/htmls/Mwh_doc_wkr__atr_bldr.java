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
public class Mwh_doc_wkr__atr_bldr implements Mwh_doc_wkr {
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
		qte_tid = Mwh_atr_itm_.Calc_qte_tid(qte_tid);
		if (!key_exists) val_bry_manual = key_bry;
		Mwh_atr_itm atr = new Mwh_atr_itm(src, valid, repeated, key_exists, atr_bgn, atr_end, key_bgn, key_end, key_bry, val_bgn, val_end, val_bry_manual, eql_pos, qte_tid);
		list.Add(atr);
	}
	public void On_txt_end(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public void On_nde_head_bgn(Mwh_doc_parser mgr, byte[] src, int nde_tid, int key_bgn, int key_end) {}
	public void On_nde_head_end(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end, boolean inline) {}
	public void On_nde_tail_end(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public void On_comment_end (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public void On_entity_end  (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public Mwh_atr_itm[] To_atr_ary() {return (Mwh_atr_itm[])list.To_ary_and_clear(Mwh_atr_itm.class);}
}