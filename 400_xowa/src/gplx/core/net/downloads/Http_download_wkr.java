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
package gplx.core.net.downloads; import gplx.*; import gplx.core.*; import gplx.core.net.*;
public interface Http_download_wkr {
	String Fail_msg();
	Io_url Tmp_url();
	Http_download_wkr Make_new();
	long Checkpoint__load_by_trg_fil(Io_url trg_url);
	byte Exec(gplx.core.progs.Gfo_prog_ui prog_ui, String src_str, Io_url trg_url, long expd_size);
	void Exec_cleanup();
}
