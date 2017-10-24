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
package gplx.xowa.addons.bldrs.centrals.tasks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
public class Xobc_task_regy__done extends Xobc_task_regy__base {
	public Xobc_task_regy__done(Xobc_task_mgr task_mgr) {super(task_mgr, "done");}
	public void Del_done(int task_id) {
		task_mgr.User_db().Done_task_tbl().Delete(task_id);
		task_mgr.Transfer(this, task_mgr.Todo_mgr(), this.Get_by(task_id));
	}
}
