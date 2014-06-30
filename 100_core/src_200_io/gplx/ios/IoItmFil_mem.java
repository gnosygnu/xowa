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
import gplx.ios.*; /*IoStream_mem*/ import gplx.texts.*; /*Encoding_*/
class IoItmFil_mem extends IoItmFil {		public static IoItmFil_mem as_(Object obj) {return obj instanceof IoItmFil_mem ? (IoItmFil_mem)obj : null;}
	@gplx.Internal protected IoStream_mem Stream() {return stream;} IoStream_mem stream;	// NOTE: using stream instead of Text, b/c no events for IoStream.Dispose; ex: stream.OpenStreamWrite; stream.Write("hi"); stream.Dispose(); "hi" would not be saved if Text is member variable
	@Override public long Size() {return (int)stream.Len();}
	public String Text() {return Text_get();} public void Text_set(String v) {stream = IoStream_mem.rdr_txt_(this.Url(), v);}
	String Text_get() {
		int len = (int)stream.Len();
		byte[] buffer = new byte[len];
		stream.Position_set(0);
		stream.Read(buffer, 0, len);
		return String_.new_utf8_(buffer);
	}
	public IoItmFil_mem Clone() {return new_(this.Url(), this.Size(), this.ModifiedTime(), this.Text());}
	public static IoItmFil_mem new_(Io_url filPath, long size, DateAdp modified, String text) {
		IoItmFil_mem rv = new IoItmFil_mem();
		rv.ctor_IoItmFil(filPath, size, modified);
		rv.stream = IoStream_mem.rdr_txt_(filPath, text);
		return rv;
	}
	public static final IoItmFil_mem Null = new_(Io_url_.Null, 0, DateAdp_.MinValue, "");
}
