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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
public class Xop_root_tkn extends Xop_tkn_itm_base {
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_root;}
	public byte[] Root_src() {return root_src;} public Xop_root_tkn Root_src_(byte[] v) {root_src = v; return this;} private byte[] root_src = Bry_.Empty;
	public byte[] Data_mid() {return data_mid;} public Xop_root_tkn Data_mid_(byte[] v) {data_mid = v; return this;} private byte[] data_mid = Bry_.Empty;
	public byte[] Data_htm() {return data_htm;} public Xop_root_tkn Data_htm_(byte[] v) {data_htm = v; return this;} private byte[] data_htm = Bry_.Empty;
	@Override public void Reset() {
		super.Reset();
		root_src = Bry_.Empty;
	}
}
