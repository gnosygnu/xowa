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
package gplx.xowa.addons.wikis.searchs.searchers.crts.visitors; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Srch_crt_visitor__print implements Srch_crt_visitor {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public byte[] Print(Srch_crt_itm root) {
		Visit(root);
		return bfr.To_bry_and_clear();
	}
	public void Visit(Srch_crt_itm node) {
		switch (node.Tid) {
			case Srch_crt_itm.Tid__word:			bfr.Add(node.Raw); break;
			case Srch_crt_itm.Tid__word_quote:	bfr.Add_byte_quote().Add(node.Raw).Add_byte_quote(); break;
			case Srch_crt_itm.Tid__and:
			case Srch_crt_itm.Tid__or:
				bfr.Add_byte(Byte_ascii.Paren_bgn);
				Srch_crt_itm[] subs = node.Subs;					
				int subs_len = subs.length;
				for (int i = 0; i < subs_len; ++i) {
					if (i != 0)
						bfr.Add_str_a7(node.Tid == Srch_crt_itm.Tid__and ? " AND " : " OR ");
					subs[i].Accept_visitor(this);
				}
				bfr.Add_byte(Byte_ascii.Paren_end);
				break;
			case Srch_crt_itm.Tid__not:
				bfr.Add_str_a7("NOT ");
				node.Subs[0].Accept_visitor(this);
				break;
			case Srch_crt_itm.Tid__invalid:		break;			// should not happen
			default:								throw Err_.new_unhandled_default(node.Tid);
		}
	}
}
