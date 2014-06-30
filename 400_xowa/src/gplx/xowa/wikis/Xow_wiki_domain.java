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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
public class Xow_wiki_domain {
	public Xow_wiki_domain(byte[] raw, byte tid, byte[] lang)					{this.raw = raw; this.tid = tid; this.lang = this.lang_orig = lang;}
	public Xow_wiki_domain(byte[] raw, byte tid, byte[] lang, byte[] lang_orig) {this.raw = raw; this.tid = tid; this.lang = lang; this.lang_orig = lang_orig;}
	public byte[] Raw() {return raw;} private byte[] raw;
	public byte Tid() {return tid;} private byte tid;
	public byte[] Lang_orig() {return lang_orig;} private byte[] lang_orig;
	public byte[] Lang() {return lang;} private byte[] lang;
}
