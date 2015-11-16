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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.stores.*; /*GfoNdeRdr_*/
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
	ProcessAdp process = new ProcessAdp();
	public static IoZipWkr regy_() {return new IoZipWkr();}
	static final String Regy_ExeUrl = "gplx.core.ios.IoZipWkr.ExeUrl", Regy_ExeArgFmt = "gplx.core.ios.IoZipWkr.ExeArgFmt";
	public static IoZipWkr new_(Io_url exeUrl, String expandArgs) {
		GfoRegy.Instance.RegObj(Regy_ExeUrl, exeUrl);
		GfoRegy.Instance.RegObj(Regy_ExeArgFmt, expandArgs);
		IoZipWkr rv = new IoZipWkr();
		return rv;
	}
}
