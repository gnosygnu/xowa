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
public class Io_stream_rdr__adp implements Io_stream_rdr {
	private java.io.InputStream strm;	
	public Io_stream_rdr__adp(java.io.InputStream strm) {this.strm = strm;} 
	public Object Under() {return strm;}
	public byte Tid() {return Io_stream_tid_.Tid__raw;}
	public boolean Exists() {return len > 0;}
	public Io_url Url() {return url;} public Io_stream_rdr Url_(Io_url v) {this.url = v; return this;} private Io_url url;
	public long Len() {return len;} public Io_stream_rdr Len_(long v) {len = v; return this;} private long len = Io_mgr.Len_null;
	public void Open_mem(byte[] v) {}
	public Io_stream_rdr Open() {return this;}
	public int Read(byte[] bry, int bgn, int len) {
		try {return strm.read(bry, bgn, len);}	
		catch (Exception e) {throw Err_.new_exc(e, "io", "read failed", "bgn", bgn, "len", len);}
	}
	public long Skip(long len) {
		try {return strm.skip(len);}	
		catch (Exception e) {throw Err_.new_exc(e, "io", "skip failed", "len", len);}
	}	
	public void Rls() {
		try {strm.close();} 
		catch (Exception e) {throw Err_.new_exc(e, "io", "close failed", "url", url.Xto_api());}
	}
}
