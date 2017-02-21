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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
public class Io_download_mgr__memory implements Io_download_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public void	Clear() {hash.Clear();}
	public void Upload_data(String url, byte[] data) {hash.Add(url, data);}
	public Io_stream_rdr Download_as_rdr(String url) {
		byte[] data = (byte[])hash.Get_by(url); if (data == null) return Io_stream_rdr_.Noop;
		return Io_stream_rdr_.New__mem(data);
	}
}
