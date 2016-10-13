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
package gplx.xowa.addons.htmls.scripts.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*; import gplx.xowa.addons.htmls.scripts.*;
public class Xoscript_page {		
	public Xoscript_page(Bry_bfr rv, Xoscript_env env, Xoscript_url url) {
		this.env = env;
		this.url = url;
		this.doc = new Xoscript_doc(rv, this);
	}
	public Xoscript_env Env() {return env;} private final    Xoscript_env env;
	public Xoscript_url Url() {return url;} private final    Xoscript_url url;
	public Xoscript_doc Doc() {return doc;} private final    Xoscript_doc doc;
}
