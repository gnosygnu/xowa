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
import gplx.ios.*; import gplx.xowa.files.fsdb.*;
import gplx.dbs.*; 
public class Xof_bin_wkr_fsdb_regy implements Xof_bin_wkr {
	private Io_url regy_url; private Db_conn conn;
	public Xof_bin_wkr_fsdb_regy() {
	}
	public boolean Bin_wkr_resize() {return bin_wkr_resize;} public void Bin_wkr_resize_(boolean v) {bin_wkr_resize = v;} private boolean bin_wkr_resize = false;
	public byte Bin_wkr_tid() {return Xof_bin_wkr_.Tid_fsdb_regy;}
	public void Init_by_wiki(Xow_wiki wiki) {
		if (regy_url == null) regy_url = wiki.App().Fsys_mgr().File_dir().GenSubFil("xowa.fsdb_regy.sqlite3");
		if (!Io_mgr._.ExistsFil(regy_url)) throw Err_.new_fmt_("fsdb_regy does not exist: url={0}", regy_url.Raw());
		conn = Sqlite_engine_.Conn_load_or_make_(regy_url);
	}
	public void Rls() {
		conn.Conn_term();
	}
	public Io_stream_rdr Bin_wkr_get_as_rdr(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w) {
		return null;
	}
	public boolean Bin_wkr_get_to_url(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url) {
		return true;
	}
	public boolean Save_to_url(byte[] orig_repo, byte[] orig_ttl, byte[] orig_md5, Xof_ext orig_ext, boolean lnki_is_thumb, int file_w, double lnki_time, int lnki_page, Io_url file_url) {return true;}
	public void Bin_wkr_get(Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url) {
		// get entry; if not exists return null;
		// if exists, open up bin_db
		// return bin
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_regy_url_))		regy_url = m.ReadIoUrl("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_regy_url_ = "regy_url_";
}
