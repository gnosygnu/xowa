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
package gplx.xowa.addons.bldrs.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.langs.mustaches.*;
class Html_page_itm implements Mustache_doc_itm {
	private byte[] http_root;
	private byte[] page_root;
	private byte[] page_title;
	private byte[] page_head_extra;
	private byte[] page_caption;
	private byte[] page_body;
	public Html_page_itm Init(byte[] http_root, byte[] page_root, byte[] page_title, byte[] page_head_extra, byte[] page_caption, byte[] page_body) {
		this.http_root = http_root;
		this.page_root = page_root;
		this.page_title = page_title;
		this.page_head_extra = page_head_extra;
		this.page_caption = page_caption;
		this.page_body = page_body;
		return this;
	}
	public boolean Mustache__write(String key, Mustache_bfr mbfr) {
		if		(String_.Eq(key, "page_title"))			mbfr.Add_bry(page_title);
		else if	(String_.Eq(key, "http_root"))			mbfr.Add_bry(http_root);
		else if	(String_.Eq(key, "page_root"))			mbfr.Add_bry(page_root);
		else if	(String_.Eq(key, "page_head_extra"))	mbfr.Add_bry(page_head_extra);
		else if	(String_.Eq(key, "page_caption"))		mbfr.Add_bry(page_caption);
		else if	(String_.Eq(key, "page_body"))			mbfr.Add_bry(page_body);
		else											return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		return Mustache_doc_itm_.Ary__empty;
	}
}
