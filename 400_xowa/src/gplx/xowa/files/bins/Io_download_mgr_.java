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
import gplx.core.ios.*; import gplx.core.ios.streams.*;
public class Io_download_mgr_ {
	public static Io_download_mgr			new_system()	{return new Io_download_mgr__system();}
	public static Io_download_mgr__memory	new_memory()	{return new Io_download_mgr__memory();}
}
class Io_download_mgr__system implements Io_download_mgr {
	private final    IoEngine_xrg_downloadFil download_wkr = IoEngine_xrg_downloadFil.new_("", Io_url_.Empty); 
	public void Upload_data(String url, byte[] data) {throw Err_.new_unimplemented();}
	public Io_stream_rdr Download_as_rdr(String url) {
		download_wkr.Init(url, Io_url_.Empty);
		return download_wkr.Exec_as_rdr();
	}
}
