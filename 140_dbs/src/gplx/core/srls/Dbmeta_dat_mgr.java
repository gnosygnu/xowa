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
package gplx.core.srls; import gplx.*; import gplx.core.*;
import gplx.dbs.metas.*;
public class Dbmeta_dat_mgr {
	private final Ordered_hash hash = Ordered_hash_.New();
	public Dbmeta_dat_mgr Clear() {hash.Clear(); return this;}
	public int Len() {return hash.Count();}
	public Dbmeta_dat_itm Get_at(int idx) {return (Dbmeta_dat_itm)hash.Get_at(idx);}
	public Dbmeta_dat_mgr Add_int(String key, int val) {
		Dbmeta_dat_itm itm = new Dbmeta_dat_itm(Dbmeta_fld_tid.Tid__int, key, val);
		hash.Add(key, itm);
		return this;
	}
}
