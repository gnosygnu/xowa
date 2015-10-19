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
package gplx.core.net; import gplx.*; import gplx.core.*;
import gplx.ios.*;
class Gfo_inet_conn__http implements Gfo_inet_conn {
	private final IoEngine_xrg_downloadFil downloader = IoEngine_xrg_downloadFil.new_("", Io_url_.Empty);
	public int				Tid() {return Gfo_inet_conn_.Tid__http;}
	public void				Clear()										{throw Err_.new_unsupported();}
	public void				Upload_by_bytes(String url, byte[] data)	{throw Err_.new_unsupported();}
	public byte[]			Download_as_bytes_or_null(String url) {
		try {return downloader.Exec_as_bry(url);}
		catch (Exception e) {Err_.Noop(e); return null;}
	}
}
