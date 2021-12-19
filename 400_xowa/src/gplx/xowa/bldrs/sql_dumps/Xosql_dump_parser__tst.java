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
package gplx.xowa.bldrs.sql_dumps;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.libs.files.Io_mgr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import org.junit.*;
public class Xosql_dump_parser__tst {		
	private final Xosql_dump_parser__fxt fxt = new Xosql_dump_parser__fxt();
	private static final String table_def = "\n  KEY \n) ENGINE; ";
	@Test public void One() {
		fxt.Init(StringUtl.Ary("c1", "c2"), "c2").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,2);", "2|");
	}
	@Test public void Many() {
		fxt.Init(StringUtl.Ary("c1", "c2"), "c2").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,2),(3,4),(5,6);", "2|\n4|\n6|");
	}
	@Test public void Quote_basic() {
		fxt.Init(StringUtl.Ary("c1", "c2", "c3"), "c2", "c3").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,'a','b');", "a|b|");
	}
	@Test public void Escape_backslash() {
		fxt.Init(StringUtl.Ary("c1", "c2", "c3"), "c2", "c3").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,'a\\\\b','c');", "a\\b|c|");
	}
	@Test public void Escape_quote() {
		fxt.Init(StringUtl.Ary("c1", "c2", "c3"), "c2", "c3").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,'a\"b','c');", "a\"b|c|");
	}
	@Test public void Fld_paren_end() {
		fxt.Init(StringUtl.Ary("c1", "c2", "c3"), "c2", "c3").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,'Психостимуляторы_(лекарственные_средства)','c');", "Психостимуляторы_(лекарственные_средства)|c|");
	}
	@Test public void Insert_multiple() {
		fxt.Init(StringUtl.Ary("c1", "c2"), "c2").Test__parse(table_def + "INSERT INTO 'tbl_1' VALUES (1,2);INSERT INTO 'tbl_1' VALUES (3,4)", "2|\n4|");
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
		GfoTstr.Eq(expd, cbk.To_bry_and_clear());
	}
	private byte[] Make_dump(String[] tbl_flds, String insert) {
		BryWtr bfr = BryWtr.New();
		bfr.AddStrA7("CREATE TABLE tbl_0 (");
		for (int i = 0; i < tbl_flds.length; ++i) {
			bfr.AddByteNl();
			bfr.AddByte(AsciiByte.Tick);
			bfr.AddStrA7(tbl_flds[i]);
			bfr.AddByte(AsciiByte.Tick);
			bfr.AddByteComma();
		}
		bfr.AddStrA7("\nUNIQUE KEY idx_0 (fld_0));\n");
            bfr.AddStrU8(insert);
		return bfr.ToBryAndClear();
	}
}
class Xosql_dump_cbk__test implements Xosql_dump_cbk {
	private int prv_idx = -1;
	private final BryWtr bfr = BryWtr.New();
	public void Clear() {prv_idx = -1; bfr.Clear();}
	public void On_fld_done(int fld_idx, byte[] src, int val_bgn, int val_end) {
		if (fld_idx <= prv_idx) {
			if (prv_idx != -1) bfr.AddByteNl();
		}
		bfr.AddMid(src, val_bgn, val_end).AddBytePipe();
		prv_idx = fld_idx;
	}
	public void On_row_done() {}
	public byte[] To_bry_and_clear() {return bfr.ToBryAndClear();}
}
