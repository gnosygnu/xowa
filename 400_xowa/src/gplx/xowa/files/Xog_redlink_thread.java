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
import gplx.core.threads.*; import gplx.xowa.files.gui.*;
public class Xog_redlink_thread implements Gfo_thread_wkr {
	private final int[] redlink_ary; private final Xog_js_wkr js_wkr;
	public Xog_redlink_thread(int[] redlink_ary, Xog_js_wkr js_wkr) {this.redlink_ary = redlink_ary; this.js_wkr = js_wkr;}
	public String Name() {return "xowa.gui.html.redlinks.set";}
	public boolean Resume() {return true;}
	public void Exec() {
		int len = redlink_ary.length;
		for (int i = 0; i < len; ++i) {
			js_wkr.Html_redlink(gplx.xowa.parsers.lnkis.redlinks.Xopg_redlink_lnki_list.Lnki_id_prefix + Int_.To_str(redlink_ary[i]));
		}
	}
}
