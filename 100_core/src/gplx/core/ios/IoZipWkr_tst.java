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
