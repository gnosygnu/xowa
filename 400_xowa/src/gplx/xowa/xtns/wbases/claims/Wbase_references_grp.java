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
package gplx.xowa.xtns.wbases.claims; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
public class Wbase_references_grp {
	public Wbase_references_grp(byte[] hash, Wbase_claim_grp_list snaks, int[] snaks_order) {
		this.hash = hash;
		this.snaks = snaks;
		this.snaks_order = snaks_order;
	}
	public byte[] Hash() {return hash;} private final    byte[] hash;
	public Wbase_claim_grp_list Snaks() {return snaks;} private final    Wbase_claim_grp_list snaks;
	public int[] Snaks_order() {return snaks_order;} private final    int[] snaks_order;
}
