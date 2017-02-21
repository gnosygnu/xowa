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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*;
public class GfuiWinFocusMgr {
	public List_adp SubElems() {return subElems;} List_adp subElems = List_adp_.New();
	public void InitForm() {this.Init(win);}
	public void Init(GfuiWin win) {
		subElems.Clear();
		InitRecursive(win, 0);
	}
	int InitRecursive(GfuiElem owner, int focusIdx) {
		for (int i = 0; i < owner.SubElems().Count(); i++) {
			GfuiElem sub = (GfuiElem)owner.SubElems().Get_at(i);
			if (sub.Focus_able()) {
				sub.Focus_idx_(focusIdx++);
				subElems.Add(sub);
			}
			focusIdx = InitRecursive(sub, focusIdx);
		}
		return focusIdx;
	}
	public GfuiElem Focus(boolean fwd, int cur) {
		int nxt = fwd 
			? cur == subElems.Idx_last() ? 0 : ++cur
			: cur == 0 ? subElems.Idx_last() : --cur;
		GfuiElem elm = (GfuiElem)subElems.Get_at(nxt);
		elm.Focus();
		return elm;
	}
	GfuiWin win;
	public static GfuiWinFocusMgr new_(GfuiWin win) {
		GfuiWinFocusMgr rv = new GfuiWinFocusMgr();
		rv.win = win;
		return rv;
	}	GfuiWinFocusMgr() {}
}
class FocusTraversalPolicy_cls_base extends FocusTraversalPolicy {
	List_adp elems; GfuiWinFocusMgr formFocusMgr;
	public FocusTraversalPolicy_cls_base(GfuiWinFocusMgr formFocusMgr) {
		this.elems = formFocusMgr.subElems;
		this.formFocusMgr = formFocusMgr;
	}
	boolean elems_empty() {return elems.Count() == 0;}
	public Component getComponentAfter(Container focusCycleRoot, Component c) {
		formFocusMgr.InitForm();
		if (elems_empty()) return c;
		GxwElem gxw = GxwElemOf(c);
		int orig = gxw.Core().Focus_index();
		int idx = orig;
		while (true) {
			idx++;
			if (idx == elems.Count())
				idx = 0;
			GfuiElem elem = null;
			try {elem = (GfuiElem)elems.Get_at(idx);}
			catch (Exception e) {
				System.out.println(idx);
				Err_.Noop(e);
			}
			if (elem == null) return c; // FIXME: why is elem null?; REP: add new tab through history and then close out
			if (elem.Focus_able() && elem.Visible()) {	
				if (elem.UnderElem() instanceof GxwTextMemo_lang) {
					GxwTextMemo_lang xx = (GxwTextMemo_lang)elem.UnderElem();
					return xx.Inner();
				}
				else
					return (Component)elem.UnderElem();
			}
			if (idx == orig)
				return c;
		}
	}
	public Component getComponentBefore(Container focusCycleRoot, Component c) {
		formFocusMgr.InitForm();
		if (elems_empty()) return c;
		GxwElem gxw = GxwElemOf(c);
		int orig = gxw.Core().Focus_index();
		int idx = orig;
		while (true) {
			idx--;
			if (idx == -1)
				idx = elems.Count() - List_adp_.Base1;
			GfuiElem elem = null;
			try {
				elem = (GfuiElem)elems.Get_at(idx);
//				System.out.println(elem.Key_of_GfuiElem() + " " + elem.Focus_able() + " " + elem.Visible());
				if (elem.getClass().getName().equals("gplx.gfds.gbu.GbuGrid") && elem.Key_of_GfuiElem().equals("grid0")) {
					System.out.println(elem.Key_of_GfuiElem() + " " + elem.Focus_able() + " " + elem.Visible());
					System.out.println(elem);
				}
			}
			catch (Exception e) {
				System.out.println(idx);
				Err_.Noop(e);				
			}
			if (elem == null) return c; // FIXME: why is elem null?; REP: add new tab through history and then close out
			if (elem.Focus_able() && elem.Visible()) {
				if (elem.UnderElem() instanceof GxwTextMemo_lang) {
					GxwTextMemo_lang xx = (GxwTextMemo_lang)elem.UnderElem();
					return xx.Inner();
				}
				else
					return (Component)elem.UnderElem();
			}
			if (idx == orig)
				return c;
		}
	}
	public Component getDefaultComponent(Container focusCycleRoot) 	{return getFirstComponent(focusCycleRoot);}
	public Component getFirstComponent(Container focusCycleRoot) 	{return elems_empty() ? focusCycleRoot : (Component)FetchAt(0).UnderElem();}
	public Component getLastComponent(Container focusCycleRoot) 	{return elems_empty() ? focusCycleRoot : (Component)FetchAt(elems.Count() - 1).UnderElem();} 
	GfuiElem FetchAt(int idx) {return (GfuiElem)elems.Get_at(idx);}
	GxwElem GxwElemOf(Component c) {		
		if (GxwElem.class.isAssignableFrom(c.getClass())) return (GxwElem)c;		
		return (GxwElem)c.getParent(); // HACK: occurs for JComboBox when editable is true; focus is on MetalComboBox, with parent of JComboBox
	}
}
//#}