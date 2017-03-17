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
package gplx.xowa.addons.wikis.fulltexts.indexers.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.indexers.*;
import gplx.xowa.wikis.nss.*;
public class Xofulltext_indexer_args implements Gfo_invk {
	public byte[] wikis;
	public String ns_ids;
	public void Init_by_wiki(Xowe_wiki wiki) {
		// wikis: null 
		if (wikis == null)
			wikis = wiki.Domain_bry();

		// ns: null / *
		if (ns_ids == null)
			ns_ids = "0";
		else if (String_.Eq(ns_ids, "*")) {
			Xow_ns[] ns_ary = wiki.Ns_mgr().Ords_ary();
			int len = ns_ary.length;
			Bry_bfr bfr = Bry_bfr_.New();
			for (int i = 0; i < len; i++) {
				Xow_ns ns = ns_ary[i];
				int ns_id = ns.Id();
				if (ns_id < 0) continue; // ignore media, special 
				if (i != 0) bfr.Add_byte(Byte_ascii.Pipe);
				bfr.Add_int_variable(ns_id);
			}
			ns_ids = bfr.To_str_and_clear();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if      (ctx.Match(k, "wikis_"))            this.wikis = m.ReadBryOr("v", null);
		else if	(ctx.Match(k, "ns_ids"))            this.ns_ids = m.ReadStrOr("v", null);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static Xofulltext_indexer_args New_by_json(gplx.langs.jsons.Json_nde args) {
		Xofulltext_indexer_args rv = new Xofulltext_indexer_args();
		rv.wikis = args.Get_as_bry("wikis");
		rv.ns_ids = args.Get_as_str("ns_ids");
		return rv;
	}
}
