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
package gplx.xowa.xtns.scribunto.engines.luaj; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import org.luaj.vm2.*;
class Luaj_value_ {
	public static String Get_val_as_str(LuaTable owner, String key) {
		return ((LuaString)owner.get(key)).tojstring();		
	}
	public static LuaTable Get_val_as_lua_table(LuaTable owner, String key) {
		return (LuaTable)owner.get(key);
	}
	public static KeyVal[] Get_val_as_kv_ary(Luaj_server server, LuaTable owner, String key) {
		LuaTable table = (LuaTable)owner.get(key);
		return Luaj_value_.X_tbl_to_kv_ary(server, table);
	}
	public static KeyVal[] X_tbl_to_kv_ary(Luaj_server server, LuaTable tbl) {
		ListAdp temp = ListAdp_.new_();
		LuaValue cur = LuaValue.NIL;
		int len = 0;
		while (true) {											// iterate over pairs in tbl; no direct way to get kvs
			Varargs itm = tbl.next(cur);
			if (itm == LuaValue.NIL) break;						// no more pairs; stop
			LuaValue itm_val = itm.arg(2);						// val is itm 2
			Object itm_val_obj = X_val_to_obj(server, itm_val);
			LuaValue itm_key = itm.arg(1);
			KeyVal kv = null;
			if (itm_val.type() == LuaValue.TFUNCTION) {			// function is converted to Scrib_lua_proc
				String func_key = itm_key.tojstring();
				int func_id = Int_.cast_(itm_val_obj);
				Scrib_lua_proc lua_func = new Scrib_lua_proc(func_key, func_id);
				if (itm_key.type() == LuaValue.TSTRING)			// most functions are named
					kv = KeyVal_.new_(func_key, lua_func);
				else											// some are not; particularly "anonymous" functions in Module for gsub_function; these will have a kv of int,int; note that key must be int; if string, lua will not be able to match it back to int later
					kv = KeyVal_.int_(((LuaInteger)itm_key).v, lua_func);
			}
			else {
				switch (itm_key.type()) {
					case LuaValue.TNUMBER:
						kv = KeyVal_.int_(((LuaNumber)itm_key).toint(), itm_val_obj);
						break;
					case LuaValue.TSTRING:
						kv = KeyVal_.new_(((LuaString)itm_key).tojstring(), itm_val_obj);
						break;
					default:
						throw Err_.unhandled(itm_key.type());
				}
			}
			temp.Add(kv);
			cur = itm_key;
			++len;
		}
		if (len == 0) return KeyVal_.Ary_empty;
		return (KeyVal[])temp.Xto_ary(KeyVal.class);
	}
	private static Object X_val_to_obj(Luaj_server server, LuaValue v) {
		switch (v.type()) {
			case LuaValue.TNIL: 				return null;
			case LuaValue.TBOOLEAN:				return ((LuaBoolean)v).toboolean();
			case LuaValue.TSTRING:				return ((LuaString)v).tojstring();
			case LuaValue.TNUMBER:
				LuaNumber v_num = (LuaNumber)v;
				if (v_num.isint())
					return v_num.toint();
				else
					return v_num.todouble();
			case LuaValue.TTABLE:				return X_tbl_to_kv_ary(server, (LuaTable)v);
			case LuaValue.TFUNCTION:			return server.Get_id_by_closure(v);
			default:							throw Err_.unhandled(v.type());
		}		
	}
	public static LuaValue X_obj_to_val(Luaj_server server, Object o) {
		if (o == null) return LuaValue.NIL;
		Class<?> c = ClassAdp_.ClassOf_obj(o);
		if		(Object_.Eq(c, Bool_.Cls_ref_type))			return LuaValue.valueOf((Boolean)o);
		else if	(Object_.Eq(c, Int_.Cls_ref_type))			return LuaValue.valueOf((Integer)o);
		else if	(Object_.Eq(c, Double_.Cls_ref_type))		return LuaValue.valueOf((Double)o);
		else if	(Object_.Eq(c, String_.Cls_ref_type))		return LuaValue.valueOf((String)o);
		else if	(Object_.Eq(c, byte[].class))				return LuaValue.valueOf(String_.new_utf8_((byte[])o));
		else if	(Object_.Eq(c, KeyVal.class))				return X_kv_ary_to_tbl(server, (KeyVal)o);
		else if	(Object_.Eq(c, KeyVal[].class))				return X_kv_ary_to_tbl(server, (KeyVal[])o);
		else if	(Object_.Eq(c, Scrib_lua_proc.class))		return server.Get_closure_by_id(((Scrib_lua_proc)o).Id());
		else return LuaValue.NIL; 
	}
	private static LuaTable X_kv_ary_to_tbl(Luaj_server server, KeyVal... ary) {
		LuaTable rv = LuaValue.tableOf();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			KeyVal itm = ary[i];
			LuaValue itm_val = X_obj_to_val(server, itm.Val());
			switch (itm.Key_tid()) {
				case KeyVal_.Key_tid_int:
					rv.set(Int_.cast_(itm.Key_as_obj()), itm_val);
					break;
				case KeyVal_.Key_tid_str:
				case KeyVal_.Key_tid_obj:
					rv.set(itm.Key(), itm_val);
					break;
			}				
		}
		return rv;
	}	
}
