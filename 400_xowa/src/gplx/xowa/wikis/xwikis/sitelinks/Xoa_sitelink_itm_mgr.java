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
import gplx.xowa.langs.*;
public class Xoa_sitelink_itm_mgr {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	private final Xoa_sitelink_grp default_grp;
	public Xoa_sitelink_itm_mgr(Xoa_sitelink_grp default_grp) {this.default_grp = default_grp;}
	public int Len() {return hash.Count();}
	public void Clear() {hash.Clear();}
	public void Add(Xoa_sitelink_itm itm)			{hash.Add(itm.Key(), itm);}
	public Xoa_sitelink_itm Get_at(int idx)			{return (Xoa_sitelink_itm)hash.Get_at(idx);}
	public Xoa_sitelink_itm Get_by(byte[] key)		{return (Xoa_sitelink_itm)hash.Get_by(key);}
	public Xoa_sitelink_itm Get_by_or_new(byte[] key) {
		Xoa_sitelink_itm rv = (Xoa_sitelink_itm)hash.Get_by(key);
		if (rv == null) {
			rv = new Xoa_sitelink_itm(default_grp, key, Bry_.Empty);
			hash.Add(key, rv);
		}
		return rv;
	}
}
