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
package gplx.xowa.bldrs.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.gfui.*; import gplx.xowa.bldrs.cmds.utils.*;
public class Xob_import_marker {
	private final Hash_adp_bry in_progress_hash = Hash_adp_bry.cs_();
	public void Bgn(Xowe_wiki wiki) {
		in_progress_hash.Add_as_key_and_val(wiki.Domain_bry());
		Io_mgr.I.SaveFilStr(url_(wiki), "XOWA has created this file to indicate that an import is in progress. This file will be deleted once the import is completed.");
	}
	public void End(Xowe_wiki wiki) {
		in_progress_hash.Del(wiki.Domain_bry());
		Io_mgr.I.DeleteFil_args(url_(wiki)).MissingFails_off().Exec();
	}
	public boolean Chk(Xowe_wiki wiki) {
		if (!wiki.App().App_type().Uid_is_gui()) return true;		// NOTE: ignore during Server / Console modes; DATE:2015-04-01
		if (in_progress_hash.Has(wiki.Domain_bry())) return true;	// NOTE: ignore if currently building; different bldr commands call wiki.Init_assert() which may lead to fals checks;
		Io_url url = url_(wiki);
		if (!Io_mgr.I.ExistsFil(url)) return true;
		Xoae_app app = wiki.Appe();
		app.Usr_dlg().Log_many("", "", "import.marker: marker found: url=~{0}", url.Raw());
		byte[] incompete_msg_bry = app.Usere().Msg_mgr().Val_by_key_args(Bry_.new_a7("api-xowa.import.core.incomplete"), wiki.Domain_str());
		int rslt = app.Gui_mgr().Kit().Ask_yes_no_cancel("", "", String_.new_u8(incompete_msg_bry));
		switch (rslt) {
			case Gfui_dlg_msg_.Btn_yes:		Xob_cleanup_cmd.Delete_wiki_sql(wiki); End(wiki); return false;	// delete wiki
			case Gfui_dlg_msg_.Btn_no:		End(wiki); return true;		// delete marker
			case Gfui_dlg_msg_.Btn_cancel:	return true;				// noop
			default:						throw Exc_.new_unhandled(rslt);
		}
	}
	private static Io_url url_(Xowe_wiki wiki) {
		return wiki.Fsys_mgr().Root_dir().GenSubFil(wiki.Domain_str() + "-import.lock");
	}
}
