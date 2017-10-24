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
package gplx.gfui.controls.tabs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import org.junit.*; import gplx.gfui.controls.tabs.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*;
public class TabBox_tst {
//		@Before public void setup() {
//			fx = TabBoxFxt.new_();
//		}	TabBoxFxt fx;
	@Test  public void Add() {
//			fx.Make(1).tst_Selected("0").FetchBtnAt(0).tst_X(0);
//			fx.Make(3).tst_Selected("2").FetchBtnAt(2).tst_X(160);
	}
//		@Test  public void Del_at() {
//			fx.Make(2).Del_at(1).tst_Btns("0");
//			fx.Make(2).Del_at(0).tst_Btns("1");
//			fx.Make(3).Del_at(0).tst_Btns("1", "2");
//			fx.Make(3).Del_at(1).tst_Btns("0", "2");
//			fx.Make(3).Del_at(2).tst_Btns("0", "1");

//			fx.Make(3).Select(1).Del_at(1).tst_Selected("2");	// 1 deleted; 2 shifted down into slot
//			fx.Make(3).Select(1).Del_at(0).tst_Selected("1");	// 0 deleted; 1 still remains active (but will have idx of 0
//			fx.Make(3).Select(2).Del_at(2).tst_Selected("1");	// 2 deleted; 1 selected
//		}
//		@Test  public void Selected_byAdd() {
//			fx.Make(2).Select(0).tst_Selected("0").Select(1).tst_Selected("1");
//		}
//		@Test  public void Selected_byBtn() {
//			fx.Make(2).tst_Selected("1");
//
//			GfuiBtn btn = fx.TabBox().SubBtnArea().Get_at(0);
//			btn.Click();
//			fx.tst_Selected("0");
//		}
//		@Test  public void ReorderTab() {
//			fx.Make(3).Reorder(0, -1).tst_Raised(false);
//			fx.Make(3).Reorder(2, 1).tst_Raised(false);
//			fx.Make(3).Reorder(0, 1).tst_Btns("1", "0", "2").tst_Raised(true).tst_FocusOrder();
//			fx.Make(3).Reorder(0, 2).tst_Btns("1", "2", "0").tst_Raised(true).tst_FocusOrder();
//			fx.Make(3).Reorder(2, -1).tst_Btns("0", "2", "1").tst_Raised(true).tst_FocusOrder();
//			fx.Make(3).Reorder(0, 1).Reorder(1, 2).tst_Btns("0", "2", "1").tst_Raised(true);//.tst_FocusOrder();	// FIXME: broken after FocusOrder set for entire form (instead of per container)
//		}
}
class GfuiElemFxt {
	public GfuiElem UnderElem() {return underElem;} GfuiElem underElem;
	@gplx.Internal protected GfuiElemFxt tst_X(int expd) {Tfds.Eq(expd, underElem.X()); return this;}
	public static GfuiElemFxt new_(GfuiElem elem) {
		GfuiElemFxt rv = new GfuiElemFxt();
		rv.underElem = elem;
		return rv;
	}	GfuiElemFxt() {}
}
class TabBoxFxt implements Gfo_invk {
	@gplx.Internal protected TabBox TabBox() {return tabBox;}
	@gplx.Internal protected TabBoxFxt Make(int count) {
		for (int i = 0; i < tabBox.Tabs_Count(); i++)
			tabBox.Tabs_DelAt(0);
		for (int i = 0; i < count; i++)
			tabBox.Tabs_Add(Int_.To_str(i), Int_.To_str(i));
		return this;
	}
	@gplx.Internal protected TabBoxFxt Del_at(int index) {tabBox.Tabs_DelAt(index); return this;}
//		@gplx.Internal protected TabBoxFxt Select(int index) {tabBox.Tabs_Select(index); return this;}
	@gplx.Internal protected GfuiElemFxt FetchBtnAt(int index) {
		GfuiBtn btn = (GfuiBtn)tabBox.BtnBox().SubElems().Get_at(index);
		GfuiElemFxt fx_elem = GfuiElemFxt.new_(btn);
		return fx_elem;
	}
//		@gplx.Internal protected TabBoxFxt tst_BtnX(int idx, int expdX) {
//			Tfds.Eq(expdX, tabBox.SubBtnArea().Get_at(idx).X());
//			return this;
//		}
	@gplx.Internal protected TabBoxFxt tst_Selected(String expd) {
		TabPnlItm curTab = tabBox.Tabs_SelectedItm();			
		GfuiBtn btn = (GfuiBtn)tabBox.BtnBox().SubElems().Get_at(curTab.Idx());
		Tfds.Eq(expd, btn.Text());
		return this;
	}
	@gplx.Internal protected TabBoxFxt tst_Btns(String... expd) {
		String[] actl = new String[tabBox.Tabs_Count() ];
		for (int i = 0; i < tabBox.Tabs_Count() ; i++) {
			GfuiBtn button = (GfuiBtn)tabBox.BtnBox().SubElems().Get_at(i);
			actl[i] = button.TextMgr().Val();
		}
		Tfds.Eq_ary(expd, actl);
		return this;
	}
//		@gplx.Internal protected TabBoxFxt tst_Raised(boolean expd) {Tfds.Eq(expd, received != null); return this;}
//		@gplx.Internal protected TabBoxFxt Reorder(int i, int delta) {
//			tabBox.Width_(240);	// needed for lytMgr
//			TabBnd_reorderTab reorderBnd = TabBnd_reorderTab.Instance;
//			received = null;
//			TabPnl pnl = tabBox.Tabs_FetchAt(i);
//			reorderBnd.MoveTab(pnl.SubTabBtn(), delta);
//			return this;
//		}
//		@gplx.Internal protected TabBoxFxt tst_FocusOrder() {
//			for (int i = 0; i < tabBox.SubBtnArea().SubZones().Get_at(0).Count(); i++) {
//				GfuiElem subBtn = (GfuiElem)tabBox.SubBtnArea().SubZones().Get_at(0).Get_at(i);
//				Tfds.Eq(i, subBtn.UnderElem().Core().Focus_index());
//			}
//			return this;
//		}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, OrderChangedReceived_cmd))	OrderChangedReceived(m);
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final    String OrderChangedReceived_cmd = "OrderChangedReceived";
	TabBox tabBox;
	public static TabBoxFxt new_() {
		TabBoxFxt rv = new TabBoxFxt();
		rv.tabBox = TabBox_.new_();
		return rv;
	}	TabBoxFxt() {}
	void OrderChangedReceived(GfoMsg msg) {
	}	//int[] received = null;
}
