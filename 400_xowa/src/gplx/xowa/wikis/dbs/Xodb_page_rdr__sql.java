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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
class Xodb_page_rdr__sql implements Xodb_page_rdr {
	private final    Xow_db_mgr db_mgr;
	private final    Xowd_page_tbl page_tbl; private final    Db_rdr rdr;
	public Xodb_page_rdr__sql(Xowe_wiki wiki) {
		this.db_mgr = wiki.Data__core_mgr();
		this.page_tbl = db_mgr.Tbl__page();
		this.rdr = page_tbl.Select_all__id__ttl();
	}
	public boolean Move_next() {return rdr.Move_next();}
	public boolean Read(Xowd_page_itm page) {
		page_tbl.Read_page__all(page, rdr);
		Xowd_text_tbl text_tbl = db_mgr.Dbs__get_by_id_or_fail(page.Text_db_id()).Tbl__text();
		page.Text_(text_tbl.Select(page.Id()));
		return true;
	}
	public void Rls() {
		rdr.Rls();
	}
}

