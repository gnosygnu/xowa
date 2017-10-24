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
package gplx.xowa.addons.bldrs.exports.splits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.bldrs.exports.splits.mgrs.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
class Split_mgr {
	public void Exec(Xow_wiki wiki, Split_cfg cfg) {
		// init
		Split_ns_itm[] ns_itms = cfg.Ns_itms();
		Split_wkr[] wkrs = new Split_wkr[] 
		{ new gplx.xowa.addons.bldrs.exports.splits.srchs.Split_wkr__srch()
		, new gplx.xowa.addons.bldrs.exports.splits.htmls.Split_wkr__html()
		, new gplx.xowa.addons.bldrs.exports.splits.pages.Split_wkr__page()	// NOTE: page needs to follow html b/c of trg_db_id
		, new gplx.xowa.addons.bldrs.exports.splits.files.Split_wkr__file()
		, new gplx.xowa.addons.bldrs.exports.splits.rndms.Split_wkr__rndm()
		};

		// init ctx
		Io_url split_root = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("tmp", "split");
		Io_mgr.Instance.DeleteDirDeep(split_root);
		Io_mgr.Instance.CreateDirIfAbsent(split_root);
		Db_conn wkr_conn = Db_conn_bldr.Instance.Get_or_autocreate(true, split_root.GenSubFil("xowa.split.sqlite3"));
		Split_ctx ctx = new Split_ctx(cfg, wiki, wkrs, ns_itms, wkr_conn);
		ctx.Trg_db__make(-1);
		new Split_mgr_init().Init(ctx, ctx.Wkr_conn(), wiki.Data__core_mgr().Tbl__page().Conn());
		for (Split_wkr wkr : wkrs) {
			wkr.Split__init(ctx, wiki, wkr_conn);
			wkr.Split__trg__1st__new(ctx, ctx.Trg_conn());
		}
		ctx.Trg_db__null();

		// split by ns
		List_adp page_list = List_adp_.New();
		Split_page_loader loader = new Split_page_loader(wiki, cfg.Loader_rows());
		for (Split_ns_itm ns_itm : ns_itms) {
			int ns_id = ns_itm.Ns_id();
			loader.Init_ns(ns_id);
			ctx.Trg_ns_(ns_id);
			while (true) {
				ctx.Trg_db__assert(ns_id);	// new db will be needed when moving between ns; EX: ns.000 goes into one db; ns.004 goes into another
				boolean reading = loader.Load_pages(ctx, page_list, wkrs, ns_id);
				Split_pages(ctx, page_list, wkrs, ns_id);
				if (!reading) {// no more rows; ns is done; stop loop and go to next ns;
					ctx.Trg_db__completed();
					break;
				}
			}
		}

		// cleanup
		loader.Rls();
		ctx.Term();
		for (Split_wkr wkr : wkrs)
			wkr.Split__term(ctx);
	}
	private void Split_pages(Split_ctx ctx, List_adp page_list, Split_wkr[] wkrs, int ns_id) {
		Split_rslt_mgr rslt_mgr = ctx.Rslt_mgr();
		int len = page_list.Len();
		for (int i = 0; i < len; ++i) { 
			ctx.Trg_db__assert(ns_id);	// new db may be needed; EX: 10,000 will be read, and 1st 100 needs 1 db; next 100 needs another db
			Xowd_page_itm page = (Xowd_page_itm)page_list.Get_at(i);
			int page_id = page.Id();
			for (Split_wkr wkr : wkrs)
				wkr.Split__exec(ctx, rslt_mgr, page, page_id);
		}
	}
}
