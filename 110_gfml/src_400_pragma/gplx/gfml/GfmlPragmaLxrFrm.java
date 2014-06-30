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
class GfmlPragmaLxrFrm implements GfmlPragma {
	public String KeyOfPragma() {return "_lxr_frame";}
	public void Exec(GfmlBldr bldr, GfmlNde pragmaNde) {
		Compile(bldr, pragmaNde);
	}
	@gplx.Internal protected static GfmlLxr Compile(GfmlBldr bldr, GfmlNde ownerNde) {
		String key = ownerNde.SubKeys().FetchDataOrFail("key");
		String type = ownerNde.SubKeys().FetchDataOrNull("type");
		String bgn = ownerNde.SubKeys().FetchDataOrFail("bgn");
		String end = ownerNde.SubKeys().FetchDataOrFail("end");
		GfmlFrame frame = String_.Eq(type, "comment") ?  GfmlFrame_.comment_() : GfmlFrame_.quote_();

		GfmlLxr lxr = bldr.Doc().LxrRegy().Fetch(key);
		if (lxr == null) {
			lxr = GfmlLxr_.frame_(key, frame, bgn, end);
			bldr.Doc().LxrRegy().Add(lxr);
			bldr.Doc().RootLxr().SubLxr_Add(lxr);		// FIXME: always add to cur lxr; should be outside if block; also, auto_add=n to skip adding to rootLxr
		}
		else {
			GfmlLxr_frame frameLxr = GfmlLxr_frame.as_(lxr); if (frameLxr == null) throw Err_.new_("lxr is not GfmlLxr_frame").Add("key", key).Add("type", ClassAdp_.NameOf_obj(lxr));
			if (type != null) {
//					frame = frameLxr.Frame.MakeNew(frameLxr);
			}
			if (bgn != null) {
				frameLxr.BgnRaw_set(bgn);
			}
			if (end != null) {
				frameLxr.EndRaw_set(end);
				//					end = frameLxr.EndLxr.CmdTkn.Raw;
			}
		}
		for (int i = 0; i < ownerNde.SubHnds().Count(); i++) {
			GfmlNde subNde = (GfmlNde)ownerNde.SubHnds().FetchAt(i);
			GfmlLxr subLxr = null;
			if (String_.Eq(subNde.Hnd(), "sym"))
				subLxr = GfmlPragmaLxrSym.Compile(bldr, subNde);
			lxr.SubLxr_Add(subLxr);
		}
		return lxr;
	}
	public GfmlType[] MakePragmaTypes(GfmlTypeMakr makr) {
		makr.MakeRootType("_lxr_frame", "_lxr_frame", "key", "type", "bgn", "end");
		return makr.XtoAry();
	}
	public static GfmlPragmaLxrFrm new_() {return new GfmlPragmaLxrFrm();} GfmlPragmaLxrFrm() {}
}
