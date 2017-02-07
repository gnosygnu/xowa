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
public class Io_stream_rdr__noop implements Io_stream_rdr {
	public Object Under() {return null;}
	public byte Tid() {return Io_stream_tid_.Tid__null;}
	public boolean Exists() {return false;}
	public Io_url Url() {return Io_url_.Empty;} public Io_stream_rdr Url_(Io_url v) {return this;}
	public long Len() {return Io_mgr.Len_null;} public Io_stream_rdr Len_(long v) {return this;}
	public void Open_mem(byte[] v) {}
	public Io_stream_rdr Open() {return this;}
	public int Read(byte[] bry, int bgn, int len) {return Io_stream_rdr_.Read_done;}
	public long Skip(long len) {return Io_stream_rdr_.Read_done;}
	public void Rls() {}
}
