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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public interface Io_stream_rdr extends Rls_able {
	byte Tid();
	boolean Exists();
	Io_url Url(); Io_stream_rdr Url_(Io_url v);
	long Len(); Io_stream_rdr Len_(long v);
	Io_stream_rdr Open();
	void Open_mem(byte[] v);
	Object Under();

	int Read(byte[] bry, int bgn, int len);
	long Skip(long len);
}
