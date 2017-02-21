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
package gplx.xowa.htmls.doms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
public class Xoh_find {
	public int Tag_bgn() {return tag_bgn;} public Xoh_find Tag_bgn_(int v) {tag_bgn = v; return this;} private int tag_bgn;
	public int Tag_end() {return tag_end;} public Xoh_find Tag_end_(int v) {tag_end = v; return this;} private int tag_end;
	public int Key_bgn() {return key_bgn;} public Xoh_find Key_bgn_(int v) {key_bgn = v; return this;} private int key_bgn;
	public int Key_end() {return key_end;} public Xoh_find Key_end_(int v) {key_end = v; return this;} private int key_end;
	public int Val_bgn() {return val_bgn;} public Xoh_find Val_bgn_(int v) {val_bgn = v; return this;} private int val_bgn;
	public int Val_end() {return val_end;} public Xoh_find Val_end_(int v) {val_end = v; return this;} private int val_end;
	public void Set_all(int tag_bgn, int tag_end, int key_bgn, int key_end, int val_bgn, int val_end) {
		this.tag_bgn = tag_bgn; this.tag_end = tag_end; this.key_bgn = key_bgn; this.key_end = key_end; this.val_bgn = val_bgn; this.val_end = val_end;
	}
}
