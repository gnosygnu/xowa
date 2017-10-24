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
public class IoStream_mock implements IoStream {
	public byte[] Data_bry() {return data_bry;} public IoStream_mock Data_bry_(byte[] v) {data_bry = v; data_bry_len = v.length; return this;} private byte[] data_bry; int data_bry_len;
	public int Data_bry_pos() {return data_bry_pos;} int data_bry_pos;
	public void Reset() {data_bry_pos = 0;}
	public IoStream_mock Read_limit_(int v) {read_limit = v; return this;} int read_limit;
	public int Read(byte[] bfr, int bfr_bgn, int bfr_len) {
		int bytes_read = bfr_len;
		if (bytes_read > read_limit) bytes_read = read_limit;	// stream may limit maximum read; EX: bfr_len of 16k but only 2k will be filled
		int bytes_left = data_bry_len - data_bry_pos;
		if (bytes_read > bytes_left) bytes_read = bytes_left;	// not enough bytes left in data_bry; bytes_read = whatever is left
		Bry_.Copy_by_pos(data_bry, data_bry_pos, data_bry_pos + bytes_read, bfr, bfr_bgn);
		data_bry_pos += bytes_read;
		return bytes_read;
	}
	public Object UnderRdr() {return null;}
	public Io_url Url() {return Io_url_.Empty;}
	public long Pos() {return -1;}
	public long Len() {return -1;}
	public int ReadAry(byte[] array) {return -1;}
	public long Seek(long pos) {return -1;}
	public void WriteAry(byte[] ary) {}
	public void Write(byte[] array, int offset, int count) {}
	public void Transfer(IoStream trg, int bufferLength) {}
	public void Flush() {}
	public void Write_and_flush(byte[] bry, int bgn, int end) {}
	public void Rls() {}
}
