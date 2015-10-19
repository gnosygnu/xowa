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
package gplx.xowa.bldrs.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.ios.*;
public class Sql_file_parser_tst {
	Sql_file_parser_fxt fxt = new Sql_file_parser_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void One() {
		fxt.Init_flds_req_idx_(2, 1).Test_parse("INSERT INTO 'tbl_1' VALUES (1,2);", "2|\n");
	}
	@Test  public void Many() {
		fxt.Init_flds_req_idx_(2, 1).Test_parse("INSERT INTO 'tbl_1' VALUES (1,2),(3,4),(5,6);", "2|\n4|\n6|\n");
	}
	@Test  public void Quote_basic() {
		fxt.Init_flds_req_idx_(3, 1, 2).Test_parse("INSERT INTO 'tbl_1' VALUES (1,'a','b');", "a|b|\n");
	}
	@Test  public void Escape_pipe() {
		fxt.Init_flds_req_idx_(3, 1, 2).Test_parse("INSERT INTO 'tbl_1' VALUES (1,'a|b','c');", "a~pb|c|\n");
	}
	@Test  public void Escape_nl() {
		fxt.Init_flds_req_idx_(3, 1, 2).Test_parse("INSERT INTO 'tbl_1' VALUES (1,'a\\nb','c');", "a~nb|c|\n");
	}
	@Test  public void Escape_tab() {
		fxt.Init_flds_req_idx_(3, 1, 2).Test_parse("INSERT INTO 'tbl_1' VALUES (1,'a\\tb','c');", "a~tb|c|\n");
	}
	@Test  public void Escape_backslash() {
		fxt.Init_flds_req_idx_(3, 1, 2).Test_parse("INSERT INTO 'tbl_1' VALUES (1,'a\\\\b','c');", "a\\b|c|\n");
	}
	@Test  public void Escape_quote() {
		fxt.Init_flds_req_idx_(3, 1, 2).Test_parse("INSERT INTO 'tbl_1' VALUES (1,'a\"b','c');", "a\"b|c|\n");
	}
	@Test  public void Fld_paren_end() {
		fxt.Init_flds_req_idx_(3, 1, 2).Test_parse("INSERT INTO 'tbl_1' VALUES (1,'Психостимуляторы_(лекарственные_средства)','c');", "Психостимуляторы_(лекарственные_средства)|c|\n");
	}
	@Test  public void Insert_multiple() {
		fxt.Init_flds_req_idx_(2, 1).Test_parse("INSERT INTO 'tbl_1' VALUES (1,2);INSERT INTO 'tbl_1' VALUES (3,4)", "2|\n4|\n");
	}
	@Test  public void Cmds() {
		Sql_file_parser_cmd_max_len cmd = new Sql_file_parser_cmd_max_len();
		fxt.Init_flds_req_idx_(2, 1).Init_cmd_(cmd).Test_parse("INSERT INTO 'tbl_1' VALUES (1,'a'),(3,'abc');", "a|\nabc|\n");
		Tfds.Eq(3, cmd.Max_len());
	}
}
class Sql_file_parser_fxt {
	Sql_file_parser parser = new Sql_file_parser().Src_len_(Io_mgr.Len_kb).Trg_len_(Io_mgr.Len_kb);
	public Sql_file_parser_fxt Clear() {Io_mgr.Instance.InitEngine_mem(); return this;}
	public Sql_file_parser_fxt Init_flds_req_idx_(int flds_all_len, int... idxs) {parser.Flds_req_idx_(flds_all_len, idxs); return this;}
	public Sql_file_parser_fxt Init_cmd_(Sql_file_parser_cmd cmd) {parser.Fld_cmd_(cmd); return this;}
	public void Test_parse(String raw_str, String expd) {
		Io_url src_fil = Io_url_.new_fil_("mem/test.sql");
		Io_mgr.Instance.SaveFilBry(src_fil, Bry_.new_u8(raw_str));
		Io_url trg_fil = Io_url_.new_fil_("mem/test.csv");
		parser.Src_fil_(src_fil).Trg_fil_gen_(Io_url_gen_.fil_(trg_fil));
		parser.Parse(Gfo_usr_dlg_.Test());
		byte[] actl = Io_mgr.Instance.LoadFilBry(trg_fil);
		Tfds.Eq(expd, String_.new_u8(actl));
	}	
}
