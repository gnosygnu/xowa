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

import gplx.Ordered_hash;
import gplx.String_;
import gplx.langs.jsons.Json_ary;
import gplx.langs.jsons.Json_itm_str;
import gplx.langs.jsons.Json_nde;

public interface ScribCfgResolver {
    String Key();
    ScribCfg Fallback(); void FallbackSet(ScribCfg v);
    ScribCfgResolver CloneNew(String key);
    void Load(Json_nde owner, ScribCfg ownerFallback);
    ScribCfg Resolve(byte[] page, byte[] mod, byte[] func);
}
class ScribCfgResolverUtil {
    public static void LoadNode(Json_nde ownerNde, ScribCfg ownerFallback, String subsKey, Ordered_hash hash, ScribCfgResolver self, ScribCfgResolver proto) {
        self.FallbackSet(ownerFallback);
        Json_ary subsNde = ownerNde.Get_as_ary_or_null(subsKey);
        if (subsNde == null) return;
        for (int subIdx = 0; subIdx < subsNde.Len(); subIdx++) {
            Json_nde subNde = subsNde.Get_at_as_nde(subIdx);
            ScribCfg subArgs = ScribCfgResolverUtil.ParseCfgOr(subNde, ownerFallback);
            Json_ary subKeys = subNde.Get_as_ary("keys");
            for (int keyIdx = 0; keyIdx < subKeys.Len(); keyIdx++) {
                byte[] subKey = subKeys.Get_at(keyIdx).Data_bry();
                ScribCfgResolver subScope = (ScribCfgResolver)hash.Get_by(subKey);
                if (subScope == null) {
                    subScope = proto.CloneNew(String_.new_u8(subKey));
                    hash.Add(subKey, subScope);
                }
                subScope.FallbackSet(subArgs);
                subScope.Load(subNde, subArgs);
            }
        }
    }
    public static void LoadLeaf(Json_nde ownerNde, ScribCfg ownerFallback, String subsKey, Ordered_hash hash, ScribCfgResolver self) {
        self.FallbackSet(ownerFallback);
        Json_ary subsNde = ownerNde.Get_as_ary_or_null(subsKey);
        if (subsNde == null) return;
        for (int idx = 0; idx < subsNde.Len(); idx++) {
            Json_nde subNde = subsNde.Get_at_as_nde(idx);
            Json_ary subKeys = subNde.Get_as_ary("keys");
            for (int keyIdx = 0; keyIdx < subKeys.Len(); keyIdx++) {
                byte[] subKey = ((Json_itm_str)subKeys.Get_at(keyIdx)).Data_bry();
                ScribCfg subArgs = ScribCfgResolverUtil.ParseCfgOr(subNde, ownerFallback);
                hash.Add(subKey, subArgs);
            }
        }
    }
    public static ScribCfg ParseCfgOr(Json_nde owner, ScribCfg or) {
        Json_nde nde = (Json_nde)owner.Get_as_itm_or_null("scribunto");
        if (nde == null) return or;

        int timeout = nde.Get_as_int_or("timeout", 0);
        int sleep = nde.Get_as_int_or("sleep", 0);
        String regexEngine = nde.Get_as_str_or("regexEngine", "luaj");

        return new ScribCfg(timeout, sleep, regexEngine);
    }
}
