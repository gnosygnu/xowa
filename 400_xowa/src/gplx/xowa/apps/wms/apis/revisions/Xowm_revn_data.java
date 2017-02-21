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
package gplx.xowa.apps.wms.apis.revisions; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
public class Xowm_revn_data {
	public Xowm_revn_data(byte[] wiki_domain
		, int page_id, int page_ns, byte[] page_ttl
		, int revn_id, DateAdp revn_time, byte[] revn_text) {
		this.wiki_domain = wiki_domain;
		this.page_id = page_id;
		this.page_ns = page_ns;
		this.page_ttl = page_ttl;
		this.revn_id = revn_id;
		this.revn_time = revn_time;
		this.revn_text = revn_text;
	}
	public byte[] Wiki_domain() {return wiki_domain;} private final    byte[] wiki_domain;

	public int Page_id() {return page_id;} private final    int page_id;
	public int Page_ns() {return page_ns;} private final    int page_ns;
	public byte[] Page_ttl() {return page_ttl;} private final    byte[] page_ttl;

	public int Revn_id() {return revn_id;} private final    int revn_id;
	public DateAdp Revn_time() {return revn_time;} private final    DateAdp revn_time;
	public byte[] Revn_text() {return revn_text;} private final    byte[] revn_text;
}
