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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xob_search_txt extends Xob_search_base {
	public Xob_search_txt(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public String Wkr_key() {return KEY;} public static final String KEY = "core.make_search_title";
	@Override public gplx.ios.Io_make_cmd Make_cmd_site() {
		return new Xob_make_cmd_site(bldr.Usr_dlg(), this.make_dir, this.make_fil_len);
	}
}
