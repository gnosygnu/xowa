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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.ios.*; import gplx.core.ios.streams.*;
public class Io_buffer_rdr_tst {
	@Before public void init() {
		Io_mgr.Instance.InitEngine_mem();
		fil = Io_url_.mem_fil_("mem/byteStreamRdr.txt");
		ini_Write("0123456789");
		rdr = Io_buffer_rdr.new_(Io_stream_rdr_.New__raw(fil), 4);
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
