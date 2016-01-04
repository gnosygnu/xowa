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
package gplx.xowa.htmls.core.wkrs.xndes.tags; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.langs.htmls.*;
import gplx.xowa.htmls.core.wkrs.xndes.atrs.*;
public class Xohz_tag_regy_ {
	public static Xohz_tag_regy New_dflt() {
		Xohz_tag_regy tags = new Xohz_tag_regy();
		Xohz_atr_regy atrs = tags.Atr_regy();
		// table; cellspacing
		atrs
			.Itms__add_int(Atr__cellpadding		, Gfh_atr_.Bry__cellpadding)	// 1 or 1%
			.Itms__add_int(Atr__cellspacing		, Gfh_atr_.Bry__cellspacing)	// 1 or 1%
			.Itms__add_int(Atr__colspan			, Gfh_atr_.Bry__colspan)
			.Itms__add_int(Atr__rowspan			, Gfh_atr_.Bry__rowspan)
			.Itms__add_enm(Atr__scope			, Gfh_atr_.Bry__scope, Bry_.Ary("row", "col", "rowgroup", "colgroup"))
			.Itms__add_enm(Atr__align			, Gfh_atr_.Bry__align, Bry_.Ary("left", "center", "right"))
			.Itms__add_str(Atr__bgcolor			, Gfh_atr_.Bry__bgcolor)
			;
		byte[] grp__tbl_cell = Bry_.new_a7("tbl.cell");
		atrs.Grps__add(grp__tbl_cell, Gfh_atr_.Bry__scope, Gfh_atr_.Bry__colspan, Gfh_atr_.Bry__rowspan);
		tags.Add(Tag__th			, Gfh_tag_.Bry__th, 1, grp__tbl_cell);
		tags.Add(Tag__td			, Gfh_tag_.Bry__td, 1, grp__tbl_cell);
		return tags;
	}
	public static final int
	  Atr__cellpadding			= 21
	, Atr__cellspacing			= 22
	, Atr__colspan				= 30
	, Atr__rowspan				= 31
	, Atr__scope				= 32
	, Atr__align				= 33
	, Atr__bgcolor				= 34
	;
	public static final int 
	  Tag__th					=  10
	, Tag__td					=  11
	;
}
