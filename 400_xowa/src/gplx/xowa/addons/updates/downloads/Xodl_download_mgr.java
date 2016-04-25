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
package gplx.xowa.addons.updates.downloads; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*;
import gplx.xowa.addons.updates.downloads.core.*;
import gplx.xowa.addons.updates.downloads.itms.*;
class Xodl_download_mgr implements Gfo_download_cbk {
	public void Download(Gfo_download_wkr download_wkr, Xodl_itm_pack[] packs) {
		download_wkr.Download__bgn(this, packs);
	}
	public void Download__end_itm(Gfo_download_itm itm) {
		// unzip; start
	}
	public void Download__end_all(Gfo_download_itm[] itms) {}
	public void Unzip__end(Gfo_download_itm[] itms) {
		// register
	}
}
