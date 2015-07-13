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
package gplx.xowa; import gplx.*;
public class Xoa_url_arg_hash {
	private Ordered_hash hash = Ordered_hash_.new_bry_();
	public Gfo_url_arg Get_arg(byte[] key) {return (Gfo_url_arg)hash.Get_by(key);}
	public int Get_val_int_or(byte[] key, int or) {
		byte[] val_bry = Get_val_bry_or(key, null); if (val_bry == null) return or;		
		return Bry_.Xto_int_or(val_bry, or);		
	}
	public byte[] Get_val_bry_or(byte[] key, byte[] or) {
		Gfo_url_arg arg = (Gfo_url_arg)hash.Get_by(key);
		return arg == null ? or : arg.Val_bry();
	}
	public String Get_val_str_or(byte[] key, String or) {
		Gfo_url_arg arg = (Gfo_url_arg)hash.Get_by(key);
		return arg == null ? or : String_.new_u8(arg.Val_bry());
	}
	public void Set_val_by_int(byte[] key, int val) {Set_val_by_bry(key, Bry_.new_a7(Int_.Xto_str(val)));}
	public void Set_val_by_bry(byte[] key, byte[] val) {		
		Gfo_url_arg arg = (Gfo_url_arg)hash.Get_by(key);
		if (arg == null) {
			arg = new Gfo_url_arg(key, Bry_.Empty);
			hash.Add(key, arg);
		}
		arg.Val_bry_(val);
	}
	public byte[] Concat(Bry_bfr bfr, byte[]... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			byte[] key = ary[i];
			Gfo_url_arg itm = Get_arg(key); if (itm == null) continue;
			bfr.Add_byte(Byte_ascii.Amp).Add(itm.Key_bry()).Add_byte(Byte_ascii.Eq).Add(itm.Val_bry());
		}
		return bfr.Xto_bry_and_clear();
	}
	public Xoa_url_arg_hash Load(Xoa_url url) {
		hash.Clear();
		Gfo_url_arg[] ary = url.Args(); 
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Gfo_url_arg itm = ary[i];
			hash.Add(itm.Key_bry(), itm);
		}
		return this;
	}
	public void Save(Xoa_url url) {
		Gfo_url_arg[] ary = (Gfo_url_arg[])hash.To_ary(Gfo_url_arg.class);
		url.Args_(ary);
	}
	public static void Concat_bfr(Bry_bfr bfr, Url_encoder href_encoder, Gfo_url_arg[] ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Gfo_url_arg itm = ary[i];
			bfr.Add_byte(i == 0 ? Byte_ascii.Question : Byte_ascii.Amp);
			href_encoder.Encode(bfr, itm.Key_bry());
			bfr.Add_byte(Byte_ascii.Eq);
			href_encoder.Encode(bfr, itm.Val_bry());
		}
	}
}
