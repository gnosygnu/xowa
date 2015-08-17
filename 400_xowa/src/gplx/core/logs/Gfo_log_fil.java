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
public class Gfo_log_fil {
	private final Bry_bfr fil_bfr = Bry_bfr.new_(), msg_bfr = Bry_bfr.new_();
	private final String key;
	private final Io_url dir;
	private final long size_max;
	private int file_idx;
	private Io_url fil_cur;
	private final Gfo_log_fmtr fmtr = new Gfo_log_fmtr();
	private final Gfo_log_fil session;
	public Gfo_log_fil(Gfo_log_fil session, String key, Io_url dir, long size_max) {
		this.session = session;
		this.key = key;
		this.dir = dir;
		this.size_max = size_max;
		this.fil_cur = Fil_new();
	}
	public void Add(String msg, Object... vals) {
		fmtr.Add(msg_bfr, msg, vals);
		Add_by_bfr(msg_bfr);
		msg_bfr.Clear();
	}
	public void Add_by_bfr(Bry_bfr msg_bfr) {
		if (msg_bfr.Len() + fil_bfr.Len() > size_max) {
			this.Flush();
			fil_cur = Fil_new();
		}
		fil_bfr.Add_bfr_and_preserve(msg_bfr);
		if (session != null) session.Add_by_bfr(msg_bfr);
	}
	public void Flush() {
		Io_mgr.I.AppendFilBfr(fil_cur, fil_bfr);
	}
	private Io_url Fil_new() {
		String part = size_max == -1 ? "" : "-" + Int_.Xto_str(++file_idx);
		return dir.OwnerDir().GenSubFil_ary(key, part, ".log");
	}
}
