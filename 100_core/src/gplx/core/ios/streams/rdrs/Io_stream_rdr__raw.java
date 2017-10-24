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
package gplx.core.ios.streams.rdrs; import gplx.*; import gplx.core.*; import gplx.core.ios.*; import gplx.core.ios.streams.*;
public class Io_stream_rdr__raw extends Io_stream_rdr__base {
	public byte Tid() {return Io_stream_tid_.Tid__raw;}
		public Io_stream_rdr Open() {
		Io_url url = this.Url();
		try {
			if (!Io_mgr.Instance.Exists(url))
				stream = Wrap_stream(new java.io.ByteArrayInputStream(Bry_.Empty));
			else {
				if (url.Info().EngineKey() == IoEngine_.MemKey)
					stream = Wrap_stream(new java.io.ByteArrayInputStream(Io_mgr.Instance.LoadFilBry(url.Xto_api())));
				else
					stream = Wrap_stream(new java.io.FileInputStream(url.Xto_api()));
			}
		}
		catch (Exception e) {throw Err_.new_exc(e, "io", "open failed", "url", url.Xto_api());}
		return this;
	}
	@Override public java.io.InputStream Wrap_stream(java.io.InputStream stream) {return stream;}
	}
