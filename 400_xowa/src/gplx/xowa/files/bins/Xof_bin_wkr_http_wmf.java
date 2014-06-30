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
import gplx.ios.*; import gplx.threads.*; import gplx.xowa.files.fsdb.*;
public class Xof_bin_wkr_http_wmf implements Xof_bin_wkr {
	private Xow_repo_mgr repo_mgr; private gplx.ios.IoEngine_xrg_downloadFil download; 
	private Xof_url_bldr url_bldr = new Xof_url_bldr();
	public int Fail_timeout() {return fail_timeout;} public Xof_bin_wkr_http_wmf Fail_timeout_(int v) {fail_timeout = v; return this;} private int fail_timeout = 0;	// NOTE: always default to 0; manually set to 1000 in fsdb_make; DATE:2014-06-21
	public boolean Bin_wkr_resize() {return bin_wkr_resize;} public void Bin_wkr_resize_(boolean v) {bin_wkr_resize = v;} private boolean bin_wkr_resize = true;	// NOTE: default to true
	public Xof_bin_wkr_http_wmf(Xow_repo_mgr repo_mgr, gplx.ios.IoEngine_xrg_downloadFil download) {
		this.repo_mgr = repo_mgr; this.download = download;
	}
	public byte Bin_wkr_tid() {return Xof_bin_wkr_.Tid_http_wmf;}
	public Io_stream_rdr Bin_wkr_get_as_rdr(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w) {
		Bin_wkr_get(itm, is_thumb, w, Io_url_.Null);
		Io_stream_rdr rdr = download.Exec_as_rdr();
		boolean rv = rdr.Len() != IoItmFil.Size_Invalid;	// NOTE: use IoItmFil.Size_Invalid, not Io_stream_rdr_.Read_done; DATE:2014-06-21
		if (!rv) Handle_error();
		return rv ? rdr : Io_stream_rdr_.Null;
	}
	public boolean Bin_wkr_get_to_url(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url) {
		Bin_wkr_get(itm, is_thumb, w, bin_url);
		boolean rv = download.Exec();
		if (!rv) Handle_error();
		return rv;
	}
	private void Handle_error() {
		if (fail_timeout > 0)
			ThreadAdp_.Sleep(fail_timeout);	// as per WMF policy, pause 1 second for every cache miss; http://lists.wikimedia.org/pipermail/wikitech-l/2013-September/071948.html
	}
	private void Bin_wkr_get(Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url) {
		byte mode = is_thumb ? Xof_repo_itm.Mode_thumb : Xof_repo_itm.Mode_orig;
		Xof_repo_pair repo_itm = repo_mgr.Repos_get_by_wiki(itm.Orig_wiki());
		String queue_msg = String_.Format("downloading ~{0} of ~{1}: ~{2};", 0, 0, String_.new_utf8_(itm.Lnki_ttl()));
		download.Prog_fmt_hdr_(queue_msg);
		String src = url_bldr.Set_src_file_(mode, repo_itm.Src(), itm.Lnki_ttl(), itm.Lnki_md5(), itm.Lnki_ext(), w, itm.Lnki_thumbtime(), itm.Lnki_page()).Xto_str();
		download.Init(src, bin_url);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_fail_timeout_))		fail_timeout = m.ReadInt("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_fail_timeout_ = "fail_timeout_";

}
