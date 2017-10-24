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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.primitives.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public class Wdata_lbl_mgr {
	private Hash_adp_bry ttl_hash = Hash_adp_bry.ci_a7();
	private Hash_adp qid_hash = Hash_adp_.New(), pid_hash = Hash_adp_.New(); private Int_obj_ref int_hash_key = Int_obj_ref.New_neg1();
	private Wdata_visitor__lbl_gatherer lbl_gatherer;
	public Wdata_lbl_mgr() {
		lbl_gatherer = new Wdata_visitor__lbl_gatherer(this);
	}
	public void Clear() {ttl_hash.Clear(); qid_hash.Clear(); pid_hash.Clear(); queue.Clear();}
	public List_adp Queue() {return queue;} private List_adp queue = List_adp_.New();
	@gplx.Internal protected void Wkr_(Wdata_lbl_wkr v) {this.wkr = v;} private Wdata_lbl_wkr wkr;
	public Wdata_lbl_itm Get_itm__ttl(byte[] ttl) {
		Wdata_lbl_itm rv = (Wdata_lbl_itm)ttl_hash.Get_by(ttl);
		if (rv == null) Gfo_usr_dlg_.Instance.Warn_many("", "", "wdata.hwtr:unknown entity; ttl=~{0}", String_.new_u8(ttl));	// NOTE: should not happen
		return rv;
	}
	public byte[] Get_text__ttl(byte[] ttl, byte[] or) {
		Wdata_lbl_itm rv_itm = Get_itm__ttl(ttl);
		return rv_itm == null ? or : rv_itm.Text();
	}
	public byte[] Get_text__qid(int id) {return Get_text(Bool_.N, id);}
	public byte[] Get_text__pid(int id) {return Get_text(Bool_.Y, id);}
	private byte[] Get_text(boolean is_pid, int id) {
		Hash_adp hash = is_pid ? pid_hash : qid_hash;
		Wdata_lbl_itm rv_itm = (Wdata_lbl_itm)hash.Get_by(int_hash_key.Val_(id));
		if (rv_itm != null) return rv_itm.Text();	// found; return lbl
		Gfo_usr_dlg_.Instance.Warn_many("", "", "wdata.hwtr:unknown entity; is_pid=~{0} id=~{1}", Yn.To_str(is_pid), id);	// NOTE: should not happen
		return Wdata_lbl_itm.Make_ttl(is_pid, id);	// missing; return ttl; EX: "Property:P1", "Q1";
	}
	public void Queue_if_missing__ttl(byte[] ttl) {Queue_if_missing__ttl(ttl, Bool_.N);}
	public void Queue_if_missing__ttl(byte[] ttl, boolean get_en) {
		if (ttl == null) {Gfo_usr_dlg_.Instance.Warn_many("", "", "wdata.hwtr:unknown href; href=~{0}", String_.new_u8(ttl)); return;}
		boolean has = ttl_hash.Has(ttl);
		if (!has) Queue_add(qid_hash, Bool_.N, Qid_int(ttl), get_en);
	}
	public void Queue_if_missing__qid(int id) {Queue_if_missing(Bool_.N, id);}
	public void Queue_if_missing__pid(int id) {Queue_if_missing(Bool_.Y, id);}
	private void Queue_if_missing(boolean is_pid, int id) {
		Hash_adp hash = is_pid ? pid_hash : qid_hash;
		boolean has = hash.Has(int_hash_key.Val_(id));
		if (!has) Queue_add(hash, is_pid, id, Bool_.N);
	}
	private void Queue_add(Hash_adp hash, boolean is_pid, int id, boolean get_en) {
		Wdata_lbl_itm itm = new Wdata_lbl_itm(is_pid, id, get_en);
		hash.Add(Int_obj_ref.New(id), itm);
		ttl_hash.Add(itm.Ttl(), itm);
		queue.Add(itm);
	}
	public void Resolve(Ordered_hash found) {
		int len = queue.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_lbl_itm pending_itm = (Wdata_lbl_itm)queue.Get_at(i);
			Wdata_langtext_itm found_itm = (Wdata_langtext_itm)found.Get_by(pending_itm.Ttl());
			if (found_itm != null)
				pending_itm.Load_vals(found_itm.Lang(), found_itm.Text());
		}
		queue.Clear();
	}
	public void Gather_labels(Wdata_doc wdoc, Wdata_lang_sorter sorter) {
		Ordered_hash claim_list = wdoc.Claim_list();
		int len = claim_list.Count();
		for (int i = 0; i < len; ++i) {
			Wbase_claim_grp grp = (Wbase_claim_grp)claim_list.Get_at(i);
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wbase_claim_base itm = (Wbase_claim_base)grp.Get_at(j);
				this.Queue_if_missing__pid(itm.Pid());
				itm.Welcome(lbl_gatherer);
				Wbase_claim_grp_list qual_list = itm.Qualifiers();
				if (qual_list != null) {
					int qual_list_len = qual_list.Len();
					for (int k = 0; k < qual_list_len; ++k) {
						Wbase_claim_grp qual_grp = qual_list.Get_at(k);
						int qual_grp_len = qual_grp.Len();
						for (int m = 0; m < qual_grp_len; ++m) {
							Wbase_claim_base qual = qual_grp.Get_at(m);
							this.Queue_if_missing__pid(qual.Pid());
							qual.Welcome(lbl_gatherer);
						}
					}
				}
				Wbase_references_grp[] ref_grp_ary = itm.References();
				if (ref_grp_ary != null) {
					int ref_grp_ary_len = ref_grp_ary.length;
					for (int k = 0; k < ref_grp_ary_len; ++k) {
						Wbase_references_grp ref_grp = ref_grp_ary[k];
						Wbase_claim_grp_list ref_list = ref_grp.References();
						int ref_list_len = ref_list.Len();
						for (int m = 0; m < ref_list_len; ++m) {
							Wbase_claim_grp claim_grp = ref_list.Get_at(m);
							int claim_grp_len = claim_grp.Len();
							for (int n = 0; n < claim_grp_len; ++n) {
								Wbase_claim_base claim = claim_grp.Get_at(n);
								this.Queue_if_missing__pid(claim.Pid());
								claim.Welcome(lbl_gatherer);
							}
						}
					}
				}
			}
		}
		Ordered_hash slink_list = wdoc.Slink_list();
		len = slink_list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)slink_list.Get_at(i);
			byte[][] badges = itm.Badges();
			int badges_len = badges.length;
			for (int j = 0; j < badges_len; ++j) {
				byte[] badge = badges[j];
				this.Queue_if_missing__ttl(badge, Bool_.Y);	// badges has qid; EX: ["Q1", "Q2"]
			}
		}
		wkr.Resolve(this, sorter);
	}
	public static int Qid_int(byte[] qid) {
		byte qid_0 = qid[0];
		if (qid_0 != Byte_ascii.Ltr_Q && qid_0 != Byte_ascii.Ltr_q) return -1;
		return Bry_.To_int_or(qid, 1, qid.length, -1);
	}
}
