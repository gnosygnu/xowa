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
package gplx.xowa.htmls.sections; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
public class Xoh_section_itm {
	public Xoh_section_itm(int uid, int level, byte[] anchor, byte[] header) {
		this.uid = uid; this.level = level; this.anchor = anchor; this.header = header;
	}
	public int Uid() {return uid;} private final int uid;
	public int Level() {return level;} private final int level;
	public byte[] Anchor() {return anchor;} private final byte[] anchor;
	public byte[] Header() {return header;} private final byte[] header;
	public byte[] Content() {return content;} private byte[] content;
	public Xoh_section_itm Content_(byte[] v) {this.content = v; return this;}
	public int Content_bgn() {return content_bgn;} public Xoh_section_itm Content_bgn_(int v) {content_bgn = v; return this;} private int content_bgn;
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add_int_variable(uid).Add_byte_pipe();
		bfr.Add_int_variable(level).Add_byte_pipe();
		bfr.Add(anchor).Add_byte_pipe();
		bfr.Add(header).Add_byte_pipe();
		bfr.Add_safe(content).Add_byte_nl();
	}
}
