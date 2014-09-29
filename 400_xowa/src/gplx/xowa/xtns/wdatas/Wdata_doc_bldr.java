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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.json.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*;
public class Wdata_doc_bldr {
	private OrderedHash descr_list, label_list, slink_list, alias_list, claim_list;
	public Wdata_doc_bldr() {this.Init();}
	public Wdata_doc_bldr Qid_(String v) {this.qid = Bry_.new_ascii_(v); return this;} private byte[] qid;
	public Wdata_doc_bldr Add_claims(Wdata_claim_itm_core... ary) {
		if (ary.length == 0) throw Err_.new_("claims must be greater than 0");
		Wdata_claim_itm_core itm = ary[0];
		Wdata_claim_grp grp = new Wdata_claim_grp(Int_obj_ref.new_(itm.Pid()), ary);
		claim_list.Add(grp.Id_ref(), grp);
		return this;
	}		
	public Wdata_doc_bldr Add_description(String lang, String text) {byte[] key = Bry_.new_utf8_(lang); descr_list.Add(key, new Wdata_langtext_itm(key, Bry_.new_utf8_(text))); return this;}
	public Wdata_doc_bldr Add_label(String lang, String text)		{byte[] key = Bry_.new_utf8_(lang); label_list.Add(key, new Wdata_langtext_itm(key, Bry_.new_utf8_(text))); return this;}
	public Wdata_doc_bldr Add_sitelink(String site, String ttl)		{byte[] key = Bry_.new_utf8_(site); slink_list.Add(key, new Wdata_sitelink_itm(key, Bry_.new_utf8_(ttl), Bry_.Ary_empty)); return this;}
	public Wdata_doc_bldr Add_alias(String lang, String... ary){byte[] key = Bry_.new_utf8_(lang); alias_list.Add(key, new Wdata_alias_itm	  (key, Bry_.Ary(ary))); return this;}
	public Wdata_doc Xto_wdoc() {
		Wdata_doc rv = new Wdata_doc(qid, slink_list, label_list, descr_list, alias_list, claim_list);
		this.Init();
		return rv;
	}
	private void Init() {
		descr_list = OrderedHash_.new_bry_(); label_list = OrderedHash_.new_bry_(); slink_list = OrderedHash_.new_bry_(); alias_list = OrderedHash_.new_bry_(); claim_list = OrderedHash_.new_();
	}
}
