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
package gplx.xowa; import gplx.*;
import gplx.core.brys.*;
public class Xodb_page_ {
	static final int Txt_len_id = 5, Txt_len_fil_idx = 5, Txt_len_row_idx = 5, Txt_len_type = 1, Txt_len_text_len = 5;
	public static final int Txt_ttl_pos		= Txt_len_id + Txt_len_fil_idx + Txt_len_row_idx + Txt_len_type + Txt_len_text_len + 5;
	public static final int Txt_ttl_len__fixed = Txt_len_id + Txt_len_fil_idx + Txt_len_row_idx + Txt_len_type + Txt_len_text_len + 5 + 1;	// 5=| 1=\n
	public static Xodb_page Txt_ttl_load(byte[] bry) {
		Xodb_page rv = new Xodb_page();
		Txt_ttl_load(rv, bry, 0, bry.length);
		return rv;
	}
	public static void Txt_ttl_load(Xodb_page page, byte[] bry) {Txt_ttl_load(page, bry, 0, bry.length);}
	private static void Txt_ttl_load(Xodb_page page, byte[] bry, int bgn, int end) {
		try {
			page.Id_				(Base85_utl.XtoIntByAry	(bry, bgn +  0, bgn +  4));
			page.Text_db_id_		(Base85_utl.XtoIntByAry	(bry, bgn +  6, bgn + 10));
			page.Db_row_idx_		(Base85_utl.XtoIntByAry	(bry, bgn + 12, bgn + 16));
			page.Type_redirect_		(bry[18] == Byte_ascii.Num_1);
			page.Text_len_			(Base85_utl.XtoIntByAry	(bry, bgn + 20, bgn + 24));
			page.Ttl_wo_ns_			(Bry_.Mid			(bry, bgn + 26, end));
		} catch (Exception e) {throw Err_.err_(e, "parse_by_ttl failed: {0}", String_.new_utf8_(bry, bgn, end));}
	}
	public static void Txt_ttl_save(Bry_bfr bfr, Xodb_page page) {Txt_ttl_save(bfr, page.Id(), page.Text_db_id(), page.Db_row_idx(), page.Type_redirect(), page.Text_len(), page.Ttl_wo_ns());}
	public static void Txt_ttl_save(Bry_bfr bfr, int id, int file_idx, int row_idx, boolean redirect, int text_len, byte[] ttl_wo_ns) {
		bfr	.Add_base85_len_5(id)					.Add_byte_pipe()
			.Add_base85_len_5(file_idx)				.Add_byte_pipe()
			.Add_base85_len_5(row_idx)				.Add_byte_pipe()
			.Add_byte(redirect ? Byte_ascii.Num_1 : Byte_ascii.Num_0).Add_byte_pipe()
			.Add_base85_len_5(text_len)				.Add_byte_pipe()
			.Add(ttl_wo_ns)							.Add_byte_nl()
			;
	}
	public static void Txt_id_load(Xodb_page page, byte[] bry) {Txt_id_load(page, bry, 0, bry.length);}
	private static void Txt_id_load(Xodb_page page, byte[] bry, int bgn, int end) {
		try {
			page.Clear();
			page.Id_			(Base85_utl.XtoIntByAry	(bry, bgn +  0, bgn +  4));
			page.Text_db_id_	(Base85_utl.XtoIntByAry	(bry, bgn +  6, bgn + 10));
			page.Db_row_idx_	(Base85_utl.XtoIntByAry	(bry, bgn + 12, bgn + 16));
			page.Type_redirect_	(bry[18] == Byte_ascii.Num_1);
			page.Text_len_		(Base85_utl.XtoIntByAry	(bry, bgn + 20, bgn + 24));
			page.Ns_id_			(Base85_utl.XtoIntByAry	(bry, bgn + 26, bgn + 30));
			page.Ttl_wo_ns_		(Bry_.Mid			(bry, bgn + 32, end));
		} catch (Exception e) {throw Err_.err_(e, "parse_by_id failed: {0}", String_.new_utf8_(bry, bgn, end));}
	}
	public static void Txt_id_save(Bry_bfr bfr, Xodb_page page) {
		bfr	.Add_base85_len_5(page.Id())					.Add_byte_pipe()
			.Add_base85_len_5(page.Text_db_id())			.Add_byte_pipe()
			.Add_base85_len_5(page.Db_row_idx())			.Add_byte_pipe()
			.Add_byte(page.Type_redirect() ? Byte_ascii.Num_1 : Byte_ascii.Num_0).Add_byte_pipe()
			.Add_base85_len_5(page.Text_len())				.Add_byte_pipe()
			.Add_base85_len_5(page.Ns_id())					.Add_byte_pipe()
			.Add(page.Ttl_wo_ns())							.Add_byte_nl();
	}
	public static void Txt_page_save(Bry_bfr bfr, int id, DateAdp modified_on, byte[] title, byte[] text, boolean add_nl) {
		int ts = Bit_.Xto_int_date_short(modified_on.XtoSegAry());
		bfr	.Add_base85(id	, Base85_utl.Len_int)			.Add_byte(Txt_page_dlm)			// needed for mass template load
			.Add_base85(ts	, Base85_utl.Len_int)			.Add_byte(Txt_page_dlm)
			.Add(title)										.Add_byte(Txt_page_dlm)			// needed for rebuilding ttl files
			.Add(text)										.Add_byte(Txt_page_dlm);
		if (add_nl)
			bfr.Add_byte_nl(); // NOTE: each page row is separated by \t\n
	}
	public static final byte Txt_page_dlm = Byte_ascii.Tab;
	public static final int Txt_page_len__fixed = 1 + 5 + 1 + 5 + 1 + 1 + 1;	// \tid|date|title|text\n
}
