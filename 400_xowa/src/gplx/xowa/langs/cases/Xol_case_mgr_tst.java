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
package gplx.xowa.langs.cases;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import gplx.types.basics.utls.BoolUtl;
import org.junit.Before;
import org.junit.Test;
public class Xol_case_mgr_tst {		
	@Before public void init() {fxt.Clear();} private Xol_case_mgr_fxt fxt = new Xol_case_mgr_fxt();
	@Test public void Mw_parse() {
		fxt.parse_mw__tst(fxt.itm_both_("A", "a"), fxt.itm_both_("B", "b"));
	}
	@Test public void Xo_parse() {
		fxt.parse_xo__tst(fxt.Init_ltrs_raw(), fxt.itm_both_("a", "A"), fxt.itm_upper_("b", "B"), fxt.itm_lower_("C", "c"));
	}
	@Test public void Upper_a()		{fxt.Init_ltrs().Upper("aAaz", "AAAz");}
	@Test public void Upper_ab()		{fxt.Init_ltrs().Upper("abac", "ABAc");}
	@Test public void Lower_a()		{fxt.Init_ltrs().Lower("aAaZ", "aaaZ");}
	@Test public void Lower_ac()		{fxt.Init_ltrs().Lower("ABAC", "aBac");}
	@Test public void Upper_1st() {
		fxt.Init_ltrs_universal();
		fxt.Test_reuse_1st_upper("a", "A");
		fxt.Test_reuse_1st_upper("abc", "Abc");
		fxt.Test_reuse_1st_upper("");
		fxt.Test_reuse_1st_upper("Abc");
		fxt.Test_reuse_1st_upper("é", "É");
		fxt.Test_reuse_1st_upper("É");
		fxt.Lower("Ι", "ι");	// PURPOSE:test reversal; PAGE:en.d:ἀρχιερεύς DATE:2014-09-02
	}
	@Test public void Turkish_redirect() {	// PURPOSE: lowercase redirect should match uppercase for asymmetric brys; PAGE:tr.w:Zvishavane DATE:2015-09-07
		Hash_adp_bry hash = Hash_adp_bry.c__u8(BoolUtl.N, Xol_case_mgr_.U8());
		byte[] upper = BryUtl.NewU8("YÖNLENDİRME");
		byte[] lower = BryUtl.NewU8("yönlendirme");
		hash.Add(upper, upper);								// add upper to hash
		GfoTstr.Eq(upper, (byte[])hash.Get_by_bry(lower));	// get upper by using lower
	}
//		@Test public void Hack() {
//			Xol_case_itm[] ary = Xol_case_mgr_.Utf_8;
//			Bry_bfr bfr = Bry_bfr_.New();
//			for (int i = 0; i < ary.length; i++) {
//				Xol_case_itm itm = ary[i];
//				bfr.Add_str_a7("xo|");
//				bfr.Add_bry_comma(itm.Src_ary()).Add_byte_pipe();
//				bfr.Add_bry_comma(itm.Trg_ary()).Add_byte_nl();
//			}
//			Io_mgr.Instance.SaveFilStr("C:\\test1.txt", bfr.To_str_and_clear());
//		}
}
class Xol_case_mgr_fxt {
	private Xol_case_mgr case_mgr = Xol_case_mgr_.new_(); private String_bldr sb = String_bldr_.new_();
	public void Clear() {case_mgr.Clear();}
	public Xol_case_itm_bry itm_both_(String src, String trg)	{return new Xol_case_itm_bry(Xol_case_itm_.Tid_both , BryUtl.NewU8(src), BryUtl.NewU8(trg));}
	public Xol_case_itm_bry itm_upper_(String src, String trg) {return new Xol_case_itm_bry(Xol_case_itm_.Tid_upper, BryUtl.NewU8(src), BryUtl.NewU8(trg));}
	public Xol_case_itm_bry itm_lower_(String src, String trg) {return new Xol_case_itm_bry(Xol_case_itm_.Tid_lower, BryUtl.NewU8(src), BryUtl.NewU8(trg));}
	public String Init_ltrs_raw() {
		return StringUtl.ConcatLinesNl
			(	"0|a|A"
			,	"1|b|B"
			,	"2|C|c"
			);
	}
	public Xol_case_mgr_fxt Init_ltrs() {
		case_mgr = Xol_case_mgr_.new_();
		case_mgr.Add_bulk(BryUtl.NewU8(Init_ltrs_raw()));
		return this;
	}
	public Xol_case_mgr_fxt Init_ltrs_universal() {
		case_mgr = Xol_case_mgr_.U8();
		return this;
	}
	public Xol_case_mgr_fxt Upper(String raw_str, String expd) {return Case_build(BoolUtl.Y, raw_str, expd);}
	public Xol_case_mgr_fxt Lower(String raw_str, String expd) {return Case_build(BoolUtl.N, raw_str, expd);}
	public Xol_case_mgr_fxt Case_build(boolean upper, String raw_str, String expd) {
		byte[] raw = BryUtl.NewU8(raw_str);
		byte[] actl = case_mgr.Case_build(upper, raw, 0, raw.length);
		GfoTstr.EqObj(expd, StringUtl.NewU8(actl));
		return this;
	}
	public void parse_xo__tst(String raw, Xol_case_itm_bry... expd) {
		GfoTstr.EqLines(Xto_str(expd), Xto_str(Xol_case_itm_.parse_xo_(BryUtl.NewU8(raw))));
	}
	public void parse_mw__tst(Xol_case_itm_bry... expd) {
		String raw = raw_(expd);
		Xol_case_itm[] actl = Xol_case_itm_.parse_mw_(BryUtl.NewU8(raw));
		GfoTstr.EqLines(Xto_str(expd), Xto_str(actl));
	}
	public String Xto_str(Xol_case_itm[] ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Xol_case_itm itm = ary[i];
			sb.Add(ByteUtl.ToStr(itm.Tid())).AddCharPipe().Add(StringUtl.NewU8(itm.Src_ary())).AddCharPipe().Add(StringUtl.NewU8(itm.Trg_ary())).AddCharNl();
		}
		return sb.ToStrAndClear();
	}
	public String raw_(Xol_case_itm_bry[] itms) {
		int itms_len = itms.length;
		uppers_list.Clear(); lowers_list.Clear();
		for (int i = 0; i < itms_len; i++) {
			Xol_case_itm_bry itm = itms[i];
			String src = StringUtl.NewU8(itm.Src_ary());
			String trg = StringUtl.NewU8(itm.Trg_ary());
			switch (itm.Tid()) {
				case Xol_case_itm_.Tid_both:
					uppers_list.Add(trg); uppers_list.Add(src);
					lowers_list.Add(src); lowers_list.Add(trg);						
					break;
			}
		}
		return raw_str_(uppers_list.ToStrAry(), lowers_list.ToStrAry());
	}	List_adp uppers_list = List_adp_.New(), lowers_list = List_adp_.New();
	String raw_str_(String[] uppers, String[] lowers) {
		sb.Add("a:2:{s:14:\"wikiUpperChars\";a:1046:{");
		raw_ary(sb, uppers);
		sb.Add("}");
		sb.Add("s:14:\"wikiLowerChars\";a:1038:{");
		raw_ary(sb, lowers);
		sb.Add("}}");
		return sb.ToStrAndClear();
	}
	private void raw_ary(String_bldr sb, String[] ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			int itm_len = StringUtl.Len(itm);
			sb.AddFmt("s:{0}:\"{1}\";", itm_len, itm);
		}
	}
	public void Test_reuse_1st_upper(String raw)				{Test_reuse_1st_upper(raw, null, BoolUtl.Y);}
	public void Test_reuse_1st_upper(String raw, String expd)	{Test_reuse_1st_upper(raw, expd, BoolUtl.N);}
	private void Test_reuse_1st_upper(String raw, String expd, boolean expd_is_same) {
		byte[] raw_bry = BryUtl.NewU8(raw);
		byte[] actl_bry = case_mgr.Case_reuse_1st_upper(raw_bry);
		String actl_str = StringUtl.NewU8(actl_bry);
		boolean actl_is_same = ObjectUtl.Eq(raw_bry, actl_bry);	// pointers will be same if no change
		if (expd_is_same) {
			GfoTstr.EqBoolY(actl_is_same, "expd should be same: " + actl_str);
		}
		else {
			GfoTstr.EqBoolY(!actl_is_same, "expd should not be same: " + actl_str);
			GfoTstr.EqObj(expd, actl_str, expd);
		}
	}
}
/*
a:2:{s:14:"wikiUpperChars";a:1046:{s:1:"a";s:1:"A";s:1:"b";}s:14:"wikiLowerChars";a:1038:{s:1:"A";s:1:"a";s:1:"B";}}
*/