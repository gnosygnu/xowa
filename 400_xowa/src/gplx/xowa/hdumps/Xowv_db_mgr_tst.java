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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.xowa.files.*; import gplx.xowa.dbs.*;
import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.dbs.*; import gplx.xowa.hdumps.pages.*; import gplx.xowa.xtns.hieros.*; import gplx.xowa.xtns.gallery.*;
public class Xowv_db_mgr_tst {
	@Before public void init() {} private Xowv_db_mgr_fxt fxt = new Xowv_db_mgr_fxt();
	@Test   public void Basic() {
		fxt.Init_rdr(fxt.Make_row(0, "mem/a", 2),fxt.Make_row(0, "mem/a", 2), fxt.Make_row(0, "mem/a", 2));
//			fxt.Exec_open_core();
//			fxt.Test_dbs(fxt.Make_db(0, "mem/a", 0), fxt.Make_db(1, "mem/2", 2), fxt.Make_db("mem/3, 3"));
	}
}
class Xowv_db_mgr_fxt {
//		private Xoav_app app; private Xowv_wiki wiki;
	public void Init_rdr(Mem_db_row... rows) {
//			Mem_db_rdr rdr = Mem_db_rdr.new_by_rows(rows);
//			app = new Xoav_app(null, nul, null);
//			Mem_db_engine engine = new Mem_db_engine();
//			engine.Pending_rdrs_add(rdr);
//			app.Db_mgr().Engines_add(engine);
	}
	public Mem_db_row Make_row(Object... vals) {return new Mem_db_row(vals);}
	public void Exec_open_core() {
//			app.Db_mgr().Get(wiki.Db_mgr().Key__core());
	}
	public void Test_dbs(Xodb_file[] expd) {
//			Tfds.Eq(expd, wiki.Db_mgr().Files());
	}
}
