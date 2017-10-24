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
package gplx.xowa.guis.bnds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*; import gplx.gfui.ipts.*;
public class Xog_bnd_itm {
	public Xog_bnd_itm(String key, boolean sys, String cmd, int box, IptArg ipt) {
		this.key = key; this.sys = sys; this.cmd = cmd; this.box = box; this.ipt = ipt;
		uid = ++Uid_next;
	}
	public String Key() {return key;} private String key;
	public int Uid() {return uid;} private int uid;
	public boolean Sys() {return sys;} private boolean sys;
	public String Cmd() {return cmd;} public void Cmd_(String v) {cmd = v;} private String cmd;
	public int Box() {return box;} private int box;
	public IptArg Ipt() {return ipt;} public void Ipt_to_none() {ipt = IptKey_.None;} private IptArg ipt;		
	public void Init_by_set(int box, IptArg ipt) {this.box = box; this.ipt = ipt;}
	private static int Uid_next = 0;
}
