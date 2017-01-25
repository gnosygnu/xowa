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
package gplx.xowa.mws.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Xomwh_atr_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public int                 Len()                  {return hash.Len();}
	public Xomwh_atr_itm       Get_at(int i)          {return (Xomwh_atr_itm)hash.Get_at(i);}
	public Xomwh_atr_mgr       Clear()                {hash.Clear(); return this;}
	public void                Add(byte[] key, byte[] val) {hash.Add(key, new Xomwh_atr_itm(-1, key, val));}
	public void                Add(Xomwh_atr_itm itm) {hash.Add(itm.Key_bry(), itm);}
	public void                Del(byte[] key)        {hash.Del(key);}
	public void                Set(byte[] key, byte[] val) {
		Xomwh_atr_itm atr = Get_by_or_make(key);
		atr.Val_(val);
	}
	public void Add_or_set(Xomwh_atr_itm src) {
		Xomwh_atr_itm trg = (Xomwh_atr_itm)hash.Get_by(src.Key_bry());
		if (trg == null)
			this.Add(src);
		else
			trg.Val_(src.Val());
	}
	public Xomwh_atr_itm Get_by_or_null(byte[] k) {
		return (Xomwh_atr_itm)hash.Get_by(k);
	}
	public Xomwh_atr_itm Get_by_or_make(byte[] k) {
		Xomwh_atr_itm rv = (Xomwh_atr_itm)hash.Get_by(k);
		if (rv == null) {
			rv = new Xomwh_atr_itm(-1, k, null);
			Add(rv);
		}
		return rv;
	}
	public byte[] Get_val_or_null(byte[] k) {
		Xomwh_atr_itm atr = (Xomwh_atr_itm)hash.Get_by(k);
		return atr == null ? null : atr.Val();
	}
}
