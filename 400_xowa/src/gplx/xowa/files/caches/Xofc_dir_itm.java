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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
public class Xofc_dir_itm {
	public Xofc_dir_itm(int id, byte[] name, byte cmd_mode) {
		this.id = id;
		this.name = name;
		this.cmd_mode = cmd_mode;
	}
	public int Id() {return id;} public void Id_(int v) {id = v;} private int id;
	public byte[] Name() {return name;} private final byte[] name;
	public byte Cmd_mode() {return cmd_mode;} public Xofc_dir_itm Cmd_mode_(byte v) {cmd_mode = v; return this;} private byte cmd_mode;
	public static final Xofc_dir_itm Null = null;
}
