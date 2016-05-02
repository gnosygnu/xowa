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
package gplx.core.tests; import gplx.*; import gplx.core.*;
import gplx.core.strings.*; import gplx.core.envs.*;
public class PerfLogMgr_fxt {
	public void Init(Io_url url, String text) {
		this.url = url;
		entries.Resize_bounds(1000);
		entries.Add(new PerfLogItm(0, text + "|" + DateAdp_.Now().XtoStr_gplx()));
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
	List_adp entries = List_adp_.new_(); PerfLogTmr tmr = PerfLogTmr.new_(); Io_url url = Io_url_.Empty;
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
	public void Bgn() {bgn = Env_.TickCount();} long bgn;
	public long ElapsedMilliseconds() {return Env_.TickCount() - bgn;	}		
	public static PerfLogTmr new_() {return new PerfLogTmr();} PerfLogTmr() {}
}
