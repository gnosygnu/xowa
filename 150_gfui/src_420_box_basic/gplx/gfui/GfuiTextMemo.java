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
public class GfuiTextMemo extends GfuiTextBox {		public int LinesPerScreen() {return textBox.LinesPerScreen();}
	public int LinesTotal() {return textBox.LinesTotal();}
	public int ScreenCount() {return Int_.DivAndRoundUp(this.LinesTotal(), this.LinesPerScreen());}
	public int CharIndexOfFirst() {return textBox.CharIndexOfFirst();}
	public int CharIndexOf(int lineIndex) {return textBox.CharIndexOf(lineIndex);}
	public int CharIndexAtLine(int lineIndex) {return textBox.CharIndexOf(lineIndex);}
	public int LineIndexOfFirst() {return textBox.LineIndexOfFirst();}
	public int LineIndexOf(int charIndex) {return textBox.LineIndexOf(charIndex);}
	public PointAdp PosOf(int charIndex) {return textBox.PosOf(charIndex);}
	public void ScrollLineUp() {textBox.ScrollLineUp();}
	public void ScrollLineDown() {textBox.ScrollLineDown();}
	public void ScrollScreenUp() {textBox.ScrollScreenUp();}
	public void ScrollScreenDown() {textBox.ScrollScreenDown();}
	public void SelectionStart_toFirstChar() {textBox.SelectionStart_toFirstChar(); GfoEvMgr_.Pub(this, SelectionStartChanged_evt);}
	public void ScrollTillSelectionStartIsFirstLine() {textBox.ScrollTillSelectionStartIsFirstLine();}

	@Override public GxwElem UnderElem_make(KeyValHash ctorArgs) {return GxwElemFactory_._.text_memo_();}
	@Override public void ctor_GfuiBox_base(KeyValHash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		textBox = (GxwTextMemo)UnderElem();
		this.SetTextBox(textBox);
	}	GxwTextMemo textBox;
}
