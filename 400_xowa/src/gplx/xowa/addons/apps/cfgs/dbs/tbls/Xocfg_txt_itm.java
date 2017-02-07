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
public class Xocfg_txt_itm {
	public Xocfg_txt_itm(int id, String lang, String name, String help) {
		this.id = id;
		this.lang = lang;
		this.name = name;
		this.help = help;
	}
	public int Id() {return id;} private final    int id;
	public String Lang() {return lang;} private final    String lang;
	public String Name() {return name;} private final    String name;
	public String Help() {return help;} private final    String help;

	public static final int Tid__grp = 1, Tid__itm = 2;
}
