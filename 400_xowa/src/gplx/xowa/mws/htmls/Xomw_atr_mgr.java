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
public class Xomw_atr_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public int                 Len()                    {return hash.Len();}
	public Xomw_atr_itm        Get_at(int i)            {return (Xomw_atr_itm)hash.Get_at(i);}
	public Xomw_atr_itm        Get_by_or_null(byte[] k) {return (Xomw_atr_itm)hash.Get_by(k);}
	public Xomw_atr_mgr        Clear()                  {hash.Clear(); return this;}
	public void                Del(byte[] key)          {hash.Del(key);}
	public void                Add(Xomw_atr_itm itm)    {hash.Add(itm.Key_bry(), itm);}
	public void Add(byte[] key, byte[] val) {this.Add(new Xomw_atr_itm(-1, key, val));}
	public void Add_or_set(Xomw_atr_itm src) {
		Xomw_atr_itm trg = (Xomw_atr_itm)hash.Get_by(src.Key_bry());
		if (trg == null)
			this.Add(src);
		else
			trg.Val_(src.Val());
	}
	public void Set(byte[] key, byte[] val) {
		Xomw_atr_itm atr = Get_by_or_make(key);
		atr.Val_(val);
	}
	public Xomw_atr_itm Get_by_or_make(byte[] k) {
		Xomw_atr_itm rv = (Xomw_atr_itm)hash.Get_by(k);
		if (rv == null) {
			rv = new Xomw_atr_itm(-1, k, null);
			Add(rv);
		}
		return rv;
	}
	public byte[] Get_val_or_null(byte[] k) {
		Xomw_atr_itm atr = (Xomw_atr_itm)hash.Get_by(k);
		return atr == null ? null : atr.Val();
	}
	public Xomw_atr_mgr Add_many(String... kvs) {// TEST
		int len = kvs.length;
		for (int i = 0; i < len; i += 2) {
			byte[] key = Bry_.new_u8(kvs[i]);
			byte[] val = Bry_.new_u8(kvs[i + 1]);
			Add(key, val);
		}
		return this;
	}
	public String To_str(Bry_bfr tmp) { // TEST
		int len = this.Len();
		for (int i = 0; i < len; i++) {
			Xomw_atr_itm itm = this.Get_at(i);
			tmp.Add(itm.Key_bry()).Add_byte_eq();
			tmp.Add(itm.Val()).Add_byte_nl();
		}
		return tmp.To_str_and_clear();
	}
}
