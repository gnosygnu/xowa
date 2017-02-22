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
package gplx.core.ios.zips; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import org.junit.*;
public class Io_zip_mgr_tst {
	@Test  public void Zip_unzip() {
		Zip_unzip_tst("abcdefghijklmnopqrstuvwxyz"); 
	}
	private void Zip_unzip_tst(String s) {
		Io_zip_mgr zip_mgr = Io_zip_mgr_base.Instance;
		byte[] src = Bry_.new_a7(s);
		byte[] zip = zip_mgr.Zip_bry(src, 0, src.length);
		byte[] unz = zip_mgr.Unzip_bry(zip, 0, zip.length);
		Tfds.Eq_ary(src, unz);
	}
}
