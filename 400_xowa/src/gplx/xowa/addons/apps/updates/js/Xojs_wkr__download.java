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
package gplx.xowa.addons.apps.updates.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.xowa.guis.cbks.*;
import gplx.core.net.downloads.*;
public class Xojs_wkr__download extends Xojs_wkr__base {
	public Xojs_wkr__download(Xog_cbk_mgr cbk_mgr, Xog_cbk_trg cbk_trg, String js_cbk, Gfo_invk_cmd done_cbk, String src, Io_url trg, long src_len) {super(cbk_mgr, cbk_trg, js_cbk, done_cbk, "downloading");
		this.src = src;
		this.src_len = src_len;
		this.trg = trg;
		this.Prog_data_end_(src_len);
	}
	public String Src() {return src;} private final    String src;
	public Io_url Trg() {return trg;} private final    Io_url trg;
	public long Src_len() {return src_len;} private final    long src_len;
	@Override protected void Exec_run() {
		Http_download_wkr wkr = Http_download_wkr_.Proto.Make_new();
		wkr.Exec(this, src, trg, src_len);
	}
}
