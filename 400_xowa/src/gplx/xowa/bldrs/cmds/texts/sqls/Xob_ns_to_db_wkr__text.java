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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_ns_to_db_wkr__text implements Xob_ns_to_db_wkr {
	public byte Db_tid() {return Xowd_db_file_.Tid_text;}
	public void Tbl_init(Xowd_db_file db) {
		Xowd_text_tbl tbl = db.Tbl__text();
		tbl.Create_tbl();
		tbl.Insert_bgn();
	}
	public void Tbl_term(Xowd_db_file db) {
		db.Tbl__text().Insert_end(); 
	}
}
