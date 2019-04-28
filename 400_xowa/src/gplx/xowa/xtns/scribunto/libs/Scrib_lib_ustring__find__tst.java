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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
import gplx.core.consoles.*;
import gplx.xowa.xtns.scribunto.engines.mocks.*;
public class Scrib_lib_ustring__find__tst {
	private final    Scrib_lib_ustring__find__fxt fxt = new Scrib_lib_ustring__find__fxt();
	@Test  public void Plain() {
		fxt.Test__find("aabaab"        , "b"    ,  2, Bool_.Y, "3;3"); // bytes=1
		fxt.Test__find("¢¢b¢¢b"        , "b"    ,  2, Bool_.Y, "3;3"); // bytes=2
		fxt.Test__find("€€b€€b"        , "b"    ,  2, Bool_.Y, "3;3"); // bytes=3
		fxt.Test__find("𤭢𤭢b𤭢𤭢b"    , "b"    ,  2, Bool_.Y, "3;3"); // bytes=4
		fxt.Test__find("()()"          , "("    ,  2, Bool_.Y, "3;3"); // exact match; note that "(" is invalid regx
		fxt.Test__find("abcd"          , ""     ,  2, Bool_.Y, "2;1"); // empty find should return values; EX:w:Fool's_mate; DATE:2014-03-04
		fxt.Test__find("a€b"           , "€"    ,  1, Bool_.Y, "2;2"); // find is bytes=3
	}
	@Test   public void Bgn__negative() {
		fxt.Test__find("abab"          , "b"    , -1, Bool_.Y, "4;4"); // search from back of String
		fxt.Test__find("abab"          , "b"    , -9, Bool_.Y, "2;2"); // do not throw error if negative index > text.length; ISSUE#:366; DATE:2019-02-23
		fxt.Test__find("𤭢"            , "𤭢"   , -1, Bool_.Y, "1;1"); // fails if "" b/c it would have counted -1 as -1 char instead of -1 codepoint
	}
	@Test  public void Regx__simple() {
		fxt.Test__find("aabaab"        , "b"       ,  2, Bool_.N, "3;3"); // bytes=1
		fxt.Test__find("¢¢b¢¢b"        , "b"       ,  2, Bool_.N, "3;3"); // bytes=2
		fxt.Test__find("€€b€€b"        , "b"       ,  2, Bool_.N, "3;3"); // bytes=3
		fxt.Test__find("𤭢𤭢b𤭢𤭢b"    , "b"       ,  2, Bool_.N, "3;3"); // bytes=4
		fxt.Test__find("abcd"          , "b"       ,  1, Bool_.N, "2;2"); // basic
		fxt.Test__find("abad"          , "a"       ,  2, Bool_.N, "3;3"); // bgn
		fxt.Test__find("abcd"          , "x"       ,  1, Bool_.N, String_.Null_mark);  // no-match
		fxt.Test__find("abcd"          , ""        ,  2, Bool_.N, "2;1"); // empty regx should return values; regx; EX:w:Fool's_mate; DATE:2014-03-04
	}
	@Test   public void Regx__int() { // PURPOSE: allow int find; PAGE:ro.w:Innsbruck DATE:2015-09-12
		fxt.Test__find(123             , "2"       ,  1, Bool_.N, "2;2");
	}
	@Test  public void Regx__groups() {
		fxt.Test__find("a bcd e"       , "(b(c)d)" ,  2, Bool_.N, "3;5;bcd;c"); // groups
		fxt.Test__find("a bcd e"       , "()(b)"   ,  2, Bool_.N, "3;3;3;b");   // groups; empty capture
	}
	@Test  public void Regx__caret() {
		fxt.Test__find("abcd"          , "^(c)"    ,  3, Bool_.N, "3;3;c");	// ^ should be converted to \G; regx; EX:cs.n:Category:1._září_2008; DATE:2014-05-07
	}
	@Test   public void Regx__return_is_int() {
		fxt.Test__find("a"             , "()"      ,  2, Bool_.N, "2;1;2");
	}
	@Test  public void Surrogate__find__value() {	// PURPOSE: handle surrogates in Find PAGE:zh.w:南北鐵路_(越南); DATE:2014-08-28
		fxt.Test__find("aé𡼾\nbî𡼾\n"  , "\n"      ,  1, Bool_.N, "4;4"); // 4 b/c \n starts at pos 4 (super 1)
		fxt.Test__find("aé𡼾\nbî𡼾\n"  , "\n"      ,  5, Bool_.N, "8;8"); // 8 b/c \n starts at pos 8 (super 1)
	}
	@Test  public void Surrogate__find__empty() {	// PURPOSE: handle surrogates in Find PAGE:zh.w:南北鐵路_(越南); DATE:2014-08-28
		fxt.Test__find("aé𡼾\nbî𡼾\n"  , ""        ,  1, Bool_.N, "1;0"); // 4 b/c \n starts at pos 4 (super 1)
		fxt.Test__find("aé𡼾\nbî𡼾\n"  , ""        ,  5, Bool_.N, "5;4"); // 8 b/c \n starts at pos 8 (super 1)
	}
	@Test  public void Balanced__numbered_1() {	// PURPOSE: handle mix of balanced and regular capture; PAGE:en.w:Bahamas
		fxt.Test__find("[[5]]XccY", "%b[]X(%a)%1Y", 1, Bool_.N, "1;9;c");
	}
}
class Scrib_lib_ustring__find__fxt {
	private boolean dbg = false;
	private final    Mock_scrib_fxt fxt = new Mock_scrib_fxt();
	private Scrib_lib lib;
	public Scrib_lib_ustring__find__fxt() {
		fxt.Clear();
		lib = fxt.Core().Lib_ustring().Init();
	}
	public Scrib_lib_ustring__find__fxt Dbg_y_() {dbg = Bool_.Y; return this;}
	public Scrib_lib_ustring__find__fxt Dbg_n_() {dbg = Bool_.N; return this;}
	public void Test__find(String text, String regx, int bgn, boolean plain, String expd) {
		if (dbg) Console_adp__sys.Instance.Write_str(Bld_test_string(text, regx, bgn, plain, expd));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_find, Scrib_kv_utl_.base1_many_(text, regx, bgn, plain), expd);
	}
	public void Test__find(int text, String regx, int bgn, boolean plain, String expd) {
		if (dbg) Console_adp__sys.Instance.Write_str(Bld_test_string(text, regx, bgn, plain, expd));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_find, Scrib_kv_utl_.base1_many_(text, regx, bgn, plain), expd);
	}
	private String Bld_test_string(Object text, String regx, int bgn, boolean plain, String expd) {
		/*
		{| class=wikitable
		! rslt !! expd !! actl !! code
		|}
		*/
		String invk = "{{" + String_.Format("#invoke:Sandbox/Gnosygnu|ustring_find|{0}|{1}|{2}|{3}", Object_.Xto_str_strict_or_empty(text), regx, bgn, plain ? Bool_.True_str : Bool_.False_str) + "}}";
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_a7("|-\n");
		bfr.Add_str_u8("| {{#ifeq:" + invk + "|" + expd + "|<span style='color:green'>pass</span>|<span style='color:red'>fail</span>}}\n");
		bfr.Add_str_u8("| " + expd + "\n");
		bfr.Add_str_u8("| " + invk + "\n");
		bfr.Add_str_u8("| <nowiki>" + invk + "</nowiki>\n");
		return bfr.To_str();
	}
}
/*
TEST:
* URL: https://en.wikipedia.org/wiki/Project:Sandbox
* CODE:
{{#invoke:Sandbox/Gnosygnu|ustring_find|abab|b|3|true}}

MODULE: 
* URL: https://en.wikipedia.org/wiki/Module:Sandbox/Gnosygnu
* CODE:
function p.ustring_find(frame)
  local args = frame.args;
  local rslt = {mw.ustring.find(args[1], args[2], tonumber(args[3]), args[4] == 'true')};

  local rv = '';
  local rslt_len = #rslt;
  for i=1,rslt_len do
    if i ~= 1 then
      rv = rv .. ';'
    end
    rv = rv .. rslt[i]
  end
  return rv;
end
*/
