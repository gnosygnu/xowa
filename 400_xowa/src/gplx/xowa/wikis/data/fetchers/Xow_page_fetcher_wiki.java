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
package gplx.xowa.wikis.data.fetchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
public class Xow_page_fetcher_wiki implements Xow_page_fetcher {
	public Xow_page_fetcher Wiki_(Xowe_wiki v) {this.wiki = v; return this;} private Xowe_wiki wiki;
	public void Clear() {}
	public byte[] Get_by(int ns_id, byte[] ttl_bry) {
		Xoa_ttl ttl = Xoa_ttl.parse(wiki, ns_id, ttl_bry);
		Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(ttl);	// go through data_mgr in case of redirects
		return page.Missing() ? null : page.Data_raw();
	}
}
