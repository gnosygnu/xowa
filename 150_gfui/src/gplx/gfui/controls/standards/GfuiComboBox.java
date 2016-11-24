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
package gplx.gfui.controls.standards; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.kits.core.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*;
public class GfuiComboBox extends GfuiElemBase {
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		this.combo = (GxwComboBox)this.UnderElem();
	}
	@Override public GxwElem UnderElem_make(Keyval_hash ctorArgs) {return GxwElemFactory_.Instance.comboBox_();} private GxwComboBox combo;
	public int SelBgn() {return combo.SelBgn();} public void SelBgn_set(int v) {combo.SelBgn_set(v); Gfo_evt_mgr_.Pub(this, Evt__selection_start_changed);}
	public int SelLen() {return combo.SelLen();} public void SelLen_set(int v) {combo.SelLen_set(v);}
	public void Sel_(int bgn, int len) {combo.Sel_(bgn, len);}
	public Object SelectedItm() {return combo.SelectedItm();} public void SelectedItm_set(Object v) {combo.SelectedItm_set(v);}
	public String[]	DataSource_as_str_ary()								{return combo.DataSource_as_str_ary();}
	public void		DataSource_set(Object... ary)					{combo.DataSource_set(ary);}
	public String	Text_fallback()										{return combo.Text_fallback();}
	public void		Text_fallback_(String v)							{combo.Text_fallback_(v);}
	public int		List_sel_idx()										{return combo.List_sel_idx();}
	public void		List_sel_idx_(int v)								{combo.List_sel_idx_(v);}
	public boolean		List_visible()										{return combo.List_visible();}
	public void		List_visible_(boolean v)							{combo.List_visible_(v);}
	public void		Items__update(String[] ary)							{combo.Items__update(ary);}
	public void		Items__size_to_fit(int len)							{combo.Items__size_to_fit(len);}
	public void		Items__visible_rows_(int v)							{combo.Items__visible_rows_(v);}
	public void		Items__jump_len_(int v)								{combo.Items__jump_len_(v);}
	public void		Margins_set(int left, int top, int right, int bot)	{combo.Margins_set(left, top, right, bot);}
	public static GfuiComboBox new_() {
		GfuiComboBox rv = new GfuiComboBox();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}
	public static GfuiComboBox kit_(Gfui_kit kit, String key, GxwElem elm, Keyval_hash ctorArgs) {
		GfuiComboBox rv = new GfuiComboBox();
		rv.ctor_kit_GfuiElemBase(kit, key, elm, ctorArgs);
		rv.combo = (GxwComboBox)elm;
		return rv;
	}
	public static final String
	  Evt__selected_changed = "Selected_changed"
	, Evt__selected_accepted = "Selected_accepted"
	, Evt__selection_start_changed = "SelectionStartChanged"
	;
}
