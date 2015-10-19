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
package gplx.xowa.wikis.xwikis.sitelinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
public class Xoa_sitelink_grp_mgr {
	private final Ordered_hash hash = Ordered_hash_.New_bry();		
	public Xoa_sitelink_grp_mgr() {
		this.default_grp = new Xoa_sitelink_grp(Bry_.new_a7("Others"), 1024);
		hash.Add(default_grp.Name(), default_grp);
	}
	public Xoa_sitelink_grp Default_grp() {return default_grp;} private final Xoa_sitelink_grp default_grp;
	public int Len() {return hash.Count();}
	public void Clear() {hash.Clear();}		
	public Xoa_sitelink_grp Get_at(int idx) {return (Xoa_sitelink_grp)hash.Get_at(idx);}
	public Xoa_sitelink_grp Get_by_or_new(byte[] key) {
		Xoa_sitelink_grp rv = (Xoa_sitelink_grp)hash.Get_by(key);
		if (rv == null) {
			rv = new Xoa_sitelink_grp(key, hash.Count());
			hash.Add(key, rv);
		}
		return rv;
	}
	public void Sort() {hash.Sort();}
	public void To_bfr(Bry_bfr bfr) {
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xoa_sitelink_grp grp = (Xoa_sitelink_grp)hash.Get_at(i);
			grp.To_bfr(bfr);
		}
	}
}
