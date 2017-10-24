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
package gplx.xowa.addons.wikis.pages.syncs.wmapis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*;
public class Xowm_parse_data {
	public Xowm_parse_data(byte[] wiki_domain
		, int page_id, byte[] page_ttl
		, int revn_id, byte[] revn_html) {
		this.wiki_domain = wiki_domain;
		this.page_id = page_id;
		this.page_ttl = page_ttl;
		this.revn_id = revn_id;
		this.revn_html = revn_html;
	}
	public byte[] Wiki_domain() {return wiki_domain;} private final    byte[] wiki_domain;

	public int Page_id() {return page_id;} private final    int page_id;
	public byte[] Page_ttl() {return page_ttl;} private final    byte[] page_ttl;

	public int Revn_id() {return revn_id;} private final    int revn_id;
	public byte[] Revn_html() {return revn_html;} private final    byte[] revn_html;

	public byte[] Redirect_to_ttl = null;
}
