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
package gplx.xowa.addons.bldrs.mass_parses.parses.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.dbs.*;
import gplx.core.ios.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xomp_text_db_loader {
	private final    Xow_wiki wiki;
	private final    Ordered_hash text_db_hash = Ordered_hash_.New();
	private final    Io_stream_zip_mgr zip_mgr;
	public Xomp_text_db_loader(Xow_wiki wiki) {
		this.wiki = wiki;
		this.zip_mgr = wiki.Utl__zip_mgr();
	}
	public void Add(int text_db_id, Xowd_text_bry_owner ppg) {
		Xomp_text_db_itm itm = (Xomp_text_db_itm)text_db_hash.Get_by(text_db_id);
		if (itm == null) {
			itm = new Xomp_text_db_itm(text_db_id);
			text_db_hash.Add(text_db_id, itm);
		}
		itm.Page_list().Add(ppg);
	}
	public void Load() {
		int text_db_hash_len = text_db_hash.Len();
		for (int i = 0; i < text_db_hash_len; ++i) {
			Xomp_text_db_itm itm = (Xomp_text_db_itm)text_db_hash.Get_at(i);
			Load_list(itm.Text_db_id(), itm.Page_list());
		}
	}
	private void Load_list(int text_db_id, List_adp list) {
		int list_len = list.Len();
		int batch_idx = 0;
		Bry_bfr bry = Bry_bfr_.New();
		Ordered_hash page_hash = Ordered_hash_.New();
		byte zip_tid = wiki.Data__core_mgr().Props().Zip_tid_text();
		for (int i = 0; i < list_len; ++i) {
			if (batch_idx == 0) {
				page_hash.Clear();
				bry.Add_str_a7("SELECT page_id, text_data FROM text WHERE page_id IN (");
			}

			// build WHERE IN for page_ids; EX: "1, 2, 3, 4"
			Xowd_text_bry_owner ppg = (Xowd_text_bry_owner)list.Get_at(i);
			int page_id = ppg.Page_id();
			if (batch_idx != 0) bry.Add_byte_comma();
			bry.Add_int_variable(page_id);
			page_hash.Add(page_id, ppg);
			++batch_idx;

			// load if 255 in list, or last
			if (	batch_idx % 255 == 0 
				||	i == list_len - 1) {
				bry.Add_byte(Byte_ascii.Paren_end);
				Load_from_text_db(page_hash, zip_tid, text_db_id, bry.To_str_and_clear());
				batch_idx = 0;
			}
		}
	}
	private void Load_from_text_db(Ordered_hash page_hash, byte zip_tid, int text_db_id, String sql) {
		Db_conn text_conn = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(text_db_id).Conn();
		Db_rdr rdr = text_conn.Stmt_sql(sql).Exec_select__rls_auto();	// ANSI.Y
		try {
			while (rdr.Move_next()) {
				int page_id = rdr.Read_int("page_id");
				byte[] text_data = rdr.Read_bry("text_data");
				text_data = zip_mgr.Unzip(zip_tid, text_data);
				Xowd_text_bry_owner ppg = (Xowd_text_bry_owner)page_hash.Get_by(page_id);
				ppg.Set_text_bry_by_db(text_data);
			}
		}
		finally {
			rdr.Rls();
			// text_conn.Rls_conn(); // TOMBSTONE: causes strange errors in tables; DATE:2016-07-06
		}
	}
}
class Xomp_text_db_itm {
	public Xomp_text_db_itm(int text_db_id) {this.text_db_id = text_db_id;}
	public int Text_db_id() {return text_db_id;} private final    int text_db_id;
	public List_adp Page_list() {return page_list;} private final    List_adp page_list = List_adp_.New();
}
