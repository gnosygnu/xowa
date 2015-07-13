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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.ios.*;
import gplx.xowa.files.fsdb.*;
public interface Xof_bin_wkr {
	byte			Tid();
	String			Key();
	boolean			Resize_allowed(); void Resize_allowed_(boolean v);
	Io_stream_rdr	Get_as_rdr	(Xof_fsdb_itm itm, boolean is_thumb, int w);
	boolean			Get_to_fsys	(Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url);
}
