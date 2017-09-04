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
import gplx.gflucene.indexers.*;
public class Xofulltext_indexer_args implements Gfo_invk {
	public byte[] wikis;
	public String idx_opt = Gflucene_idx_opt.Docs_and_freqs.Key();
	private String ns_ids_str;
	public int[] ns_ids_ary;
	public void Init_by_wiki(Xowe_wiki wiki) {
		// wikis: null 
		if (wikis == null)
			wikis = wiki.Domain_bry();

		// ns: null or *
		// if null, use Main namespace
		List_adp temp_ns_list = List_adp_.New();
		if (ns_ids_str == null)
			temp_ns_list.Add(Xow_ns_.Tid__main);
		// if *, use all namespaces
		else if (String_.Eq(ns_ids_str, "*")) {
			Xow_ns[] ns_ary = wiki.Ns_mgr().Ords_ary();
			int len = ns_ary.length;
			for (int i = 0; i < len; i++) {
				Xow_ns ns = ns_ary[i];
				int ns_id = ns.Id();
				if (ns_id < 0) continue; // ignore media, special 
				temp_ns_list.Add(ns_id);
			}
		}
		// else, parse ns
		else {
			byte[][] ns_bry_ary = Bry_split_.Split(Bry_.new_u8(ns_ids_str), Byte_ascii.Comma, true);
			for (byte[] ns_bry : ns_bry_ary) {
				temp_ns_list.Add(Bry_.To_int(ns_bry));
			}
		}
		ns_ids_ary = (int[])temp_ns_list.To_ary_and_clear(int.class);

		// idx_opt
		if (idx_opt == null) {
			idx_opt = Gflucene_idx_opt.Docs_and_freqs.Key();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if      (ctx.Match(k, "wikis_"))            this.wikis = m.ReadBryOr("v", null);
		else if	(ctx.Match(k, "ns_ids_"))           this.ns_ids_str = m.ReadStrOr("v", null);
		else if	(ctx.Match(k, "idx_opt_"))          this.idx_opt = m.ReadStrOr("v", null);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static Xofulltext_indexer_args New_by_json(gplx.langs.jsons.Json_nde args) {
		Xofulltext_indexer_args rv = new Xofulltext_indexer_args();
		rv.wikis = args.Get_as_bry("wikis");
		rv.ns_ids_str = args.Get_as_str("ns_ids");
		rv.idx_opt = args.Get_as_str("idx_opt");
		return rv;
	}
}
