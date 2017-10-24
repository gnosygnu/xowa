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
package gplx.core.tests; import gplx.*; import gplx.core.*;
import gplx.core.strings.*; import gplx.core.envs.*;
public class PerfLogMgr_fxt {
	public void Init(Io_url url, String text) {
		this.url = url;
		entries.Resize_bounds(1000);
		entries.Add(new PerfLogItm(0, text + "|" + Datetime_now.Get().XtoStr_gplx()));
		tmr.Bgn();
	}
	public void Write(String text) {
		long milliseconds = tmr.ElapsedMilliseconds();
		entries.Add(new PerfLogItm(milliseconds, text));
		tmr.Bgn();
	}
	public void WriteFormat(String fmt, Object... ary) {
		long milliseconds = tmr.ElapsedMilliseconds();
		String text = String_.Format(fmt, ary);
		entries.Add(new PerfLogItm(milliseconds, text));
		tmr.Bgn();
	}
	public void Flush() {
		String_bldr sb = String_bldr_.new_();
		for (Object itmObj : entries) {
			PerfLogItm itm = (PerfLogItm)itmObj;
			sb.Add(itm.To_str()).Add_char_crlf();
		}
		Io_mgr.Instance.AppendFilStr(url, sb.To_str());
		entries.Clear();
	}
	List_adp entries = List_adp_.New(); PerfLogTmr tmr = PerfLogTmr.new_(); Io_url url = Io_url_.Empty;
	public static final    PerfLogMgr_fxt Instance = new PerfLogMgr_fxt(); PerfLogMgr_fxt() {}
	class PerfLogItm {
		public String To_str() {
			String secondsStr = Time_span_.To_str(milliseconds, Time_span_.Fmt_Default);
			secondsStr = String_.PadBgn(secondsStr, 7, "0"); // 7=000.000; left-aligns all times
			return String_.Concat(secondsStr, "|", text);
		}
		long milliseconds; String text;
		@gplx.Internal protected PerfLogItm(long milliseconds, String text) {
			this.milliseconds = milliseconds; this.text = text;
		}
	}

}
class PerfLogTmr {
	public void Bgn() {bgn = System_.Ticks();} long bgn;
	public long ElapsedMilliseconds() {return System_.Ticks() - bgn;	}		
	public static PerfLogTmr new_() {return new PerfLogTmr();} PerfLogTmr() {}
}
