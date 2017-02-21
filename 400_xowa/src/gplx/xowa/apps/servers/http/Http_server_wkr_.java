/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
		if (domain_itm.Domain_type_id() == Xow_domain_tid_.Tid__other && domain_itm.Lang_actl_itm().Id() == Xol_lang_stub_.Id__intl) return req;
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_or_make(wiki_domain);
		wiki.Init_assert();
		String main_page = String_.new_u8(wiki.Props().Main_page());
		if 		(mode == 1) main_page = "/" + main_page;
		else if (mode == 2) main_page = "wiki/" + main_page;
		return req + main_page;
	}
}
