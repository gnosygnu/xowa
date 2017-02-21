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
package gplx.xowa.users.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xou_cfg_itm {
	public Xou_cfg_itm(int usr, String ctx, String key, String val) {
		this.usr = usr; this.ctx = ctx; this.key = key; this.val = val;
		this.uid = Xou_cfg_mgr.Bld_uid(usr, ctx, key);
	}
	public String	Uid() {return uid;} private final    String uid;
	public int		Usr() {return usr;} private final    int usr;
	public String	Ctx() {return ctx;} private final    String ctx;
	public String	Key() {return key;} private final    String key;
	public String	Val() {return val;} private String val;
	public void Val_(String v) {this.val = v;}
}
