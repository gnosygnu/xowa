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
package gplx;
import org.junit.*;
public class Bry_fmtr_tst {
	@Test  public void Idx_text()		{tst_Format("a", "a");}
	@Test  public void Idx_1()			{tst_Format("a", "~{0}", "a");}
	@Test  public void Idx_3()			{tst_Format("abc", "~{0}~{1}~{2}", "a", "b", "c");}
	@Test  public void Idx_mix()		{tst_Format("abcde", "a~{0}c~{1}e", "b", "d");}
	@Test  public void Key_basic() 			{tst("~{key}"			, String_.Ary("key")			, ary_("a")				, "a");}
	@Test  public void Key_mult()		 	{tst("~{key1}~{key2}"	, String_.Ary("key1", "key2")	, ary_("a", "b")		, "ab");}
	@Test  public void Key_mix()			{tst("~{key1}~{1}"		, String_.Ary("key1", "key2")	, ary_("a", "b")		, "ab");}
	@Test  public void Key_repeat()			{tst("~{key1}~{key1}"	, String_.Ary("key1")			, ary_("a")				, "aa");}
	@Test  public void Simple() {
		Bry_fmtr fmtr = Bry_fmtr.new_("0~{key1}1~{key2}2", "key1", "key2");
		Tfds.Eq("0.1,2", fmtr.Bld_str_many(".", ","));
	}
	@Test  public void Cmd() {
		Bry_fmtr_tst_mok mok = new Bry_fmtr_tst_mok();
		Bry_fmtr fmtr = Bry_fmtr.new_("0~{key1}2~{<>3<>}4", "key1").Eval_mgr_(mok);
		Tfds.Eq("012~{<>3<>}4", fmtr.Bld_str_many("1"));
		mok.Enabled_(true);
		Tfds.Eq("01234", fmtr.Bld_str_many("1"));
	}
	@Test  public void Err_missing_idx()	{tst_Format("~{0}", "~{0}");}
	String[] ary_(String... ary) {return ary;}
	void tst(String fmt, String[] keys, String[] args, String expd) {
		Bry_fmtr fmtr = new Bry_fmtr().Fmt_(Bry_.new_utf8_(fmt));
		fmtr.Keys_(keys);
		String actl = fmtr.Bld_str_many(args);
		Tfds.Eq(expd, actl);
	}
	void tst_Format(String expd, String fmt, String... args) {
		Bry_fmtr fmtr = new Bry_fmtr().Fmt_(fmt);
		Tfds.Eq(expd, fmtr.Bld_str_many(args));
	}
	@Test  public void Bld_bfr_many_and_set_fmt() {
		Bry_fmtr_fxt fxt = new Bry_fmtr_fxt().Clear();
		fxt.Bld_bfr_many_and_set_fmt("a~{0}c", Object_.Ary("b"), "abc");
	}
}
class Bry_fmtr_tst_mok implements Bry_fmtr_eval_mgr {
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled;
	public byte[] Eval(byte[] cmd) {
		return enabled ? cmd : null;
	}
}
class Bry_fmtr_fxt {
	public Bry_fmtr_fxt Clear() {
		if (fmtr == null) {
			fmtr = Bry_fmtr.new_();
		}
		return this;
	}	private Bry_fmtr fmtr;
	public void Bld_bfr_many_and_set_fmt(String fmt, Object[] args, String expd) {
		fmtr.Fmt_(fmt);
		fmtr.Bld_bfr_many_and_set_fmt(args);
		Tfds.Eq(expd, String_.new_ascii_(fmtr.Fmt()));
	}
}
