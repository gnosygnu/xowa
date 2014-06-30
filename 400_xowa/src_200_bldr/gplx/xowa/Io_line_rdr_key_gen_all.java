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
package gplx.xowa; import gplx.*;
import gplx.ios.*;
public class Io_line_rdr_key_gen_all implements Io_line_rdr_key_gen {
	public void Gen(Io_line_rdr bfr) {
		bfr.Key_pos_bgn_(bfr.Itm_pos_bgn()).Key_pos_end_(bfr.Itm_pos_end());
	}
	public static final Io_line_rdr_key_gen_all _ = new Io_line_rdr_key_gen_all(); Io_line_rdr_key_gen_all() {}
}
