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
package gplx.xowa.files.fsdb; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.fsdb.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.qrys.*; import gplx.xowa.files.wiki_orig.*;
import gplx.xowa.files.fsdb.caches.*;
import gplx.xowa.files.gui.*;
public interface Xof_fsdb_mgr extends RlsAble {
	boolean Tid_is_mem();
	int Patch_upright();
	Xow_wiki Wiki();
	Xof_qry_mgr Qry_mgr();
	Xof_bin_mgr Bin_mgr();
	Xof_bin_wkr Bin_wkr_fsdb();
	Fsdb_mnt_mgr Mnt_mgr();
	Cache_mgr Cache_mgr();
	void Db_bin_max_(long v);
	void Init_by_wiki(Xow_wiki wiki, Io_url db_dir, Io_url fs_dir, Xow_repo_mgr repo_mgr);
	boolean Init_by_wiki(Xow_wiki wiki);
	boolean Init_by_wiki__add_bin_wkrs(Xow_wiki wiki);

	boolean Orig_exists_by_ttl(byte[] ttl);
	void Orig_select_by_list(Xoa_page page, byte exec_tid, ListAdp itms, OrderedHash hash);
	void Fsdb_search_by_list(Xoa_page page, byte exec_tid, ListAdp itms);

	void Img_insert(Fsdb_xtn_img_itm rv, byte[] dir, byte[] fil, int ext_id, int img_w, int img_h, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr);
	void Thm_insert(Fsdb_xtn_thm_itm rv, byte[] dir, byte[] fil, int ext_id, int thm_w, int thm_h, double thumbtime, int page, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr);
	void Fil_insert(Fsdb_fil_itm rv    , byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr);
	void Orig_insert(Xof_fsdb_itm itm, byte repo_id, byte status);
}
