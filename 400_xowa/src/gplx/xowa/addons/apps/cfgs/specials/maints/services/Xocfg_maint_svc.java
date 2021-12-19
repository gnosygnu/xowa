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
package gplx.xowa.addons.apps.cfgs.specials.maints.services;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import gplx.xowa.addons.apps.cfgs.dbs.*; import gplx.xowa.addons.apps.cfgs.dbs.tbls.*;
import gplx.xowa.addons.apps.cfgs.enums.*;
public class Xocfg_maint_svc {
	private Xoa_app app;
	public Xocfg_maint_svc(Xoa_app app) {
		this.app = app;
	}
	public void Upsert(String raw) {
		// get wikitext parser
		Xowe_wiki wiki = (Xowe_wiki)app.User().Wikii();
		gplx.xowa.parsers.Xow_parser_mgr parser_mgr = wiki.Parser_mgr();

		// parse raw
		Xocfg_maint_parser maint_parser = new Xocfg_maint_parser();
		Xocfg_maint_nde[] ndes = maint_parser.Parse(raw);

		// exec
		Xocfg_db_app db_app = Xocfg_db_app.New(app);
		db_app.Conn().Txn_bgn("xo__cfg_maint__upsert");
		byte[] anch_find_bry = BryUtl.NewA7("<a "), anch_repl_bry = BryUtl.NewA7(" tabindex=\"-1\"");
		BryWtr tmp_bfr = BryWtr.New();
		for (Xocfg_maint_nde nde : ndes) {
			// parse help to html
			parser_mgr.Main().Parse_text_to_html(tmp_bfr, parser_mgr.Ctx(), parser_mgr.Ctx().Page(), true, BryUtl.NewU8(nde.Help()));
			byte[] help = tmp_bfr.ToBryAndClear();

			// add "tabindex=-1" to "<a>" tages else tabbing will go to hidden anchors in help text
			int help_pos = 0;
			while (true) {
				int a_bgn = BryFind.FindFwd(help, anch_find_bry, help_pos, help.length);
				if (a_bgn == BryFind.NotFound) break;
				int a_end = BryFind.FindFwd(help, AsciiByte.AngleEnd, a_bgn, help.length);
				if (a_end == BryFind.NotFound) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "could not find closing > after <a", "src", help);
					break;
				}

				tmp_bfr.AddMid(help, 0, a_end);
				tmp_bfr.Add(anch_repl_bry);
				tmp_bfr.AddMid(help, a_end, help.length);
				help = tmp_bfr.ToBryAndClear();
				help_pos = a_end;
			}

			// do insert
			if (nde.Type_is_grp()) {
				Xocfg_maint_svc.Create_grp(db_app, nde.Owner(), nde.Id(), nde.Key(), nde.Name(), StringUtl.NewU8(help));
			}
			else {
				Xocfg_maint_itm itm = (Xocfg_maint_itm)nde;
				Xocfg_maint_svc.Create_itm(db_app, nde.Owner(), nde.Id(), nde.Key(), nde.Name(), StringUtl.NewU8(help), itm.Scope(), itm.Type(), itm.Dflt(), itm.Html_atrs(), itm.Html_cls());
			}
		}
		db_app.Conn().Txn_end();
	}
	public static void Create_grp(Xocfg_db_app db_app, String owner, int id, String key, String name, String help) {
		// insert grp_meta
		db_app.Tbl__grp().Upsert(id, key);

		// insert grp_map
		int owner_id = StringUtl.IsNullOrEmpty(owner) ? Xocfg_grp_row.Id__root : db_app.Tbl__grp().Select_id_by_key_or_fail(owner);
		int map_sort = db_app.Tbl__map().Select_sort_or_next(owner_id, id);
		db_app.Tbl__map().Upsert(owner_id, id, map_sort);

		// insert nde_i18n
		db_app.Tbl__txt().Upsert(id, Xoitm_lang_tid.Lang__dflt, name, help);
	}
	public static void Create_itm(Xocfg_db_app db_app, String owner, int id, String key, String name, String help, String scope, String type, String dflt, String html_atrs, String html_cls) {
		// insert itm_meta
		db_app.Tbl__itm().Upsert(id, key, Xoitm_scope_enum.To_int(scope), type, dflt, html_atrs, html_cls);

		// insert grp_map
		int grp_id = db_app.Tbl__grp().Select_id_by_key_or_fail(owner);
		int itm_sort = db_app.Tbl__map().Select_sort_or_next(grp_id, id);
		db_app.Tbl__map().Upsert(grp_id, id, itm_sort);

		// insert nde_i18n
		db_app.Tbl__txt().Upsert(id, Xoitm_lang_tid.Lang__dflt, name, help);
	}
}
