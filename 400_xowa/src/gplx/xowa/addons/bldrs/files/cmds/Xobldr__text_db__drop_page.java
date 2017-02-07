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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.wikis.dbs.*;
import gplx.xowa.addons.bldrs.files.dbs.*;
public class Xobldr__text_db__drop_page extends Xob_cmd__base {
	public Xobldr__text_db__drop_page(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		wiki.Init_assert();
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		int len = db_mgr.Dbs__len();
		for (int i = 0; i < len; i++) {
			Xow_db_file db_file = db_mgr.Dbs__get_at(i);
			switch (db_file.Tid()) {
				case Xow_db_file_.Tid__wiki_solo:
				case Xow_db_file_.Tid__text_solo:
				case Xow_db_file_.Tid__text:
					db_file.Conn().Meta_tbl_delete(Xob_page_dump_tbl.Tbl_name);
					break;
			}
		}
	}

	public static final String BLDR_CMD_KEY = "wiki.page_dump.drop";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__text_db__drop_page(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__text_db__drop_page(bldr, wiki);}
}
