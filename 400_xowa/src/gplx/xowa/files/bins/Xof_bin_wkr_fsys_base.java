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
import gplx.xowa.files.fsdb.*;
public abstract class Xof_bin_wkr_fsys_base implements Xof_bin_wkr, GfoInvkAble {
	public Xof_bin_wkr_fsys_base() {}
	public abstract byte Bin_wkr_tid();
	public boolean Bin_wkr_resize() {return bin_wkr_resize;} public void Bin_wkr_resize_(boolean v) {bin_wkr_resize = v;} private boolean bin_wkr_resize = false;
	public gplx.ios.Io_stream_rdr Bin_wkr_get_as_rdr(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w) {
		byte mode = is_thumb ? Xof_repo_itm.Mode_thumb : Xof_repo_itm.Mode_orig;
		Io_url src_url = this.Get_src_url(mode, String_.new_utf8_(itm.Orig_wiki()), itm.Lnki_ttl(), itm.Lnki_md5(), itm.Lnki_ext(), w, itm.Lnki_thumbtime(), itm.Lnki_page());
		return (src_url == Io_url_.Null) ? gplx.ios.Io_stream_rdr_.Null : gplx.ios.Io_stream_rdr_.file_(src_url);
	}
	public boolean Bin_wkr_get_to_url(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url) {
		return Save_to_url(itm.Orig_wiki(), itm.Lnki_ttl(), itm.Lnki_md5(), itm.Lnki_ext(), is_thumb, w, itm.Lnki_thumbtime(), itm.Lnki_page(), bin_url);
	}
	public boolean Save_to_url(byte[] orig_repo, byte[] orig_ttl, byte[] orig_md5, Xof_ext orig_ext, boolean lnki_is_thumb, int file_w, double lnki_time, int lnki_page, Io_url file_url) {
		byte mode = lnki_is_thumb ? Xof_repo_itm.Mode_thumb : Xof_repo_itm.Mode_orig;
		Io_url src_url = this.Get_src_url(mode, String_.new_utf8_(orig_repo), orig_ttl, orig_md5, orig_ext, file_w, lnki_time, lnki_page);
		if (src_url == Io_url_.Null) return false;
		byte[] bin = Io_mgr._.LoadFilBry(src_url);
		return bin != Io_mgr.LoadFilBry_fail;
	}
	protected abstract Io_url Get_src_url(byte mode, String wiki, byte[] ttl_wo_ns, byte[] md5, Xof_ext ext, int w, double time, int page);
	public abstract void Url_(Io_url v);
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_url_))		this.Url_(m.ReadIoUrl("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_url_ = "url_";
}
abstract class Xof_bin_wkr_fsys_wmf_base extends Xof_bin_wkr_fsys_base {
	public Xof_url_bldr Url_bldr() {return url_bldr;} private Xof_url_bldr url_bldr = new Xof_url_bldr();
	public abstract void Init_by_root();
	@Override public void Url_(Io_url v) {url_bldr.Root_(Bry_.new_utf8_(v.Raw()));}
	@Override protected Io_url Get_src_url(byte mode, String wiki, byte[] ttl_wo_ns, byte[] md5, Xof_ext ext, int w, double time, int page) {
		return this.Url_bldr().Init_by_itm(mode, ttl_wo_ns, md5, ext, w, time, page).Xto_url();
	}
}
class Xof_bin_wkr_fsys_wmf extends Xof_bin_wkr_fsys_wmf_base {
	@Override public byte Bin_wkr_tid() {return Xof_bin_wkr_.Tid_fsys_wmf;}
	@Override public void Init_by_root() {
		this.Url_bldr().Init_by_root(Bry_.Empty, Op_sys.Cur().Fsys_dir_spr_byte(), Bool_.Y, Bool_.Y, Xof_repo_itm.Dir_depth_wmf);
	}
}
class Xof_bin_wkr_fsys_xowa extends Xof_bin_wkr_fsys_wmf_base {
	@Override public byte Bin_wkr_tid() {return Xof_bin_wkr_.Tid_fsys_xowa;}
	@Override public void Init_by_root() {
		this.Url_bldr().Init_by_root(Bry_.Empty, Op_sys.Cur().Fsys_dir_spr_byte(), Bool_.N, Bool_.N, Xof_repo_itm.Dir_depth_xowa);
	}
}
