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
package gplx.xowa.mediawiki.includes.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.xowa.mediawiki.includes.utls.*;
public class Xomw_params_handler {
	public int width;
	public int height;
	public int page;
	public int physicalWidth;
	public int physicalHeight;
	public Xomw_params_handler Clear() {
		width = height = page
		= physicalWidth = physicalHeight = Php_utl_.Null_int;
		return this;
	}
	public void Copy_to(Xomw_params_handler src) {
		this.width = src.width;
		this.height = src.height;
		this.page = src.page;
		this.physicalWidth = src.physicalWidth;
		this.physicalHeight = src.physicalHeight;
	}
	public void Set(int uid, byte[] val_bry, int val_int) {
		switch (uid) {
			case Xomw_param_itm.Name__width: width = val_int; break;
			case Xomw_param_itm.Name__height: height = val_int; break;
			default: throw Err_.new_unhandled_default(uid);
		}
	}
}
