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
package gplx.gfui.layouts.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.layouts.*;
public class Swt_layout_data__grid implements Swt_layout_data {
	public boolean Grab_excess_w() {return grab_excess_w;} public Swt_layout_data__grid Grab_excess_w_(boolean v) {grab_excess_w = v; return this;} private boolean grab_excess_w;
	public boolean Grab_excess_h() {return grab_excess_h;} public Swt_layout_data__grid Grab_excess_h_(boolean v) {grab_excess_h = v; return this;} private boolean grab_excess_h;
	public int Align_w() {return align_w;} public Swt_layout_data__grid Align_w_(int v) {align_w = v; return this;} private int align_w = Align__null;
	public int Align_h() {return align_h;} public Swt_layout_data__grid Align_h_(int v) {align_h = v; return this;} private int align_h = Align__null;
	public int Min_w() {return min_w;} public Swt_layout_data__grid Min_w_(int v) {min_w = v; return this;} private int min_w = -1;
	public int Min_h() {return min_h;} public Swt_layout_data__grid Min_h_(int v) {min_h = v; return this;} private int min_h = -1;
	public int Hint_w() {return hint_w;} public Swt_layout_data__grid Hint_w_(int v) {hint_w = v; return this;} private int hint_w = -1;
	public int Hint_h() {return hint_h;} public Swt_layout_data__grid Hint_h_(int v) {hint_h = v; return this;} private int hint_h = -1;

	public Swt_layout_data__grid Align_w__fill_() {return Align_w_(Align__fill);}
	public Swt_layout_data__grid Align_h__fill_() {return Align_h_(Align__fill);}

	// SWT: maps to GridData.BEGINNING, etc
	public static final int Align__null = 0, Align__bgn = 1, Align__mid = 2, Align__end = 3, Align__fill = 4;
}
