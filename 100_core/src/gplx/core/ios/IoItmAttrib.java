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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class IoItmAttrib {
	public boolean ReadOnly() {return readOnly;} public IoItmAttrib ReadOnly_() {return ReadOnly_(true);} public IoItmAttrib ReadOnly_(boolean val) {readOnly = val; return this;} private boolean readOnly;
	public boolean Hidden() {return hidden;} public IoItmAttrib Hidden_() {return Hidden_(true);} public IoItmAttrib Hidden_(boolean val) {hidden = val; return this;} private boolean hidden;
	public static IoItmAttrib readOnly_() {return new IoItmAttrib().ReadOnly_();}
	public static IoItmAttrib hidden_() {return new IoItmAttrib().Hidden_();}
	public static IoItmAttrib normal_() {return new IoItmAttrib().ReadOnly_(false).Hidden_(false);}
}
