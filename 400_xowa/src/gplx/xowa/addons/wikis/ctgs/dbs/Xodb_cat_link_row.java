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
package gplx.xowa.addons.wikis.ctgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
public class Xodb_cat_link_row {
	public Xodb_cat_link_row(int page_id, int cat_id, byte type_id, long timestamp_unix, byte[] sortkey, byte[] sortkey_prefix) {
		this.page_id = page_id;
		this.cat_id = cat_id;
		this.type_id = type_id;
		this.timestamp_unix = timestamp_unix;
		this.sortkey = sortkey;
		this.sortkey_prefix = sortkey_prefix;
	}
	public int Page_id() {return page_id;} private final    int page_id;
	public int Cat_id() {return cat_id;} private final    int cat_id;
	public byte Type_id() {return type_id;} private final    byte type_id;
	public long Timestamp_unix() {return timestamp_unix;} private final    long timestamp_unix;
	public byte[] Sortkey() {return sortkey;} private final    byte[] sortkey;
	public byte[] Sortkey_prefix() {return sortkey_prefix;} private final    byte[] sortkey_prefix;
}