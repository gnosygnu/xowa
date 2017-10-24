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
package gplx.xowa.addons.bldrs.hdumps.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.hdumps.*;
class Dumpdiff_page_itm {
	public Dumpdiff_page_itm(int page_id, int ns_id, byte[] ttl_bry, int cur_db_id, int prv_db_id) {
		this.page_id = page_id;
		this.ns_id = ns_id;
		this.ttl_bry = ttl_bry;
		this.cur_db_id = cur_db_id;
		this.prv_db_id = prv_db_id;
	}
	public int Page_id() {return page_id;} private final    int page_id;
	public int Ns_id() {return ns_id;} private final    int ns_id;
	public byte[] Ttl_bry() {return ttl_bry;} private final    byte[] ttl_bry;
	public int Cur_db_id() {return cur_db_id;} private final    int cur_db_id;
	public int Prv_db_id() {return prv_db_id;} private final    int prv_db_id;
}
