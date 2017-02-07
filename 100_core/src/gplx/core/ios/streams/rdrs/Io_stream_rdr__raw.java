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
public class Io_stream_rdr__raw extends Io_stream_rdr__base {
	public byte Tid() {return Io_stream_tid_.Tid__raw;}
		public Io_stream_rdr Open() {
		Io_url url = this.Url();
		try {
			if (!Io_mgr.Instance.Exists(url))
				stream = Wrap_stream(new java.io.ByteArrayInputStream(Bry_.Empty));
			else {
				if (url.Info().EngineKey() == IoEngine_.MemKey)
					stream = Wrap_stream(new java.io.ByteArrayInputStream(Io_mgr.Instance.LoadFilBry(url.Xto_api())));
				else
					stream = Wrap_stream(new java.io.FileInputStream(url.Xto_api()));
			}
		}
		catch (Exception e) {throw Err_.new_exc(e, "io", "open failed", "url", url.Xto_api());}
		return this;
	}
	@Override public java.io.InputStream Wrap_stream(java.io.InputStream stream) {return stream;}
	}
