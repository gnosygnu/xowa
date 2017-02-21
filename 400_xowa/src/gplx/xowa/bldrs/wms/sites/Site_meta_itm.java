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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
public class Site_meta_itm {
	public Ordered_hash			General_list() {return general_list;} private final    Ordered_hash general_list = Ordered_hash_.New_bry();
	public Ordered_hash			Namespace_list() {return namespace_list;} private final    Ordered_hash namespace_list = Ordered_hash_.New();
	public Site_statistic_itm	Statistic_itm() {return statistic_itm;} private final    Site_statistic_itm statistic_itm = new Site_statistic_itm();
	public Ordered_hash			Interwikimap_list() {return interwikimap_list;} private final    Ordered_hash interwikimap_list = Ordered_hash_.New_bry();
	public List_adp				Namespacealias_list() {return namespacealias_list;} private final    List_adp namespacealias_list = List_adp_.New();
	public Ordered_hash			Specialpagealias_list() {return specialpagealias_list;} private final    Ordered_hash specialpagealias_list = Ordered_hash_.New_bry();
	public Ordered_hash			Library_list() {return library_list;} private final    Ordered_hash library_list = Ordered_hash_.New_bry();
	public Ordered_hash			Extension_list() {return extension_list;} private final    Ordered_hash extension_list = Ordered_hash_.New_bry();
	public Ordered_hash			Skin_list() {return skin_list;} private final    Ordered_hash skin_list = Ordered_hash_.New_bry();
	public Ordered_hash			Magicword_list() {return magicword_list;} private final    Ordered_hash magicword_list = Ordered_hash_.New_bry();
	public Ordered_hash			Functionhook_list() {return functionhook_list;} private final    Ordered_hash functionhook_list = Ordered_hash_.New_bry();
	public Ordered_hash			Showhook_list() {return showhook_list;} private final    Ordered_hash showhook_list = Ordered_hash_.New_bry();
	public Ordered_hash			Extensiontag_list() {return extensiontag_list;} private final    Ordered_hash extensiontag_list = Ordered_hash_.New_bry();
	public Ordered_hash			Protocol_list() {return protocol_list;} private final    Ordered_hash protocol_list = Ordered_hash_.New_bry();
	public Ordered_hash			Defaultoption_list() {return defaultoption_list;} private final    Ordered_hash defaultoption_list = Ordered_hash_.New();
	public Ordered_hash			Language_list() {return language_list;} private final    Ordered_hash language_list = Ordered_hash_.New_bry();
	public Site_meta_itm Clear() {
		general_list.Clear();
		namespace_list.Clear();
		statistic_itm.Ctor(0, 0, 0, 0, 0, 0, 0, 0, 0);
		interwikimap_list.Clear();
		namespacealias_list.Clear();
		specialpagealias_list.Clear();
		library_list.Clear();
		extension_list.Clear();
		skin_list.Clear();
		magicword_list.Clear();
		functionhook_list.Clear();
		showhook_list.Clear();
		extensiontag_list.Clear();
		protocol_list.Clear();
		defaultoption_list.Clear();
		language_list.Clear();
		return this;
	}
}
