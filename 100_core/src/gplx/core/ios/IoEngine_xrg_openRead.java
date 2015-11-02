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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class IoEngine_xrg_openRead {
	public Io_url Url() {return url;} Io_url url;
	public String ErrMsg() {return errMsg;} private String errMsg;
	public IoStream ExecAsIoStreamOrFail() {return IoEnginePool.Instance.Get_by(url.Info().EngineKey()).OpenStreamRead(url);}
	public IoStream ExecAsIoStreamOrNull() {
		try {return IoEnginePool.Instance.Get_by(url.Info().EngineKey()).OpenStreamRead(url);}
		catch (Exception exc) {
			errMsg = Err_.Message_lang(exc);
			return IoStream_.Null;
		}
	}
	public static IoEngine_xrg_openRead new_(Io_url url) {
		IoEngine_xrg_openRead rv = new IoEngine_xrg_openRead();
		rv.url = url;
		return rv;
	}	IoEngine_xrg_openRead() {}
}
