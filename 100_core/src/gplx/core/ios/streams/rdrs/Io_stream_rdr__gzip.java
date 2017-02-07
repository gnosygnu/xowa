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
package gplx.core.ios.streams.rdrs; import gplx.*; import gplx.core.*; import gplx.core.ios.*; import gplx.core.ios.streams.*;
public class Io_stream_rdr__gzip extends Io_stream_rdr__base {
	@Override public byte Tid() {return Io_stream_tid_.Tid__gzip;}
		@Override public int Read(byte[] bry, int bgn, int len) {
		synchronized (this) {
		try {
			int total_read = 0;
			while (true) {  // NOTE: the gz stream reads partially; (request 100; only get back 10); keep reading until entire bfr is full or -1
				int read = stream.read(bry, bgn, len);
				if (read == Io_stream_rdr_.Read_done) break;
				total_read += read;
				if (total_read >= len) break;  // entire bfr full; stop
				bgn += read;  // increase bgn by amount read
				len -= read;  // decrease len by amount read 
			}
			return total_read == 0 ? Io_stream_rdr_.Read_done : total_read;	// gzip seems to allow 0 bytes read (bz2 and zip return -1 instead); normalize return to -1;
		}
		catch (Exception e) {
			throw Err_.new_exc(e, "io", "read failed", "bgn", bgn, "len", len);
		}
		}
	}
	@Override public java.io.InputStream Wrap_stream(java.io.InputStream stream) {
		try {return new java.util.zip.GZIPInputStream(stream);}
		catch (Exception exc) {throw Err_.new_wo_type("failed to open gz stream");}
	}
	}
