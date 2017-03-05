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
package gplx.gfui.controls.gxws; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.ipts.*;
import gplx.gfui.draws.*;
public class GxwElem_mock_base implements GxwElem {
	public GxwCore_base Core() {return ctrlMgr;} final    GxwCore_mock ctrlMgr = new GxwCore_mock();
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	public String TextVal() {return text;} public void TextVal_set(String v) {text = v;} private String text = "";
			public void SendKeyDown(IptKey key) {}
	public void EnableDoubleBuffering() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}

	List_adp list = List_adp_.New();
	public static GxwElem_mock_base new_() {return new GxwElem_mock_base();} protected GxwElem_mock_base() {}
}
class MockTextBox extends GxwElem_mock_base implements GxwTextFld {
	public boolean Border_on() {return borderOn;} public void Border_on_(boolean v) {borderOn = v;} private boolean borderOn = true;
	public ColorAdp Border_color() {return border_color;} public void Border_color_(ColorAdp v) {border_color = v;} private ColorAdp border_color;
	public boolean OverrideTabKey() {return false;} public void OverrideTabKey_(boolean v) {}
	public int SelBgn() {return selectionStart;} public void SelBgn_set(int v) {selectionStart = v;} int selectionStart;
	public int SelLen() {return selectionLength;} public void SelLen_set(int v) {selectionLength = v;} int selectionLength;
	public void AlignH_(GfuiAlign val) {}
	public void CreateControlIfNeeded() {}
	public void Margins_set(int left, int top, int right, int bot) {}
}
class MockTextBoxMulti extends MockTextBox implements GxwTextMemo, GxwTextHtml {		public Keyval[] Html_sel_atrs() {return Keyval_.Ary_empty;}
	public void Html_enabled(boolean v) {}
	public String Html_doc_html() {return "";}
	public void Html_css_set(String s) {}
	public int LinesPerScreen() {return linesPerScreen;} int linesPerScreen = 1;
	public int LinesTotal() {return linesTotal;} int linesTotal = 1;
	public int ScreenCount() {return screenCount;} int screenCount = 1;
	public int LineLength(int lineIndex) {return -1;}
	public int CharIndexOf(int lineIndex) {return -1;}
	public int CharIndexOfFirst() {return -1;}
	public int LineIndexOfFirst() {return -1;}
	public int LineIndexOf(int charIndex) {return -1;}
	public PointAdp PosOf(int charIndex) {return PointAdp_.Null;}
	public void ScrollLineUp() {}
	public void ScrollLineDown() {}
	public void ExtendLineUp() {}
	public void ExtendLineDown() {}
	public void ScrollScreenUp() {}
	public void ScrollScreenDown() {}
	public void SelectionStart_toFirstChar() {}
	public void ScrollTillSelectionStartIsFirstLine() {}
	public void ScrollTillCaretIsVisible() {}
}
class MockComboBox extends GxwElem_mock_base implements GxwComboBox {
	public ColorAdp Border_color() {return border_color;} public void Border_color_(ColorAdp v) {border_color = v;} private ColorAdp border_color;
	public int SelBgn() {return -1;} public void SelBgn_set(int v) {}
	public int SelLen() {return 0;}  public void SelLen_set(int v) {}
	public void Sel_(int bgn, int end) {}
	public String[] DataSource_as_str_ary() {return String_.Ary_empty;}
	public void DataSource_set(Object... ary) {}
	public String Text_fallback() {return "";} public void Text_fallback_(String v) {}
	public int List_sel_idx() {return -1;} public void List_sel_idx_(int v) {}
	public boolean List_visible() {return false;} public void List_visible_(boolean v) {}
	public void Items__update(String[] ary) {}
	public void Items__size_to_fit(int count) {}
	public void Items__visible_rows_(int v) {}
	public void Items__jump_len_(int v) {}
	public void Items__backcolor_(ColorAdp v) {}
	public void Items__forecolor_(ColorAdp v) {}
	public void Margins_set(int left, int top, int right, int bot) {}
	public Object SelectedItm() {return selectedItm;} public void SelectedItm_set(Object v) {this.selectedItm = v;} Object selectedItm;
}
class MockListBox extends GxwElem_mock_base implements GxwListBox {
	public void Items_Add(Object item) {}
	public void Items_Clear() {}
	public Object Items_SelObj() {return null;}
	public int Items_Count() {return -1;}
	public int Items_SelIdx() {return -1;} public void Items_SelIdx_set(int v) {}
}
