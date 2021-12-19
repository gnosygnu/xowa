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
package gplx.xowa.xtns.scribunto.libs;
import gplx.types.commons.KeyVal;
public class Scrib_lib_text__reindex_data {
	public boolean				Rv_is_kvy() {return rv_is_kvy;} private boolean rv_is_kvy;
	public KeyVal[]			Rv_as_kvy() {return rv_as_kvy;} private KeyVal[] rv_as_kvy;
	public Object		Rv_as_ary() {return rv_as_ary;} private Object rv_as_ary;	
	public void Init(boolean rv_is_kvy, KeyVal[] rv_as_kvy, Object rv_as_ary) {
		this.rv_is_kvy = rv_is_kvy;
		this.rv_as_kvy = rv_as_kvy;
		this.rv_as_ary = rv_as_ary;
	}
}
