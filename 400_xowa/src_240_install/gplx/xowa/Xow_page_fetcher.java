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
package gplx.xowa; import gplx.*;
public interface Xow_page_fetcher {
	Xow_page_fetcher Wiki_(Xow_wiki v);
	byte[] Fetch(int ns_id, byte[] ttl);
}
class Xow_page_fetcher_wiki implements Xow_page_fetcher {
	public Xow_page_fetcher Wiki_(Xow_wiki v) {this.wiki = v; return this;} private Xow_wiki wiki;
	public byte[] Fetch(int ns_id, byte[] ttl_bry) {
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, ns_id, ttl_bry);
		Xoa_page page = wiki.Data_mgr().Get_page(ttl, false);	// go through data_mgr in case of redirects
		return page.Missing() ? null : page.Data_raw();
	}
}
class Xow_page_fetcher_mok implements Xow_page_fetcher {
	public Xow_page_fetcher Wiki_(Xow_wiki v) {return this;}
	public void Clear() {pages.Clear();}	private HashAdp pages = HashAdp_.new_();
	public void Add(int ns_id, byte[] ttl, byte[] text) {
		Xodb_page page = new Xodb_page().Ns_id_(ns_id).Ttl_wo_ns_(ttl).Text_(text);
		pages.Add(Make_key(ns_id, ttl), page);
	}
	public byte[] Fetch(int ns_id, byte[] ttl) {
		Xodb_page rv = (Xodb_page)pages.Fetch(Make_key(ns_id, ttl));
		return rv == null ? null : rv.Text();
	}
	String Make_key(int ns_id, byte[] ttl) {return Int_.Xto_str(ns_id) + "|" + String_.new_utf8_(ttl);}
}
