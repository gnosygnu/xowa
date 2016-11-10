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
package gplx.xowa.addons.parsers.mediawikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.parsers.*;
class Xow_page_cache_wkr__embeddable implements gplx.xowa.wikis.caches.Xow_page_cache_wkr {
	private final    Xop_mediawiki_loader cbk;
	public Xow_page_cache_wkr__embeddable(Xop_mediawiki_loader cbk) {
		this.cbk = cbk;
	}
	public byte[] Get_page_or_null(byte[] full_db) {
		return Bry_.new_u8(cbk.LoadWikitext(String_.new_u8(full_db)));
	}
}
