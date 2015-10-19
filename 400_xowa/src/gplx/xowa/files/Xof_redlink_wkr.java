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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.core.threads.*;
import gplx.xowa.files.gui.*;
class Xof_redlink_wkr implements Gfo_thread_wkr {
	private Xog_js_wkr js_wkr; private int[] uids;
	public Xof_redlink_wkr(Xog_js_wkr js_wkr, int[] uids) {
		this.js_wkr = js_wkr; this.uids = uids;
	}
	public String Name() {return "xowa.redlinks";}
	public boolean Resume() {return true;}
	public void Exec() {
		int len = uids.length;
		for (int i = 0; i < len; ++i) {
			int uid = uids[i];
			js_wkr.Html_atr_set(Int_.To_str(uid), "", "");
		}
	}
}
