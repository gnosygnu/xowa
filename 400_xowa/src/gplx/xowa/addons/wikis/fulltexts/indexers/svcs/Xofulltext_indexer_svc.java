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
package gplx.xowa.addons.wikis.fulltexts.indexers.svcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.indexers.*;
import gplx.core.btries.*;
import gplx.langs.jsons.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.guis.cbks.*;
import gplx.xowa.addons.apps.cfgs.*;
import gplx.xowa.addons.wikis.fulltexts.indexers.specials.*;
import gplx.xowa.addons.wikis.fulltexts.indexers.bldrs.*;
class Xofulltext_indexer_svc implements Gfo_invk {
	private final    Xoa_app app;
	private final    Xog_cbk_trg cbk_trg = Xog_cbk_trg.New(Xofulltext_indexer_special.Prototype.Special__meta().Ttl_bry());
	public Xofulltext_indexer_svc(Xoa_app app) {
		this.app = app;
	}
	public void Index(Json_nde args) {
		// create args
		Xofulltext_indexer_args indexer_args = Xofulltext_indexer_args.New_by_json(args);

		// launch thread
		gplx.core.threads.Thread_adp_.Start_by_val("index", Cancelable_.Never, this, Invk__index, indexer_args);
	}
	private void Index(Xofulltext_indexer_args args) {
		// loop wikis
		byte[][] domain_ary = Bry_split_.Split(args.wikis, Byte_ascii.Pipe);
		for (byte[] domain : domain_ary) {
			// get wiki
			Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_n(domain);
			if (!Io_mgr.Instance.ExistsDir(wiki.Fsys_mgr().Root_dir())) {
				app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.fulltext_indexer.status__note__recv", gplx.core.gfobjs.Gfobj_nde.New()
					.Add_str("note", Datetime_now.Get().XtoStr_fmt_yyyy_MM_dd_HH_mm_ss() + ": wiki does not exist: " + String_.new_u8(domain)));
				continue;
			}

			// check if dir exists
			wiki.Init_by_wiki();
			Io_url search_dir = Xosearch_fulltext_addon.Get_index_dir(wiki);
			if (Io_mgr.Instance.ExistsDir(search_dir)) {
				app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.fulltext_indexer.status__note__recv", gplx.core.gfobjs.Gfobj_nde.New()
					.Add_str("note", Datetime_now.Get().XtoStr_fmt_yyyy_MM_dd_HH_mm_ss() + ": search dir already exists; please delete it manually before reindexing; dir=" + search_dir.Xto_api()));
				continue;
			}

			// notify bgn
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.fulltext_indexer.status__note__recv", gplx.core.gfobjs.Gfobj_nde.New()
				.Add_str("note", Datetime_now.Get().XtoStr_fmt_yyyy_MM_dd_HH_mm_ss() + ": wiki index started: " + String_.new_u8(domain)));

			// run index
			new Xofulltext_indexer_mgr().Exec((Xowe_wiki)wiki, new Xofulltext_indexer_ui(app.Gui__cbk_mgr(), cbk_trg), args);

			// notify end
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.fulltext_indexer.status__note__recv", gplx.core.gfobjs.Gfobj_nde.New()
				.Add_str("note", Datetime_now.Get().XtoStr_fmt_yyyy_MM_dd_HH_mm_ss() + ": wiki index ended: " + String_.new_u8(domain)));
		}
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__index))           this.Index((Xofulltext_indexer_args)m.ReadObj("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}  
	private static final String Invk__index = "index";
}
