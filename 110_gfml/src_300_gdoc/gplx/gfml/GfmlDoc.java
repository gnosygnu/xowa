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
public class GfmlDoc {
	public GfmlNde				RootNde() {return rootNde;} GfmlNde rootNde;
	@gplx.Internal protected List_adp			UsrMsgs() {return usrMsgs;} List_adp usrMsgs = List_adp_.New();
	@gplx.Internal protected GfmlLxrRegy		LxrRegy() {return lxrRegy;} GfmlLxrRegy lxrRegy = new GfmlLxrRegy();
	@gplx.Internal protected GfmlBldrCmdRegy	CmdRegy() {return cmdRegy;} GfmlBldrCmdRegy cmdRegy = GfmlBldrCmdRegy.new_();
	@gplx.Internal protected GfmlPragmaMgr		PragmaMgr() {return pragmaMgr;} GfmlPragmaMgr pragmaMgr = GfmlPragmaMgr.new_();
	@gplx.Internal protected GfmlLxr			RootLxr() {return rootLxr;} GfmlLxr rootLxr;
	@gplx.Internal protected void RootLxr_set(GfmlLxr v) {rootLxr = v;}
	@gplx.Internal protected void Clear() {
		usrMsgs.Clear();
		rootNde = GfmlNde.named_(GfmlTkn_.cmd_("tkn.gfml.root_tkn", GfmlBldrCmd_pendingTkns_add.Instance), GfmlType_.Null);
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
//        public static final    GfmlDocEditor Instance = new GfmlDocEditor(); GfmlDocEditor() {}
//	}
