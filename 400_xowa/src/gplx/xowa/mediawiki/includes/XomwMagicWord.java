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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
public class XomwMagicWord {
	public boolean case_match;
	public byte[] name;
	public XomwMagicWordSynonym[] synonyms;
	public XomwMagicWord(byte[] name, boolean case_match, byte[][] synonyms_ary) {
		this.name = name;
		this.case_match = case_match;

		int synonyms_len = synonyms_ary.length;
		this.synonyms = new XomwMagicWordSynonym[synonyms_len];
		for (int i = 0; i < synonyms_len; i++) {
			synonyms[i] = new XomwMagicWordSynonym(name, case_match, synonyms_ary[i]);
		}
	}
}
