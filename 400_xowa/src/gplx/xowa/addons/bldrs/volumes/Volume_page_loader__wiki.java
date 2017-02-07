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
package gplx.xowa.addons.bldrs.volumes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.xowa.parsers.lnkis.*; import gplx.xowa.wikis.nss.*;
interface Volume_page_loader {
	boolean Load(Volume_page_itm rv, byte[] ttl);
}
class Volume_page_loader__wiki implements Volume_page_loader {
	private final    Xowe_wiki wiki;
	public Volume_page_loader__wiki(Xowe_wiki wiki) {this.wiki = wiki;}
	public boolean Load(Volume_page_itm rv, byte[] ttl) {
		Xoa_ttl page_ttl = wiki.Ttl_parse(ttl); if (page_ttl == null) return false;
		Xoa_url page_url = wiki.Utl__url_parser().Parse(ttl);
		Xoae_page page = wiki.Data_mgr().Load_page_and_parse(page_url, page_ttl);
		Load_links(rv, page.Lnki_list());
		return true;
	}
	private void Load_links(Volume_page_itm rv, List_adp list) {
		int len = list.Len();
		//gplx.xowa.wikis.data.tbls.Xowd_page_tbl page_tbl; page_tbl.Select_in__ttl
		for (int i = 0; i < len; i++) {
			Xop_lnki_tkn lnki = (Xop_lnki_tkn)list.Get_at(i);
			int ns_id = lnki.Ns_id();
			switch (ns_id) {
				case Xow_ns_.Tid__special:
				case Xow_ns_.Tid__media:
					break;
				case Xow_ns_.Tid__file:
					break;	// file
				default:
					Volume_make_itm make_itm = new Volume_make_itm();
					make_itm.Item_ttl = lnki.Ttl().Page_db();
					make_itm.Item_size = 1;
					rv.Link_list().Add(make_itm);
					break;
			}
		}
	}
}
class Volume_page_itm {
	public void Init(Xoa_ttl page_ttl, Xoa_url page_url) {
		this.page_ttl = page_ttl; this.page_url = page_url;
	}
	public Xoa_ttl Page_ttl() {return page_ttl;} private Xoa_ttl page_ttl;
	public Xoa_url Page_url() {return page_url;} private Xoa_url page_url;
	public List_adp Link_list() {return link_list;} private final    List_adp link_list = List_adp_.New();
	public List_adp File_list() {return file_list;} private final    List_adp file_list = List_adp_.New();
}
