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
package gplx.xowa.xtns.xowa_cmds.wiki_setups;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.langs.mustaches.Mustache_bfr;
import gplx.langs.mustaches.Mustache_render_ctx;
import gplx.langs.mustaches.Mustache_tkn_itm;
import gplx.langs.mustaches.Mustache_tkn_parser;
import gplx.types.basics.utls.ArrayUtl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.bldrs.centrals.dbs.Xobc_data_db;
import gplx.xowa.addons.bldrs.centrals.dbs.datas.Xobc_task_regy_itm;
import gplx.xowa.addons.bldrs.centrals.dbs.datas.imports.Xobc_import_step_itm;
import gplx.xowa.addons.bldrs.centrals.hosts.Host_eval_itm;
import gplx.xowa.addons.bldrs.centrals.tasks.Xobc_task_key;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_root_tkn;
class Xop_wiki_setup_mgr {
	private final Mustache_tkn_parser parser = new Mustache_tkn_parser();
	private Xobc_data_db data_db;
	public Xop_root_tkn Write(Xoae_app app, Xowe_wiki wiki, Xop_ctx ctx, Xoae_page wpg, byte[] language, byte[][] wiki_domains) {
		Io_url mustache_url = app.Fsys_mgr().Bin_addon_dir().GenSubFil_nest("bldr", "wiki_setup", "wiki_setup_main.mustache.html");
		Mustache_tkn_itm root = parser.Parse(Io_mgr.Instance.LoadFilBry(mustache_url));
		Mustache_render_ctx mctx = new Mustache_render_ctx();
		BryWtr tmp_bfr = BryWtr.New();
		Mustache_bfr mbfr = new Mustache_bfr(tmp_bfr);

		this.data_db = Xobc_data_db.New(app.Fsys_mgr());
		root.Render(mbfr, mctx.Init(Make_root(language, wiki_domains)));

		return wiki.Parser_mgr().Main().Parse_text_to_wdom(Xop_ctx.New__sub(wiki, ctx, wpg), mbfr.To_bry_and_clear(), true);
	}
	private Xows_root_itm Make_root(byte[] language, byte[][] wiki_domains) {
		BryWtr url_list_bfr = BryWtr.New();
		int len = wiki_domains.length;
		Xows_wiki_itm[] wiki_ary = new Xows_wiki_itm[len];
		for (int i = 0; i < len; ++i) {
			byte[] wiki_domain = wiki_domains[i];
			wiki_ary[i] = new Xows_wiki_itm(wiki_domain, Make_task_ary(url_list_bfr, wiki_domain));
		}
		return new Xows_root_itm(language, url_list_bfr.ToBryAndClear(), wiki_ary);
	}
	private Xows_task_itm[] Make_task_ary(BryWtr url_list_bfr, byte[] wiki_domain) {
		Xobc_task_regy_itm[] task_rows = data_db.Tbl__task_regy().Select_by_wiki(wiki_domain);
		int len = task_rows.length;
		List_adp list = List_adp_.New();
		for (int i = 0; i < len; ++i) {
			Xobc_task_regy_itm task_row = task_rows[i];
			if (task_row.Seqn() == 999990) continue; // ignore 999990 archive rows for en.w; DATE:2017-04-26
			Xobc_task_key task_key = Xobc_task_key.To_itm(StringUtl.NewU8(task_row.Key()));
			String task_key_type = task_key.Task_type_ui();
			Xows_file_itm[] files = Make_file_ary(url_list_bfr, wiki_domain, task_row.Id());
			list.Add(new Xows_task_itm(task_row.Seqn(), wiki_domain, task_row.Name(), BryUtl.NewU8(task_key_type), BryUtl.NewU8(task_key.Wiki_date_ui()), files));
		}
		list.SortBy(new Xows_task_itm_sorter());
		return (Xows_task_itm[])list.ToAryAndClear(Xows_task_itm.class);
	}
	private Xows_file_itm[] Make_file_ary(BryWtr url_list_bfr, byte[] wiki_domain, int task_id) {
		Xobc_import_step_itm[] rows = data_db.Tbl__import_step().Select_by_task_id(task_id);
		int len = rows.length;
		Xows_file_itm[] rv = new Xows_file_itm[len];
		Host_eval_itm host_eval = new Host_eval_itm();
		for (int i = 0; i < len; ++i) {
			Xobc_import_step_itm row = rows[i];
			String src_fil = host_eval.Eval_src_fil(data_db, row.Host_id, gplx.xowa.wikis.domains.Xow_domain_itm_.parse(wiki_domain), row.Import_name);
			url_list_bfr.AddStrU8(src_fil).AddByteNl();
			rv[i] = new Xows_file_itm(row.Step_id, BryUtl.NewU8(src_fil));
		}
		ArrayUtl.Sort(rv, new Xows_file_itm_sorter());
		return rv;
	}
}
