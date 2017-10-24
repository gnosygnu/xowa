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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
public class Php_line_assign implements Php_line {
	public Php_key Key() {return key;} public Php_line_assign Key_(Php_key v) {this.key = v; return this;} Php_key key;
	public Php_key[] Key_subs() {return key_subs;} public Php_line_assign Key_subs_(Php_key[] v) {this.key_subs = v; return this;} Php_key[] key_subs = Php_key_.Ary_empty;
	public Php_itm Val() {return val;} public Php_line_assign Val_(Php_itm v) {this.val = v; return this;} Php_itm val;
}
