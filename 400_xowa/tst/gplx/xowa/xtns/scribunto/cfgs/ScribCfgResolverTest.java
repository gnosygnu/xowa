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
package gplx.xowa.xtns.scribunto.cfgs;

import gplx.Bry_;
import gplx.core.tests.Gftest;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_parser;
import org.junit.Test;

public class ScribCfgResolverTest {
    private final ScribCfgResolverTstr tstr = new ScribCfgResolverTstr();

    @Test public void Pages() {
        ScribCfgResolver resolver = tstr.Init(ScribCfgResolverPages.PROTOTYPE, "fallback"
        , "{ 'pages':"
        , "  ["
        , "    { 'keys':['p1', 'p2']"
        , "    , 'scribunto.invoke':{'regexEngine':'p1re'}"
        , "    }"
        , "  , { 'keys':['p3']"
        , "    , 'scribunto.invoke':{'regexEngine':'p3re'}"
        , "    }"
        , "  ]"
        , "}"
        );
        tstr.TestResolve(resolver, "p1", null, null, "p1re");
        tstr.TestResolve(resolver, "p2", null, null, "p1re");
        tstr.TestResolve(resolver, "p3", null, null, "p3re");
        tstr.TestResolve(resolver, "z", null, null, "fallback");
    }

    @Test public void Funcs() {
        ScribCfgResolver resolver = tstr.Init(ScribCfgResolverFuncs.PROTOTYPE, "fallback"
        , "{ 'funcs':"
        , "  ["
        , "    { 'keys':['f1', 'f2']"
        , "    , 'scribunto.invoke':{'regexEngine':'f1re'}"
        , "    }"
        , "  , { 'keys':['f3']"
        , "    , 'scribunto.invoke':{'regexEngine':'f3re'}"
        , "    }"
        , "  ]"
        , "}"
        );
        tstr.TestResolve(resolver, null, null, "f1", "f1re");
        tstr.TestResolve(resolver, null, null, "f2", "f1re");
        tstr.TestResolve(resolver, null, null, "f3", "f3re");
        tstr.TestResolve(resolver, null, null, "z", "fallback");
    }

    @Test public void Module() {
        ScribCfgResolver resolver = tstr.Init(ScribCfgResolverModule.PROTOTYPE, "fallback"
        , "{ 'funcs':"
        , "  ["
        , "    { 'keys':['f1', 'f2']"
        , "    , 'scribunto.invoke':{'regexEngine':'f1re'}"
        , "    }"
        , "  ]"
        , ", 'pages':"
        , "  ["
        , "    { 'keys':['p1', 'p2']"
        , "    , 'scribunto.invoke':{'regexEngine':'p1re'}"
        , "    }"
        , "  ]"
        , "}"
        );
        tstr.TestResolve(resolver, null, null, "f1", "f1re");
        tstr.TestResolve(resolver, null, null, "f2", "f1re");
        tstr.TestResolve(resolver, "p1", null, null, "p1re");
        tstr.TestResolve(resolver, "p2", null, null, "p1re");
        tstr.TestResolve(resolver, "pz", null, "fz", "fallback");
    }

    @Test public void Wiki() {
        ScribCfgResolver resolver = tstr.Init(ScribCfgResolverWiki.PROTOTYPE, "fallback"
        , "{ 'scribunto.modules':"
        , "  ["
        , "    { 'keys':['m1', 'm2']"
        , "    , 'scribunto.invoke':{'regexEngine':'m1re'}"
        , "    , 'funcs':"
        , "      ["
        , "        { 'keys':['f1', 'f2']"
        , "        , 'scribunto.invoke':{'regexEngine':'m1f1re'}"
        , "        }"
        , "      ]"
        , "    , 'pages':"
        , "      ["
        , "        { 'keys':['p1', 'p2']"
        , "        , 'scribunto.invoke':{'regexEngine':'m1p1re'}"
        , "        }"
        , "      ]"
        , "    }"
        , "  , { 'keys':['m3']"
        , "    , 'scribunto.invoke':{'regexEngine':'m3re'}"
        , "    , 'funcs':"
        , "      ["
        , "        { 'keys':['f1', 'f2']"
        , "        , 'scribunto.invoke':{'regexEngine':'m3f1re'}"
        , "        }"
        , "      ]"
        , "    , 'pages':"
        , "      ["
        , "        { 'keys':['p1', 'p2']"
        , "        , 'scribunto.invoke':{'regexEngine':'m3p1re'}"
        , "        }"
        , "      ]"
        , "    }"
        , "  ]"
        , "}"
        );
        tstr.TestResolve(resolver, "p1", "mz", "f1", "fallback");
        tstr.TestResolve(resolver, null, "m1", "f1", "m1f1re");
        tstr.TestResolve(resolver, null, "m1", "f2", "m1f1re");
        tstr.TestResolve(resolver, "p1", "m1", null, "m1p1re");
        tstr.TestResolve(resolver, "p2", "m1", null, "m1p1re");
        tstr.TestResolve(resolver, "pz", "m1", null, "m1re");
        tstr.TestResolve(resolver, null, "m3", "f1", "m3f1re");
        tstr.TestResolve(resolver, null, "m3", "f2", "m3f1re");
        tstr.TestResolve(resolver, "p1", "m3", null, "m3p1re");
        tstr.TestResolve(resolver, "p2", "m3", null, "m3p1re");
        tstr.TestResolve(resolver, "pz", "m3", null, "m3re");
    }

    @Test public void WikiPageScope() {
        ScribCfgResolver resolver = tstr.Init(ScribCfgResolverMods.PROTOTYPE, "fallback"
        , "{ 'scribunto.modules':"
        , "  ["
        , "    { 'keys':['m1', 'm2']"
        , "    , 'scribunto.invoke':{'regexEngine':'m1re'}"
        , "    , 'funcs':"
        , "      ["
        , "        { 'keys':['f1', 'f2']"
        , "        , 'scribunto.invoke':{'regexEngine':'m1f1re'}"
        , "        }"
        , "      ]"
        , "    }"
        , "  ,"
        , "    { 'keys':['m3']"
        , "    , 'scribunto.invoke':{'regexEngine':'m3re'}"
        , "    , 'funcs':"
        , "      ["
        , "        { 'keys':['f1']"
        , "        , 'scribunto.invoke':{'regexEngine':'m3f1re'}"
        , "        }"
        , "      ]"
        , "    }"
        , "  ]"
        , "}"
        );
        tstr.TestResolve(resolver, null, "mz", null, "fallback");
        tstr.TestResolve(resolver, null, "m1", "f1", "m1f1re");
        tstr.TestResolve(resolver, null, "m2", "f1", "m1f1re");
        tstr.TestResolve(resolver, null, "m1", "fz", "m1re");
        tstr.TestResolve(resolver, null, "m3", "f1", "m3f1re");
        tstr.TestResolve(resolver, null, "m3", "fz", "m3re");
    }
}
class ScribCfgResolverTstr {
    public ScribCfgResolver Init(ScribCfgResolver proto, String fallbackRegexEngine, String... jsonAry) {
        ScribCfg fallbackArgs = new ScribCfg(1, 2, fallbackRegexEngine);
        ScribCfgResolver rv = proto.CloneNew("test");

        String json = Json_doc.Make_str_by_apos(jsonAry);
        Json_doc jdoc = Json_parser.ParseToJdoc(json);
        rv.Load(jdoc.Root_nde(), fallbackArgs);
        return rv;
    }
    public void TestResolve(ScribCfgResolver resolver, String page, String mod, String func, String expdRegexEngine) {
        ScribCfg actl = resolver.Resolve(Bry_.new_u8_safe(page), Bry_.new_u8_safe(mod), Bry_.new_u8_safe(func));
        Gftest.Eq__str(expdRegexEngine, actl.RegexEngine(), func);
    }
}
