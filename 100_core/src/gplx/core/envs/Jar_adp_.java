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
package gplx.core.envs; import gplx.*; import gplx.core.*;
public class Jar_adp_ {
	public static DateAdp ModifiedTime_type(Class<?> type) {if (type == null) throw Err_.new_null();
		Io_url url = Url_type(type);
		return Io_mgr.Instance.QueryFil(url).ModifiedTime();
	}
	public static Io_url Url_type(Class<?> type) {if (type == null) throw Err_.new_null();
				String codeBase = type.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (Op_sys.Cur().Tid_is_wnt())
			codeBase = String_.Mid(codeBase, 1);	// codebase always starts with /; remove for wnt 
		codeBase = String_.Replace(codeBase, "/", Op_sys.Cur().Fsys_dir_spr_str());	// java always returns DirSpr as /; change to Env_.DirSpr to handle windows
		try   {codeBase = java.net.URLDecoder.decode(codeBase, "UTF-8");}
		catch (java.io.UnsupportedEncodingException e) {Err_.Noop(e);}
				return Io_url_.new_fil_(codeBase);
	}
}
