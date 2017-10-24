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
package gplx.xowa.addons.wikis.fulltexts.indexers.svcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.indexers.*;
import gplx.xowa.guis.cbks.*;
public class Xofulltext_indexer_ui {
	private final    Xog_cbk_mgr cbk_mgr;
	private final    Xog_cbk_trg cbk_trg;
	public Xofulltext_indexer_ui(Xog_cbk_mgr cbk_mgr, Xog_cbk_trg cbk_trg) {
		this.cbk_mgr = cbk_mgr;
		this.cbk_trg = cbk_trg;
	}
	public void Send_prog(String prog) {
		cbk_mgr.Send_json(cbk_trg, "xo.fulltext_indexer.status__prog__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_str("prog", prog)
			);
	}
}
