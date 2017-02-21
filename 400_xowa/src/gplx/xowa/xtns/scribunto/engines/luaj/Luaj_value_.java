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
package gplx.xowa.xtns.scribunto.engines.luaj; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import java.util.*;
import org.luaj.vm2.*;
class Luaj_value_ {
	public static String Get_val_as_str(LuaTable owner, String key) {
		return ((LuaString)owner.get(key)).tojstring();		
	}
	public static LuaTable Get_val_as_lua_table(LuaTable owner, String key) {
		return (LuaTable)owner.get(key);
	}
	public static Keyval[] Get_val_as_kv_ary(Luaj_server server, LuaTable owner, String key) {
		LuaTable table = (LuaTable)owner.get(key);
		return Luaj_value_.Lua_tbl_to_kv_ary(server, table);
	}
	public static Keyval[] Lua_tbl_to_kv_ary(Luaj_server server, LuaTable tbl) {
		// init
		int tbl_len = tbl.length();	// gets length of tbl.array; note no way of getting length of tbl.hash; note that tbl.length is same as len; getn; rawlen; maxn;
		Keyval[] rv_ary = tbl_len == 0 ? Keyval_.Ary_empty : new Keyval[tbl_len];	// guess rv_ary dimensions 
		List_adp rv_list = null;
		int rv_idx = 0;
		LuaValue cur = LuaValue.NIL;	// needed for luaj iterator; tbl.next(cur);
		
		// loop over pairs in tbl; no direct way to get kvs
		while (true) {
			// get next itm
			Varargs itm = tbl.next(cur);
			if (itm == LuaValue.NIL) break;	// no more pairs; stop
			
			// extract luaj key / val from itm; note itm.arg(1) is key and itm.arg(2) is val
			LuaValue itm_key = itm.arg(1);
			LuaValue itm_val = itm.arg(2);
			Object itm_val_obj = Lua_val_to_obj(server, itm_val);

			// transform to xowa kv
			Keyval kv = null;
			// val is function
			if (itm_val.type() == LuaValue.TFUNCTION) {
				String func_key = itm_key.tojstring();
				int func_id = Int_.cast(itm_val_obj);
				Scrib_lua_proc lua_func = new Scrib_lua_proc(func_key, func_id);
				if (itm_key.type() == LuaValue.TSTRING)			// most functions are named
					kv = Keyval_.new_(func_key, lua_func);
				else											// some are not; particularly "anonymous" functions in Module for gsub_function; these will have a kv of int,int; note that key must be int; if string, lua will not be able to match it back to int later
					kv = Keyval_.int_(((LuaInteger)itm_key).v, lua_func);
			}
			// val is number or string
			else {
				switch (itm_key.type()) {
					case LuaValue.TNUMBER:
						int key_int = ((LuaNumber)itm_key).toint();
						kv = Keyval_.int_(key_int, itm_val_obj);
						break;
					case LuaValue.TSTRING:
						kv = Keyval_.new_(((LuaString)itm_key).tojstring(), itm_val_obj);
						break;
					default:
						throw Err_.new_unhandled(itm_key.type());
				}
			}

			// store kv in rv
			// still enough space in array
			if (rv_idx < tbl_len) {
				rv_ary[rv_idx] = kv;
			}
			// exceeded rv_ary; store in list
			else {
				if (rv_idx == tbl_len) {
					rv_list = List_adp_.New();
					if (tbl_len > 0) {	// don't bother entering for loop if rv_ary was 0
						for (int i = 0; i < tbl_len; i++) {
							rv_list.Add(rv_ary[i]);
						}
					}
				}
				rv_list.Add(kv);
			}
			
			// increment list and rv_idx
			cur = itm_key;
			++rv_idx;
		}
		return rv_list == null ? rv_ary : (Keyval[])rv_list.To_ary_and_clear(Keyval.class);
	}
	private static Object Lua_val_to_obj(Luaj_server server, LuaValue v) {
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
			case LuaValue.TTABLE:				return Lua_tbl_to_kv_ary(server, (LuaTable)v);
			case LuaValue.TFUNCTION:			return server.Get_id_by_closure(v);
			default:							throw Err_.new_unhandled(v.type());
		}		
	}
	public static LuaValue Obj_to_lua_val(Luaj_server server, Object o) {
		if (o == null) return LuaValue.NIL;
		Class<?> c = Type_adp_.ClassOf_obj(o);
		if		(Object_.Eq(c, Bool_.Cls_ref_type))			return LuaValue.valueOf((Boolean)o);
		else if	(Object_.Eq(c, Byte_.Cls_ref_type))			return LuaValue.valueOf((Byte)o);
		else if	(Object_.Eq(c, Int_.Cls_ref_type))			return LuaValue.valueOf((Integer)o);
		else if	(Object_.Eq(c, String_.Cls_ref_type))		return LuaValue.valueOf((String)o);
		else if	(Object_.Eq(c, Double_.Cls_ref_type))		return LuaValue.valueOf((Double)o);
		else if	(Object_.Eq(c, byte[].class))				return LuaValue.valueOf(String_.new_u8((byte[])o));
		else if	(Object_.Eq(c, Keyval.class))				return Kv_ary_to_lua_tbl(server, (Keyval)o);
		else if	(Object_.Eq(c, Keyval[].class))				return Kv_ary_to_lua_tbl(server, (Keyval[])o);
		else if	(Object_.Eq(c, Long_.Cls_ref_type))			return LuaValue.valueOf((Long)o);
		else if	(Object_.Eq(c, Scrib_lua_proc.class))		return server.Get_closure_by_id(((Scrib_lua_proc)o).Id());
		else if	(Object_.Eq(c, Float_.Cls_ref_type))		return LuaValue.valueOf((Float)o);
		else if	(Object_.Eq(c, Char_.Cls_ref_type))			return LuaValue.valueOf((Character)o);
		else if	(Object_.Eq(c, Short_.Cls_ref_type))		return LuaValue.valueOf((Short)o);
		else if	(Object_.Eq(c, Decimal_adp.class))			return LuaValue.valueOf(((Decimal_adp)o).To_double());	// DATE:2016-08-01
		else return LuaValue.NIL; 
	}
	private static LuaTable Kv_ary_to_lua_tbl(Luaj_server server, Keyval... ary) {
		LuaTable rv = LuaValue.tableOf();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Keyval itm = ary[i];
			LuaValue itm_val = Obj_to_lua_val(server, itm.Val());
			switch (itm.Key_tid()) {
				case Type_adp_.Tid__int:
					rv.set(Int_.cast(itm.Key_as_obj()), itm_val);
					break;
				case Type_adp_.Tid__str:
				case Type_adp_.Tid__obj:
					rv.set(itm.Key(), itm_val);
					break;
			}				
		}
		return rv;
	}	
}
