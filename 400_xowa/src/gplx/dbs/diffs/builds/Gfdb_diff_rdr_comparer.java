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
package gplx.dbs.diffs.builds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
class Gfdb_diff_rdr_comparer {
	private Db_rdr lhs_rdr, rhs_rdr;
	private boolean lhs_rdr_move, rhs_rdr_move;
	private boolean lhs_rdr_done, rhs_rdr_done;
	private Db_meta_fld[] key_flds; private int key_flds_len;
	public void Init(Gfdb_diff_tbl lhs_tbl, Gfdb_diff_tbl rhs_tbl) {
		this.lhs_rdr = lhs_tbl.Rdr(); this.rhs_rdr = rhs_tbl.Rdr();
		this.lhs_rdr_move = rhs_rdr_move = Bool_.Y;
		this.lhs_rdr_done = rhs_rdr_done = Bool_.N;
		this.key_flds = rhs_tbl.Keys(); key_flds_len = key_flds.length;
	}
	public int Compare() {
		if (lhs_rdr_move) {
			lhs_rdr_move = lhs_rdr.Move_next();
			if (!lhs_rdr_move) lhs_rdr_done = true;
		}
		if (rhs_rdr_move) {
			rhs_rdr_move = rhs_rdr.Move_next();
			if (!rhs_rdr_move) rhs_rdr_done = true;
		}
		if		(lhs_rdr_done && rhs_rdr_done)	return Gfdb_diff_rdr_comparer.Rslt__done;
		else if	(lhs_rdr_done)					{rhs_rdr_move = true; return Gfdb_diff_rdr_comparer.Rslt__lhs_missing;}
		else if (rhs_rdr_done)					{lhs_rdr_move = true; return Gfdb_diff_rdr_comparer.Rslt__rhs_missing;}
		else {
			int comp = Gfdb_rdr_utl_.Compare(key_flds, key_flds_len, lhs_rdr, rhs_rdr);
			switch (comp) {
				case CompareAble_.Same:			// lhs == rhs; move both
					lhs_rdr_move = rhs_rdr_move = true;
					return Gfdb_diff_rdr_comparer.Rslt__same;
				case CompareAble_.Less:			// lhs < rhs; EX: lhs == 2; rhs == 3
					lhs_rdr_move = true;
					rhs_rdr_move = false;
					return Gfdb_diff_rdr_comparer.Rslt__rhs_missing;
				case CompareAble_.More:			// lhs > rhs; EX: lhs == 4; rhs == 3
					lhs_rdr_move = false;
					rhs_rdr_move = true;
					return Gfdb_diff_rdr_comparer.Rslt__lhs_missing;
				default: throw Err_.new_unhandled(comp);
			}
		}
	}
	public static final int 
	  Rslt__same		= 0
	, Rslt__lhs_missing	= 1
	, Rslt__rhs_missing	= 2
	, Rslt__done		= 3
	;
}
