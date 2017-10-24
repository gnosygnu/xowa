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
		return dir.GenSubFil(Datetime_now.Get().XtoUtc().XtoStr_fmt(Gfo_log_.File__fmt) + Gfo_log_.File__ext);
	}
	public static Gfo_log New_file(Io_url dir) {
		Io_url url = dir.GenSubFil(Datetime_now.Get().XtoStr_fmt(File__fmt) + File__ext);
		Gfo_log__file.Delete_old_files(dir, Gfo_log_.Instance);
		return new Gfo_log__file(url, new Gfo_log_itm_wtr__csv());
	}
}
