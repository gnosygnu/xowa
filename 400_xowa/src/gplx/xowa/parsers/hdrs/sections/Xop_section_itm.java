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
package gplx.xowa.parsers.hdrs.sections; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
class Xop_section_itm {
	public Xop_section_itm(int idx, int num, byte[] key, int src_bgn, int src_end) {
		this.idx = idx;
		this.num = num;
		this.key = key;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
	}
	public int Idx() {return idx;} private final    int idx;
	public int Num() {return num;} private final    int num;
	public byte[] Key() {return key;} private final    byte[] key;
	public int Src_bgn() {return src_bgn;} private final    int src_bgn;
	public int Src_end() {return src_end;} private final    int src_end;
}
