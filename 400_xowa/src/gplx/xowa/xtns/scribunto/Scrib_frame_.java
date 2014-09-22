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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Scrib_frame_ {
	public static final byte Tid_null = 0, Tid_current = 1, Tid_parent = 2, Tid_dynamic = 3;
	public static final int Arg_adj_current = 1, Arg_adj_owner = 0;
	public static int Get_frame_tid(String frame_id) {
		if		(String_.Eq(frame_id, "current"))	return Tid_current;
		else if (String_.Eq(frame_id, "parent"))	return Tid_parent;
		else										return Tid_dynamic;
	}
	public static Xot_invk Get_frame(Scrib_core core, String frame_id) {
		if		(String_.Eq(frame_id, "current"))	return core.Frame_current();
		else if (String_.Eq(frame_id, "parent"))	return core.Frame_parent();
		else if (String_.Eq(frame_id, "empty"))		return Xot_invk_mock.new_(core.Frame_current().Defn_tid(), 0, null, KeyVal_.Ary_empty);	// not sure if it should return null title; DATE:2014-07-12
		else {
			return (Xot_invk)core.Frame_created_list().Fetch(frame_id);	// NOTE: can return null; some calls expect nil; EX:mw.lua and "currentFrame = newFrame( 'empty' )"; DATE:2014-07-12
		}
	}
	public static int Get_arg_adj(byte frame_tid) {
		if		(frame_tid == Tid_current)	return 1;		// arg_0 is at idx_1 b/c Module frames use 1 arg for name of proc; EX: {{#invoke:module|proc|arg_0}}
		else								return 0;		// arg_0 is at idx_0; EX: {{template|arg_0}}
	}
	public static Xot_invk Get_parent(Scrib_core core, byte frame_tid) {
		if		(frame_tid == Tid_current)	return core.Frame_parent();			// current frame has an owner frame
		else								return Xot_invk_mock.Null;			// all other frames do not
	}
}
