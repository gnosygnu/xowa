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
package gplx.core.net; import gplx.*; import gplx.core.*;
import gplx.core.ios.*;
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
