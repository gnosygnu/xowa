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
package gplx.xowa.addons.apps.cfgs.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
public class Xoitm_gui_tid {
	public Xoitm_gui_tid(int uid, String key) {
		this.uid = uid;
		this.key = key;
	}
	public int Uid()		{return uid;} private final    int uid;
	public String Key()		{return key;} private final    String key;

	public static final int // SERIALIZED
	  Tid__bool			= 0
	, Tid__str			= 1
	, Tid__int			= 2
	, Tid__memo			= 3
	, Tid__list			= 4
	, Tid__io_cmd		= 5
	, Tid__io_file		= 6
	, Tid__io_dir		= 7
	, Tid__btn			= 8
	, Tid__label		= 9
	;
	private static final    Hash_adp		to_uid_hash = Hash_adp_.New();
	private static final    Xoitm_gui_tid[] to_key_ary = new Xoitm_gui_tid[10];
	public static final    Xoitm_gui_tid 
	  Itm__bool			= New(Tid__bool			, "bool")
	, Itm__str			= New(Tid__str			, "string")
	, Itm__int			= New(Tid__int			, "int")
	, Itm__memo			= New(Tid__memo			, "memo")
	, Itm__list			= New(Tid__list			, "select")
	, Itm__io_cmd		= New(Tid__io_cmd		, "io.cmd")
	, Itm__io_file		= New(Tid__io_file		, "io.file")
	, Itm__io_dir		= New(Tid__io_dir		, "io.dir")
	, Itm__btn			= New(Tid__btn			, "btn")
	, Itm__lbl			= New(Tid__label		, "label")
	;
	private static Xoitm_gui_tid New(int uid, String key) {
		Xoitm_gui_tid rv = new Xoitm_gui_tid(uid, key);
		to_uid_hash.Add(key, rv);
		to_key_ary[uid] = rv;
		return rv;
	}
	public static int To_uid(String str) {
		Xoitm_gui_tid rv = (Xoitm_gui_tid)to_uid_hash.Get_by_or_fail(str);
		return rv.uid;
	}
	public static String To_key(int uid) {
		return to_key_ary[uid].key;
	}
	public static String Infer_gui_type(String db_type) {
		if		(String_.Eq(db_type, "bool"))		return Itm__bool.key;
		else if (String_.Eq(db_type, "int"))			return Itm__int.key;
		else if (String_.Eq(db_type, "memo"))			return Itm__memo.key;
		else if (String_.Eq(db_type, "io.cmd"))			return Itm__io_cmd.key;
		else if (String_.Has_at_bgn(db_type, "list:"))	return Itm__list.key;
		else if (String_.Eq(db_type, "btn"))			return Itm__btn.key;
		else											return Itm__str.key;
	}
}
