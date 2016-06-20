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
package gplx.xowa.apps.cfgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.cfgs.*;
public class Xocfg_data_itm {
	public Xocfg_data_itm(String key, String usr, String ctx, String val) {this.key = key; this.usr = usr; this.ctx = ctx; this.val = val;}
	public String Key() {return key;} private final    String key;
	public String Usr() {return usr;} private final    String usr;
	public String Ctx() {return ctx;} private String ctx;
	public String Val() {return val;} private String val;
}
