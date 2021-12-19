/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.envs;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.errs.ErrUtl;
public class Jar_adp_ {
	public static GfoDate ModifiedTime_type(Class<?> type) {if (type == null) throw ErrUtl.NewNull();
		Io_url url = Url_type(type);
		return Io_mgr.Instance.QueryFil(url).ModifiedTime();
	}
	public static Io_url Url_type(Class<?> type) {if (type == null) throw ErrUtl.NewNull();
				String codeBase = type.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (Op_sys.Cur().Tid_is_wnt())
			codeBase = StringUtl.Mid(codeBase, 1);    // codebase always starts with /; remove for wnt
		codeBase = StringUtl.Replace(codeBase, "/", Op_sys.Cur().Fsys_dir_spr_str());    // java always returns DirSpr as /; change to Env_.DirSpr to handle windows
		try   {codeBase = java.net.URLDecoder.decode(codeBase, "UTF-8");}
		catch (java.io.UnsupportedEncodingException e) {}
				return Io_url_.new_fil_(codeBase);
	}
}
