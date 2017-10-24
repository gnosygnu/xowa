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
package gplx.core.ios.streams; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import gplx.core.texts.*; /*Encoding_*/
public class IoStream_mem extends IoStream_base {
	@Override public Io_url Url() {return url;} Io_url url;
	@Override public Object UnderRdr() {throw Err_.new_unimplemented();} // NOTE: should not use System.IO.MemoryStream, b/c resized data will not be captured in this instance's buffer
	@Override public long Len() {return Array_.Len(buffer);}
	public int Position() {return position;} public void Position_set(int v) {position = v;} int position;
	public byte[] Buffer() {return buffer;} private byte[] buffer = new byte[0];

	@Override public int Read(byte[] array, int offset, int count) {
		int read = 0;
		int len = Array_.Len(buffer);
		for (int i = 0; i < count; i++) {
			if (position + i >= len) break;
			array[offset + i] = buffer[position + i];
			read++;
		}
		position += read;
		return read;
	}
	@Override public void Write(byte[] array, int offset, int count) {
		// expand buffer if needed; necessary to emulate fileStream writing; ex: FileStream fs = new FileStream(); fs.Write(data); where data may be unknown length
		int length = (int)position + count + -offset;
		int bufLen = Array_.Len(buffer);
		if (bufLen < length) buffer = Bry_.Resize(buffer, length);
		for (int i = 0; i < count; i++)
			buffer[position + i] = array[offset + i];
		position += count +-offset;
	}
	@Override public long Pos() {return position;}
	@Override public long Seek(long pos) {
		this.position = (int)pos;
		return pos;
	}

	@Override public void Flush() {}
	@Override public void Rls() {}

	public static IoStream_mem rdr_txt_(Io_url url, String v) {return rdr_ary_(url, Bry_.new_u8(v));}
	public static IoStream_mem rdr_ary_(Io_url url, byte[] v) {
		IoStream_mem rv = new IoStream_mem();
		rv.buffer = v;
		rv.url = url;
		return rv;
	}
	public static IoStream_mem wtr_data_(Io_url url, int length) {
		IoStream_mem rv = new IoStream_mem();
		rv.buffer = new byte[length];
		rv.url = url;
		return rv;
	}
	IoStream_mem() {}
}
