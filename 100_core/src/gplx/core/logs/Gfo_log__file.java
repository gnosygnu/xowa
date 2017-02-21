/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
