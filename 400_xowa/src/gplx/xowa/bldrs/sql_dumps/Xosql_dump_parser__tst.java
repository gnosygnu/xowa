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
package gplx.xowa.bldrs.sql_dumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.core.ios.*; import gplx.core.tests.*;
public class Xosql_dump_parser__tst {		
	private final    Xosql_dump_parser__fxt fxt = new Xosql_dump_parser__fxt();
	private static final String table_def = "\n  KEY \n) ENGINE; ";
	@Test  public void One() {
		fxt.Init(String_.Ary("c1", "c2"), "c2").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,2);", "2|");
	}
	@Test  public void Many() {
		fxt.Init(String_.Ary("c1", "c2"), "c2").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,2),(3,4),(5,6);", "2|\n4|\n6|");
	}
	@Test  public void Quote_basic() {
		fxt.Init(String_.Ary("c1", "c2", "c3"), "c2", "c3").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,'a','b');", "a|b|");
	}
	@Test  public void Escape_backslash() {
		fxt.Init(String_.Ary("c1", "c2", "c3"), "c2", "c3").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,'a\\\\b','c');", "a\\b|c|");
	}
	@Test  public void Escape_quote() {
		fxt.Init(String_.Ary("c1", "c2", "c3"), "c2", "c3").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,'a\"b','c');", "a\"b|c|");
	}
	@Test  public void Fld_paren_end() {
		fxt.Init(String_.Ary("c1", "c2", "c3"), "c2", "c3").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,'Психостимуляторы_(лекарственные_средства)','c');", "Психостимуляторы_(лекарственные_средства)|c|");
	}
	@Test  public void Insert_multiple() {
		fxt.Init(String_.Ary("c1", "c2"), "c2").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,2);INSERT INTO 'tbl_1' VALUES (3,4)", "2|\n4|");
	}
}
class Xosql_dump_parser__fxt {
	private Xosql_dump_parser parser;
	private Xosql_dump_cbk__test cbk;
	private String[] tbl_flds;
	public Xosql_dump_parser__fxt Init(String[] tbl_flds, String... cbk_flds) {
		this.tbl_flds = tbl_flds;
		this.cbk = new Xosql_dump_cbk__test();
		this.parser = new Xosql_dump_parser(cbk, cbk_flds);
		return this;
	}
	public void Test__parse(String raw_str, String expd) {
		Io_url src_fil = Io_url_.new_fil_("mem/test.sql");
		Io_mgr.Instance.SaveFilBry(src_fil, Make_dump(tbl_flds, raw_str));
		parser.Src_fil_(src_fil);
		parser.Parse(Gfo_usr_dlg_.Test());
		Gftest.Eq__str(expd, cbk.To_bry_and_clear());
	}
	private byte[] Make_dump(String[] tbl_flds, String insert) {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_a7("CREATE TABLE tbl_0 (");
		for (int i = 0; i < tbl_flds.length; ++i) {
			bfr.Add_byte_nl();
			bfr.Add_byte(Byte_ascii.Tick);
			bfr.Add_str_a7(tbl_flds[i]);
			bfr.Add_byte(Byte_ascii.Tick);
			bfr.Add_byte_comma();
		}
		bfr.Add_str_a7("\nUNIQUE KEY idx_0 (fld_0));\n");
            bfr.Add_str_u8(insert);
		return bfr.To_bry_and_clear();
	}
}
class Xosql_dump_cbk__test implements Xosql_dump_cbk {
	private int prv_idx = -1;
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Clear() {prv_idx = -1; bfr.Clear();}
	public void On_fld_done(int fld_idx, byte[] src, int val_bgn, int val_end) {
		if (fld_idx <= prv_idx) {
			if (prv_idx != -1) bfr.Add_byte_nl();
		}
		bfr.Add_mid(src, val_bgn, val_end).Add_byte_pipe();
		prv_idx = fld_idx;
	}
	public void On_row_done() {}
	public byte[] To_bry_and_clear() {return bfr.To_bry_and_clear();}
}
