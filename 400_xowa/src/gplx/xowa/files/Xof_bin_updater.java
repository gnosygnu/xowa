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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.fsdb.data.*; import gplx.fsdb.meta.*; import gplx.xowa.files.fsdb.*;
public class Xof_bin_updater {
	private final    Fsd_thm_itm tmp_thm_itm = Fsd_thm_itm.new_(); 
	public int Save_bin(Fsm_mnt_itm mnt, Fsm_atr_fil atr_fil, Fsm_bin_fil bin_fil, Xof_fsdb_itm fsdb, Io_stream_rdr rdr, long rdr_len) {
		int db_uid = -1;
		int orig_ext_id = fsdb.Orig_ext().Id();
		if (fsdb.File_is_orig()) {
			if (fsdb.Orig_ext().Id_is_image()) {
				Fsd_img_itm img = mnt.Insert_img(atr_fil, bin_fil, fsdb.Orig_repo_name(), fsdb.Orig_ttl(), orig_ext_id, fsdb.Orig_w(), fsdb.Orig_h(), rdr_len, rdr);
				db_uid = img.Fil_id();
			}
			else {	// adds .pdf and .djvu b/c latter does not have w,h for fsdb_img
				Fsd_fil_itm fil = mnt.Insert_fil(atr_fil, bin_fil, fsdb.Orig_repo_name(), fsdb.Orig_ttl(), orig_ext_id, rdr_len, rdr);
				db_uid = fil.Fil_id();
			}
		}
		else {
			mnt.Insert_thm(tmp_thm_itm, atr_fil, bin_fil, fsdb.Orig_repo_name(), fsdb.Orig_ttl(), orig_ext_id, fsdb.Html_w(), fsdb.Html_h(), fsdb.Lnki_time(), fsdb.Lnki_page(), rdr_len, rdr);
			db_uid = tmp_thm_itm.Thm_id();
		}
		mnt.Cfg_mgr().Next_id_commit();
		return db_uid;
	}
}
