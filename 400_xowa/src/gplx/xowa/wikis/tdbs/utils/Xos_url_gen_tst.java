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
package gplx.xowa.wikis.tdbs.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import org.junit.*;
import gplx.core.ios.*;
public class Xos_url_gen_tst {
	@Test  public void Url_gen() {
		tst_url_gen("mem/root/",          0, "mem/root/00/00/00/00/0000000000.csv");
		tst_url_gen("mem/root/",         99, "mem/root/00/00/00/00/0000000099.csv");
		tst_url_gen("mem/root/", 1234567890, "mem/root/12/34/56/78/1234567890.csv");
	}
	private void tst_url_gen(String root_str, int idx, String expd) {
		Io_url root = Io_url_.mem_fil_(root_str);
		Io_url actl_url = Xos_url_gen.bld_fil_(root, idx, Bry_.new_a7(".csv"));
		Tfds.Eq(expd, actl_url.Xto_api());
	}
}
