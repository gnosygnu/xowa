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
package gplx.gfui.controls.customs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import org.junit.*; import gplx.gfui.ipts.*; import gplx.gfui.controls.windows.*; import gplx.gfui.controls.standards.*; import gplx.gfui.controls.customs.*;
public class GfuiMoveElemBtn_tst {
	@Before public void setup() {
		form = GfuiWin_.app_("form"); form.Size_(100, 100);
		moveBtn = GfuiBtn_.new_("moveBtn");
		GfuiMoveElemBnd bnd = GfuiMoveElemBnd.new_(); bnd.TargetElem_set(form);
		moveBtn.IptBnds().Add(bnd);
	}
	@Test  public void Basic() {
		Tfds.Eq(form.X(), 0);
		IptEventMgr.ExecKeyDown(moveBtn, IptEvtDataKey.test_(MoveRightArg()));
		Tfds.Eq(form.X(), 10);
	}

	IptKey MoveRightArg() {return IptKey_.Ctrl.Add(IptKey_.Right);}
	GfuiWin form; GfuiBtn moveBtn;
}
