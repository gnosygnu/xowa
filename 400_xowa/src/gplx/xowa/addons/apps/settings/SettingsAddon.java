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
package gplx.xowa.addons.apps.settings;

import gplx.GfoMsg;
import gplx.Gfo_invk;
import gplx.GfsCtx;
import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_parser;
import gplx.objects.errs.ErrUtl;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xow_wiki;
import gplx.xowa.addons.Xoax_addon_itm;
import gplx.xowa.addons.Xoax_addon_itm__init;
import gplx.xowa.xtns.scribunto.cfgs.ScribCfgResolverApp;

public class SettingsAddon implements Xoax_addon_itm, Xoax_addon_itm__init, Gfo_invk {
    private final Ordered_hash hash = Ordered_hash_.New();
    private Json_doc jdoc;
    private boolean init = true;
	public String Addon__key() {return ADDON_KEY;} public static final String ADDON_KEY = "xowa.app.settings";

    @Override
    public void Init_addon_by_app(Xoa_app app) {
        // NOTE: should move to addon_mgr and create a new interface for settings
        this.Add(new ScribCfgResolverApp(app));
    }
    private void Add(CfgResolver mgr) {
        hash.Add(mgr.Key(), mgr);
    }

    @Override
    public void Init_addon_by_wiki(Xow_wiki wiki) {
        if (init) { // NOTE: cannot `init` in `Init_addon_by_app` b/c `Bind_many` will try to load from db, and dbEngine is not set yet
            Update(wiki.App().Cfg().Get_str_app_or(CFG_PUBLISH, null));
        }
        else {
            if (jdoc == null) return;
            for (int i = 0; i < hash.Len(); i++) {
                CfgResolver resolver = (CfgResolver)hash.Get_at(i);
                resolver.WhenWikiCreated(wiki);
            }
        }
    }
    private void Update(String json) {
        init = false;
        if (json == null) return;
        this.jdoc = Json_parser.ParseToJdoc(json);
        if (jdoc == null) return;
        for (int i = 0; i < hash.Len(); i++) {
            CfgResolver resolver = (CfgResolver)hash.Get_at(i);
            resolver.WhenCfgChanged(jdoc);
        }
    }

    @Override
    public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
        switch (k) {
            case CFG_PUBLISH:
                Update(m.ReadStrOr("v", null));
                break;
            default:
                throw ErrUtl.NewUnhandledDefault(k);
        }
        return null;
    }
	private static final String CFG_PUBLISH = "xowa.app.settings.publish";
}
