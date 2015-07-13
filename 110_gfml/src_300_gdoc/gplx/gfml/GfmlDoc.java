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
public class GfmlDoc {
	public GfmlNde				RootNde() {return rootNde;} GfmlNde rootNde;
	@gplx.Internal protected List_adp			UsrMsgs() {return usrMsgs;} List_adp usrMsgs = List_adp_.new_();
	@gplx.Internal protected GfmlLxrRegy		LxrRegy() {return lxrRegy;} GfmlLxrRegy lxrRegy = new GfmlLxrRegy();
	@gplx.Internal protected GfmlBldrCmdRegy	CmdRegy() {return cmdRegy;} GfmlBldrCmdRegy cmdRegy = GfmlBldrCmdRegy.new_();
	@gplx.Internal protected GfmlPragmaMgr		PragmaMgr() {return pragmaMgr;} GfmlPragmaMgr pragmaMgr = GfmlPragmaMgr.new_();
	@gplx.Internal protected GfmlLxr			RootLxr() {return rootLxr;} GfmlLxr rootLxr;
	@gplx.Internal protected void RootLxr_set(GfmlLxr v) {rootLxr = v;}
	@gplx.Internal protected void Clear() {
		usrMsgs.Clear();
		rootNde = GfmlNde.named_(GfmlTkn_.cmd_("tkn.gfml.root_tkn", GfmlBldrCmd_pendingTkns_add._), GfmlType_.Null);
		rootNde.DocPos_(GfmlDocPos_.Root);
	}
	@gplx.Internal protected static GfmlDoc new_() {
		GfmlDoc rv = new GfmlDoc();
		rv.Clear();
		return rv;
	}	GfmlDoc() {}
}
//	class GfmlDocEditor {
//		public GfmlTkn BgnParen() {return bgnParen;} public GfmlDocEditor BgnParen_(GfmlTkn v) {bgnParen = v; return this;} GfmlTkn bgnParen = GfmlTkn_.new_("(", "");
//		public GfmlTkn EndParen() {return endParen;} public GfmlDocEditor EndParen_(GfmlTkn v) {endParen = v; return this;} GfmlTkn endParen = GfmlTkn_.new_(")", "");
//		public GfmlTkn BgnBrace() {return bgnBrace;} public GfmlDocEditor BgnBrace_(GfmlTkn v) {bgnBrace = v; return this;} GfmlTkn bgnBrace = GfmlTkn_.new_("{", "");
//		public GfmlTkn EndBrace() {return endBrace;} public GfmlDocEditor EndBrace_(GfmlTkn v) {endBrace = v; return this;} GfmlTkn endBrace = GfmlTkn_.new_("}", "");
//		public GfmlTkn Hnd() {return hnd;} public GfmlDocEditor Hnd_(GfmlTkn v) {hnd = v; return this;} GfmlTkn hnd = GfmlTkn_.new_(":", "");
//        public static final GfmlDocEditor _ = new GfmlDocEditor(); GfmlDocEditor() {}
//	}
