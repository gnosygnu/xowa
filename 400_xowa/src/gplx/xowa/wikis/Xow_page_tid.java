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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.xtns.wbases.*;
public class Xow_page_tid {
	public static byte Identify(int wiki_tid, int ns_id, byte[] ttl) {
		switch (ns_id) {
			case Xow_ns_.Tid__mediawiki:
				byte rv = Identify_by_ttl(ttl);
				return rv == Tid_wikitext ? Tid_msg : rv;	// if mediawiki ns, but not css / js, return msg, not wikitext; DATE:2016-09-12
			case Xow_ns_.Tid__user:
				return Identify_by_ttl(ttl);
			case Xow_ns_.Tid__module:
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
	private static final    byte[] Ext_js = Bry_.new_a7(".js"), Ext_css = Bry_.new_a7(".css"), Ext_doc= Bry_.new_a7("/doc");
	public static final byte Tid_wikitext = 1, Tid_json = 2, Tid_js = 3, Tid_css = 4, Tid_lua = 5, Tid_msg = 6;
}
