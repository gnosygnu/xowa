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
package gplx.xowa.addons.wikis.pages.syncs.wmapis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*;
public class Xowm_parse_data {
	public Xowm_parse_data(byte[] wiki_domain
		, int page_id, byte[] page_ttl, byte[] redirect_src
		, int revn_id, byte[] revn_html) {
		this.wiki_domain = wiki_domain;
		this.page_id = page_id;
		this.page_ttl = page_ttl;
		this.redirect_src = redirect_src;
		this.revn_id = revn_id;
		this.revn_html = revn_html;
	}
	public byte[] Wiki_domain() {return wiki_domain;} private final    byte[] wiki_domain;

	public int Page_id() {return page_id;} private final    int page_id;
	public byte[] Page_ttl() {return page_ttl;} private final    byte[] page_ttl;
	public byte[] Redirect_src() {return redirect_src;} private final    byte[] redirect_src;

	public int Revn_id() {return revn_id;} private final    int revn_id;
	public byte[] Revn_html() {return revn_html;} private final    byte[] revn_html;
}
