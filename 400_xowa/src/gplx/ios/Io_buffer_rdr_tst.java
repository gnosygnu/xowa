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
package gplx.ios; import gplx.*;
import org.junit.*; import gplx.ios.*;
public class Io_buffer_rdr_tst {
	@Before public void init() {
		Io_mgr.Instance.InitEngine_mem();
		fil = Io_url_.mem_fil_("mem/byteStreamRdr.txt");
		ini_Write("0123456789");
		rdr = Io_buffer_rdr.new_(Io_stream_rdr_.file_(fil), 4);
	}	Io_buffer_rdr rdr; Io_url fil;
	@After public void teardown() {rdr.Rls();}
	@Test  public void Bfr_load_all() {
		tst_Bfr("0", "1", "2", "3").tst_ReadDone(false);

		rdr.Bfr_load_all();
		tst_Bfr("4", "5", "6", "7").tst_ReadDone(false);

		rdr.Bfr_load_all();
		tst_Bfr("8", "9");
		rdr.Bfr_load_all();	// NOTE: change to zip_rdrs make eof detection difficult; force another load to ensure that file_pos goes past file_len
		tst_ReadDone(true);	// NOTE: bfr truncated from 4 to 2
	}
	@Test  public void Bfr_load_from() {
		tst_Bfr("0", "1", "2", "3").tst_ReadDone(false);

		rdr.Bfr_load_from(3);	// read from pos 3
		tst_Bfr("3", "4", "5", "6").tst_ReadDone(false);

		rdr.Bfr_load_from(1);	// read from pos 1
		tst_Bfr("4", "5", "6", "7").tst_ReadDone(false);

		rdr.Bfr_load_from(1);
		tst_Bfr("5", "6", "7", "8").tst_ReadDone(false);

		rdr.Bfr_load_from(3);
		rdr.Bfr_load_all();	// NOTE: change to zip_rdrs make eof detection difficult; force another load to ensure that file_pos goes past file_len
		tst_Bfr("8", "9").tst_ReadDone(true);
	}
	private void ini_Write(String s) {Io_mgr.Instance.SaveFilStr(fil, s);}
	Io_buffer_rdr_tst tst_Bfr(String... expdAry) {
		String[] actlAry = new String[rdr.Bfr_len()];
		for (int i = 0; i < actlAry.length; i++)
			actlAry[i] = String_.new_u8(rdr.Bfr(), i, i + 1);
	    Tfds.Eq_ary(expdAry, actlAry);
		return this;
	}
	Io_buffer_rdr_tst tst_ReadDone(boolean expd) {Tfds.Eq(expd, rdr.Fil_eof()); return this;}
}
