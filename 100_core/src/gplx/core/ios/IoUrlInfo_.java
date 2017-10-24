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
public class IoUrlInfo_ {
	public static final IoUrlInfo Nil = IoUrlInfo_nil.Instance;
	public static final IoUrlInfo Wnt = IoUrlInfo_wnt.Instance;
	public static final IoUrlInfo Lnx = IoUrlInfo_lnx.Instance;
	public static final IoUrlInfo Mem = IoUrlInfo_mem.new_("mem", IoEngine_.MemKey);

	public static IoUrlInfo mem_(String key, String engineKey) {return IoUrlInfo_mem.new_(key, engineKey);}
	public static IoUrlInfo alias_(String srcRoot, String trgRoot, String engineKey) {return IoUrlInfo_alias.new_(srcRoot, trgRoot, engineKey);}
}
/*
wnt		C:\dir\fil.txt
wce		\dir\fil.txt	
lnx		/dir/fil.txt
mem		mem/dir/fil.txt
alias	app:\dir\fil.txt
*/