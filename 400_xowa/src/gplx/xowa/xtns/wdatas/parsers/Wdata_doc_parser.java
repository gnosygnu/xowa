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
package gplx.xowa.xtns.wdatas.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.core.json.*; import gplx.xowa.xtns.wdatas.core.*;
public interface Wdata_doc_parser {
	byte[] Parse_qid(Json_doc doc);
	Ordered_hash Parse_sitelinks(byte[] qid, Json_doc doc);
	Ordered_hash Parse_langvals(byte[] qid, Json_doc doc, boolean label_or_description);
	Ordered_hash Parse_aliases(byte[] qid, Json_doc doc);
	Ordered_hash Parse_claims(byte[] qid, Json_doc doc);
	Wdata_claim_itm_base Parse_claims_data(byte[] qid, int pid, byte snak_tid, Json_nde nde);
	Wdata_claim_grp_list Parse_qualifiers(byte[] qid, Json_nde nde);
	int[] Parse_pid_order(byte[] qid, Json_ary ary);
	Wdata_references_grp[] Parse_references(byte[] qid, Json_ary owner);
}
