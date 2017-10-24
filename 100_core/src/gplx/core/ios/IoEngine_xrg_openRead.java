/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.ios.streams.*;
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
