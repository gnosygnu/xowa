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
public class Gfo_qarg_mgr {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public Gfo_qarg_mgr Init(Gfo_qarg_itm[] args) {
		hash.Clear();
		int len = args.length;
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_itm arg = args[i];
			hash.Add_bry_obj(arg.Key_bry(), arg);
		}
		return this;
	}
	public byte[] Read_bry_or_fail(String key)		{return Read_bry_or_fail(Bry_.new_u8(key));}
	public byte[] Read_bry_or_fail(byte[] key)		{byte[] rv = Read_bry_or_null(key); if (rv == null) Fail_when_missing(String_.new_u8(key)); return rv;}
	public byte[] Read_bry_or_empty(byte[] key)		{return Read_bry_or(key, Bry_.Empty);}
	public byte[] Read_bry_or_null(String key)		{return Read_bry_or(Bry_.new_u8(key), null);}
	public byte[] Read_bry_or_null(byte[] key)		{return Read_bry_or(key, null);}
	public byte[] Read_bry_or(String key, byte[] or) {return Read_bry_or(Bry_.new_u8(key), or);}
	public byte[] Read_bry_or(byte[] key, byte[] or) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by_bry(key);
		return arg == null ? or : arg.Val_bry();
	}
	public String Read_str_or_fail(String key) {String rv = Read_str_or_null(Bry_.new_u8(key)); if (rv == null) Fail_when_missing(key); return rv;}
	public String Read_str_or_null(String key) {return Read_str_or_null(Bry_.new_u8(key));}
	public String Read_str_or_null(byte[] key) {return Read_str_or(key, null);}
	public String Read_str_or(String key, String or) {return Read_str_or(Bry_.new_u8(key), or);}
	public String Read_str_or(byte[] key, String or) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by_bry(key);
		return arg == null ? or : String_.new_u8(arg.Val_bry());
	}
	public int Read_int_or(String key, int or) {return Read_int_or(Bry_.new_u8(key), or);}
	public int Read_int_or(byte[] key, int or) {
		byte[] val = Read_bry_or(key, null);
		return val == null ? or : Int_.parse_or(String_.new_a7(val), or);
	}
	public int Read_enm_as_int_or(Gfo_qarg_enum_itm enm, int or) {
		Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by_bry(enm.Key());
		return arg == null ? or : enm.Get_as_int_or(arg.Val_bry(), or);
	}
	private void Fail_when_missing(String key) {throw Err_.new_("", "url_arg missing", "key", key);}
	// if (url_args.Read_enm(Enm_cmd.Itm) == Enm_cmd.Tid__add) {}

//		public int Read_enm_or_neg1(byte[] key) {
//			Gfo_qarg_enum_itm enm = enm_mgr.Get(key);				if (enm == null) return -1;
//			Gfo_qarg_itm arg = (Gfo_qarg_itm)hash.Get_by_bry(key);	if (arg == null) return -1;
//			return enm.Get_as_int_or(arg.Val_bry(), -1);
//		}
}
