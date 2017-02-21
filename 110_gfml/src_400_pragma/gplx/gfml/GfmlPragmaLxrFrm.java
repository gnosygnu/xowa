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

		GfmlLxr lxr = bldr.Doc().LxrRegy().Get_by(key);
		if (lxr == null) {
			lxr = GfmlLxr_.frame_(key, frame, bgn, end);
			bldr.Doc().LxrRegy().Add(lxr);
			bldr.Doc().RootLxr().SubLxr_Add(lxr);		// FIXME: always add to cur lxr; should be outside if block; also, auto_add=n to skip adding to rootLxr
		}
		else {
			GfmlLxr_frame frameLxr = GfmlLxr_frame.as_(lxr); if (frameLxr == null) throw Err_.new_wo_type("lxr is not GfmlLxr_frame", "key", key, "type", Type_adp_.NameOf_obj(lxr));
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
			GfmlNde subNde = (GfmlNde)ownerNde.SubHnds().Get_at(i);
			GfmlLxr subLxr = null;
			if (String_.Eq(subNde.Hnd(), "sym"))
				subLxr = GfmlPragmaLxrSym.Compile(bldr, subNde);
			lxr.SubLxr_Add(subLxr);
		}
		return lxr;
	}
	public GfmlType[] MakePragmaTypes(GfmlTypeMakr makr) {
		makr.MakeRootType("_lxr_frame", "_lxr_frame", "key", "type", "bgn", "end");
		return makr.Xto_bry();
	}
	public static GfmlPragmaLxrFrm new_() {return new GfmlPragmaLxrFrm();} GfmlPragmaLxrFrm() {}
}
