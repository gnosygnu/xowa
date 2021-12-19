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
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.BrySplit;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.IntUtl;
class Xosql_tbl_parser {
	public Ordered_hash Parse(byte[] raw) {
		Ordered_hash rv = Ordered_hash_.New_bry();
		Parse_flds(rv, Extract_flds(raw));
		return rv;
	}
	private void Parse_flds(Ordered_hash rv, byte[] raw) {
		byte[][] lines = BrySplit.Split(raw, AsciiByte.Nl);
		int lines_len = lines.length;
		int fld_idx = 0;
		for (int i = 0; i < lines_len; i++) {
			byte[] line = lines[i];
			// get fld bgn / end; EX: "`fld_1`"
			int bgn = BryFind.FindFwd(line, AsciiByte.Tick); if (bgn == BryFind.NotFound) continue;		// skip blank lines
			bgn += IntUtl.Offset1;
			int end = BryFind.FindFwd(line, AsciiByte.Tick, bgn); if (end == BryFind.NotFound) continue;	// skip blank lines

			// add fld
			byte[] key = BryLni.Mid(line, bgn, end);
			rv.Add(key, new Xosql_fld_itm(IntUtl.MaxValue, key, fld_idx++));
		}
	}
	public byte[] Extract_flds(byte[] raw) {	// NOTE: very dependent on MySQL dump formatter
		// get bgn of flds; assume after "CREATE TABLE"
		int bgn = BryFind.FindFwd(raw, Tkn__create_table);	if (bgn == BryFind.NotFound) throw ErrUtl.NewArgs("could not find 'CREATE TABLE'");
		bgn = BryFind.FindFwd(raw, AsciiByte.Nl, bgn);		if (bgn == BryFind.NotFound) throw ErrUtl.NewArgs("could not find new line after 'CREATE TABLE'");
		bgn += 1;	// position after \n

		// get end of flds; more involved, as need to find last field before indexes
		// first, get absolute end; don't want to pick up "PRIMARY KEY" in data; EX:en.b:categorylinks.sql DATE:2016-10-17
		int end = BryFind.FindFwd(raw, Tkn__engine);			if (end == BryFind.NotFound) throw ErrUtl.NewArgs("could not find ') ENGINE'");

		// now look for "UNIQUE KEY", "KEY", "PRIMARY KEY"
		int key_idx  = BryFind.FindFwdOr(raw, Tkn__key , bgn, end, IntUtl.MaxValue31);
		int pkey_idx = BryFind.FindFwdOr(raw, Tkn__pkey, bgn, end, IntUtl.MaxValue31);
		int ukey_idx = BryFind.FindFwdOr(raw, Tkn__ukey, bgn, end, IntUtl.MaxValue31);

		// get min; fail if none found
		int rv = IntUtl.MinMany(key_idx, pkey_idx, ukey_idx);
		if (rv == IntUtl.MaxValue31) throw ErrUtl.NewArgs("could not find 'PRIMARY KEY', 'UNIQUE KEY', or 'KEY' in SQL", "raw", BryLni.Mid(raw, bgn, end));
		return BryLni.Mid(raw, bgn, rv);
	}
	private final byte[]
	  Tkn__create_table	= BryUtl.NewA7("CREATE TABLE")
	, Tkn__ukey			= BryUtl.NewA7("\n  UNIQUE KEY")
	, Tkn__pkey			= BryUtl.NewA7("\n  PRIMARY KEY")
	, Tkn__key			= BryUtl.NewA7("\n  KEY ")
	, Tkn__engine		= BryUtl.NewA7("\n) ENGINE")
	;
}
