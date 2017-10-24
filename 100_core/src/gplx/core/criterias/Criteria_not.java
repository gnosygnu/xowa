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
package gplx.core.criterias; import gplx.*; import gplx.core.*;
public class Criteria_not implements Criteria {
	public Criteria_not(Criteria v) {this.criteria = v;}
	public byte				Tid() {return Criteria_.Tid_not;}
	public boolean				Matches(Object obj) {return !criteria.Matches(obj);}
	public void				Val_from_args(Hash_adp args) {criteria.Val_from_args(args);}
	public void				Val_as_obj_(Object v) {criteria.Val_as_obj_(v);}
	public String			To_str() {return String_.Concat_any(" NOT ", criteria.To_str());}
	public Criteria			Crt() {return criteria;} private final Criteria criteria;
}
