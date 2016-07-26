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
