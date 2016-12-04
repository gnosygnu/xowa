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
package gplx.xowa.addons.apps.cfgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
public class Xoitm_meta_itm {
	public Xoitm_meta_itm(int id, int scope_id, int db_type, int gui_type, String gui_args, String key, String dflt) {
		this.id = id;
		this.scope_id = scope_id;
		this.db_type = db_type;
		this.gui_type = gui_type;
		this.gui_args = gui_args;
		this.key = key;
		this.dflt = dflt;
	}
	public int Id() {return id;} private final    int id;
	public int Scope_id() {return scope_id;} private final    int scope_id;
	public int Db_type() {return db_type;} private final    int db_type;
	public int Gui_type() {return gui_type;} private final    int gui_type;
	public String Gui_args() {return gui_args;} private final    String gui_args;
	public String Key() {return key;} private final    String key;
	public String Dflt() {return dflt;} private final    String dflt;
}
