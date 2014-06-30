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
public interface Xof_img_wkr_convert_djvu_to_tiff {
	boolean Exec(Io_url src, Io_url trg);
}
class Xof_img_wkr_convert_djvu_to_tiff_app implements Xof_img_wkr_convert_djvu_to_tiff {
	public Xof_img_wkr_convert_djvu_to_tiff_app(ProcessAdp process) {this.process = process;} ProcessAdp process;
	public boolean Exec(Io_url src, Io_url trg) {
		process.Run(src, trg);
		return process.Exit_code_pass();
	}
}
class Xof_img_wkr_convert_djvu_to_tiff_mok implements Xof_img_wkr_convert_djvu_to_tiff {
	public Xof_img_wkr_convert_djvu_to_tiff_mok(int w, int h) {this.w = w; this.h = h;} private int w, h;
	public boolean Exec(Io_url src, Io_url trg) {
		Io_mgr._.SaveFilStr(trg, gplx.gfui.SizeAdp_.new_(w, h).XtoStr());
		return true;
	}
}
