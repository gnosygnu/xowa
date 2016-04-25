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
package gplx.xowa.addons.apps.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.xowa.wikis.pages.*;
public class Wikis_list_page implements Xows_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		Xoa_url_arg_mgr arg_mgr = new Xoa_url_arg_mgr(null).Init(url.Qargs_ary());
		Wikis_list_wtr wtr = new Wikis_list_wtr();
		byte[] cmd = arg_mgr.Read_bry_or_null("cmd");
		if (cmd == null) {
			Xopage_html_data html_data = wtr.Write(wiki.App(), url.Qargs_ary(), GfoInvkAble_.Null);
			html_data.Apply(page);
		}
		else {
			if (Bry_.Eq(cmd, Bry_.new_a7("add"))) {
				byte[] file = arg_mgr.Read_bry_or_null("file");
				if (file != null) {
					if (wiki.App().Tid_is_edit()) {
						wiki.App().User().User_db_mgr().Init_site_mgr();
					}
					wiki.App().Wiki_mgri().Import_by_url(Io_url_.new_fil_(String_.new_u8(file)));
					Xopage_html_data html_data = wtr.Write(wiki.App(), url.Qargs_ary(), GfoInvkAble_.Null);
					html_data.Apply(page);
				}
			}
		}
	}

	public static final String SPECIAL_KEY = "XowaWikis";
	public static final    byte[] Display_ttl = Bry_.new_a7("XOWA Wikis");
	public Xows_special_meta Special__meta() {return new Xows_special_meta(Xows_special_meta_.Src__xowa, SPECIAL_KEY);}
	public static final    Xows_page Prototype = new Wikis_list_page();
	public Xows_page Special__clone() {return this;}
}
