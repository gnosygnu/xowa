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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xou_history_itm {
	public Xou_history_itm(byte[] wiki, byte[] page) {
		this.wiki = wiki;
		this.page = page;
		this.key = key_(wiki, page);
		this.view_bgn = DateAdp_.Now();
	}	private Xou_history_itm() {}
	public byte[] Key() {return key;} private byte[] key;
	public byte[] Wiki() {return wiki;} private byte[] wiki;
	public byte[] Page() {return page;} private byte[] page;
	public int View_count() {return view_count;} private int view_count;
	public DateAdp View_bgn() {return view_bgn;} DateAdp view_bgn;
	public DateAdp View_end() {return view_end;} DateAdp view_end;
	public Object Fld(int idx) {
		switch (idx) {
			case Fld_key		: return key;
			case Fld_wiki		: return wiki;
			case Fld_page		: return page;
			case Fld_view_count	: return view_count;
			case Fld_view_bgn	: return view_bgn;
			case Fld_view_end	: return view_end;
			default				: throw Err_.unhandled(idx);
		}
	}
	public void Tally() {
		view_end = view_count == 0 ? view_bgn : DateAdp_.Now();
		view_count++;
	}
	public void Merge(Xou_history_itm merge) {
		view_count += merge.View_count();
		if (merge.View_bgn().compareTo(view_bgn) < CompareAble_.Same) view_bgn = merge.View_bgn();
		if (merge.View_end().compareTo(view_end) > CompareAble_.Same) view_end = merge.View_end();
	}
	public static Xou_history_itm csv_(byte[] ary, Int_obj_ref pos) {
		Xou_history_itm rv = new Xou_history_itm();
		rv.view_bgn		= Bry_.ReadCsvDte(ary, pos, Bry_.Dlm_fld);
		rv.view_end		= Bry_.ReadCsvDte(ary, pos, Bry_.Dlm_fld);
		rv.view_count	= Bry_.ReadCsvInt(ary, pos, Bry_.Dlm_fld);
		rv.wiki			= Bry_.ReadCsvBry(ary, pos, Bry_.Dlm_fld);
		rv.page			= Bry_.ReadCsvBry(ary, pos, Bry_.Dlm_row);
		rv.key			= key_(rv.wiki, rv.page);
		return rv;
	}
	public void Save(Bry_bfr bb) {
		bb	.Add_dte(view_bgn)				.Add_byte(Bry_.Dlm_fld)
			.Add_dte(view_end)				.Add_byte(Bry_.Dlm_fld)
			.Add_int_variable(view_count)	.Add_byte(Bry_.Dlm_fld)
			.Add(wiki)						.Add_byte(Bry_.Dlm_fld)
			.Add(page)						.Add_byte(Bry_.Dlm_row);
	}
	public static byte[] key_(byte[] wiki, byte[] page) {return Bry_.Add(wiki, Key_dlm, page);} private static final byte[] Key_dlm = Xoa_consts.Pipe_bry;
	public static final byte Fld_key = 0, Fld_wiki = 1, Fld_page = 2, Fld_view_count = 3, Fld_view_bgn = 4, Fld_view_end = 5;
}
