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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_cmd_regy {
	private final    Ordered_hash regy = Ordered_hash_.New();
	public Xob_cmd Get_or_null(String key) {return (Xob_cmd)regy.Get_by(key);}
	public void Add_many(Xob_cmd... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xob_cmd cmd = ary[i];
			regy.Add(cmd.Cmd_key(), cmd);
		}
	}
}
