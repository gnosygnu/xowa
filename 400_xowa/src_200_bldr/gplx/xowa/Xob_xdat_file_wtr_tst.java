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
import org.junit.*;
public class Xob_xdat_file_wtr_tst {
	@Test  public void Write() {
		Io_mgr.I.InitEngine_mem();
		Io_url dir = Io_url_.mem_dir_("mem/dir");
		Xob_xdat_file_wtr wtr = Xob_xdat_file_wtr.new_file_(1000, dir);
		tst_Write(wtr, "<1", "<1");
		tst_Write(wtr, ">a", "<1>a");
		tst_Write(wtr, ">b", "<1>a>b");
		tst_Add_idx(wtr, 7);
		wtr.Bfr().Add(Bry_.new_u8("<2>b>cc"));
		tst_Add_idx(wtr, 15);
		wtr.Bfr().Add(Bry_.new_u8("<3>c>dd"));
		tst_Add_idx(wtr, 23);
		tst_Flush(wtr, String_.Concat
			( "!!!!(|!!!!)|!!!!)|\n"
			, "<1>a>b\n"
			, "<2>b>cc\n"
			, "<3>c>dd\n"
			));
	}
	private void tst_Write(Xob_xdat_file_wtr wtr, String val, String expd) {
		wtr.Bfr().Add(Bry_.new_u8(val));
		Tfds.Eq(expd, String_.new_u8(wtr.Bfr().Bfr(), 0, wtr.Bfr().Len()));
	}
	private void tst_Add_idx(Xob_xdat_file_wtr wtr, int expd) {
		wtr.Add_idx(Byte_ascii.NewLine);
		Tfds.Eq(expd, wtr.Idx()[wtr.Idx_pos() - 1]);
	}
	private void tst_Flush(Xob_xdat_file_wtr wtr, String expd) {
		Io_url url = wtr.Fil_url();
		wtr.Flush(Gfo_usr_dlg_.Test());
		String actl = Io_mgr.I.LoadFilStr(url);
		Tfds.Eq(expd, actl);
	}
}
