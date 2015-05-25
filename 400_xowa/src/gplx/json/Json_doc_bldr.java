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
package gplx.json; import gplx.*;
public class Json_doc_bldr {
	public Json_itm_nde Nde() {return factory.Nde(-1);}
	public Json_itm_nde Nde(Json_grp owner) {
		Json_itm_nde rv = factory.Nde(-1);
		owner.Subs_add(rv);
		return rv;
	}
	public Json_itm Str(byte[] v) {return Str(String_.new_u8(v));}
	public Json_itm Str(String v) {return Json_itm_tmp.new_str_(v);}
	public Json_itm Int(int v) {return Json_itm_tmp.new_int_(v);}
	public Json_itm_kv Kv_int(Json_grp owner, String key, int val)		{Json_itm_kv rv = factory.Kv(Json_itm_tmp.new_str_(key), Json_itm_tmp.new_int_(val)); owner.Subs_add(rv); return rv;}
	public Json_itm_kv Kv_str(Json_grp owner, String key, String val)	{Json_itm_kv rv = factory.Kv(Json_itm_tmp.new_str_(key), Json_itm_tmp.new_str_(val)); owner.Subs_add(rv); return rv;}
	public Json_itm_ary Kv_ary(Json_grp owner, String key, Json_itm... subs) {
		Json_itm key_itm = Json_itm_tmp.new_str_(key);
		Json_itm_ary val_ary = factory.Ary(-1, -1);			
		Json_itm_kv kv = factory.Kv(key_itm, val_ary);
		owner.Subs_add(kv);
		int len = subs.length;
		for (int i = 0; i < len; i++)
			val_ary.Subs_add(subs[i]);
		return val_ary;
	}
	Json_doc doc = new Json_doc(); Json_factory factory = new Json_factory();
}
