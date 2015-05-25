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
