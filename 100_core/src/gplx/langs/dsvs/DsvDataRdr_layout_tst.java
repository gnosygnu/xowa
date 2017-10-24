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
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
import org.junit.*; import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*;
public class DsvDataRdr_layout_tst {
	@Test  public void TableName() {
		run_parse_lines("table0, ,\" \",#");
		tst_Layout(0, DsvHeaderItm.Id_TableName);
	}
	@Test  public void Comment() {
		run_parse_lines("-------------, ,\" \",//", "data");		// need dataLine or parser will throw away standalone header
		tst_Layout(0, DsvHeaderItm.Id_Comment);
	}
	@Test  public void BlankLine() {
		run_parse_lines("", "data");								// need dataLine or parser will throw away standalone header
		tst_Layout(0, DsvHeaderItm.Id_BlankLine);
	}
	@Test  public void LeafNames() {
		run_parse_lines("id,name, ,\" \",@");
		tst_Layout(0, DsvHeaderItm.Id_LeafNames);
	}
	@Test  public void LeafTypes() {
		run_parse_lines("int," + StringClassXtn.Key_const + ", ,\" \",$");
		tst_Layout(0, DsvHeaderItm.Id_LeafTypes);
	}
	@Test  public void Combined() {
		run_parse_lines
			(	""
			,	"-------------, ,\" \",//"
			,	"table0, ,\" \",#"
			,	"int," + StringClassXtn.Key_const + ", ,\" \",$"
			,	"id,name, ,\" \",@"
			,	"-------------, ,\" \",//"
			,	"0,me"
			);
		tst_Layout(0
			, DsvHeaderItm.Id_BlankLine
			, DsvHeaderItm.Id_Comment
			, DsvHeaderItm.Id_TableName
			, DsvHeaderItm.Id_LeafTypes
			, DsvHeaderItm.Id_LeafNames
			, DsvHeaderItm.Id_Comment
			);
	}
	@Test  public void Tbl_N() {
		run_parse_lines
			(	""
			,	"*************, ,\" \",//"
			,	"table0, ,\" \",#"
			,	"-------------, ,\" \",//"
			,	"0,me"
			,	""
			,	"*************, ,\" \",//"
			,	"table1, ,\" \",#"
			,	"	extended data, ,\" \",//"
			,	"-------------, ,\" \",//"
			,	"1,you,more"
			);
		tst_Layout(0
			, DsvHeaderItm.Id_BlankLine
			, DsvHeaderItm.Id_Comment
			, DsvHeaderItm.Id_TableName
			, DsvHeaderItm.Id_Comment
			);
		tst_Layout(1
			, DsvHeaderItm.Id_BlankLine
			, DsvHeaderItm.Id_Comment
			, DsvHeaderItm.Id_TableName
			, DsvHeaderItm.Id_Comment
			, DsvHeaderItm.Id_Comment
			);
	}
	@Test  public void Tbl_N_FirstIsEmpty() {
		run_parse_lines
			(	""
			,	"*************, ,\" \",//"
			,	"table0, ,\" \",#"
			,	"-------------, ,\" \",//"
			,	""
			,	""
			,	"*************, ,\" \",//"
			,	"table1, ,\" \",#"
			,	"	extended data, ,\" \",//"
			,	"-------------, ,\" \",//"
			,	"1,you,more"
			);
		tst_Layout(0
			, DsvHeaderItm.Id_BlankLine
			, DsvHeaderItm.Id_Comment
			, DsvHeaderItm.Id_TableName
			, DsvHeaderItm.Id_Comment
			);
		tst_Layout(1
			, DsvHeaderItm.Id_BlankLine
			, DsvHeaderItm.Id_BlankLine
			, DsvHeaderItm.Id_Comment
			, DsvHeaderItm.Id_TableName
			, DsvHeaderItm.Id_Comment
			, DsvHeaderItm.Id_Comment
			);
	}
	void run_parse_lines(String... ary) {
		String raw = String_.Concat_lines_crlf(ary);
		DsvParser parser = DsvParser.dsv_();
		root = parser.ParseAsNde(raw);
	}
	void tst_Layout(int subIdx, int... expd) {
		GfoNde tbl = root.Subs().FetchAt_asGfoNde(subIdx);
		DsvStoreLayout layout = (DsvStoreLayout)tbl.EnvVars().Get_by(DsvStoreLayout.Key_const);
		int[] actl = new int[layout.HeaderList().Count()];
		for (int i = 0; i < actl.length; i++)
			actl[i] = layout.HeaderList().Get_at(i).Id();
		Tfds.Eq_ary(expd, actl);
	}
	GfoNde root;
}
