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
import gplx.lists.*; /*StackAdp*/
public class GfmlDataWtr extends DataWtr_base implements DataWtr {
	public void InitWtr(String key, Object val) {
		if (!String_.Eq(key, GfmlDataWtrOpts.Key_const)) return;
		GfmlDataWtrOpts layout = GfmlDataWtrOpts.cast(val);
		keyedSpr = layout.KeyedSpr();
		indentNodes = layout.IndentNodes();
		ignoreNullNames = layout.IgnoreNullNames();
	}
	@Override public void WriteData(String name, Object val) {
		if (nde.SubKeys().Count() != 0) AddTkn_nullVal(keyedSpr);	// add keyedSprTkn if not first
		GfmlTkn keyTkn = AddTkn_raw(name);
		AddTkn_raw("=");
		GfmlTkn valTkn = AddTkn_raw(To_str(val));
		GfmlAtr atr = GfmlAtr.new_(keyTkn, valTkn, GfmlType_.String);
		nde.SubObjs_Add(atr);
	}
	public void WriteLeafBgn(String leafName) {this.WriteNodeBgn(leafName);}
	@Override public void WriteNodeBgn(String nodeName) {
		stack.Push(nde);
		if (stack.Count() != 1					// not root
			&& nde.SubHnds().Count() == 0) {	// first subNde
			AddTkn_nullVal("{");
			if (indentNodes) AddTkn_nullVal(String_.CrLf);
		}
		if (indentNodes) AddTkn_nullVal(String_.Repeat("\t", stack.Count() - 1));
		GfmlTkn nameTkn = null;
		if (ignoreNullNames && String_.Eq(nodeName, ""))
			nameTkn = GfmlTkn_.Null;
		else {
			nameTkn = AddTkn_raw(String_.Eq(nodeName, "") ? "%" : nodeName);
			AddTkn_nullVal(":");
		}
		nde = GfmlNde.named_(nameTkn, GfmlType_.new_any_());
	}
	public void WriteLeafEnd() {this.WriteNodeEnd();}
	@Override public void WriteNodeEnd() {
		if (nde.SubHnds().Count() == 0)
			AddTkn_nullVal(";");
		else {
			if (indentNodes) AddTkn_nullVal(String_.Repeat("\t", stack.Count() - 1));
			AddTkn_nullVal("}");
		}
		if (indentNodes) AddTkn_nullVal(String_.CrLf);
		GfmlNde finishedNde = nde;
		nde = GfmlNde.as_(stack.Pop());
		nde.SubObjs_Add(finishedNde);
	}
	public void WriteComment(String comment) {
		AddTkn_nullVal("/*");
		AddTkn_raw(comment);
		AddTkn_nullVal("*/");
	}
	public void Clear() {nde.SubObjs_Clear();}
	public void WriteTableBgn(String name, GfoFldList fields) {}
	public void CloseBranchHdr(boolean isInline) {}
	public String To_str() {
		while (stack.Count() > 0) {	// auto-close all nodes
			WriteNodeEnd();
		}
		return GfmlDocWtr_.xtoStr_(gdoc.RootNde());
	}
	String To_str(Object obj) {
		if (obj == null) return "''";
		String s = Object_.Xto_str_strict_or_empty(obj);
		return String_.Concat("'", String_.Replace(s, "'", "''"), "'");
	}
	GfmlTkn AddTkn_raw(String raw) {return AddTkn(raw, raw);}
	GfmlTkn AddTkn_nullVal(String raw) {return AddTkn(raw, GfmlTkn_.NullVal);}
	GfmlTkn AddTkn(String raw, String val) {
		GfmlTkn tkn = GfmlTkn_.new_(raw, val);
		nde.SubObjs_Add(tkn);
		return tkn;
	}
	@Override public SrlMgr SrlMgr_new(Object o) {return new GfmlDataWtr();}
	StackAdp stack = StackAdp_.new_();
	GfmlDoc gdoc = GfmlDoc.new_(); GfmlNde nde;
	String keyedSpr = GfmlDataWtrOpts._.KeyedSpr(); boolean indentNodes, ignoreNullNames;
        public static GfmlDataWtr new_() {return new GfmlDataWtr();}
	GfmlDataWtr() {this.nde = this.gdoc.RootNde();}
}
