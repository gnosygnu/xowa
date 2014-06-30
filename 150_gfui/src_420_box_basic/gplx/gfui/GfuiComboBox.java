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
public class GfuiComboBox extends GfuiElemBase {
	@Override public GxwElem UnderElem_make(KeyValHash ctorArgs) {return GxwElemFactory_._.comboBox_();} GxwComboBox comboBox;
	public Object SelectedItm() {return comboBox.SelectedItm();} public void SelectedItm_set(Object v) {comboBox.SelectedItm_set(v);}
	@Override public void ctor_GfuiBox_base(KeyValHash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		this.comboBox = (GxwComboBox)this.UnderElem();
	}
	public void DataSource_set(Object... ary) {
		comboBox.DataSource_set(ary);
	}
	public static GfuiComboBox new_() {
		GfuiComboBox rv = new GfuiComboBox();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}
}
