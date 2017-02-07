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
package gplx.xowa.bldrs.cmds.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.wikis.data.*;
class Xowd_tbl_mapr {
	public Xowd_tbl_mapr(String name, int[] db_ids) {
		this.Name = name;
		this.Db_ids = db_ids; 
	}
	public final    String Name;
	public final    int[] Db_ids;
	public boolean Db_ids__has(int id) {return true;}
//		private static List_adp Fill_tbl_names(List_adp rv, int db_tid) {
//			switch (db_tid) {
//				case Xow_db_file_.Tid__cat:
//					return 
//					break;
//		}
}
