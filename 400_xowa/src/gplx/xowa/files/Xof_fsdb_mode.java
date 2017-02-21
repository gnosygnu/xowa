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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
public class Xof_fsdb_mode {
	private int tid;
	Xof_fsdb_mode(int tid) {this.tid = tid;}
	public boolean Tid__v2__bld()		{return tid == TID__v2__bld;}
	public void Tid__v2__bld__y_()	{tid = TID__v2__bld;}
	public void Tid__v2__mp__y_()	{tid = TID__v2__mp;}
	public boolean Tid__bld()			{return tid > TID__v2__gui;}
	private static final int
	  TID__v0			= 1
	, TID__v2__gui		= 2
	, TID__v2__bld		= 3
	, TID__v2__mp		= 4
	;
	public static Xof_fsdb_mode New__v0()		{return new Xof_fsdb_mode(TID__v0);}
	public static Xof_fsdb_mode New__v2__gui()	{return new Xof_fsdb_mode(TID__v2__gui);}
}
