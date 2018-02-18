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
package gplx.xowa.parsers.uniqs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
class Xop_uniq_itm {
	public Xop_uniq_itm(boolean expand_after_template_parsing, byte[] type, int idx, byte[] key, byte[] val) {
		this.expand_after_template_parsing = expand_after_template_parsing;
		this.type = type;
		this.idx = idx;
		this.key = key;
		this.val = val;
	}
	public boolean Expand_after_template_parsing() {return expand_after_template_parsing;} private final    boolean expand_after_template_parsing;
	public byte[] Type() {return type;} private final    byte[] type;
	public int Idx() {return idx;} private final    int idx;
	public byte[] Key() {return key;} private final    byte[] key;
	public byte[] Val() {return val;} private final    byte[] val;
}
