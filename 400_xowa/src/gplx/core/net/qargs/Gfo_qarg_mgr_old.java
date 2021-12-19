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
package gplx.core.net.qargs;
import gplx.langs.htmls.encoders.*;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
public class Gfo_qarg_mgr_old {
	private final List_adp list = List_adp_.New();
	private final Hash_adp hash = Hash_adp_bry.cs();
	public int Len() {return list.Len();}
	public boolean Match(byte[] key, byte[] val) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.GetByOrNull(key);
		return arg == null ? false : BryLni.Eq(val, arg.Val_bry());
	}
	public Gfo_qarg_itm Get_at(int i) {return (Gfo_qarg_itm)list.GetAt(i);}
	public Gfo_qarg_itm	Get_arg(byte[] key) {return (Gfo_qarg_itm)hash.GetByOrNull(key);}
	public int			Get_val_int_or(byte[] key, int or) {
		byte[] val_bry = Get_val_bry_or(key, null); if (val_bry == null) return or;		
		return BryUtl.ToIntOr(val_bry, or);
	}
	public byte[]		Get_val_bry_or(byte[] key, byte[] or) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.GetByOrNull(key);
		return arg == null ? or : arg.Val_bry();
	}
	public String		Get_val_str_or(byte[] key, String or) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.GetByOrNull(key);
		return arg == null ? or : StringUtl.NewU8(arg.Val_bry());
	}
	public void			Set_val_by_int(byte[] key, int val) {Set_val_by_bry(key, BryUtl.NewA7(IntUtl.ToStr(val)));}
	public void			Set_val_by_bry(byte[] key, byte[] val) {		
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.GetByOrNull(key);
		if (arg == null) {
			arg = new Gfo_qarg_itm(key, BryUtl.Empty);
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
			hash.AddIfDupeUseNth(itm.Key_bry(), itm);
		}
		return this;
	}
	public Gfo_qarg_itm[] To_ary() {return (Gfo_qarg_itm[])list.ToAry(Gfo_qarg_itm.class);}
	public byte[] Concat(BryWtr bfr, byte[]... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			byte[] key = ary[i];
			Gfo_qarg_itm itm = Get_arg(key); if (itm == null) continue;
			bfr.AddByte(AsciiByte.Amp).Add(itm.Key_bry()).AddByte(AsciiByte.Eq).Add(itm.Val_bry());
		}
		return bfr.ToBryAndClear();
	}
	public byte[] To_bry() {
		int len = list.Len(); if (len == 0) return BryUtl.Empty;
		BryWtr bfr = BryWtr.New();
		To_bry(bfr, gplx.langs.htmls.encoders.Gfo_url_encoder_.Href, false);
		return bfr.ToBryAndClear();
	}
	public void To_bry(BryWtr bfr, Gfo_url_encoder href_encoder, boolean encode) {
		int len = list.Len(); if (len == 0) return;
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_itm itm = (Gfo_qarg_itm)list.GetAt(i);
			bfr.AddByte(i == 0 ? AsciiByte.Question : AsciiByte.Amp);
			Write_or_encode(bfr, href_encoder, encode, itm.Key_bry());
			bfr.AddByte(AsciiByte.Eq);
			Write_or_encode(bfr, href_encoder, encode, itm.Val_bry());
		}
	}
	public static void Concat_bfr(BryWtr bfr, Gfo_url_encoder href_encoder, Gfo_qarg_itm[] ary) {Concat_bfr(bfr, href_encoder, ary, true);}
	private static void Concat_bfr(BryWtr bfr, Gfo_url_encoder href_encoder, Gfo_qarg_itm[] ary, boolean encode) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Gfo_qarg_itm itm = ary[i];
			bfr.AddByte(i == 0 ? AsciiByte.Question : AsciiByte.Amp);
			Write_or_encode(bfr, href_encoder, encode, itm.Key_bry());
			bfr.AddByte(AsciiByte.Eq);
			Write_or_encode(bfr, href_encoder, encode, itm.Val_bry());
		}
	}
	private static void Write_or_encode(BryWtr bfr, Gfo_url_encoder href_encoder, boolean encode, byte[] bry) {
		if (bry == null) return;	// NOTE: need null check b/c itm.Val_bry can be null
		if (encode)
			href_encoder.Encode(bfr, bry);
		else
			bfr.Add(bry);
	}
}
