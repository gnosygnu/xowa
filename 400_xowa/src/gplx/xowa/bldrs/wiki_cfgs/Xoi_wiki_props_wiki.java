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
package gplx.xowa.bldrs.wiki_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xoi_wiki_props_wiki {
	public byte[] Wiki_domain()  {return wiki_domain;} public Xoi_wiki_props_wiki Wiki_domain_(byte[] v) {wiki_domain = v; return this;} private byte[] wiki_domain;
	public Xoi_wiki_props_ns[] Ns_ary() {return ns_ary;} public Xoi_wiki_props_wiki Ns_ary_(Xoi_wiki_props_ns... v) {this.ns_ary = v; return this;} private Xoi_wiki_props_ns[] ns_ary;
	public Xoi_wiki_props_alias[] Alias_ary() {return alias_ary;} public Xoi_wiki_props_wiki Alias_ary_(Xoi_wiki_props_alias... v) {this.alias_ary = v; return this;} private Xoi_wiki_props_alias[] alias_ary;
}
