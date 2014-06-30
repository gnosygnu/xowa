/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.ios; import gplx.*;
public class IoStream_stream_rdr implements IoStream {
	public int Read(byte[] bfr, int bfr_bgn, int bfr_len) {
		try {
						return stream.read(bfr, bfr_bgn, bfr_len);
					}
		catch (Exception e) {throw Err_.err_(e, "failed to read from stream");}
	}
	public IoStream UnderRdr_(Object v) {this.stream = (java.io.InputStream)v; return this;}  java.io.InputStream stream;	
	public Object UnderRdr() {return stream;}
	public Io_url Url() {return Io_url_.Null;}
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
