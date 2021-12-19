/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.criterias;
import gplx.core.texts.RegxPatn_cls_like;
import gplx.core.texts.RegxPatn_cls_like_;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Criteria_like implements Criteria {
	public Criteria_like(boolean neg, RegxPatn_cls_like pattern) {this.neg = neg; this.pattern = pattern;}
	public byte                    Tid() {return Criteria_.Tid_like;}
	public boolean                    Neg() {return neg;} private final boolean neg;
	public RegxPatn_cls_like    Pattern() {return pattern;} private RegxPatn_cls_like pattern;
	public void                    Val_from_args(Hash_adp args) {throw ErrUtl.NewUnimplemented();}
	public void                    Val_as_obj_(Object v) {this.pattern = RegxPatn_cls_like_.parse((String)v, RegxPatn_cls_like.EscapeDefault);}
	public boolean Matches(Object compObj) {
		String comp = StringUtl.CastOrNull(compObj); if (comp == null) throw ErrUtl.NewCast(String.class, compObj);
		boolean rv = pattern.Matches(comp);
		return neg ? !rv : rv;
	}

	public String ToStr() {return StringUtl.ConcatObjs("LIKE ", pattern);}
}
