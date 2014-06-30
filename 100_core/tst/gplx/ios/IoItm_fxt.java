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
public class IoItm_fxt {
	public IoItmFil fil_wnt_(String s) {return fil_(Io_url_.wnt_fil_(s));}
	public IoItmFil fil_(Io_url url) {return IoItmFil_.new_(url, 1, DateAdp_.parse_gplx("2001-01-01"), DateAdp_.parse_gplx("2001-01-01"));}
	public IoItmDir dir_wnt_(String s) {return dir_(Io_url_.wnt_dir_(s));}
	public IoItmDir dir_(Io_url url, IoItm_base... ary) {
		IoItmDir rv = IoItmDir_.top_(url);
		for (IoItm_base itm : ary) {
			if (itm.Type_dir())
				rv.SubDirs().Add(itm);
			else
				rv.SubFils().Add(itm);
		}
		return rv;
	}
	public static IoItm_fxt new_() {return new IoItm_fxt();} IoItm_fxt() {}
}
