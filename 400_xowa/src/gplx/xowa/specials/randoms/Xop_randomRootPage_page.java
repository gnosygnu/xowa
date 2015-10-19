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
public class Xop_randomRootPage_page implements Xows_page {
	public Xows_special_meta Special_meta() {return Xows_special_meta_.Itm__random_root_page;}
	public void Special_gen(Xowe_wiki wiki, Xoae_page page, Xoa_url url, Xoa_ttl ttl) {
		Xow_ns ns = wiki.Ns_mgr().Names_get_or_main(ttl.Rest_txt());
		byte[] random_ttl_bry = wiki.Db_mgr().Load_mgr().Find_random_ttl(ns);
		byte[] root_bry = Xoa_ttl.parse(wiki, random_ttl_bry).Root_txt();
		wiki.Data_mgr().Redirect(page, ns.Gen_ttl(root_bry));
	}
}
