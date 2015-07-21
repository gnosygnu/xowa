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
package gplx.xowa.wmfs.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
class Xowmf_wiki_data {
	public Ordered_hash		General_list() {return general_list;}	private final Ordered_hash general_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Namespaces_list() {return namespaces_list;}	private final Ordered_hash namespaces_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Statistics_list() {return statistics_list;}	private final Ordered_hash statistics_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Interwikimap_list() {return interwikimap_list;}	private final Ordered_hash interwikimap_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Namespacealiases_list() {return namespacealiases_list;}	private final Ordered_hash namespacealiases_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Specialpagealiases_list() {return specialpagealiases_list;}	private final Ordered_hash specialpagealiases_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Libraries_list() {return libraries_list;}	private final Ordered_hash libraries_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Extensions_list() {return extensions_list;}	private final Ordered_hash extensions_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Skins_list() {return skins_list;}	private final Ordered_hash skins_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Magicwords_list() {return magicwords_list;}	private final Ordered_hash magicwords_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Functionhooks_list() {return functionhooks_list;}	private final Ordered_hash functionhooks_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Showhooks_list() {return showhooks_list;}	private final Ordered_hash showhooks_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Extensiontags_list() {return extensiontags_list;}	private final Ordered_hash extensiontags_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Protocols_list() {return protocols_list;} private final Ordered_hash protocols_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Defaultoptions_list() {return defaultoptions_list;} private final Ordered_hash defaultoptions_list = Ordered_hash_.new_bry_();
	public Ordered_hash		Languages_list() {return languages_list;} private final Ordered_hash languages_list = Ordered_hash_.new_bry_();
}
