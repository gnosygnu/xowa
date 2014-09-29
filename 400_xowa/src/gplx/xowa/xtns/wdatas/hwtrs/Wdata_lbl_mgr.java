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
class Wdata_lbl_mgr {
	public Wdata_lbl_list Pid_regy() {return pid_regy;} private Wdata_lbl_list pid_regy = new Wdata_lbl_list(Bool_.Y);
	public Wdata_lbl_list Qid_regy() {return qid_regy;} private Wdata_lbl_list qid_regy = new Wdata_lbl_list(Bool_.N);
	public void Wkr_(Wdata_lbl_wkr v) {this.wkr = v;} private Wdata_lbl_wkr wkr;
	public void Gather_labels(Wdata_doc wdoc) {
		OrderedHash claim_list = wdoc.Claim_list();
		int len = claim_list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_claim_grp grp = (Wdata_claim_grp)claim_list.FetchAt(i);
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wdata_claim_itm_base itm = (Wdata_claim_itm_base)grp.Get_at(j);
				pid_regy.Queue_if_missing(itm.Pid());
				switch (itm.Val_tid()) {
					case Wdata_dict_val_tid.Tid_entity:
						Wdata_claim_itm_entity entity = (Wdata_claim_itm_entity)itm;
						qid_regy.Queue_if_missing(entity.Entity_id());
						break;
				}
			}
		}
		wkr.Resolve(pid_regy, qid_regy);
	}
}
interface Wdata_lbl_wkr {
	void Resolve(Wdata_lbl_list pid_regy, Wdata_lbl_list qid_regy);
}
