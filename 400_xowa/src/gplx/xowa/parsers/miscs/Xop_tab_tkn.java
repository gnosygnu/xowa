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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
public class Xop_tab_tkn extends Xop_tkn_itm_base {
	public Xop_tab_tkn(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end);}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_tab;}
	public static final    byte[] Bry_tab_ent = Bry_.new_a7("&#09;");
}
/*
NOTE_1:tabs
. tabs exist in wikimedia source; note that tabs (\t) are not a meaningful HTML character
. xowa uses tabs for delimiters in its xowa files
. in order to maintain some semblance of fidelity, "\t" was replaced with &#09;
. unfortunately, "\t" is generally trimmed as whitespace throughout mediawiki; "&#09;" is not
. so, as a HACK, replace "&#09;" with "\t\s\s\s\s";
.. note that all 5 chars of "&#09;" must be replaced; hence "\t\s\s\s\s"
.. note that they all need to be ws in order to be trimmed out
.. note that shrinking the src[] would be (a) memory-expensive (b) complexity-expensive (many functions assume a static src size)
.. note that "\t\t\t\t\t" was the 1st attempt, but this resulted in exponential growth of "\t"s with each save (1 -> 5 -> 25 -> 125). "\t\s\s\s\s" is less worse with its linear growth (1 -> 5 -> 10)
. TODO_OLD: swap out the "&#09;" at point of file-read;
*/