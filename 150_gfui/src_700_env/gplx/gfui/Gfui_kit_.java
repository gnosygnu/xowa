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
package gplx.gfui; import gplx.*;
public class Gfui_kit_ {
	public static final byte Mem_tid = 0, Swing_tid = 1, Swt_tid = 2, Android_tid = 3;
	public static Gfui_kit Mem()	{return mem_kit;} private static final Gfui_kit mem_kit = Mem_kit._;
	public static Gfui_kit Swt()	{if (swt_kit == null) swt_kit = Swt_kit._;	return swt_kit;} private static Gfui_kit swt_kit; // NOTE: late-binding else swing apps will fail (since swt jar is not deployed)
	public static Gfui_kit Swing()	{if (swing_kit == null) swing_kit = Swing_kit._; return swing_kit;} private static Gfui_kit swing_kit;
	public static Gfui_kit Get_by_key(String key) {
		if		(String_.Eq(key, Mem().Key()))			return Mem();
		else if	(String_.Eq(key, Swt().Key()))			return Swt();
		else if	(String_.Eq(key, Swing().Key()))		return Swing();
		else											throw Err_.unhandled(key);
	}
	public static final String Cfg_HtmlBox = "HtmlBox";
	public static final byte File_dlg_type_open = 0, File_dlg_type_save = 1;
}
