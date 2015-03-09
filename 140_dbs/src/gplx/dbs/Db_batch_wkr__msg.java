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
package gplx.dbs; import gplx.*;
public class Db_batch_wkr__msg implements Db_batch_wkr {
	private final Gfo_usr_dlg usr_dlg; private final String msg_pre;
	public Db_batch_wkr__msg(Gfo_usr_dlg usr_dlg, String msg_pre) {this.usr_dlg = usr_dlg; this.msg_pre = msg_pre;}
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;}
	public String Msg() {return msg;} public Db_batch_wkr__msg Msg_(String v) {msg = v; return this;} private String msg;
	public void Batch_bgn() {usr_dlg.Plog_many("", "", "bgn:" + msg_pre + "." + msg);}
	public void Batch_end() {usr_dlg.Plog_many("", "", "end:" + msg_pre + "." + msg);}
}
