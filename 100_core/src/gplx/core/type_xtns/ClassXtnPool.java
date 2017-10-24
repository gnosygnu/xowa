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
package gplx.core.type_xtns; import gplx.*; import gplx.core.*;
import gplx.core.lists.*;
public class ClassXtnPool extends Hash_adp_base {
	public void Add(ClassXtn typx) {Add_base(typx.Key(), typx);}
	public ClassXtn Get_by_or_fail(String key) {return (ClassXtn)Get_by_or_fail_base(key);}

	public static final ClassXtnPool Instance =  new ClassXtnPool();
	public static final String Format_null = "";
	public static ClassXtnPool new_() {return new ClassXtnPool();}
	ClassXtnPool() {
		Add(ObjectClassXtn.Instance);
		Add(StringClassXtn.Instance);
		Add(IntClassXtn.Instance);
		Add(BoolClassXtn.Instance);
		Add(ByteClassXtn.Instance);
		Add(DateAdpClassXtn.Instance);
		Add(TimeSpanAdpClassXtn.Instance);
		Add(IoUrlClassXtn.Instance);
		Add(DecimalAdpClassXtn.Instance);
		Add(FloatClassXtn.Instance);
	}
}
