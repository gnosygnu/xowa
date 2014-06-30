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
import gplx.xowa.files.fsdb.*;
public interface Xof_bin_wkr extends GfoInvkAble {
	byte Bin_wkr_tid();
	boolean Bin_wkr_resize(); void Bin_wkr_resize_(boolean v);
	gplx.ios.Io_stream_rdr Bin_wkr_get_as_rdr(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w);
	boolean Bin_wkr_get_to_url(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url);
}
