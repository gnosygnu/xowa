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
package gplx.xowa.bldrs.cmds.texts.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.ios.*; import gplx.xowa.tdbs.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_make_id_wkr extends Xob_itm_dump_base implements Xobd_wkr, GfoInvkAble {
	public Xob_make_id_wkr(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Wkr_key() {return KEY;} public static final String KEY = "core.make_id";
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_bgn(Xob_bldr bldr) {
		this.Init_dump(KEY, wiki.Tdb_fsys_mgr().Site_dir().GenSubDir(Xotdb_dir_info_.Name_id));
	}
	public void Wkr_run(Xowd_page_itm page) {
		byte[] ttl = page.Ttl_page_db();
		if (dump_bfr.Len() + row_fixed_len + ttl.length > dump_fil_len) Io_mgr._.AppendFilBfr(dump_url_gen.Nxt_url(), dump_bfr);
		Xotdb_page_itm_.Txt_id_save(dump_bfr, page);
	}
	public void Wkr_end() {
		this.Term_dump(new Xob_make_cmd_site(bldr.Usr_dlg(), make_dir, make_fil_len));
		if (delete_temp) Io_mgr._.DeleteDirDeep(temp_dir);
	}
	public void Wkr_print() {}
	static final int row_fixed_len = 25 + 1 + 7;	// 25=5 base_85 flds; 1=Redirect; 7=dlm
}
