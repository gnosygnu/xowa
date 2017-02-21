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
package gplx.xowa.wikis.tdbs.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import org.junit.*; import gplx.xowa.files.*;
public class Xof_meta_fil_tst {
	Xof_meta_fil_fxt fxt = new Xof_meta_fil_fxt();
	@Before public void init() {fxt.Ini();}
	@Test  public void Bld_url() {fxt.Bld_url("mem/root/", "abcdef", 3, "mem/root/a/b/abc.csv");}
}
class Xof_meta_fil_fxt {
	byte[] md5_(byte[] name) {return Xof_file_wkr_.Md5(name);}
	public void Ini() {}
	public void Bld_url(String root, String md5, int depth, String expd) {Tfds.Eq(expd, Xof_meta_fil.Bld_url(Io_url_.new_dir_(root), Bry_.new_a7(md5), depth).Raw());}
}
