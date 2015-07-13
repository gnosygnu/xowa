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
package gplx.xowa.files.downloads; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.ios.*;
public class Xof_download_wkr_test implements Xof_download_wkr {
	public IoEngine_xrg_downloadFil Download_xrg() {return IoEngine_xrg_downloadFil.new_("", Io_url_.Empty).Trg_engine_key_(IoEngine_.MemKey);}
	public byte Download(boolean src_is_web, String src_str, Io_url trg_url, String prog_fmt_hdr) {
		Io_mgr.I.CreateDirIfAbsent(trg_url.OwnerDir());
		Io_url src_url = Io_url_.new_fil_(src_str);
		if (!Io_mgr.I.ExistsFil(src_url)) return IoEngine_xrg_downloadFil.Rslt_fail_file_not_found;
		try {Io_mgr.I.CopyFil(src_url, trg_url, true);}
		catch (Exception exc) {Exc_.Noop(exc); return IoEngine_xrg_downloadFil.Rslt_fail_unknown;}
		return IoEngine_xrg_downloadFil.Rslt_pass;
	}
}
