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
package gplx.core.logs; import gplx.*; import gplx.core.*;
public class Gfo_log__file extends Gfo_log__base {
	public final    Gfo_log_itm_wtr fmtr;
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public Gfo_log__file(Io_url url, Gfo_log_itm_wtr fmtr) {
		this.url = url; this.fmtr = fmtr;
	}
	public Io_url Url() {return url;} private final    Io_url url;
	@Override public List_adp Itms() {return itms;} @Override public Gfo_log Itms_(List_adp v) {this.itms = v; return this;} private List_adp itms;
	@Override public void Exec(byte type, long time, long elapsed, String msg, Object[] args) {
		if (type == Gfo_log_itm.Type__prog) return;

		// add itm
		Gfo_log_itm itm = new Gfo_log_itm(type, time, elapsed, msg, args);
		itms.Add(itm);

		// flush if warning or failure; needed for download central
		switch (type) {
			case Gfo_log_itm.Type__note:
			case Gfo_log_itm.Type__warn:
			case Gfo_log_itm.Type__fail: this.Flush(); break;
		}
	}
	@Override public void Flush() {
		int len = itms.Len();
		for (int i = 0; i < len; ++i) {
			Gfo_log_itm itm = (Gfo_log_itm)itms.Get_at(i);
			fmtr.Write(bfr, itm);
		}
		byte[] bry = bfr.To_bry_and_clear(); if (bry.length == 0) return;	// don't bother writing empty bfr; happens during Xolog.Delete
		Io_mgr.Instance.AppendFilByt(url, bry);
		itms.Clear();
	}
	public static void Delete_old_files(Io_url dir, Gfo_log log) {
		Io_url[] fils = Io_mgr.Instance.QueryDir_fils(dir);
		int fils_len = fils.length;
		if (fils_len < 9) return;	// exit if less than 8 files
		int cutoff = fils_len - 8;	
		Array_.Sort(fils);			// sort by path
		for (int i = 0; i < cutoff; ++i) {
			Io_url fil = fils[i];
			log.Info("deleting old log file", "file", fil.Raw());
			Io_mgr.Instance.DeleteFil(fil);
		}
	}
}
