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
package gplx.xowa.addons.wikis.searchs.bldrs.cmds.adjustments; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.bldrs.*; import gplx.xowa.addons.wikis.searchs.bldrs.cmds.*;
import gplx.core.primitives.*;
class Page_matcher_itm {
	public Page_matcher_itm(byte match_type, byte calc_type, double val, String page_filter) {
		this.Match_type = match_type;
		this.Calc_type = calc_type;
		this.Val = val;
		this.Page_filter = page_filter;
	}
	public final    byte Match_type;
	public final    byte Calc_type;
	public final    double Val;
	public final    String Page_filter;
	public Int_obj_ref[] Page_ids;
	public int Calc(int score_old) {
		switch (this.Calc_type) {
			case Page_matcher__calc_type.Type__set	: return (int)Val;
			case Page_matcher__calc_type.Type__mult	: return (int)(score_old * Val);
			case Page_matcher__calc_type.Type__add	: return score_old + (int)Val;
			default: throw Err_.new_unhandled_default(this.Calc_type);
		}
	}
}
class Page_matcher__match_type {
	public static final byte Type__bgn = 0, Type__end = 1, Type__all = 2, Type__any = 3;
	public static byte To_tid(String s) {
		if		(String_.Eq(s, "bgn"))	return Type__bgn;
		else if	(String_.Eq(s, "end"))	return Type__end;
		else if	(String_.Eq(s, "all"))	return Type__all;
		else							throw Err_.new_unhandled_default(s);
	}
}
class Page_matcher__calc_type {
	public static final byte Type__set = 0, Type__mult = 1, Type__add = 2;
	public static byte To_tid(String s) {
		if		(String_.Eq(s, "set"))	return Type__set;
		else if	(String_.Eq(s, "mult"))	return Type__mult;
		else if	(String_.Eq(s, "add"))	return Type__add;
		else							throw Err_.new_unhandled_default(s);
	}
}
