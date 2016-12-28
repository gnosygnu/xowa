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
package gplx.xowa.addons.apps.cfgs.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.dbs.*;
public class Xocfg_itm_row {
	public Xocfg_itm_row(int id, String key, int scope, String type, String dflt, String html_atrs, String html_cls) {
		this.id = id;
		this.key = key;
		this.scope = scope;
		this.type = type;
		this.dflt = dflt;
		this.html_atrs = html_atrs;
		this.html_cls = html_cls;
	}
	public int Id() {return id;} private final    int id;
	public String Key() {return key;} private final    String key;
	public int Scope() {return scope;} private final    int scope;
	public String Type() {return type;} private final    String type;
	public String Dflt() {return dflt;} private final    String dflt;
	public String Html_atrs() {return html_atrs;} private final    String html_atrs;
	public String Html_cls() {return html_cls;} private final    String html_cls;
}
