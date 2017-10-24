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
package gplx.gfui.controls.tabs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
public class TabPnlItm {
	public String Key() {return key;} private String key;
	public String Name() {return name;}
	public TabPnlItm Name_(String val) {
		name = val;
		TabBoxEvt_nameChange.Send(mgr, this);
		return this;
	}	String name;
	public int Idx() {return idx;}
	@gplx.Internal protected TabPnlItm Idx_(int val) {
		idx = val;
		return this;
	}	int idx;
	TabBoxMgr mgr;
	public static TabPnlItm new_(TabBoxMgr mgr, String key) {
		TabPnlItm rv = new TabPnlItm();
		rv.mgr = mgr; rv.key = key;
		return rv;
	}	TabPnlItm() {}
}
