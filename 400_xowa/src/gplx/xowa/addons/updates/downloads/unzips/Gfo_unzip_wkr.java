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
package gplx.xowa.addons.updates.downloads.unzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*; import gplx.xowa.addons.updates.downloads.*;
public interface Gfo_unzip_wkr {
	void Unzip__bgn(Gfo_unzip_cbk cbk, Gfo_unzip_itm itm);
}
class Gfo_unzip_wkr__noop implements Gfo_unzip_wkr {
	public void Unzip__bgn(Gfo_unzip_cbk cbk, Gfo_unzip_itm itm) {cbk.Unzip__end_itm(itm);}
	public static final    Gfo_unzip_wkr__noop Instance = new Gfo_unzip_wkr__noop(); Gfo_unzip_wkr__noop() {}
}
