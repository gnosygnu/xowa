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
package gplx.core.json; import gplx.*; import gplx.core.*;
import gplx.core.primitives.*;
public abstract class Json_parser__itm__base {
	protected String context;
	protected final Hash_adp_bry hash = Hash_adp_bry.cs();
	protected final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	protected String[] keys;
	protected Json_kv[] atrs;
	protected Json_itm cur_itm;
	protected int keys_len;
	public void Ctor(String... keys) {
		this.keys = keys;
		this.keys_len = keys.length;
		for (int i = 0; i < keys_len; ++i)
			hash.Add(Bry_.new_u8(keys[i]), Int_obj_val.new_(i));
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
		Gfo_usr_dlg_.I.Warn_many("", "", msg + ": path=~{0}.~{1} excerpt=~{2}", context, kv.Key_as_bry(), Json_itm_.To_bry(tmp_bfr, cur_itm));
	}
}
