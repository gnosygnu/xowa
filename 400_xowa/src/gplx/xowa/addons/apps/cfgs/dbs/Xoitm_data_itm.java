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
package gplx.xowa.addons.apps.cfgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
public class Xoitm_data_itm {
	public Xoitm_data_itm(int id, String ctx, String val, String date) {
		this.id = id;
		this.ctx = ctx;
		this.val = val;
		this.date = date;
	}
	public int Id() {return id;} private final    int id;
	public String Ctx() {return ctx;} private final    String ctx;
	public String Val() {return val;} private final    String val;
	public String Date() {return date;} private final    String date;
}
