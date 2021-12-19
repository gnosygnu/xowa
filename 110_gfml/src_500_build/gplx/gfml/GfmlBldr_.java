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
class GfmlBldr_ {
	public static GfmlBldr new_() {return new GfmlBldr();}
	public static GfmlBldr default_() {
		GfmlBldr rv = new GfmlBldr();
		GfmlLxr rootLxr = GfmlDocLxrs.Root_lxr();
		GfmlDocLxrs.Default_lxr(rv.Doc().LxrRegy(), rootLxr);
		rv.Doc().RootLxr_set(rootLxr);
		InitDocBldr(rv
			, GfmlPragmaVar.new_()
			, GfmlPragmaType.new_()
			, GfmlPragmaDefault.new_()
			, GfmlPragmaLxrSym.new_()
			, GfmlPragmaLxrFrm.new_()
			);
		return rv;
	}
	static void InitDocBldr(GfmlBldr bldr, GfmlPragma... ary) {
		GfmlTypeMakr makr = GfmlTypeMakr.new_();
		GfmlTypRegy regy = bldr.TypeMgr().TypeRegy(); GfmlPragmaMgr cmdMgr = bldr.Doc().PragmaMgr();

		for (GfmlPragma pragma : ary) {
			regy.Add_ary(pragma.MakePragmaTypes(makr));
			cmdMgr.Pragmas_add(pragma);
		}
	}
}
