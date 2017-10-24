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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_ns_to_db_wkr__text implements Xob_ns_to_db_wkr {
	public byte Db_tid() {return Xow_db_file_.Tid__text;}
	public void Tbl_init(Xow_db_file db) {
		Xowd_text_tbl tbl = db.Tbl__text();
		tbl.Create_tbl();
		tbl.Insert_bgn();
	}
	public void Tbl_term(Xow_db_file db) {
		db.Tbl__text().Insert_end(); 
	}
}
