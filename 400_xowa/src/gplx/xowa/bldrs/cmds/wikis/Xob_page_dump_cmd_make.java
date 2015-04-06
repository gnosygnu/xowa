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
package gplx.xowa.bldrs.cmds.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.dbs.*;
public class Xob_page_dump_cmd_make extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_page_dump_cmd_make(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_wiki_page_dump_make;}
	public void Cmd_run() {
		wiki.Init_assert();
		Xowd_db_mgr db_mgr = wiki.Data_mgr__core_mgr();
		Io_url page_db_url = db_mgr.Db__core().Url();
		int len = db_mgr.Dbs__len();
		for (int i = 0; i < len; i++) {
			Xowd_db_file db_file = db_mgr.Dbs__get_at(i);
			switch (db_file.Tid()) {
				case Xowd_db_file_.Tid_wiki_solo:
				case Xowd_db_file_.Tid_text_solo:
				case Xowd_db_file_.Tid_text:
					Xobd_page_dump_tbl tbl = new Xobd_page_dump_tbl(db_file.Conn());
					tbl.Create_data(page_db_url, db_file.Id());
					break;
			}
		}
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
}
