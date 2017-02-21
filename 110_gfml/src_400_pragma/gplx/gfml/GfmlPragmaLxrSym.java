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
package gplx.gfml; import gplx.*;
class GfmlPragmaLxrSym implements GfmlPragma {
	public String KeyOfPragma() {return "_lxr_sym";}
	public void Exec(GfmlBldr bldr, GfmlNde pragmaNde) {
		Compile(bldr, pragmaNde);
	}
	@gplx.Internal protected static GfmlLxr Compile(GfmlBldr bldr, GfmlNde ownerNde) {
		String key = ownerNde.SubKeys().FetchDataOrFail("key");
		String raw = ownerNde.SubKeys().FetchDataOrFail("raw");
		String val = ownerNde.SubKeys().FetchDataOr("val", raw);
		GfmlTkn cmdTkn = ownerNde.SubKeys().FetchDataTknOrNull("cmd");			
		GfmlBldrCmd cmd = bldr.Doc().CmdRegy().GetOrFail(cmdTkn.Val());

		GfmlLxr lxr = bldr.Doc().LxrRegy().Get_by(key);
		if (lxr == null) {
			lxr = GfmlLxr_.symbol_(key, raw, val, cmd);
			bldr.Doc().LxrRegy().Add(lxr);
			bldr.Doc().RootLxr().SubLxr_Add(lxr);		// FIXME: always add to cur lxr; should be outside if block; also, auto_add=n to skip adding to rootLxr
		}
		else {
			GfmlTkn curTkn = lxr.CmdTkn();
			if (raw == null) raw = curTkn.Raw();
			if (val == null) val = curTkn.Val();
			if (String_.Eq(cmdTkn.Raw(), GfmlTkn_.NullRaw)) cmd = bldr.Doc().CmdRegy().GetOrFail(curTkn.Cmd_of_Tkn().Key());

			GfmlTkn tkn = GfmlTkn_.singleton_(key, raw, val, cmd);
			lxr.CmdTkn_set(tkn);
		}
		return lxr;
	}
	public GfmlType[] MakePragmaTypes(GfmlTypeMakr makr) {
		makr.MakeRootType("_lxr_sym", "_lxr_sym", "key", "raw");
		GfmlFld fld = GfmlFld.new_(true, "cmd", GfmlType_.StringKey).DefaultTkn_(GfmlTkn_.val_("gfml.elm_data"));
		makr.AddSubFld(fld);
		return makr.Xto_bry();
	}
	public static GfmlPragmaLxrSym new_() {return new GfmlPragmaLxrSym();} GfmlPragmaLxrSym() {}
	public static final String CacheLog_key = "log:var";
}
