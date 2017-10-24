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
class GfmlParse_fxt {
	public GfmlNde_mok nde_() {return GfmlNde_mok.new_();}
	public GfmlTkn_mok tkn_grp_ary_(String... ary) {return GfmlTkn_mok.new_().Subs_(GfmlTkn_mok.Xto_bry(ary));}
	public GfmlTkn_mok tkn_grp_(GfmlTkn_mok... ary) {return GfmlTkn_mok.new_().Subs_(ary);}
	public GfmlTkn_mok tkn_itm_(String r) {return GfmlTkn_mok.new_().Raw_(r);}
	public void ini_RootLxr_Add(GfmlLxr... ary) {rootLxr.SubLxr_Add(ary);}
	public void tst_Doc(String raw, GfmlNde_mok... expdAry) {
		GfmlDoc gdoc = bldr.XtoGfmlDoc(raw);
		GfmlNde_mok expd = GfmlNde_mok.new_();
		for (GfmlNde_mok itm : expdAry)
			expd.Subs_(itm);
		TfdsTstr_fxt tstr = TfdsTstr_fxt.new_();
		GfmlTypeResolver_fxt.tst_Nde(tstr, expd, GfmlNde_mok.gfmlNde_(gdoc.RootNde()));
		tstr.tst_Equal("parse");
	}
	public void tst_Tkn(String raw, GfmlTkn_mok... expdAry) {
		GfmlDoc gdoc = bldr.XtoGfmlDoc(raw);
		GfmlTkn_mok expd = GfmlTkn_mok.new_();
		for (GfmlTkn_mok itm : expdAry)
			expd.Subs_(itm);
		TfdsTstr_fxt tstr = TfdsTstr_fxt.new_();
		GfmlTkn_mok.tst(tstr, expd, GfmlTkn_mok.gfmlNde_(gdoc.RootNde()));
		tstr.tst_Equal("parse");
	}
        public static GfmlParse_fxt new_() {
		GfmlParse_fxt rv = new GfmlParse_fxt();
		rv.rootLxr = GfmlDocLxrs.Root_lxr();
		rv.bldr = GfmlBldr_.new_();
		rv.bldr.Doc().RootLxr_set(rv.rootLxr);
		return rv;
	}
	public void tst_Err(String raw, UsrMsg_mok... expdErrs) {
		bldr.ThrowErrors_set(false);
		GfmlDoc actlDoc = bldr.XtoGfmlDoc(raw);
		List_adp expd = List_adp_.New(), actl = actlDoc.UsrMsgs();
		expd.Add_many((Object[])expdErrs);
		TfdsTstr_fxt tstr = TfdsTstr_fxt.new_();
		int max = tstr.List_Max(expd, actl);
		for (int i = 0; i < max; i++) {
			UsrMsg_mok expdUm = (UsrMsg_mok)tstr.List_FetchAtOrNull(expd, i);
			UsrMsg actlUm = (UsrMsg)tstr.List_FetchAtOrNull(actl, i);
			UsrMsg_mok actlUmm = UsrMsg_mok.new_(actlUm);
			tstr.Eq_str(expdUm.Main(), actlUmm.Main(), "main");
			for (int j = 0; j < expdUm.Args().Count(); j++) {
				Keyval expdKv = (Keyval)expdUm.Args().Get_at(j);
				Keyval actlKv = (Keyval)actlUmm.Args().Get_by(expdKv.Key());
				Object actlVal = actlKv == null ? String_.Null_mark : actlKv.Val();
				tstr.Eq_str(expdKv.Val(), actlVal, expdKv.Key());
			}
			for (int j = 0; j < expdUm.Required().Count(); j++) {
				String expdKv = (String)expdUm.Required().Get_at(j);
				Keyval actlKv = (Keyval)actlUmm.Args().Get_by(expdKv);
				Object actlVal = actlKv == null ? String_.Null_mark : actlKv.Val();
				Object actlValV = actlKv == null ? "<<REQD>>" : actlKv.Val();
				tstr.Eq_str(actlValV, actlVal, expdKv);
			}
		}
		tstr.tst_Equal("errs");
	}		
	GfmlBldr bldr; GfmlLxr rootLxr;
}
