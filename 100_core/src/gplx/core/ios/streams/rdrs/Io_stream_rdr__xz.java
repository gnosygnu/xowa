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
public class Io_stream_rdr__xz extends Io_stream_rdr__base {
	@Override public byte Tid() {return Io_stream_tid_.Tid__xz;}
		@Override public java.io.InputStream Wrap_stream(java.io.InputStream stream) {
		try {return new org.tukaani.xz.XZInputStream(stream);}
		catch (Exception exc) {throw Err_.new_wo_type("failed to open xz stream");}
	}
	}
