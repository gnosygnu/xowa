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
public class Io_stream_rdr__noop implements Io_stream_rdr {
	public Object Under() {return null;}
	public byte Tid() {return Io_stream_tid_.Tid__null;}
	public boolean Exists() {return false;}
	public Io_url Url() {return Io_url_.Empty;} public Io_stream_rdr Url_(Io_url v) {return this;}
	public long Len() {return Io_mgr.Len_null;} public Io_stream_rdr Len_(long v) {return this;}
	public void Open_mem(byte[] v) {}
	public Io_stream_rdr Open() {return this;}
	public int Read(byte[] bry, int bgn, int len) {return Io_stream_rdr_.Read_done;}
	public long Skip(long len) {return Io_stream_rdr_.Read_done;}
	public void Rls() {}
}
