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
package gplx.dbs.metas.parsers; import gplx.*; import gplx.dbs.*; import gplx.dbs.metas.*;
import gplx.core.brys.*; import gplx.core.btries.*;
public class Dbmeta_parser__idx {
	private final    Sql_bry_rdr rdr = new Sql_bry_rdr();
	private final    List_adp tmp_list = List_adp_.New();
	public Dbmeta_idx_itm Parse(byte[] src) {
		rdr.Init_by_page(Bry_.Empty, src, src.length);
		rdr.Skip_ws().Chk_trie_val(trie, Tid__create);
		boolean unique = false;
		byte tid = rdr.Skip_ws().Chk_or(trie, Byte_.Max_value_127);
		switch (tid) {
			case Tid__index:	break;
			case Tid__unique:	rdr.Skip_ws().Chk_trie_val(trie, Tid__index); unique = true; break;
			default:			throw Err_.new_("db", "index parse failed; 'CREATE' should be followed by 'INDEX' or 'UNIQUE'", "src", src);
		}
		byte[] idx_name = rdr.Read_sql_identifier();
		rdr.Skip_ws().Chk_trie_val(trie, Tid__on);
		byte[] tbl_name = rdr.Read_sql_identifier();
		rdr.Skip_ws().Chk(Byte_ascii.Paren_bgn);
		while (true) {
			byte[] fld_bry = rdr.Read_sql_identifier(); if (fld_bry == null) throw Err_.new_("db", "index parse failed; index field is not an identifier", "src", src);
			// TODO_OLD: check for ASC / DESC
			Dbmeta_idx_fld fld_itm = new Dbmeta_idx_fld(String_.new_u8(fld_bry), Dbmeta_idx_fld.Sort_tid__none);
			tmp_list.Add(fld_itm);
			byte sym = rdr.Skip_ws().Read_byte();
			if (sym == Byte_ascii.Paren_end) break;
		}
		return new Dbmeta_idx_itm(unique, String_.new_u8(tbl_name), String_.new_u8(idx_name), (Dbmeta_idx_fld[])tmp_list.To_ary_and_clear(Dbmeta_idx_fld.class));
	}
	private static final byte Tid__create = 0, Tid__unique = 1, Tid__index = 2, Tid__on = 3;
	private static final    byte[]
	  Bry__create	= Bry_.new_a7("create")
	, Bry__unique	= Bry_.new_a7("unique")
	, Bry__index	= Bry_.new_a7("index")
	, Bry__on		= Bry_.new_a7("on");
	private static final    Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(Bry__create	, Tid__create)
	.Add_bry_byte(Bry__unique	, Tid__unique)
	.Add_bry_byte(Bry__index	, Tid__index)
	.Add_bry_byte(Bry__on		, Tid__on);
}
