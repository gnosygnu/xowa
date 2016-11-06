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
	public Xoitm_meta_itm(int grp_id, int id, int itm_sort, int scope_id, int type_id, String key, String dflt) {
		this.grp_id = grp_id;
		this.id = id;
		this.itm_sort = itm_sort;
		this.scope_id = scope_id;
		this.type_id = type_id;
		this.key = key;
		this.dflt = dflt;
	}
	public int Grp_id() {return grp_id;} private final    int grp_id;
	public int Id() {return id;} private final    int id;
	public int Itm_sort() {return itm_sort;} private final    int itm_sort;
	public int Scope_id() {return scope_id;} private final    int scope_id;
	public int Type_id() {return type_id;} private final    int type_id;
	public String Key() {return key;} private final    String key;
	public String Dflt() {return dflt;} private final    String dflt;
}
