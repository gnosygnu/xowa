/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.xowa.addons.wikis.ctgs.htmls.*;
public class Xoa_ctg_mgr implements Gfo_invk {
	public void Init_by_app(Xoae_app app) {}
	public boolean Pagecats_grouping_enabled() {return pagecats_grouping_enabled;} private boolean pagecats_grouping_enabled = false;
	public byte Missing_ctg_cls_tid() {return missing_ctg_cls_tid;} private byte missing_ctg_cls_tid = Missing_ctg_cls_red_tid;
	public byte[] Missing_ctg_cls_css() {
		switch (missing_ctg_cls_tid) {
			case Missing_ctg_cls_normal_tid:					return Missing_ctg_cls_normal_css;
			case Missing_ctg_cls_hide_tid:						return Missing_ctg_cls_hide_css;
			case Missing_ctg_cls_red_tid:						return Missing_ctg_cls_red_css;
			default:											throw Err_.new_unhandled(missing_ctg_cls_tid);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_missing_ctg_cls))				return Missing_ctg_cls_tid_print(missing_ctg_cls_tid);
		else if	(ctx.Match(k, Invk_missing_ctg_cls_))				missing_ctg_cls_tid = Missing_ctg_cls_tid_parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_missing_ctg_cls_list))			return Options_missing_ctg_cls_list;
		else if	(ctx.Match(k, Invk_pagecats_grouping_enabled))		return Yn.To_str(pagecats_grouping_enabled);
		else if	(ctx.Match(k, Invk_pagecats_grouping_enabled_))		pagecats_grouping_enabled = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_missing_ctg_cls = "missing_ctg_cls", Invk_missing_ctg_cls_ = "missing_ctg_cls_", Invk_missing_ctg_cls_list = "missing_ctg_cls_list", Invk_pagecats_grouping_enabled = "pagecats_grouping_enabled", Invk_pagecats_grouping_enabled_ = "pagecats_grouping_enabled_";
	private static final byte Missing_ctg_cls_normal_tid = 0, Missing_ctg_cls_hide_tid = 1, Missing_ctg_cls_red_tid = 2;
	private static final String Missing_ctg_cls_normal_str = "normal", Missing_ctg_cls_hide_str = "hide", Missing_ctg_cls_red_str = "red_link";
	private static final    byte[] Missing_ctg_cls_normal_css = Bry_.new_a7(".xowa-missing-category-entry {}"), Missing_ctg_cls_hide_css = Bry_.new_a7(".xowa-missing-category-entry {display: none;}"), Missing_ctg_cls_red_css = Bry_.new_a7(".xowa-missing-category-entry {color: red;}");
	private static Keyval[] Options_missing_ctg_cls_list = Keyval_.Ary(Keyval_.new_(Missing_ctg_cls_normal_str), Keyval_.new_(Missing_ctg_cls_hide_str), Keyval_.new_(Missing_ctg_cls_red_str)); 
	private static byte Missing_ctg_cls_tid_parse(String v) {
		if		(String_.Eq(v, Missing_ctg_cls_normal_str))		return Missing_ctg_cls_normal_tid;
		else if	(String_.Eq(v, Missing_ctg_cls_hide_str))		return Missing_ctg_cls_hide_tid;
		else if	(String_.Eq(v, Missing_ctg_cls_red_str))		return Missing_ctg_cls_red_tid;
		else													throw Err_.new_unhandled(v);
	}
	private static String Missing_ctg_cls_tid_print(byte v) {
		switch (v) {
			case Missing_ctg_cls_normal_tid:					return Missing_ctg_cls_normal_str;
			case Missing_ctg_cls_hide_tid:						return Missing_ctg_cls_hide_str;
			case Missing_ctg_cls_red_tid:						return Missing_ctg_cls_red_str;
			default:											throw Err_.new_unhandled(v);
		}
	}
	public static final byte Version_null = Byte_.Zero, Version_1 = 1, Version_2 = 2;
	public static final byte Tid__subc = 0, Tid__file = 1, Tid__page = 2, Tid___max = 3;	// SERIALIZED; cat_link.cl_type_id
	public static final byte Hidden_n = Byte_.Zero, Hidden_y = (byte)1;
	public static final String Html__cls__str = "CategoryTreeLabel CategoryTreeLabelNs14 CategoryTreeLabelCategory";
	public static final    byte[] Html__cls__bry = Bry_.new_a7(Html__cls__str);
}
