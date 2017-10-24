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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
public class Console_adp_ {
	public static final Console_adp Noop = new Console_adp__noop();
	public static Console_adp__mem Dev() {return new Console_adp__mem();}
}
class Console_adp__noop implements Console_adp {
	public boolean Enabled() {return false;}
	public boolean Canceled_chk() {return false;}
	public int Chars_per_line_max() {return 80;} public void Chars_per_line_max_(int v) {}
	public void Write_str(String s) {}
	public void Write_fmt_w_nl(String s, Object... args) {} 
	public void Write_tmp(String s) {}
	public char Read_key(String msg) {return '\0';}
	public String Read_line(String msg) {return "";}
}
