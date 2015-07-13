/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.bldrs.wiki_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xoi_wiki_props_wiki {
	public byte[] Wiki_domain()  {return wiki_domain;} public Xoi_wiki_props_wiki Wiki_domain_(byte[] v) {wiki_domain = v; return this;} private byte[] wiki_domain;
	public Xoi_wiki_props_ns[] Ns_ary() {return ns_ary;} public Xoi_wiki_props_wiki Ns_ary_(Xoi_wiki_props_ns... v) {this.ns_ary = v; return this;} private Xoi_wiki_props_ns[] ns_ary;
	public Xoi_wiki_props_alias[] Alias_ary() {return alias_ary;} public Xoi_wiki_props_wiki Alias_ary_(Xoi_wiki_props_alias... v) {this.alias_ary = v; return this;} private Xoi_wiki_props_alias[] alias_ary;
}
