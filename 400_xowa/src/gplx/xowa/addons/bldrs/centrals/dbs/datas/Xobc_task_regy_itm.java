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
package gplx.xowa.addons.bldrs.centrals.dbs.datas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
public class Xobc_task_regy_itm {
	public Xobc_task_regy_itm(int id, int seqn, byte[] key, byte[] name, int step_count) {
		this.id = id;
		this.seqn = seqn;
		this.key = key;
		this.name = name;
		this.step_count = step_count;
	}
	public int Id() {return id;} private final    int id;
	public int Seqn() {return seqn;} private final    int seqn;
	public byte[] Key() {return key;} private final    byte[] key;
	public byte[] Name() {return name;} private final    byte[] name;
	public int Step_count() {return step_count;} private final    int step_count;

	public static final String Type__text = "text", Type__html = "html", Type__file = "file";
	public static final int Seqn__obsolete = 999999;
}
