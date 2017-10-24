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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
import gplx.core.primitives.*;
public abstract class Json_parser__itm__base {
	protected String context;
	protected final    Hash_adp_bry hash = Hash_adp_bry.cs();
	protected final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
	protected String[] keys;
	protected Json_kv[] atrs;
	protected Json_itm cur_itm;
	protected int keys_len;
	public void Ctor(String... keys) {
		this.keys = keys;
		this.keys_len = keys.length;
		for (int i = 0; i < keys_len; ++i)
			hash.Add(Bry_.new_u8(keys[i]), new Int_obj_val(i));
		this.atrs = new Json_kv[keys_len];
	}
	public int Kv__int(Json_kv[] ary, int i)			{return Bry_.To_int(ary[i].Val_as_bry());}
	public long Kv__long(Json_kv[] ary, int i)			{return Bry_.To_long_or(ary[i].Val_as_bry(), 0);}
	public long Kv__long_or_0(Json_kv[] ary, int i)		{
		Json_kv kv = ary[i]; if (kv == null) return 0;
		return Bry_.To_long_or(kv.Val_as_bry(), 0);
	}
	public byte[] Kv__bry(Json_kv[] ary, int i)	{
		byte[] rv = Kv__bry_or_null(ary, i); if (rv == null) throw Err_.new_("json.parser", "missing val", "key", context + "." + keys[i], "excerpt", Json_itm_.To_bry(tmp_bfr, cur_itm));
		return rv;
	}
	public byte[][] Kv__bry_ary(Json_kv[] ary, int i) {
		return ary[i].Val_as_ary().Xto_bry_ary();
	}
	public byte[] Kv__bry_or_empty(Json_kv[] ary, int i) {
		byte[] rv = Kv__bry_or_null(ary, i);
		return rv == null ? Bry_.Empty : rv;
	}
	public byte[] Kv__bry_or_null(Json_kv[] ary, int i)	{
		Json_kv kv = ary[i]; if (kv == null) return null;
		Json_itm val = kv.Val();			
		return  kv == null ? null : val.Data_bry();
	}
	public boolean Kv__mw_bool(Json_kv[] ary, int i)	{
		Json_kv kv = ary[i]; if (kv == null) return false;
		Json_itm val = kv.Val();
		if (	val.Tid() == Json_itm_.Tid__str
			&&	Bry_.Len_eq_0(val.Data_bry())) {
			return true;
		}
		else {
			Warn("unknown val: val=" + String_.new_u8(kv.Data_bry()) + " excerpt=" + String_.new_u8(Json_itm_.To_bry(tmp_bfr, cur_itm)), kv);
			return false;
		}
	}
	public boolean Kv__has(Json_kv[] ary, int i)			{return Kv__bry_or_empty(ary, i) != null;}
	protected abstract void Parse_hook_nde(Json_nde sub, Json_kv[] atrs);
	protected void Warn(String msg, Json_kv kv) {
		Gfo_usr_dlg_.Instance.Warn_many("", "", msg + ": path=~{0}.~{1} excerpt=~{2}", context, kv.Key_as_bry(), Json_itm_.To_bry(tmp_bfr, cur_itm));
	}
}
