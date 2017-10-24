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
public class IoEngine_ {
	public static final String SysKey = "sys";
	public static final String MemKey = "mem";

	public static final IoEngine Sys = IoEngine_system.new_();
	public static final IoEngine_memory Mem = IoEngine_memory.new_(MemKey); 
	public static IoEngine Mem_init_() {
		Mem.Clear();
		return Mem;
	}
	public static IoEngine mem_new_(String key) {return IoEngine_memory.new_(key);}
}
