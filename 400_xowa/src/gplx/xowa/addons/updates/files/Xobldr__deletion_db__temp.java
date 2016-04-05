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
package gplx.xowa.addons.updates.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.xowa.files.*;
public class Xobldr__deletion_db__temp extends Xob_cmd__base {
	public Xobldr__deletion_db__temp(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		wiki.Init_assert();
		gplx.xowa.wikis.data.tbls.Xowd_page_tbl page_tbl = wiki.Data__core_mgr().Db__core().Tbl__page();
		Db_conn conn = page_tbl.conn;
		Db_rdr rdr = conn.Exec_rdr("SELECT page_id, page_title FROM page WHERE page_namespace = 0;");
		Io_url html_dir = wiki.Fsys_mgr().Root_dir().GenSubDir("html");
		int count = 0;
		while (rdr.Move_next()) {
			String page_ttl = rdr.Read_str("page_title");
			Xoa_ttl ttl = wiki.Ttl_parse(Bry_.new_u8(page_ttl));
			Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(wiki.Utl__url_parser().Parse(Bry_.new_u8(page_ttl)), ttl);
			wiki.Parser_mgr().Parse(page, true);
			page.Wikie().Html_mgr().Page_wtr_mgr().Page_read_fmtr().Fmt_("<html><head></head><body>~{page_data}</body></html>");
			byte[] html_src = page.Wikie().Html_mgr().Page_wtr_mgr().Gen(page, gplx.xowa.wikis.pages.Xopg_page_.Tid_read);	// NOTE: must use wiki of page, not of owner tab; DATE:2015-03-05
			Io_mgr.Instance.SaveFilBry(Io_url_.new_fil_(html_dir.Gen_sub_path_for_os(page_ttl) + ".html"), html_src);
			if (++count > 10) break;
		}
	}

	public static final String BLDR_CMD_KEY = "file.deletion_db.temp";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__deletion_db__temp(null, null);
	@Override public Xob_cmd Cmd_new(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__deletion_db__temp(bldr, wiki);}
}
