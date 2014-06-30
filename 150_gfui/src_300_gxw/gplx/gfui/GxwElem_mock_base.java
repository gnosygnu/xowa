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
package gplx.gfui; import gplx.*;
public class GxwElem_mock_base implements GxwElem {
	public GxwCore_base Core() {return ctrlMgr;} final GxwCore_mock ctrlMgr = new GxwCore_mock();
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	public String TextVal() {return text;} public void TextVal_set(String v) {text = v;} private String text = "";
			public void SendKeyDown(IptKey key) {}
	public void EnableDoubleBuffering() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}

	ListAdp list = ListAdp_.new_();
	public static GxwElem_mock_base new_() {return new GxwElem_mock_base();} protected GxwElem_mock_base() {}
}
class MockTextBox extends GxwElem_mock_base implements GxwTextFld {
	public boolean Border_on() {return borderOn;} public void Border_on_(boolean v) {borderOn = v;} private boolean borderOn = true;
	public boolean OverrideTabKey() {return false;} public void OverrideTabKey_(boolean v) {}
	public int SelBgn() {return selectionStart;} public void SelBgn_set(int v) {selectionStart = v;} int selectionStart;
	public int SelLen() {return selectionLength;} public void SelLen_set(int v) {selectionLength = v;} int selectionLength;
	public void AlignH_(GfuiAlign val) {}
	public void CreateControlIfNeeded() {}
	public void Margins_set(int left, int top, int right, int bot) {}
}
class MockTextBoxMulti extends MockTextBox implements GxwTextMemo, GxwTextHtml {		public KeyVal[] Html_sel_atrs() {return KeyVal_.Ary_empty;}
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
	public void DataSource_set(Object... ary) {}
	public Object SelectedItm() {return selectedItm;} public void SelectedItm_set(Object v) {this.selectedItm = v;} Object selectedItm;
}
class MockListBox extends GxwElem_mock_base implements GxwListBox {
	public void Items_Add(Object item) {}
	public void Items_Clear() {}
	public Object Items_SelObj() {return null;}
	public int Items_Count() {return -1;}
	public int Items_SelIdx() {return -1;} public void Items_SelIdx_set(int v) {}
}
