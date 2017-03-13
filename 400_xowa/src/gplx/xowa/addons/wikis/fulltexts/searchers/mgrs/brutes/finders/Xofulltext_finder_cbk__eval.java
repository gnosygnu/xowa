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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes.finders; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes.*;
public class Xofulltext_finder_cbk__eval implements Xofulltext_finder_cbk {
	public boolean found;
	public byte[] Page_ttl() {return page_ttl;} private byte[] page_ttl;
	public void Init(byte[] page_ttl) {
		this.found = false;
		this.page_ttl = page_ttl;
	}
	public void Process_item_found(byte[] src, int hook_bgn, int hook_end, int word_bgn, int word_end, Xofulltext_word_node term) {
		term.found = true;
	}
	public void Process_page_done(byte[] src, Xofulltext_word_node root) {
		this.found = root.Eval();
	}
}
