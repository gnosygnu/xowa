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
class GfmlVarItm implements GfmlScopeItm {
	public String Key() {return key;} private String key;
	public GfmlDocPos DocPos() {return docPos;} public GfmlVarItm DocPos_(GfmlDocPos v) {docPos = v; return this;} GfmlDocPos docPos = GfmlDocPos_.Null;		
	public GfmlTkn Tkn() {return tkn;} public void Tkn_set(GfmlTkn v) {tkn = v;} GfmlTkn tkn;
	public String TknVal() {return tkn.Val();} 
	public String CtxKey() {return ctxKey;} private String ctxKey;
	@gplx.Internal protected void Scope_bgn(GfmlVarCtx ctx) {ctx.Add_if_dupe_use_nth(this);}
	@gplx.Internal protected void Scope_end(GfmlVarCtx ctx) {ctx.Del(key);}
	public static GfmlVarItm new_(String key, GfmlTkn tkn, String ctxKey) {
		GfmlVarItm rv = new GfmlVarItm();
		rv.key = key; rv.tkn = tkn; rv.ctxKey = ctxKey;
		return rv;
	}	GfmlVarItm() {}
}
