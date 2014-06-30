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
class Xof_file_stat {
	public Xof_file_stat(int tid) {file_type = Xof_ext_.new_(tid, Xof_ext_.Bry__ary[tid]);} private Xof_ext file_type;
	public int Count_file() {return count_file;} private int count_file;
	public int Count_lnki() {return count_lnki;} private int count_lnki;
	public long Size_orig() {return size_orig;} long size_orig;
	public long Size_300() {return size_300;} long size_300;
	public long Size_250() {return size_250;} long size_250;
	public long Size_220() {return size_220;} long size_220;
	public long Size_200() {return size_200;} long size_200;
	public long Size_180() {return size_180;} long size_180;
	public long Size_150() {return size_150;} long size_150;
	public long Size_120() {return size_120;} long size_120;
	public void Bld(Bry_bfr bfr) {
		bfr.Add(Xof_ext_.Bry__ary[file_type.Id()]).Add_byte(Byte_ascii.Pipe);
		bfr.Add_int_variable(count_file).Add_byte(Byte_ascii.Pipe);
		bfr.Add_long_variable(size_orig).Add_byte(Byte_ascii.Pipe);
		bfr.Add_long_variable(size_300).Add_byte(Byte_ascii.Pipe);
		bfr.Add_long_variable(size_250).Add_byte(Byte_ascii.Pipe);
		bfr.Add_long_variable(size_220).Add_byte(Byte_ascii.Pipe);
		bfr.Add_long_variable(size_200).Add_byte(Byte_ascii.Pipe);
		bfr.Add_long_variable(size_180).Add_byte(Byte_ascii.Pipe);
		bfr.Add_long_variable(size_150).Add_byte(Byte_ascii.Pipe);
		bfr.Add_long_variable(size_120).Add_byte(Byte_ascii.Pipe);
		bfr.Add_byte_nl();
	}
	public void Update_file(Xofo_file file, int lnki_len) {
		count_file++;
		size_orig += file.Orig_size();
		count_lnki += lnki_len;
		for (int i = 0; i < lnki_len; i++) {
			size_300 += Calc_thumb_size(file, 300);
			size_250 += Calc_thumb_size(file, 250);
			size_220 += Calc_thumb_size(file, 220);
			size_200 += Calc_thumb_size(file, 200);
			size_180 += Calc_thumb_size(file, 180);
			size_150 += Calc_thumb_size(file, 150);
			size_120 += Calc_thumb_size(file, 120);
			if (!file_type.Id_is_thumbable_img()) break;
		}
	}
	int Calc_thumb_size(Xofo_file file, int thumb_w) {		
		int file_size = file.Orig_size(), orig_w = file.Orig_w(), orig_h = file.Orig_h();
		switch (file_type.Id()) {
			case Xof_ext_.Id_png: 
			case Xof_ext_.Id_jpg: 
			case Xof_ext_.Id_jpeg: 
			case Xof_ext_.Id_gif: 
			case Xof_ext_.Id_tif:
			case Xof_ext_.Id_tiff: 
			case Xof_ext_.Id_bmp: 
			case Xof_ext_.Id_xcf: 
			{
				if (file_size == 0 || orig_w == 0) return 0;
				int bits = file.Bits();
				if (bits == 0) bits = 8;
				double compression = (double)file_size / (double)((double)orig_w * (double)orig_h * (double)bits);
				int thumb_h = (thumb_w * orig_h) / orig_w;
				int rv = (int)((thumb_w * thumb_h * file.Bits()) * compression); 
				return rv;
			}
			case Xof_ext_.Id_svg: {
				if (file.Orig_w() == 0) return 0;
				int thumb_h = (thumb_w * orig_h) / orig_w;
				int rv = (int)((thumb_w * thumb_h * 8) * .03);
				return rv;
			}
			case Xof_ext_.Id_djvu:
			case Xof_ext_.Id_pdf:
			case Xof_ext_.Id_mid:
			case Xof_ext_.Id_ogg:
			case Xof_ext_.Id_oga:
			case Xof_ext_.Id_ogv:
			case Xof_ext_.Id_flac:
			default:
				return file_size;
		}
	}
}
