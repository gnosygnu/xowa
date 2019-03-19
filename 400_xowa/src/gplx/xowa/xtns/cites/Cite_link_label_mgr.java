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
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Cite_link_label_mgr {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public void Clear() {
		hash.Clear();
	}
	public Cite_link_label_grp Get_or_null(byte[] group) {
		return (Cite_link_label_grp)hash.Get_by(group);
	}
	public void Add(byte[] group, Cite_link_label_grp grp) {
		hash.Add(group, grp);
	}
}
class Cite_link_label_grp {
	private final    byte[][] labels;
	public Cite_link_label_grp(byte[] name, byte[][] labels) {
		this.name = name;
		this.labels = labels;
	}
	public byte[] Name() {return name;} private final    byte[] name;
	public int Len() {return labels.length;}
	public byte[] Get_or_null(int idx) {
		return idx < labels.length ? labels[idx] : null;
	}
}
