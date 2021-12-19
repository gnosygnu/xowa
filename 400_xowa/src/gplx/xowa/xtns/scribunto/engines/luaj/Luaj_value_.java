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
package gplx.xowa.xtns.scribunto.engines.luaj;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.ShortUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.TypeIds;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.xtns.scribunto.Scrib_lua_proc;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
class Luaj_value_ {
	public static String Get_val_as_str(LuaTable owner, String key) {
		return ((LuaString)owner.get(key)).tojstring();		
	}
	public static LuaTable Get_val_as_lua_table(LuaTable owner, String key) {
		return (LuaTable)owner.get(key);
	}
	public static KeyVal[] Get_val_as_kv_ary(Luaj_server server, LuaTable owner, String key) {
		LuaTable table = (LuaTable)owner.get(key);
		return Luaj_value_.Lua_tbl_to_kv_ary(server, table);
	}
	public static KeyVal[] Lua_tbl_to_kv_ary(Luaj_server server, LuaTable tbl) {
		// init
		int tbl_len = tbl.length();	// gets length of tbl.array; note no way of getting length of tbl.hash; note that tbl.length is same as len; getn; rawlen; maxn;
		KeyVal[] rv_ary = tbl_len == 0 ? KeyValUtl.AryEmpty : new KeyVal[tbl_len];	// guess rv_ary dimensions
		List_adp rv_list = null;
		int rv_idx = 0;
		LuaValue cur = LuaValue.NIL;	// needed for luaj iterator; tbl.next(cur);
		
		// override tbl with xo_orig_table from mw.lua.dataWrapper; ISSUE#:586: DATE:2019-10-29
		LuaValue metatable_obj = tbl.getmetatable();
		if (metatable_obj != null && !metatable_obj.isnil()) {
			LuaValue orig_data_obj = ((LuaTable)metatable_obj).get("xo_orig_data");
			if (orig_data_obj != null && !orig_data_obj.isnil())
				tbl = (LuaTable)orig_data_obj;
		}

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
			KeyVal kv = null;
			// val is function
			if (itm_val.type() == LuaValue.TFUNCTION) {
				String func_key = itm_key.tojstring();
				int func_id = IntUtl.Cast(itm_val_obj);
				Scrib_lua_proc lua_func = new Scrib_lua_proc(func_key, func_id);
				if (itm_key.type() == LuaValue.TSTRING)			// most functions are named
					kv = KeyVal.NewStr(func_key, lua_func);
				else											// some are not; particularly "anonymous" functions in Module for gsub_function; these will have a kv of int,int; note that key must be int; if string, lua will not be able to match it back to int later
					kv = KeyVal.NewInt(((LuaInteger)itm_key).v, lua_func);
			}
			// val is number or string
			else {
				switch (itm_key.type()) {
					case LuaValue.TNUMBER:
						int key_int = ((LuaNumber)itm_key).toint();
						kv = KeyVal.NewInt(key_int, itm_val_obj);
						break;
					case LuaValue.TSTRING:
						kv = KeyVal.NewStr(((LuaString)itm_key).tojstring(), itm_val_obj);
						break;
					default:
						throw ErrUtl.NewUnhandled(itm_key.type());
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
		return rv_list == null ? rv_ary : (KeyVal[])rv_list.ToAryAndClear(KeyVal.class);
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
			default:							throw ErrUtl.NewUnhandled(v.type());
		}		
	}
	public static LuaValue Obj_to_lua_val(Luaj_server server, Object o) {
		if (o == null) return LuaValue.NIL;
		Class<?> c = ClassUtl.TypeByObj(o);
		if		(ObjectUtl.Eq(c, BoolUtl.ClsRefType))			return LuaValue.valueOf((Boolean)o);
		else if	(ObjectUtl.Eq(c, ByteUtl.ClsRefType))			return LuaValue.valueOf((Byte)o);
		else if	(ObjectUtl.Eq(c, IntUtl.ClsRefType))			return LuaValue.valueOf((Integer)o);
		else if	(ObjectUtl.Eq(c, StringUtl.ClsRefType))		return LuaValue.valueOf((String)o);
		else if	(ObjectUtl.Eq(c, DoubleUtl.ClsRefType))		return LuaValue.valueOf((Double)o);
		else if	(ObjectUtl.Eq(c, byte[].class))				return LuaValue.valueOf(StringUtl.NewU8((byte[])o));
		else if	(ObjectUtl.Eq(c, KeyVal.class))				return Make_lua_tbl_by_kv_ary(server, (KeyVal)o);
		else if	(ObjectUtl.Eq(c, KeyVal[].class))				return Make_lua_tbl_by_kv_ary(server, (KeyVal[])o);
		else if	(ObjectUtl.Eq(c, Object[].class))				return Make_lua_tbl_by_obj_ary(server, ((Object[])o));	// PAGE:de.w:Reicholzheim DATE:2017-12-25
		else if	(ObjectUtl.Eq(c, LongUtl.ClsRefType))			return LuaValue.valueOf((Long)o);
		else if	(ObjectUtl.Eq(c, Scrib_lua_proc.class))		return server.Get_closure_by_id(((Scrib_lua_proc)o).Id());
		else if	(ObjectUtl.Eq(c, FloatUtl.ClsRefType))		return LuaValue.valueOf((Float)o);
		else if	(ObjectUtl.Eq(c, CharUtl.ClsRefType))			return LuaValue.valueOf((Character)o);
		else if	(ObjectUtl.Eq(c, ShortUtl.ClsRefType))		return LuaValue.valueOf((Short)o);
		else if	(ObjectUtl.Eq(c, GfoDecimal.class))			return LuaValue.valueOf(((GfoDecimal)o).ToDouble());	// DATE:2016-08-01
		else                     							return LuaValue.NIL; 
	}
	private static LuaTable Make_lua_tbl_by_kv_ary(Luaj_server server, KeyVal... ary) {
		LuaTable rv = LuaValue.tableOf();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			KeyVal itm = ary[i];
			LuaValue itm_val = Obj_to_lua_val(server, itm.Val());
			switch (itm.KeyTid()) {
				case TypeIds.IdInt:
					rv.set(IntUtl.Cast(itm.KeyAsObj()), itm_val);
					break;
				case TypeIds.IdStr:
				case TypeIds.IdObj:
					rv.set(itm.KeyToStr(), itm_val);
					break;
			}				
		}
		return rv;
	}	
	private static LuaTable Make_lua_tbl_by_obj_ary(Luaj_server server, Object... ary) {
		LuaTable rv = LuaValue.tableOf();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Object itm = ary[i];
			LuaValue itm_val = Obj_to_lua_val(server, itm);
			rv.set(i + List_adp_.Base1, itm_val); // NOTE: + 1 b/c lua array are 1-based
		}
		return rv;
	}	
}
