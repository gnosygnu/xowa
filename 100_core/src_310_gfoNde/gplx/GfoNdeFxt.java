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
package gplx;
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
