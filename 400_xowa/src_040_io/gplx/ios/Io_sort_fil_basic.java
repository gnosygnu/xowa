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
package gplx.ios; import gplx.*;
public class Io_sort_fil_basic implements Io_sort_cmd { // 123|bgn|end|1
	public Io_sort_fil_basic(Gfo_usr_dlg usr_dlg, Io_url_gen url_gen, int flush_len) {this.usr_dlg = usr_dlg; this.url_gen = url_gen; this.flush_len = flush_len;} Io_url_gen url_gen; Bry_bfr bfr = Bry_bfr.new_(); int flush_len; Gfo_usr_dlg usr_dlg;
	public void Sort_bgn() {}
	public void Sort_do(Io_line_rdr rdr) {
		int bgn = rdr.Itm_pos_bgn(), end = rdr.Itm_pos_end();
		if (bfr.Len() + (end - bgn) > flush_len) Flush();
		bfr.Add_mid(rdr.Bfr(), bgn, end);
	}
	public void Sort_end() {
		Flush();
		bfr.Rls();
	}
	private void Flush() {
		Io_url url = url_gen.Nxt_url();
		usr_dlg.Prog_one(GRP_KEY, "make", "making: ~{0}", url.NameAndExt());
		Io_mgr._.SaveFilBry(url, bfr.Bfr(), bfr.Len());
		bfr.Clear();
	}
	static final String GRP_KEY = "xowa.bldr.io_sort";
}
