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
package gplx.xowa.wikis.pages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_db_protection {
	public Xopg_db_protection() {this.Clear();}
	public byte[]	User() {return user;} public Xopg_db_protection User_(byte[] v) {user = v; return this;} private byte[] user;
	public byte[]	Protection_level() {return protection_level;} public Xopg_db_protection Protection_level_(byte[] v) {protection_level = v; return this;} private byte[] protection_level;
	public byte[]	Protection_expiry() {return protection_expiry;} private byte[] protection_expiry;

	public void Clear() {
		this.user = Bry_.Empty;
		this.protection_level = Bry_.Empty;
		this.protection_expiry = Bry__protection_expiry__infinite;
	}

	public static final    byte[] Bry__protection_expiry__infinite = Bry_.new_a7("infinity");// NOTE: means page never expires; must be "infinity" as per en.w:Module:Effective_protection_expiry DATE:2016-08-05
}
