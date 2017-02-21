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
package gplx.core.security.files; import gplx.*; import gplx.core.*; import gplx.core.security.*;
import org.junit.*; import gplx.core.tests.*;
public class Cksum_list_tst {
	private final    Cksum_list_fxt fxt = new Cksum_list_fxt();
	@Test 	public void Basic() {
		fxt.Init__file("a.txt").Init__file("ab.txt");
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a5e54d1fd7bb69a228ef0dcd2431367e *a.txt"
		, "90f15b7ca11bd4c70d9047cd29a80040 *ab.txt"
		), 11
		, fxt.Make__itm("a5e54d1fd7bb69a228ef0dcd2431367e", "a.txt", 5)
		, fxt.Make__itm("90f15b7ca11bd4c70d9047cd29a80040", "ab.txt", 6)
		);
	}
}
class Cksum_list_fxt {
	private final    Io_url dir = Io_url_.mem_dir_("mem/dir/");
	public Cksum_list_fxt() {
		Io_mgr.Instance.InitEngine_mem();
	}
	public Cksum_list_fxt Init__file(String fil_name) {
		Io_url fil_url = dir.GenSubFil(fil_name);
		Io_mgr.Instance.SaveFilStr(fil_url, fil_name);
		return this;
	}
	public Cksum_itm Make__itm(String hash, String file_name, long size) {return new Cksum_itm(Bry_.new_u8(hash), dir.GenSubFil(file_name), size);}
	public Cksum_list_fxt Test__parse(String raw, long expd_size, Cksum_itm... expd_itms) {
		Cksum_list actl_list = Cksum_list.Parse(gplx.core.security.Hash_algo_.Tid__md5, dir, Bry_.new_u8(raw));
		Gftest.Eq__long(expd_size, actl_list.Itms_size);
		Gftest.Eq__ary(expd_itms, actl_list.Itms);
		return this;
	}
}
