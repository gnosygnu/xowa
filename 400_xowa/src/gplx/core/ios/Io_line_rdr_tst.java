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
import org.junit.*; import gplx.core.envs.*;
public class Io_line_rdr_tst {
	Io_line_rdr_fxt fxt;
	@Before public void init() {
		fxt = new Io_line_rdr_fxt(Io_url_.new_fil_("mem/test.txt"));
		fxt.Clear();
	}
	@Test  public void Basic() {
		fxt.File_lines_(3).tst_Read_til_lines(3, "00", "01", "02");
		fxt.tst_Read_til_lines(1);	// make sure nothing more is read
	}
	@Test  public void Load_3x() {
		fxt.File_lines_(9).Load_len_lines_(3).tst_Read_til_lines(9, "00", "01", "02", "03", "04", "05", "06", "07", "08");
	}
	@Test  public void Load_irregular() {
		fxt.File_lines_(9).Load_len_(4).tst_Read_til_lines(9, "00", "01", "02", "03", "04", "05", "06", "07", "08");
	}
	@Test  public void Load_multiple_files() {
		fxt = new Io_line_rdr_fxt(Io_url_.new_fil_("mem/test0.txt"), Io_url_.new_fil_("mem/test1.txt"), Io_url_.new_fil_("mem/test2.txt"));
		fxt.File_lines_(0, 0, 3).File_lines_(1, 3, 5).File_lines_(2, 5, 9).Load_len_(4).tst_Read_til_lines(9, "00", "01", "02", "03", "04", "05", "06", "07", "08");
	}
	@Test  public void Match() {
		fxt.File_lines_pipe_(9).Load_len_(6);
		fxt.tst_Match("00", "00");
		fxt.tst_Match("01", "01");
		fxt.tst_Match("03", "03");
		fxt.tst_Match("08", "08");
		fxt.tst_Match("12", "");
	}
}
class Io_line_rdr_fxt {
	Io_line_rdr rdr;
	List_adp lines = List_adp_.New(); Bry_bfr tmp = Bry_bfr_.New();
	public Io_line_rdr_fxt(Io_url... urls) {rdr = new Io_line_rdr(Gfo_usr_dlg_.Test(), urls);}
	public Io_line_rdr_fxt Load_len_lines_(int v) {return Load_len_(v * 3);}	// 3: 2=##, 1=\n
	public Io_line_rdr_fxt Load_len_(int v) {rdr.Load_len_(v); return this;}
	public Io_line_rdr_fxt File_lines_(int count) {
		for (int i = 0; i < count; i++)
			tmp.Add_int_fixed(i, 2).Add_byte_nl();
		Io_mgr.Instance.SaveFilBry(rdr.Urls()[0], tmp.To_bry_and_clear());
		return this;
	}
//	public Io_url[] Src_fils() {return src_fils;} public Io_line_rdr_fxt Src_fils_(Io_url[] v) {src_fils = v; return this;} Io_url[] src_fils;
	public Io_line_rdr_fxt tst_Match(String match, String expd) {
		rdr.Key_gen_(Io_line_rdr_key_gen_.first_pipe);
		boolean match_v = rdr.Match(Bry_.new_u8(match));
		String actl = match_v ? String_.new_u8(rdr.Bfr(), rdr.Key_pos_bgn(), rdr.Key_pos_end()) : "";
		Tfds.Eq(expd, actl);
		return this;
	}  
	public Io_line_rdr_fxt File_lines_pipe_(int count) {
		for (int i = 0; i < count; i++)
			tmp.Add_int_fixed(i, 2).Add_byte(Byte_ascii.Pipe).Add_byte_nl();
		Io_mgr.Instance.SaveFilBry(rdr.Urls()[0], tmp.To_bry_and_clear());
		return this;
	}

	public Io_line_rdr_fxt File_lines_(int fil_idx, int bgn, int end) {
		for (int i = bgn; i < end; i++)
			tmp.Add_int_fixed(i, 2).Add_byte_nl();
		Io_mgr.Instance.SaveFilBry(rdr.Urls()[fil_idx], tmp.To_bry_and_clear());
		return this;
	}
	public Io_line_rdr_fxt Clear() {rdr.Clear(); return this;}
	public Io_line_rdr_fxt tst_Read_til_lines(int count, String... expd) {		
		lines.Clear();
		for (int i = 0; i < expd.length; i++)
			expd[i] = expd[i] + Op_sys.Lnx.Nl_str();
		for (int i = 0; i < count; i++) {
			if (rdr.Read_next())
				lines.Add(String_.new_u8(rdr.Bfr(), rdr.Itm_pos_bgn(), rdr.Itm_pos_end()));
			else
				break;
		}
		Tfds.Eq_ary_str(expd, lines.To_str_ary());
		return this;
	}		
}
