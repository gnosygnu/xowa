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
package gplx.core.progs; import gplx.*; import gplx.core.*;
public class Gfo_prog_ui_ {
	public static final    Gfo_prog_ui Noop = new Gfo_prog_ui__noop(), Always = new Gfo_prog_ui__always();
	public static final byte 
	  Status__init			=  1
	, Status__working		=  2
	, Status__done			=  4
	, Status__fail			=  8
	, Status__suspended		= 16
	, Status__runnable		= Status__init | Status__suspended | Status__fail
	;
}
class Gfo_prog_ui__noop implements Gfo_prog_ui {
	public boolean		Canceled() {return true;}
	public void		Cancel() {}
	public byte		Prog_status() {return Gfo_prog_ui_.Status__init;}
	public void		Prog_status_(byte v) {}
	public long		Prog_data_cur() {return 0;}
	public long		Prog_data_end() {return 0;}
	public boolean		Prog_notify_and_chk_if_suspended(long cur, long max) {return false;}
	public void		Prog_notify_by_msg(String msg) {}
}
class Gfo_prog_ui__always implements Gfo_prog_ui {
	public boolean		Canceled() {return false;}
	public void		Cancel() {}
	public byte		Prog_status() {return Gfo_prog_ui_.Status__init;}
	public void		Prog_status_(byte v) {}
	public long		Prog_data_cur() {return 0;}
	public long		Prog_data_end() {return 0;}
	public boolean		Prog_notify_and_chk_if_suspended(long cur, long max) {return false;}
	public void		Prog_notify_by_msg(String msg) {}
}
