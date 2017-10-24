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
package gplx.xowa.xtns.geoCrumbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.dbs.*;
class Geoc_isin_mgr {
	public void Reg(int page_id, byte[] page_ttl, byte[] owner_ttl) {

	}
}
class Geoc_isin_itm {
	public int Page_id() {return page_id;} private int page_id;
	public byte[] Page_ttl() {return page_ttl;} private byte[] page_ttl;
	public byte[] Owner_ttl() {return owner_ttl;} private byte[] owner_ttl;
	public Db_cmd_mode Db_state() {return db_state;} private Db_cmd_mode db_state = Db_cmd_mode.Retrieved;
	public void Init_by_make(int page_id, byte[] page_ttl, byte[] owner_ttl) {
		this.page_id = page_id; this.page_ttl = page_ttl; this.owner_ttl = owner_ttl; db_state = Db_cmd_mode.Created;
	}
}
