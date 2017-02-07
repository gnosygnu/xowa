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
package gplx.xowa.apps.gfs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.langs.gfs.*;
public class Xoa_gfs_mgr_ {
	public static GfoMsg Parse_to_msg(String v) {return Gfs_msg_bldr.Instance.ParseToMsg(v);}
	public static void Cfg_os_assert(Io_url orig_url) {
		Io_url dflt_url = orig_url.GenNewNameOnly(orig_url.NameOnly() + "_default");
		if (!Io_mgr.Instance.ExistsFil(dflt_url)) return;	// no dflt
		if (!Io_mgr.Instance.ExistsFil(orig_url)) {
			Io_mgr.Instance.CopyFil(dflt_url, orig_url, true);
			Gfo_usr_dlg_.Instance.Log_many("", "", "xowa_cfg_os generated; url=~{0}", orig_url.Raw());
		}
	}
}
