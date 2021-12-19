/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.minifys;

import gplx.core.tooling.dataCollectors.GfoDataCollectorMgr;
import gplx.core.tooling.asserts.TestAssert;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;

public class XoCssMinTest {
    private final XoCssMinTstr tstr = new XoCssMinTstr();
    // NOTE: can pull more tests from https://github.com/yui/yuicompressor/tree/master/tests
    @Test public void extractDataUrls() {
        tstr.Test("extractDataUrls.basic" // all 3 types: `'`, `"`, ``
            , "url('data:a1') url(\"data:a2\") url(data:a3)"
            , "url('data:a1') url(\"data:a2\") url(data:a3)"
            , new TestAssert.Grp("extractDataUrls"
                , new TestAssert.ListEq("preservedTokens", "'data:a1'", "\"data:a2\"", "data:a3")
                , new TestAssert.StringEq("css", "url(___YUICSSMIN_PRESERVED_TOKEN_0___) url(___YUICSSMIN_PRESERVED_TOKEN_1___) url(___YUICSSMIN_PRESERVED_TOKEN_2___)")
            )
        );

        tstr.Test("extractDataUrls.whitespace"
            , "url(  'data:a1  '  )  "
            , "url('data:a1')"
            , new TestAssert.Grp("extractDataUrls"
                , new TestAssert.ListEq("preservedTokens", "'data:a1'")
                , new TestAssert.StringEq("css", "url(___YUICSSMIN_PRESERVED_TOKEN_0___)  ")
            )
        );

        tstr.Test("extractDataUrls.escapedTerminator"
            , "url('data:a\\')bc')"
            , "url('data:a\\')bc')"
            , new TestAssert.Grp("extractDataUrls"
                , new TestAssert.ListEq("preservedTokens", "'data:a\\')bc'")
                , new TestAssert.StringEq("css", "url(___YUICSSMIN_PRESERVED_TOKEN_0___)")
            )
        );
    }
    @Test public void comments() {
        tstr.Test("collectComments.basic"
            , "a /* b */ c /* d */ e"
            , "a c e"
            , new TestAssert.Grp("collectComments"
                , new TestAssert.ListEq("comments", " b ", " d ")
                , new TestAssert.StringEq("css", "a /*___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_0___*/ c /*___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_1___*/ e")
            )
        );
    }
    @Test public void commentsEmpty() {// PURPOSE:handle empty comments; ISSUE#:741 DATE:2020-06-08
        tstr.Test("collectComments.empty"
            , "a /**/ b"
            , "a b" // NOTE: `\s` instead of `\s\s` b/c of "Normalize all whitespace strings to single spaces"
            , new TestAssert.Grp("collectComments"
                , new TestAssert.ListEq("comments", "")
                , new TestAssert.StringEq("css", "a /*___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_0___*/ b")
            )
        );
    }
    @Test public void rgb() {
        tstr.Test("rgb.basic"  ,"rgb (128,128,128)", "#808080");
        tstr.Test("rgb.casing" ,"RgB (128,128,128)", "#808080");
    }
    @Test public void borders() {
        tstr.Test("borders.basic"      , "border:none;"    , "border:0;");
        tstr.Test("borders.background" , "background:none;", "background:0;");
        tstr.Test("borders.casing"     , "bOrDeR:NoNe;"    , "border:0;");
        tstr.Test("borders.close-brace", "{border:none}"   , "{border:0}");
    }
    @Test public void background() {
        tstr.Test("background.basic"       ,"background-position:0;" , "background-position:0 0;");
        tstr.Test("background.ms-transform","ms-transform-origin:0;" , "ms-transform-origin:0 0;");
        tstr.Test("background.casing"      ,"Background-Position:0;" , "background-position:0 0;");
        tstr.Test("background.close-brace" ,"{background-position:0}", "{background-position:0 0}");
    }
    @Test public void pseudo() {
        tstr.Test("swapPseudo.caret"
            , "abc ^ class:val { xyz"
            , "abc ^ class:val{xyz"
            , new TestAssert.Grp("swapPseudo"
                , new TestAssert.StringEq("css", "abc ^ class___YUICSSMIN_PSEUDOCLASSCOLON___val { xyz")
            )
        );

        tstr.Test("swapPseudo.brace"
            , "abc } class:val { xyz"
            , "abc}class:val{xyz"
            , new TestAssert.Grp("swapPseudo"
                , new TestAssert.StringEq("css", "abc } class___YUICSSMIN_PSEUDOCLASSCOLON___val { xyz")
            )
        );
    }
    @Test public void compressHex() {
        tstr.Test("compressHex.basic"        ,"(  #aa1199)"                        , "(#a19)");
        tstr.Test("compressHex.case"         ,"(  #ABCDEF)"                        , "(#abcdef)");
        tstr.Test("compressHex.skip"         ,"color=#ffffff"); // surprisingly, this doesn't compress due to ([^"'=\s])
        tstr.Test("compressHex.chromaExample","filter:chroma(color=\"#FFFFFF\");");
    }
}
class XoCssMinTstr {
    private final XoCssMin min = new XoCssMin();
    private GfoDataCollectorMgr dataCollectorMgr = new GfoDataCollectorMgr();
    public void Mode_(int v) {mode = v;} private int mode = XoCssMin.MODE_NODEJS | XoCssMin.MODE_YCSS_MIN;
    public void Test(String note, String orig, TestAssert.Grp... expdRules) {Test(note, orig, orig, expdRules);}
    public void Test(String note, String orig, String expd, TestAssert.Grp... expdRules) {
        if (expdRules != null)
            min.DataCollectorMgr_(dataCollectorMgr);
        String actl = min.cssmin(orig, -1, mode);
        if (expdRules != null) {
            TestAssert.Test(note, dataCollectorMgr, expdRules);
        }
        GfoTstr.Eq(expd, actl, note);
    }
}
