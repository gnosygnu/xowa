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
package gplx.xowa.bldrs.sql_dumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.ios.*;
class Xosql_tbl_parser {
	public Ordered_hash Parse(byte[] raw) {
		Ordered_hash rv = Ordered_hash_.New_bry();
		Parse_flds(rv, Extract_flds(raw));
		return rv;
	}
	private void Parse_flds(Ordered_hash rv, byte[] raw) {
		byte[][] lines = Bry_split_.Split(raw, Byte_ascii.Nl);
		int lines_len = lines.length;
		int fld_idx = 0;
		for (int i = 0; i < lines_len; i++) {
			byte[] line = lines[i];
			// get fld bgn / end; EX: "`fld_1`"
			int bgn = Bry_find_.Find_fwd(line, Byte_ascii.Tick); if (bgn == Bry_find_.Not_found) continue;		// skip blank lines
			bgn += Int_.Const_position_after_char;
			int end = Bry_find_.Find_fwd(line, Byte_ascii.Tick, bgn); if (end == Bry_find_.Not_found) continue;	// skip blank lines

			// add fld
			byte[] key = Bry_.Mid(line, bgn, end);
			rv.Add(key, new Xosql_fld_itm(Int_.Max_value, key, fld_idx++));
		}
	}
	public byte[] Extract_flds(byte[] raw) {	// NOTE: very dependent on MySQL dump formatter
		// get bgn of flds; assume after "CREATE TABLE"
		int bgn = Bry_find_.Find_fwd(raw, Tkn__create_table);	if (bgn == Bry_find_.Not_found) throw Err_.new_wo_type("could not find 'CREATE TABLE'");
		bgn = Bry_find_.Find_fwd(raw, Byte_ascii.Nl, bgn);		if (bgn == Bry_find_.Not_found) throw Err_.new_wo_type("could not find new line after 'CREATE TABLE'");
		bgn += 1;	// position after \n

		// get end of flds; more involved, as need to find last field before indexes
		// first, get absolute end; don't want to pick up "PRIMARY KEY" in data; EX:en.b:categorylinks.sql DATE:2016-10-17
		int end = Bry_find_.Find_fwd(raw, Tkn__engine);			if (end == Bry_find_.Not_found) throw Err_.new_wo_type("could not find ') ENGINE'");

		// now look for "UNIQUE KEY", "KEY", "PRIMARY KEY"
		int key_idx  = Bry_find_.Find_fwd_or(raw, Tkn__key , bgn, end, Int_.Max_value__31);
		int pkey_idx = Bry_find_.Find_fwd_or(raw, Tkn__pkey, bgn, end, Int_.Max_value__31);
		int ukey_idx = Bry_find_.Find_fwd_or(raw, Tkn__ukey, bgn, end, Int_.Max_value__31);

		// get min; fail if none found
		int rv = Int_.Min_many(key_idx, pkey_idx, ukey_idx);
		if (rv == Int_.Max_value__31) throw Err_.new_wo_type("could not find 'PRIMARY KEY', 'UNIQUE KEY', or 'KEY' in SQL", "raw", Bry_.Mid(raw, bgn, end));
		return Bry_.Mid(raw, bgn, rv);
	}
	private final    byte[] 
	  Tkn__create_table	= Bry_.new_a7("CREATE TABLE")
	, Tkn__ukey			= Bry_.new_a7("\n  UNIQUE KEY")
	, Tkn__pkey			= Bry_.new_a7("\n  PRIMARY KEY")
	, Tkn__key			= Bry_.new_a7("\n  KEY ")
	, Tkn__engine		= Bry_.new_a7("\n) ENGINE")
	;
}
