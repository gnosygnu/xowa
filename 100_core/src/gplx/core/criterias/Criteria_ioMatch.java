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
import gplx.core.texts.*;
public class Criteria_ioMatch implements Criteria { // EX: url IOMATCH '*.xml|*.txt'
	public Criteria_ioMatch(boolean match, RegxPatn_cls_ioMatch pattern) {this.match = match; this.pattern = pattern;}
	public byte				Tid() {return Criteria_.Tid_iomatch;}
	public boolean				Neg() {return !match;} private final    boolean match;
	public void				Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public void				Val_as_obj_(Object v) {this.pattern = (RegxPatn_cls_ioMatch)v;}
	public RegxPatn_cls_ioMatch Pattern() {return pattern;} private RegxPatn_cls_ioMatch pattern;
	public boolean Matches(Object compObj) {
		Io_url comp = (Io_url)compObj;
		boolean rv = pattern.Matches(comp.XtoCaseNormalized());
		return match ? rv : !rv;
	}
	public String To_str() {return String_.Concat_any("IOMATCH ", pattern);}

	public static final String TokenName = "IOMATCH";
	public static Criteria_ioMatch as_(Object obj) {return obj instanceof Criteria_ioMatch ? (Criteria_ioMatch)obj : null;}
	public static Criteria_ioMatch parse(boolean match, String raw, boolean caseSensitive) {return new Criteria_ioMatch(match, RegxPatn_cls_ioMatch_.parse(raw, caseSensitive));}
}
