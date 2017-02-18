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
package gplx.xowa.wikis.pages.wtxts; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_wtxt_data {
	public Xopg_toc_mgr				Toc()				{return toc;}	private final    Xopg_toc_mgr toc = new Xopg_toc_mgr(); 		
	public int						Ctgs__len()			{return ctg_hash == null ? 0 : ctg_hash.Len();} private Ordered_hash ctg_hash;
	public Xoa_ttl					Ctgs__get_at(int i) {return (Xoa_ttl)ctg_hash.Get_at(i);}
	public Xoa_ttl[]				Ctgs__to_ary()		{return ctg_hash == null ? new Xoa_ttl[0] : (Xoa_ttl[])ctg_hash.To_ary(Xoa_ttl.class);}
	public void Ctgs__add(Xoa_ttl ttl) {
		if (ctg_hash == null) ctg_hash = Ordered_hash_.New_bry();
		ctg_hash.Add_if_dupe_use_1st(ttl.Full_db(), ttl);
	}
	public void Clear() {
		if (ctg_hash != null) ctg_hash.Clear();
		toc.Clear();
	}
}
