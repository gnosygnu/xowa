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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.flds.*;
public class Xoctg_idx_itm {
	public int Pos() {return pos;} public Xoctg_idx_itm Pos_(int v) {pos = v; return this;} private int pos = -1;
	public int Id() {return id;} private int id;
	public int Timestamp() {return timestamp;} private int timestamp;
	public byte[] Sortkey() {return sortkey;} private byte[] sortkey;
	public int compareTo(Object obj) {Xoctg_idx_itm comp = (Xoctg_idx_itm)obj; return Int_.Compare(pos, comp.Pos());}
	public Xoctg_idx_itm Parse(Gfo_fld_rdr fld_rdr, int pos) {
		this.pos = pos;
		id				= fld_rdr.Read_int_base85_len5();
		timestamp		= fld_rdr.Read_int_base85_len5();
		sortkey			= fld_rdr.Read_bry_escape();
		return this;
	}
	public void Load(int id, byte[] sortkey, int timestamp) {
		this.id = id; this.sortkey = sortkey; this.timestamp = timestamp;
	}
	public void Copy(Xoctg_idx_itm orig) {
		this.pos = orig.pos;
		this.id = orig.id;
		this.timestamp = orig.timestamp;
		this.sortkey = orig.sortkey;
	}
}
class Xoctg_idx_itm_sorter_sortkey implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xoctg_idx_itm lhs = (Xoctg_idx_itm)lhsObj;
		Xoctg_idx_itm rhs = (Xoctg_idx_itm)rhsObj;
		return Bry_.Compare(lhs.Sortkey(), rhs.Sortkey());
	}
	public static final Xoctg_idx_itm_sorter_sortkey Instance = new Xoctg_idx_itm_sorter_sortkey();
}
