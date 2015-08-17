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
import gplx.ios.*;
public class Io_download_mgr__memory implements Io_download_mgr {
	private final Ordered_hash hash = Ordered_hash_.new_();
	public void	Clear() {hash.Clear();}
	public void Upload_data(String url, byte[] data) {hash.Add(url, data);}
	public Io_stream_rdr Download_as_rdr(String url) {
		byte[] data = (byte[])hash.Get_by(url); if (data == null) return Io_stream_rdr_.Noop;
		return Io_stream_rdr_.mem_(data);
	}
}
