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
public abstract class Io_stream_rdr__base implements Io_stream_rdr {
	public abstract byte Tid();
	public Io_url Url() {return url;} protected Io_url url;
	public long Len() {return len;} private long len = Io_mgr.Len_null;
	public boolean Exists() {return this.Len() > 0;}
	public Io_stream_rdr Url_(Io_url v) {this.url = v; return this;}
	public Io_stream_rdr Len_(long v) {len = v; return this;}
		public Object Under() {return stream;} public Io_stream_rdr Under_(java.io.InputStream v) {this.stream = v; return this;} protected java.io.InputStream stream;
	public void Open_mem(byte[] v) {
		stream = Wrap_stream(new java.io.ByteArrayInputStream(v));
	}
	public Io_stream_rdr Open() {
		try {stream = Wrap_stream(new java.io.FileInputStream(url.Xto_api()));}
		catch (Exception e) {throw Err_.new_exc(e, "io", "open failed", "url", url.Xto_api());}
		return this;
	}
	public int Read(byte[] bry, int bgn, int len) {
		try {return stream.read(bry, bgn, len);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "read failed", "bgn", bgn, "len", len);}
	}
	public long Skip(long len) {		
		try {return stream.skip(len);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "skip failed", "len", len);}
	}
	public void Rls() {
		try {stream.close();}
		catch (Exception e) {throw Err_.new_exc(e, "io", "close failed", "url", url.Xto_api());}
	}
	public abstract java.io.InputStream Wrap_stream(java.io.InputStream stream);
	}
