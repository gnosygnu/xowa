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
package gplx.langs.mustaches;

import gplx.Bry_;
import gplx.Bry_bfr_;
import gplx.core.tests.Gftest;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_parser;
import org.junit.Test;

public class JsonMustacheNdeTest {
    private JsonMustacheNdeTestUtil util = new JsonMustacheNdeTestUtil();

    @Test public void Str() {
        util.TestWrite("[{{key}}]", "{'key':'abc'}", "[abc]");
    }
    @Test public void Bool() {
        String mustache = "[{{#key}}y{{/key}}]";
        util.TestWrite(mustache, "{'key':true}", "[y]");
        util.TestWrite(mustache, "{'key':false}", "[]");
    }
    @Test public void Ary() {
        String mustache = "[{{#group}}{{key}} {{/group}}]";
        util.TestWrite(mustache, "{'group':[{'key':'a'}, {'key':'b'}, {'key':'c'}]}", "[a b c ]");
    }
    @Test public void SectionPropWithDot() {
        String mustache = "[{{#key}}{{.}}{{/key}}]";
        util.TestWrite(mustache, "{'key':'test'}", "[test]");
        util.TestWrite(mustache, "{'key1':'test'}", "[]");
    }
    @Test public void SectionPropWithoutDot() {
        String mustache = "[{{#prop}}{{propx}}{{/prop}}]";
        util.TestWrite(mustache, "{'prop':'test'}", "[]");
        util.TestWrite(mustache, "{'propx':'test'}", "[]");
    }
    @Test public void Dot() {// NOTE: online demo gives `([object Object])`; https://mustache.github.io/#demo
        String mustache = "({{.}})";
        util.TestWrite(mustache, "{'key':'test'}", "()");
    }
}
class JsonMustacheNdeTestUtil {
    public void TestWrite(String mustache, String json, String expd) {
        // parse JSON to mustache itm
        Json_doc jdoc = Json_parser.ParseToJdoc(Json_doc.Make_str_by_apos(json));
        JsonMustacheNde nde = new JsonMustacheNde(jdoc.Root_nde());

        // parse template
        Mustache_tkn_itm actl_itm = new Mustache_tkn_parser().Parse(Bry_.new_u8(mustache));

        // render
        Mustache_bfr bfr = new Mustache_bfr(Bry_bfr_.New());
		actl_itm.Render(bfr, new Mustache_render_ctx().Init(nde));

        // test
		Gftest.Eq__ary__lines(expd, bfr.To_str_and_clear());
    }
}