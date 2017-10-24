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
package gplx.xowa.mediawiki.includes.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_prepro_node__part extends Xomw_prepro_node__base {
	public Xomw_prepro_node__part(int idx, byte[] key, byte[] val) {
		this.idx = idx;
		this.key = key;
		this.val = val;
	}
	public int Idx() {return idx;} private final    int idx;
	public byte[] Key() {return key;} private final    byte[] key;
	public byte[] Val() {return val;} private final    byte[] val;
	@Override public void To_xml(Bry_bfr bfr) {
		bfr.Add_str_a7("<part>");
		bfr.Add_str_a7("<name");
		if (idx > 0) {
			bfr.Add_str_a7(" index=\"").Add_int_variable(idx).Add_str_a7("\" />");
		}
		else {
			bfr.Add_str_a7(">");
			bfr.Add(key);
			bfr.Add_str_a7("</name>");
			bfr.Add_str_a7("=");
		}
		bfr.Add_str_a7("<value>");
		bfr.Add(val);
		bfr.Add_str_a7("</value>");
		bfr.Add_str_a7("</part>");
	}
}
