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
import gplx.json.*;
public class Wdata_doc {
	public Wdata_doc(byte[] qid, Wdata_wiki_mgr mgr, Json_doc doc) {this.qid = qid; this.mgr = mgr; this.doc = doc;} Wdata_wiki_mgr mgr;
	public Json_doc Doc() {return doc;} Json_doc doc;
	public byte[] Qid() {return qid;} private byte[] qid;
	public OrderedHash Link_list()  {if (link_list == null)  link_list  = mgr.Page_doc_parser().Bld_hash(doc, Wdata_doc_consts.Key_atr_links_bry); return link_list;} private OrderedHash link_list;
	public OrderedHash Label_list() {if (label_list == null) label_list = mgr.Page_doc_parser().Bld_hash(doc, Wdata_doc_consts.Key_atr_label_bry); return label_list;} private OrderedHash label_list;
	public OrderedHash Alias_list() {if (alias_list == null) alias_list = mgr.Page_doc_parser().Bld_hash(doc, Wdata_doc_consts.Key_atr_aliases_bry); return alias_list;} private OrderedHash alias_list;
	public OrderedHash Description_list() {if (description_list == null) description_list = mgr.Page_doc_parser().Bld_hash(doc, Wdata_doc_consts.Key_atr_description_bry); return description_list;} private OrderedHash description_list;
	public OrderedHash Claim_list() {if (claim_list == null) claim_list = mgr.Page_doc_parser().Bld_props(doc); return claim_list;} private OrderedHash claim_list;
	public byte[] Label_list_get(byte[] lang_key) {
		Object rv = this.Label_list().Fetch(lang_key); if (rv == null) return null;
		Json_itm_kv kv = (Json_itm_kv)rv;
		return kv.Val().Data_bry();
	}	
	public Wdata_prop_grp Claim_list_get(int pid) {return (Wdata_prop_grp)this.Claim_list().Fetch(mgr.Tmp_prop_ref().Val_(pid));}	
}
