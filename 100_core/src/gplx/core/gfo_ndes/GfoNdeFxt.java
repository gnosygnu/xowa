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
package gplx.core.gfo_ndes; import gplx.*; import gplx.core.*;
import gplx.core.type_xtns.*;
public class GfoNdeFxt {
	public GfoNde root_(GfoNde... subs)								{return GfoNde_.root_(subs);}
	public GfoNde tbl_(String name, GfoNde... rows)					{return GfoNde_.tbl_(name, GfoFldList_.Null, rows);}
	public GfoNde nde_(String name, GfoFldList flds, GfoNde... subs)	{return GfoNde_.tbl_(name, flds, subs);}
	public GfoNde row_(GfoFldList flds, Object... vals)				{return GfoNde_.vals_(flds, vals);}
	public GfoNde row_vals_(Object... vals)							{return GfoNde_.vals_(GfoFldList_by_count_(vals.length), vals);}
	public GfoNde csv_dat_(GfoNde... rows)							{return GfoNde_.tbl_("", GfoFldList_.Null, rows);}
	public GfoNde csv_hdr_(GfoFldList flds, GfoNde... rows)			{return GfoNde_.tbl_("", flds, rows);}
	public static GfoNdeFxt new_() {return new GfoNdeFxt();}

	static GfoFldList GfoFldList_by_count_(int count) {
		GfoFldList rv = GfoFldList_.new_();
		for (int i = 0; i < count; i++)
			rv.Add("fld" + Int_.To_str(i), StringClassXtn.Instance);
		return rv;
	}
}
