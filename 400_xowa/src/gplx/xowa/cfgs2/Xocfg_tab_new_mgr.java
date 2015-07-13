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
package gplx.xowa.cfgs2; import gplx.*; import gplx.xowa.*;
public class Xocfg_tab_new_mgr implements GfoInvkAble {
	public byte Insert_pos() {return insert_pos;} private byte insert_pos = Xocfg_new_tab_pos.Tid_cur_nxt;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_insert_pos))				return Xocfg_new_tab_pos.Xto_str(insert_pos);
		else if	(ctx.Match(k, Invk_insert_pos_))			insert_pos = Xocfg_new_tab_pos.Xto_tid(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_insert_pos_list))		return Xocfg_new_tab_pos.Options__all;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_insert_pos = "insert_pos", Invk_insert_pos_ = "insert_pos_", Invk_insert_pos_list = "insert_pos_list";
}
class Xocfg_new_tab_pos {
	public static final byte Tid_all_bgn = 0, Tid_all_end = 1, Tid_cur_prv = 2, Tid_cur_nxt = 3;
	public static final String Key_all_bgn = "first", Key_all_end = "last", Key_cur_prv = "before", Key_cur_nxt = "after";
	public static byte Xto_tid(String v) {
		if		(String_.Eq(v, Key_all_bgn))	return Tid_all_bgn;
		else if	(String_.Eq(v, Key_all_end))	return Tid_all_end;
		else if	(String_.Eq(v, Key_cur_prv))	return Tid_all_end;
		else if	(String_.Eq(v, Key_cur_nxt))	return Tid_all_end;
		else									throw Exc_.new_unhandled(v);
	}
	public static String Xto_str(byte v) {
		switch (v) {
			case Tid_all_bgn:	return Key_all_bgn;
			case Tid_all_end:	return Key_all_end;
			case Tid_cur_prv:	return Key_cur_prv;
			case Tid_cur_nxt:	return Key_cur_nxt;
			default:			throw Exc_.new_unhandled(v);
		}
	}
	public static KeyVal[] Options__all = KeyVal_.Ary(KeyVal_.new_(Key_all_bgn), KeyVal_.new_(Key_all_end), KeyVal_.new_(Key_cur_prv), KeyVal_.new_(Key_cur_nxt));
}
