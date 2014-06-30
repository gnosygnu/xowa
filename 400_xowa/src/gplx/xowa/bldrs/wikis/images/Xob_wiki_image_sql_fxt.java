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
package gplx.xowa.bldrs.wikis.images; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wikis.*;
public class Xob_wiki_image_sql_fxt {
	Xob_wiki_image_sql cmd;
	private Db_mgr_fxt fxt = new Db_mgr_fxt();
	public void Init() {
		fxt.Ctor_fsys();
		fxt.Init_db_sqlite();
	}
	public void Term() {
		fxt.Rls();
	}
	public Xob_wiki_image_sql Run(String raw_sql) {return Run(raw_sql, new Xob_wiki_image_sql(fxt.Bldr(), fxt.Wiki()));}
	public Xob_wiki_image_sql Run(String raw_sql, Xob_wiki_image_sql rv) {
		this.cmd = rv;
		cmd.Make_fil_len_(Io_mgr.Len_kb);
		rv.Parser().Src_len_(Io_mgr.Len_kb);
		Io_url src_fil = Io_url_.mem_fil_("mem/temp/sql_dump.sql");
		Io_mgr._.SaveFilStr(src_fil, raw_sql);

		rv.Src_fil_(src_fil);
		Xobc_base_fxt.Run_cmd(fxt.Bldr(), rv);
		return rv;
	}
//		public void Tst(String expd) {
//			Io_url trg_fil = Io_url_.mem_fil_("mem/temp/sql_dump.sql");//cmd.Make_url_gen().Prv_urls()[0];
//			String actl = Io_mgr._.LoadFilStr(trg_fil);
//			Tfds.Eq_str_lines(expd, actl);
//		}
}
