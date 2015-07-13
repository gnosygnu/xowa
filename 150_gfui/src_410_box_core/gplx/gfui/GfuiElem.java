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
public interface GfuiElem extends GfoInvkAble, GxwCbkHost, IptBndsOwner, GftItem, GfoEvObj {
	//% Layout
	int X(); GfuiElem X_(int val);
	int Y(); GfuiElem Y_(int val);
	int X_max();
	int Y_max();
	GfuiElem Pos_(PointAdp val); GfuiElem Pos_(int x, int y);
	int Width(); GfuiElem Width_(int val);
	int Height(); GfuiElem Height_(int val);
	GfuiElem Size_(SizeAdp val); GfuiElem Size_(int w, int h); 
	RectAdp Rect(); void Rect_set(RectAdp rect);
	void Zorder_front(); void Zorder_back();
	PointAdp Pos();
	SizeAdp Size();
	
	//% Visual
	boolean Visible(); void Visible_set(boolean v); GfuiElem Visible_on_(); GfuiElem Visible_off_();
	ColorAdp BackColor(); GfuiElem BackColor_(ColorAdp v);
	GfuiBorderMgr Border(); GfuiElem Border_on_(); GfuiElem Border_off_();
	void Redraw();

	//% Text
	GfxStringData TextMgr();
	String Text(); GfuiElem Text_(String v); GfuiElem Text_any_(Object v);
	GfuiElem ForeColor_(ColorAdp v);
	void TextAlignH_(GfuiAlign v);
	GfuiElem TextAlignH_left_(); GfuiElem TextAlignH_right_(); GfuiElem TextAlignH_center_();
	String TipText(); GfuiElem TipText_(String v);

	//% Focus
	boolean Focus_has();
	boolean Focus_able(); GfuiElem Focus_able_(boolean v);
	int Focus_idx(); GfuiElem Focus_idx_(int val);
	String Focus_default(); GfuiElem Focus_default_(String v);
	void Focus();

	//% Action
	void Click();

	//% Elem Tree Hierarchy (Owners And Subs)
	//String Key_of_GfuiElem(); 
	GfuiElem Key_of_GfuiElem_(String val);	
	GfuiElem OwnerElem(); GfuiElem OwnerElem_(GfuiElem val); GfuiElem Owner_(GfuiElem owner); GfuiElem Owner_(GfuiElem owner, String key);
	GfuiElemList SubElems();
	GfuiElem Inject_(InjectAble sub);

	//% Form
	GfuiWin OwnerWin(); GfuiElem OwnerWin_(GfuiWin val);		
	void Opened_cbk(); boolean Opened_done();
	void Dispose();

	//% Infrastructure
	GxwElem UnderElem();
	GxwElem UnderElem_make(KeyValHash ctorArgs);
	void ctor_GfuiBox_base(KeyValHash ctorArgs);
	void Invoke(GfoInvkAbleCmd cmd);
}
