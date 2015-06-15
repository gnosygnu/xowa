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
package gplx.xowa.html.hdumps.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.core.brys.*; import gplx.xowa.html.hdumps.data.*; import gplx.xowa.files.repos.*;
public abstract class Xohd_data_itm__base implements Xohd_data_itm {
	public abstract int Img_tid();
	public int			Data_tid()				{return Xohd_data_tid.Tid_img;}
	public byte[]		Lnki_ttl()				{return lnki_ttl;} private byte[] lnki_ttl;
	public byte			Lnki_type()				{return lnki_type;} private byte lnki_type;
	public double		Lnki_upright()			{return lnki_upright;} private double lnki_upright;
	public int			Lnki_w()				{return lnki_w;} private int lnki_w;
	public int			Lnki_h()				{return lnki_h;} private int lnki_h;
	public double		Lnki_time()				{return lnki_time;} private double lnki_time;
	public int			Lnki_page()				{return lnki_page;} private int lnki_page;
	public int			Orig_repo_id()			{return orig_repo_id;} private int orig_repo_id;
	public int			Orig_ext()				{return orig_ext;} private int orig_ext;
	public boolean			File_is_orig()			{return file_is_orig;} private boolean file_is_orig;
	public int			File_w()				{return file_w;} private int file_w;
	public int			Html_uid()				{return html_uid;} private int html_uid;
	public int			Html_w()				{return html_w;} private int html_w;
	public int			Html_h()				{return html_h;} private int html_h;
	public Io_url		Html_view_url()			{return html_view_url;} public void File_url_(Io_url v) {html_view_url = v;} private Io_url html_view_url;
	public abstract byte Html_elem_tid();
	public Xohd_data_itm__base Data_init_base
		( byte[] lnki_ttl, byte lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page
		, int orig_repo_id, int orig_ext, boolean file_is_orig, int file_w
		, int html_uid, int html_w, int html_h
		) {
		this.lnki_ttl = lnki_ttl; this.orig_ext = orig_ext; this.lnki_type = lnki_type;
		this.lnki_w = lnki_w; this.lnki_h = lnki_h; this.lnki_upright = lnki_upright; this.lnki_time = lnki_time; this.lnki_page = lnki_page;
		this.orig_repo_id = orig_repo_id; this.file_is_orig = file_is_orig; this.file_w = file_w;
		this.html_uid = html_uid; this.html_w = html_w; this.html_h = html_h;
		return this;
	}
	public void Data_write(Bry_bfr bfr) {
		bfr	.Add_int_variable(Xohd_data_tid.Tid_img).Add_byte_pipe()
			.Add_int_variable(this.Img_tid()).Add_byte_pipe()
			.Add(lnki_ttl).Add_byte_pipe()
			.Add_int_variable(orig_ext).Add_byte_pipe()
			.Add_byte(lnki_type).Add_byte_pipe()
			.Add_int_variable(lnki_w).Add_byte_pipe()
			.Add_int_variable(lnki_h).Add_byte_pipe()
			.Add_double(lnki_upright).Add_byte_pipe()
			.Add_double(lnki_time).Add_byte_pipe()
			.Add_int_variable(lnki_page).Add_byte_pipe()
			.Add_int_variable(orig_repo_id).Add_byte_pipe()
			.Add_yn(file_is_orig).Add_byte_pipe()
			.Add_int_variable(file_w).Add_byte_pipe()
			.Add_int_variable(html_uid).Add_byte_pipe()
			.Add_int_variable(html_w).Add_byte_pipe()
			.Add_int_variable(html_h).Add_byte_pipe()
			;
		Data_write_hook(bfr);
		bfr.Add_byte_nl();
	}
	public String Data_print() {
		return String_.Concat_with_str("|", Int_.Xto_str(this.Img_tid()), String_.new_u8(lnki_ttl), Int_.Xto_str(html_uid), Int_.Xto_str(html_w), Int_.Xto_str(html_h)
		, Int_.Xto_str(orig_repo_id), Int_.Xto_str(orig_ext), Yn.Xto_str(file_is_orig), Int_.Xto_str(file_w), Double_.Xto_str(lnki_time), Int_.Xto_str(lnki_page)
		);
	}
	@gplx.Virtual public void Data_parse(Bry_rdr rdr) {
		this.lnki_ttl = Xoa_app_.Utl__encoder_mgr().Http_url().Decode(rdr.Read_bry_to_pipe());
		this.orig_ext = rdr.Read_int_to_pipe();
		this.lnki_type = rdr.Read_byte_to_pipe();
		this.lnki_w = rdr.Read_int_to_pipe();
		this.lnki_h = rdr.Read_int_to_pipe();
		this.lnki_upright = rdr.Read_double_to_pipe();
		this.lnki_time = rdr.Read_double_to_pipe();
		this.lnki_page = rdr.Read_int_to_pipe();
		this.orig_repo_id = rdr.Read_int_to_pipe();
		this.file_is_orig = rdr.Read_yn_to_pipe();
		this.file_w = rdr.Read_int_to_pipe();
		this.html_uid = rdr.Read_int_to_pipe();
		this.html_w = rdr.Read_int_to_pipe();
		this.html_h = rdr.Read_int_to_pipe();
	}
	@gplx.Virtual public void Data_write_hook(Bry_bfr bfr) {}
	@Override public String toString() {return this.Data_print();}	// TEST
	public static final int Tid_basic = 0, Tid_gallery = 1;
	public static final int
	  File_repo_id_commons		= Xof_repo_itm_.Repo_remote
	, File_repo_id_local		= Xof_repo_itm_.Repo_local
	, File_repo_id_null			= Xof_repo_itm_.Repo_null
	;
	public static final Xohd_data_itm__base[] Ary_empty = new Xohd_data_itm__base[0];
}
