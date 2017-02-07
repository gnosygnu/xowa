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
package gplx.core.ios.streams.wtrs; import gplx.*; import gplx.core.*; import gplx.core.ios.*; import gplx.core.ios.streams.*;
public class Io_stream_wtr__bzip2 extends Io_stream_wtr__base {
	@Override public byte Tid() {return Io_stream_tid_.Tid__bzip2;}
		@Override public java.io.OutputStream Wrap_stream(java.io.OutputStream stream) {
		try {return new org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream(stream);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "failed to open bzip2 stream");}
	}
	}
