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
package gplx;
import java.util.*;	
public class RandomAdp_ implements Gfo_invk {
	public static RandomAdp new_() {
		Random random = new Random(System.currentTimeMillis()); 
		return new RandomAdp(random);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Next))	return RandomAdp_.new_().Next(m.ReadInt("max"));
		else								return Gfo_invk_.Rv_unhandled;
	}	static final    String Invk_Next = "Next";
        public static final    RandomAdp_ Gfs = new RandomAdp_();
}
