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
package gplx.xowa.mediawiki.includes.parsers.preprocessors; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_prepro_elem {
	private static final    byte[] Bry__tag_end = Bry_.new_a7("</");
	public Xomw_prepro_elem(int type, byte[] name) {
		this.type = type;
		this.name = name;
		this.tag_end_lhs = Bry_.Add(Bry__tag_end, name);
	}
	public final    int type;
	public final    byte[] name;
	public final    byte[] tag_end_lhs;
	public static final int Type__comment = 0, Type__other = 1;
}
