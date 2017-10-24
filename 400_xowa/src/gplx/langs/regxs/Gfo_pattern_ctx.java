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
package gplx.langs.regxs; import gplx.*; import gplx.langs.*;
public class Gfo_pattern_ctx {
	public boolean Rslt_pass() {return rslt;} private boolean rslt;
	public void Rslt_fail_() {rslt = false;}
	public boolean Prv_was_wild() {return prv_was_wild;} public void Prv_was_wild_(boolean v) {prv_was_wild = v;} private boolean prv_was_wild;
	private int itm_len;
	public int Itm_idx() {return itm_idx;} public void Itm_idx_(int v) {itm_idx = v;} private int itm_idx;
	public boolean Itm_idx_is_last() {return itm_idx == itm_len - 1;}
	public void Init(int itm_len) {
		this.rslt = true;
		this.itm_len = itm_len;
		this.prv_was_wild = false;
	}
}
