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
class GfmlPragmaVar implements GfmlPragma {
	public String KeyOfPragma() {return pragmaKey;} public void PragmaKey_set(String v) {pragmaKey = v;} private String pragmaKey = "_var";
	public void Exec(GfmlBldr bldr, GfmlNde pragmaNde) {
		List_adp list = Compile(pragmaNde);
		ExecList(bldr.Vars(), list);
		bldr.Doc().PragmaMgr().EndCmds_add(bldr.CurNdeFrame().CurDocPos().NewUp(), GfmlPragmaVar_scopeEndCmd.new_(list));
	}
	@gplx.Internal protected List_adp Compile(GfmlNde pragmaNde) {
		List_adp list = List_adp_.New();
		for (int i = 0; i < pragmaNde.SubHnds().Count(); i++) {
			GfmlNde subNde = (GfmlNde)pragmaNde.SubHnds().Get_at(i);
			GfmlVarItm itm = CompileItmNde(subNde);
			list.Add(itm);
		}
		return list;
	}
	@gplx.Internal protected GfmlVarItm CompileItmNde(GfmlNde subNde) {
		String key = subNde.SubKeys().FetchDataOrFail("key");
		GfmlAtr valAtr = (GfmlAtr)subNde.SubKeys().Get_by("val");
		String ctx = subNde.SubKeys().FetchDataOrNull("ctx"); if (ctx == null) ctx = GfmlVarCtx_.DefaultKey;
		return GfmlVarItm.new_(key, valAtr.DatTkn(), ctx);
	}
	public GfmlType[] MakePragmaTypes(GfmlTypeMakr makr) {
		makr.MakeSubTypeAsOwner	(pragmaKey);
		makr.MakeSubType		(	"text", "key", "val", "ctx");
		return makr.Xto_bry();
	}
	void ExecList(Hash_adp cache, List_adp list) {
		GfmlVarCtx ctx = null;
		for (Object varObj : list) {
			GfmlVarItm var = (GfmlVarItm)varObj;
			ctx = FetchIfNew(ctx, var, cache);
			var.Scope_bgn(ctx);
		}
	}
	public static GfmlPragmaVar new_() {return new GfmlPragmaVar();} GfmlPragmaVar() {}
	public static GfmlVarCtx FetchIfNew(GfmlVarCtx ctx, GfmlVarItm var, Hash_adp cache) {// reused in two procs
		if (ctx == null || !String_.Eq(ctx.Key(), var.CtxKey()))
			ctx = GfmlVarCtx_.FetchFromCacheOrNew(cache, var.CtxKey());
		return ctx;
	}
}
class GfmlPragmaVar_scopeEndCmd implements GfmlBldrCmd {
	public String Key() {return "cmd:gfml.var.expire";}
	public void Exec(GfmlBldr bldr, GfmlTkn tkn) {
		Hash_adp cache = bldr.Vars();
		GfmlVarCtx ctx = null;
		for (Object varObj : list) {
			GfmlVarItm var = (GfmlVarItm)varObj;
			ctx = GfmlPragmaVar.FetchIfNew(ctx, var, cache);
			var.Scope_end(ctx);
		}
	}
	List_adp list;
	public static GfmlPragmaVar_scopeEndCmd new_(List_adp list) {
		GfmlPragmaVar_scopeEndCmd rv = new GfmlPragmaVar_scopeEndCmd();
		rv.list = list;
		return rv;
	}	GfmlPragmaVar_scopeEndCmd() {}
}
