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
package gplx;
import gplx.core.logs.*;
public class Gfo_log_ {
	public static Gfo_log Instance = new Gfo_log__mem();
	public static Gfo_log Instance__set(Gfo_log v) {
		v.Itms_(Instance.Itms());
		Instance = v;			
		return v;
	}
	public static final String File__fmt = "yyyyMMdd_HHmmss", File__ext = ".log";
	public static Io_url New_url(Io_url dir) {
		return dir.GenSubFil(DateAdp_.Now().XtoUtc().XtoStr_fmt(Gfo_log_.File__fmt) + Gfo_log_.File__ext);
	}
	public static Gfo_log New_file(Io_url dir) {
		Io_url url = dir.GenSubFil(DateAdp_.Now().XtoStr_fmt(File__fmt) + File__ext);
		return new Gfo_log__file(url, new Gfo_log_itm_wtr__csv());
	}
}
