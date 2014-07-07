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
public class Xop_xatr_parser_tst {		
	@Test  public void Kv_quote_double() 		{fxt.tst_("a=\"b\"", fxt.new_atr_("a", "b"));} private Xop_xatr_parser_fxt fxt = new Xop_xatr_parser_fxt();
	@Test  public void Kv_quote_single()		{fxt.tst_("a='b'", fxt.new_atr_("a", "b"));}
	@Test  public void Kv_quote_none()			{fxt.tst_("a=b", fxt.new_atr_("a", "b"));}
	@Test  public void Kv_empty()				{fxt.tst_("a=''", fxt.new_atr_("a", ""));}
	@Test  public void Kv_key_has_underline()	{fxt.tst_("a_b=c", fxt.new_atr_("a_b", "c"));}
	@Test  public void Val_quote_none()			{fxt.tst_("b", fxt.new_atr_("b", "b"));}
	@Test  public void Val_quote_none_ws()		{fxt.tst_(" b ", fxt.new_atr_("b", "b"));}	// PURPOSE:discovered while writing test for ref's "lower-alpha" DATE:2014-07-03
	@Test  public void Invalid_key_plus() 		{fxt.tst_("a+b", fxt.new_invalid_(0, 3));}
	@Test  public void Invalid_key_plus_many() 	{fxt.tst_("a+b c=d", fxt.new_invalid_(0, 3), fxt.new_atr_("c", "d"));}
	@Test  public void Invalid_val_plus() 		{fxt.tst_("a=b+c", fxt.new_invalid_(0, 5));}
	@Test  public void Invalid_recover() 		{fxt.tst_("* a=b", fxt.new_invalid_(0, 1), fxt.new_atr_("a", "b"));}	// PURPOSE: * is invalid, but should not stop parsing of a=b
	@Test  public void Nowiki_val() 			{fxt.tst_("a=<nowiki>'b'</nowiki>", fxt.new_atr_("a", "b").Expd_atr_rng_(0, 13).Expd_key_("a").Expd_val_("b"));}
	@Test  public void Nowiki_key()				{fxt.tst_("<nowiki>a=b</nowiki>", fxt.new_atr_("a", "b").Expd_atr_rng_(8, 11));}
	@Test  public void Nowiki_key_2()			{fxt.tst_("a<nowiki>b</nowiki>c=d", fxt.new_atr_("abc", "d").Expd_atr_rng_(0, 22));}
	@Test  public void Nowiki_key_3()			{fxt.tst_("a<nowiki>=</nowiki>\"b\"", fxt.new_atr_("a", "b").Expd_atr_rng_(0, 22));}	// EX:fr.w:{{Portail|Transpédia|Californie}}
	@Test  public void Nowiki_quote()			{fxt.tst_("a=\"b<nowiki>c</nowiki>d<nowiki>e</nowiki>f\"", fxt.new_atr_("a", "bcdef"));}
	@Test  public void Int_value() 				{fxt.tst_int("a='-123'", -123);}
	@Test  public void Many_apos()				{fxt.tst_("a='b' c='d' e='f'", fxt.new_atr_("a", "b"), fxt.new_atr_("c", "d"), fxt.new_atr_("e", "f"));}
	@Test  public void Many_raw()				{fxt.tst_("a=b c=d e=f", fxt.new_atr_("a", "b"), fxt.new_atr_("c", "d"), fxt.new_atr_("e", "f"));}
	@Test  public void Ws_ini()					{fxt.tst_(" a='b'", fxt.new_atr_("a", "b").Expd_atr_rng_(1, 6));}
	@Test  public void Ws_end()					{fxt.tst_(" a='b' c='d'", fxt.new_atr_("a", "b").Expd_atr_rng_(1, 6), fxt.new_atr_("c", "d").Expd_atr_rng_(7, 12));}
	@Test  public void Quote_ws_nl()			{fxt.tst_("a='b\nc'", fxt.new_atr_("a", "b c"));}
	@Test  public void Quote_ws_mult()			{fxt.tst_("a='b  c'", fxt.new_atr_("a", "b c"));}
	@Test  public void Quote_ws_mult_mult()		{fxt.tst_("a='b  c d'", fxt.new_atr_("a", "b c d"));}	// PURPOSE: fix wherein 1st-gobble gobbled rest of spaces (was b cd)
	@Test  public void Quote_apos()				{fxt.tst_("a=\"b c'd\"", fxt.new_atr_("a", "b c'd"));}	// PURPOSE: fix wherein apos was gobbled up; EX: s:Alice's_Adventures_in_Wonderland; DATE:2013-11-22
	@Test  public void Quote_apos_2()			{fxt.tst_("a=\"b'c d\"", fxt.new_atr_("a", "b'c d"));}	// PURPOSE: fix wherein apos was causing "'b'c d"; EX:s:Grimm's_Household_Tales,_Volume_1; DATE:2013-12-22
	@Test  public void Multiple()				{fxt.tst_("a b1 c", fxt.new_atr_("a", "a"), fxt.new_atr_("b1", "b1"), fxt.new_atr_("c", "c"));}
	@Test  public void Ws()						{fxt.tst_("a  = 'b'", fxt.new_atr_("a", "b"));}			// PURPOSE: fix wherein multiple space was causing "a=a"; EX:fr.s:La_Sculpture_dans_les_cimetières_de_Paris/Père-Lachaise; DATE:2014-01-18
	@Test  public void Dangling_eos() 			{fxt.tst_("a='b' c='d", fxt.new_atr_("a", "b"), fxt.new_invalid_(6, 10));}	// PURPOSE: handle dangling quote at eos; PAGE:en.w:Aubervilliers DATE:2014-06-25
	@Test  public void Dangling_bos() 			{fxt.tst_("a='b c=d", fxt.new_invalid_(0, 4), fxt.new_atr_("c", "d"));}	// PURPOSE: handle dangling quote at bos; resume at next valid atr; PAGE:en.w:Aubervilliers DATE:2014-06-25
/*
TODO:
change ws to be end; EX: "a=b c=d" atr1 ends at 4 (not 3)
*/
//	@Test  public void Val_quote_none_many() {
//		fxt.tst_("a b", fxt.new_atr_("", "a"), fxt.new_atr_("", "b"));
////		fxt.tst_("a='b' c d e='f'", fxt.new_atr_("a", "b"), fxt.new_atr_("", "c"), fxt.new_atr_("", "d"), fxt.new_atr_("e", "f"));
//	}
}
class Xop_xatr_parser_fxt {
	Xop_xatr_parser parser = new Xop_xatr_parser();
	Tst_mgr tst_mgr = new Tst_mgr();
	public Xop_xatr_itm_chkr new_invalid_(int bgn, int end) {return new Xop_xatr_itm_chkr().Expd_atr_rng_(bgn, end).Expd_typeId_(Xop_xatr_itm.Tid_invalid);}
	public Xop_xatr_itm_chkr new_atr_(String key, String val) {return new Xop_xatr_itm_chkr().Expd_key_(key).Expd_val_(val);}
	public void tst_(String src_str, Xop_xatr_itm_chkr... expd) {
		byte[] src = Bry_.new_utf8_(src_str);
		Gfo_msg_log msg_log = new Gfo_msg_log(Xoa_app_.Name);
		Xop_xatr_itm[] actl = parser.Parse(msg_log, src, 0, src.length);
		tst_mgr.Vars().Clear().Add("raw_bry", src);
		tst_mgr.Tst_ary("xatr:", expd, actl);
	}
	public void tst_int(String src_str, int... expd) {
		byte[] src = Bry_.new_utf8_(src_str);
		Gfo_msg_log msg_log = new Gfo_msg_log(Xoa_app_.Name);
		Xop_xatr_itm[] actl_atr = parser.Parse(msg_log, src, 0, src.length);
		int[] actl = new int[actl_atr.length];
		
		for (int i = 0; i < actl.length; i++)
			actl[i] = actl_atr[i].Val_as_int_or(src, 0);
		Tfds.Eq_ary(expd, actl);
	}
}
class Xop_xatr_itm_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Xop_xatr_itm.class;}
	public Xop_xatr_itm_chkr Expd_atr_rng_(int bgn, int end) {expd_atr_bgn = bgn; expd_atr_end = end; return this;} private int expd_atr_bgn = -1, expd_atr_end = -1;
	public Xop_xatr_itm_chkr Expd_key_rng_(int bgn, int end) {expd_key_bgn = bgn; expd_key_end = end; return this;} private int expd_key_bgn = -1, expd_key_end = -1;
	public Xop_xatr_itm_chkr Expd_key_(String v) {expd_key = v; return this;} private String expd_key;
	public Xop_xatr_itm_chkr Expd_val_(String v) {expd_val = v; return this;} private String expd_val;
	public Xop_xatr_itm_chkr Expd_typeId_(byte v) {expd_typeId = v; return this;} private byte expd_typeId = Xop_xatr_itm.Tid_null;
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Xop_xatr_itm actl = (Xop_xatr_itm)actl_obj;
		int err = 0;
		err += mgr.Tst_val(expd_typeId == Xop_xatr_itm.Tid_null, path, "atr_typeId", expd_typeId, actl.Tid());
		err += mgr.Tst_val(expd_atr_bgn == -1, path, "atr_bgn", expd_atr_bgn, actl.Atr_bgn());
		err += mgr.Tst_val(expd_atr_end == -1, path, "atr_end", expd_atr_end, actl.Atr_end());
		err += mgr.Tst_val(expd_key_bgn == -1, path, "key_bgn", expd_key_bgn, actl.Key_bgn());
		err += mgr.Tst_val(expd_key_end == -1, path, "key_end", expd_key_end, actl.Key_end());
		if (actl.Key_bry() == null)
			err += mgr.Tst_val(expd_key == null, path, "key", expd_key, mgr.Vars_get_bry_as_str("raw_bry", actl.Key_bgn(), actl.Key_end()));
		else
			err += mgr.Tst_val(expd_key == null, path, "key", expd_key, String_.new_utf8_(actl.Key_bry()));
		if (actl.Val_bry() == null)
			err += mgr.Tst_val(expd_val == null, path, "val", expd_val, mgr.Vars_get_bry_as_str("raw_bry", actl.Val_bgn(), actl.Val_end()));
		else
			err += mgr.Tst_val(expd_val == null, path, "val", expd_val, String_.new_utf8_(actl.Val_bry()));
		return err;
	}
}
/*
*/
