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
package gplx.gfui.kits.core; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
public class Gfui_kit_ {
	public static final byte Mem_tid = 0, Swing_tid = 1, Swt_tid = 2, Android_tid = 3;
	public static Gfui_kit Mem()	{return mem_kit;} private static final    Gfui_kit mem_kit = Mem_kit.Instance;
	public static Gfui_kit Swt()	{if (swt_kit == null) swt_kit = Swt_kit.Instance;	return swt_kit;} private static Gfui_kit swt_kit; // NOTE: late-binding else swing apps will fail (since swt jar is not deployed)
	public static Gfui_kit Swing()	{if (swing_kit == null) swing_kit = Swing_kit.Instance; return swing_kit;} private static Gfui_kit swing_kit;
	public static Gfui_kit Get_by_key(String key) {
		if		(String_.Eq(key, Mem().Key()))			return Mem();
		else if	(String_.Eq(key, Swt().Key()))			return Swt();
		else if	(String_.Eq(key, Swing().Key()))		return Swing();
		else											throw Err_.new_unhandled(key);
	}
	public static final    String Cfg_HtmlBox = "HtmlBox";
	public static final byte File_dlg_type_open = 0, File_dlg_type_save = 1;
}
