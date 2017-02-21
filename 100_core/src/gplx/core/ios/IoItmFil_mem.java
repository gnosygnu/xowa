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
import gplx.core.ios.streams.*; /*IoStream_mem*/ import gplx.core.texts.*; /*Encoding_*/
class IoItmFil_mem extends IoItmFil {		public static IoItmFil_mem as_(Object obj) {return obj instanceof IoItmFil_mem ? (IoItmFil_mem)obj : null;}
	@gplx.Internal protected IoStream_mem Stream() {return stream;} IoStream_mem stream;	// NOTE: using stream instead of Text, b/c no events for IoStream.Dispose; ex: stream.OpenStreamWrite; stream.Write("hi"); stream.Dispose(); "hi" would not be saved if Text is member variable
	@Override public long Size() {return (int)stream.Len();}
	public String Text() {return Text_get();} public void Text_set(String v) {stream = IoStream_mem.rdr_txt_(this.Url(), v);}
	String Text_get() {
		int len = (int)stream.Len();
		byte[] buffer = new byte[len];
		stream.Position_set(0);
		stream.Read(buffer, 0, len);
		return String_.new_u8(buffer);
	}
	public IoItmFil_mem Clone() {return new_(this.Url(), this.Size(), this.ModifiedTime(), this.Text());}
	public static IoItmFil_mem new_(Io_url filPath, long size, DateAdp modified, String text) {
		IoItmFil_mem rv = new IoItmFil_mem();
		rv.ctor_IoItmFil(filPath, size, modified);
		rv.stream = IoStream_mem.rdr_txt_(filPath, text);
		return rv;
	}
	public static final    IoItmFil_mem Null = new_(Io_url_.Empty, -1, DateAdp_.MinValue, "");	// NOTE: size must be -1 for .Exists to be false; DATE:2015-05-16
}
