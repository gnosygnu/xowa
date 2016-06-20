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
package gplx.xowa.addons.bldrs.exports.packs.splits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
class Pack_itm {
	public Pack_itm(int idx, int type, Io_url zip_url, Io_url[] raw_urls) {
		this.idx = idx;
		this.type = type;
		this.zip_url = zip_url;
		this.raw_urls = raw_urls;
	}
	public int			Idx()		{return idx;}		private final    int idx;
	public int			Type()		{return type;}		private final    int type;
	public Io_url[]		Raw_urls()	{return raw_urls;}	private final    Io_url[] raw_urls;
	public Io_url		Zip_url()	{return zip_url;}	private final    Io_url zip_url;
}
