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
package gplx.langs.htmls.styles;
import gplx.frameworks.objects.ToStrAble;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
public class Gfh_style_itm implements ToStrAble {
	public Gfh_style_itm(int idx, byte[] key, byte[] val) {this.idx = idx; this.key = key; this.val = val;}
	public int Idx() {return idx;} private final int idx;
	public byte[] Key() {return key;} private final byte[] key;
	public byte[] Val() {return val;} private final byte[] val;
	public String ToStr() {return StringUtl.NewU8(BryUtl.Add(key, AsciiByte.ColonBry, val, AsciiByte.SemicBry));}
}
