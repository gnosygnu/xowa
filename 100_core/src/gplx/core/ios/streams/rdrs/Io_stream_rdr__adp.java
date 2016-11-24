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
public class Io_stream_rdr__adp implements Io_stream_rdr {
	private java.io.InputStream strm;	
	public Io_stream_rdr__adp(java.io.InputStream strm) {this.strm = strm;} 
	public Object Under() {return strm;}
	public byte Tid() {return Io_stream_tid_.Tid__raw;}
	public boolean Exists() {return len > 0;}
	public Io_url Url() {return url;} public Io_stream_rdr Url_(Io_url v) {this.url = v; return this;} private Io_url url;
	public long Len() {return len;} public Io_stream_rdr Len_(long v) {len = v; return this;} private long len = Io_mgr.Len_null;
	public void Open_mem(byte[] v) {}
	public Io_stream_rdr Open() {return this;}
	public int Read(byte[] bry, int bgn, int len) {
		try {return strm.read(bry, bgn, len);}	
		catch (Exception e) {throw Err_.new_exc(e, "io", "read failed", "bgn", bgn, "len", len);}
	}
	public long Skip(long len) {
		try {return strm.skip(len);}	
		catch (Exception e) {throw Err_.new_exc(e, "io", "skip failed", "len", len);}
	}	
	public void Rls() {
		try {strm.close();} 
		catch (Exception e) {throw Err_.new_exc(e, "io", "close failed", "url", url.Xto_api());}
	}
}
