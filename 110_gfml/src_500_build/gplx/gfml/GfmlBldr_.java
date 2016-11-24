/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.gfml; import gplx.*;
class GfmlBldr_ {
	@gplx.Internal protected static GfmlBldr new_() {return new GfmlBldr();}
	@gplx.Internal protected static GfmlBldr default_() {			
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
