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
package gplx.dbs; import gplx.*;
public class Db_cmd_mode {
	Db_cmd_mode(int val) {this.val = val;}
	public int Val() {return val;} int val;
	public Db_cmd_mode MarkUpdated() {return this == Retrieved ? Updated : this;} // Created/Deleted noops
	public boolean Modified() {return this == Created || this == Updated;}
	public static final byte Tid_create = 1, Tid_update = 2, Tid_delete = 3, Tid_ignore = 4;
	public static final Db_cmd_mode
	  Created		= new Db_cmd_mode(Tid_create)
	, Updated		= new Db_cmd_mode(Tid_update)
	, Deleted		= new Db_cmd_mode(Tid_delete)
	, Retrieved		= new Db_cmd_mode(Tid_ignore)
	;
	public static byte To_update(byte cur) {
		switch (cur) {
			case Tid_create:					// ignore update if item is already marked for create
			case Tid_delete:					// ignore update if item is already marked for delete (might want to throw error)
							return cur;		
			case Tid_ignore:					// must mark for update
			case Tid_update:					// return self
							return Tid_update;
			default:		throw Err_.new_unhandled(cur);
		}
	}
}
