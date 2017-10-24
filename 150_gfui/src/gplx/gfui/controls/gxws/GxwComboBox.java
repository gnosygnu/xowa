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
import gplx.gfui.draws.*;
public interface GxwComboBox extends GxwElem {
	ColorAdp Border_color();
	void Border_color_(ColorAdp v);
	int SelBgn(); void SelBgn_set(int v);
	int SelLen(); void SelLen_set(int v);
	void Sel_(int bgn, int end);
	Object SelectedItm(); void SelectedItm_set(Object v);
	String[] DataSource_as_str_ary();
	void DataSource_set(Object... ary);
	String Text_fallback(); void Text_fallback_(String v);
	int List_sel_idx(); void List_sel_idx_(int v);
	boolean List_visible(); void List_visible_(boolean v);
	void Items__update(String[] ary);
	void Items__size_to_fit(int count);
	void Items__visible_rows_(int v);
	void Items__jump_len_(int v);
	void Items__backcolor_(ColorAdp v);
	void Items__forecolor_(ColorAdp v);
	void Margins_set(int left, int top, int right, int bot);
}
