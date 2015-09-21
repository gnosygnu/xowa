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
package gplx.xowa.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.wms.*;
public class Site_meta_itm {
	public Ordered_hash			General_list() {return general_list;} private final Ordered_hash general_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Namespace_list() {return namespace_list;} private final Ordered_hash namespace_list = Ordered_hash_.new_();
	public Site_statistic_itm	Statistic_itm() {return statistic_itm;} private final Site_statistic_itm statistic_itm = new Site_statistic_itm();
	public Ordered_hash			Interwikimap_list() {return interwikimap_list;} private final Ordered_hash interwikimap_list = Ordered_hash_.new_bry_();
	public List_adp				Namespacealias_list() {return namespacealias_list;} private final List_adp namespacealias_list = List_adp_.new_();
	public Ordered_hash			Specialpagealias_list() {return specialpagealias_list;} private final Ordered_hash specialpagealias_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Library_list() {return library_list;} private final Ordered_hash library_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Extension_list() {return extension_list;} private final Ordered_hash extension_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Skin_list() {return skin_list;} private final Ordered_hash skin_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Magicword_list() {return magicword_list;} private final Ordered_hash magicword_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Functionhook_list() {return functionhook_list;} private final Ordered_hash functionhook_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Showhook_list() {return showhook_list;} private final Ordered_hash showhook_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Extensiontag_list() {return extensiontag_list;} private final Ordered_hash extensiontag_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Protocol_list() {return protocol_list;} private final Ordered_hash protocol_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Defaultoption_list() {return defaultoption_list;} private final Ordered_hash defaultoption_list = Ordered_hash_.new_();
	public Ordered_hash			Language_list() {return language_list;} private final Ordered_hash language_list = Ordered_hash_.new_bry_();
}
