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
import gplx.core.lists.*; /*EnumerAble*/
public class GfuiListBox extends GfuiElemBase {
	@Override public GxwElem UnderElem_make(KeyValHash ctorArgs) {return GxwElemFactory_.Instance.listBox_();}
	@Override public void ctor_GfuiBox_base(KeyValHash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		this.listBox = (GxwListBox)UnderElem();
	}
	public void Items_Add(Object o) {listBox.Items_Add(o);}
	public void Items_Clear() {listBox.Items_Clear();}
	public Object Items_SelObj() {return listBox.Items_SelObj();}
	public void BindData(EnumerAble enumerable) {
		for (Object item : enumerable)
			this.listBox.Items_Add(item.toString());
	}
	public int Items_Count() {return listBox.Items_Count();}
	public int Items_SelIdx() {return listBox.Items_SelIdx();} public void Items_SelIdx_set(int v) {listBox.Items_SelIdx_set(v);}

	GxwListBox listBox;
	public static GfuiListBox new_() {
		GfuiListBox rv = new GfuiListBox();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}
}

