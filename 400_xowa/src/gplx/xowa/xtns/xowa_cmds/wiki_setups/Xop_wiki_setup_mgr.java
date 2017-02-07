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
package gplx.xowa.xtns.xowa_cmds.wiki_setups; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.xowa_cmds.*;
import gplx.langs.mustaches.*;
import gplx.xowa.addons.bldrs.centrals.dbs.*; import gplx.xowa.addons.bldrs.centrals.dbs.datas.*; import gplx.xowa.addons.bldrs.centrals.dbs.datas.imports.*; import gplx.xowa.addons.bldrs.centrals.hosts.*;
import gplx.xowa.addons.bldrs.exports.packs.files.*; import gplx.xowa.addons.bldrs.centrals.tasks.*;
import gplx.xowa.parsers.*;
class Xop_wiki_setup_mgr {
	private final    Mustache_tkn_parser parser = new Mustache_tkn_parser();
	private Xobc_data_db data_db;
	public Xop_root_tkn Write(Xoae_app app, Xowe_wiki wiki, Xop_ctx ctx, Xoae_page wpg, byte[] language, byte[][] wiki_domains) {
		Io_url mustache_url = app.Fsys_mgr().Bin_addon_dir().GenSubFil_nest("bldr", "wiki_setup", "wiki_setup_main.mustache.html");
		Mustache_tkn_itm root = parser.Parse(Io_mgr.Instance.LoadFilBry(mustache_url));
		Mustache_render_ctx mctx = new Mustache_render_ctx();
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		Mustache_bfr mbfr = new Mustache_bfr(tmp_bfr);

		this.data_db = Xobc_data_db.New(app.Fsys_mgr());
		root.Render(mbfr, mctx.Init(Make_root(language, wiki_domains)));

		return wiki.Parser_mgr().Main().Parse_text_to_wdom(Xop_ctx.New__sub(wiki, ctx, wpg), mbfr.To_bry_and_clear(), true);
	}
	private Xows_root_itm Make_root(byte[] language, byte[][] wiki_domains) {
		Bry_bfr url_list_bfr = Bry_bfr_.New();
		int len = wiki_domains.length;
		Xows_wiki_itm[] wiki_ary = new Xows_wiki_itm[len];
		for (int i = 0; i < len; ++i) {
			byte[] wiki_domain = wiki_domains[i];
			wiki_ary[i] = new Xows_wiki_itm(wiki_domain, Make_task_ary(url_list_bfr, wiki_domain));
		}
		return new Xows_root_itm(language, url_list_bfr.To_bry_and_clear(), wiki_ary);
	}
	private Xows_task_itm[] Make_task_ary(Bry_bfr url_list_bfr, byte[] wiki_domain) {
		Xobc_task_regy_itm[] task_rows = data_db.Tbl__task_regy().Select_by_wiki(wiki_domain);
		int len = task_rows.length;
		Xows_task_itm[] rv = new Xows_task_itm[len];
		for (int i = 0; i < len; ++i) {
			Xobc_task_regy_itm task_row = task_rows[i];
			Xobc_task_key task_key = Xobc_task_key.To_itm(String_.new_u8(task_row.Key()));
			String task_key_type = task_key.Task_type_ui();
			Xows_file_itm[] files = Make_file_ary(url_list_bfr, wiki_domain, task_row.Id());
			rv[i] = new Xows_task_itm(task_row.Seqn(), wiki_domain, task_row.Name(), Bry_.new_u8(task_key_type), Bry_.new_u8(task_key.Wiki_date_ui()), files);
		}
		Array_.Sort(rv, new Xows_task_itm_sorter());
		return rv;
	}
	private Xows_file_itm[] Make_file_ary(Bry_bfr url_list_bfr, byte[] wiki_domain, int task_id) {
		Xobc_import_step_itm[] rows = data_db.Tbl__import_step().Select_by_task_id(task_id);
		int len = rows.length;
		Xows_file_itm[] rv = new Xows_file_itm[len];
		Host_eval_itm host_eval = new Host_eval_itm();
		for (int i = 0; i < len; ++i) {
			Xobc_import_step_itm row = rows[i];
			String src_fil = host_eval.Eval_src_fil(data_db, row.Host_id, gplx.xowa.wikis.domains.Xow_domain_itm_.parse(wiki_domain), row.Import_name);
			url_list_bfr.Add_str_u8(src_fil).Add_byte_nl();
			rv[i] = new Xows_file_itm(row.Step_id, Bry_.new_u8(src_fil));
		}
		Array_.Sort(rv, new Xows_file_itm_sorter());
		return rv;
	}
}
