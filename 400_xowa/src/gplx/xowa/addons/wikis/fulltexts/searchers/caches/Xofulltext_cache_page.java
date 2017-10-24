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
package gplx.xowa.addons.wikis.fulltexts.searchers.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
public class Xofulltext_cache_page {
	public Xofulltext_cache_page(int page_id, int page_seq, byte[] page_ttl) {
		this.page_id = page_id;
		this.page_seq = page_seq;
		this.page_ttl = page_ttl;
	}
	public int Page_id() {return page_id;} private final    int page_id;
	public int Page_seq() {return page_seq;} private final    int page_seq;
	public byte[] Page_ttl() {return page_ttl;} private final    byte[] page_ttl;
	public List_adp Lines() {return lines;} private final    List_adp lines = List_adp_.New();
}
