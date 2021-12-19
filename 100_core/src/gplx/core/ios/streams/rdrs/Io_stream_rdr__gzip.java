/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios.streams.rdrs;
import gplx.core.ios.streams.Io_stream_rdr_;
import gplx.core.ios.streams.Io_stream_tid_;
import gplx.types.errs.ErrUtl;
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
			return total_read == 0 ? Io_stream_rdr_.Read_done : total_read;    // gzip seems to allow 0 bytes read (bz2 and zip return -1 instead); normalize return to -1;
		}
		catch (Exception e) {
			throw ErrUtl.NewArgs(e, "read failed", "bgn", bgn, "len", len);
		}
		}
	}
	@Override public java.io.InputStream Wrap_stream(java.io.InputStream stream) {
		try {return new java.util.zip.GZIPInputStream(stream);}
		catch (Exception exc) {throw ErrUtl.NewArgs("failed to open gz stream");}
	}
	}
