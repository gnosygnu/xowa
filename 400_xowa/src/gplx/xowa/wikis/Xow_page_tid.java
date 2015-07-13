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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import gplx.xowa.xtns.wdatas.*;
public class Xow_page_tid {
	public static byte Identify(int wiki_tid, int ns_id, byte[] ttl) {
		switch (ns_id) {
			case Xow_ns_.Id_mediawiki:
			case Xow_ns_.Id_user:
				return Identify_by_ttl(ttl);
			case gplx.xowa.xtns.scribunto.Scrib_xtn_mgr.Ns_id_module:
				return	(Bry_.Has_at_end(ttl, Ext_doc))
					? Tid_wikitext : Tid_lua;
			default:
				return Wdata_wiki_mgr.Wiki_page_is_json(wiki_tid, ns_id)
					? Tid_json : Tid_wikitext;
		}
	}
	public static byte Identify_by_ttl(byte[] ttl) {
		if		(Bry_.Has_at_end(ttl, Ext_css))	return Tid_css;
		else if (Bry_.Has_at_end(ttl, Ext_js))	return Tid_js;
		else									return Tid_wikitext;
	}
	private static final byte[] Ext_js = Bry_.new_a7(".js"), Ext_css = Bry_.new_a7(".css"), Ext_doc= Bry_.new_a7("/doc");
	public static final byte Tid_wikitext = 1, Tid_json = 2, Tid_js = 3, Tid_css = 4, Tid_lua = 5;
}
