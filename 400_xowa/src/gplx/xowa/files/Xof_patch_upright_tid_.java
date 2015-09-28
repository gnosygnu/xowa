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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
public class Xof_patch_upright_tid_ {
	public static final int Tid_unpatched = 0, Tid_use_thumb_w = 1, Tid_fix_default = 2;
	public static final int Tid_all = Tid_use_thumb_w | Tid_fix_default;
	public static int Merge(boolean use_thumb_w, boolean fix_default) {
		if		(use_thumb_w && fix_default)	return Bitmask_.Add_int(Tid_use_thumb_w, Tid_fix_default);
		else if (use_thumb_w)					return Tid_use_thumb_w;
		else if (fix_default)					return Tid_fix_default;
		else									return Tid_unpatched;
	}
	public static boolean Split_use_thumb_w(int tid)		{return Bitmask_.Has_int(tid, Tid_use_thumb_w);}
	public static boolean Split_fix_default(int tid)		{return Bitmask_.Has_int(tid, Tid_fix_default);}
}
