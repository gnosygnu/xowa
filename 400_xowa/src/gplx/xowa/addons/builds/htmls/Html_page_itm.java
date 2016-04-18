/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.builds.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
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
