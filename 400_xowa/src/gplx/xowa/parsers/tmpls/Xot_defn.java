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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public interface Xot_defn extends Rls_able {
	byte Defn_tid();
	byte[] Name();
	int Cache_size();
	boolean Defn_require_colon_arg();
	Xot_defn Clone(int id, byte[] name);
}
class Xot_defn_null implements Xot_defn {
	public byte Defn_tid() {return Xot_defn_.Tid_null;}
	public boolean Defn_require_colon_arg() {return false;}
	public byte[] Name() {return Bry_.Empty;}
	public Xot_defn Clone(int id, byte[] name) {return this;}
	public int Cache_size() {return 0;}
	public void Rls() {}
	public static final Xot_defn_null Instance = new Xot_defn_null(); Xot_defn_null() {}
}
