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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_amp_mgr_rslt {
	public Xop_amp_mgr_rslt(int pos, int val, Xop_tkn_itm tkn) {
		this.pos = pos;
		this.val = val;
		this.tkn = tkn;
	}
	public Xop_amp_mgr_rslt() {}
	public boolean Pass() {return pass;} private boolean pass; public void Valid_(boolean v) {this.pass = v;} 
	public int Pos() {return pos;} private int pos; public void Pos_(int v) {this.pos = v;}
	public int Val() {return val;} private int val; public void Val_(int v) {this.val = v;}
	public Xop_tkn_itm Tkn() {return tkn;} private Xop_tkn_itm tkn; public void Tkn_(Xop_tkn_itm v) {this.tkn = v;}
	public boolean Pass_y_(int pos, int val) {
		this.pos = pos; this.val = val;
		this.pass = true;
		return true;
	}
	public boolean Pass_n_(int pos) {
		this.pass = false;
		this.pos = pos;
		this.val = -1;
		this.tkn = null;
		return false;
	}
}
