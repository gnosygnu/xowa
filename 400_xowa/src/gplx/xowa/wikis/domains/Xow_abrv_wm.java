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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.langs.*;
public class Xow_abrv_wm {
	public Xow_abrv_wm(byte[] raw, byte[] lang_domain, Xol_lang_itm lang_actl, int domain_type) {
		this.raw = raw;
		this.lang_domain = lang_domain;
		this.lang_actl = lang_actl;
		this.domain_type = domain_type;
	}
	public byte[] Raw() {return raw;} private final byte[] raw;
	public byte[] Lang_domain() {return lang_domain;} private final byte[] lang_domain;
	public Xol_lang_itm Lang_actl() {return lang_actl;} private final Xol_lang_itm lang_actl;
	public int Domain_type() {return domain_type;} private final int domain_type;
}
