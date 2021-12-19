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
package gplx.xowa.addons.wikis.searchs.searchers.crts.visitors;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.errs.ErrUtl;
import gplx.xowa.addons.wikis.searchs.searchers.crts.Srch_crt_itm;
import gplx.xowa.addons.wikis.searchs.searchers.crts.Srch_crt_visitor;
public class Srch_crt_visitor__print implements Srch_crt_visitor {
	private final BryWtr bfr = BryWtr.New();
	public byte[] Print(Srch_crt_itm root) {
		Visit(root);
		return bfr.ToBryAndClear();
	}
	public void Visit(Srch_crt_itm node) {
		switch (node.Tid) {
			case Srch_crt_itm.Tid__word:			bfr.Add(node.Raw); break;
			case Srch_crt_itm.Tid__word_quote:	bfr.AddByteQuote().Add(node.Raw).AddByteQuote(); break;
			case Srch_crt_itm.Tid__and:
			case Srch_crt_itm.Tid__or:
				bfr.AddByte(AsciiByte.ParenBgn);
				Srch_crt_itm[] subs = node.Subs;					
				int subs_len = subs.length;
				for (int i = 0; i < subs_len; ++i) {
					if (i != 0)
						bfr.AddStrA7(node.Tid == Srch_crt_itm.Tid__and ? " AND " : " OR ");
					subs[i].Accept_visitor(this);
				}
				bfr.AddByte(AsciiByte.ParenEnd);
				break;
			case Srch_crt_itm.Tid__not:
				bfr.AddStrA7("NOT ");
				node.Subs[0].Accept_visitor(this);
				break;
			case Srch_crt_itm.Tid__invalid:		break;			// should not happen
			default:								throw ErrUtl.NewUnhandled(node.Tid);
		}
	}
}
