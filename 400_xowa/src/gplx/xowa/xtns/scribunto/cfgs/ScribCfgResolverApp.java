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

import gplx.Err_;
import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.String_;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_nde;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xow_wiki;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.apps.settings.CfgResolver;

public class ScribCfgResolverApp implements ScribCfgResolver, CfgResolver {
    private final Xoa_app app;
    private final Ordered_hash wikis = Ordered_hash_.New_bry();
    private ScribCfg appInvokeArgs;
    public ScribCfgResolverApp(Xoa_app app) {
        this.app = app;
    }
    @Override public String Key() {return KEY;} private static final String KEY = "scribCfgResolverApp";
    @Override public ScribCfg Fallback() {return fallback;} @Override public void FallbackSet(ScribCfg v) {fallback = v;} private ScribCfg fallback;
    @Override public void WhenWikiCreated(Xow_wiki wikii) {
        SetResolverCfg(wikii);
    }
    @Override public void WhenCfgChanged(Json_doc jdoc) {
        Json_nde appNde = jdoc.Root_nde();
        this.appInvokeArgs = ScribCfgResolverUtil.ParseCfgOr(appNde, null);
        Load(appNde, appInvokeArgs);
    }
    @Override public ScribCfg Resolve(byte[] page, byte[] mod, byte[] func) {
        throw Err_.new_unimplemented_w_msg("appResolver should not be called; only wikiResolver");
    }
    @Override public void Load(Json_nde owner, ScribCfg ownerFallback) {
        ScribCfgResolverUtil.LoadNode(owner, ownerFallback, "wikis", wikis, this, ScribCfgResolverWiki.PROTOTYPE);

        // iterate all wikis; needed b/c wiki could get created before Cfg gets fired
        for (int i = 0; i < app.Wiki_mgri().Count(); i++) {
            Xow_wiki wiki = app.Wiki_mgri().Get_at(i);
            SetResolverCfg(wiki);
        }
    }
    private void SetResolverCfg(Xow_wiki wikii) {
        Xowe_wiki wiki = (Xowe_wiki)wikii;
        byte[] key = wiki.Domain_bry();
        ScribCfgResolverWiki wikiScope = (ScribCfgResolverWiki)wikis.Get_by(key);
        if (wikiScope == null) { // no defined cfg; create defailt
            wikiScope = new ScribCfgResolverWiki(String_.new_u8(key));
            wikis.Add(key, wikiScope); // cache it for future
        }
        wiki.Parser_mgr().Scrib().CfgResolverSet(wikiScope);
    }
    @Override public ScribCfgResolver CloneNew(String key) {return new ScribCfgResolverApp(null);}
    public static final ScribCfgResolver PROTOTYPE = new ScribCfgResolverApp(null);
}
class ScribCfgResolverWiki implements ScribCfgResolver {
    private final Ordered_hash mods = Ordered_hash_.New_bry();
    private final Ordered_hash pages = Ordered_hash_.New_bry();
    public ScribCfgResolverWiki(String wiki) {
        this.key = wiki;
    }
    @Override public String Key() {return key;} private final String key;
    @Override public ScribCfg Fallback() {return fallback;} @Override public void FallbackSet(ScribCfg v) {fallback = v;} private ScribCfg fallback;
    @Override public ScribCfg Resolve(byte[] page, byte[] mod, byte[] func) {
        ScribCfgResolverModule modResolver = (ScribCfgResolverModule)mods.Get_by(mod);
        if (modResolver == null) {
            ScribCfgResolverMods pageResolver = (ScribCfgResolverMods)pages.Get_by(mod);
            return pageResolver == null
                ? fallback
                : pageResolver.Resolve(page, mod, func);
        }
        else {
            return modResolver.Resolve(page, mod, func);
        }
    }
    @Override public void Load(Json_nde owner, ScribCfg ownerFallback) {
        ScribCfgResolverUtil.LoadNode(owner, ownerFallback, "scribunto.modules", mods, this, ScribCfgResolverModule.PROTOTYPE);
        ScribCfgResolverUtil.LoadNode(owner, ownerFallback, "pages", pages, this, ScribCfgResolverMods.PROTOTYPE);
    }
    @Override public ScribCfgResolver CloneNew(String key) {return new ScribCfgResolverWiki(key);}
    public static final ScribCfgResolver PROTOTYPE = new ScribCfgResolverWiki(null);
}
class ScribCfgResolverModule implements ScribCfgResolver {
    private final Ordered_hash funcs = Ordered_hash_.New_bry();
    private final Ordered_hash pages = Ordered_hash_.New_bry();
    public ScribCfgResolverModule(String mod) {
        this.key = mod;
    }
    @Override public String Key() {return key;} private final String key;
    @Override public ScribCfg Fallback() {return fallback;} @Override public void FallbackSet(ScribCfg v) {fallback = v;} private ScribCfg fallback;
    @Override public ScribCfg Resolve(byte[] page, byte[] mod, byte[] func) {
        ScribCfgResolverPages funcResolver = (ScribCfgResolverPages)funcs.Get_by(func);
        if (funcResolver == null) {
            ScribCfgResolverFuncs pageResolver = (ScribCfgResolverFuncs)pages.Get_by(page);
            return pageResolver == null
                ? fallback
                : pageResolver.Resolve(page, mod, func);
        }
        else {
            return funcResolver.Resolve(page, mod, func);
        }
    }

    @Override public void Load(Json_nde owner, ScribCfg ownerFallback) {
        ScribCfgResolverUtil.LoadNode(owner, ownerFallback, "funcs", funcs, this, ScribCfgResolverPages.PROTOTYPE);
        ScribCfgResolverUtil.LoadNode(owner, ownerFallback, "pages", pages, this, ScribCfgResolverFuncs.PROTOTYPE);
    }

    @Override public ScribCfgResolver CloneNew(String key) {return new ScribCfgResolverModule(key);}
    public static final ScribCfgResolver PROTOTYPE = new ScribCfgResolverModule(null);
}
class ScribCfgResolverPages implements ScribCfgResolver {
    private final Ordered_hash pages = Ordered_hash_.New_bry();
    public ScribCfgResolverPages(String func) {
        this.key = func;
    }
    @Override public String Key() {return key;} private final String key;
    @Override public ScribCfg Fallback() {return fallback;} @Override public void FallbackSet(ScribCfg v) {fallback = v;} private ScribCfg fallback;
    @Override public ScribCfg Resolve(byte[] page, byte[] mod, byte[] func) {
        ScribCfg rv = (ScribCfg)pages.Get_by(page);
        return rv == null
            ? fallback
            : rv;
    }

    @Override public void Load(Json_nde owner, ScribCfg ownerFallback) {
        ScribCfgResolverUtil.LoadLeaf(owner, ownerFallback, "pages", pages, this);
    }

    @Override public ScribCfgResolver CloneNew(String key) {return new ScribCfgResolverPages(key);}
    public static final ScribCfgResolver PROTOTYPE = new ScribCfgResolverPages(null);
}
class ScribCfgResolverMods implements ScribCfgResolver {
    private final Ordered_hash mods = Ordered_hash_.New_bry();
    public ScribCfgResolverMods(String page) {
        this.key = page;
    }
    @Override public String Key() {return key;} private final String key;
    @Override public ScribCfg Fallback() {return fallback;} @Override public void FallbackSet(ScribCfg v) {fallback = v;} private ScribCfg fallback;
    @Override public ScribCfg Resolve(byte[] page, byte[] mod, byte[] func) {
        ScribCfgResolverFuncs rv = (ScribCfgResolverFuncs)mods.Get_by(mod);
        return rv == null
            ? fallback
            : rv.Resolve(page, mod, func);
    }
    @Override public void Load(Json_nde owner, ScribCfg ownerFallback) {
        ScribCfgResolverUtil.LoadNode(owner, ownerFallback, "scribunto.modules", mods, this, ScribCfgResolverFuncs.PROTOTYPE);
    }
    @Override public ScribCfgResolver CloneNew(String key) {return new ScribCfgResolverMods(key);}
    public static final ScribCfgResolver PROTOTYPE = new ScribCfgResolverMods(null);
}
class ScribCfgResolverFuncs implements ScribCfgResolver {
    private final Ordered_hash funcs = Ordered_hash_.New_bry();
    public ScribCfgResolverFuncs(String key) {
        this.key = key;
    }
    @Override public String Key() {return key;} private final String key;
    @Override public ScribCfg Fallback() {return fallback;} @Override public void FallbackSet(ScribCfg v) {fallback = v;} private ScribCfg fallback;
    @Override public ScribCfg Resolve(byte[] page, byte[] mod, byte[] func) {
        ScribCfg rv = (ScribCfg)funcs.Get_by(func);
        return rv == null
            ? fallback
            : rv;
    }
    @Override public void Load(Json_nde owner, ScribCfg ownerFallback) {
        ScribCfgResolverUtil.LoadLeaf(owner, ownerFallback, "funcs", funcs, this);
    }

    @Override public ScribCfgResolver CloneNew(String key) {return new ScribCfgResolverFuncs(key);}
    public static final ScribCfgResolver PROTOTYPE = new ScribCfgResolverFuncs(null);
}
