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
package gplx.dbs.diffs.builds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
class Gfdb_diff_rdr_comparer {
	private Db_rdr old_rdr, new_rdr;
	private boolean old_rdr_move, new_rdr_move;
	private boolean old_rdr_done, new_rdr_done;
	private Dbmeta_fld_itm[] key_flds; private int key_flds_len;
	public void Init_rdrs(Gfdb_diff_tbl tbl, Db_rdr old_rdr, Db_rdr new_rdr) {
		this.old_rdr = old_rdr; this.new_rdr = new_rdr;
		this.old_rdr_move = new_rdr_move = Bool_.Y;
		this.old_rdr_done = new_rdr_done = Bool_.N;
		this.key_flds = tbl.Keys; key_flds_len = key_flds.length;
	}
	public int Compare() {
		if (old_rdr_move) {
			old_rdr_move = old_rdr.Move_next();
			if (!old_rdr_move) old_rdr_done = true;
		}
		if (new_rdr_move) {
			new_rdr_move = new_rdr.Move_next();
			if (!new_rdr_move) new_rdr_done = true;
		}
		if		(old_rdr_done && new_rdr_done)	return Gfdb_diff_rdr_comparer.Rslt__done;
		else if	(old_rdr_done)					{new_rdr_move = true; return Gfdb_diff_rdr_comparer.Rslt__old_missing;}
		else if (new_rdr_done)					{old_rdr_move = true; return Gfdb_diff_rdr_comparer.Rslt__new_missing;}
		else {
			int comp = Gfdb_rdr_utl_.Compare(key_flds, key_flds_len, old_rdr, new_rdr);
			switch (comp) {
				case CompareAble_.Same:			// old == cur; move both
					old_rdr_move = new_rdr_move = true;
					return Gfdb_diff_rdr_comparer.Rslt__same;
				case CompareAble_.Less:			// old < cur; EX: old == 2; cur == 3
					old_rdr_move = true;
					new_rdr_move = false;
					return Gfdb_diff_rdr_comparer.Rslt__new_missing;
				case CompareAble_.More:			// old > cur; EX: old == 4; cur == 3
					old_rdr_move = false;
					new_rdr_move = true;
					return Gfdb_diff_rdr_comparer.Rslt__old_missing;
				default: throw Err_.new_unhandled(comp);
			}
		}
	}
	public static final int 
	  Rslt__same		= 0
	, Rslt__old_missing	= 1
	, Rslt__new_missing	= 2
	, Rslt__done		= 3
	;
}
