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
