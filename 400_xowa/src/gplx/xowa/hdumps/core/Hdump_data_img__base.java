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
package gplx.xowa.hdumps.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.xowa.hdumps.dbs.*; import gplx.xowa.files.repos.*;
public abstract class Hdump_data_img__base implements Hdump_data_itm {
	public Hdump_data_img__base Init_by_base(byte[] lnki_ttl, int html_uid, int html_w, int html_h, int file_repo_id, int file_ext_id, boolean file_is_orig, int file_w, double file_time, int file_page) {
		this.lnki_ttl = lnki_ttl;
		this.html_uid = html_uid;
		this.html_w = html_w;
		this.html_h = html_h;
		this.file_repo_id = file_repo_id;
		this.file_ext_id = file_ext_id;
		this.file_is_orig = file_is_orig;
		this.file_w = file_w;
		this.file_time = file_time;
		this.file_page = file_page;
		return this;
	}
	public int Data_tid() {return Hdump_data_tid.Tid_img;}
	public abstract int Img_tid();
	public byte[] Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public int Html_uid() {return html_uid;} private int html_uid;
	public int Html_w() {return html_w;} private int html_w;
	public int Html_h() {return html_h;} private int html_h;
	public int File_repo_id() {return file_repo_id;} private int file_repo_id;
	public int File_ext_id() {return file_ext_id;} private int file_ext_id;
	public boolean File_is_orig() {return file_is_orig;} private boolean file_is_orig;
	public int File_w() {return file_w;} private int file_w;
	public double File_time() {return file_time;} private double file_time;
	public int File_page() {return file_page;} private int file_page;
	public Io_url File_url() {return file_url;} public void File_url_(Io_url v) {file_url = v;} private Io_url file_url;
	public String Data_print() {
		return String_.Concat_with_str("|", Int_.Xto_str(this.Img_tid()), String_.new_utf8_(lnki_ttl), Int_.Xto_str(html_uid), Int_.Xto_str(html_w), Int_.Xto_str(html_h)
		, Int_.Xto_str(file_repo_id), Int_.Xto_str(file_ext_id), Yn.Xto_str(file_is_orig), Int_.Xto_str(file_w), Double_.Xto_str(file_time), Int_.Xto_str(file_page)
		);
	}
	public void Data_write(Bry_bfr bfr) {
		Data_write_static(bfr, this.Img_tid(), lnki_ttl, html_uid, html_w, html_h, file_repo_id, file_ext_id, file_is_orig, file_w, file_time, file_page);
		Data_write_hook(bfr);
		bfr.Add_byte_nl();
	}
	public static void Data_write_static(Bry_bfr bfr, int img_tid, byte[] lnki_ttl, int html_uid, int html_w, int html_h, int file_repo_id, int file_ext_id, boolean file_is_orig, int file_w, double file_time, int file_page) {
		bfr	.Add_int_variable(Hdump_data_tid.Tid_img).Add_byte_pipe()
			.Add_int_variable(img_tid).Add_byte_pipe()
			.Add(lnki_ttl).Add_byte_pipe()
			.Add_int_variable(html_uid).Add_byte_pipe()
			.Add_int_variable(html_w).Add_byte_pipe()
			.Add_int_variable(html_h).Add_byte_pipe()
			.Add_int_variable(file_repo_id).Add_byte_pipe()
			.Add_int_variable(file_ext_id).Add_byte_pipe()
			.Add_yn(file_is_orig).Add_byte_pipe()
			.Add_int_variable(file_w).Add_byte_pipe()
			.Add_double(file_time).Add_byte_pipe()
			.Add_int_variable(file_page).Add_byte_pipe()
			;
	}
	@gplx.Virtual public void Data_write_hook(Bry_bfr bfr) {}
	public static final Hdump_data_img__base[] Ary_empty = new Hdump_data_img__base[0];
	public static final int Tid_basic = 0, Tid_gallery = 1;
	@Override public String toString() {return this.Data_print();}	// TEST
	public static final int File_repo_id_commons = Xof_repo_itm.Repo_remote, File_repo_id_local = Xof_repo_itm.Repo_local, File_repo_id_null = Xof_repo_itm.Repo_null;
}
