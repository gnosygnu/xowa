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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa2.files.orig_regy.*;
public class Xofv_file_itm {
	Xofv_file_itm(byte[] file_repo, byte[] file_ttl, byte[] file_md5, Xof_ext file_ext, Io_url file_url, int html_uid, int html_w, int html_h, byte lnki_exec_tid, boolean lnki_is_orig, double lnki_time, int lnki_page) {
		this.file_repo = file_repo; this.file_ttl = file_ttl; this.file_md5 = file_md5; this.file_ext = file_ext; this.file_url = file_url;
		this.html_uid = html_uid; this.html_w = html_w; this.html_h = html_h;
		this.lnki_exec_tid = lnki_exec_tid; this.lnki_is_orig = lnki_is_orig; this.lnki_time = lnki_time; this.lnki_page = lnki_page;
	}
	public byte[] File_repo() {return file_repo;} private final byte[] file_repo;
	public byte[] File_ttl() {return file_ttl;} private final byte[] file_ttl;
	public byte[] File_md5() {return file_md5;} private final byte[] file_md5;
	public Xof_ext File_ext() {return file_ext;} private final Xof_ext file_ext;
	public Io_url File_url() {return file_url;} private final Io_url file_url;
	public long File_size() {return file_size;} public void File_size_(long v) {file_size = v;} private long file_size;
	public int Html_uid() {return html_uid;} private final int html_uid;
	public int Html_w() {return html_w;} private final int html_w;
	public int Html_h() {return html_h;} private final int html_h;
	public byte Lnki_exec_tid() {return lnki_exec_tid;} private final byte lnki_exec_tid;
	public boolean Lnki_is_orig() {return lnki_is_orig;} private final boolean lnki_is_orig;
	public double Lnki_time() {return lnki_time;} private final double lnki_time;
	public int Lnki_page() {return lnki_page;} private final int lnki_page;
	public static Xofv_file_itm new_(Xof_xfer_itm xfer, Xof_orig_regy_itm orig, Xofv_repo_mgr repo_mgr, Xof_img_size img_size, Xof_url_bldr url_bldr) {
		Xofv_repo_itm repo = repo_mgr.Get_by_tid(orig.Repo_tid());
		byte[] file_repo = repo.Key();
		byte[] file_ttl = orig.Orig_redirect(); if (file_ttl == null) file_ttl = orig.Ttl();
		byte[] file_md5 = Xof_xfer_itm_.Md5_(file_ttl);
		Xof_ext file_ext = Xof_ext_.new_by_ttl_(file_ttl);
		byte lnki_exec_tid = xfer.Lnki_exec_tid();
		boolean lnki_is_orig = xfer.Img_is_orig();
		double lnki_time = xfer.Lnki_thumbtime();
		int lnki_page = xfer.Lnki_page();
		int html_uid = xfer.Html_uid();
		img_size.Html_size_calc
			( xfer.Lnki_exec_tid(), xfer.Lnki_w(), xfer.Lnki_h(), xfer.Lnki_type(), Xof_patch_upright_tid_.Tid_all, xfer.Lnki_upright()
			, file_ext.Id(), orig.Orig_w(), orig.Orig_h(), Xof_img_size.Thumb_width_img);
		int html_w = img_size.Html_w();
		int html_h = img_size.Html_h();
		byte repo_mode = 0; byte[] repo_dir_sub = null;
		if (xfer.Img_is_orig()) {
			repo_mode = Xof_repo_itm.Mode_orig;
			repo_dir_sub = repo.Dir_sub_orig();
		}
		else {
			repo_mode = Xof_repo_itm.Mode_thumb;
			repo_dir_sub = repo.Dir_sub_thumb();
		}
		url_bldr.Init(Bool_.N, Bool_.N, repo.Dir_spr(), repo.Dir_root(), repo_dir_sub, Xof_url_bldr.Md5_dir_depth_2
		, file_ttl, file_md5, file_ext, repo_mode
		, html_w, lnki_time, lnki_page
		);
		Io_url file_url = url_bldr.Xto_url();
		return new Xofv_file_itm(file_repo, file_ttl, file_md5, file_ext, file_url, html_uid, html_w, html_h, lnki_exec_tid, lnki_is_orig, lnki_time, lnki_page);
	}
}
