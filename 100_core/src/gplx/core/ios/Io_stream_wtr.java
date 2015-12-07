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
public interface Io_stream_wtr extends Rls_able {
	byte Tid();
	Io_url Url(); Io_stream_wtr Url_(Io_url v);
	void Trg_bfr_(Bry_bfr v);
	Io_stream_wtr Open();
	byte[] To_ary_and_clear();

	void Write(byte[] bry, int bgn, int len);
	void Flush();
}
