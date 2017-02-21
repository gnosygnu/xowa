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
package gplx.xowa.wikis.tdbs.xdats; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
public class Xob_xdat_itm {
	public byte[] Src() {return src;} public Xob_xdat_itm Src_(byte[] v) {src = v; return this;} private byte[] src;
	public int Itm_bgn() {return itm_bgn;} public Xob_xdat_itm Itm_bgn_(int v) {itm_bgn = v; return this;} private int itm_bgn = -1;
	public int Itm_end() {return itm_end;} public Xob_xdat_itm Itm_end_(int v) {itm_end = v; return this;} private int itm_end = -1;
	public int Itm_idx() {return itm_idx;} public Xob_xdat_itm Itm_idx_(int v) {itm_idx = v; return this;} private int itm_idx = -1;
	public void Clear() {itm_bgn = itm_end = itm_idx = -1; src = null; found_exact = false;}
	public boolean Found_exact() {return found_exact;} private boolean found_exact;
	public Xob_xdat_itm Found_exact_y_() {found_exact = true; return this;}
	public boolean Missing() {return itm_bgn == -1;}
	public byte[] Itm_bry() {return Bry_.Mid(src, itm_bgn, itm_end);}
}
