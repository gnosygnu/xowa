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
package gplx.xowa.xtns.wbases.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.langs.jsons.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public interface Wdata_doc_parser {
	byte[] Parse_qid(Json_doc doc);
	Ordered_hash Parse_sitelinks(byte[] qid, Json_doc doc);
	Ordered_hash Parse_langvals(byte[] qid, Json_doc doc, boolean label_or_description);
	Ordered_hash Parse_aliases(byte[] qid, Json_doc doc);
	Ordered_hash Parse_claims(byte[] qid, Json_doc doc);
	Wbase_claim_base Parse_claims_data(byte[] qid, int pid, byte snak_tid, Json_nde nde);
	Wbase_claim_grp_list Parse_qualifiers(byte[] qid, Json_nde nde);
	int[] Parse_pid_order(byte[] qid, Json_ary ary);
	Wbase_references_grp[] Parse_references(byte[] qid, Json_ary owner);
}
