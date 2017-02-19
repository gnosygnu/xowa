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
package gplx.xowa.mediawiki.includes.title; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
public class XomwMediaWikiTitleCodecParts {
	public byte[] interwiki;
	public boolean local_interwiki;
	public byte[] fragment = Bry_.Empty;
	public int ns;
	public byte[] dbkey;
	public byte[] user_case_dbkey;
	public XomwMediaWikiTitleCodecParts(byte[] dbkey, int defaultNamespace) {
		this.interwiki = Bry_.Empty;
		this.local_interwiki = false;
		this.fragment = Bry_.Empty;
		this.ns = defaultNamespace;
		this.dbkey = dbkey;
		this.user_case_dbkey = dbkey;
	}
}
