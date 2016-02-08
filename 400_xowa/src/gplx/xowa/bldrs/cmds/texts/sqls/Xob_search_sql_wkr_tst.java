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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import org.junit.*; import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_search_sql_wkr_tst {
	private final Xob_search_sql_wkr_fxt fxt = new Xob_search_sql_wkr_fxt();
	@Test   public void Basic() {
		fxt.Exec__sort
		( "a|!!!!#"
		, "a|!!!!$"
		, "b|!!!!#"
		, "b|!!!!$"
		, "b|!!!!%"
		);
		fxt.Test__search_word
		( "1|a|2"
		, "2|b|3"
		);
		fxt.Test__search_link
		( "1|2"
		, "1|3"
		, "2|2"
		, "2|3"
		, "2|4"
		);
	}
}
class Xob_search_sql_wkr_fxt {
	private final Xob_search_sql_wkr wkr;
	private final Xowe_wiki wiki;
	private final Bry_bfr tmp_bfr = Bry_bfr.new_();
	public Xob_search_sql_wkr_fxt() {
		Xoae_app app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
		Xoa_test_.Db__init__mem(wiki);
		wkr = new Xob_search_sql_wkr(app.Bldr(), wiki);
	}
	public void Exec__sort(String... lines_str) {
		byte[][] lines = Bry_.Ary(lines_str);
		wkr.Sort_bgn();
		for (byte[] line : lines)
			wkr.Sort_do(line, 0, Bry_find_.Find_fwd(line, Byte_ascii.Pipe));
		wkr.Sort_end();
	}
	public void Test__search_word(String... lines) {
		Xowd_search_word_tbl tbl = wiki.Data__core_mgr().Db__search().Tbl__search_word();
		Tfds.Eq_str_lines(String_.Concat_lines_nl(lines), Xoa_test_.Db__print_tbl_as_str(tmp_bfr, tbl.conn, tbl.Tbl_name(), tbl.Fld_id(), tbl.Fld_text(), tbl.Fld_page_count()));
	}
	public void Test__search_link(String... lines) {
		Xowd_search_link_tbl tbl = wiki.Data__core_mgr().Db__search().Tbl__search_link();
		Tfds.Eq_str_lines(String_.Concat_lines_nl(lines), Xoa_test_.Db__print_tbl_as_str(tmp_bfr, tbl.conn, tbl.Tbl_name(), tbl.Fld_word_id(), tbl.Fld_page_id()));
	}
}
