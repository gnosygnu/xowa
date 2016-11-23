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
public class Xojs_wkr__replace extends Xojs_wkr__base {
	private final    Io_url src_dir, trg_dir;
	private final    Io_url[] src_fils;
	public Xojs_wkr__replace(Xog_cbk_mgr cbk_mgr, Xog_cbk_trg cbk_trg, String js_cbk, Gfo_invk_cmd done_cbk, Io_url src_dir, Io_url trg_dir) {super(cbk_mgr, cbk_trg, js_cbk, done_cbk, "unzipping");
		this.src_dir = src_dir; 
		this.trg_dir = trg_dir;
		this.src_fils = Io_mgr.Instance.QueryDir_args(src_dir).Recur_().ExecAsUrlAry();
		this.Prog_data_end_(src_fils.length);
	}
	@Override protected void Exec_run() {
		int len = src_fils.length;
		for (int i = 0; i < len; i++) {
			Io_url src_fil = src_fils[i];
			Io_url trg_fil = trg_dir.GenSubFil(src_fil.GenRelUrl_orEmpty(src_dir));
			// Io_url trg_fil_del = trg_fil.GenNewNameAndExt(trg_fil.NameAndExt() + ".del");
			try {
//					Io_mgr.Instance.DeleteFil(trg_fil);
//					Io_mgr.Instance.MoveFil_args(src_fil, trg_fil, true).Exec();
				
				Io_mgr.Instance.CopyFil(src_fil, trg_fil, true);
			} catch (Exception exc) {
				Tfds.Write(src_fil, Err_.Message_lang(exc));
			}
		}
	}
}
