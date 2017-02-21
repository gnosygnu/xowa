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
package gplx.xowa.langs.genders; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
public class Xol_gender_ {
	public static Xol_gender new_by_lang_id(int lang_id) {return Xol_gender__basic.Instance;}
	public static final int Tid_male = 0, Tid_female = 1, Tid_unknown = 2;
}
class Xol_gender__basic implements Xol_gender {
	public byte[] Gender_eval(int gender, byte[] when_m, byte[] when_f, byte[] when_u) {
		switch (gender) {
			case Xol_gender_.Tid_male:		return when_m;
			case Xol_gender_.Tid_female:	return when_f;
			case Xol_gender_.Tid_unknown:	return when_u;
			default:						throw Err_.new_unimplemented();
		}
	}
	public static final Xol_gender__basic Instance = new Xol_gender__basic(); Xol_gender__basic() {}
}
