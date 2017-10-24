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
package gplx.xowa.wikis.data.fetchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xow_page_fetcher_test implements Xow_page_fetcher {
	public Xow_page_fetcher Wiki_(Xowe_wiki v) {return this;}
	public void Clear() {pages.Clear();}	private Hash_adp pages = Hash_adp_.New();
	public void Add(int ns_id, byte[] ttl, byte[] text) {
		Xowd_page_itm page = new Xowd_page_itm().Ns_id_(ns_id).Ttl_page_db_(ttl).Text_(text);
		pages.Add(Make_key(ns_id, ttl), page);
	}
	public byte[] Get_by(int ns_id, byte[] ttl) {
		Xowd_page_itm rv = (Xowd_page_itm)pages.Get_by(Make_key(ns_id, ttl));
		return rv == null ? null : rv.Text();
	}
	String Make_key(int ns_id, byte[] ttl) {return Int_.To_str(ns_id) + "|" + String_.new_u8(ttl);}
}
