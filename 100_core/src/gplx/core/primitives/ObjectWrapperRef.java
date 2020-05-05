/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.primitives;

import gplx.Object_;

public class ObjectWrapperRef {
	public ObjectWrapperRef() {}
	public Object Val() {return val;} public ObjectWrapperRef Val_(Object v) {val = v; return this;} private Object val;
	public ObjectWrapperRef Val_null_() {return Val_(null);}
	@Override public String toString() {return Object_.Xto_str_strict_or_null(val);}
}
