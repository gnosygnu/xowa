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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.gfui.*;
public class Xob_import_marker {
	public static void Import_bgn(Xowe_wiki wiki) {Io_mgr._.SaveFilStr(url_(wiki), "XOWA has created this file to indicate that an import is in progress. This file will be deleted once the import is completed.");}
	public static void Import_end(Xowe_wiki wiki) {Io_mgr._.DeleteFil_args(url_(wiki)).MissingFails_off().Exec();}
	public static boolean Check(Xowe_wiki wiki) {
		Io_url url = url_(wiki);
		if (!Io_mgr._.ExistsFil(url)) return true;
		Xoae_app app = wiki.Appe();
		app.Usr_dlg().Log_many("", "", "import.marker: marker found: url=~{0}", url.Raw());
		byte[] incompete_msg_bry = app.User().Msg_mgr().Val_by_key_args(Bry_.new_ascii_("api-xowa.import.core.incomplete"), wiki.Domain_str());
		int rslt = app.Gui_mgr().Kit().Ask_yes_no_cancel("", "", String_.new_utf8_(incompete_msg_bry));
		switch (rslt) {
			case Gfui_dlg_msg_.Btn_yes:		Xobc_core_cleanup.Delete_wiki_sql(wiki); Import_end(wiki); return false;	// delete wiki
			case Gfui_dlg_msg_.Btn_no:		Import_end(wiki); return true;	// delete marker
			case Gfui_dlg_msg_.Btn_cancel:	return true;					// noop
			default:						throw Err_.unhandled(rslt);
		}
	}
	private static Io_url url_(Xowe_wiki wiki) {
		return wiki.Fsys_mgr().Root_dir().GenSubFil(wiki.Domain_str() + "-import.lock");
	}
}
