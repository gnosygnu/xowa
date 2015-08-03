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
package gplx.xowa.specials.xowa.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.core.primitives.*; import gplx.core.net.*;
class Xoa_url_arg_mgr {
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	private final Xoa_url_enum_mgr enm_mgr;
	public Xoa_url_arg_mgr(Xoa_url_enum_mgr enm_mgr) {this.enm_mgr = enm_mgr;}
	public void Init(Gfo_qarg_itm[] args) {
		hash.Clear();
		int len = args.length;
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_itm arg = args[i];
			hash.Add_bry_obj(arg.Key_bry(), arg);
		}
	}
	public int Read_enm_or_neg1(byte[] key) {
		Xoa_url_enum_itm enm = enm_mgr.Get(key);				if (enm == null) return -1;
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by_bry(key);	if (arg == null) return -1;
		return enm.Get_as_int_or(arg.Val_bry(), -1);
	}
	public byte[] Read_bry_or_empty(byte[] key) {return Read_bry_or(key, Bry_.Empty);}
	public byte[] Read_bry_or_null(byte[] key) {return Read_bry_or(key, null);}
	public byte[] Read_bry_or(byte[] key, byte[] or) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by_bry(key);
		return arg == null ? or : arg.Val_bry();
	}
	public String Read_str_or_null(String key) {return Read_str_or_null(Bry_.new_u8(key));}
	public String Read_str_or_null(byte[] key) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by_bry(key);
		return arg == null ? null : String_.new_u8(arg.Val_bry());
	}
}
class Xoa_url_enum_mgr {
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	public Xoa_url_enum_mgr(Xoa_url_enum_itm... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xoa_url_enum_itm itm = ary[i];
			hash.Add_bry_obj(itm.Key(), itm);
		}
	}
	public Xoa_url_enum_itm Get(byte[] key) {return (Xoa_url_enum_itm)hash.Get_by_bry(key);}
}
class Xoa_url_enum_itm {
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	public Xoa_url_enum_itm(byte[] key) {this.key = key;}
	public byte[] Key() {return key;} private final byte[] key;
	public Xoa_url_enum_itm Add(String key, int val) {
		hash.Add_bry_obj(Bry_.new_u8(key), Int_obj_val.new_(val));
		return this;
	}
	public int Get_as_int_or(byte[] val, int or) {
		Int_obj_val rv = (Int_obj_val)hash.Get_by_bry(val);
		return rv == null ? or : rv.Val();
	}
}
