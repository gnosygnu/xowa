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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.envs.*; import gplx.core.stores.*; /*GfoNdeRdr_*/
import gplx.core.gfo_regys.*;
public class IoZipWkr {
	public Io_url ExeUrl()		{return (Io_url)GfoRegy.Instance.FetchValOrFail(Regy_ExeUrl);}
	public String ExeArgFmt()	{return (String)GfoRegy.Instance.FetchValOrFail(Regy_ExeArgFmt);}
	public void Expand(Io_url srcUrl, Io_url trgUrl) {			
		String exeArgs = Expand_genCmdString(srcUrl, trgUrl);
		process.Exe_url_(this.ExeUrl()).Args_str_(exeArgs);
		process.Run_wait();
	}
	@gplx.Internal protected String Expand_genCmdString(Io_url srcUrl, Io_url trgUrl) {
		return String_.Format(this.ExeArgFmt(), srcUrl.Xto_api(), trgUrl.Xto_api());			
	}
	Process_adp process = new Process_adp();
	public static IoZipWkr regy_() {return new IoZipWkr();}
	static final String Regy_ExeUrl = "gplx.core.ios.IoZipWkr.ExeUrl", Regy_ExeArgFmt = "gplx.core.ios.IoZipWkr.ExeArgFmt";
	public static IoZipWkr new_(Io_url exeUrl, String expandArgs) {
		GfoRegy.Instance.RegObj(Regy_ExeUrl, exeUrl);
		GfoRegy.Instance.RegObj(Regy_ExeArgFmt, expandArgs);
		IoZipWkr rv = new IoZipWkr();
		return rv;
	}
}
