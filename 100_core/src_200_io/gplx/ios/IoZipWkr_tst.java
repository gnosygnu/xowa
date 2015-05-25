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
package gplx.ios; import gplx.*;
import org.junit.*;
public class IoZipWkr_tst {
	@Test  public void Basic() {
		wkr = IoZipWkr.new_(Io_url_.Empty, "e \"{0}\" -o\"{1}\" -y");
		tst_Expand_genCmdString(Io_url_.wnt_fil_("C:\\fil1.zip"), Io_url_.wnt_dir_("D:\\out\\"), "e \"C:\\fil1.zip\" -o\"D:\\out\" -y");	// NOTE: not "D:\out\" because .Xto_api
	}	IoZipWkr wkr;
	void tst_Expand_genCmdString(Io_url srcUrl, Io_url trgUrl, String expd) {
		Tfds.Eq(expd, wkr.Expand_genCmdString(srcUrl, trgUrl));
	}	
}
