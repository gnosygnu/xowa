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
package gplx.core.threads.poolables; import gplx.*; import gplx.core.*; import gplx.core.threads.*;
public class Gfo_poolable_mgr_ {
	public static Gfo_poolable_mgr New(int len, int max, Gfo_poolable_itm prototype) {return new Gfo_poolable_mgr(prototype, Object_.Ary_empty, len, max);}
	public static Gfo_poolable_mgr New(int len, int max, Gfo_poolable_itm prototype, Object[] make_args) {return new Gfo_poolable_mgr(prototype, make_args, len, max);}
}
