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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.ios.*; import gplx.core.threads.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.repos.*;
public class Xof_bin_wkr__http_wmf implements Xof_bin_wkr {
	private final Xow_repo_mgr repo_mgr; private final IoEngine_xrg_downloadFil download_wkr; 
	private final Xof_url_bldr url_bldr = new Xof_url_bldr();
	public Xof_bin_wkr__http_wmf(Xow_repo_mgr repo_mgr, gplx.ios.IoEngine_xrg_downloadFil download_wkr) {this.repo_mgr = repo_mgr; this.download_wkr = download_wkr;}
	public byte Tid() {return Xof_bin_wkr_.Tid_http_wmf;}
	public String Key() {return Xof_bin_wkr_.Key_http_wmf;}
	public boolean Resize_allowed() {return bin_wkr_resize;} public void Resize_allowed_(boolean v) {bin_wkr_resize = v;} private boolean bin_wkr_resize = true;
	public int Fail_timeout() {return fail_timeout;} public Xof_bin_wkr__http_wmf Fail_timeout_(int v) {fail_timeout = v; return this;} private int fail_timeout = 0;	// NOTE: always default to 0; manually set to 1000 for fsdb_make only; DATE:2014-06-21
	public Io_stream_rdr Get_as_rdr(Xof_fsdb_itm fsdb, boolean is_thumb, int w) {
		Download_init(fsdb.Orig_repo_name(), fsdb.Orig_ttl(), fsdb.Orig_ttl_md5(), fsdb.Orig_ext(), is_thumb, w, fsdb.Lnki_time(), fsdb.Lnki_page(), Io_url_.Empty);
		Io_stream_rdr rdr = download_wkr.Exec_as_rdr();
		boolean rv = rdr.Exists();	// NOTE: use Exists which detects for response_code 200, not content length > 0; DATE:2015-05-20
		if (!rv) Handle_error();
		fsdb.Fsdb_insert_y_();
		return rv ? rdr : Io_stream_rdr_.Noop;
	}
	public boolean Get_to_fsys(Xof_fsdb_itm fsdb, boolean is_thumb, int w, Io_url bin_url) {
		boolean rv = Get_to_fsys(fsdb.Orig_repo_name(), fsdb.Orig_ttl(), fsdb.Orig_ttl_md5(), fsdb.Orig_ext(), is_thumb, w, fsdb.Lnki_time(), fsdb.Lnki_page(), bin_url);
		if (rv) fsdb.Fsdb_insert_y_();
		return rv;
	}
	private boolean Get_to_fsys(byte[] orig_repo, byte[] orig_ttl, byte[] orig_md5, Xof_ext orig_ext, boolean lnki_is_thumb, int file_w, double lnki_time, int lnki_page, Io_url file_url) {
		Download_init(orig_repo, orig_ttl, orig_md5, orig_ext, lnki_is_thumb, file_w, lnki_time, lnki_page, file_url);
		boolean rv = download_wkr.Exec();
		if (!rv) Handle_error();
		return rv;
	}
	private void Handle_error() {
		if (fail_timeout > 0)
			Thread_adp_.Sleep(fail_timeout);	// as per WMF policy, pause 1 second for every cache miss; http://lists.wikimedia.org/pipermail/wikitech-l/2013-September/071948.html
	}
	private void Download_init(byte[] orig_repo, byte[] orig_ttl, byte[] orig_md5, Xof_ext orig_ext, boolean lnki_is_thumb, int file_w, double lnki_time, int lnki_page, Io_url file_url) {
		byte mode = lnki_is_thumb ? Xof_repo_itm_.Mode_thumb : Xof_repo_itm_.Mode_orig;
		Xof_repo_pair repo_itm = repo_mgr.Repos_get_by_wiki(orig_repo);
		String src = url_bldr.Init_for_src_file(mode, repo_itm.Src(), orig_ttl, orig_md5, orig_ext, file_w, lnki_time, lnki_page).Xto_str();
		download_wkr.Init(src, file_url);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_fail_timeout_))		fail_timeout = m.ReadInt("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_fail_timeout_ = "fail_timeout_";
	public static Xof_bin_wkr__http_wmf new_(Xow_wiki wiki) {return new Xof_bin_wkr__http_wmf(wiki.File__repo_mgr(), wiki.App().Wmf_mgr().Download_wkr().Download_xrg());}
}
