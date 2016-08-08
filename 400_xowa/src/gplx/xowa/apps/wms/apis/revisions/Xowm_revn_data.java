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
