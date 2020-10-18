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
package gplx.langs.jsons;

import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.core.tests.Gftest;
import org.junit.Test;

public class JsonDocBldrTest {
    private JsonDocBldrTestUtil util = new JsonDocBldrTestUtil();
    @Test public void Basic() {
        JsonDocBldr bldr = JsonDocBldr.NewRootNde();
        bldr.NdeBgn("nde")
            .KvBool("bool", true)
            .KvInt("int", 123)
            .KvStr("str", "abc")
            .NdeEnd();
        util.Test(bldr
            , "{ 'nde':"
            , "  { 'bool':true"
            , "  , 'int':123"
            , "  , 'str':'abc'"
            , "  }"
            , "}");
    }
}
class JsonDocBldrTestUtil {
    public void Test(JsonDocBldr bldr, String... ary) {
        Bry_bfr bfr = Bry_bfr_.New();
        bldr.ToDoc().Root_grp().Print_as_json(bfr, 0);
        Gftest.Eq__ary__lines(Json_doc.Make_str_by_apos(ary), bfr.To_str_and_clear());
    }
}