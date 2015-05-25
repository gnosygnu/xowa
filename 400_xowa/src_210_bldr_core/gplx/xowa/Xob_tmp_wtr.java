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
package gplx.xowa; import gplx.*;
import gplx.ios.*;
public class Xob_tmp_wtr {
	Xob_tmp_wtr(Xow_ns ns_itm, Io_url_gen url_gen, int fil_max) {
		this.ns_itm = ns_itm;
		this.url_gen = url_gen;
		this.fil_max = fil_max; 
		bfr = Bry_bfr.reset_(fil_max);
	}	int fil_max;
	public Bry_bfr Bfr() {return bfr;} Bry_bfr bfr;
	public Io_url_gen Url_gen() {return url_gen;} Io_url_gen url_gen;
	public void Clear() {bfr.ClearAndReset();}
	public boolean FlushNeeded(int writeLen) {return bfr.Len() + writeLen > fil_max;} //int bfr_len;
	public Xow_ns Ns_itm() {return ns_itm;} private Xow_ns ns_itm;
	public void Flush(Gfo_usr_dlg usr_dlg) {
		if (bfr.Len() == 0) return;		// nothing to flush
		Io_url url = url_gen.Nxt_url();
		if (bfr.Len() > fil_max)	// NOTE: data can exceed proscribed len; EX: wikt:Category for Italian nouns is 1 MB+
			usr_dlg.Log_many("xowa.tmp_wtr", "flush", "--fil exceeds len: ~{0} ~{1} ~{2}", bfr.Len(), fil_max, url.Xto_api());
		Io_mgr.I.AppendFilBfr(url, bfr);
	}
	public void Rls() {bfr.Rls();}
	public static Xob_tmp_wtr new_(Xow_ns ns_itm, Io_url_gen url_gen, int fil_max)	{return new Xob_tmp_wtr(ns_itm, url_gen, fil_max);}
	public static Xob_tmp_wtr new_wo_ns_(Io_url_gen url_gen, int fil_max)			{return new Xob_tmp_wtr(null, url_gen, fil_max);}
}
