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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.langs.*;
class Http_server_wkr_ {
	public static String Assert_main_page(Xoae_app app, String req) {
		int mode = -1;
		String[] req_array = String_.Split(req, "/");
		if		(String_.Has_at_end(req, "wiki/")) 	mode = 0;
		else if	(String_.Has_at_end(req, "wiki")) 	mode = 1;
		else if (req_array.length == 3)				mode = 2;
		if (mode == -1) return req;	// not a link to a Main Page; EX:localhost:8080/en.wikipedia.org/wiki/Earth
		if (req_array.length < 3) return req; // shouldn't happen; EX: "localhost:8080wiki"
		byte[] wiki_domain = Bry_.new_u8(req_array[1]);
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(wiki_domain);
		if (domain_itm.Domain_type_id() == Xow_domain_tid_.Int__other && domain_itm.Lang_actl_itm().Id() == Xol_lang_stub_.Id__intl) return req;
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_key_or_make(wiki_domain);
		wiki.Init_assert();
		String main_page = String_.new_u8(wiki.Props().Main_page());
		if 		(mode == 1) main_page = "/" + main_page;
		else if (mode == 2) main_page = "wiki/" + main_page;
		return req + main_page;
	}
}
