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
package gplx.xowa.htmls.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.hzips.stats.*; import gplx.xowa.htmls.heads.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.pages.*;
public class Xow_hdump_mgr__save {
	private final Xow_wiki wiki; private final Xow_hzip_mgr hzip_mgr;
	private final Xoh_page tmp_hpg; private final Bry_bfr tmp_bfr; private boolean html_db_is_new = false;
	public Xow_hdump_mgr__save(Xow_wiki wiki, Xow_hzip_mgr hzip_mgr, Xoh_page tmp_hpg, Bry_bfr tmp_bfr) {
		this.wiki = wiki; this.hzip_mgr = hzip_mgr;
		this.tmp_hpg = tmp_hpg; this.tmp_bfr = tmp_bfr;
	}
	public int Save(Xoae_page page) {
		synchronized (tmp_hpg) {
			Make_body_as_hswap(page);
			tmp_hpg.Ctor_by_page(tmp_bfr, page);
			this.html_db_is_new = false;
			Xowd_db_file html_db = Get_html_db(page);
			return Save(tmp_hpg, html_db, html_db_is_new);
		}
	}
	public int Save(Xoh_page hpg, Xowd_db_file html_db, boolean insert) {
		byte db_body_flag = hzip_mgr.Body_flag();
		byte[] db_body = hzip_mgr.Write(hpg.Url_bry_safe(), db_body_flag, hpg.Body());
		if (insert)
			html_db.Tbl__html_page().Insert(hpg, db_body_flag, db_body);
		else
			html_db.Tbl__html_page().Update(hpg, db_body_flag, db_body);
		return db_body.length;
	}
	public void Make_body_as_hswap(Xoae_page page) {
		page.File_queue().Clear();																	// need to reset uid to 0, else xowa_file_# will keep incrementing upwards
		wiki.Html__wtr_mgr().Wkr(Xopg_page_.Tid_read).Write_body(tmp_bfr, Xoh_wtr_ctx.Hdump, page); // save as hdump_fmt
		page.Hdump_data().Body_(tmp_bfr.To_bry_and_clear());
	}
	private Xowd_db_file Get_html_db(Xoae_page page) {
		Xowd_db_file rv = Xowd_db_file.Null;
		Xowd_db_mgr core_data_mgr = wiki.Data__core_mgr();
		int html_db_id = page.Revision_data().Html_db_id();
		if (html_db_id == -1) {
			html_db_is_new = true;
			rv = core_data_mgr.Db__html();
			if (rv == null) {					
				rv = core_data_mgr.Dbs__make_by_tid(Xowd_db_file_.Tid_html_data);
				rv.Tbl__html_redlink().Create_tbl();
			}					
			html_db_id = rv.Id();
			page.Revision_data().Html_db_id_(html_db_id);
			core_data_mgr.Tbl__page().Update__html_db_id(page.Revision_data().Id(), html_db_id);
		}
		else {
			rv = core_data_mgr.Dbs__get_at(html_db_id);
		}
		return rv;
	}
}
