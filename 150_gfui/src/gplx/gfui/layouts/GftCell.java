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
package gplx.gfui.layouts; import gplx.*; import gplx.gfui.*;
public class GftCell {
	public GftSizeCalc Len0() {return len0;} public GftCell Len0_(GftSizeCalc c) {len0 = c; return this;} GftSizeCalc len0 = new GftSizeCalc_num(1);
	public GftCell Clone() {
		GftCell rv = new GftCell();
		rv.len0 = len0.Clone();
		return rv;
	}		
}
