/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.net.qargs; import gplx.*; import gplx.core.*; import gplx.core.net.*;
import gplx.langs.htmls.encoders.*;
public class Gfo_qarg_mgr_old {
	private final    List_adp list = List_adp_.New();
	private final    Hash_adp hash = Hash_adp_bry.cs();
	public int Len() {return list.Count();}
	public boolean Match(byte[] key, byte[] val) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by(key);
		return arg == null ? false : Bry_.Eq(val, arg.Val_bry());
	}
	public Gfo_qarg_itm Get_at(int i) {return (Gfo_qarg_itm)list.Get_at(i);}
	public Gfo_qarg_itm	Get_arg(byte[] key) {return (Gfo_qarg_itm)hash.Get_by(key);}
	public int			Get_val_int_or(byte[] key, int or) {
		byte[] val_bry = Get_val_bry_or(key, null); if (val_bry == null) return or;		
		return Bry_.To_int_or(val_bry, or);		
	}
	public byte[]		Get_val_bry_or(byte[] key, byte[] or) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by(key);
		return arg == null ? or : arg.Val_bry();
	}
	public String		Get_val_str_or(byte[] key, String or) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by(key);
		return arg == null ? or : String_.new_u8(arg.Val_bry());
	}
	public void			Set_val_by_int(byte[] key, int val) {Set_val_by_bry(key, Bry_.new_a7(Int_.To_str(val)));}
	public void			Set_val_by_bry(byte[] key, byte[] val) {		
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by(key);
		if (arg == null) {
			arg = new Gfo_qarg_itm(key, Bry_.Empty);
			list.Add(arg);
			hash.Add(key, arg);
		}
		arg.Val_bry_(val);
	}
	public Gfo_qarg_mgr_old Load(Gfo_qarg_itm[] ary) {
		hash.Clear();
		list.Clear();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_itm itm = ary[i];
			list.Add(itm);
			hash.Add_if_dupe_use_nth(itm.Key_bry(), itm);
		}
		return this;
	}
	public Gfo_qarg_itm[] To_ary() {return (Gfo_qarg_itm[])list.To_ary(Gfo_qarg_itm.class);}
	public byte[] Concat(Bry_bfr bfr, byte[]... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			byte[] key = ary[i];
			Gfo_qarg_itm itm = Get_arg(key); if (itm == null) continue;
			bfr.Add_byte(Byte_ascii.Amp).Add(itm.Key_bry()).Add_byte(Byte_ascii.Eq).Add(itm.Val_bry());
		}
		return bfr.To_bry_and_clear();
	}
	public byte[] To_bry() {
		int len = list.Count(); if (len == 0) return Bry_.Empty;
		Bry_bfr bfr = Bry_bfr_.New();
		To_bry(bfr, gplx.langs.htmls.encoders.Gfo_url_encoder_.Href, false);
		return bfr.To_bry_and_clear();
	}
	public void To_bry(Bry_bfr bfr, Gfo_url_encoder href_encoder, boolean encode) {
		int len = list.Count(); if (len == 0) return;
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_itm itm = (Gfo_qarg_itm)list.Get_at(i);
			bfr.Add_byte(i == 0 ? Byte_ascii.Question : Byte_ascii.Amp);
			Write_or_encode(bfr, href_encoder, encode, itm.Key_bry());
			bfr.Add_byte(Byte_ascii.Eq);
			Write_or_encode(bfr, href_encoder, encode, itm.Val_bry());
		}
	}
	public static void Concat_bfr(Bry_bfr bfr, Gfo_url_encoder href_encoder, Gfo_qarg_itm[] ary) {Concat_bfr(bfr, href_encoder, ary, true);}
	private static void Concat_bfr(Bry_bfr bfr, Gfo_url_encoder href_encoder, Gfo_qarg_itm[] ary, boolean encode) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Gfo_qarg_itm itm = ary[i];
			bfr.Add_byte(i == 0 ? Byte_ascii.Question : Byte_ascii.Amp);
			Write_or_encode(bfr, href_encoder, encode, itm.Key_bry());
			bfr.Add_byte(Byte_ascii.Eq);
			Write_or_encode(bfr, href_encoder, encode, itm.Val_bry());
		}
	}
	private static void Write_or_encode(Bry_bfr bfr, Gfo_url_encoder href_encoder, boolean encode, byte[] bry) {
		if (bry == null) return;	// NOTE: need null check b/c itm.Val_bry can be null
		if (encode)
			href_encoder.Encode(bfr, bry);
		else
			bfr.Add(bry);
	}
}
