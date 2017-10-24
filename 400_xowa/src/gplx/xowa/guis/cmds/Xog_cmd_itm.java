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
package gplx.xowa.guis.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_cmd_itm {
	public Xog_cmd_itm(String key, Xog_cmd_ctg ctg, String cmd) {
		this.key = key; this.ctg = ctg; this.cmd = cmd;
		this.key_bry = Bry_.new_u8(key);
		this.uid = ++Uid_next;
	}
	public int Uid() {return uid;} private int uid;
	public String Key() {return key;} private String key;
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public Xog_cmd_ctg Ctg() {return ctg;} private Xog_cmd_ctg ctg;
	public String Cmd() {return cmd;} public Xog_cmd_itm Cmd_(String v) {cmd = v; return this;} private String cmd;
	public String Name() {return name;} public Xog_cmd_itm Name_(String v) {name = v; return this;} private String name;
	public String Name_or_missing() {return name == null ? "<" + name + ">" : name;}
	public String Tip() {return tip;} public Xog_cmd_itm Tip_(String v) {tip = v; return this;} private String tip;
	public String Tip_or_missing() {return tip == null ? "<" + tip + ">" : tip;}
	private static int Uid_next = 0;
}
