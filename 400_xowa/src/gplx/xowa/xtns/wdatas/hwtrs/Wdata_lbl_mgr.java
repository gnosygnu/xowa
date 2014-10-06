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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.xtns.wdatas.core.*;
public class Wdata_lbl_mgr {
	private Hash_adp_bry ttl_hash = Hash_adp_bry.ci_ascii_();
	private HashAdp qid_hash = HashAdp_.new_(), pid_hash = HashAdp_.new_(); private Int_obj_ref int_hash_key = Int_obj_ref.neg1_();
	private Wdata_visitor__lbl_gatherer lbl_gatherer;
	public Wdata_lbl_mgr() {
		lbl_gatherer = new Wdata_visitor__lbl_gatherer(this);
	}
	public void Clear() {ttl_hash.Clear(); qid_hash.Clear(); pid_hash.Clear(); queue.Clear();}
	public ListAdp Queue() {return queue;} private ListAdp queue = ListAdp_.new_();
	@gplx.Internal protected void Wkr_(Wdata_lbl_wkr v) {this.wkr = v;} private Wdata_lbl_wkr wkr;
	public Wdata_lbl_itm Get_itm__ttl(byte[] ttl) {
		Wdata_lbl_itm rv = (Wdata_lbl_itm)ttl_hash.Fetch(ttl);
		if (rv == null) Gfo_usr_dlg_._.Warn_many("", "", "wdata.hwtr:unknown entity; ttl=~{0}", String_.new_utf8_(ttl));	// NOTE: should not happen
		return rv;
	}
	public byte[] Get_text__ttl(byte[] ttl, byte[] or) {
		Wdata_lbl_itm rv_itm = Get_itm__ttl(ttl);
		return rv_itm == null ? or : rv_itm.Text();
	}
	public byte[] Get_text__qid(int id) {return Get_text(Bool_.N, id);}
	public byte[] Get_text__pid(int id) {return Get_text(Bool_.Y, id);}
	private byte[] Get_text(boolean is_pid, int id) {
		HashAdp hash = is_pid ? pid_hash : qid_hash;
		Wdata_lbl_itm rv_itm = (Wdata_lbl_itm)hash.Fetch(int_hash_key.Val_(id));
		if (rv_itm != null) return rv_itm.Text();	// found; return lbl
		Gfo_usr_dlg_._.Warn_many("", "", "wdata.hwtr:unknown entity; is_pid=~{0} id=~{1}", Yn.Xto_str(is_pid), id);	// NOTE: should not happen
		return Wdata_lbl_itm.Make_ttl(is_pid, id);	// missing; return ttl; EX: "Property:P1", "Q1";
	}
	public void Queue_if_missing__ttl(byte[] ttl) {Queue_if_missing__ttl(ttl, Bool_.N);}
	public void Queue_if_missing__ttl(byte[] ttl, boolean get_en) {
		if (ttl == null) {Gfo_usr_dlg_._.Warn_many("", "", "wdata.hwtr:unknown href; href=~{0}", String_.new_utf8_(ttl)); return;}
		boolean has = ttl_hash.Has(ttl);
		if (!has) Queue_add(qid_hash, Bool_.N, Qid_int(ttl), get_en);
	}
	public void Queue_if_missing__qid(int id) {Queue_if_missing(Bool_.N, id);}
	public void Queue_if_missing__pid(int id) {Queue_if_missing(Bool_.Y, id);}
	private void Queue_if_missing(boolean is_pid, int id) {
		HashAdp hash = is_pid ? pid_hash : qid_hash;
		boolean has = hash.Has(int_hash_key.Val_(id));
		if (!has) Queue_add(hash, is_pid, id, Bool_.N);
	}
	private void Queue_add(HashAdp hash, boolean is_pid, int id, boolean get_en) {
		Wdata_lbl_itm itm = new Wdata_lbl_itm(is_pid, id, get_en);
		hash.Add(Int_obj_ref.new_(id), itm);
		ttl_hash.Add(itm.Ttl(), itm);
		queue.Add(itm);
	}
	public void Resolve(OrderedHash found) {
		int len = queue.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_lbl_itm pending_itm = (Wdata_lbl_itm)queue.FetchAt(i);
			Wdata_langtext_itm found_itm = (Wdata_langtext_itm)found.Fetch(pending_itm.Ttl());
			if (found_itm != null)
				pending_itm.Load_vals(found_itm.Lang(), found_itm.Text());
		}
		queue.Clear();
	}
	public void Gather_labels(Wdata_doc wdoc, Wdata_lang_sorter sorter) {
		OrderedHash claim_list = wdoc.Claim_list();
		int len = claim_list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_claim_grp grp = (Wdata_claim_grp)claim_list.FetchAt(i);
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wdata_claim_itm_core itm = (Wdata_claim_itm_core)grp.Get_at(j);
				this.Queue_if_missing__pid(itm.Pid());
				itm.Welcome(lbl_gatherer);
				Wdata_claim_grp_list qual_list = itm.Qualifiers();
				if (qual_list != null) {
					int qual_list_len = qual_list.Len();
					for (int k = 0; k < qual_list_len; ++k) {
						Wdata_claim_grp qual_grp = qual_list.Get_at(k);
						int qual_grp_len = qual_grp.Len();
						for (int m = 0; m < qual_grp_len; ++m) {
							Wdata_claim_itm_base qual = qual_grp.Get_at(m);
							this.Queue_if_missing__pid(qual.Pid());
							qual.Welcome(lbl_gatherer);
						}
					}
				}
				Wdata_references_grp[] ref_grp_ary = itm.References();
				if (ref_grp_ary != null) {
					int ref_grp_ary_len = ref_grp_ary.length;
					for (int k = 0; k < ref_grp_ary_len; ++k) {
						Wdata_references_grp ref_grp = ref_grp_ary[k];
						Wdata_claim_grp_list ref_list = ref_grp.References();
						int ref_list_len = ref_list.Len();
						for (int m = 0; m < ref_list_len; ++m) {
							Wdata_claim_grp claim_grp = ref_list.Get_at(m);
							int claim_grp_len = claim_grp.Len();
							for (int n = 0; n < claim_grp_len; ++n) {
								Wdata_claim_itm_core claim = claim_grp.Get_at(n);
								this.Queue_if_missing__pid(claim.Pid());
								claim.Welcome(lbl_gatherer);
							}
						}
					}
				}
			}
		}
		OrderedHash slink_list = wdoc.Slink_list();
		len = slink_list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)slink_list.FetchAt(i);
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
		return Bry_.Xto_int_or(qid, 1, qid.length, -1);
	}
}
