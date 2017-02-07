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
package gplx.xowa.addons.apps.scripts.xtns; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.scripts.*;
import gplx.core.scripts.*;
public class Xoscript_xtn_itm {
	public Xoscript_xtn_itm(String key, Io_url url, Gfo_script_engine engine) {
		this.key = key;
		this.url = url;
		this.engine = engine;
	}
	public String Key() {return key;} private final    String key;
	public Io_url Url() {return url;} private final    Io_url url;
	public Gfo_script_engine Engine() {return engine;} private final    Gfo_script_engine engine;
}
