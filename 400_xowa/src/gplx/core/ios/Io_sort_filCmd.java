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
public interface Io_sort_filCmd {
	void Bfr_add(Io_line_rdr stream);
	void Fil_bgn(Io_line_rdr stream);
	void Fil_end();
}
class Io_sort_filCmd_null implements Io_sort_filCmd {
	public void Bfr_add(Io_line_rdr stream) {}
	public void Fil_bgn(Io_line_rdr stream) {}
	public void Fil_end() {}
	public static final Io_sort_filCmd_null Instance = new Io_sort_filCmd_null(); Io_sort_filCmd_null() {}
}
