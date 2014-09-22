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
public class Wdata_doc {
	public Wdata_doc(byte[] qid, Wdata_wiki_mgr mgr, Json_doc jdoc) {this.qid = qid; this.mgr = mgr; this.jdoc = jdoc;} private Wdata_wiki_mgr mgr;
	public Json_doc Jdoc() {return jdoc;} private Json_doc jdoc;
	public byte[] Qid() {return qid;} private byte[] qid;
	public OrderedHash Sitelink_list()  {if (sitelink_list == null)  sitelink_list  = mgr.Wdoc_parser(jdoc).Parse_sitelinks(qid, jdoc); return sitelink_list;} private OrderedHash sitelink_list;
	public OrderedHash Label_list() {if (label_list == null) label_list = mgr.Wdoc_parser(jdoc).Parse_langvals(qid, jdoc, Bool_.Y); return label_list;} private OrderedHash label_list;
	public OrderedHash Description_list() {if (description_list == null) description_list = mgr.Wdoc_parser(jdoc).Parse_langvals(qid, jdoc, Bool_.N); return description_list;} private OrderedHash description_list;
	public OrderedHash Alias_list() {if (alias_list == null) alias_list = mgr.Wdoc_parser(jdoc).Parse_aliases(qid, jdoc); return alias_list;} private OrderedHash alias_list;
	public OrderedHash Claim_list() {if (claim_list == null) claim_list = mgr.Wdoc_parser(jdoc).Parse_claims(jdoc); return claim_list;} private OrderedHash claim_list;
	public Wdata_claim_grp Claim_list_get(int pid) {return (Wdata_claim_grp)this.Claim_list().Fetch(mgr.Tmp_prop_ref().Val_(pid));}	
	public byte[] Label_list_get(byte[] lang_key) {
		Object rv_obj = this.Label_list().Fetch(lang_key); if (rv_obj == null) return null;
		Wdata_langtext_itm rv = (Wdata_langtext_itm)rv_obj;
		return rv.Text();
	}
}
