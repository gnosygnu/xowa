/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.tests;
import gplx.core.envs.*;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDateNow;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.commons.GfoTimeSpanUtl;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
public class PerfLogMgr_fxt {
	public void Init(Io_url url, String text) {
		this.url = url;
		entries.ResizeBounds(1000);
		entries.Add(new PerfLogItm(0, text + "|" + GfoDateNow.Get().ToStrGplx()));
		tmr.Bgn();
	}
	public void Write(String text) {
		long milliseconds = tmr.ElapsedMilliseconds();
		entries.Add(new PerfLogItm(milliseconds, text));
		tmr.Bgn();
	}
	public void WriteFormat(String fmt, Object... ary) {
		long milliseconds = tmr.ElapsedMilliseconds();
		String text = StringUtl.Format(fmt, ary);
		entries.Add(new PerfLogItm(milliseconds, text));
		tmr.Bgn();
	}
	public void Flush() {
		String_bldr sb = String_bldr_.new_();
		for (Object itmObj : entries) {
			PerfLogItm itm = (PerfLogItm)itmObj;
			sb.Add(itm.To_str()).AddCharCrlf();
		}
		Io_mgr.Instance.AppendFilStr(url, sb.ToStr());
		entries.Clear();
	}
	List_adp entries = List_adp_.New(); PerfLogTmr tmr = PerfLogTmr.new_(); Io_url url = Io_url_.Empty;
	public static final PerfLogMgr_fxt Instance = new PerfLogMgr_fxt(); PerfLogMgr_fxt() {}
	class PerfLogItm {
		public String To_str() {
			String secondsStr = GfoTimeSpanUtl.ToStr(milliseconds, GfoTimeSpanUtl.Fmt_Default);
			secondsStr = StringUtl.PadBgn(secondsStr, 7, "0"); // 7=000.000; left-aligns all times
			return StringUtl.Concat(secondsStr, "|", text);
		}
		long milliseconds; String text;
		public PerfLogItm(long milliseconds, String text) {
			this.milliseconds = milliseconds; this.text = text;
		}
	}

}
class PerfLogTmr {
	public void Bgn() {bgn = SystemUtl.Ticks();} long bgn;
	public long ElapsedMilliseconds() {return SystemUtl.Ticks() - bgn;    }
	public static PerfLogTmr new_() {return new PerfLogTmr();} PerfLogTmr() {}
}
