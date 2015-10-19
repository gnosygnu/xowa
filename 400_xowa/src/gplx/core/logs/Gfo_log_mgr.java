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
package gplx.core.logs; import gplx.*; import gplx.core.*;
public class Gfo_log_mgr {
	private final Ordered_hash fil_list = Ordered_hash_.New();
	private final Gfo_log_fil session_fil;
	private final Io_url dir;
	private final long size_dflt = Io_mgr.Len_mb * 2;
	public Gfo_log_mgr(Io_url dir) {
		this.dir = dir;
		this.session_fil = new Gfo_log_fil(null, "session", dir, -1);
	}
	public Gfo_log_fil Fils__get_or_new(String key) {
		Gfo_log_fil rv = (Gfo_log_fil)fil_list.Get_by(key);
		if (rv == null) {
			rv = new Gfo_log_fil(session_fil, key, dir, size_dflt);
			fil_list.Add(key, rv);
		}
		return rv;
	}
	public void Msgs__add(String fil_key, String msg, Object... vals) {
		Gfo_log_fil fil = (Gfo_log_fil)Fils__get_or_new(fil_key);
		fil.Add(msg, vals);
	}
}
//	class Some_log_cls {
//		private final Gfo_log_mgr log_mgr = new Gfo_log_mgr();
//		public void Init() {
//		}
//		private Gfo_log_fil dedicated;
//		public void Init_dedicated() {
//			dedicated = log_mgr.Fil_get_or_new("parse");
//		}
//		public void Proc_w_dedicate() {
//			dedicated.Add("file download failed", "url", "msg");
//		}
//		public void Proc_dynamic() {
//			log_mgr.Msg_add("parse", "file download failed", "url", "msg");
//		}
//	}
