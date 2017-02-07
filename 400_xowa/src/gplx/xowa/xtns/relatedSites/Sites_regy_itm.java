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
package gplx.xowa.xtns.relatedSites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.wikis.xwikis.*;
public class Sites_regy_itm {
	public Sites_regy_itm(Xow_xwiki_itm xwiki_itm, Xoa_ttl ttl) {
		this.xwiki_itm = xwiki_itm; this.ttl = ttl;
		this.cls = Bry_.Lcase__all(Bry_.Copy(xwiki_itm.Key_bry()));
	}
	public Xow_xwiki_itm		Xwiki_itm() {return xwiki_itm;} private Xow_xwiki_itm xwiki_itm;
	public Xoa_ttl				Ttl()		{return ttl;} private Xoa_ttl ttl;
	public byte[]				Cls()		{return cls;} private byte[] cls;
}
