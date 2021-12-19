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
package gplx.dbs.metas.parsers; import gplx.dbs.*; import gplx.dbs.metas.*;
import gplx.core.btries.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
public class Dbmeta_parser__idx {
	private final Sql_bry_rdr rdr = new Sql_bry_rdr();
	private final List_adp tmp_list = List_adp_.New();
	public Dbmeta_idx_itm Parse(byte[] src) {
		rdr.InitByPage(BryUtl.Empty, src, src.length);
		rdr.SkipWs().ChkTrieVal(trie, Tid__create);
		boolean unique = false;
		byte tid = rdr.SkipWs().ChkOr(trie, ByteUtl.MaxValue127);
		switch (tid) {
			case Tid__index:	break;
			case Tid__unique:	rdr.SkipWs().ChkTrieVal(trie, Tid__index); unique = true; break;
			default:			throw ErrUtl.NewArgs("index parse failed; 'CREATE' should be followed by 'INDEX' or 'UNIQUE'", "src", src);
		}
		byte[] idx_name = rdr.Read_sql_identifier();
		rdr.SkipWs().ChkTrieVal(trie, Tid__on);
		byte[] tbl_name = rdr.Read_sql_identifier();
		rdr.SkipWs().Chk(AsciiByte.ParenBgn);
		while (true) {
			byte[] fld_bry = rdr.Read_sql_identifier(); if (fld_bry == null) throw ErrUtl.NewArgs("index parse failed; index field is not an identifier", "src", src);
			// TODO_OLD: check for ASC / DESC
			Dbmeta_idx_fld fld_itm = new Dbmeta_idx_fld(StringUtl.NewU8(fld_bry), Dbmeta_idx_fld.Sort_tid__none);
			tmp_list.Add(fld_itm);
			byte sym = rdr.SkipWs().ReadByte();
			if (sym == AsciiByte.ParenEnd) break;
		}
		return new Dbmeta_idx_itm(unique, StringUtl.NewU8(tbl_name), StringUtl.NewU8(idx_name), (Dbmeta_idx_fld[])tmp_list.ToAryAndClear(Dbmeta_idx_fld.class));
	}
	private static final byte Tid__create = 0, Tid__unique = 1, Tid__index = 2, Tid__on = 3;
	private static final byte[]
	  Bry__create	= BryUtl.NewA7("create")
	, Bry__unique	= BryUtl.NewA7("unique")
	, Bry__index	= BryUtl.NewA7("index")
	, Bry__on		= BryUtl.NewA7("on");
	private static final Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(Bry__create	, Tid__create)
	.Add_bry_byte(Bry__unique	, Tid__unique)
	.Add_bry_byte(Bry__index	, Tid__index)
	.Add_bry_byte(Bry__on		, Tid__on);
}
