/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
