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
package gplx.core.enums; import gplx.*; import gplx.core.*;
class Gfo_enum_grp {
//		private Ordered_hash itms = Ordered_hash_.New();
	public Gfo_enum_grp(Guid_adp uid, String key, int id, String name, int sort, String xtn) {
		this.uid = uid; this.key = key; this.id = id; this.name = name; this.sort = sort; this.xtn = xtn;
	}
	public Guid_adp Uid() {return uid;} private Guid_adp uid;
	public String Key() {return key;} private String key;
	public int Id() {return id;} private int id;
	public String Name() {return name;} private String name;
	public int Sort() {return sort;} private int sort;
	public String Xtn() {return xtn;} private String xtn;
}
class Gfo_enum_itm {
	public Gfo_enum_itm(Guid_adp uid, String key, int id, String name, int sort, String xtn) {
		this.uid = uid; this.key = key; this.id = id; this.name = name; this.sort = sort; this.xtn = xtn;
	}
	public Guid_adp Uid() {return uid;} private Guid_adp uid;
	public String Key() {return key;} private String key;
	public int Id() {return id;} private int id;
	public String Name() {return name;} private String name;
	public int Sort() {return sort;} private int sort;
	public String Xtn() {return xtn;} private String xtn;
}
/*
enum_grps
grp_guid,grp_key,grp_int,grp_name,grp_sort,grp_xtn
0-1-2-3,xowa.wiki,0,wiki,,

enum_itms
grp_int,itm_guid,itm_key,itm_int,itm_name,itm_sort,itm_xtn
1,0-1-2-3,0,en.wikipedia.org,0,enwiki,0,''

class Gfo_enum_mgr {
//	public Gui
}
*/
