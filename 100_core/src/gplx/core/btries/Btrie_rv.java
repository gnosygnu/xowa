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
package gplx.core.btries; import gplx.*; import gplx.core.*;
import gplx.core.threads.poolables.*;
public class Btrie_rv {
	public int Match_bgn = -1;
	public Object Obj() {return obj;} private Object obj;
	public int Pos() {return pos;} private int pos;
	public Btrie_rv Init(int pos, Object obj) {
		this.obj = obj;
		this.pos = pos;
		return this;
	}
}
