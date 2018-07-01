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
import org.junit.*;
public class Io_mgr__tst {
	@Before public void init() {fxt.Clear();} private final Io_mgr__fxt fxt = new Io_mgr__fxt();
	@Test   public void Dir_delete_empty__basic() {
		fxt.Exec_itm_create("mem/dir/");
		fxt.Exec_dir_delete_empty("mem/dir/");
		fxt.Test_itm_exists_n("mem/dir/");
	}
	@Test   public void Dir_delete_empty__no_delete() {
		fxt.Exec_itm_create
		( "mem/dir/"
		, "mem/dir/fil.txt"
		);
		fxt.Exec_dir_delete_empty("mem/dir/");
		fxt.Test_itm_exists_y("mem/dir/");
	}
	@Test   public void Dir_delete_empty__nested_simple() {
		fxt.Exec_itm_create
		( "mem/dir/"
		, "mem/dir/1/"
		, "mem/dir/1/11/"
		);
		fxt.Exec_dir_delete_empty("mem/dir/");
		fxt.Test_itm_exists_n("mem/dir/");
	}
	@Test   public void Dir_delete_empty__nested_many() {
		fxt.Exec_itm_create
		( "mem/dir/"
		, "mem/dir/1/"
		, "mem/dir/1/11/"
		, "mem/dir/2/22/"
		, "mem/dir/2/22/222a/"
		, "mem/dir/2/22/222b/"
		);
		fxt.Exec_dir_delete_empty("mem/dir/");
		fxt.Test_itm_exists_n("mem/dir/");
	}
	@Test   public void Dir_delete_empty__nested_some() {
		fxt.Exec_itm_create
		( "mem/dir/"
		, "mem/dir/1/"
		, "mem/dir/1/11/"
		, "mem/dir/2/22/"
		, "mem/dir/2/22/a.txt"
		, "mem/dir/2/22/222a/"
		, "mem/dir/2/22/222b/"
		);
		fxt.Exec_dir_delete_empty("mem/dir/");
		fxt.Test_itm_exists_n
		( "mem/dir/1/"
		, "mem/dir/1/11/"
		, "mem/dir/2/22/222a/"
		, "mem/dir/2/22/222b/"
		);
		fxt.Test_itm_exists_y
		( "mem/dir/"
		, "mem/dir/2/22/"
		);
	}
}
class Io_mgr__fxt {
	public void Clear() {Io_mgr.Instance.InitEngine_mem();}
	public void Exec_itm_create(String... ary) {
		for (String itm : ary) {
			Io_url url = Io_url_.new_any_(itm);
			if (url.Type_dir())
				Io_mgr.Instance.CreateDir(url);
			else
				Io_mgr.Instance.SaveFilStr(url, url.NameAndExt());
		}
	}
	public void Exec_dir_delete_empty(String url)		{Io_mgr.Instance.Delete_dir_empty(Io_url_.mem_dir_(url));}
	public void Test_itm_exists_n(String... ary)	{Test_itm_exists(Bool_.N, ary);}
	public void Test_itm_exists_y(String... ary)	{Test_itm_exists(Bool_.Y, ary);}
	public void Test_itm_exists(boolean expd, String... ary) {
		for (String itm : ary) {
			Io_url url = Io_url_.new_any_(itm);
			boolean actl = url.Type_dir() ? Io_mgr.Instance.ExistsDir(url) : Io_mgr.Instance.ExistsFil(url);
			Tfds.Eq(expd, actl, itm);
		}
	}
}
