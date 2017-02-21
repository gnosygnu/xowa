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
package gplx.xowa.wikis.tdbs.hives; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import org.junit.*;
public class Xowd_regy_mgr_tst {
	Xowd_regy_mgr_fxt fxt = new Xowd_regy_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Create_cur_is_empty() 	{fxt.Create("A").Save().Tst_file(String_.Concat_lines_nl("0|A|A|1"));}
	@Test  public void Create_cur_has_one() 	{fxt.Load(String_.Concat_lines_nl("0|A|A|1")).Create("B").Save().Tst_file(String_.Concat_lines_nl("0|A|A|1", "1|B|B|1"));}
	@Test  public void Update_1st_end() 		{fxt.Load(String_.Concat_lines_nl("0|B|B|1")).Update_add(0, "C").Save().Tst_file(String_.Concat_lines_nl("0|B|C|2"));}
	@Test  public void Update_1st_bgn() 		{fxt.Load(String_.Concat_lines_nl("0|B|B|1")).Update_add(0, "A").Save().Tst_file(String_.Concat_lines_nl("0|A|B|2"));}
	@Test  public void Update_mid() 			{fxt.Load(String_.Concat_lines_nl("0|B|D|2")).Update_add(0, "C").Save().Tst_file(String_.Concat_lines_nl("0|B|D|3"));}
	@Test  public void Update_bgn() 			{fxt.Load(String_.Concat_lines_nl("0|B|D|2")).Update_add(0, "A").Save().Tst_file(String_.Concat_lines_nl("0|A|D|3"));}
	@Test  public void Update_end() 			{fxt.Load(String_.Concat_lines_nl("0|B|D|2")).Update_add(0, "E").Save().Tst_file(String_.Concat_lines_nl("0|B|E|3"));}
	@Test  public void Update_change_bgn() 		{fxt.Load(String_.Concat_lines_nl("0|B|D|2")).Update_change(0, "B", "A").Save().Tst_file(String_.Concat_lines_nl("0|A|D|2"));}
	@Test  public void Update_change_end() 		{fxt.Load(String_.Concat_lines_nl("0|B|D|2")).Update_change(0, "D", "E").Save().Tst_file(String_.Concat_lines_nl("0|B|E|2"));}
	@Test  public void Update_change_mid() 		{fxt.Load(String_.Concat_lines_nl("0|B|D|2")).Update_change(0, "C1", "C2").Save().Tst_file(String_.Concat_lines_nl("0|B|D|2"));}
	@Test  public void Find_none() 				{fxt.Tst_find("A", Xowd_regy_mgr.Regy_null);}
	@Test  public void Find_existing() {
		fxt.Load(String_.Concat_lines_nl
			(	"0|B|D|3"
			,	"1|E|G|3"
			,	"2|H|J|3"
			))
			.Tst_find("B", 0).Tst_find("C", 0).Tst_find("D", 0)
			.Tst_find("A", 0)
			.Tst_find("Z", 2)
			.Tst_find("Da", 1)
			;
	}
	@Test  public void Find_existing_null() {
		fxt.Load(String_.Concat_lines_nl
			(	"0|B|D|3"
			,	"1|D|H|0"
			,	"2|H|J|3"
			))
			.Tst_find("B", 0).Tst_find("C", 0).Tst_find("D", 0)
			.Tst_find("A", 0)
			.Tst_find("Z", 2)
			.Tst_find("Da", 1)	// rely on 
			;
	}
}
class Xowd_regy_mgr_fxt {
	Xowd_regy_mgr mgr; Io_url mgr_url;
	public void Clear() {
		if (mgr == null) {
			mgr_url = Io_url_.mem_fil_("mem/hive_regy.csv");
			Io_mgr.Instance.DeleteFil(mgr_url);
			mgr = new Xowd_regy_mgr(mgr_url);
		}
		else {
			mgr.Clear();
		}
	}
	public Xowd_regy_mgr_fxt Create(String key) {mgr.Create(Bry_.new_a7(key)); return this;}
	public Xowd_regy_mgr_fxt Update_add(int fil_idx, String key) {mgr.Update_add(fil_idx, Bry_.new_a7(key)); return this;}
	public Xowd_regy_mgr_fxt Update_change(int fil_idx, String old_key, String new_key) {mgr.Update_change(fil_idx, Bry_.new_a7(old_key), Bry_.new_a7(new_key)); return this;}
	public Xowd_regy_mgr_fxt Load(String lines) {
		Io_mgr.Instance.SaveFilStr(mgr_url, lines);
		mgr = new Xowd_regy_mgr(mgr_url);
		return this;
	}
	public Xowd_regy_mgr_fxt Save() {mgr.Save(); return this;}
	public Xowd_regy_mgr_fxt Tst_file(String expd) {
		Tfds.Eq_str_lines(expd, Io_mgr.Instance.LoadFilStr(mgr_url));
		return this;
	}
	public Xowd_regy_mgr_fxt Tst_find(String find, int expd) {
		Tfds.Eq(expd, mgr.Files_find(Bry_.new_a7(find)));
		return this;
	}
}
