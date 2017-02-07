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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*;
public class Merge_ctx {
	public void Init(Xow_wiki wiki, Db_conn pack_conn) {
		this.wiki = wiki;
		this.pack_conn = pack_conn;
	}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public Db_conn Pack_conn() {return pack_conn;} private Db_conn pack_conn;
	public int Idx_cur() {return idx_cur;} private int idx_cur; public void Idx_cur_add_() {++idx_cur;}
	public int Idx_end = 70;
	public int Idx_gap = 10;
	public int Idx_nxt = 10;
	public boolean Heap__copy_to_wiki()		{return idx_cur == Idx_nxt || idx_cur == Idx_end;}
	public void Heap__increment_nxt()		{Idx_nxt += Idx_gap;}
	public boolean Heap__last_idx()			{return idx_cur == Idx_end;}
}
