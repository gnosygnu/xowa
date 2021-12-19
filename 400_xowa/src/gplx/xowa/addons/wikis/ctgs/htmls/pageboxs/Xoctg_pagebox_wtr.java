/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs;

import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.files.Io_mgr;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_page;
import gplx.xowa.Xow_wiki;
import gplx.xowa.addons.wikis.ctgs.dbs.Xodb_cat_db_;
import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.doubles.Xoctg_double_box;
import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.singles.Xoctg_single_box;
import gplx.xowa.wikis.data.Xow_db_file;
import gplx.xowa.wikis.data.tbls.Xowd_cat_core_tbl;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;

public class Xoctg_pagebox_wtr implements Gfo_invk {
	private final Xoctg_single_box single_box = new Xoctg_single_box();
	private final Xoctg_double_box double_box = new Xoctg_double_box();
	private Xow_wiki wiki;
	private boolean grouping_enabled;

	public void Init_by_wiki(Xow_wiki wiki) {
		this.wiki = wiki;
		single_box.Init_by_wiki(wiki);
		double_box.Init_by_wiki(wiki);
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__grouping_enabled);
	}

	public void Write_pagebox(BryWtr bfr, Xoa_page page) {
		Xoctg_pagebox_itm[] itms = Get_catlinks_by_page(page);
		if (itms.length > 0) {
			this.Write_pagebox(bfr, page, itms);
		}
	}

	public void Write_pagebox(BryWtr bfr, Xoa_page page, Xoctg_pagebox_itm[] pagebox_itms) {
		try {
			if (grouping_enabled)
				double_box.Write_pagebox(bfr, pagebox_itms);
			else
				single_box.Write_pagebox(bfr, pagebox_itms);
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to write pagebox categories; page=~{0} err=~{1}", page.Url_bry_safe(), ErrUtl.ToStrLog(e));
		}
	}

	private Xoctg_pagebox_itm[] Get_catlinks_by_page(Xoa_page page) {
		// NOTE: db load is necessary b/c page.Wtxt().Ctgs__len() is insufficient
		// html_dbs will always be 0 since they do not parse ctgs;
		// wtxt_dbs may have page.Wtxt().Ctgs__len() > 0 but these items don't have Id / Hidden
		Xoctg_pagebox_hash hash = new Xoctg_pagebox_hash();
		Xoctg_pagebox_loader select_cbk = new Xoctg_pagebox_loader(hash, page.Url_bry_safe());

		// get cat_db_id from page
		Xowd_page_itm pageRow = new Xowd_page_itm();
		boolean exists = wiki.Data__core_mgr().Tbl__page().Select_by_ttl(pageRow, page.Ttl().Ns(), page.Ttl().Page_db());
		int cat_db_id = pageRow.Cat_db_id();
		if (exists && cat_db_id != -1) {// note that wtxt_dbs can have 0 ctgs but will have cat_db_id == -1
			Xow_db_file cat_link_db = wiki.Data__core_mgr().Dbs__get_by_id_or_null(cat_db_id);
			if (cat_link_db != null && Io_mgr.Instance.ExistsFil(cat_link_db.Url())) { // make sure cat_db_id exists
				select_cbk.Select_catlinks_by_page(wiki, cat_link_db.Conn(), hash, pageRow.Id());
			}
		}

		// get hidden
		int hash_len = hash.Len();
		if (hash_len > 0) {	// note that html_dbs may have 0 ctgs in cat_db; don't bother checking cat_core
			Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(wiki.Data__core_mgr());
			cat_core_tbl.Select_by_cat_id_many(select_cbk);
		}
		return hash.To_ary_and_clear();
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__grouping_enabled))			this.grouping_enabled = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Cfg__grouping_enabled = "xowa.addon.category.pagebox.grouping_enabled";
}
