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
package gplx.xowa.htmls.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
public class Xow_hdump_mode {
	private final    int tid;
	private final    String key;
	private final    String gui;

	public Xow_hdump_mode(int tid, String key, String gui) {
		this.tid = tid; this.key = key; this.gui = gui;
	}
	public boolean			Tid_is_hdump_save() {return tid == Hdump_save.tid;}

	private static final    Xow_hdump_mode
	  Shown			= new Xow_hdump_mode(0, "shown"			, "Shown")
	, Hdump_save	= new Xow_hdump_mode(1, "hdump_save"	, "Saved for HTML DB")
	, Hdump_load	= new Xow_hdump_mode(2, "hdump_load"	, "Loaded by HTML DB");
	public static void Cfg__reg_type(gplx.xowa.addons.apps.cfgs.mgrs.types.Xocfg_type_mgr type_mgr) {
		type_mgr.Lists__add("list:xowa.wiki.hdumps.html_mode", To_kv(Shown), To_kv(Hdump_save), To_kv(Hdump_load));
	}
	private static Keyval To_kv(Xow_hdump_mode mode) {return Keyval_.new_(mode.key, mode.gui);}
	public static Xow_hdump_mode Parse(String v) {
		if		(String_.Eq(v, Shown.key))			return Shown;
		else if	(String_.Eq(v, Hdump_save.key))		return Hdump_save;
		else if	(String_.Eq(v, Hdump_load.key))		return Hdump_load;
		else										throw Err_.new_unhandled(v);
	}
}
