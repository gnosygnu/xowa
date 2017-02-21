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
class Xomw_prepro_rule {
	public Xomw_prepro_rule(byte[] bgn, byte[] end, int min, int max, int[] names) {
		this.bgn = bgn;
		this.end = end;
		this.min = min;
		this.max = max;
		this.names = names;
	}
	public final    byte[] bgn;
	public final    byte[] end;
	public final    int min;
	public final    int max;
	public final    int[] names;
	public boolean Names_exist(int idx) {
		return idx < names.length && names[idx] != Name__invalid;
	}
	private static final    byte[] Name__tmpl_bry = Bry_.new_a7("template"), Name__targ_bry = Bry_.new_a7("tplarg");
	public static final int Name__invalid = -1, Name__null = 0, Name__tmpl = 1, Name__targ = 2;
	public static byte[] Name(int type) {
		switch (type) {
			case Name__tmpl:    return Name__tmpl_bry;
			case Name__targ:    return Name__targ_bry;
			default:
			case Name__invalid: return null;
			case Name__null:    return null;
		}
	}
}
class Xomw_prepro_elem {
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
class Xomw_prepro_curchar_itm {
	public Xomw_prepro_curchar_itm(byte[] bry, byte type) {
		this.bry = bry;
		this.type = type;
	}
	public byte[] bry;
	public byte type;
}
