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
public class IoItmDir_ {
	public static IoItmDir as_(Object obj) {return obj instanceof IoItmDir ? (IoItmDir)obj : null;}
	public static final    IoItmDir Null = null_();
	public static IoItmDir top_(Io_url url) {return scan_(url);}
	public static IoItmDir scan_(Io_url url) {
		IoItmDir rv = new IoItmDir(url.Info().CaseSensitive());
		rv.ctor_IoItmBase_url(url);
		return rv;
	}
	public static IoItmDir sub_(String name) {
		IoItmDir rv = new IoItmDir(Bool_.Y);
		rv.ctor_IoItmBase_url(Io_url_.mem_dir_("mem/" + name));
		return rv;
	}
	static IoItmDir null_() {
		IoItmDir rv = new IoItmDir(true);	// TODO_OLD: NULL should be removed
		rv.ctor_IoItmBase_url(Io_url_.Empty);
		rv.Exists_set(false);
		return rv;
	}
	public static void Make(IoItmDir dir) {
		Io_mgr.Instance.CreateDir(dir.Url());
		int len = dir.SubDirs().Count();
		for (int i = 0; i < len; ++i) {
			IoItmDir sub_dir = (IoItmDir)dir.SubDirs().Get_at(i);
			Make(sub_dir);
		}
		len = dir.SubFils().Count();
		for (int i = 0; i < len; ++i) {
			IoItmFil sub_fil = (IoItmFil)dir.SubFils().Get_at(i);
			String text = String_.Repeat("a", (int)sub_fil.Size());
			Io_url sub_url = sub_fil.Url();
			Io_mgr.Instance.SaveFilStr(sub_url, text);
			Io_mgr.Instance.UpdateFilModifiedTime(sub_url, sub_fil.ModifiedTime());
		}
	}
}
