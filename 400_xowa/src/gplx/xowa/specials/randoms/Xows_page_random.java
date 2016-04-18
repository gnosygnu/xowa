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
package gplx.xowa.specials.randoms; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.xowa.wikis.nss.*;
public class Xows_page_random implements Xows_page {
	public Xows_page_random(Xowe_wiki wiki) {}
	// public Rndm_mgr Mgr() {return mgr;} private final    Rndm_mgr mgr = new Rndm_mgr();
	public Xows_special_meta Special__meta() {return Xows_special_meta_.Itm__random;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		Xow_ns ns = wiki.Ns_mgr().Names_get_or_main(ttl.Rest_txt());
		byte[] random_ttl_bry = wiki.Db_mgr().Load_mgr().Find_random_ttl(ns);
		wiki.Data_mgr().Redirect(page, ns.Gen_ttl(random_ttl_bry));
	}

	public Xows_page Special__clone() {return this;}
}
