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
package gplx.gfml; import gplx.*;
class UsrMsg_mok {
	public String Main() {return main;} public UsrMsg_mok Main_(String v) {main = v; return this;} private String main;
	public UsrMsg_mok Add_(String k, Object o) {hash.Add(k, Keyval_.new_(k, o)); return this;}
	public UsrMsg_mok Require_(String k) {required.Add(k, k); return this;}
	public Ordered_hash Args() {return hash;} Ordered_hash hash = Ordered_hash_.New();
	public Ordered_hash Required() {return required;} Ordered_hash required = Ordered_hash_.New();
	public static UsrMsg_mok new_(UsrMsg um) {
		UsrMsg_mok rv = new UsrMsg_mok();
		if (um != null) {
			rv.main = um.Hdr();
			for (int i = 0; i < um.Args().Count(); i++) {
				Keyval kv = (Keyval)um.Args().Get_at(i);
				rv.Add_(kv.Key(), kv.Val());
			}
		}
		return rv;
	}	UsrMsg_mok() {}
}
