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
package gplx.xowa.addons.bldrs.exports.splits.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
class Bin_meta_itm {
	public Bin_meta_itm(byte bin_type, int bin_owner_id, int bin_id, int bin_db_id) {
		this.bin_type = bin_type; this.bin_owner_id = bin_owner_id; this.bin_id = bin_id; this.bin_db_id = bin_db_id;
	}
	public byte Bin_type() {return bin_type;} private final    byte bin_type;
	public int Bin_owner_id() {return bin_owner_id;} private final    int bin_owner_id;
	public int Bin_id() {return bin_id;} private final    int bin_id;
	public int Bin_db_id() {return bin_db_id;} private final    int bin_db_id;
}
