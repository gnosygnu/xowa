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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import gplx.dbs.*; import gplx.xowa.addons.wikis.ctgs.dbs.*; import gplx.xowa.wikis.data.tbls.*;	
class Xoctg_pagebox_loader implements Select_in_cbk {
	private final    Xoctg_pagebox_hash hash; private final    byte[] page_url;
	public Xoctg_pagebox_loader(Xoctg_pagebox_hash hash, byte[] page_url) {
		this.hash = hash; this.page_url = page_url;
	}
	public int Hash_max() {return hash.Len();}
	public void Write_sql(Bry_bfr bfr, int idx) {
		Xoctg_pagebox_itm page = (Xoctg_pagebox_itm)hash.Get_at(idx);
		bfr.Add_int_variable(page.Id());
	}
	public void Read_data(Db_rdr rdr) {
		int cat_id = rdr.Read_int("cat_id");
		Xoctg_pagebox_itm page = (Xoctg_pagebox_itm)hash.Get_by_id(cat_id);
		if (page == null) {// unlikely, but possible for itms to exist in cat_links, but not in cat_core; log and return;
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cat_id in cat_link but not in cat_core; page=~{0} cat_id=~{1}", page_url, cat_id);
		}
		page.Load_by_cat_core(rdr.Read_bool_by_byte("cat_hidden"), rdr.Read_int("cat_pages"), rdr.Read_int("cat_subcats"), rdr.Read_int("cat_files"));
	}
	public void Select_catlinks_by_page(Xow_wiki wiki, Db_conn cat_link_conn, Xoctg_pagebox_hash hash, int page_id) {
		// init
		Db_attach_mgr attach_mgr = new Db_attach_mgr(cat_link_conn, new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Conn()));
		String sql = String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "SELECT  cl.cl_to_id"
		, ",       cl.cl_timestamp_unix"
		, ",       p.page_namespace"
		, ",       p.page_title"
		, "FROM    cat_link cl"
		, "        JOIN <page_db>page p ON cl.cl_to_id = p.page_id"
		, "WHERE   cl.cl_from = " + Int_.To_str(page_id)
		);
		sql = attach_mgr.Resolve_sql(sql);

		// select
		attach_mgr.Attach();
		Db_rdr rdr = Db_rdr_.Empty;
		try {
			rdr = cat_link_conn.Stmt_sql(sql).Exec_select__rls_auto();
			while (rdr.Move_next()) {
				Xoa_ttl ttl = wiki.Ttl_parse(rdr.Read_int("page_namespace"), rdr.Read_bry_by_str("page_title"));
				// check if ttl exists already in hash; add it if not; check is not needed now b/c html_dbs will never put itms in hash, but may need in future if merging "wtxt" and "ctgs_dbs"
				Xoctg_pagebox_itm itm = (Xoctg_pagebox_itm)hash.Get_by_ttl(ttl.Full_db());
				if (itm == null)
					itm = hash.Add_by_ttl(ttl);
				itm.Load_by_db(rdr.Read_int("cl_to_id"), DateAdp_.unixtime_utc_ms_(rdr.Read_long("cl_timestamp_unix")));
			}
		}
		catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "category.pagebox: fatal error while retrieving categories; page=~{0} err=~{1}", page_id, Err_.Message_gplx_log(e));
		}
		finally {
			rdr.Rls();
			attach_mgr.Detach();
		}

		// hash items by id
		hash.Sort_and_fill_ids();
	}
}
