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
public class IoEnginePool {
	private final    Hash_adp hash = Hash_adp_.New();
	public void Add_if_dupe_use_nth(IoEngine engine) {
		hash.Del(engine.Key());
		hash.Add(engine.Key(), engine);
	}
	public IoEngine Get_by(String key) {
		IoEngine rv = (IoEngine)hash.Get_by(key); 
		return rv == null ? IoEngine_.Mem : rv; // rv == null when url is null or empty; return Mem which should be a noop; DATE:2013-06-04
	}
	public static final    IoEnginePool Instance = new IoEnginePool();
	IoEnginePool() {
		this.Add_if_dupe_use_nth(IoEngine_.Sys);
		this.Add_if_dupe_use_nth(IoEngine_.Mem);
	}
}