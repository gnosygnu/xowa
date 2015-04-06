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
import gplx.ios.*;
import gplx.fsdb.data.*; import gplx.fsdb.meta.*; import gplx.xowa.files.fsdb.*;
public class Xof_bin_updater {
	private final Fsd_img_itm tmp_img_itm = new Fsd_img_itm(); private final Fsd_thm_itm tmp_thm_itm = Fsd_thm_itm.new_(); private final Fsd_fil_itm tmp_fil_itm = new Fsd_fil_itm();		
	public int Save_bin(Fsm_mnt_itm mnt, Fsm_atr_fil atr_fil, Fsm_bin_fil bin_fil, Xof_fsdb_itm fsdb, Io_stream_rdr rdr, long rdr_len) {
		int db_uid = -1;
		int lnki_ext_id = fsdb.Lnki_ext().Id();
		if (fsdb.File_is_orig()) {
			if (fsdb.Lnki_ext().Id_is_image()) {	// does not add .pdf and .djvu b/c latter do not have w,h for fsdb_img
				mnt.Insert_img(tmp_img_itm, atr_fil, bin_fil, fsdb.Orig_repo_name(), fsdb.Lnki_ttl(), lnki_ext_id, fsdb.Orig_w(), fsdb.Orig_h(), rdr_len, rdr);
				db_uid = tmp_img_itm.Fil_id();
			}
			else {
				mnt.Insert_fil(tmp_fil_itm, atr_fil, bin_fil, fsdb.Orig_repo_name(), fsdb.Lnki_ttl(), lnki_ext_id, rdr_len, rdr);
				db_uid = tmp_fil_itm.Fil_id();
			}
		}
		else {
			mnt.Insert_thm(tmp_thm_itm, atr_fil, bin_fil, fsdb.Orig_repo_name(), fsdb.Lnki_ttl(), lnki_ext_id, fsdb.Html_w(), fsdb.Html_h(), fsdb.Lnki_time(), fsdb.Lnki_page(), rdr_len, rdr);
			db_uid = tmp_thm_itm.Thm_id();
		}
		mnt.Cfg_mgr().Next_id_commit();
		return db_uid;
	}
}
